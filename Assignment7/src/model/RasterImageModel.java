package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for an image loaded from
 * a PPM file. Contains the methods to
 * manipulate this image and save as another
 * image.
 *
 * @author Matt Stetter
 */
public class RasterImageModel implements ImageModel {

  /**
   * Field variables taken from the
   * loaded PPM file.
   * width: width of the file in pixels.
   * height: height of the file in pixels.
   * maxRGB: max integer value for RGB pixel color.
   * pixelGrid: 2d list of the pixels in the image.
   * fileFormat: the format of the image (see ImageModel.ImageFileFormat)
   */
  private final int width;
  private final int height;
  private int maxRGB;
  private final List<List<Pixel>> pixelGrid;

  /**
   * Constructor creates the model with the
   * input width, height, maximum RGB value, and
   * a 2d List of the pixels that make up the image.
   *
   * @param width width in pixels
   * @param height height in pixels
   * @param maxRGB max value for RGB color
   * @param pixelGrid grid of pixels
   * @throws IllegalArgumentException if any provided arguments
   *         are null
   */
  public RasterImageModel(int width, int height, int maxRGB, List<List<Pixel>> pixelGrid)
      throws IllegalArgumentException {
    if (width <= 0 || height <= 0 || maxRGB < 0) {
      throw new IllegalArgumentException(
              "Height, width, and max RGB values cannot be negative");
    }
    this.width = width;
    this.height = height;
    this.maxRGB = maxRGB;
    this.pixelGrid = pixelGrid;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getMaxRGB() {
    return this.maxRGB;
  }

  /**
   * Returns the pixel at the supplied
   * row and column position. Throws
   * IllegalArgumentException if the position
   * is invalid.
   *
   * @param row pixel row
   * @param col pixel column
   * @return Pixel at specified position
   * @throws IllegalArgumentException if the position
   *         is out of bounds of the image.
   */
  @Override
  public Pixel getPixel(int row, int col)
      throws IllegalArgumentException {
    if (this.pixelGrid == null) {
      throw new IllegalArgumentException("Pixel grid is null");
    } else if (row < 0 || row >= this.height
              || col < 0 || col >= this.width) {
      throw new IllegalArgumentException("Position out of bounds");
    } else {
      return this.pixelGrid.get(row).get(col);
    }
  }

  /**
   * Called to visualize a specific component of
   * an image and return the new version.
   *
   * @param component the component of the image to be visualized
   * @return new ImageModel with component visualized
   * @throws IllegalArgumentException if the component passed is
   *         invalid
   * @throws IllegalStateException if the image has not been initialized
   */
  @Override
  public ImageModel visualizeComponent(Component component)
          throws IllegalArgumentException, IllegalStateException {
    if (this.pixelGrid == null) {
      throw new IllegalStateException("Image to process cannot be null");
    } else if (component == null) {
      throw new IllegalArgumentException("Component cannot be null");
    }
    List<List<Pixel>> newPixelGrid = new ArrayList<>();
    for (int row = 0; row < this.getHeight(); row++) {
      newPixelGrid.add(new ArrayList<>());
      for (int col = 0; col < this.getWidth(); col++) {
        int setComponent;
        switch (component) {
          case RED:
            setComponent = this.getPixel(row, col).getRed();
            break;
          case GREEN:
            setComponent = this.getPixel(row, col).getGreen();
            break;
          case BLUE:
            setComponent = this.getPixel(row, col).getBlue();
            break;
          case VALUE:
            setComponent = this.getPixel(row, col).getValue();
            break;
          case INTENSITY:
            setComponent = this.getPixel(row, col).getIntensity();
            break;
          case LUMA:
            setComponent = this.getPixel(row, col).getLuma();
            break;
          default:
            throw new IllegalArgumentException("Error, impossible value");
        }
        Pixel newPixel = new ARGBPixel(
                this.getPixel(row, col).getAlpha(), setComponent, setComponent, setComponent);
        newPixelGrid.get(row).add(newPixel);
      }
    }
    return new RasterImageModel(this.getWidth(),
            this.getHeight(), this.getMaxRGB(), newPixelGrid);
  }

  private ImageModel flipHorizontal() {
    List<List<Pixel>> newPixelGrid = new ArrayList<>();
    for (int row = 0; row < this.getHeight(); row++) {
      newPixelGrid.add(new ArrayList<>());
      for (int col = this.getWidth() - 1; col >= 0; col--) {
        int newRed = this.getPixel(row, col).getRed();
        int newGreen = this.getPixel(row, col).getGreen();
        int newBlue = this.getPixel(row, col).getBlue();
        newPixelGrid.get(row).add(
                new ARGBPixel(this.getPixel(row, col).getAlpha(), newRed, newGreen, newBlue));
      }
    }
    return new RasterImageModel(this.getWidth(),
            this.getHeight(), this.getMaxRGB(), newPixelGrid);
  }

  private ImageModel flipVertical() {
    List<List<Pixel>> newPixelGrid = new ArrayList<>();
    for (int row = this.getHeight() - 1; row >= 0; row--) {
      newPixelGrid.add(new ArrayList<>());
      for (int col = 0; col < this.getWidth(); col++) {
        int newRed = this.getPixel(row, col).getRed();
        int newGreen = this.getPixel(row, col).getGreen();
        int newBlue = this.getPixel(row, col).getBlue();
        newPixelGrid.get(this.getHeight() - 1 - row).add(
                new ARGBPixel(this.getPixel(row, col).getAlpha(), newRed, newGreen, newBlue));
      }
    }
    return new RasterImageModel(this.getWidth(),
            this.getHeight(), this.getMaxRGB(), newPixelGrid);
  }

  /**
   * Returns an image flip along the
   * specified direction.
   *
   * @param direction the direction to flip the image, e.g. horizontally or vertically
   * @return new flipped image
   * @throws IllegalArgumentException if the direction passed is not
   *         an option
   * @throws IllegalStateException if the image has not been initialized
   */
  @Override
  public ImageModel flip(String direction)
          throws IllegalArgumentException, IllegalStateException {
    if (this.pixelGrid == null) {
      throw new IllegalStateException("Image to process cannot be null");
    }
    ImageModel newModel;
    switch (direction) {
      case "horizontal":
        newModel = this.flipHorizontal();
        break;
      case "vertical":
        newModel = this.flipVertical();
        break;
      default:
        throw new IllegalArgumentException("Invalid flip direction");
    }
    return newModel;
  }

  private int addColor(int increment, int color) {
    if (color + increment > 255) {
      return 255;
    } else {
      return Math.max(color + increment, 0);
    }
  }

  /**
   * Returns a brightened (or darkened) version
   * of the input image.
   *
   * @param increment the amount to brighten this image, where positive values brighten and
   *                  negative values darken
   * @return brightened/darkened image
   * @throws IllegalStateException if the image has not been initialized
   */
  @Override
  public ImageModel brighten(int increment) throws IllegalStateException {
    if (this.pixelGrid == null) {
      throw new IllegalStateException("Image to process cannot be null");
    }
    List<List<Pixel>> newPixelGrid = new ArrayList<>();
    for (int row = 0; row < this.getHeight(); row++) {
      newPixelGrid.add(new ArrayList<>());
      for (int col = 0; col < this.getWidth(); col++) {
        int newRed = addColor(increment, this.getPixel(row, col).getRed());
        int newGreen = addColor(increment, this.getPixel(row, col).getGreen());
        int newBlue = addColor(increment, this.getPixel(row, col).getBlue());
        this.maxRGB = Math.max(this.maxRGB, Math.max(newRed, Math.max(newGreen, newBlue)));
        newPixelGrid.get(row).add(
                new ARGBPixel(this.getPixel(row, col).getAlpha(), newRed, newGreen, newBlue));
      }
    }
    return new RasterImageModel(this.getWidth(),
            this.getHeight(), this.getMaxRGB(), newPixelGrid);
  }

  /**
   * Returns a filtered version of this image based on the provided kernel.
   * Currently, only supports filtering uniformly across all color channels.
   *
   * @param kernel the kernel of the desired filter
   * @return the filtered image
   * @throws IllegalStateException if the image has not been initialized
   * @throws IllegalArgumentException if the provided kernel is null
   */
  @Override
  public ImageModel filter(Kernel kernel) throws IllegalStateException, IllegalArgumentException {
    if (this.pixelGrid == null) {
      throw new IllegalStateException("Image to process cannot be null");
    }
    if (kernel == null) {
      throw new IllegalArgumentException("Kernel cannot be null.");
    }
    List<List<Pixel>> newPixelGrid = new ArrayList<>();
    for (int row = 0; row < this.getHeight(); row++) {
      newPixelGrid.add(new ArrayList<>());
      for (int col = 0; col < this.getWidth(); col++) {
        int newRed = applyKernel(kernel, Component.RED, row, col);
        int newGreen = applyKernel(kernel, Component.GREEN, row, col);
        int newBlue = applyKernel(kernel, Component.BLUE, row, col);
        // apply the kernel centered at the current pixel and return the sum as the channel value
        newPixelGrid.get(row).add(
                new ARGBPixel(255, newRed, newGreen, newBlue));
      }
    }
    return new RasterImageModel(this.getWidth(),
            this.getHeight(), this.getMaxRGB(), newPixelGrid);
  }

  // applies the kernel on the given channel centered at the pixel with the given row and column
  private int applyKernel(Kernel kernel, Component channel, int row, int col) {
    int kernelStartRow = -(kernel.getHeight() - 1) / 2; // starts from -1 for a 3-row kernel
    int kernelStartCol = -(kernel.getWidth() - 1) / 2;
    double sum = 0.0;
    for (int r = kernelStartRow; r <= -kernelStartRow; r++) { // start and end are symmetrical
      for (int c = kernelStartCol; c <= -kernelStartCol; c++) {
        try {
          Pixel pixel = this.pixelGrid.get(row + r).get(col + c);
          int channelVal = 0; // the value of the chosen channel of this pixel
          if (channel.equals(Component.RED)) {
            channelVal = pixel.getRed();
          } else if (channel.equals(Component.GREEN)) {
            channelVal = pixel.getGreen();
          } else if (channel.equals(Component.BLUE)) {
            channelVal = pixel.getBlue();
          }
          sum += kernel.get(r, c) * channelVal;
        } catch (IndexOutOfBoundsException ignored) { // if the kernel overlaps outside the image
        } // do not apply the kernel there, ie sum does not increase
      }
    }
    return (int) Math.max(0, Math.min(Math.round(sum), 255)); // round to integer, clamp 0 to 255
  }

  /**
   * Returns a color transformed version of the image according to the given
   * color coefficient matrix.
   *
   * @param matrix the matrix of color coefficients for the desired transformation
   * @return the color transformed image
   * @throws IllegalStateException if the image has not been initialized
   * @throws IllegalArgumentException if the matrix is null
   */
  @Override
  public ImageModel colorTransform(ColorMatrix matrix) throws IllegalStateException {
    if (this.pixelGrid == null) {
      throw new IllegalStateException("Image to process cannot be null");
    }
    if (matrix == null) {
      throw new IllegalArgumentException("Transformation matrix cannot be null.");
    }
    List<List<Pixel>> newPixelGrid = new ArrayList<>();
    for (int row = 0; row < this.getHeight(); row++) {
      newPixelGrid.add(new ArrayList<>());
      for (int col = 0; col < this.getWidth(); col++) {
        int newRed = applyCoefficients(matrix.redCoefficients(), this.getPixel(row, col));
        int newGreen = applyCoefficients(matrix.greenCoefficients(), this.getPixel(row, col));
        int newBlue = applyCoefficients(matrix.blueCoefficients(), this.getPixel(row, col));
        newPixelGrid.get(row).add(
                new ARGBPixel(this.getPixel(row, col).getAlpha(), newRed, newGreen, newBlue));
      }
    }
    return new RasterImageModel(this.getWidth(),
            this.getHeight(), this.getMaxRGB(), newPixelGrid);
  }

  // applies an array of coefficients to a pixel's corresponding RGB values and returns the sum
  private int applyCoefficients(Double[] coeff, Pixel pixel) {
    return (int) Math.max(0, Math.min(Math.round(
            coeff[0] * pixel.getRed()
            + coeff[1] * pixel.getGreen()
            + coeff[2] * pixel.getBlue()), 255)); // round and clamp 0 to 255
  }

  /**
   * Overrides the toString method for
   * the image model. Returns essentially
   * the contents of the file.
   *
   * @return string representation
   */
  @Override
  public String toPPMFile() {
    StringBuilder outputText = new StringBuilder();
    outputText.append(String.format("P3%s%d %d%s%d%s", System.lineSeparator(),
            this.getWidth(), this.getHeight(), System.lineSeparator(), this.getMaxRGB(),
            System.lineSeparator()));
    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        String pixelValues = String.format("%d\n%d\n%d",
                this.getPixel(row, col).getRed(),
                this.getPixel(row, col).getGreen(),
                this.getPixel(row, col).getBlue());
        if (row != this.getHeight() - 1 || col != this.getWidth() - 1) {
          pixelValues += '\n';
        }
        outputText.append(pixelValues);
      }
    }
    return outputText.toString();
  }
}
