package controller;

import java.io.IOException;

/**
 * This class holds abstracted code common to both synchronous
 * and asynchronous image processor controllers.
 */
public abstract class AbstractImageController {
  protected ImageManager manager;

  /**
   * Creates a controller that handles images using the given image manager.
   *
   * @param manager the image manager to use
   * @throws IllegalArgumentException if the given image manager is null
   */
  protected AbstractImageController(ImageManager manager) throws IllegalArgumentException {
    if (manager == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    this.manager = manager;
  }

  /**
   * Parses a file path to determine which image file format it uses.
   *
   * @param filePath the file path to be parsed
   * @return the file format used by the specified file path
   * @throws IOException if the file format is invalid or missing
   */
  protected ImageManager.ImageFileFormat getFileFormat(String filePath) throws IOException {
    String[] splitPath = filePath.split("\\.");
    String extension;
    if (splitPath.length == 2) {
      extension = splitPath[1];
    } else {
      throw new IOException("Invalid file name (no extension)");
    }
    switch (extension) {
      case "bmp":
        return ImageManager.ImageFileFormat.BMP;
      case "png":
        return ImageManager.ImageFileFormat.PNG;
      case "jpeg":
        return ImageManager.ImageFileFormat.JPEG;
      case "jpg":
        return ImageManager.ImageFileFormat.JPG;
      case "ppm":
        return ImageManager.ImageFileFormat.PPM;
      default:
        throw new IOException("Invalid file extension");
    }
  }
}
