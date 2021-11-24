package model.histogram;

import java.util.ArrayList;
import java.util.List;

import model.Pixel;

public interface Histogram {

  void setImage(Pixel[][] image);

  ArrayList<Integer> getRedBar();

  ArrayList<Integer> getGreenBar();

  ArrayList<Integer> getBlueBar();
  void fillFrequencies();

  ArrayList<Integer> getIntensityBar();
}
