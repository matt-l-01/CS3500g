package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import model.Pixel;
import model.RGBPixel;

/**
 * Class for a histogram panel
 * used in the GUIImageProcessorView
 * class.
 */
public class HistogramPanel extends JPanel {
  private Image image;

  public HistogramPanel(Image image) {
    super();
    this.image = image;
  }

  /**
   * Calculates the number of pixels that have each value for each component and returns them
   * in a 4 by 256 2d array. The first inner array is frequencies for the red component,
   * the second is green, the third is blue, and the fourth is intensity.
   */
  private int[][] getFrequencies() {
    int[][] frequencies = new int[4][256];
    int imData;
    Pixel curPixel;
    int r;
    int g;
    int b;
    for (int row = 0; row < this.image.getHeight(new JPanel()); row++) {
      for (int col = 0; col < this.image.getWidth(new JPanel()); col++) {
        imData = ((BufferedImage)this.image).getRGB(col, row);
        r = (imData >> 16) & 0xFF;
        g = (imData >> 8) & 0xFF;
        b = imData & 0xFF;
        curPixel = new RGBPixel(r, g, b);
        frequencies[0][r] += 1;
        frequencies[1][g] += 1;
        frequencies[2][b] += 1;
        frequencies[3][curPixel.getIntensity()] += 1;
      }
    }
    return frequencies;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (image.getWidth(this) == 1 && image.getHeight(this) == 1) {
      return; // don't draw the histogram for the initial dummy image
    }

    int[] vals = new int[256];
    for (int i = 0; i < 256; i++) {
      vals[i] = i;
    }


    int[][] frequencies = getFrequencies();

    int max = frequencies[0][0];
    for (int[] comp : frequencies) {
      for (int i = 0; i < comp.length; i++) {
        max = Math.max(max, comp[i]);
      }
    }
    max += 20; // add some padding
    double scaleFactor = 200.0 / max; // have to scale the resulting graph to fit in the panel

    int[] redFrequencies = new int[256];
    for (int i = 0; i < 256; i++) {
      redFrequencies[i] = (int) Math.round(200 - scaleFactor * frequencies[0][i]);
      // subtract from max so that graph appears right-side up
    }
    int[] greenFrequencies = new int[256];
    for (int i = 0; i < 256; i++) {
      greenFrequencies[i] = (int) Math.round(200 - scaleFactor * frequencies[1][i]);
    }
    int[] blueFrequencies = new int[256];
    for (int i = 0; i < 256; i++) {
      blueFrequencies[i] = (int) Math.round(200 - scaleFactor * frequencies[2][i]);
    }
    int[] intensityFrequencies = new int[256];
    for (int i = 0; i < 256; i++) {
      intensityFrequencies[i] = (int) Math.round(200 - scaleFactor * frequencies[3][i]);
    }

    g.setColor(Color.RED);
    g.drawPolyline(vals, redFrequencies, 256);
    g.setColor(Color.GREEN);
    g.drawPolyline(vals, greenFrequencies, 256);
    g.setColor(Color.BLUE);
    g.drawPolyline(vals, blueFrequencies, 256);
    g.setColor(Color.BLACK);
    g.drawPolyline(vals, intensityFrequencies, 256);
  }
}
