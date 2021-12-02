package controller.command;

import controller.ImageManager;
import model.ImageModel;

/**
 * Represents a command to visualize the green component of each pixel in an image.
 */
public class Green extends AbstractImageCommand {

  public Green(ImageManager model) {
    super(model);
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.visualizeComponent(ImageModel.Component.GREEN, inputName, resultName);
  }
}
