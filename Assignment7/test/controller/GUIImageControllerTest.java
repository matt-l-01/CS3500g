package controller;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import controller.command.Blue;
import controller.command.Brighten;
import controller.command.FlipHorizontal;
import controller.command.FlipVertical;
import controller.command.GaussianBlur;
import controller.command.Green;
import controller.command.Greyscale;
import controller.command.ImageCommand;
import controller.command.Intensity;
import controller.command.Luma;
import controller.command.Red;
import controller.command.Sepia;
import controller.command.Sharpen;
import controller.command.Value;
import model.ColorMatrix;
import model.ImageModel;
import model.Kernel;
import model.RasterImageModel;
import view.ImageGUIView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Simple test class for the GUIImageController
 * class.
 */
public class GUIImageControllerTest {
  private ImageProcessorFeatures controller;
  private MockView v;
  private MockManager m;

  private class MockView implements ImageGUIView {
    private StringBuilder log = new StringBuilder();

    private String getLog() {
      return this.log.toString();
    }

    @Override
    public void addFeatures(ImageProcessorFeatures features) {
      log.append("addFeatures\n");
    }

    @Override
    public void setDisplayImage(ImageModel image) {
      log.append("setDisplayImage ").append(image.getWidth()).append(" ").append(image.getHeight())
              .append(" ").append(image.getMaxRGB()).append("\n");
    }

    @Override
    public void renderMessage(String message) {
      log.append("renderMessage ").append(message).append("\n");
    }

    @Override
    public void refresh() {
      log.append("refresh\n");
    }
  }

  private class MockManager implements ImageManager {
    private StringBuilder log = new StringBuilder();

    private String getLog() {
      return this.log.toString();
    }

    @Override
    public void load(String filePath, String name, ImageFileFormat fileFormat)
            throws IOException, IllegalArgumentException {
      log.append("load ").append(filePath).append(" ").append(name).append(" ")
              .append(fileFormat).append("\n");
    }

    @Override
    public void save(String imageName, String filePath, ImageFileFormat fileFormat)
            throws IOException, IllegalArgumentException {
      log.append("save ").append(imageName).append(" ").append(filePath).append(" ")
              .append(fileFormat).append("\n");
    }

    @Override
    public Map<String, ImageModel> getStoredImages() {
      log.append("getStoredImages\n");
      Map<String, ImageModel> dummyMap = new HashMap<>();
      dummyMap.put("1", new RasterImageModel(1,1,1,new ArrayList<>()));
      dummyMap.put("2", new RasterImageModel(2,2,2,new ArrayList<>()));
      dummyMap.put("3", new RasterImageModel(3,3,3,new ArrayList<>()));

      return dummyMap;
    }

    @Override
    public Map<String, Function<String[], ImageCommand>> getKnownCommands() {
      log.append("getKnownCommands\n");
      Map<String, Function<String[], ImageCommand>> commands = new HashMap<>();
      commands.put("red-component",s -> new Red(this));
      commands.put("green-component",s -> new Green(this));
      commands.put("blue-component",s -> new Blue(this));
      commands.put("value-component",s -> new Value(this));
      commands.put("intensity-component",s -> new Intensity(this));
      commands.put("luma-component",s -> new Luma(this));
      commands.put("brighten",s -> new Brighten(s,this));
      commands.put("horizontal-flip",s -> new FlipHorizontal(this));
      commands.put("vertical-flip",s -> new FlipVertical(this));
      commands.put("blur",s -> new GaussianBlur(this));
      commands.put("sharpen",s -> new Sharpen(this));
      commands.put("greyscale",s -> new Greyscale(this));
      commands.put("sepia",s -> new Sepia(this));
      return commands; //have to actually store commands so the image manipulation methods work
    }

    @Override
    public void visualizeComponent(ImageModel.Component component,
                                   String imageName, String resultName)
            throws IllegalArgumentException, IllegalStateException {
      log.append("visualizeComponent ").append(component).append(" ").append(imageName).append(" ")
              .append(resultName).append("\n");
    }

