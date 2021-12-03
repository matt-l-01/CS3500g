package model;

import controller.ImageManager;
import controller.ImageManager.ImageFileFormat;
import controller.RasterImageManager;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * Test class for the RasterImageModel
 * class. Tests all the operations
 * on the PPM images.
 *
 * @author Matt Stetter
 */
public class RasterImageModelTest {

  // START OF PIXEL CLASS TESTS

  /**
   * Internal test class used for the
   * Pixel inner class in RasterImageModel.
   */
  public static class PixelTest {

    /**
     * Tests if the get methods return
     * the correct values passed to the Pixel
     * constructor.
     */
    @Test
    public void gettersCorrectReturnVal() {
      Pixel testPixel = new ARGBPixel(255, 10, 40, 50);
      assertEquals(10, testPixel.getRed());
      assertEquals(40, testPixel.getGreen());
      assertEquals(50, testPixel.getBlue());
    }

    /**
     * Tests if two pixels of the same color are
     * equal, and two pixels of different colors
     * are not equal.
     */
    @Test
    public void correctComparison() {
      Pixel p1 = new ARGBPixel(255, 39, 100, 10);
      Pixel p2 = new ARGBPixel(255, 39, 100, 10);
      Pixel p3 = new ARGBPixel(255, 12, 40, 19);
      assertEquals(p1, p2);
      assertNotEquals(p2, p3);
      assertNotEquals(p1, p3);
    }
  }

  /**
   * Test field variable used
   * to assert on the functionality
   * of the model's public methods.
   */
  private ImageModel testModel;

  /**
   * Field ImageManager instance used to
   * test the load and save methods for
   * image processing.
   */
  private ImageManager testProcessorModel;

  // the kernel for the gaussian blur filter
  private final Kernel gaussianKernel = new FilterKernel(new Double[][] {
          {0.0625, 0.125, 0.0625},
          {0.125, 0.25, 0.125},
          {0.0625, 0.125, 0.0625}});

  // the kernel for the sharpen filter
  private final Kernel sharpenKernel = new FilterKernel(new Double[][] {
          {-0.125, -0.125, -0.125, -0.125, -0.125},
          {-0.125, 0.25, 0.25, 0.25, -0.125},
          {-0.125, 0.25, 1.0, 0.25, -0.125},
          {-0.125, 0.25, 0.25, 0.25, -0.125},
          {-0.125, -0.125, -0.125, -0.125, -0.125}
  });

