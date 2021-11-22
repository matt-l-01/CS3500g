package model.histogram;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

//don't need to test
public class DrawHistogram extends JPanel implements HistogramView {

  private ArrayList<Rectangle> createBar(ArrayList<Integer> frequency) {
    ArrayList<Rectangle> rect = new ArrayList<Rectangle>();
    for (int x = 0; x < frequency.size(); x++) {
      rect.add(new Rectangle(10, frequency.get(x)));
    }
    return rect;
  }


  @Override
  public void draw(Graphics g) {

  }
}
