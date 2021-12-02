package controller.command;

import controller.ImageManager;
import model.ImageModel;

/**
 * Represents a command to visualize the value of each pixel in an image.
 */
public class Value extends AbstractImageCommand {

  public Value(ImageManager model) {
    super(model);
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.visualizeComponent(ImageModel.Component.VALUE, inputName, resultName);
  }

}
