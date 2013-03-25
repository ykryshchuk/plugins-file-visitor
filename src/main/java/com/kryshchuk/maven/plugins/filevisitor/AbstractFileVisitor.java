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

/**
 * @author yura
 * @since 1.0.1
 */
public abstract class AbstractFileVisitor implements FileVisitor {

  public void visit(final File inputFile, final File outputFile) throws VisitorException {
    if (inputFile.isFile()) {
      if (outputFile.isFile()) {
        if (outputFile.lastModified() > inputFile.lastModified() && !shouldOverwrite(inputFile, outputFile)) {
          return;
        }
      }
      handleFile(inputFile, outputFile);
    } else {
      throw new VisitorException("Input file does not exist " + inputFile);
    }
  }

  /**
   * @param inputFile
   * @param outputFile
   */
  protected abstract void handleFile(File inputFile, File outputFile) throws VisitorException;

  protected abstract boolean shouldOverwrite(File inputFile, File outputFile);

  protected void verifyParentDirectory(final File file) throws VisitorException {
    final File dir = file.getParentFile();
    if (!dir.isDirectory()) {
      if (dir.exists()) {
        throw new VisitorException("Path " + dir + " expected to be a directory");
      } else {
        if (!dir.mkdirs()) {
          throw new VisitorException("Could not create directory " + dir);
        }
      }
    }
  }

}
