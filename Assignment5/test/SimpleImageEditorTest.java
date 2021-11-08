import org.junit.Test;

import model.Component;
import model.ImageEditorModel;
import model.Pixel;
import model.SimpleEditorModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for the model.SimpleEditorModel.
 */
public class SimpleImageEditorTest {

  @Test
  public void testLoadSaveImage() {
    /*
     * Loads the test file Test.ppm into the system and names it "testing". Then saves it as
     * NewTest.ppm. Finally, uses the ImageUtil readPPM() method to confirm the image was properly
     * loaded and saved into the new file with the same data.
     */
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.save("res/test/NewTest.ppm", "testing");

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
  public void testBrighten() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.brighten(10, "testing", "testing");
    model.save("res/test/NewTestBright.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestBright.ppm");

    assertEquals(array1[0][0].getRed() + 10, array2[0][0].getRed());
    assertEquals(array1[0][0].getGreen() + 10, array2[0][0].getGreen());
    assertEquals(array1[0][0].getBlue() + 10, array2[0][0].getBlue());

    assertEquals(array1[0][1].getRed() + 10, array2[0][1].getRed());
    assertEquals(array1[0][1].getGreen() + 10, array2[0][1].getGreen());
    assertEquals(array1[0][1].getBlue() + 10, array2[0][1].getBlue());

    assertEquals(array1[1][0].getRed() + 10, array2[1][0].getRed());
    assertEquals(array1[1][0].getGreen() + 10, array2[1][0].getGreen());
    assertEquals(array1[1][0].getBlue() + 10, array2[1][0].getBlue());

    assertEquals(array1[1][1].getRed() + 10, array2[1][1].getRed());
    assertEquals(array1[1][1].getGreen() + 10, array2[1][1].getGreen());
    assertEquals(array1[1][1].getBlue() + 10, array2[1][1].getBlue());
  }

  @Test
  public void testFlipHorizontal() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.flipHorizontal("testing", "testing");
    model.save("res/test/NewTestFlipH.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestFlipH.ppm");

    assertEquals(array1[0][0].getRed(), array2[0][1].getRed());
    assertEquals(array1[0][0].getGreen(), array2[0][1].getGreen());
    assertEquals(array1[0][0].getBlue(), array2[0][1].getBlue());

    assertEquals(array1[0][1].getRed(), array2[0][0].getRed());
    assertEquals(array1[0][1].getGreen(), array2[0][0].getGreen());
    assertEquals(array1[0][1].getBlue(), array2[0][0].getBlue());

    assertEquals(array1[1][0].getRed(), array2[1][1].getRed());
    assertEquals(array1[1][0].getGreen(), array2[1][1].getGreen());
    assertEquals(array1[1][0].getBlue(), array2[1][1].getBlue());

