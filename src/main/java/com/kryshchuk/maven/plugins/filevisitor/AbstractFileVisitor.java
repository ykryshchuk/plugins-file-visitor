/**
 * 
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
