package model;

/**
 * Represents operations offered by a matrix representing a color transformation.
 */
public interface ColorMatrix {

  /**
   * Gets an array containing the coefficients to apply to the original RGB values of a pixel
   * to produce the red component of the output.
   *
   * @return an array of coefficients to produce the transformed red component
   */
  Double[] redCoefficients();

  /**
   * Gets an array containing the coefficients to apply to the original RGB values of a pixel
   * to produce the green component of the output.
   *
   * @return an array of coefficients to produce the transformed green component
   */
  Double[] greenCoefficients();

  /**
   * Gets an array containing the coefficients to apply to the original RGB values of a pixel
   * to produce the blue component of the output.
   *
   * @return an array of coefficients to produce the transformed blue component
   */
  Double[] blueCoefficients();

  /**
   * Overriding toString to produce a string containing the contents of this color matrix.
   *
   * @return the contents of this color matrix, as a string
   */
  @Override
  String toString();
}
