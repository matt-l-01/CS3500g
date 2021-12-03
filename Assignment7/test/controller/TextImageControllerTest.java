package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import controller.command.GaussianBlur;
import controller.command.Greyscale;
import controller.command.Mosaic;
import controller.command.Sepia;
import controller.command.Sharpen;
import model.ColorMatrix;
import model.ImageModel;
import model.Kernel;
import controller.command.Blue;
import controller.command.Brighten;
import controller.command.FlipHorizontal;
import controller.command.FlipVertical;
import controller.command.Green;
import controller.command.ImageCommand;
import controller.command.Intensity;
import controller.command.Luma;
import controller.command.Red;
import controller.command.Value;
import view.ImageTextView;
import view.SimpleImageTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Holds tests for methods of the TextImageController class.
 */
public class TextImageControllerTest {
  private ImageManager manager;
  private Appendable output;
  private ImageTextView view;
  private static final String MENU_MESSAGE =
          "Supported commands:\n"
                  + "- menu (display the menu of supported commands)\n"
                  + "- load file-path name "
                  + "(loads the file at the file path and refer to it by the given name)\n"
                  + "- save file-path image-name"
                  + "(save the image with the given name to the specified file path,"
                  + " including the file name)\n"
                  + "- red-component image-name result-name "
                  + "(create a greyscale image using the red component of the given image, "
                  + "and refer to the result by the given result name)\n"
                  + "- green-component image-name result-name "
                  + "(create a greyscale image using the green component of the given image, "
                  + "and refer to the result by the given result name)\n"
                  + "- blue-component image-name result-name "
                  + "(create a greyscale image using the blue component of the given image, "
                  + "and refer to the result by the given result name)\n"
                  + "- value-component image-name result-name "
                  + "(create a greyscale image using the value component of the given image, "
                  + "and refer to the result by the given result name)\n"
                  + "- intensity-component image-name result-name "
                  + "(create a greyscale image using the intensity component of the given image, "
                  + "and refer to the result by the given result name)\n"
                  + "- luma-component image-name result-name "
                  + "(create a greyscale image using the luma component of the given image, "
                  + "and refer to the result by the given result name)\n"
                  + "- horizontal-flip image-name result-name "
                  + "(flip an image horizontally to create a new image, "
                  + "and refer to the result by the given result name)\n"
                  + "- vertical-flip image-name result-name "
                  + "(flip an image vertically to create a new image, "
                  + "and refer to the result by the given result name)\n"
                  + "- brighten increment image-name result-name "
                  + "(brighten an image by the given increment if positive, or darken if negative, "
                  + "and refer to the result by the given result name)\n"
                  +"- mosaic num-seeds source-image-name dest-image-name "
                  +"(mosaic an image by the given amount of seeds"
                  +"and refer to the result by the given result name)\n"
                  + "- blur image-name result-name "
                  + "(apply a Gaussian blur to an image and refer to the result "
                  + "by the given result name)\n"
                  + "- sharpen image-name result-name "
                  + "(sharpen an image and refer to the result by the given result name)\n"
                  + "- greyscale image-name result-name "
                  + "(create a greyscale version of an image and refer to the result "
                  + "by the given result name)\n"
                  + "- sepia image-name result-name "
                  + "(create a sepia-toned version of and image and refer to the result "
                  + "by the given result name)\n"
                  + "- q or quit (end the program)\n";

  /**
   * A manager that keeps a log of all method calls and
   * arguments to test that go() communicates to the manager,
   * and from there the model, correctly.
   */
  public static final class ManagerLogMock implements ImageManager {
    private final StringBuilder log = new StringBuilder();

    public String getLog() {
      return log.toString();
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
      return new HashMap<>();
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
      commands.put("mosaic", s -> new Mosaic(s,this));
      return commands;
      // needs to have a list of the commands so go() tests will be able to process inputs
    }

    @Override
    public void visualizeComponent(ImageModel.Component component, String imageName,
                                   String resultName)
            throws IllegalArgumentException, IllegalStateException {
      log.append(component).append(" ").append(imageName).append(" ")
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
      log.append("brighten ").append(Integer.toString(increment)).append(" ")
              .append(imageName).append(" ").append(resultName).append("\n");
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
      log.append("brighten ").append(Integer.toString(seeds)).append(" ")
              .append(imageName).append(" ").append(resultName).append("\n");
    }

  }