    @Override
    public void flip(String direction, String imageName, String resultName)
            throws IllegalArgumentException, IllegalStateException {
      log.append("flip ").append(direction).append(" ").append(imageName).append(" ")
              .append(resultName).append("\n");
    }

    @Override
    public void brighten(int increment, String imageName, String resultName)
            throws IllegalArgumentException {
      log.append("brighten").append(" ").append(increment).append(" ").append(imageName)
              .append(" ").append(resultName).append("\n");
    }

    @Override
    public void filter(Kernel kernel, String imageName, String resultName)
            throws IllegalArgumentException {
      log.append("filter ").append(kernel.toString()).append(" ").append(imageName).append(" ")
              .append(resultName).append("\n");
    }

    @Override
    public void colorTransform(ColorMatrix matrix, String imageName, String resultName)
            throws IllegalArgumentException {
      log.append("colorTransform ").append(matrix.toString()).append(" ").append(imageName)
              .append(" ").append(resultName).append("\n");
    }

    @Override
    public void mosaic(int seeds, String imageName, String resultName) throws IllegalArgumentException {

    }
  }

  private class ExceptionMockManager extends MockManager {
    @Override
    public void load(String filePath, String name, ImageFileFormat fileFormat)
            throws IOException, IllegalArgumentException {
      super.load(filePath, name, fileFormat);
      throw new IOException();
    }

    @Override
    public void save(String imageName, String filePath, ImageFileFormat fileFormat)
            throws IOException, IllegalArgumentException {
      super.save(imageName, filePath, fileFormat);
      throw new IOException();
    }
  }

  // has a save method which looks up the given image before saving it and throws an exception
  // if the given image is not found, for controller save() testing
  private class AccurateSaveMockManager extends MockManager {
    @Override
    public void save(String imageName, String filePath, ImageFileFormat fileFormat)
            throws IOException, IllegalArgumentException {
      super.save(imageName, filePath, fileFormat);
      ImageModel image = this.getStoredImages().get(imageName);
      if (image == null) {
        throw new IllegalArgumentException("Image not found.");
      }
    }
  }

  // constructor exceptions
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullView() {
    new GUIImageController(null, new RasterImageManager());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullManager() {
    new GUIImageController(new MockView(), null);
  }

  // test load IOException handling
  @Test
  public void testLoadIOException() {
    v = new MockView();
    m = new ExceptionMockManager();
    controller = new GUIImageController(v, m);
    controller.loadImage("aaa.jpg");
    assertEquals("addFeatures\nrenderMessage Could not load file.\n", v.getLog());
    assertEquals("load aaa.jpg 1 jpg\n", m.getLog());
  }

  // test load with all file extensions
  @Test
  public void testLoadBadExtension() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.loadImage("bird.jfk");
    assertEquals("addFeatures\nrenderMessage Invalid file extension\n", v.getLog());
    assertEquals("", m.getLog()); // error state reached before manager method is called
  }

