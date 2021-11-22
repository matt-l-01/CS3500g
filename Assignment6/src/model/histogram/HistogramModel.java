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

  public HistogramModel() {
    this.redFrequency = new ArrayList<>();
    this.greenFrequency = new ArrayList<>();
    this.blueFrequency = new ArrayList<>();

    for (int i = 0; i < 256; i++) {
      redFrequency.add(0);
      greenFrequency.add(0);
      blueFrequency.add(0);
    }
  }

  //don't keep here, put into util class
  public boolean checkTransparentImage(Pixel[][] imageModel) {
    //if the image is not a png then go to fillFrequencies
    //go through all the pixels of an image and see if each pixel has an RGB equal to (0,0,0)
    int count0 = 0;
    int count255 = 0;
    for (int i = 0; i < imageModel.length; i++) {
      for (int j = 0; j < imageModel[i].length; j++) {
        Pixel checkRGB = imageModel[i][j];
        if (checkRGB.clone().equals(new Pixel(0, 0, 0))) {
          count0++;
        } else if (checkRGB.clone().equals(new Pixel(255, 255, 255))) {
          count255++;
        }
      }
    }
    //checks if image is black or white
    return ((count0 == imageModel.length * imageModel[0].length)) ||
            (!(count255 == imageModel.length * imageModel[0].length));
  }


  //have 3 tables for each RGB component
  //have another table for the average of all components
  //create 4 different histograms
  //include intensity table which is average of all components in RGB value
  public void fillFrequencies(Pixel[][] imageModel) {
    for (int i = 0; i < imageModel.length; i++) {
      for (int j = 0; j < imageModel[i].length; j++) {
        Pixel p = imageModel[i][j];
        int resultR = p.getRed();
        int resultG = p.getGreen();
        int resultB = p.getRed();
        redFrequency.set(resultR, redFrequency.get(resultR) + 1);
        greenFrequency.set(resultG, greenFrequency.get(resultG) + 1);
        blueFrequency.set(resultB, blueFrequency.get(resultB) + 1);
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
}




//get the bars of the newly saved image for the different operations