  /**
   * A Readable that throws IOExceptions for all methods
   * to test that go() handles IOExceptions correctly.
   */
  private static class ReadableMock implements Readable, AutoCloseable {


    @Override
    public void close() throws Exception {
      throw new IOException();
    }

    @Override
    public int read(CharBuffer cb) throws IOException {
      throw new IOException();
    }
  }

  /**
   * A view that throws IOExceptions for all methods
   * to test that go() handles IOExceptions correctly.
   */
  private static class ViewExceptionMock implements ImageTextView {

    @Override
    public void renderMessage(String message) throws IOException {
      throw new IOException();
    }
  }


  @Before
  public void setUp() {
    manager = new RasterImageManager(); // default model
    output = new StringBuilder();
    view = new SimpleImageTextView(output);
  }

  // no observers, so can't test constructor directly, just exceptions.
  // view/model communication tests ensure that constructor initializes fields correctly.

  /**
   * Tests that the constructor throws an IllegalArgumentException
   * if the readable provided is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionReadable() {
    new TextImageController(null, manager, view);
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException
   * if the manager provided is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionModel() {
    new TextImageController(new InputStreamReader(System.in),null, view);
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException
   * if the view provided is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionView() {
    new TextImageController(new InputStreamReader(System.in), manager, null);
  }

  /**
   * Tests that the default constructor throws an
   * IllegalArgumentException if the manager provided is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDefaultConstructorException() {
    new TextImageController(null);
  }

  /**
   * Tests that the controller handles IOExceptions
   * from reading input correctly.
   */
  @Test
  public void testRunReadException() {
    Readable input = new ReadableMock();
    ImageController controller1 =
            new TextImageController(input, manager, view);
    controller1.runProgram();
    assertEquals("Welcome to this simple image processing program!\n"
            + "Enter a command, or type \"menu\" to see a list of supported commands.\n"
            + "Program ended. Thank you for using this program!\n", output.toString());
  } // scanner catches the readable IOException and interprets the input as closed,
  // so it never enters the loop

  /**
   * Tests that the controller handles IOExceptions
   * from printing to the view correctly.
   */
  @Test(expected = IllegalStateException.class)
  public void testRunWriteException() {
    ImageTextView view1 = new ViewExceptionMock();
    ImageController controller1 =
            new TextImageController(new StringReader("quit"), manager, view1);
    controller1.runProgram();
  }

  /**
   * Test that runProgram() behaves as expected when the
   * program is quit immediately.
   */
  @Test
  public void testQuitImmediate() {
    Readable input = new StringReader("quit");
    ImageController controller = new TextImageController(input, manager, view);
    assertEquals("", output.toString());
    controller.runProgram();
    assertEquals("Welcome to this simple image processing program!\n"
            + "Enter a command, or type \"menu\" to see a list of supported commands.\n"
            + "Program ended. Thank you for using this program!\n", output.toString());
  } // no sense in writing a view mock that keeps a log when output is just a log

  /**
   * Test that runProgram() responds to the menu command
   * by printing the menu to the view.
   */
  @Test
  public void testMenu() {
    Readable input = new StringReader("menu\nq");
    ImageController controller = new TextImageController(input, manager, view);
    assertEquals("", output.toString());
    controller.runProgram();
    assertEquals("Welcome to this simple image processing program!\n"
            + "Enter a command, or type \"menu\" to see a list of supported commands.\n"
            + MENU_MESSAGE
            + "Program ended. Thank you for using this program!\n", output.toString());
  }

  /**
   * Test that runProgram() responds to the load command by calling
   * the correct model's load method with the correct arguments.
   */
  @Test
  public void testLoad() {
    String filePath = "test\\model\\TestInputFiles\\test1.ppm";
    Readable input = new StringReader("load " + filePath + " test1\nq");
    ManagerLogMock manager1 = new ManagerLogMock();
    ImageController controller1 = new TextImageController(input, manager1, view);
    controller1.runProgram();
    assertEquals("load " + filePath + " test1 ppm\n", manager1.getLog());
  }

  /**
   * Test that runProgram() responds to the save command by calling
   * the model's save method with the correct arguments.
   */
  @Test
  public void testSave() {
    String filePath = "test\\model\\TestInputFiles\\output1.ppm";
    Readable input = new StringReader("save " + filePath + " test1\nq");
    ManagerLogMock manager1 = new ManagerLogMock();
    ImageController controller1 = new TextImageController(input, manager1, view);
    controller1.runProgram();
    assertEquals("save test1 " + filePath + " ppm\n", manager1.getLog());
  }

