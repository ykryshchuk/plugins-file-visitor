/**
 * 
 */
package com.kryshchuk.maven.plugins.filevisitor;

/**
 * FilesIterationException class.
 * 
 * @author yura
 * @since 1.0
 */
public class FilesIterationException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public FilesIterationException(final String message) {
    super(message);
  }

  public FilesIterationException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
