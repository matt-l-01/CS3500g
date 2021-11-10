package model.filters;

import model.Pixel;

/**
 * Represents an abstract filter object to convert the RGB values of an image.
 */
public abstract class AFilter implements Filter {
  float[][] kernel;
  int kWidth;
  int kHeight;
  Pixel[][] image;

  protected AFilter(float[][] kernel, Pixel[][] image) throws IllegalArgumentException {
    if (kernel == null || image == null) {
      throw new IllegalArgumentException("Arguments of filter must not be null");
    }

    this.kernel = kernel;
    kWidth = kernel[0].length;
    kHeight = kernel.length;
    this.image = image.clone();
  }

  /**
   * Goes through each Pixel of an image and applies a given filter to change the RGB value.
   * @return a new image with converted pixels
   */
  public Pixel[][] filter() {
    // Goes through each Pixel from the image
//    for (int row = kHeight / 2; row < this.image.length - kHeight / 2; row++) {
//      for (int col = kWidth / 2; col < this.image[row].length - kWidth / 2; col++) {
//        int r = 0;
//        int g = 0;
//        int b = 0;
//
//        // Goes through the Kernel
//        for (int i = 0; i < kHeight; i++) {
//          for (int j = 0; j < kWidth; j++) {
//            int row_index = row + i - kHeight / 2;
//            int col_index = col + j - kWidth / 2;
//
//            r += this.image[row_index][col_index].getRed() * this.kernel[i][j];
//            g += this.image[row_index][col_index].getGreen() * this.kernel[i][j];
//            b += this.image[row_index][col_index].getBlue() * this.kernel[i][j];
//          }
//        }
//        this.image[row][col] = new Pixel(this.colorCap(r), this.colorCap(g), this.colorCap(b));
//      }
//    }

    for (int row = 0; row < this.image.length; row++) {
      for (int col = 0; col < this.image[row].length; col++) {
        int r = 0;
        int g = 0;
        int b = 0;

        for (int i = 0; i < this.kHeight; i++) {
          for (int j = 0; j < this.kWidth; j++) {
            int newRow = (kHeight / 2) + i + row;
            int newCol = (kWidth / 2) + j + col;

            if (this.outOfBounds(newRow, newCol)) {
              continue;
            }

            r += this.kernel[i][j] * this.image[newRow][newCol].getRed();
            g += this.kernel[i][j] * this.image[newRow][newCol].getGreen();
            b += this.kernel[i][j] * this.image[newRow][newCol].getBlue();
          }
        }
        this.image[row][col] = new Pixel(this.colorCap(r), this.colorCap(g), this.colorCap(b));
      }
    }

    return this.image;
  }

//  private boolean outOfBounds(int row, int col) {
//    return row + (kHeight / 2) > this.image.length - 1
//        || row - (kHeight / 2) < 0
//        || col - (kWidth / 2) < 0
//        || col + (kWidth / 2) > this.image[row].length - 1;
//  }

  private boolean outOfBounds(int row, int col) {
    return row > this.image.length - 1|| col > this.image[row].length - 1;
  }

  private int colorCap(int val) {
    if (val < 0) {
      return 0;
    }
    return Math.min(val, 255);
  }
}