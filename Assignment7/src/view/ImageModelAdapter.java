package view;

import java.awt.image.BufferedImage;

import model.ImageModel;
import model.Pixel;

/**
 * This class converts an ImageModel into a form that can be used like a BufferedImage
 * for use in view methods that require objects of type Image.
 */
public class ImageModelAdapter extends BufferedImage {

  /**
   * Constructor converts the image to a Buffered
   * Image by parsing through the ImageModel pixel
   * data and creating a new BufferedImage.
   * @param model the model to convert
   */
  public ImageModelAdapter(ImageModel model) {
    super(model.getWidth(), model.getHeight(), BufferedImage.TYPE_INT_RGB);
    int mask = 0xFFFFFFFF;
    Pixel pixel;
    int color;
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        pixel = model.getPixel(row, col);
        color = ((pixel.getAlpha() << 24) & mask)
                + ((pixel.getRed() << 16) & mask)
                + ((pixel.getGreen() << 8) & mask)
                + (pixel.getBlue() & mask);
        this.setRGB(col, row, color);
      }
    }
  }
}
