package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.function.Function;

import controller.command.ImageCommand;
import controller.ImageManager.ImageFileFormat;
import view.ImageTextView;
import view.SimpleImageTextView;

/**
 * Represents a controller for a simple text-based image processing program.
 */
public class TextImageController extends AbstractImageController implements ImageController {
  private Readable input;
  private ImageTextView view;

  /**
   * Constructs a controller for the given model that reads from the specified input
   * and displays to the specified view.
   *
   * @param in    the input to read from
   * @param manager the image manager with the file I/O and storing operations used by this program
   * @param view  the view to display to
   * @throws IllegalArgumentException if the arguments are null
   */
  public TextImageController(Readable in, ImageManager manager, ImageTextView view)
          throws IllegalArgumentException {
    super(manager);
    if (in == null || view == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    this.input = in;
    this.view = view;
  }

  /**
   * Constructs a controller for the given model that by default
   * reads from and prints to the console.
   *
   * @param manager the model representing the operations of the program
   * @throws IllegalArgumentException if the model is null
   */
  public TextImageController(ImageManager manager) throws IllegalArgumentException {
    this(new InputStreamReader(System.in), manager, new SimpleImageTextView());
  }

  /**
   * The sequence of operations to run a simple image processing program.
   *
   * @throws IllegalStateException if any errors occur reading input or writing to output
   */
  @Override
  public void runProgram() throws IllegalStateException {
    try {
      view.renderMessage("Welcome to this simple image processing program!\n"
              + "Enter a command, or type \"menu\" to see a list of supported commands.\n");
      Scanner scan = new Scanner(this.input);
      while (scan.hasNext()) {
        String in = scan.nextLine();
        String[] args = in.split(" "); // split line at spaces to get arguments
        String command;
        try {
          command = args[0];
        } catch (IndexOutOfBoundsException e) {
          continue; // do not process inputs unless there is at least one argument
        }

        try {
          // handle the four non-image manipulation commands separately,
          // as they do not fit in the image command interface and we are less likely to add more
          if (command.equalsIgnoreCase("q") || command.equalsIgnoreCase("quit")) {
            break; // exit loop and print exit message

          } else if (command.equalsIgnoreCase("menu")) {
            this.printMenu(); // display supported commands

          } else if (command.equalsIgnoreCase("save")) {
            this.handleSave(args);

          } else if (command.equalsIgnoreCase("load")) {
            this.handleLoad(args);

          } else {
            // handle image manipulation commands via command design pattern-style lookup
            Function<String[], ImageCommand> cmd =
                    manager.getKnownCommands().getOrDefault(command, null);
            ImageCommand c = this.getCommand(cmd, args);
            if (c == null) { // if a command could not be retrieved,
              continue; // go to the next iteration of the loop to get new inputs
            }

            String imageName = this.getImageNames(args)[0];
            String resultName = this.getImageNames(args)[1];

            try {
              c.execute(imageName, resultName); // throws exception if names are null/not found
            } catch (IllegalArgumentException e) {
              this.view.renderMessage("Image name not found.\n");
            }
          }
        } catch (IndexOutOfBoundsException e) { // args[] will throw this if index is out of bounds
          view.renderMessage("Insufficient arguments for the given command.\n");
        }
      }

      this.view.renderMessage("Program ended. Thank you for using this program!\n");

    } catch (IOException e) {
      throw new IllegalStateException("Error reading input or writing output.");
    }
  }

  /**
   * Prints the list of supported commands to the view.
   */
  private void printMenu() throws IOException {
    this.view.renderMessage("Supported commands:\n" +
            "- menu (display the menu of supported commands)\n" +
            "- load file-path name " +
            "(loads the file at the file path and refer to it by the given name)\n" +
            "- save file-path image-name" +
            "(save the image with the given name to the specified file path," +
            " including the file name)\n" +
            "- red-component image-name result-name " +
            "(create a greyscale image using the red component of the given image, " +
            "and refer to the result by the given result name)\n" +
            "- green-component image-name result-name " +
            "(create a greyscale image using the green component of the given image, " +
            "and refer to the result by the given result name)\n" +
            "- blue-component image-name result-name " +
            "(create a greyscale image using the blue component of the given image, " +
            "and refer to the result by the given result name)\n" +
            "- value-component image-name result-name " +
            "(create a greyscale image using the value component of the given image, " +
            "and refer to the result by the given result name)\n" +
            "- intensity-component image-name result-name " +
            "(create a greyscale image using the intensity component of the given image, " +
            "and refer to the result by the given result name)\n" +
            "- luma-component image-name result-name " +
            "(create a greyscale image using the luma component of the given image, " +
            "and refer to the result by the given result name)\n" +
            "- horizontal-flip image-name result-name " +
            "(flip an image horizontally to create a new image, " +
            "and refer to the result by the given result name)\n" +
            "- vertical-flip image-name result-name " +
            "(flip an image vertically to create a new image, " +
            "and refer to the result by the given result name)\n" +
            "- brighten increment image-name result-name " +
            "(brighten an image by the given increment if positive, or darken if negative, " +
            "and refer to the result by the given result name)\n" +
            "- blur image-name result-name " +
            "(apply a Gaussian blur to an image and refer to the result " +
            "by the given result name)\n" +
            "- sharpen image-name result-name " +
            "(sharpen an image and refer to the result by the given result name)\n" +
            "- greyscale image-name result-name " +
            "(create a greyscale version of an image and refer to the result " +
            "by the given result name)\n" +
            "- sepia image-name result-name " +
            "(create a sepia-toned version of and image and refer to the result " +
            "by the given result name)\n" +
            "- q or quit (end the program)\n");
  }

  /**
   * Returns the ImageFileFormat of the provided image.
   *
   * @param filePath the path to find the extension of
   * @return the file extension
   * @throws IOException if extension is missing or not supported/invalid
   */
  protected ImageFileFormat getFileFormat(String filePath) throws IOException {
    String[] splitPath = filePath.split("\\.");
    String extension;
    if (splitPath.length == 2) {
      extension = splitPath[1];
    } else {
      throw new IOException("Invalid file name (no extension)");
    }
    switch (extension) {
      case "bmp":
        return ImageFileFormat.BMP;
      case "png":
        return ImageFileFormat.PNG;
      case "jpeg":
        return ImageFileFormat.JPEG;
      case "jpg":
        return ImageFileFormat.JPG;
      case "ppm":
        return ImageFileFormat.PPM;
      default:
        throw new IOException("Invalid file extension");
    }
  }

  /**
   * Tells the model to save the image, prints an error if unable to do so.
   */
  private void handleSave(String[] args) throws IOException {
    String filePath = args[1];
    String imageName = args[2];
    if ((imageName != null) && (filePath != null)) {
      try {
        ImageFileFormat ext = getFileFormat(filePath);
        manager.save(imageName, filePath, ext);
        view.renderMessage("File saved successfully.\n");
      } catch (IllegalStateException e) { // thrown by save if name isn't in stored images
        view.renderMessage("Image not found.\n");
      }
    } else {
      view.renderMessage("Could not save file.\n");
    } // wait for more input rather than throwing an exception and crashing
  }

  /**
   * Tells the model to load the image, prints an error if unable to do so.
   */
  private void handleLoad(String[] args) throws IOException {
    String filePath = args[1];
    String name = args[2];
    if ((filePath != null) && (name != null)) {
      try {
        ImageFileFormat ext = getFileFormat(filePath);
        manager.load(filePath, name, ext);
        view.renderMessage("File loaded successfully.\n");
      } catch (IOException e) {
        view.renderMessage("Could not load file.\n");
      } // don't crash the program over a file path typo. get a new set of inputs
    }
  }

  /**
   * Returns an ImageCommand object if one can be found, null if not.
   */
  private ImageCommand getCommand(Function<String[], ImageCommand> cmd, String[] args)
          throws IOException {
    ImageCommand c;
    if (cmd == null) {
      this.view.renderMessage("Command not recognized.\n");
      c = null;
    } else {
      try {
        c = cmd.apply(args);
        // if the command needs more arguments in order to be created,
        // it can look for them in what the user provided
      } catch (IllegalArgumentException e) {
        view.renderMessage(e.getMessage() + "\n");
        // tell the user what was wrong with the inputs
        c = null;
      }
    }
    return c;
  }

  /**
   * Parses an array of String arguments and returns an array
   * containing an image name and a result name.
   *
   * @throws IndexOutOfBoundsException if the given array does not have enough elements
   */
  private String[] getImageNames(String[] args) throws IndexOutOfBoundsException {
    String[] names = new String[2];
    try {
      Integer.parseInt(args[1]); // if first arg is a number (as in brighten)
      names[0] = args[2]; // take next two as image names
      names[1] = args[3];
    } catch (NumberFormatException e) {
      names[0] = args[1]; // otherwise take first two args as image names
      names[1] = args[2];
    }
    return names;
  }
}
