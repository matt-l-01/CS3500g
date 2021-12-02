package controller.command;

import controller.ImageManager;
import model.ImageModel;

/**
 * Represents a command to visualize the intensity of each pixel in an image.
 */
public class Intensity extends AbstractImageCommand {

  public Intensity(ImageManager model) {
    super(model);
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.visualizeComponent(ImageModel.Component.INTENSITY, inputName, resultName);
  }

}
