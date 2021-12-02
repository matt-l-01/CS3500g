package controller.command;

import controller.ImageManager;
import model.FilterKernel;
import model.Kernel;

/**
 * Represents a command to apply a gaussian blur filter to an image.
 */
public class Sharpen extends AbstractImageCommand {
  private Kernel kernel;

  /**
   * Constructs a sharpen command
   * using the necessary filter array.
   * @param model model to sharpen
   */
  public Sharpen(ImageManager model) {
    super(model);
    Double[][] sharpenArray = {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1.0, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}
    };
    this.kernel = new FilterKernel(sharpenArray);
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.filter(this.kernel, inputName, resultName);
  }
}
