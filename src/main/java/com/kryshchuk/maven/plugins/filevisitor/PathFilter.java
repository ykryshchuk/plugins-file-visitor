/**
 * 
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
