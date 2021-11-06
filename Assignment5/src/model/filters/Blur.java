package model.filters;

import model.Pixel;


public class Blur extends AFilter {
  private static final float[][] KERNEL = {{1f / 16f, 1f / 8f, 1f / 16f},
                                          {1f / 8f, 1f / 4f, 1f / 8f},
                                          {1f / 16f, 1f / 8f, 1f / 16f}};

  public Blur(Pixel[][] image) {
    super(KERNEL, image);
  }
}
