package controller.command;

import controller.ImageManager;

/**
 * Represents a command to flip an image vertically.
 */
public class FlipVertical extends AbstractImageCommand {

  public FlipVertical(ImageManager model) {
    super(model);
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.flip("vertical", inputName, resultName);
  }
}
