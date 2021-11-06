package model.filters;

import model.Pixel;


public class Sharpen extends AFilter {
  private static final float[][] KERNEL = { {-1f/8f, -1f/8f, -1f/8f, -1f/8f, -1f/8f},
                                            {-1f/8f, 1f/4f, 1f/4f, 1f/4f, -1f/8f},
                                            {-1f/8f, 1f/4f, 1f, 1f/4f, -1f/8f},
                                            {-1f/8f, 1f/4f, 1f/4f, 1f/4f, -1f/8f},
                                            {-1f/8f, -1f/8f, -1f/8f, -1f/8f, -1f/8f} };


  public Sharpen(Pixel[][] image) {
    super(KERNEL, image);
  }
}
