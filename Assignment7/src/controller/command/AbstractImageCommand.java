package controller.command;

import controller.ImageManager;

/**
 * Holds abstracted code for image manipulation command objects.
 */
public abstract class AbstractImageCommand implements ImageCommand {
  protected ImageManager model;

  protected AbstractImageCommand(ImageManager model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    this.model = model;
  }

}
