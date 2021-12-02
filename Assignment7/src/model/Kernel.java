package model;

/**
 * This interface represents the operations offered by a kernel used in an image filter.
 */
public interface Kernel {

  /**
   * Gets the width of this kernel in pixels.
   *
   * @return the width of the kernel
   */
  int getWidth();

  /**
   * Gets the height of this kernel in pixels.
   *
   * @return the height of the kernel
   */
  int getHeight();

  /**
   * Gets the value of the number at the specified position in this kernel.
   * The kernel is indexed such that the center is 0,0, columns to the left and
   * rows above are represented by negative indices, and columns to the right and
   * rows below are represented by positive indices.
   *
   * @param row the row to the desired value
   * @param col the column of the desired value
   * @return the value of the number at the specified position in the kernel
   * @throws IndexOutOfBoundsException if the specified position does not exist in this kernel
   */
  double get(int row, int col) throws IndexOutOfBoundsException;

  /**
   * Overriding toString to produce a string with the contents of this kernel.
   *
   * @return the contents of the kernel, as a string
   */
  @Override
  String toString();
}