    assertEquals(array1[1][1].getRed(), array2[1][0].getRed());
    assertEquals(array1[1][1].getGreen(), array2[1][0].getGreen());
    assertEquals(array1[1][1].getBlue(), array2[1][0].getBlue());
  }

  @Test
  public void testFlipVertical() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.flipVertical("testing", "testing");
    model.save("res/test/NewTestFlipV.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestFlipV.ppm");

    assertEquals(array1[0][0].getRed(), array2[1][0].getRed());
    assertEquals(array1[0][0].getGreen(), array2[1][0].getGreen());
    assertEquals(array1[0][0].getBlue(), array2[1][0].getBlue());

    assertEquals(array1[0][1].getRed(), array2[1][1].getRed());
    assertEquals(array1[0][1].getGreen(), array2[1][1].getGreen());
    assertEquals(array1[0][1].getBlue(), array2[1][1].getBlue());

    assertEquals(array1[1][0].getRed(), array2[0][0].getRed());
    assertEquals(array1[1][0].getGreen(), array2[0][0].getGreen());
    assertEquals(array1[1][0].getBlue(), array2[0][0].getBlue());

    assertEquals(array1[1][1].getRed(), array2[0][1].getRed());
    assertEquals(array1[1][1].getGreen(), array2[0][1].getGreen());
    assertEquals(array1[1][1].getBlue(), array2[0][1].getBlue());
  }

  @Test
  public void testRedComponent() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.component(Component.RED, "testing", "testing");
    model.save("res/test/NewTestCompRed.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestCompRed.ppm");

    assertEquals(array1[0][0].getRed(), array2[0][0].getRed());
    assertEquals(array1[0][0].getRed(), array2[0][0].getGreen());
    assertEquals(array1[0][0].getRed(), array2[0][0].getBlue());

    assertEquals(array1[0][1].getRed(), array2[0][1].getRed());
    assertEquals(array1[0][1].getRed(), array2[0][1].getGreen());
    assertEquals(array1[0][1].getRed(), array2[0][1].getBlue());

    assertEquals(array1[1][0].getRed(), array2[1][0].getRed());
    assertEquals(array1[1][0].getRed(), array2[1][0].getGreen());
    assertEquals(array1[1][0].getRed(), array2[1][0].getBlue());

    assertEquals(array1[1][1].getRed(), array2[1][1].getRed());
    assertEquals(array1[1][1].getRed(), array2[1][1].getGreen());
    assertEquals(array1[1][1].getRed(), array2[1][1].getBlue());
  }

  @Test
  public void testGreenComponent() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.component(Component.GREEN, "testing", "testing");
    model.save("res/test/NewTestCompGreen.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestCompGreen.ppm");

    assertEquals(array1[0][0].getGreen(), array2[0][0].getRed());
    assertEquals(array1[0][0].getGreen(), array2[0][0].getGreen());
    assertEquals(array1[0][0].getGreen(), array2[0][0].getBlue());

    assertEquals(array1[0][1].getGreen(), array2[0][1].getRed());
    assertEquals(array1[0][1].getGreen(), array2[0][1].getGreen());
    assertEquals(array1[0][1].getGreen(), array2[0][1].getBlue());

    assertEquals(array1[1][0].getGreen(), array2[1][0].getRed());
    assertEquals(array1[1][0].getGreen(), array2[1][0].getGreen());
    assertEquals(array1[1][0].getGreen(), array2[1][0].getBlue());

    assertEquals(array1[1][1].getGreen(), array2[1][1].getRed());
    assertEquals(array1[1][1].getGreen(), array2[1][1].getGreen());
    assertEquals(array1[1][1].getGreen(), array2[1][1].getBlue());
  }

  @Test
  public void testBlueComponent() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.component(Component.BLUE, "testing", "testing");
    model.save("res/test/NewTestCompBlue.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestCompBlue.ppm");

    assertEquals(array1[0][0].getBlue(), array2[0][0].getRed());
    assertEquals(array1[0][0].getBlue(), array2[0][0].getGreen());
    assertEquals(array1[0][0].getBlue(), array2[0][0].getBlue());

    assertEquals(array1[0][1].getBlue(), array2[0][1].getRed());
    assertEquals(array1[0][1].getBlue(), array2[0][1].getGreen());
    assertEquals(array1[0][1].getBlue(), array2[0][1].getBlue());

    assertEquals(array1[1][0].getBlue(), array2[1][0].getRed());
    assertEquals(array1[1][0].getBlue(), array2[1][0].getGreen());
    assertEquals(array1[1][0].getBlue(), array2[1][0].getBlue());

    assertEquals(array1[1][1].getBlue(), array2[1][1].getRed());
    assertEquals(array1[1][1].getBlue(), array2[1][1].getGreen());
    assertEquals(array1[1][1].getBlue(), array2[1][1].getBlue());
  }

  @Test
  public void testValueComponent() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.component(Component.VALUE, "testing", "testing");
    model.save("res/test/NewTestCompValue.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestCompValue.ppm");

    int max1 = Math.max(Math.max(array1[0][0].getRed(), array1[0][0].getGreen()),
        array1[0][0].getBlue());
    assertEquals(max1, array2[0][0].getRed());
    assertEquals(max1, array2[0][0].getGreen());
    assertEquals(max1, array2[0][0].getBlue());

    int max2 = Math.max(Math.max(array1[0][1].getRed(), array1[0][1].getGreen()),
        array1[0][1].getBlue());
    assertEquals(max2, array2[0][1].getRed());
    assertEquals(max2, array2[0][1].getGreen());
    assertEquals(max2, array2[0][1].getBlue());

    int max3 = Math.max(Math.max(array1[1][0].getRed(), array1[1][0].getGreen()),
        array1[1][0].getBlue());
    assertEquals(max3, array2[1][0].getRed());
    assertEquals(max3, array2[1][0].getGreen());
    assertEquals(max3, array2[1][0].getBlue());

    int max4 = Math.max(Math.max(array1[1][1].getRed(), array1[1][1].getGreen()),
        array1[1][1].getBlue());
    assertEquals(max4, array2[1][1].getRed());
    assertEquals(max4, array2[1][1].getGreen());
    assertEquals(max4, array2[1][1].getBlue());
  }

  @Test
  public void testIntensityComponent() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.component(Component.INTENSITY, "testing", "testing");
    model.save("res/test/NewTestCompIntensity.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestCompIntensity.ppm");

    int avg1 = (int) Math.round((array1[0][0].getRed() + array1[0][0].getGreen()
        + array1[0][0].getBlue()) / 3.0);
    assertEquals(avg1, array2[0][0].getRed());
    assertEquals(avg1, array2[0][0].getGreen());
    assertEquals(avg1, array2[0][0].getBlue());

    int avg2 = (int) Math.round((array1[0][1].getRed() + array1[0][1].getGreen()
        + array1[0][1].getBlue()) / 3.0);
    assertEquals(avg2, array2[0][1].getRed());
    assertEquals(avg2, array2[0][1].getGreen());
    assertEquals(avg2, array2[0][1].getBlue());

    int avg3 = (int) Math.round((array1[1][0].getRed() + array1[1][0].getGreen()
        + array1[1][0].getBlue()) / 3.0);
    assertEquals(avg3, array2[1][0].getRed());
    assertEquals(avg3, array2[1][0].getGreen());
    assertEquals(avg3, array2[1][0].getBlue());

    int avg4 = (int) Math.round((array1[1][1].getRed() + array1[1][1].getGreen()
        + array1[1][1].getBlue()) / 3.0);
    assertEquals(avg4, array2[1][1].getRed());
    assertEquals(avg4, array2[1][1].getGreen());
    assertEquals(avg4, array2[1][1].getBlue());
  }

  @Test
  public void testLumaComponent() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.component(Component.LUMA, "testing", "testing");
    model.save("res/test/NewTestCompLuma.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestCompLuma.ppm");

    int val1 = (int) Math.round((array1[0][0].getRed() * 0.2126)
        + (array1[0][0].getGreen() * 0.7152) + (array1[0][0].getBlue() * 0.0722));
    assertEquals(val1, array2[0][0].getRed());
    assertEquals(val1, array2[0][0].getGreen());
    assertEquals(val1, array2[0][0].getBlue());

    int val2 = (int) Math.round((array1[0][1].getRed() * 0.2126)
        + (array1[0][1].getGreen() * 0.7152) + (array1[0][1].getBlue() * 0.0722));
    assertEquals(val2, array2[0][1].getRed());
    assertEquals(val2, array2[0][1].getGreen());
    assertEquals(val2, array2[0][1].getBlue());

    int val3 = (int) Math.round((array1[1][0].getRed() * 0.2126)
        + (array1[1][0].getGreen() * 0.7152) + (array1[1][0].getBlue() * 0.0722));
    assertEquals(val3, array2[1][0].getRed());
    assertEquals(val3, array2[1][0].getGreen());
    assertEquals(val3, array2[1][0].getBlue());

    int val4 = (int) Math.round((array1[1][1].getRed() * 0.2126)
        + (array1[1][1].getGreen() * 0.7152) + (array1[1][1].getBlue() * 0.0722));
    assertEquals(val4, array2[1][1].getRed());
    assertEquals(val4, array2[1][1].getGreen());
    assertEquals(val4, array2[1][1].getBlue());
  }

  @Test
  public void testBlur() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.blur("testing", "testing");
    model.save("res/test/NewTestBlur.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestBlur.ppm");

  }

  @Test
  public void testSharpen() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.sharpen("testing", "testing");
    model.save("res/test/NewTestSharpen.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestSharpen.ppm");

  }

  @Test
  public void testGreyscale() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.greyscale("testing", "testing");
    model.save("res/test/NewTestGreyscale.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestGreyscale.ppm");

    int val1 = (int) Math.round((array1[0][0].getRed() * 0.2126)
            + (array1[0][1].getGreen() * 0.7152) + (array1[0][2].getBlue() * 0.0722));
    assertEquals(val1, array2[0][0].getRed());
    assertEquals(val1, array2[0][1].getGreen());
    assertEquals(val1, array2[0][2].getBlue());

    int val2 = (int) Math.round((array1[1][0].getRed() * 0.2126)
            + (array1[1][2].getGreen() * 0.7152) + (array1[1][3].getBlue() * 0.0722));
    assertEquals(val2, array2[1][0].getRed());
    assertEquals(val2, array2[1][1].getGreen());
    assertEquals(val2, array2[1][2].getBlue());

    int val3 = (int) Math.round((array1[2][0].getRed() * 0.2126)
            + (array1[2][1].getGreen() * 0.7152) + (array1[2][2].getBlue() * 0.0722));
    assertEquals(val3, array2[2][0].getRed());
    assertEquals(val3, array2[2][1].getGreen());
    assertEquals(val3, array2[2][2].getBlue());

  }

  @Test
  public void testSepia() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "testing");
    model.sepia("testing", "testing");
    model.save("res/test/NewTestSepia.ppm", "testing");

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestSepia.ppm");



    int val1 = (int) Math.round((array1[0][0].getRed() * 0.393)
            + (array1[0][1].getGreen() * 0.769) + (array1[0][2].getBlue() * 0.189));
    assertEquals(val1, array2[0][0].getRed());
    assertEquals(val1, array2[0][1].getGreen());
    assertEquals(val1, array2[0][2].getBlue());

    int val2 = (int) Math.round((array1[1][0].getRed() * 0.349)
            + (array1[1][2].getGreen() * 0.686) + (array1[1][3].getBlue() * 0.168));
    assertEquals(val2, array2[1][0].getRed());
    assertEquals(val2, array2[1][1].getGreen());
    assertEquals(val2, array2[1][2].getBlue());

    int val3 = (int) Math.round((array1[2][0].getRed() * 0.272)
            + (array1[2][1].getGreen() * 0.534) + (array1[2][2].getBlue() * 0.131));
    assertEquals(val3, array2[2][0].getRed());
    assertEquals(val3, array2[2][1].getGreen());
    assertEquals(val3, array2[2][2].getBlue());

  }

  //tests if path or name is null
  @Test(expected = IllegalArgumentException.class)
  public void loadIllegalNull1() {
    ImageEditorModel model1 = new SimpleEditorModel();
    model1.loadImage("res/test/Test.ppm", null);
  }

  //tests if path or name is null
  @Test(expected = IllegalArgumentException.class)
  public void loadIllegalNull2() {
    ImageEditorModel model1 = new SimpleEditorModel();
    model1.loadImage(null, "test");
  }

  //tests if file doesn't exist
  @Test(expected = IllegalStateException.class)
  public void loadIllegal1() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Google.ppm", "google");
  }

  //tests if file is empty
  @Test(expected = IllegalStateException.class)
  public void loadIllegal2() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Empty.ppm", "Empty");
  }

  //tests if token isn't equal to P3
  @Test(expected = IllegalStateException.class)
  public void loadIllegal3() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/NoP3.ppm", "nop3");
  }

  //tests if path or name is null
  @Test
  public void saveIllegal1() {
    ImageEditorModel model1 = new SimpleEditorModel();
    try {
      model1.save("res/test/Test.ppm", null);
      fail();
    } catch (IllegalArgumentException ignored) {
      // Should not reach here
    }

    try {
      model1.save(null, "test");
      fail();
    } catch (IllegalArgumentException ignored) {
      // Should not reach here
    }
  }

  //tests if type, toImageName, or fromImageName is null
  @Test(expected = IllegalArgumentException.class)
  public void componentIllegal1() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "test");
    model.component(Component.LUMA, null, null);
  }

  //tests if image is not in hashmap
  @Test
  public void componentIllegal2() {
    ImageEditorModel model = new SimpleEditorModel();
    try {
      model.component(Component.RED, "Not", "Here");
      fail();
    } catch (IllegalStateException ignored) {
      // Should not reach here
    }

    try {
      model.component(Component.BLUE, "Google", "Gone");
      fail();
    } catch (IllegalStateException ignored) {
      // Should not reach here
    }
  }

  //tests if fromImageName or toImageName is null
  @Test
  public void flipHorizontalIllegal1() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "test");

    try {
      model.flipHorizontal(null, null);
      fail();
    } catch (IllegalArgumentException ignore) {
      // Should not reach here
    }

    try {
      model.flipHorizontal("test", null);
      fail();
    } catch (IllegalArgumentException ignore) {
      // Should not reach here
    }

    try {
      model.flipHorizontal(null, "test");
      fail();
    } catch (IllegalArgumentException ignore) {
      // Should not reach here
    }
  }

  //tests if image is not in hashmap
  @Test
  public void flipHorizontalIllegal2() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "test");

    try {
      model.component(Component.LUMA, "bob", "bob2");
      fail();
    } catch (IllegalStateException ignore) {
      // Should not reach here
    }

    try {
      model.flipHorizontal("abc", "def");
      fail();
    } catch (IllegalStateException ignore) {
      // Should not reach here
    }
  }

  //tests if fromImageName or toImageName is null
  @Test
  public void flipVerticalIllegal1() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "test");

    try {
      model.flipVertical(null, null);
      fail();
    } catch (IllegalArgumentException ignore) {
      // Should not reach here
    }

    try {
      model.flipVertical("test", null);
      fail();
    } catch (IllegalArgumentException ignore) {
      // Should not reach here
    }

    try {
      model.flipVertical(null, "test");
      fail();
    } catch (IllegalArgumentException ignore) {
      // Should not reach here
    }
  }

  //tests if image is not in hashmap
  @Test
  public void flipVerticalIllegal2() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "test");

    try {
      model.flipVertical("bob", "bob2");
      fail();
    } catch (IllegalStateException ignore) {
      // Should not reach here
    }

    try {
      model.flipVertical("abc", "def");
      fail();
    } catch (IllegalStateException ignore) {
      // Should not reach here
    }
  }

  //tests if fromImageName or toImageName is null
  @Test
  public void brighten1() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "test");

    try {
      model.brighten(10, null, null);
      fail();
    } catch (IllegalArgumentException ignore) {
      // Should not reach here
    }

    try {
      model.brighten(10, "test", null);
      fail();
    } catch (IllegalArgumentException ignore) {
      // Should not reach here
    }

    try {
      model.brighten(-10, null, "test");
      fail();
    } catch (IllegalArgumentException ignore) {
      // Should not reach here
    }
  }

  //test if image is not in hashmap
  @Test
  public void brighten2() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "test");

    try {
      model.brighten(10, "bob", "bob2");
      fail();
    } catch (IllegalStateException ignore) {
      // Should not reach here
    }

    try {
      model.brighten(100, "abc", "def");
      fail();
    } catch (IllegalStateException ignore) {
      // Should not reach here
    }
  }

  //tests if the image is not in hashmap
  @Test
  public void illegalBlur() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "test");
    try {
      model.blur("hello", "bye");
      fail();
    } catch (IllegalStateException ignore) {
      //Should not reach here
    }
  }

  //tests if the image is not in hashmap
  @Test
  public void illegalSharpen() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "test");
    //throws IllegalStateException
    try {
      model.sharpen("matt", "ritu");
      fail();
    } catch (IllegalStateException ignore) {
      //Should not reach here
    }
  }

  //tests if the image is not in hashmap
  @Test
  public void illegalGreyscale() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "test");

    try {
      model.greyscale("image1", "image2");
      fail();
    } catch (IllegalStateException ignore) {
      //Should not reach here
    }
  }

  //tests if the image is not in hashmap
  @Test
  public void illegalSepia() {
    ImageEditorModel model = new SimpleEditorModel();
    model.loadImage("res/test/Test.ppm", "test");

    try {
      model.sepia("picture1", "picture2");
      fail();
    } catch (IllegalStateException ignore) {
      //Should not reach here
    }
  }
}


