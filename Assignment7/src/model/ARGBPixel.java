package model;

/**
 * Simple class extends the RGBPixel
 * class to allow the storage of pixels that
 * support alpha components.
 */
public class ARGBPixel extends RGBPixel {
  private final int alpha;

  /**
   * Public constructor for the pixel
   * class. Sets the passed RGB values
   * to the fields.
   *
   * @param r red value
   * @param g green value
   * @param b blue value
   * @throws IllegalArgumentException if any color value is negative
   */
  public ARGBPixel(int a, int r, int g, int b) throws IllegalArgumentException {
    super(r, g, b);
    this.alpha = a;
  }

  public int getAlpha() {
    return this.alpha;
  }
}
