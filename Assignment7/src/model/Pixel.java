package model;

/**
 * This interface holds methods representing a single pixel of an image.
 */
public interface Pixel {

  /**
   * Provides the alpha component of this pixel.
   * @return the alpha component of the pixel, rounded to the nearest integer
   */
  int getAlpha();

  /**
   * Provides the red component of this pixel.
   * @return the red component of the pixel, rounded to the nearest integer
   */
  int getRed();

  /**
   * Provides the green component of this pixel.
   * @return the green component of the pixel, rounded to the nearest integer
   */
  int getGreen();

  /**
   * Provides the blue component of this pixel.
   * @return the blue component of the pixel, rounded to the nearest integer
   */
  int getBlue();

  /**
   * Provides the value of this pixel.
   * @return the value of this pixel, rounded to the nearest integer
   */
  int getValue();

  /**
   * Provides the intensity of this pixel.
   * @return the intensity of this pixel, rounded to the nearest integer
   */
  int getIntensity();

  /**
   * Provides the intensity of this pixel.
   * @return the intensity of this pixel, rounded to the nearest integer
   */
  int getLuma();
}
