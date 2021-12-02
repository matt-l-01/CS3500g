package controller;

/**
 * This interface represents the features that an image processor program offers
 * which a user of the program would need to interact with, such as loading and saving files
 * and performing various image manipulations.
 */
public interface ImageProcessorFeatures {

  /**
   * Loads an image into the program and updates the view to display it.
   *
   * @param filepath the file path of the image to be loaded
   */
  void loadImage(String filepath);

  /**
   * Saves the current image.
   *
   * @param filePath the file path to save the image to
   */
  void saveImage(String filePath);

  /**
   * Has the model visualize the red component of the current image and updates the view
   * to display it.
   */
  void visualizeRed();

  /**
   * Has the model visualize the green component of the current image and updates the view
   * to display it.
   */
  void visualizeGreen();

  /**
   * Has the model visualize the blue component of the current image and updates the view
   * to display it.
   */
  void visualizeBlue();

  /**
   * Has the model visualize the value component of the current image and updates the view
   * to display it.
   */
  void visualizeValue();

  /**
   * Has the model visualize the intensity component of the current image and updates the view
   * to display it.
   */
  void visualizeIntensity();

  /**
   * Has the model visualize the luma component of the current image and updates the view
   * to display it.
   */
  void visualizeLuma();

  /**
   * Has the model brighten the current image by a given amount and updates the view
   * to display it.
   *
   * @param amount the amount to brighten by (positive)
   */
  void brighten(String amount);

  /**
   * Has the model darken the current image by a given amount and updates the view
   * to display it.
   *
   * @param amount the amount to darken by (positive)
   */
  void darken(String amount);

  /**
   * Has the model flip the current image horizontally and updates the view
   * to display it.
   */
  void flipHorizontal();

  /**
   * Has the model flip the current image vertically and updates the view
   * to display it.
   */
  void flipVertical();

  /**
   * Has the model produce a greyscale version of the current image and updates the view
   * to display it.
   */
  void greyscale();

  /**
   * Has the model produce a sepia-toned version of the current image and updates the view
   * to display it.
   */
  void sepia();

  /**
   * Has the model produce a blurred version of the current image and updates the view
   * to display it.
   */
  void blur();

  /**
   * Has the model produce a sharpened version of the current image and updates the view
   * to display it.
   */
  void sharpen();

  /**
   * Has the model produce a mosaic version of the current image and updates the view
   * to display it.
   * @param text
   */
  void mosaic(String text);
//r

}
