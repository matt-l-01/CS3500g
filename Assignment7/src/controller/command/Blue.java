package controller.command;

import model.ImageModel;
import controller.ImageManager;

/**
 * Represents a command to visualize the blue component of each pixel in an image.
 */
public class Blue extends AbstractImageCommand {

  public Blue(ImageManager model) {
    super(model);
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.visualizeComponent(ImageModel.Component.BLUE, inputName, resultName);
  }
}
