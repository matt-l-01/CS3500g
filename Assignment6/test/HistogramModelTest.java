import org.junit.Test;

import model.Pixel;
import model.histogram.HistogramModel;

import static org.junit.Assert.*;

public class HistogramModelTest {

  @Test
  public void checkTransparentImage1() {
    Pixel[][] p1 = new Pixel[10][10];
    for (int i = 0; i < p1.length; i++) {
      for (int j = 0; j < p1[i].length; j++) {
        p1[i][j] = new Pixel(0, 0, 0);
      }
    }

    HistogramModel hm = new HistogramModel();
    assertTrue(hm.checkTransparentImage(p1));

  }

  @Test
  public void checkTransparentImage2() {
    Pixel[][] p1 = new Pixel[10][10];
    for (int i = 0; i < p1.length; i++) {
      for (int j = 0; j < p1[i].length; j++) {
        p1[i][j] = new Pixel(255, 255, 255);
      }
    }

    HistogramModel hm = new HistogramModel();
    assertTrue(hm.checkTransparentImage(p1));

  }
}