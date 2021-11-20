package controller.gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import model.ImageEditorModel;
import model.Pixel;

/**
 * Description of class goes here.
 *
 * @author Matthew Love
 */
public class EditorCanvas extends Canvas {
  private final ImageEditorModel model;
  private Pixel[][] image;

  public EditorCanvas(ImageEditorModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model must not be null");
    }
    this.model = model;
    this.image = null;
  }
  @Override
  public void paint(Graphics g) {
    if (this.image == null) {
      return;
    }
    BufferedImage img = new BufferedImage(this.image[0].length, this.image.length, 1);

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        Pixel p = image[i][j];
        Color c = new Color(p.getRed(), p.getGreen(), p.getBlue());
        img.setRGB(j, i, c.getRGB());
        System.out.println("Drawing " + i);
      }
    }
    g.drawImage(img, 0,0,this);
    System.out.println("Drawn");
  }

  public void drawImage(String name) {
    this.image = this.model.releaseImage(name);
    this.repaint();
  }
}
