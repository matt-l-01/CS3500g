package controller.command;

import model.ImageModel;
import controller.ImageManager;

/**
 * Represents a command to visualize the luma of each pixel in an image.
 */
public class Luma extends AbstractImageCommand {

  public Luma(ImageManager model) {
    super(model);
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.visualizeComponent(ImageModel.Component.LUMA, inputName, resultName);
  }
}
