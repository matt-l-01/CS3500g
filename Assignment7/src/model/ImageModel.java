package model;


/**
 * Interface containing the methods
 * used for processing the image model.
 *
 * @author Matt Stetter
 */
public interface ImageModel {

  /**
   * Visualize the given component of this image.
   *
   * @param component the component of the image to be visualized
   * @return a new image visualizing the red component
   * @throws IllegalArgumentException if the given component is null
   * @throws IllegalStateException if the image has not been initialized
   */
  ImageModel visualizeComponent(Component component)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Visualize this image flipped along the given direction.
   *
   * @param direction the direction to flip the image, eg horizontally or vertically
   * @return a new image visualizing this image flipped in a direction
   * @throws IllegalArgumentException if the given direction is not horizontal or vertical
   * @throws IllegalStateException if the image has not been initialized
   */
  ImageModel flip(String direction)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Brighten or darken this image by the specified amount.
   *
   * @param increment the amount to brighten this image, where positive values brighten and
   *                  negative values darken
   * @return a new image visualizing a brightened or darkened image
   */
  ImageModel brighten(int increment);

  /**
   * Filter this image according to the given kernel. Currently only supports filtering uniformly
   * across all color channels.
   *
   * @param kernel the kernel of the desired filter
   * @return a new image visualizing a filtered image
   */
  ImageModel filter(Kernel kernel);

  /**
   * Apply a color transformation according to the coefficients in the given matrix.
   * Supports a color transformation is such that the RGB components of each pixel in a resulting
   * image are linear combinations of the RGB values in the original image.
   *
   * @param matrix the matrix of color coefficients for the desired transformation
   * @return a new image visualizing a color transformed image
   */
  ImageModel colorTransform(ColorMatrix matrix);

  /**
   * Conducts a mosaic operation on the given image using the number of seeds provided.
   * @param seeds the number of seeds to conduct the operation with.
   * @return a new image visualizing a mosaic image
   */
  ImageModel mosaic(int seeds) throws IllegalArgumentException;

  /**
   * Returns the width of the
   * image in pixels.
   *
   * @return image width
   */
  int getWidth();

  /**
   * Returns the height of
   * the image in pixels.
   *
   * @return image height
   */
  int getHeight();

  /**
   * Returns the maximum
   * allowed value for
   * ARGB component.
   *
   * @return max value for ARGB component
   */
  int getMaxRGB();

  /**
   * Returns the pixel at the supplied
   * row and column position. Throws
   * IllegalArgumentException if the position
   * is invalid.
   *
   * @param row pixel row
   * @param col pixel column
   * @return Pixel at specified position
   * @throws IllegalArgumentException if the position
   *         is out of bounds of the image.
   */
  Pixel getPixel(int row, int col);

  /**
   * Returns the text of the PPM file
   * using the contents of the image.
   *
   * @return string file contents
   */
  String toPPMFile();

  /**
   * Enum declaration used to differentiate
   * between the different options for
   * visualizing each component of an
   * image.
   */
  enum Component {
    RED,
    GREEN,
    BLUE,
    VALUE,
    INTENSITY,
    LUMA
  }
}
