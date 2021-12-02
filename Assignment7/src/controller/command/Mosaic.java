package controller.command;

import controller.ImageManager;

/**
 * Description of class goes here.
 *
 * @author Matthew Love
 */
public class Mosaic extends AbstractImageCommand {
  public Mosaic(ImageManager model) throws IllegalArgumentException {
    super(model);
  }

  @Override
  public void execute(String inputName, String resultName) {
  }
}
