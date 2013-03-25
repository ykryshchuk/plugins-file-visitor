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

import java.io.File;

/**
 * SingleFileIterator class.
 * 
 * @author yura
 * @since 1.0
 */
public class SingleFileIterator extends AbstractFileIterator {

  private final File file;

  public SingleFileIterator(final File file, final FileMapper fileMapper) {
    super(fileMapper);
    this.file = file;
  }

  @Override
  public void iterate(final FileVisitor visitor) throws VisitorException {
    final File outputFile = getFileMapper().getMappedFile(file);
    visitor.visit(file, outputFile);
  }

}
