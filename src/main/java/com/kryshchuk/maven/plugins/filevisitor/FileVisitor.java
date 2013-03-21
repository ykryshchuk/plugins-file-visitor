/**
 * 
 */
package com.kryshchuk.maven.plugins.filevisitor;

import java.io.File;

/**
 * FileVisitor interface.
 * 
 * @author yura
 * @since 1.0
 */
public interface FileVisitor {

  void visit(File inputFile, File outputFile) throws VisitorException;

}
