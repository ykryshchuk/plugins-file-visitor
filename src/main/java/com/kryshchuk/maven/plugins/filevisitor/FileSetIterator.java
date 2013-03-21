/**
 * 
 */
package com.kryshchuk.maven.plugins.filevisitor;

import java.io.File;

/**
 * FileSetIterator class.
 * 
 * @author yura
 * @since 1.0
 */
public class FileSetIterator extends AbstractFileIterator {

  private final FileSet fileset;

  public FileSetIterator(final FileSet fileset, final FileMapper fileMapper) {
    super(fileMapper);
    this.fileset = fileset;
  }

  @Override
  public void iterate(final FileVisitor visitor) throws FilesIterationException {
    iterate(fileset.getDirectory(), new FileLister(fileset), visitor);
  }

  private void iterate(final File dir, final FileLister fileLister, final FileVisitor visitor)
      throws FilesIterationException {
    final File[] list = dir.listFiles(fileLister);
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

}
