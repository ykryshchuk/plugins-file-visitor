/**
 * 
 */
package com.kryshchuk.maven.plugins.filevisitor;

import java.io.File;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * FileSet class.
 * 
 * @author yura
 * @since 1.0
 */
public class FileSet extends PathFilter {

  /**
   * Directory containing input data files for chart(s).
   */
  @Parameter(required = true)
  private File directory;

  /**
   * @return the directory
   */
  public File getDirectory() {
    return directory;
  }

  /**
   * @param directory
   *          the directory to set
   */
  protected void setDirectory(final File directory) {
    this.directory = directory;
  }

  @Override
  public String toString() {
    final StringBuilder str = new StringBuilder("File set: ");
    str.append("directory=").append(directory).append(" ");
    str.append(super.toString());
    return str.toString();
  }

  static String convertPath(final String path) {
    return path.replace('\\', '/');
  }

}
