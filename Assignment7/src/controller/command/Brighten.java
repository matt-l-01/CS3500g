package controller.command;

import controller.ImageManager;

/**
 * Represents a command to brighten each pixel in an image by a specific increment.
 */
public class Brighten extends AbstractImageCommand {
  private int increment;

  /**
   * Constructs a function object for the brighten command that brightens
   * by the given amount if positive or darkens if negative.
   * @param args additional arguments needed to execute this command,
   *             in this case containing the increment to brighten the image by.
   * @param model the model containing the image to be brightened
   * @throws IllegalArgumentException if the additional arguments are null or
   *                                  do not include an increment to brighten by
   */
  public Brighten(String[] args, ImageManager model) throws IllegalArgumentException {
    super(model);
    boolean incrementFound = false;
    if (args == null) {
      throw new IllegalArgumentException("Arguments may not be null.");
    }
    for (String arg : args) {
      try {
        this.increment = Integer.parseInt(arg);
        incrementFound = true;
        return;
      } catch (NumberFormatException ignored) {
      }
    }

    if (!incrementFound) {
      throw new IllegalArgumentException("Must specify an integer increment to brighten by.");
    }
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.brighten(this.increment, inputName, resultName);
  }
}
