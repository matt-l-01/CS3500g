package model;

import java.util.Arrays;

/**
 * This class represents a kernel used by an image filter.
 */
public class FilterKernel implements Kernel {
  private final Double[][] array;

  /**
   * Constructs a filter kernel from a 2D array of doubles.
   *
   * @param array the 2D array of doubles to make a kernel out of
   * @throws IllegalArgumentException if the given array is null, not rectangular (not the same
   *                                  number of columns per row), or does not have odd dimensions
   */
  public FilterKernel(Double[][] array) throws IllegalArgumentException {
    if (array == null) {
      throw new IllegalArgumentException("Array cannot be null.");
    }
    if (array.length == 0 || !rectangular(array) || !oddDimensions(array)) {
      throw new IllegalArgumentException("Provided array is not a valid kernel.");
    }
    this.array = array;
  }

  // assumes the array is not null
  private boolean rectangular(Double[][] array) {
    int cols = array[0].length;
    for (int i = 0;  i < array.length; i++) {
      if (array[i].length != cols) {
        return false; // if any row is a different length than the first one, not all the same
      }
    }
    return true; // if none are different, all are the same -> rectangular
  }

  private boolean oddDimensions(Double[][] array) {
    if (array.length % 2 == 0) {
      return false; // check rows
    }
    for (int i = 0; i < array.length; i++) {
      if (array[i].length % 2 == 0) {
        return false; // check cols
      }
    }
    return true; // if neither have returned already, must be all odd
  }

  @Override
  public int getWidth() {
    int width = array[0].length;
    for (int i = 0; i < array.length; i++) {
      width = Math.min(array[i].length, width); // take shortest row-- avoid indexing errors later
    }
    return width; // number of columns
  }

  @Override
  public int getHeight() {
    return array.length; // number of rows
  }

  @Override
  public double get(int row, int col) throws IndexOutOfBoundsException {
    int centerRow = (this.getHeight() - 1) / 2; // height is always odd
    int centerCol = (this.getWidth() - 1) / 2; // width is always odd
    try {
      return array[centerRow + row][centerCol + col];
    } catch (IndexOutOfBoundsException e) {
      throw new IndexOutOfBoundsException("Index (" + row + ", " + col
              + ") is out of bounds for this kernel.");
    }
  }

  @Override
  public String toString() {
    return Arrays.deepToString(array);
  }
}
