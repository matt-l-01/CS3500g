package controller.command;

import model.ColorMatrix;
import controller.ImageManager;
import model.RGBColorMatrix;

/**
 * Represents a command to apply a sepia color transformation to an image.
 */
public class Sepia extends AbstractImageCommand {
  private ColorMatrix matrix;

  /**
   * Constructs the sepia command
   * with the necessary filter array.
   * @param model model to perform sepia on
   */
  public Sepia(ImageManager model) {
    super(model);
    Double[][] sepiaMatrix = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
    this.matrix = new RGBColorMatrix(sepiaMatrix);
  }

  @Override
  public void execute(String inputName, String resultName) {
    model.colorTransform(this.matrix, inputName, resultName);
  }
}