  // the matrix for the greyscale color transformation
  private final ColorMatrix greyscaleMatrix = new RGBColorMatrix(new Double[][] {
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722}
  });

  // the matrix for the sepia color transformation
  private final ColorMatrix sepiaMatrix = new RGBColorMatrix(new Double[][] {
          {0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168},
          {0.272, 0.534, 0.131}
  });

  // START OF VISUALIZE COMPONENT TESTS (RED)

  /**
   * Tests if an input file has the
   * correct data when the red component
   * is visualized.
   */
  @Test
  public void redVisualize1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("test\\model\\TestInputFiles\\visualize1.ppm", "visualize1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize1");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.visualizeComponent(ImageModel.Component.RED);
    assertEquals(3, testModel.getWidth());
    assertEquals(2, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 12, 12, 12), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 95, 95, 95), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 26, 26, 26), testModel.getPixel(0, 2));
    assertEquals(new ARGBPixel(255, 129, 129, 129), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 173, 173, 173), testModel.getPixel(1, 1));
    assertEquals(new ARGBPixel(255, 18, 18, 18), testModel.getPixel(1, 2));
  }

  /**
   * Tests if an input file has the
   * correct data when the red component
   * is visualized.
   */
  @Test
  public void redVisualize2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize2.ppm", "visualize2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize2");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.visualizeComponent(ImageModel.Component.RED);
    assertEquals(1, testModel.getWidth());
    assertEquals(4, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 107, 107, 107), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 174, 174, 174), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 99, 99, 99), testModel.getPixel(2, 0));
    assertEquals(new ARGBPixel(255, 49, 49, 49), testModel.getPixel(3, 0));
  }

  // (GREEN)

  /**
   * Tests if an input file has the
   * correct data when the green component
   * is visualized.
   */
  @Test
  public void greenVisualize1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize1.ppm", "visualize1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize1");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.visualizeComponent(ImageModel.Component.GREEN);
    assertEquals(3, testModel.getWidth());
    assertEquals(2, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 26, 26, 26), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 103, 103, 103), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 39, 39, 39), testModel.getPixel(0, 2));
    assertEquals(new ARGBPixel(255, 9, 9, 9), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 18, 18, 18), testModel.getPixel(1, 1));
    assertEquals(new ARGBPixel(255, 38, 38, 38), testModel.getPixel(1, 2));
  }

  /**
   * Tests if an input file has the
   * correct data when the green component
   * is visualized.
   */
  @Test
  public void greenVisualize2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize2.ppm", "visualize2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize2");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.visualizeComponent(ImageModel.Component.GREEN);
    assertEquals(1, testModel.getWidth());
    assertEquals(4, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 38, 38, 38), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 28, 28, 28), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 180, 180, 180), testModel.getPixel(2, 0));
    assertEquals(new ARGBPixel(255, 108, 108, 108), testModel.getPixel(3, 0));
  }

  // (BLUE)

  /**
   * Tests if an input file has the
   * correct data when the blue component
   * is visualized.
   */
  @Test
  public void blueVisualize1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize1.ppm", "visualize1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize1");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.visualizeComponent(ImageModel.Component.BLUE);
    assertEquals(3, testModel.getWidth());
    assertEquals(2, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 34, 34, 34), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 78, 78, 78), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 245, 245, 245), testModel.getPixel(0, 2));
    assertEquals(new ARGBPixel(255, 10, 10, 10), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 45, 45, 45), testModel.getPixel(1, 1));
    assertEquals(new ARGBPixel(255, 209, 209, 209), testModel.getPixel(1, 2));
  }

  /**
   * Tests if an input file has the
   * correct data when the blue component
   * is visualized.
   */
  @Test
  public void blueVisualize2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize2.ppm", "visualize2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize2");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.visualizeComponent(ImageModel.Component.BLUE);
    assertEquals(1, testModel.getWidth());
    assertEquals(4, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 18, 18, 18), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 60, 60, 60), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 218, 218, 218), testModel.getPixel(2, 0));
    assertEquals(new ARGBPixel(255, 182, 182, 182), testModel.getPixel(3, 0));
  }

  // (VALUE)

  /**
   * Tests if an input file has the
   * correct data when the value component
   * is visualized.
   */
  @Test
  public void valueVisualize1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize1.ppm", "visualize1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize1");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.visualizeComponent(ImageModel.Component.VALUE);
    assertEquals(3, testModel.getWidth());
    assertEquals(2, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 34, 34, 34), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 103, 103, 103), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 245, 245, 245), testModel.getPixel(0, 2));
    assertEquals(new ARGBPixel(255, 129, 129, 129), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 173, 173, 173), testModel.getPixel(1, 1));
    assertEquals(new ARGBPixel(255, 209, 209, 209), testModel.getPixel(1, 2));
  }

  /**
   * Tests if an input file has the
   * correct data when the value component
   * is visualized.
   */
  @Test
  public void valueVisualize2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize2.ppm", "visualize2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize2");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.visualizeComponent(ImageModel.Component.VALUE);
    assertEquals(1, testModel.getWidth());
    assertEquals(4, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 107, 107, 107), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 174, 174, 174), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 218, 218, 218), testModel.getPixel(2, 0));
    assertEquals(new ARGBPixel(255, 182, 182, 182), testModel.getPixel(3, 0));
  }

  // (INTENSITY)

  /**
   * Tests if an input file has the
   * correct data when the intensity component
   * is visualized.
   */
  @Test
  public void intensityVisualize1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize1.ppm", "visualize1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize1");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.visualizeComponent(ImageModel.Component.INTENSITY);
    assertEquals(3, testModel.getWidth());
    assertEquals(2, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 24, 24, 24), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 92, 92, 92), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 103, 103, 103), testModel.getPixel(0, 2));
    assertEquals(new ARGBPixel(255, 49, 49, 49), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 79, 79, 79), testModel.getPixel(1, 1));
    assertEquals(new ARGBPixel(255, 88, 88, 88), testModel.getPixel(1, 2));
  }

  /**
   * Tests if an input file has the
   * correct data when the intensity component
   * is visualized.
   */
  @Test
  public void intensityVisualize2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize2.ppm", "visualize2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize2");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.visualizeComponent(ImageModel.Component.INTENSITY);
    assertEquals(1, testModel.getWidth());
    assertEquals(4, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 54, 54, 54), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 87, 87, 87), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 166, 166, 166), testModel.getPixel(2, 0));
    assertEquals(new ARGBPixel(255, 113, 113, 113), testModel.getPixel(3, 0));
  }

  // (LUMA)

  /**
   * Tests if an input file has the
   * correct data when the luma component
   * is visualized.
   */
  @Test
  public void lumaVisualize1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize1.ppm", "visualize1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize1");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.visualizeComponent(ImageModel.Component.LUMA);
    assertEquals(3, testModel.getWidth());
    assertEquals(2, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 24, 24, 24), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 99, 99, 99), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 51, 51, 51), testModel.getPixel(0, 2));
    assertEquals(new ARGBPixel(255, 35, 35, 35), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 53, 53, 53), testModel.getPixel(1, 1));
    assertEquals(new ARGBPixel(255, 46, 46, 46), testModel.getPixel(1, 2));
  }

  /**
   * Tests if an input file has the
   * correct data when the luma component
   * is visualized.
   */
  @Test
  public void lumaVisualize2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize2.ppm", "visualize2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize2");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.visualizeComponent(ImageModel.Component.LUMA);
    assertEquals(1, testModel.getWidth());
    assertEquals(4, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 51, 51, 51), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 61, 61, 61), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 166, 166, 166), testModel.getPixel(2, 0));
    assertEquals(new ARGBPixel(255, 101, 101, 101), testModel.getPixel(3, 0));
  }

  /**
   * Tests if IllegalArgumentException
   * is thrown when a null component
   * value is passed to the model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void nullComponent() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\test1.ppm", "test1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("test1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    testModel = testModel.visualizeComponent(null);
  }

  // FLIP HORIZONTALLY

  /**
   * Tests if an input file has the
   * correct data when flipped horizontally.
   */
  @Test
  public void flipHorizontal1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize1.ppm", "visualize1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize1");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.flip("horizontal");
    assertEquals(3, testModel.getWidth());
    assertEquals(2, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 26, 39, 245), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 95, 103, 78), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 12, 26, 34), testModel.getPixel(0, 2));
    assertEquals(new ARGBPixel(255, 18, 38, 209), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 173, 18, 45), testModel.getPixel(1, 1));
    assertEquals(new ARGBPixel(255, 129, 9, 10), testModel.getPixel(1, 2));
  }

  /**
   * Tests if an input file has the
   * correct data when flipped horizontally.
   */
  @Test
  public void flipHorizontal2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize2.ppm", "visualize2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize2");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.flip("horizontal");
    assertEquals(1, testModel.getWidth());
    assertEquals(4, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 107, 38, 18), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 174, 28, 60), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 99, 180, 218), testModel.getPixel(2, 0));
    assertEquals(new ARGBPixel(255, 49, 108, 182), testModel.getPixel(3, 0));
  }

  // FLIP VERTICALLY

  /**
   * Tests if an input file has the
   * correct data when flipped vertically.
   */
  @Test
  public void flipVertically1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize1.ppm", "visualize1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize1");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.flip("vertical");
    assertEquals(3, testModel.getWidth());
    assertEquals(2, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 129, 9, 10), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 173, 18, 45), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 18, 38, 209), testModel.getPixel(0, 2));
    assertEquals(new ARGBPixel(255, 12, 26, 34), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 95, 103, 78), testModel.getPixel(1, 1));
    assertEquals(new ARGBPixel(255, 26, 39, 245), testModel.getPixel(1, 2));
  }

  /**
   * Tests if an input file has the
   * correct data when flipped vertically.
   */
  @Test
  public void flipVertically2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize2.ppm", "visualize2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize2");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.flip("vertical");
    assertEquals(1, testModel.getWidth());
    assertEquals(4, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 49, 108, 182), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 99, 180, 218), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 174, 28, 60), testModel.getPixel(2, 0));
    assertEquals(new ARGBPixel(255, 107, 38, 18), testModel.getPixel(3, 0));
  }

  // BRIGHTEN

  /**
   * Tests if an input file has the
   * correct data when brightened.
   */
  @Test
  public void brighten1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize1.ppm", "visualize1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize1");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.brighten(10);
    assertEquals(3, testModel.getWidth());
    assertEquals(2, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 22, 36, 44), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 105, 113, 88), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 36, 49, 255), testModel.getPixel(0, 2));
    assertEquals(new ARGBPixel(255, 139, 19, 20), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 183, 28, 55), testModel.getPixel(1, 1));
    assertEquals(new ARGBPixel(255, 28, 48, 219), testModel.getPixel(1, 2));
  }

  /**
   * Tests if an input file has the
   * correct data when brightened.
   */
  @Test
  public void brighten2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize2.ppm", "visualize2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize2");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.brighten(20);
    assertEquals(1, testModel.getWidth());
    assertEquals(4, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 127, 58, 38), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 194, 48, 80), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 119, 200, 238), testModel.getPixel(2, 0));
    assertEquals(new ARGBPixel(255, 69, 128, 202), testModel.getPixel(3, 0));
  }

  /**
   * Tests if the maxRGB value of
   * the image model is increased
   * when the brightening value is increased
   * over the current max RGB value.
   */
  @Test
  public void brightenIncreaseMaxRGB() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("test\\model\\TestInputFiles\\brighten1.ppm", "brighten", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("brighten");
    } catch (IOException e) {
      fail("Could not open file");
    }
    assertEquals(100, testModel.getMaxRGB());
    testModel.brighten(10);
    assertEquals(110, testModel.getMaxRGB());
  }

  /**
   * Tests if an input file has the
   * correct data when brightened over the
   * max RGB color value.
   */
  @Test
  public void brightenOverBounds() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize2.ppm", "visualize2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize2");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.brighten(50);
    assertEquals(1, testModel.getWidth());
    assertEquals(4, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 157, 88, 68), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 224, 78, 110), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 149, 230, 255), testModel.getPixel(2, 0));
    assertEquals(new ARGBPixel(255, 99, 158, 232), testModel.getPixel(3, 0));
  }

  // DARKEN

  /**
   * Tests if an input file has the
   * correct data when brightened.
   */
  @Test
  public void darken1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize1.ppm", "visualize1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize1");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.brighten(-5);
    assertEquals(3, testModel.getWidth());
    assertEquals(2, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 7, 21, 29), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 90, 98, 73), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 21, 34, 240), testModel.getPixel(0, 2));
    assertEquals(new ARGBPixel(255, 124, 4, 5), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 168, 13, 40), testModel.getPixel(1, 1));
    assertEquals(new ARGBPixel(255, 13, 33, 204), testModel.getPixel(1, 2));
  }

  /**
   * Tests if an input file has the
   * correct data when brightened.
   */
  @Test
  public void darken2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize2.ppm", "visualize2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize2");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.brighten(-10);
    assertEquals(1, testModel.getWidth());
    assertEquals(4, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 97, 28, 8), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 164, 18, 50), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 89, 170, 208), testModel.getPixel(2, 0));
    assertEquals(new ARGBPixel(255, 39, 98, 172), testModel.getPixel(3, 0));
  }

  /**
   * Tests if an input file has the
   * correct data when brightened over the
   * max RGB color value.
   */
  @Test
  public void darkenUnderBounds() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\visualize2.ppm", "visualize2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("visualize2");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.brighten(-100);
    assertEquals(1, testModel.getWidth());
    assertEquals(4, testModel.getHeight());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 7, 0, 0), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 74, 0, 0), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 0, 80, 118), testModel.getPixel(2, 0));
    assertEquals(new ARGBPixel(255, 0, 8, 82), testModel.getPixel(3, 0));
  }

  // MOSAIC
  @Test
  public void mosaic1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\mosaic.ppm", "mosaic", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("mosaic");
    } catch (IOException e)  {
      fail("Could not find file");
    }
    testModel = testModel.mosaic(1);
    assertEquals(new ARGBPixel(255, 71, 106, 73), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 71, 106, 73), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 71, 106, 73), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 71, 106, 73), testModel.getPixel(1, 1));
  }

  // test filter exception
  @Test(expected = IllegalArgumentException.class)
  public void testFilterNullKernelException() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\test1.ppm", "test1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("test1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    testModel = testModel.filter(null);
  }

  // test filter rounding
  @Test
  public void testFilter1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\test1.ppm", "test1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("test1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    testModel = testModel.filter(gaussianKernel);
    assertEquals(37, testModel.getPixel(0,0).getRed());
    assertEquals(56, testModel.getPixel(0,0).getGreen());
    assertEquals(43, testModel.getPixel(0,0).getBlue());
  } // also tests kernel-off-edge behavior, as this image is smaller than the kernel size

  // test filter max-255 clamping
  @Test
  public void testFilter2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\filter1.ppm", "filter1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("filter1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    testModel = testModel.filter(gaussianKernel);
    assertEquals(99, testModel.getPixel(1,1).getRed());
    assertEquals(72, testModel.getPixel(1,1).getGreen());
    assertEquals(255, testModel.getPixel(1,1).getBlue());
  } // this image is big enough to apply the full gaussian kernel

  // test larger size filter kernel
  @Test
  public void testFilter3() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\filter2.ppm", "filter2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("filter2");
    } catch (IOException e) {
      fail("Could not open file");
    }
    testModel = testModel.filter(sharpenKernel);
    assertEquals(0, testModel.getPixel(2,2).getRed());
    assertEquals(0, testModel.getPixel(2, 2).getGreen());
    assertEquals(204, testModel.getPixel(2, 2).getBlue());
  } // also tests min-0 clamping

  // test color transformation exception
  @Test(expected = IllegalArgumentException.class)
  public void testColorTransformNullMatrixException() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\test1.ppm", "test1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("test1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    testModel = testModel.colorTransform(null);
  }

  // test color transformation on 2 images
  @Test
  public void testColorTransform1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\test1.ppm", "test1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("test1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    testModel = testModel.colorTransform(greyscaleMatrix);
    assertEquals(83, testModel.getPixel(0,1).getRed());
    assertEquals(83, testModel.getPixel(0,1).getGreen());
    assertEquals(83, testModel.getPixel(0,1).getBlue());
  }

  @Test
  public void testColorTransform2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(
              "test\\model\\TestInputFiles\\test1.ppm", "test1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("test1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    testModel = testModel.colorTransform(sepiaMatrix);
    assertEquals(57, testModel.getPixel(1,1).getRed());
    assertEquals(51, testModel.getPixel(1,1).getGreen());
    assertEquals(40, testModel.getPixel(1,1).getBlue());
  }
}
