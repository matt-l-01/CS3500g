package controller.command;

import controller.ImageManager;

/**
 * Represents a command to flip an image horizontally.
 */
public class FlipHorizontal extends AbstractImageCommand {

  public FlipHorizontal(ImageManager model) {
    super(model);
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.flip("horizontal", inputName, resultName);
  }
}
