/**
 * 
 */
package com.kryshchuk.maven.plugins.filevisitor;

import java.io.File;

/**
 * @author yura
 */
class DummyFileMapper implements FileMapper {

  public File getMappedFile(final File file) {
    return null;
  }

}
