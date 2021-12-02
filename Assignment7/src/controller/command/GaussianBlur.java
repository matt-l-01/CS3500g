package controller.command;

import controller.ImageManager;
import model.FilterKernel;
import model.Kernel;

/**
 * Represents a command to apply a gaussian blur filter to an image.
 */
public class GaussianBlur extends AbstractImageCommand {
  private Kernel kernel;

  /**
   * Constructs a blur command with the
   * necessary filter array.
   * @param model model to perform blur on
   */
  public GaussianBlur(ImageManager model) {
    super(model);
    Double[][] gaussianArray = {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}
    };
    this.kernel = new FilterKernel(gaussianArray);
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.filter(this.kernel, inputName, resultName);
  }
}
