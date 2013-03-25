/*
 * File iteration library for plugins.
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

import java.util.List;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * Abstract PathFilter class.
 * 
 * @author yura
 * @since 1.0
 */
public abstract class PathFilter {

  /**
   * 
   */
  static final String WILDCARD_DEEP = "**";

  /**
   * 
   */
  static final String WILDCARD = "*";

  @Parameter(required = false)
  private List<String> includes;

  @Parameter(required = false)
  private List<String> excludes;

  private boolean includesAny;

  private boolean excludesAny;

  /**
   * @return the includes
   */
  public List<String> getIncludes() {
    return includes;
  }

  /**
   * @return the excludes
   */
  public List<String> getExcludes() {
    return excludes;
  }

  /**
   * @param includes
   *          the includes to set
   */
  protected void setIncludes(final List<String> includes) {
    this.includes = includes;
    if (includes != null) {
      for (final String inc : includes) {
        if (matchesWildcards(inc)) {
          includesAny = true;
          break;
        }
      }
    }
  }

  /**
   * @param excludes
   *          the excludes to set
   */
  protected void setExcludes(final List<String> excludes) {
    this.excludes = excludes;
    if (excludes != null) {
      for (final String exc : excludes) {
        if (matchesWildcards(exc)) {
          excludesAny = true;
          break;
        }
      }
    }
  }

  static boolean matchesWildcards(final String path) {
    return WILDCARD_DEEP.equals(path) || WILDCARD.equals(path);
  }

  /**
   * @return the includesAny
   */
  protected boolean isIncludesAny() {
    return includesAny;
  }

  /**
   * @return the excludesAny
   */
  protected boolean isExcludesAny() {
    return excludesAny;
  }

  @Override
  public String toString() {
    final StringBuilder str = new StringBuilder();
    if (includes != null) {
      str.append("includes=").append(includes).append(" ");
    }
    if (excludes != null) {
      str.append("excludes=").append(excludes).append(" ");
    }
    return str.toString();
  }

}
