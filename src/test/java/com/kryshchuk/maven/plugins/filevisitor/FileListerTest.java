/*
 * File iteration helper library for Maven Plugins.
 * Copyright (C) 2013  Yuriy Kryshchuk
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses />.
 */
package com.kryshchuk.maven.plugins.filevisitor;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author yura
 */
public class FileListerTest {

  @Test
  public void verifyDirMatchesWithString() {
    final File dir = Mockito.spy(new File("/tmp/mydir"));
    Mockito.doReturn(true).when(dir).isDirectory();
    Assert.assertTrue(FileLister.match(dir, "**", false));
    Assert.assertTrue(FileLister.match(dir, "**/file", false));
    Assert.assertTrue(FileLister.match(dir, "*", false));
    Assert.assertTrue(FileLister.match(dir, "*/*/file", false));
    Assert.assertFalse(FileLister.match(dir, "*/*/file", true));
    Assert.assertTrue(FileLister.match(dir, "mydir", false));
    Assert.assertTrue(FileLister.match(dir, "mydir/subdir", false));
    Assert.assertFalse(FileLister.match(dir, "mydib/subdir", false));
    Assert.assertTrue(FileLister.match(dir, "my?ir", false));
    Assert.assertTrue(FileLister.match(dir, "*dir", false));
    Assert.assertTrue(FileLister.match(dir, "my*", false));
  }

  @Test
  public void verifyFileMatchesWithString() {
    final File f = new File("/tmp/mydir/file.ext");
    Assert.assertTrue(FileLister.match(f, "**", false));
    Assert.assertFalse(FileLister.match(f, "**/file.ext", false));
    Assert.assertTrue(FileLister.match(f, "*", false));
    Assert.assertFalse(FileLister.match(f, "*/file.ext", false));
    Assert.assertTrue(FileLister.match(f, "file.ext", false));
    Assert.assertFalse(FileLister.match(f, "mydir/file.ext", false));
    Assert.assertFalse(FileLister.match(f, "fille.ext", false));
    Assert.assertFalse(FileLister.match(f, "file.exxt", false));
  }

  @Test
  public void verifyConstructorWithPathFilter() {
    final PathFilter pathFilter = Mockito.mock(PathFilter.class);
    Mockito.doReturn(Arrays.asList("a", "b", "c")).when(pathFilter).getIncludes();
    Mockito.doReturn(Arrays.asList("1", "2", "3")).when(pathFilter).getExcludes();
    final FileLister fileLister = new FileLister(pathFilter);
    Assert.assertNull(fileLister.getRelativePath(), "Default relative path shall be null");
    Assert.assertEqualsNoOrder(fileLister.getIncludes().toArray(), new String[] { "a", "b", "c" }, "Wrong includes");
    Assert.assertEqualsNoOrder(fileLister.getExcludes().toArray(), new String[] { "1", "2", "3" }, "Wrong excludes");
  }

  @Test
  public void verifyConstructorWithAnyPathFilter() {
    final PathFilter pathFilter = Mockito.mock(PathFilter.class);
    Mockito.doReturn(Arrays.asList("a", "**/b")).when(pathFilter).getIncludes();
    Mockito.doReturn(Arrays.asList("1", "**/2")).when(pathFilter).getExcludes();
    final FileLister fileLister = new FileLister(pathFilter);
    Assert.assertNull(fileLister.getRelativePath(), "Default relative path shall be null");
    Assert.assertEqualsNoOrder(fileLister.getIncludes().toArray(), new String[] { "a", "**/b", "b" }, "Wrong includes");
    Assert.assertEqualsNoOrder(fileLister.getExcludes().toArray(), new String[] { "1", "**/2", "2" }, "Wrong excludes");
  }

  @Test
  public void verifyNestedRelativePath() {
    final FileLister fileLister = new FileLister(null);
    final String path1 = fileLister.getNestedRelativePath("sub1");
    Assert.assertEquals(path1, "sub1");
  }

  @Test
  public void verify2ndNestedRelativePath() {
    final FileLister fileLister = new FileLister(null).getFileLister(new File("subdir"));
    final String path1 = fileLister.getNestedRelativePath("sub1");
    Assert.assertEquals(path1, "subdir/sub1");
  }

  @Test
  public void verifyExpand() {
    final List<String> list = Arrays.asList("**/file.txt", "*/*/log", "dir1/*", "dir2/*");
    Assert.assertEquals(FileLister.expand(list, "dir"), Arrays.asList("**/file.txt", "file.txt", "*/log"));
    Assert.assertEquals(FileLister.expand(list, "dir1"), Arrays.asList("**/file.txt", "file.txt", "*/log", "*"));
    Assert.assertEquals(FileLister.expand(list, "dir2"), Arrays.asList("**/file.txt", "file.txt", "*/log", "*"));
  }

  @Test
  public void verifyNestedExpand() {
    final List<String> initial = Arrays.asList("**", "**/file.txt", "*/*/log", "dir1/*", "dir2/*");
    final List<String> expanded = FileLister.expand(initial, "dir1");
    Assert.assertEquals(FileLister.expand(expanded, "sub"), Arrays.asList("**", "**/file.txt", "file.txt", "log"));
  }

}
