package model;

/**
 * Represents an image editor. Allows for multiple different types of operations to be done on
 * images within the editor. Can store multiple image representations in memory as they are loaded
 * in.
 */
public interface ImageEditorModel extends ImageEditorState {
  /**
   * Loads the image from the given path. Obtains the internal information about the file, such as
   * the pixels, and stores this in memory as the given name.
   * @param path the path at which the file is loaded from.
   * @param name the name the file is being stored as in memory.
   * @throws IllegalArgumentException if either of the parameters are null.
   * @throws IllegalStateException if the file is not found by the reader.
   */
  void loadImage(String path, String name) throws IllegalArgumentException, IllegalStateException;

  /**
   * Saves an image stored in memory to the given path. Takes in the path and image name as
   * strings and saves the file.
   * @param path the path where the file is being saved.
   * @param name the name of the image stored in memory to save.
   * @throws IllegalArgumentException if any of the parameters are null.
   * @throws IllegalStateException if the program cannot properly write to the path.
   */
  void save(String path, String name) throws IllegalArgumentException, IllegalStateException;

  /**
   * Uses the given component type to convert the given image to greyscale. Then, stores the
   * resulting image in memory with the new name.
   * @param type the component type, enum, which can be used to convert the image to greyscale.
   * @param fromImageName the image to conduct this operation on, stored in memory.
   * @param toImageName the new image name to save the resulting image to in memory.
   * @throws IllegalArgumentException if any of the parameters are null.
   * @throws IllegalStateException if the given image name cannot be found in memory.
   */
  void component(Component type, String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Conducts a horizontal flip on the given image in memory, and saves the resulting image to
   * the new name in memory.
   * @param fromImageName the image to conduct this operation on, stored in memory.
   * @param toImageName the new image name to save the resulting image to in memory.
   * @throws IllegalArgumentException if any of the parameters are null.
   * @throws IllegalStateException if the given image name cannot be found in memory.
   */
  void flipHorizontal(String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Conducts a verticle flip on the given image in memory, and saves the resulting image to
   * the new name in memory.
   * @param fromImageName the image to conduct this operation on, stored in memory.
   * @param toImageName the new image name to save the resulting image to in memory.
   * @throws IllegalArgumentException if any of the parameters are null.
   * @throws IllegalStateException if the given image name cannot be found in memory.
   */
  void flipVertical(String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Brightens the image by the given value to the image stored in memory, and then saves the
   * resulting image to the name in memory. If the value is negative, the image will be darkened.
   * @param value the value by which to brighten the image, either positive or negative.
   * @param fromImageName the image to conduct this operation on, stored in memory.
   * @param toImageName the new image name to save the resulting image to in memory.
   * @throws IllegalArgumentException if any of the parameters are null.
   * @throws IllegalStateException if the given image name cannot be found in memory.
   */
  void brighten(int value, String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Blurs the image and saves as the new name.
   * @param fromImageName the image to conduct this operation on, stored in memory.
   * @param toImageName the new image name to save the resulting image to in memory.
   */
  void blur(String fromImageName, String toImageName);

  /**
   * Sharpens the image and saves as the new name.
   * @param fromImageName the image to conduct this operation on, stored in memory.
   * @param toImageName the new image name to save the resulting image to in memory.
   */
  void sharpen(String fromImageName, String toImageName);
}
