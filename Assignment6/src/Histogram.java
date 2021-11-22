import java.util.ArrayList;

import model.Pixel;

public interface Histogram {

  void fillFrequencies(Pixel[][] image);

  ArrayList<Integer> getRedBar();

  ArrayList<Integer> getGreenBar();

  ArrayList<Integer> getBlueBar();


}
