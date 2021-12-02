package controller.command;

import controller.ImageManager;
import model.ColorMatrix;
import model.RGBColorMatrix;

/**
 * Represents a command to apply a greyscale color transformation to an image.
 */
public class Greyscale extends AbstractImageCommand {
  private ColorMatrix matrix;

  /**
   * Constructs the greyscale command
   * with the necessary filter array.
   * @param model model to perform greyscale on
   */
  public Greyscale(ImageManager model) {
    super(model);
    Double[][] greyscaleMatrix = {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };
    this.matrix = new RGBColorMatrix(greyscaleMatrix);
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.colorTransform(this.matrix, inputName, resultName);
  }
}
