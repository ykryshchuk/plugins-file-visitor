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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FileSetIterator class.
 * 
 * @author yura
 * @since 1.0
 */
public class FileSetIterator extends AbstractFileIterator {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileSetIterator.class);

  private final FileSet fileset;

  public FileSetIterator(final FileSet fileset, final FileMapper fileMapper) {
    super(fileMapper);
    this.fileset = fileset;
  }

  @Override
  public void iterate(final FileVisitor visitor) throws VisitorException {
    iterate(fileset.getDirectory(), new FileLister(fileset), visitor);
  }

  private void iterate(final File dir, final FileLister fileLister, final FileVisitor visitor) throws VisitorException {
    if (dir.isDirectory()) {
      final File[] list = dir.listFiles(fileLister);
      if (list == null) {
        LOGGER.warn("Failed to iterate directory {}", dir);
      } else {
        for (final File file : list) {
          if (file.isDirectory()) {
            final FileLister nestedFileLister = fileLister.getFileLister(file);
            iterate(file, nestedFileLister, visitor);
          } else {
            final File outputFile = getFileMapper().getMappedFile(file);
            visitor.visit(file, outputFile);
          }
        }
      }
    } else {
      LOGGER.warn("Fileset source {} is not an existing directory", dir);
    }
  }

}
