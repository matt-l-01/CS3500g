package model.colortransform;

import model.Pixel;

public class Sepia extends AColorTransform{

  public static final float[][] matrix = {{0.393f, 0.769f, 0.189f},
                                      {0.349f, 0.686f, 0.168f},
                                      {0.272f, 0.534f, 0.131f}};


  /**
   * Constructs a new abstract color transformation function object.
   *
   * @param matrix the specific matrix values for this color transformation.
   * @param image  the array of Pixels to be operated on.
   */
  public Sepia(float[][] matrix, Pixel[][] image) {
    super(matrix, image);
  }
}
