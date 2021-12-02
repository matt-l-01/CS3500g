package controller;

import controller.ImageManager.ImageFileFormat;
import model.ARGBPixel;
import model.FilterKernel;
import model.ImageModel;
import model.RGBColorMatrix;
import model.RGBPixel;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Holds tests for methods of the RasterImageManager class.
 */
public class RasterImageManagerTest {

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

  // START OF LOAD METHOD TESTS

  /**
   * Tests if a loaded file
   * contains the correct
   * contents when loaded into
   * the RasterImageModel.
   */
  @Test
  public void file1TestStartingComment() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test1.ppm", "test1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("test1");
    } catch (IOException e) {
      fail("File could not be found");
    }
    assertEquals(2, testModel.getHeight());
    assertEquals(2, testModel.getWidth());
    assertEquals(255, testModel.getMaxRGB());
    Assert.assertEquals(new ARGBPixel(255, 25, 36, 104), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 27, 99, 84), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 207, 255, 0), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 25, 36, 104), testModel.getPixel(1, 1));
  }

  /**
   * Tests if a loaded file
   * contains the correct
   * contents when loaded into
   * the RasterImageModel.
   */
  @Test
  public void file2TestNoComment() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test2.ppm", "test2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("test2");
    } catch (IOException e) {
      fail("File could not be found");
    }
    assertEquals(3, testModel.getHeight());
    assertEquals(1, testModel.getWidth());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 99, 104, 55), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 25, 36, 204), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 37, 68, 12), testModel.getPixel(2, 0));
  }

  /**
   * Tests if IllegalArgumentException
   * is thrown when an image contains
   * an invalid number of RGB values for
   * its stated size.
   */
  @Test(expected = IllegalArgumentException.class)
  public void file3TestInvalidNumberOfPixels() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test3.ppm", "test3", ImageFileFormat.PPM);
    } catch (IOException e) {
      fail("File could not be found");
    }
  }

  /**
   * Tests if IllegalArgumentException
   * is thrown when a file does not start
   * with P3.
   */
  @Test(expected = IllegalArgumentException.class)
  public void file4TestDoesNotStartWithP3() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test4.ppm", "test4", ImageFileFormat.PPM);
    } catch (IOException e) {
      fail("File could not be found");
    }
  }

  /**
   * Tests if IllegalArgumentException
   * is thrown when an image file contains
   * a non-numeric character for an RGB value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void file5TestContainsNonNumericValues() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test5.ppm", "test5", ImageFileFormat.PPM);
    } catch (IOException e) {
      fail("File could not be found");
    }
  }

  /**
   * Tests if IllegalArgumentException
   * is thrown when an RGB value is
   * invalid (less than 0).
   */
  @Test(expected = IllegalArgumentException.class)
  public void file6TestContainsInvalidRGBValues() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test6.ppm", "test6", ImageFileFormat.PPM);
    } catch (IOException e) {
      fail("File could not be found");
    }
  }

  /**
   * Tests if IllegalArgumentException
   * is thrown when an RGB value is
   * invalid (greater than max).
   */
  @Test(expected = IllegalArgumentException.class)
  public void file7TestContainsInvalidRGBValues() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test7.ppm", "test7", ImageFileFormat.PPM);
    } catch (IOException e) {
      fail("File could not be found");
    }
  }

  /**
   * Tests if IOException is thrown
   * when a non-existent file path
   * is provided to the load method.
   */
  @Test
  public void fileNotFound() {
    boolean fileFound;
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test99.ppm", "test99", ImageFileFormat.PPM);
      fileFound = true;
    } catch (IOException e) {
      fileFound = false;
    }
    assertFalse(fileFound);
  }

  /**
   * Tests if IllegalArgumentException
   * is thrown when a row and column
   * provided to getPixel is out of bounds.
   */
  @Test(expected = IllegalArgumentException.class)
  public void pixelOutOfBounds() {
    try {
      String path = "images\\ppm\\test1.ppm";
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test1.ppm", "test1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("test1");
    } catch (IOException e) {
      fail("File could not be found");
    }
    testModel.getPixel(2, 1);
  }

  /**
   * Tests if IllegalArgumentException is thrown
   * when a null string path is provided to the
   * load method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void nullLoadPath() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(null, "name", ImageFileFormat.PPM);
    } catch (IOException e) {
      fail("Should check for null before finding file");
    }
  }

  /**
   * Tests if IllegalArgumentException is thrown
   * when a null string name is provided to the
   * load method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void nullLoadName() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test1.ppm", null, ImageFileFormat.PPM);
    } catch (IOException e) {
      fail("Should check for null before finding file");
    }
  }

  // START OF SAVE METHOD TESTS

  /**
   * Tests if the file gets loaded correctly,
   * and then checks if it is saved to the correct
   * path by opening the output file and asserting
   * on the contents.
   */
  @Test
  public void correctWriteOperation1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test1.ppm", "test1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("test1");
    } catch (IOException e) {
      fail("File could not be found");
    }
    assertEquals(2, testModel.getHeight());
    assertEquals(2, testModel.getWidth());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 25, 36, 104), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 27, 99, 84), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 207, 255, 0), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 25, 36, 104), testModel.getPixel(1, 1));
    try {
      testProcessorModel.save("test1", "images\\ppm\\output1.ppm", ImageFileFormat.PPM);
    } catch (IOException e) {
      fail("Could not save file");
    }
    File out = new File("images\\ppm\\output1.ppm");
    assertTrue(out.exists());
    try {
      String path = "images\\ppm\\output1.ppm";
      testProcessorModel.load(path, "output1", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("output1");
    } catch (IOException e) {
      fail("File could not be found");
    }
    assertEquals(2, testModel.getHeight());
    assertEquals(2, testModel.getWidth());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 25, 36, 104), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 27, 99, 84), testModel.getPixel(0, 1));
    assertEquals(new ARGBPixel(255, 207, 255, 0), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 25, 36, 104), testModel.getPixel(1, 1));
  }

  /**
   * Tests if the file gets loaded correctly,
   * and then checks if it is saved to the correct
   * path by opening the output file and asserting
   * on the contents.
   */
  @Test
  public void correctWriteOperation2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test2.ppm", "test2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("test2");
    } catch (IOException e) {
      fail("File could not be found");
    }
    assertEquals(3, testModel.getHeight());
    assertEquals(1, testModel.getWidth());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 99, 104, 55), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 25, 36, 204), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 37, 68, 12), testModel.getPixel(2, 0));
    try {
      testProcessorModel.save("test2", "images\\ppm\\output2.ppm", ImageFileFormat.PPM);
    } catch (IOException e) {
      fail("Could not save file");
    }
    File out = new File("images\\ppm\\output2.ppm");
    assertTrue(out.exists());
    try {
      String path = "images\\ppm\\output2.ppm";
      testProcessorModel.load(path, "output2", ImageFileFormat.PPM);
      testModel = testProcessorModel.getStoredImages().get("output2");
    } catch (IOException e) {
      fail("File could not be found");
    }
    assertEquals(3, testModel.getHeight());
    assertEquals(1, testModel.getWidth());
    assertEquals(255, testModel.getMaxRGB());
    assertEquals(new ARGBPixel(255, 99, 104, 55), testModel.getPixel(0, 0));
    assertEquals(new ARGBPixel(255, 25, 36, 204), testModel.getPixel(1, 0));
    assertEquals(new ARGBPixel(255, 37, 68, 12), testModel.getPixel(2, 0));
  }

  /**
   * Tests if IllegalArgumentException
   * is thrown when a null model is passed
   * to the save method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void nullSaveFileName() {
    try {
      new RasterImageManager().save(null, "path\\ex", ImageFileFormat.PPM);
    } catch (IOException e) {
      fail("Should check for null before saving to file");
    }
  }

  /**
   * Tests if IllegalArgumentException
   * is thrown when a null path is passed
   * to the save method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void nullSavePathName() {
    try {
      new RasterImageManager().save("test1", null, ImageFileFormat.PPM);
    } catch (IOException e) {
      fail("Should check for null before saving to file");
    }
  }

  /**
   * Tries to save a file name
   * that does not exist.
   */
  @Test(expected = IllegalStateException.class)
  public void nonExistentFile() {
    try {
      new RasterImageManager().save("test1", "images\\ppm\\output4.ppm", ImageFileFormat.PPM);
    } catch (IOException e) {
      fail("Should check for null before saving to file");
    }
  }

  // START OF DIFFERENT EXTENSION TESTS
  // (TEST FILES DRAWN IN MS PAINT)

  /**
   * Tests if a BMP is loaded correctly
   * with the correct contents.
   */
  @Test
  public void loadBMP1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(".\\images\\bmp\\test1.bmp", "test1", ImageFileFormat.BMP);
      testModel = testProcessorModel.getStoredImages().get("test1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    Assert.assertEquals(new RGBPixel(131, 210, 154), testModel.getPixel(0, 0));
    assertEquals(new RGBPixel(74, 191, 108), testModel.getPixel(0, 1));
    assertEquals(new RGBPixel(149, 218, 169), testModel.getPixel(0, 2));
    assertEquals(new RGBPixel(61, 186, 97), testModel.getPixel(1, 0));
    assertEquals(new RGBPixel(68, 117, 57), testModel.getPixel(1, 1));
    assertEquals(new RGBPixel(76, 90, 77), testModel.getPixel(1, 2));
    assertEquals(new RGBPixel(108, 131, 89), testModel.getPixel(2, 0));
    assertEquals(new RGBPixel(108, 48, 36), testModel.getPixel(2, 1));
    assertEquals(new RGBPixel(136, 0, 21), testModel.getPixel(2, 2));
  }

  /**
   * Tests if a BMP is loaded correctly
   * with the correct contents.
   */
  @Test
  public void loadBMP2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(".\\images\\bmp\\test2.bmp", "test2", ImageFileFormat.BMP);
      testModel = testProcessorModel.getStoredImages().get("test2");
    } catch (IOException e) {
      fail("Could not open file");
    }
    assertEquals(new RGBPixel(180, 228, 194), testModel.getPixel(0, 0));
    assertEquals(new RGBPixel(149, 218, 169), testModel.getPixel(0, 1));
    assertEquals(new RGBPixel(255, 227, 237), testModel.getPixel(0, 2));
    assertEquals(new RGBPixel(149, 203, 160), testModel.getPixel(1, 0));
    assertEquals(new RGBPixel(34, 177, 76), testModel.getPixel(1, 1));
    assertEquals(new RGBPixel(255, 216, 229), testModel.getPixel(1, 2));
    assertEquals(new RGBPixel(255, 216, 229), testModel.getPixel(2, 0));
    assertEquals(new RGBPixel(255, 174, 201), testModel.getPixel(2, 1));
    assertEquals(new RGBPixel(255, 255, 255), testModel.getPixel(2, 2));
  }

  /**
   * Tests if a JPG is loaded correctly
   * with the correct contents.
   */
  @Test
  public void loadJPG1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(".\\images\\jpg\\test1.jpg", "test1", ImageFileFormat.JPG);
      testModel = testProcessorModel.getStoredImages().get("test1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    assertEquals(new RGBPixel(139, 211, 145), testModel.getPixel(0, 0));
    assertEquals(new RGBPixel(82, 183, 113), testModel.getPixel(0, 1));
    assertEquals(new RGBPixel(142, 223, 164), testModel.getPixel(0, 2));
    assertEquals(new RGBPixel(56, 187, 109), testModel.getPixel(1, 0));
    assertEquals(new RGBPixel(56, 118, 45), testModel.getPixel(1, 1));
    assertEquals(new RGBPixel(77, 94, 86), testModel.getPixel(1, 2));
    assertEquals(new RGBPixel(113, 127, 92), testModel.getPixel(2, 0));
    assertEquals(new RGBPixel(110, 51, 37), testModel.getPixel(2, 1));
    assertEquals(new RGBPixel(131, 2, 30), testModel.getPixel(2, 2));
  }

  /**
   * Tests if a JPG is loaded correctly
   * with the correct contents.
   */
  @Test
  public void loadJPG2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(".\\images\\jpg\\test2.jpg", "test2", ImageFileFormat.JPG);
      testModel = testProcessorModel.getStoredImages().get("test2");
    } catch (IOException e) {
      fail("Could not open file");
    }
    assertEquals(new RGBPixel(165, 227, 186), testModel.getPixel(0, 0));
    assertEquals(new RGBPixel(148, 219, 177), testModel.getPixel(0, 1));
    assertEquals(new RGBPixel(255, 231, 240), testModel.getPixel(0, 2));
    assertEquals(new RGBPixel(157, 211, 162), testModel.getPixel(1, 0));
    assertEquals(new RGBPixel(42, 167, 73), testModel.getPixel(1, 1));
    assertEquals(new RGBPixel(249, 218, 236), testModel.getPixel(1, 2));
    assertEquals(new RGBPixel(252, 216, 228), testModel.getPixel(2, 0));
    assertEquals(new RGBPixel(255, 167, 198), testModel.getPixel(2, 1));
    assertEquals(new RGBPixel(255, 255, 239), testModel.getPixel(2, 2));
  }

  /**
   * Tests if a PNG is loaded correctly
   * with the correct contents.
   */
  @Test
  public void loadPNG1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(".\\images\\png\\test1.png", "test1", ImageFileFormat.PNG);
      testModel = testProcessorModel.getStoredImages().get("test1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    assertEquals(new RGBPixel(131, 210, 154), testModel.getPixel(0, 0));
    assertEquals(new RGBPixel(74, 191, 108), testModel.getPixel(0, 1));
    assertEquals(new RGBPixel(149, 218, 169), testModel.getPixel(0, 2));
    assertEquals(new RGBPixel(61, 186, 97), testModel.getPixel(1, 0));
    assertEquals(new RGBPixel(68, 117, 57), testModel.getPixel(1, 1));
    assertEquals(new RGBPixel(76, 90, 77), testModel.getPixel(1, 2));
    assertEquals(new RGBPixel(108, 131, 89), testModel.getPixel(2, 0));
    assertEquals(new RGBPixel(108, 48, 36), testModel.getPixel(2, 1));
    assertEquals(new RGBPixel(136, 0, 21), testModel.getPixel(2, 2));
  }

  /**
   * Tests if a PNG is loaded correctly
   * with the correct contents.
   */
  @Test
  public void loadPNG2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(".\\images\\png\\test2.png", "test2", ImageFileFormat.PNG);
      testModel = testProcessorModel.getStoredImages().get("test2");
    } catch (IOException e) {
      fail("Could not open file");
    }
    assertEquals(new RGBPixel(180, 228, 194), testModel.getPixel(0, 0));
    assertEquals(new RGBPixel(149, 218, 169), testModel.getPixel(0, 1));
    assertEquals(new RGBPixel(255, 227, 237), testModel.getPixel(0, 2));
    assertEquals(new RGBPixel(149, 203, 160), testModel.getPixel(1, 0));
    assertEquals(new RGBPixel(34, 177, 76), testModel.getPixel(1, 1));
    assertEquals(new RGBPixel(255, 216, 229), testModel.getPixel(1, 2));
    assertEquals(new RGBPixel(255, 216, 229), testModel.getPixel(2, 0));
    assertEquals(new RGBPixel(255, 174, 201), testModel.getPixel(2, 1));
    assertEquals(new RGBPixel(255, 255, 255), testModel.getPixel(2, 2));
  }

  /**
   * Tests if IllegalArgumentException is
   * thrown when a null extension is passed to load.
   */
  @Test(expected = IllegalArgumentException.class)
  public void nullLoadExtension() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("path", "name", null);
    } catch (IOException e) {
      fail("IOException thrown");
    }
  }

  /**
   * Tests if a BMP file is
   * saved with the correct data.
   */
  @Test
  public void saveBMP1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(".\\images\\bmp\\test1.bmp", "test1", ImageFileFormat.BMP);
      testProcessorModel.save("test1", ".\\images\\bmp\\output1.bmp", ImageFileFormat.BMP);
      testProcessorModel.load(".\\images\\bmp\\output1.bmp", "output1", ImageFileFormat.BMP);
      testModel = testProcessorModel.getStoredImages().get("output1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    assertEquals(new RGBPixel(131, 210, 154), testModel.getPixel(0, 0));
    assertEquals(new RGBPixel(74, 191, 108), testModel.getPixel(0, 1));
    assertEquals(new RGBPixel(149, 218, 169), testModel.getPixel(0, 2));
    assertEquals(new RGBPixel(61, 186, 97), testModel.getPixel(1, 0));
    assertEquals(new RGBPixel(68, 117, 57), testModel.getPixel(1, 1));
    assertEquals(new RGBPixel(76, 90, 77), testModel.getPixel(1, 2));
    assertEquals(new RGBPixel(108, 131, 89), testModel.getPixel(2, 0));
    assertEquals(new RGBPixel(108, 48, 36), testModel.getPixel(2, 1));
    assertEquals(new RGBPixel(136, 0, 21), testModel.getPixel(2, 2));
  }

  /**
   * Tests if a BMP file is
   * saved with the correct data.
   */
  @Test
  public void saveBMP2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(".\\images\\bmp\\test2.bmp", "test2", ImageFileFormat.BMP);
      testProcessorModel.save("test2", ".\\images\\bmp\\output2.bmp", ImageFileFormat.BMP);
      testProcessorModel.load(".\\images\\bmp\\output2.bmp", "output2", ImageFileFormat.BMP);
      testModel = testProcessorModel.getStoredImages().get("output2");
    } catch (IOException e) {
      fail("Could not open file");
    }
    assertEquals(new RGBPixel(180, 228, 194), testModel.getPixel(0, 0));
    assertEquals(new RGBPixel(149, 218, 169), testModel.getPixel(0, 1));
    assertEquals(new RGBPixel(255, 227, 237), testModel.getPixel(0, 2));
    assertEquals(new RGBPixel(149, 203, 160), testModel.getPixel(1, 0));
    assertEquals(new RGBPixel(34, 177, 76), testModel.getPixel(1, 1));
    assertEquals(new RGBPixel(255, 216, 229), testModel.getPixel(1, 2));
    assertEquals(new RGBPixel(255, 216, 229), testModel.getPixel(2, 0));
    assertEquals(new RGBPixel(255, 174, 201), testModel.getPixel(2, 1));
    assertEquals(new RGBPixel(255, 255, 255), testModel.getPixel(2, 2));
  }

  /**
   * Tests if a JPG file is
   * saved with the correct data.
   */
  @Test
  public void saveJPG1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(".\\images\\jpg\\test1.jpg", "test1", ImageFileFormat.JPG);
      testProcessorModel.save("test1", ".\\images\\jpg\\output1.jpg", ImageFileFormat.JPG);
      testProcessorModel.load(".\\images\\jpg\\output1.jpg", "output1", ImageFileFormat.JPG);
      testModel = testProcessorModel.getStoredImages().get("output1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    assertEquals(new RGBPixel(178, 204, 195), testModel.getPixel(0, 0));
    assertEquals(new RGBPixel(116, 142, 133), testModel.getPixel(0, 1));
    assertEquals(new RGBPixel(176, 199, 191), testModel.getPixel(0, 2));
    assertEquals(new RGBPixel(99, 122, 114), testModel.getPixel(1, 0));
    assertEquals(new RGBPixel(100, 123, 115), testModel.getPixel(1, 1));
    assertEquals(new RGBPixel(71, 92, 85), testModel.getPixel(1, 2));
    assertEquals(new RGBPixel(118, 137, 131), testModel.getPixel(2, 0));
    assertEquals(new RGBPixel(48, 67, 61), testModel.getPixel(2, 1));
    assertEquals(new RGBPixel(28, 47, 41), testModel.getPixel(2, 2));
  }

  /**
   * Tests if a JPG file is
   * saved with the correct data.
   */
  @Test
  public void saveJPG2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(".\\images\\jpg\\test2.jpg", "test2", ImageFileFormat.JPG);
      testProcessorModel.save("test2", ".\\images\\jpg\\output2.jpg", ImageFileFormat.JPG);
      testProcessorModel.load(".\\images\\jpg\\output2.jpg", "output2", ImageFileFormat.JPG);
      testModel = testProcessorModel.getStoredImages().get("output2");
    } catch (IOException e) {
      fail("Could not open file");
    }
    assertEquals(new RGBPixel(181, 208, 193), testModel.getPixel(0, 0));
    assertEquals(new RGBPixel(183, 208, 186), testModel.getPixel(0, 1));
    assertEquals(new RGBPixel(214, 238, 204), testModel.getPixel(0, 2));
    assertEquals(new RGBPixel(185, 211, 200), testModel.getPixel(1, 0));
    assertEquals(new RGBPixel(99, 124, 105), testModel.getPixel(1, 1));
    assertEquals(new RGBPixel(231, 255, 223), testModel.getPixel(1, 2));
    assertEquals(new RGBPixel(204, 226, 223), testModel.getPixel(2, 0));
    assertEquals(new RGBPixel(167, 188, 179), testModel.getPixel(2, 1));
    assertEquals(new RGBPixel(242, 255, 241), testModel.getPixel(2, 2));
  }

  /**
   * Tests if a PNG file is
   * saved with the correct data.
   */
  @Test
  public void savePNG1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(".\\images\\png\\test1.png", "test1", ImageFileFormat.PNG);
      testProcessorModel.save("test1", ".\\images\\png\\output1.png", ImageFileFormat.PNG);
      testProcessorModel.load(".\\images\\png\\output1.png", "output1", ImageFileFormat.PNG);
      testModel = testProcessorModel.getStoredImages().get("output1");
    } catch (IOException e) {
      fail("Could not open file");
    }
    assertEquals(new RGBPixel(131, 210, 154), testModel.getPixel(0, 0));
    assertEquals(new RGBPixel(74, 191, 108), testModel.getPixel(0, 1));
    assertEquals(new RGBPixel(149, 218, 169), testModel.getPixel(0, 2));
    assertEquals(new RGBPixel(61, 186, 97), testModel.getPixel(1, 0));
    assertEquals(new RGBPixel(68, 117, 57), testModel.getPixel(1, 1));
    assertEquals(new RGBPixel(76, 90, 77), testModel.getPixel(1, 2));
    assertEquals(new RGBPixel(108, 131, 89), testModel.getPixel(2, 0));
    assertEquals(new RGBPixel(108, 48, 36), testModel.getPixel(2, 1));
    assertEquals(new RGBPixel(136, 0, 21), testModel.getPixel(2, 2));
  }

  /**
   * Tests if a PNG file is
   * saved with the correct data.
   */
  @Test
  public void savePNG2() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load(".\\images\\png\\test2.png", "test2", ImageFileFormat.PNG);
      testProcessorModel.save("test2", ".\\images\\png\\output2.png", ImageFileFormat.PNG);
      testProcessorModel.load(".\\images\\png\\output2.png", "output2", ImageFileFormat.PNG);
      testModel = testProcessorModel.getStoredImages().get("output2");
    } catch (IOException e) {
      fail("Could not open file");
    }
    assertEquals(new RGBPixel(180, 228, 194), testModel.getPixel(0, 0));
    assertEquals(new RGBPixel(149, 218, 169), testModel.getPixel(0, 1));
    assertEquals(new RGBPixel(255, 227, 237), testModel.getPixel(0, 2));
    assertEquals(new RGBPixel(149, 203, 160), testModel.getPixel(1, 0));
    assertEquals(new RGBPixel(34, 177, 76), testModel.getPixel(1, 1));
    assertEquals(new RGBPixel(255, 216, 229), testModel.getPixel(1, 2));
    assertEquals(new RGBPixel(255, 216, 229), testModel.getPixel(2, 0));
    assertEquals(new RGBPixel(255, 174, 201), testModel.getPixel(2, 1));
    assertEquals(new RGBPixel(255, 255, 255), testModel.getPixel(2, 2));
  }

  /**
   * Tests if IllegalArgumentException is
   * thrown when a null extension is passed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void nullSaveExtension() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.save("name", "path", null);
    } catch (IOException e) {
      fail("IOException thrown");
    }
  }

  // test that filter throws an exception for a null kernel
  @Test(expected = IllegalArgumentException.class)
  public void testFilterNullKernel() {
    testProcessorModel = new RasterImageManager();
    testProcessorModel.filter(null, "image1", "image2");
  }

  // test that filter throws an exception for a null image name
  @Test(expected = IllegalArgumentException.class)
  public void testFilterNullImageName() {
    testProcessorModel = new RasterImageManager();
    Double[][] gaussianArray = new Double[][] {{0.0625, 0.125, 0.0625},
        {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}};
    testProcessorModel.filter(new FilterKernel(gaussianArray), null, "image2");
  }

  // test that filter throws an exception for a null result name
  @Test(expected = IllegalArgumentException.class)
  public void testFilterNullResultName() {
    testProcessorModel = new RasterImageManager();
    Double[][] gaussianArray = new Double[][] {{0.0625, 0.125, 0.0625},
        {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}};
    testProcessorModel.filter(new FilterKernel(gaussianArray), "image1", null);
  }

  // test that filter stores images correctly
  @Test
  public void testFilter1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test1.ppm", "image1", ImageFileFormat.PPM);
      Double[][] gaussianArray = new Double[][]{{0.0625, 0.125, 0.0625},
          {0.125, 0.25, 0.125},
          {0.0625, 0.125, 0.0625}};
      testProcessorModel.filter(new FilterKernel(gaussianArray), "image1", "image2");
    } catch (IOException e) {
      fail("Could not load file");
    }
    ImageModel model2 = testProcessorModel.getStoredImages().getOrDefault("image2", null);
    assertNotEquals(null, model2);
  }

  // test that colorTransform throws an exception for a null matrix
  @Test(expected = IllegalArgumentException.class)
  public void testTransformNullMatrix() {
    testProcessorModel = new RasterImageManager();
    testProcessorModel.colorTransform(null, "image1", "image2");
  }

  // test that colorTransform throws an exception for a null image name
  @Test(expected = IllegalArgumentException.class)
  public void testTransformNullImageName() {
    testProcessorModel = new RasterImageManager();
    Double[][] sepiaArray = new Double[][] {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
    testProcessorModel.colorTransform(new RGBColorMatrix(sepiaArray), null, "image2");
  }

  // test that colorTransform throws an exception for a null result name
  @Test(expected = IllegalArgumentException.class)
  public void testTransformNullResultName() {
    testProcessorModel = new RasterImageManager();
    Double[][] sepiaArray = new Double[][] {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
    testProcessorModel.colorTransform(new RGBColorMatrix(sepiaArray), "image1", null);
  }

  // test that colorTransform stores images correctly
  @Test
  public void testTransform1() {
    try {
      testProcessorModel = new RasterImageManager();
      testProcessorModel.load("images\\ppm\\test1.ppm", "image1", ImageFileFormat.PPM);
      Double[][] sepiaArray = new Double[][]{
              {0.393, 0.769, 0.189},
              {0.349, 0.686, 0.168},
              {0.272, 0.534, 0.131}
      };
      testProcessorModel.colorTransform(new RGBColorMatrix(sepiaArray), "image1", "image2");
    } catch (IOException e) {
      fail("Could not load file");
    }
    ImageModel model2 = testProcessorModel.getStoredImages().getOrDefault("image2", null);
    assertNotEquals(null, model2);
  }
}