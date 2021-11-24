import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import model.ImageEditorModel;
import model.Pixel;
import model.SimpleEditorModel;
import model.histogram.Histogram;
import model.histogram.HistogramModel;

import static org.junit.Assert.*;

public class HistogramModelTest {

  @Test
  public void setImageTest() {

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTest.ppm");

    assertEquals(array1[0][0].getRed(), array2[0][0].getRed());
    assertEquals(array1[0][0].getGreen(), array2[0][0].getGreen());
    assertEquals(array1[0][0].getBlue(), array2[0][0].getBlue());

    assertEquals(array1[0][1].getRed(), array2[0][1].getRed());
    assertEquals(array1[0][1].getGreen(), array2[0][1].getGreen());
    assertEquals(array1[0][1].getBlue(), array2[0][1].getBlue());

    assertEquals(array1[1][0].getRed(), array2[1][0].getRed());
    assertEquals(array1[1][0].getGreen(), array2[1][0].getGreen());
    assertEquals(array1[1][0].getBlue(), array2[1][0].getBlue());

    assertEquals(array1[1][1].getRed(), array2[1][1].getRed());
    assertEquals(array1[1][1].getGreen(), array2[1][1].getGreen());
    assertEquals(array1[1][1].getBlue(), array2[1][1].getBlue());

  }

  @Test
  public void fillFrequencies() {
    Histogram hm = new HistogramModel();
    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    ArrayList<Integer> redVal = new ArrayList<>();
    ArrayList<Integer> greenVal = new ArrayList<>();
    ArrayList<Integer> blueVal = new ArrayList<>();
    hm.fillFrequencies();
    Pixel p = array1(getRed(), getGreen(), getBlue());

    assertEquals(redVal[0][0], p.getRed());
    assertEquals(redVal[0][1], p.getRed());
    assertEquals(greenVal[0][0], p.getGreen());
    assertEquals(greenVal[0][1], p.getGreen());
    assertEquals(blueVal[0][0], p.getBlue());
    assertEquals(blueVal[0][1], p.getBlue());
  }

  @Test
  public void getRedBarTest() {
    Histogram hm = new HistogramModel();
    ArrayList<Integer> redList = new ArrayList<>();
    hm.fillFrequencies();
    hm.getRedBar();
    assertEquals(redList, hm.getRedBar());
  }

  @Test
  public void getGreenBarTest() {
    Histogram hm = new HistogramModel();
    ArrayList<Integer> greenList = new ArrayList<>();
    hm.fillFrequencies();
    hm.getGreenBar();
    assertEquals(greenList, hm.getGreenBar());
  }

  @Test
  public void getBlueBarTest() {
    Histogram hm = new HistogramModel();
    ArrayList<Integer> blueList = new ArrayList<>();
    hm.fillFrequencies();
    hm.getBlueBar();
    assertEquals(blueList, hm.getBlueBar());
  }

  @Test
  public void getIntensityBarTest() {
    Histogram hm = new HistogramModel();
    ArrayList<Integer> intensityList = new ArrayList<>();
    hm.fillFrequencies();
    hm.getIntensityBar();
    assertEquals(intensityList, hm.getIntensityBar());
  }

}
