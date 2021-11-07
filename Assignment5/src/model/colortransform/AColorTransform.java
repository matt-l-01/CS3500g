package model.colortransform;

import model.Pixel;

/**
 * Represents an abstract color transformation function object to transform an image's color.
 */
public abstract class AColorTransform {
  float[][] matrix;
  Pixel[][] image;

  /**
   * Constructs a new abstract color transformation function object.
   * @param matrix the specific matrix values for this color transformation.
   * @param image the array of Pixels to be operated on.
   */
  public AColorTransform(float[][] matrix, Pixel[][] image) {

  }

  /**
   * Transforms the image using the given coloring filter values.
   * @return the new array of Pixels representing the image.
   */
  public Pixel[][] transform() {
    return null;
  }
}
