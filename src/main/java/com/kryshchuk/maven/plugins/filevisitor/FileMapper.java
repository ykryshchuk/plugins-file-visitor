/**
 * 
 */
package com.kryshchuk.maven.plugins.filevisitor;

import java.io.File;

/**
 * @author yura
 * @since 1.0
 */
public interface FileMapper {

  File getMappedFile(File file);

}
