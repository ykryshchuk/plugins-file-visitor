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
import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.testng.annotations.Test;

/**
 * @author yura
 */
public class FileSetIteratorTest {

  @Test
  public void iterateMissingDirectory() throws VisitorException {
    final FileSet fileset = new FileSet();
    fileset.setDirectory(new File("unexisting-dir"));
    final FileSetIterator iterator = new FileSetIterator(fileset, new DummyFileMapper());
    final FileVisitor visitor = Mockito.mock(FileVisitor.class);
    iterator.iterate(visitor);
  }

  @Test
  public void runScenarioOther() throws IOException, VisitorException {
    // setup dir
    final File dir = File.createTempFile("jfcmp-", "-test");
    Assert.assertTrue(dir.delete());
    Assert.assertTrue(dir.mkdirs());
    Assert.assertTrue(new File(dir, "data1.txt").createNewFile());
    Assert.assertTrue(new File(dir, "data2.txt").createNewFile());
    final File subdir = new File(dir, "subdir");
    Assert.assertTrue(subdir.mkdirs());
    Assert.assertTrue(new File(subdir, "file1.log").createNewFile());
    Assert.assertTrue(new File(subdir, "file2.log").createNewFile());
    Assert.assertTrue(new File(subdir, "other.log").createNewFile());
    // setup fileset
    final FileSet fileset = new FileSet();
    fileset.setDirectory(dir);
    fileset.setIncludes(Arrays.asList("subdir/*.log"));
    fileset.setExcludes(Arrays.asList("*/other.*"));
    // iterate
    final FileSetIterator iterator = new FileSetIterator(fileset, new DummyFileMapper());
    final FileVisitor visitor = Mockito.mock(FileVisitor.class);
    iterator.iterate(visitor);
    Mockito.verify(visitor).visit(Matchers.eq(new File(subdir, "file1.log")), Matchers.any(File.class));
    Mockito.verify(visitor).visit(Matchers.eq(new File(subdir, "file2.log")), Matchers.any(File.class));
    Mockito.verifyNoMoreInteractions(visitor);
    // cleanup

  }

  @Test
  public void runScenarioWithRoot() throws IOException, VisitorException {
    // setup dir
    final File dir = File.createTempFile("jfcmp-", "-test");
    Assert.assertTrue(dir.delete());
    Assert.assertTrue(dir.mkdirs());
    Assert.assertTrue(new File(dir, "file1.txt").createNewFile());
    Assert.assertTrue(new File(dir, "file2.txt").createNewFile());
    final File subdir = new File(dir, "subdir");
    Assert.assertTrue(subdir.mkdirs());
    Assert.assertTrue(new File(subdir, "file1.log").createNewFile());
    Assert.assertTrue(new File(subdir, "file2.log").createNewFile());
    Assert.assertTrue(new File(subdir, "other.log").createNewFile());
    Assert.assertTrue(new File(subdir, "more.txt").createNewFile());
    // setup fileset
    final FileSet fileset = new FileSet();
    fileset.setDirectory(dir);
    fileset.setIncludes(Arrays.asList("**/*.txt"));
    // iterate
    final FileSetIterator iterator = new FileSetIterator(fileset, new DummyFileMapper());
    final FileVisitor visitor = Mockito.mock(FileVisitor.class);
    iterator.iterate(visitor);
    Mockito.verify(visitor).visit(Matchers.eq(new File(dir, "file1.txt")), Matchers.any(File.class));
    Mockito.verify(visitor).visit(Matchers.eq(new File(dir, "file2.txt")), Matchers.any(File.class));
    Mockito.verify(visitor).visit(Matchers.eq(new File(subdir, "more.txt")), Matchers.any(File.class));
    Mockito.verifyNoMoreInteractions(visitor);
    // cleanup

  }

}