  /**
   * Test that runProgram() responds to the red-component command by calling
   * the model's visualize method with the correct arguments.
   */
  @Test
  public void testRed() {
    Readable input = new StringReader("red-component image1 image1-red\nq");
    ManagerLogMock manager1 = new ManagerLogMock();
    ImageController controller1 = new TextImageController(input, manager1, view);
    controller1.runProgram();
    assertEquals("getKnownCommands\nRED image1 image1-red\n",
            manager1.getLog()); // ask the model to look up the command and then call that method
  }

  /**
   * Test that runProgram() responds to the green-component command by calling
   * the model's visualize method with the correct arguments.
   */
  @Test
  public void testGreen() {
    Readable input = new StringReader("green-component image1 image1-green\nq");
    ManagerLogMock manager1 = new ManagerLogMock();
    ImageController controller1 = new TextImageController(input, manager1, view);
    controller1.runProgram();
    assertEquals("getKnownCommands\nGREEN image1 image1-green\n",
            manager1.getLog());
  }

  /**
   * Test that runProgram() responds to the blue-component command by calling
   * the model's visualize method with the correct arguments.
   */
  @Test
  public void testBlue() {
    Readable input = new StringReader("blue-component image1 image1-blue\nq");
    ManagerLogMock manager1 = new ManagerLogMock();
    ImageController controller1 = new TextImageController(input, manager1, view);
    controller1.runProgram();
    assertEquals("getKnownCommands\nBLUE image1 image1-blue\n",
            manager1.getLog());
  }

  /**
   * Test that runProgram() responds to the intensity-component command by calling
   * the model's visualize method with the correct arguments.
   */
  @Test
  public void testIntensity() {
    Readable input = new StringReader("intensity-component image1 image1-intensity\nq");
    ManagerLogMock manager1 = new ManagerLogMock();
    ImageController controller1 = new TextImageController(input, manager1, view);
    controller1.runProgram();
    assertEquals("getKnownCommands\nINTENSITY image1 image1-intensity\n",
            manager1.getLog());
  }

  /**
   * Test that runProgram() responds to the value-component command by calling
   * the model's visualize method with the correct arguments.
   */
  @Test
  public void testValue() {
    Readable input = new StringReader("value-component image1 image1-value\nq");
    ManagerLogMock manager1 = new ManagerLogMock();
    ImageController controller1 = new TextImageController(input, manager1, view);
    controller1.runProgram();
    assertEquals("getKnownCommands\nVALUE image1 image1-value\n",
            manager1.getLog());
  }

  /**
   * Test that runProgram() responds to the luma-component command by calling
   * the model's visualize method with the correct arguments.
   */
  @Test
  public void testLuma() {
    Readable input = new StringReader("luma-component image1 image1-luma\nq");
    ManagerLogMock manager1 = new ManagerLogMock();
    ImageController controller1 = new TextImageController(input, manager1, view);
    controller1.runProgram();
    assertEquals("getKnownCommands\nLUMA image1 image1-luma\n",
            manager1.getLog());
  }

  /**
   * Test that runProgram() responds to the horizontal-flip command by calling
   * the model's flip method with the correct arguments.
   */
  @Test
  public void testFlipHorizontal() {
    Readable input = new StringReader("horizontal-flip image1 image1-hflip\nq");
    ManagerLogMock manager1 = new ManagerLogMock();
    ImageController controller1 = new TextImageController(input, manager1, view);
    controller1.runProgram();
    assertEquals("getKnownCommands\nflip horizontal image1 image1-hflip\n",
            manager1.getLog());
  }

  /**
   * Test that runProgram() responds to the vertical-flip command by calling
   * the model's flip method with the correct arguments.
   */
  @Test
  public void testFlipVertical() {
    Readable input = new StringReader("vertical-flip image1 image1-vflip\nq");
    ManagerLogMock manager1 = new ManagerLogMock();
    ImageController controller1 = new TextImageController(input, manager1, view);
    controller1.runProgram();
    assertEquals("getKnownCommands\nflip vertical image1 image1-vflip\n",
            manager1.getLog());
  }

  /**
   * Test that runProgram() responds to the brighten command by calling
   * the model's brighten method with the correct arguments.
   */
  @Test
  public void testBrighten() {
    Readable input = new StringReader("brighten -10 image1 image1-darker\nq");
    ManagerLogMock manager1 = new ManagerLogMock();
    ImageController controller1 = new TextImageController(input, manager1, view);
    controller1.runProgram();
    assertEquals("getKnownCommands\nbrighten -10 image1 image1-darker\n",
            manager1.getLog());
  }

