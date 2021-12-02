package controller.command;

import controller.ImageManager;
import model.ImageModel;

/**
 * Represents a command to visualize the red component of each pixel in an image.
 */
public class Red extends AbstractImageCommand {

  public Red(ImageManager model) {
    super(model);
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.visualizeComponent(ImageModel.Component.RED, inputName, resultName);
  }
}
