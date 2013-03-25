/*
 * File iteration support for maven plugins.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.kryshchuk.maven.plugins.filevisitor;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * FileLister class.
 * 
 * @author yura
 * @since 1.0
 */
public class FileLister extends PathFilter implements FileFilter {

  private static final String DEEP_PREFIX = PathFilter.WILDCARD_DEEP + "/";

  private final String relativePath;

  /**
   * @param pathFilter
   */
  public FileLister(final PathFilter pathFilter) {
    relativePath = null;
    if (pathFilter != null) {
      setIncludes(extend(pathFilter.getIncludes()));
      setExcludes(extend(pathFilter.getExcludes()));
    }
  }

  private FileLister(final String relativePath) {
    this.relativePath = relativePath;
  }

  private static List<String> extend(final List<String> paths) {
    if (paths == null) {
      return null;
    }
    final Set<String> extended = new HashSet<String>();
    for (final String path : paths) {
      extended.add(path);
      if (path.startsWith(DEEP_PREFIX)) {
        extended.add(path.substring(DEEP_PREFIX.length()));
      }
    }
    return new ArrayList<String>(extended);
  }

  public boolean accept(final File file) {
    if (!isIncludesAny()) {
      if (getIncludes() != null) {
        if (!match(file, getIncludes(), false)) {
          return false;
        }
      }
    }
    if (isExcludesAny()) {
      return false;
    }
    if (getExcludes() != null) {
      if (match(file, getExcludes(), true)) {
        return false;
      }
    }
    return true;
  }

  protected static boolean match(final File file, final List<String> paths, final boolean firstLevel) {
    for (final String path : paths) {
      if (match(file, path, firstLevel)) {
        return true;
      }
    }
    return false;
  }

  protected static boolean match(final File file, final String path, final boolean firstLevel) {
    final String fname = FileSet.convertPath(file.getName());
    if (fname.equals(path) || PathFilter.matchesWildcards(path)) {
      return true;
    } else {
      final int pathSeparator = path.indexOf('/');
      if (pathSeparator > 0) {
        if (!firstLevel && file.isDirectory()) {
          return match(file, path.substring(0, pathSeparator), true);
        } else {
          return false;
        }
      } else {
        final String pathRegexp = path.replace(".", "\\.").replace('?', '.').replace(PathFilter.WILDCARD, ".*");
        return Pattern.matches(pathRegexp, fname);
      }
    }
  }

  /**
   * @return the relativePath
   */
  public String getRelativePath() {
    return relativePath;
  }

  /**
   * @param file
   * @return
   */
  public FileLister getFileLister(final File file) {
    final String fname = FileSet.convertPath(file.getName());
    final FileLister fileLister = new FileLister(getNestedRelativePath(fname));
    if (getIncludes() != null) {
      fileLister.setIncludes(expand(getIncludes(), fname));
    }
    if (getExcludes() != null) {
      fileLister.setExcludes(expand(getExcludes(), fname));
    }
    return fileLister;
  }

  protected static List<String> expand(final List<String> paths, final String fname) {
    final List<String> expanded = new ArrayList<String>();
    for (final String inc : paths) {
      if (inc.startsWith(PathFilter.WILDCARD_DEEP)) {
        expanded.add(inc);
      }
      final int firstSeparator = inc.indexOf('/');
      if (firstSeparator > 0 && (firstSeparator < inc.length() - 1)) {
        final String firstPath = inc.substring(0, firstSeparator);
        final String remainingPath = inc.substring(firstSeparator + 1);
        if (PathFilter.matchesWildcards(firstPath) || fname.equals(firstPath)) {
          expanded.add(remainingPath);
        }
      }
    }
    return expanded;
  }

  protected String getNestedRelativePath(final String fname) {
    if (relativePath == null) {
      return fname;
    } else {
      return relativePath + "/" + fname;
    }
  }

}