  /**
   * Tests that runProgram() will reject invalid commands
   * without crashing.
   */
  @Test
  public void testCommandNotRecognized() {
    Readable input = new StringReader("red-compnent q");
    ManagerLogMock manager1 = new ManagerLogMock();
    ImageController controller1 = new TextImageController(input, manager1, view);
    controller1.runProgram();
    assertEquals("getKnownCommands\n", manager1.getLog());
    assertTrue(output.toString().endsWith(
            "Command not recognized.\nProgram ended. Thank you for using this program!\n"));
  }

  /**
   * Tests whether runProgram() gives the user the correct error message
   * when they attempt to load from an invalid file path.
   */
  @Test
  public void testLoadInvalidPath() {
    Readable input = new StringReader("load test\\badFolder\\image.ppm image1\nq");
    ImageController controller = new TextImageController(input, manager, view);
    controller.runProgram();
    assertTrue(output.toString().endsWith(
            "Could not load file.\nProgram ended. Thank you for using this program!\n"));
  }

  /**
   * Tests whether runProgram() gives the user the correct error message
   * when they attempt to save with an invalid image name.
   */
  @Test
  public void testSaveInvalidName() {
    Readable input = new StringReader("save image1.ppm image1\nq");
    ImageController controller = new TextImageController(input, manager, view);
    controller.runProgram();
    assertTrue(output.toString().endsWith(
            "Image not found.\nProgram ended. Thank you for using this program!\n"));
  }

  /**
   * Tests whether runProgram() gives the user the correct error message
   * when they attempt to manipulate an image using an invalid image name.
   */
  @Test
  public void testCommandInvalidName() {
    Readable input = new StringReader("luma-component image1 image1-red\n"
            + "vertical-flip image1-red image1-red-vertical\n"
            + "brighten -20 image1-red image1-red-dark\nquit");
    ImageController controller = new TextImageController(input, manager, view);
    controller.runProgram();
    assertTrue(output.toString().endsWith("Image name not found.\n"
            + "Image name not found.\nImage name not found.\n"
            + "Program ended. Thank you for using this program!\n"));
  }

  /**
   * Tests whether runProgram() gives the user the correct error message
   * when they attempt to save a file without entering a file path.
   */
  @Test
  public void testSaveNoFilePath() {
    Readable input = new StringReader("save image1\nq");
    ImageController controller = new TextImageController(input, manager, view);
    controller.runProgram();
    assertTrue(output.toString().endsWith(
            "Insufficient arguments for the given command.\n"
            + "Program ended. Thank you for using this program!\n"));
  }

  /**
   * Tests whether runProgram() gives the user the correct error message
   * when they attempt to load a file without entering a name.
   */
  @Test
  public void testLoadNoName() {
    Readable input = new StringReader("load image1.ppm\nq");
    ImageController controller = new TextImageController(input, manager, view);
    controller.runProgram();
    assertTrue(output.toString().endsWith(
            "Insufficient arguments for the given command.\n"
            + "Program ended. Thank you for using this program!\n"));
  }

  /**
   * Tests whether runProgram() gives the user the correct error message
   * when they attempt to manipulate an image without entering enough arguments.
   */
  @Test
  public void testCommandMissingArgument() {
    Readable input = new StringReader("green-component image1\nhorizontal-flip \n" +
            "brighten 10 image1\nq");
    ImageController controller = new TextImageController(input, manager, view);
    controller.runProgram();
    assertTrue(output.toString().endsWith(
            "Insufficient arguments for the given command.\n"
            + "Insufficient arguments for the given command.\n"
            + "Insufficient arguments for the given command.\n"
            + "Program ended. Thank you for using this program!\n"));
  }

  /**
   * Tests whether runProgram() gives the user the correct error message
   * when they attempt to brighten an image without entering a valid increment.
   */
  @Test
  public void testBrightenInvalidIncrement() {
    Readable input = new StringReader("brighten some image1 image1-brighter\n");
    ImageController controller = new TextImageController(input, manager, view);
    controller.runProgram();
    assertTrue(output.toString().endsWith(
            "Must specify an integer increment to brighten by.\n"
            + "Program ended. Thank you for using this program!\n"));
  }

