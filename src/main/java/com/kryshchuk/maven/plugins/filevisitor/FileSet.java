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
