package model.colortransform;

import model.Pixel;

/**
 * Represents a color transformation and representation of luma
 */
public class Luma extends Greyscale {
  public Luma(Pixel[][] image) {
    super(image);
  }
}
