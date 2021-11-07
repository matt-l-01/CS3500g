package model.colortransform;

import model.Pixel;

/**
 * Greyscale the image by using a color transformation.
 */
public class Greyscale extends AColorTransform {
  private static final float[][] MATRIX = {{0.2126f, 0.7152f, 0.0722f},
                                            {0.2126f, 0.7152f, 0.0722f},
                                            {0.2126f, 0.7152f, 0.0722f}};

  public Greyscale(Pixel[][] image) {
    super(MATRIX, image);
  }
}
