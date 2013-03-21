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
public class VisitorException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public VisitorException(final String message) {
    super(message);
  }

  public VisitorException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
