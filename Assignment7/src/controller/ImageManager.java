package controller;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import model.ColorMatrix;
import model.ImageModel;
import model.Kernel;
import controller.command.ImageCommand;

/**
 * Represents operations needed to load, save, and store a group of images.
 */
public interface ImageManager {

  /**
   * Load the image at the specified file path into this program and refer to it
   * by the given name.
   *
   * @param filePath the file path where the image is found
   * @param name the name to refer to the image as within the program
   * @throws IOException if an error occurs reading from the specified file path
   * @throws IllegalArgumentException if the provided path is null
   */
  void load(String filePath, String name, ImageFileFormat fileFormat)
          throws IOException, IllegalArgumentException;

  /**
   * Save an image to the specified file path with the specified name and file format.
   *
   * @param imageName the name of the image to be saved
   * @param filePath the file path to save the image to, including the file name
   * @param fileFormat the format to save the file as
   * @throws IOException if new file cannot be created
   * @throws IllegalArgumentException if any parameters are null
   */
  void save(String imageName, String filePath, ImageFileFormat fileFormat)
          throws IOException, IllegalArgumentException;

  /**
   * Get all images that the program has loaded or created.
   *
   * @return a copy of a map of all images that this program has stored
   */
  Map<String, ImageModel> getStoredImages();

  /**
   * Get all commands that the program can use to manipulate an image.
   *
   * @return a copy of a map of all known commands to manipulate images
   */
  Map<String, Function<String[], ImageCommand>> getKnownCommands();

  /**
   * Visualize the given component of the specified image,
   * and store the result under the given name.
   *
   * @param component the component of the image to be visualized
   * @param imageName the name of the image to be visualized
   * @param resultName the name to store the resulting image under
   * @throws IllegalArgumentException if the given component is null or the image is not found
   */
  void visualizeComponent(ImageModel.Component component, String imageName, String resultName)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Visualize the specified image flipped along the given direction,
   * and store the result under the given name.
   *
   * @param direction the direction to flip the image, eg horizontally or vertically
   * @param imageName the name of the image to be flipped
   * @param resultName the name to store the resulting image under
   * @throws IllegalArgumentException if the given direction is not horizontal or vertical
   *                                  or the image is not found
   */
  void flip(String direction, String imageName, String resultName)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Brighten or darken this image by the specified amount.
   *
   * @param increment the amount to brighten this image, where positive values brighten and
   *                  negative values darken
   * @param imageName the name of the image to be brightened
   * @param resultName the name to store the resulting image under
   * @throws IllegalArgumentException if the image is not found
   */
  void brighten(int increment, String imageName, String resultName) throws IllegalArgumentException;

  /**
   * Apply the filter expressed by the given kernel to the image with the specified name,
   * and store the result under the result name. Currently only supports filtering uniformly
   * across all color channels.
   *
   * @param kernel the kernel of the desired filtering operation
   * @param imageName the name of the image to be filtered
   * @param resultName the name to store the resulting image under
   * @throws IllegalArgumentException if the image is not found
   */
  void filter(Kernel kernel, String imageName, String resultName)
          throws IllegalArgumentException;

  /**
   * Apply the color transformation expressed by the provided matrix to the image with the given
   * name, and store the result under the given result name.
   * @param matrix the color coefficient matrix for the desired color transformation
   * @param imageName the name of the image to be transformed
   * @param resultName the name to store the resulting image under
   * @throws IllegalArgumentException if the image is not found
   */
  void colorTransform(ColorMatrix matrix, String imageName, String resultName)
          throws IllegalArgumentException;

  /**
   * Conducts a mosaic operation on the respective image with the given number of seeds and stores
   * the result under the given resultName.
   * @param seeds the number of seeds for the mosaic operation
   * @param imageName the name of the image to be operated on
   * @param resultName the name to store the resulting image under
   * @throws IllegalArgumentException if the image is not found
   */
  void mosaic(int seeds, String imageName, String resultName) throws IllegalArgumentException;

  /**
   * Enum used to represent the different
   * choices for possible file extensions.
   * This limits the amount of file formats
   * that this program can support saving as.
   */
  enum ImageFileFormat {

    BMP("bmp"), PNG("png"), JPEG("jpeg"), JPG("jpg"), PPM("ppm");

    ImageFileFormat(String extension) {
      this.extension = extension;
    }

    private String extension;

    public String toString() {
      return this.extension;
    }
  }
}
