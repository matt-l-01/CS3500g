package model.histogram;

import java.util.ArrayList;
import java.util.List;

import model.Pixel;

public interface Histogram {

  void fillFrequencies(Pixel[][] image);

  ArrayList<Integer> getRedBar();

  ArrayList<Integer> getGreenBar();

  ArrayList<Integer> getBlueBar();


  ArrayList<Integer> getIntensityBar();
}
