package model;

import java.util.Arrays;

/**
 * This class represents a 3x3 matrix of color coefficients to produce a color transformation
 * of images with R, G, and B channels.
 */
public class RGBColorMatrix implements ColorMatrix {
  private final Double[][] matrix;

  /**
   * Constructs a new RGBColorMatrix from a 2d array of color coefficients.
   * Each row should contain R, G, and B coefficients in that order, with the first row
   * containing coefficients for the red result channel, the second row for the green result
   * channel, and the third row for the blue result channel.
   *
   * @param array a 2d array of color coefficients
   * @throws IllegalArgumentException if the provided array is null or is not 3x3
   */
  public RGBColorMatrix(Double[][] array) throws IllegalArgumentException {
    if (array == null || !this.validSize(array)) {
      throw new IllegalArgumentException("Must provide a valid 3x3 array.");
    }
    this.matrix = array;
  }

  // determines whether the given 2d array is 3x3
  private boolean validSize(Double[][] array) {
    if (array.length == 3) { // make sure array has 3 rows before checking them
      return ((array[0].length == 3) && (array[1].length == 3) && (array[2].length == 3));
    }
    else { // if there aren't 3 rows, def not 3x3
      return false;
    }
  }

  @Override
  public Double[] redCoefficients() {
    return this.matrix[0].clone(); // first row is the red result coefficients
  }

  @Override
  public Double[] greenCoefficients() {
    return this.matrix[1].clone(); // second row is the green result coefficients
  }

  @Override
  public Double[] blueCoefficients() {
    return this.matrix[2].clone(); // third row is the blue result coefficients
  }

  @Override
  public String toString() {
    return Arrays.deepToString(matrix);
  }
}
