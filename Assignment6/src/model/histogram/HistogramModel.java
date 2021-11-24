package model.histogram;

import java.util.ArrayList;

import model.Pixel;
import model.SimpleEditorModel;
import model.histogram.Histogram;

/**
 * Represents a model.histogram.HistogramModel.
 */
public class HistogramModel extends SimpleEditorModel implements Histogram {
  private final ArrayList<Integer> redFrequency;
  private final ArrayList<Integer> greenFrequency;
  private final ArrayList<Integer> blueFrequency;
  private final ArrayList<Integer> intensityFrequency;
  private Pixel[][] image;

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

  private boolean checkTransparentImage() {
    if (image == null) {
      return false;
    }
    int count0 = 0;
    int count255 = 0;
    for (int i = 0; i < this.image.length; i++) {
      for (int j = 0; j < this.image[i].length; j++) {
        Pixel checkRGB = this.image[i][j];
        if (checkRGB.getRed() == 0 && checkRGB.getBlue() == 0 && checkRGB.getGreen() == 0) {
          count0++;
        } else if (checkRGB.getRed() == 255 && checkRGB.getBlue() == 255
                && checkRGB.getGreen() == 255) {
          count255++;
        }
      }
    }
    return (!(count0 == this.image.length * this.image[0].length)) ||
            (!(count255 == this.image.length * this.image[0].length));
  }

  //have 3 tables for each RGB component & 1 table for the intensity component
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

  public ArrayList<Integer> getRedBar() {
    return this.redFrequency;
  }

  public ArrayList<Integer> getGreenBar() {
    return this.greenFrequency;
  }

  public ArrayList<Integer> getBlueBar() {
    return this.blueFrequency;
  }

  public ArrayList<Integer> getIntensityBar() {
    return this.intensityFrequency;
  }
}

//get the bars of the newly saved image for the different operations