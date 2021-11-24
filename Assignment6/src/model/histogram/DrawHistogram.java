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
  private HistogramModel redFrequency;
  private HistogramModel greenFrequency;
  private HistogramModel blueFrequency;
  private HistogramModel intensityFrequency;

  private final int width = 450;
  private final int height = 450;
  private final int padding = 25;
  private final int labelPadding = 25;

  private static final Stroke graph_stroke = new BasicStroke(2f);
  private final int pointWidth = 2;
  private final int amountYDivisions = 10;

  public DrawHistogram(Histogram hm) {
    super();
    this.hm = hm;
  }

//  private ArrayList<Rectangle> createBar(ArrayList<Integer> frequency) {
//    ArrayList<Rectangle> rect = new ArrayList<Rectangle>();
//    for (int x = 0; x < frequency.size(); x++) {
//      rect.add(new Rectangle(10, frequency.get(x)));
//    }
//    return rect;
//  }


  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g1 = (Graphics2D) g;
    g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    //make background
    g.setColor(Color.RED);
    g.fillRect(labelPadding + padding, padding, getWidth() - (2 * padding) - labelPadding,
                getHeight() - (2 * padding) - labelPadding);

    //hash marks & grid lines y-axis
    for (int i = 0; i < amountYDivisions + 1; i++) {
      int x0 = padding + labelPadding;
      int x1 = pointWidth + x0;
      int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) /
              amountYDivisions + padding + labelPadding);
      int y1 = y0;
    }

    //hash marks & grid lines x-axis


    //3 for-loops draw the points
    //3 for-loops that draws the lines

    //go through array list
    //at each index and index+1, plot two points and draw a line between them
    //make it in terms of coordinate values

    //redFrequency lines
    int x_valueR = 0;
    g.setColor(Color.RED);
    //hm.fillFrequencies();
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

  public void changeImage(Pixel[][] imageModel) {
    invalidate();
    this.repaint();


  }
}



