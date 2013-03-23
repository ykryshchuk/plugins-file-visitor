/**
 * 
 */
package com.kryshchuk.maven.plugins.filevisitor;

import java.io.File;

/**
 * File mapper that returns the same file.
 * 
 * @author yura
 * @since 1.0.2
 */
public class IdentityFileMapper implements FileMapper {

  public File getMappedFile(final File file) {
    return file;
  }

}