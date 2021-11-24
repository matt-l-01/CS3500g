package model.histogram;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.*;

import model.Pixel;

//don't need to test
//make it output a bufferedImage

public class DrawHistogram extends JPanel implements HistogramView {
  private final Histogram hm;

  private final ArrayList<Color> lineColor = new ArrayList<>();
  private final ArrayList<Color> pointColor = new ArrayList<>();

  public DrawHistogram(Histogram hm) {
    super();

    this.hm = hm;
    pointColor.add(Color.DARK_GRAY);
    pointColor.add(Color.DARK_GRAY);
    pointColor.add(Color.DARK_GRAY);
    pointColor.add(Color.DARK_GRAY);

    lineColor.add(Color.RED);
    lineColor.add(Color.GREEN);
    lineColor.add(Color.BLUE);
    lineColor.add(Color.BLACK);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g1 = (Graphics2D) g;
    g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    //redFrequency lines
    int x_valueR = 0;
    g.setColor(Color.RED);
    java.util.List<Integer> red = this.hm.getRedBar();
    for (Integer y_valueR : red) {
      g.fillRect(x_valueR, 350 - y_valueR, 2, 2);
      x_valueR++;
    }

    //greenFrequency lines
    int x_valueG = 0;
    g.setColor(Color.GREEN);
    java.util.List<Integer> green = this.hm.getGreenBar();
    for (Integer y_valueG : green) {
      g.fillRect(x_valueG, 350 - y_valueG, 2, 2);
      x_valueG++;
    }

    //blueFrequency lines
    int x_valueB = 0;
    g.setColor(Color.blue);
    java.util.List<Integer> blue = this.hm.getBlueBar();
    for (Integer y_valueB : blue) {
      g.fillRect(x_valueB, 350 - y_valueB, 2, 2);
      x_valueB++;
    }

    //intensity component lines
    int x_valueI = 0;
    g.setColor(Color.black);
    java.util.List<Integer> intensity = this.hm.getIntensityBar();
    for (Integer y_valueI : intensity) {
      g.fillRect(x_valueI, 350 - y_valueI, 2, 2);
      x_valueI++;
    }
  }
}



