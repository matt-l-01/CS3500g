package model.histogram;

import java.util.ArrayList;

import model.Pixel;

/**
 * Represents a model for the histogram representation. Allows the image to be shown as components
 * on the histogram with red, green, blue, and intensity values shown on the screen.
 */
public class HistogramModel implements Histogram {
  private final ArrayList<Integer> redFrequency;
  private final ArrayList<Integer> greenFrequency;
  private final ArrayList<Integer> blueFrequency;
  private final ArrayList<Integer> intensityFrequency;
  private Pixel[][] image;

  /**
   * Constructs a Histogram model to represent the different components of an image on a histogram
   * with each red, green, blue, and intensity value being shown.
   */
  public HistogramModel() {
    this.redFrequency = new ArrayList<>();
    this.greenFrequency = new ArrayList<>();
    this.blueFrequency = new ArrayList<>();
    this.intensityFrequency = new ArrayList<>();
    this.image = null;

    for (int i = 0; i < 256; i++) {
      redFrequency.add(0);
      greenFrequency.add(0);
      blueFrequency.add(0);
      intensityFrequency.add(0);
    }
  }

  @Override
  public void setImage(Pixel[][] image) {
    this.image = image;
    this.fillFrequencies();
  }

  @Override
  public void fillFrequencies() {
    if (image == null) {
      return;
    }

    for (int i = 0; i < this.image.length; i++) {
      for (int j = 0; j < this.image[i].length; j++) {
        Pixel p = this.image[i][j];
        this.redFrequency.set(p.getRed(), this.redFrequency.get(p.getRed()) + 1);
        this.greenFrequency.set(p.getGreen(), this.greenFrequency.get(p.getGreen()) + 1);
        this.blueFrequency.set(p.getBlue(), this.blueFrequency.get(p.getBlue()) + 1);
      }
    }
  }
  @Override
  public ArrayList<Integer> getRedBar() {
    return this.redFrequency;
  }

  @Override
  public ArrayList<Integer> getGreenBar() {
    return this.greenFrequency;
  }

  @Override
  public ArrayList<Integer> getBlueBar() {
    return this.blueFrequency;
  }

  @Override
  public ArrayList<Integer> getIntensityBar() {
    return this.intensityFrequency;
  }
}