  /**
   * Tests whether runProgram() behaves as expected
   * when the user fails to enter any command name.
   */
  @Test
  public void testMissingCommandName() {
    Readable input = new StringReader("    \nq");
    ImageController controller = new TextImageController(input, manager, view);
    controller.runProgram();
    assertEquals("Welcome to this simple image processing program!\n"
            + "Enter a command, or type \"menu\" to see a list of supported commands.\n"
            + "Program ended. Thank you for using this program!\n", output.toString());
  }


  /**
   * Tests that the program will accept valid input after the user enters invalid input.
   */
  @Test
  public void testInputCorrection() {
    Readable input = new StringReader("load test\\model\\TestInputFlies\\test1.ppm test1\n"
            + "load test\\model\\TestInputFiles\\test1.ppm test1\n"
            + "visualize-red test1 test1red\n"
            + "red-component test1 test1red\n"
            + "save test1red\n"
            + "save test\\model\\TestInputFiles\\test1red.ppm test1red\n"
            + "quit");
    ImageController controller = new TextImageController(input, manager, view);
    controller.runProgram();
    assertTrue(output.toString().contains("Could not load file.\nFile loaded successfully.\n"
            + "Command not recognized.\n"
            + "Insufficient arguments for the given command.\n"
            + "File saved successfully.\nProgram ended."));
    File result = new File(".\\test\\model\\TestInputFiles\\test1red.ppm");
    assertTrue(result.exists());
  }

  /**
   * Tests that the controller can successfully parse a script file.
   */
  @Test
  public void testScriptParsing() throws FileNotFoundException {
    Readable input = new FileReader("res\\ExampleScript.txt");
    ManagerLogMock m = new ManagerLogMock();
    ImageController controller = new TextImageController(input, m, view);
    controller.runProgram();
    String result = "load bird.jpg bird jpg\ngetKnownCommands\nRED bird bird-red\n"
            + "getKnownCommands\nGREEN bird bird-green\ngetKnownCommands\nBLUE bird bird-blue\n"
            + "getKnownCommands\nVALUE bird bird-value\ngetKnownCommands\n"
            + "INTENSITY bird bird-intensity\ngetKnownCommands\nLUMA bird bird-luma\n"
            + "getKnownCommands\nbrighten 50 bird bird-brighten\ngetKnownCommands\n"
            + "brighten -50 bird bird-darken\ngetKnownCommands\nflip horizontal bird bird-hflip\n"
            + "getKnownCommands\nflip vertical bird bird-vflip\ngetKnownCommands\n"
            + "colorTransform [[0.2126, 0.7152, 0.0722], [0.2126, 0.7152, 0.0722], "
            + "[0.2126, 0.7152, 0.0722]] bird bird-greyscale\n"
            + "getKnownCommands\ncolorTransform [[0.393, 0.769, 0.189], [0.349, 0.686, 0.168], "
            + "[0.272, 0.534, 0.131]] bird bird-sepia\n"
            + "getKnownCommands\nfilter [[0.0625, 0.125, 0.0625], [0.125, 0.25, 0.125], "
            + "[0.0625, 0.125, 0.0625]] bird bird-blur\n"
            + "getKnownCommands\nfilter [[0.0625, 0.125, 0.0625], [0.125, 0.25, 0.125], "
            + "[0.0625, 0.125, 0.0625]] bird-blur bird-blur\n"
            + "getKnownCommands\nfilter [[-0.125, -0.125, -0.125, -0.125, -0.125], "
            + "[-0.125, 0.25, 0.25, 0.25, -0.125], [-0.125, 0.25, 1.0, 0.25, -0.125], "
            + "[-0.125, 0.25, 0.25, 0.25, -0.125], [-0.125, -0.125, -0.125, -0.125, -0.125]] "
            + "bird bird-sharpen\ngetKnownCommands\n"
            + "filter [[-0.125, -0.125, -0.125, -0.125, -0.125], "
            + "[-0.125, 0.25, 0.25, 0.25, -0.125], "
            + "[-0.125, 0.25, 1.0, 0.25, -0.125], [-0.125, 0.25, 0.25, 0.25, -0.125], "
            + "[-0.125, -0.125, -0.125, -0.125, -0.125]] bird-sharpen bird-sharpen\n"
            + "save bird-greyscale bird-greyscale.png png\nsave bird-sepia bird-sepia.png png\n"
            + "save bird-blur bird-blur.png png\nsave bird-sharpen bird-sharpen.png png\n";
    assertEquals(result, m.getLog());
  }
}