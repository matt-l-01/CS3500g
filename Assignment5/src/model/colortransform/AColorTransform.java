package model.colortransform;

import model.Pixel;

/**
 * Represents an abstract color transformation function object to transform an image's color.
 */
public abstract class AColorTransform implements ColorTransform {
  float[][] matrix;
  Pixel[][] image;

  /**
   * Constructs a new abstract color transformation function object.
   * @param matrix the specific matrix values for this color transformation.
   * @param image the array of Pixels to be operated on.
   */
  protected AColorTransform(float[][] matrix, Pixel[][] image) {
    this.matrix = matrix;
    this.image = image.clone();
  }

  /**
   * Transforms the image using the given coloring filter values.
   * @return the new array of Pixels representing the image.
   */
  public Pixel[][] transform() {
    for (int row = 0; row < this.image.length; row++) {
      for (int col = 0; col < this.image[row].length; col++) {
        float r = this.image[row][col].getRed();
        float g = this.image[row][col].getGreen();
        float b = this.image[row][col].getBlue();

        r = (this.matrix[0][0] * r) + (this.matrix[0][1] * g) + (this.matrix[0][2] * b);
        g = (this.matrix[1][0] * r) + (this.matrix[1][1] * g) + (this.matrix[1][2] * b);
        b = (this.matrix[2][0] * r) + (this.matrix[2][1] * g) + (this.matrix[2][2] * b);

        this.image[row][col] = new Pixel(this.colorCap(Math.round(r)),
            this.colorCap(Math.round(g)),
            this.colorCap(Math.round(b)));
      }
    }
    return this.image;
  }

  private int colorCap(int val) {
    if (val < 0) {
      return 0;
    }
    if (val > 255) {
      return 255;
    }
    return val;
  }
}
