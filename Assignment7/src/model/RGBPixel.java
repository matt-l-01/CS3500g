package model;

/**
 * Represents a pixel with alpha, red, green, and blue components.
 */
public class RGBPixel implements Pixel {
  private final int red;
  private final int green;
  private final int blue;

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
  public RGBPixel(int r, int g, int b) throws IllegalArgumentException {
    if (r < 0 || g < 0 || b < 0) {
      throw new IllegalArgumentException("Pixel cannot have negative color value");
    }
    this.red = r;
    this.green = g;
    this.blue = b;
  }

  @Override
  public int getAlpha() {
    return 255;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public int getValue() {
    return Math.max(this.red, Math.max(this.green, this.blue));
  }

  @Override
  public int getIntensity() {
    return (int) Math.round((this.red + this.green + this.blue) / 3.0);
  }

  @Override
  public int getLuma() {
    return (int) Math.round(0.2126 * this.red + 0.7152 * this.green + 0.0722 * this.blue);
  }

  /**
   * Overrides the Object equals
   * method to compare two pixels.
   * They are equal if their colors
   * are equal to each other.
   *
   * @param other object to compare with
   * @return true if colors are equal, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    } else if (!(other instanceof Pixel)) {
      return false;
    } else {
      return this.getAlpha() == ((Pixel)other).getAlpha()
              && this.getRed() == ((Pixel) other).getRed()
              && this.getGreen() == ((Pixel) other).getGreen()
              && this.getBlue() == ((Pixel) other).getBlue();
    }
  }

  /**
   * Overrides the hashCode
   * function in Object to return
   * an integer for a given pixel.
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    String code = this.getAlpha() + "" + this.getRed() + "" + this.getGreen()
            + "" + this.getBlue();
    return code.hashCode();
  }

  /**
   * Prints out the RGB values
   * of the pixel.
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return String.format("(%d, %d, %d)", this.red, this.green, this.blue);
  }
}
