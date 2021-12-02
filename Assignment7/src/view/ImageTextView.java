package view;

import java.io.IOException;

/**
 * Represents the operations offered by the view for a text-based image processing program.
 */
public interface ImageTextView {

  /**
   * Displays the given message to the user.
   *
   * @param message the message to be displayed
   * @throws IOException if an error occurs in writing to the output destination
   */
  void renderMessage(String message) throws IOException;


}