  // test no extension
  @Test
  public void testLoadNoExtension() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.loadImage("bird");
    assertEquals("addFeatures\nrenderMessage Invalid file name (no extension)\n",
            v.getLog());
    assertEquals("", m.getLog()); // error state reached before manager method is called
  }

  // test load with all file extensions
  // test load ppm
  @Test
  public void testLoadPPM() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.loadImage("bird.ppm");
    assertEquals("load bird.ppm 1 ppm\ngetStoredImages\n", m.getLog());
    assertTrue(v.getLog().contains("setDisplayImage") && v.getLog().contains("refresh\n"));
  }

  // test load png
  @Test
  public void testLoadPNG() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.loadImage("bird.png");
    assertEquals("load bird.png 1 png\ngetStoredImages\n", m.getLog());
    assertTrue(v.getLog().contains("setDisplayImage") && v.getLog().contains("refresh\n"));
  }

  // test load jpg
  @Test
  public void testLoadJPG() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.loadImage("bird.jpg");
    assertEquals("load bird.jpg 1 jpg\ngetStoredImages\n", m.getLog());
    assertTrue(v.getLog().contains("setDisplayImage") && v.getLog().contains("refresh\n"));
  }

  // test load jpeg
  @Test
  public void testLoadJPEG() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.loadImage("bird.jpeg");
    assertEquals("load bird.jpeg 1 jpeg\ngetStoredImages\n", m.getLog());
    assertTrue(v.getLog().contains("setDisplayImage") && v.getLog().contains("refresh\n"));
  }

  // test load bmp
  @Test
  public void testLoadBMP() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.loadImage("bird.bmp");
    assertEquals("load bird.bmp 1 bmp\ngetStoredImages\n", m.getLog());
    assertTrue(v.getLog().contains("setDisplayImage") && v.getLog().contains("refresh\n"));
  }

  // test save with no images loaded
  @Test
  public void testSaveNoImages() {
    v = new MockView();
    m = new AccurateSaveMockManager();
    controller = new GUIImageController(v, m);
    controller.saveImage("aaa.jpg");
    assertEquals("addFeatures\nrenderMessage Image not found.\n", v.getLog());
    assertEquals("save 0 aaa.jpg jpg\ngetStoredImages\n", m.getLog());
  }

  // test save with invalid extension
  @Test
  public void testSaveBadExtension() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.saveImage("aaa.jfk");
    assertEquals("addFeatures\nrenderMessage Invalid file extension\n", v.getLog());
    assertEquals("", m.getLog()); // throws exception before asking the model to save
  }

  // test save with missing extension
  @Test
  public void testSaveMissingExtension() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.saveImage("aaa");
    assertEquals("addFeatures\nrenderMessage Invalid file name (no extension)\n", v.getLog());
    assertEquals("", m.getLog()); // throws exception before asking the model to save
  }

  // test save IOException handling
  @Test
  public void testSaveIOException() {
    v = new MockView();
    m = new ExceptionMockManager();
    controller = new GUIImageController(v, m);
    controller.saveImage("aaa.jpg");
    assertEquals("addFeatures\nrenderMessage Could not save file.\n", v.getLog());
    assertEquals("save 0 aaa.jpg jpg\n", m.getLog());
  }

  // test save with all file extensions
  // test save ppm
  @Test
  public void testSavePPM() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.saveImage("bird.ppm");
    assertEquals("save 0 bird.ppm ppm\n", m.getLog());
    assertEquals("addFeatures\nrefresh\n", v.getLog());
  }

  // test save png
  @Test
  public void testSavePNG() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.saveImage("bird.png");
    assertEquals("save 0 bird.png png\n", m.getLog());
    assertEquals("addFeatures\nrefresh\n", v.getLog());
  }

  // test save jpg
  @Test
  public void testSaveJPG() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.saveImage("bird.jpg");
    assertEquals("save 0 bird.jpg jpg\n", m.getLog());
    assertEquals("addFeatures\nrefresh\n", v.getLog());
  }

  // test save jpeg
  @Test
  public void testSaveJPEG() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.saveImage("bird.jpeg");
    assertEquals("save 0 bird.jpeg jpeg\n", m.getLog());
    assertEquals("addFeatures\nrefresh\n", v.getLog());
  }

  // test save bmp
  @Test
  public void testSaveBMP() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.saveImage("bird.bmp");
    assertEquals("save 0 bird.bmp bmp\n", m.getLog());
    assertEquals("addFeatures\nrefresh\n", v.getLog());
  }

  // test visualize red
  @Test
  public void testVisualizeRed() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.visualizeRed();
    assertEquals("getKnownCommands\nvisualizeComponent RED 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test visualize green
  @Test
  public void testVisualizeGreen() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.visualizeGreen();
    assertEquals("getKnownCommands\nvisualizeComponent GREEN 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test visualize blue
  @Test
  public void testVisualizeBlue() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.visualizeBlue();
    assertEquals("getKnownCommands\nvisualizeComponent BLUE 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test visualize value
  @Test
  public void testVisualizeValue() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.visualizeValue();
    assertEquals("getKnownCommands\nvisualizeComponent VALUE 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test visualize intensity
  @Test
  public void testVisualizeIntensity() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.visualizeIntensity();
    assertEquals("getKnownCommands\nvisualizeComponent INTENSITY 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test visualize luma
  @Test
  public void testVisualizeLuma() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.visualizeLuma();
    assertEquals("getKnownCommands\nvisualizeComponent LUMA 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test greyscale
  @Test
  public void testGreyscale() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.greyscale();
    assertEquals("getKnownCommands\ncolorTransform [[0.2126, 0.7152, 0.0722], "
                    + "[0.2126, 0.7152, 0.0722], [0.2126, 0.7152, 0.0722]] 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test sepia
  @Test
  public void testSepia() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.sepia();
    assertEquals("getKnownCommands\ncolorTransform [[0.393, 0.769, 0.189], "
                    + "[0.349, 0.686, 0.168], [0.272, 0.534, 0.131]] 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test horizontal flip
  @Test
  public void testHorizFlip() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.flipHorizontal();
    assertEquals("getKnownCommands\nflip horizontal 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test vertical flip
  @Test
  public void testVertFlip() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.flipVertical();
    assertEquals("getKnownCommands\nflip vertical 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test blur
  @Test
  public void testBlur() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.blur();
    assertEquals("getKnownCommands\nfilter [[0.0625, 0.125, 0.0625], "
                    + "[0.125, 0.25, 0.125], [0.0625, 0.125, 0.0625]] 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test sharpen
  @Test
  public void testSharpen() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.sharpen();
    assertEquals("getKnownCommands\nfilter [[-0.125, -0.125, -0.125, -0.125, -0.125], "
                    + "[-0.125, 0.25, 0.25, 0.25, -0.125], [-0.125, 0.25, 1.0, 0.25, -0.125], "
                    + "[-0.125, 0.25, 0.25, 0.25, -0.125], "
                    + "[-0.125, -0.125, -0.125, -0.125, -0.125]] 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test brighten
  @Test
  public void testBrighten() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.brighten("10");
    assertEquals("getKnownCommands\nbrighten 10 0 1\ngetStoredImages\n",
            m.getLog());
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test brighten input error
  @Test
  public void testBrightenInvalidIncrement() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.brighten("aa");
    assertEquals("", m.getLog()); // will not call the manager for invalid input
    assertEquals("addFeatures\nrenderMessage Please enter an integer increment.\n",
            v.getLog());
  }

  // test brighten no input error
  @Test
  public void testBrightenMissingIncrement() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.brighten("");
    assertEquals("", m.getLog()); // will not call the manager for invalid input
    assertEquals("addFeatures\nrenderMessage Please enter an integer increment.\n",
            v.getLog());
  }

  // test darken
  @Test
  public void testDarken() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.darken("10");
    assertEquals("getKnownCommands\nbrighten -10 0 1\ngetStoredImages\n",
            m.getLog()); // brighten by negative inputted value
    assertEquals("addFeatures\nsetDisplayImage 1 1 1\nrefresh\n", v.getLog());
  }

  // test darken input error
  @Test
  public void testDarkenInvalidIncrement() {
    v = new MockView();
    m = new MockManager();
    controller = new GUIImageController(v, m);
    controller.darken("aa");
    assertEquals("", m.getLog()); // will not call the manager for invalid input
    assertEquals("addFeatures\nrenderMessage Please enter an integer increment.\n",
            v.getLog());
  }

}