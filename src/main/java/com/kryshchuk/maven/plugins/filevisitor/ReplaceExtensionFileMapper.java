/**
 * 
 */
package com.kryshchuk.maven.plugins.filevisitor;

import java.io.File;

/**
 * @author yura
 * @since 1.0
 */
public class ReplaceExtensionFileMapper implements FileMapper {

  private final String absSourceDirPath;

  private final File targetDir;

  private final String targetExt;

  /**
   * 
   */
  public ReplaceExtensionFileMapper(final File sourceDir, final File targetDir, final String targetExt) {
    absSourceDirPath = FileSet.convertPath(sourceDir.getAbsolutePath());
    this.targetDir = targetDir;
    this.targetExt = targetExt;
  }

  public File getMappedFile(final File file) {
    final String absFilepath = FileSet.convertPath(file.getAbsolutePath());
    if (absFilepath.startsWith(absSourceDirPath)) {
      final String relpath = cleanRelPath(absFilepath.substring(absSourceDirPath.length()));
      final String justname = getPathWithoutExtension(relpath);
      return new File(targetDir, justname + "." + targetExt);
    } else {
      throw new IllegalArgumentException("File is not in source tree");
    }
  }

  private String getPathWithoutExtension(final String path) {
    final int lastSeparator = path.lastIndexOf('/');
    final int lastDot = path.lastIndexOf('.');
    if (lastDot < 0) {
      return path;
    }
    if (lastSeparator > 0) {
      if (lastDot < lastSeparator || lastDot == lastSeparator + 1) {
        return path;
      }
    } else {
      if (lastDot == 0) {
        return path;
      }
    }
    return path.substring(0, lastDot);
  }

  private static String cleanRelPath(final String relpath) {
    switch (relpath.charAt(0)) {
    case '\\':
    case '/':
      return cleanRelPath(relpath.substring(1));
    default:
      return FileSet.convertPath(relpath);
    }
  }

}
