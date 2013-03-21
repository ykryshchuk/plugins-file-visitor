/**
 * 
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
