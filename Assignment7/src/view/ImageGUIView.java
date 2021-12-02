package view;

import controller.ImageProcessorFeatures;
import model.ImageModel;

/**
 * This interface represents methods offered by a view for an image processing program
 * with a graphical user interface, including redrawing itself, choosing an image to
 * display to the user, adding a set of implemented features, and displaying a message
 * to the user.
 */
public interface ImageGUIView extends ImageTextView {

  /**
   * Adds the set of features that the GUI will offer to the user by way of an object
   * that contains the logic to execute those features.
   *
   * @param features the object that offers the functionality of the features
   */
  void addFeatures(ImageProcessorFeatures features);

  /**
   * Displays a message to the user.
   *
   * @param message the message to be displayed
   */
  @Override
  void renderMessage(String message);

  /**
   * Sets the image to be displayed to the user.
   *
   * @param image the image to be displayed
   */
  void setDisplayImage(ImageModel image);

  /**
   * Re-draws the graphical user interface.
   */
  void refresh();
}
