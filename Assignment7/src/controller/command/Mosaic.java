package controller.command;

import controller.ImageManager;

/**
 * Represents a command to conduct a mosaic operation on an image.
 */
public class Mosaic extends AbstractImageCommand {
  int seeds;

  /**
   * Constructs a mosaic command to conduct mosaic operations on the given images.
   * @param args additional arguments for the command provided here
   * @param model the model for the image provided here
   * @throws IllegalArgumentException if the arguments are null, or if the provided string cannot
   *                                  be parsed to an integer
   */
  public Mosaic(String[] args, ImageManager model) throws IllegalArgumentException {
    super(model);
    if (args == null) {
      throw new IllegalArgumentException("Arguments may not be null.");
    }

    for (String arg : args) {
      try {
        this.seeds = Integer.parseInt(arg);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Must specify an amount of seeds.");
      }
    }
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.mosaic(this.seeds, inputName, resultName);
  }
}
