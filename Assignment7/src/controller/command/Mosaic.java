package controller.command;

import controller.ImageManager;

/**
 * Description of class goes here.
 *
 * @author Matthew Love
 */
public class Mosaic extends AbstractImageCommand {
  int seeds;
  public Mosaic(String[] args, ImageManager model) throws IllegalArgumentException {
    super(model);
    if (args == null) {
      throw new IllegalArgumentException("Arguments may not be null.");
    }

    for (String arg : args) {
      try {
        this.seeds = Integer.parseInt(arg);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Must specify an amount of seeds.");
      }
    }
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.mosaic(this.seeds, inputName, resultName);
  }
}
