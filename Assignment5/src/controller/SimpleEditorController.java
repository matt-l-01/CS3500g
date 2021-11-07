package controller;

import java.util.NoSuchElementException;
import java.util.Scanner;

import controller.ImageEditorController;
import model.Component;
import model.ImageEditorModel;
import view.ImageEditorView;

/**
 * Implementation of the controller for a SimpleEditor. Allows the program to be run, the intake
 * of commands, and facilitation of the output for the user. Runs the program.
 */
public class SimpleEditorController implements ImageEditorController {
  private final ImageEditorModel model;
  private final ImageEditorView view;
  private final Scanner scan;

  /**
   * Constructs a SimpleEditor controller using the provided parameters: the model, the view for
   * the model, and the input as a Readable. Then instantiates these instance fields along with the
   * creation of a new scanner.
   * @param model the model for the Simple Editor.
   * @param view the view providing output for the Simple Editor.
   * @param in the means of input, as a Readable, for the program.
   * @throws IllegalArgumentException if any of the provided parameters are null.
   */
  public SimpleEditorController(ImageEditorModel model, ImageEditorView view, Readable in)
      throws IllegalArgumentException {
    if (model == null || view == null || in == null) {
      throw new IllegalArgumentException("Arguments must not be null.");
    }

    this.model = model;
    this.view = view;
    this.scan = new Scanner(in); // Instantiates a Scanner with the Readable input provided
  }

  @Override
  public void start() throws IllegalStateException {
    this.welcomeMessage();

    while (true) {
      this.view.renderMessage("Enter a command: ");

      String input = "";
      try {
        input = this.scan.nextLine();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Not enough inputs.");
      }

      if (input.equals("q")) {
        this.view.renderMessage("Goodbye.");
        break;
      }

      String[] args = input.split(" ");

      switch (args[0]) {
        case "load":
          if (this.argsError(args, 2)) {
            this.load(args[1], args[2]);
          }
          break;
        case "save":
          if (this.argsError(args, 2)) {
            this.save(args[1], args[2]);
          }
          break;
        case "brighten":
          if (this.argsError(args, 3)) {
            this.brighten(args[1], args[2], args[3]);
          }
          break;
        case "vertical-flip":
          if (this.argsError(args, 2)) {
            this.verticalFlip(args[1], args[2]);
          }
          break;
        case "horizontal-flip":
          if (this.argsError(args, 2)) {
            this.horizontalFlip(args[1], args[2]);
          }
          break;
        case "value-component":
          if (this.argsError(args, 2)) {
            this.component(Component.VALUE, args[1], args[2]);
          }
          break;
        case "intensity-component":
          if (this.argsError(args, 2)) {
            this.component(Component.INTENSITY, args[1], args[2]);
          }
          break;
        case "luma-component":
          if (this.argsError(args, 2)) {
            this.component(Component.LUMA, args[1], args[2]);
          }
          break;
        case "red-component":
          if (this.argsError(args, 2)) {
            this.component(Component.RED, args[1], args[2]);
          }
          break;
        case "green-component":
          if (this.argsError(args, 2)) {
            this.component(Component.GREEN, args[1], args[2]);
          }
          break;
        case "blue-component":
          if (this.argsError(args, 2)) {
            this.component(Component.BLUE, args[1], args[2]);
          }
          break;
        case "blur":
          if (this.argsError(args, 2)) {
            this.blur(args[1], args[2]);
          }
          break;
        case "sharpen":
          if (this.argsError(args, 2)) {
            this.sharpen(args[1], args[2]);
          }
          break;
        case "greyscale":
          if (this.argsError(args, 2)) {
            this.greyscale(args[1], args[2]);
          }
          break;
        case "sepia":
          if (this.argsError(args, 2)) {
            this.sepia(args[1], args[2]);
          }
          break;
        case "menu":
          this.welcomeMessage();
          break;
        default:
          this.view.renderMessage("Invalid command...please try again.\n");
      }
    }
  }

  /**
   * Renders the welcome message to the view.
   */
  private void welcomeMessage() {
    this.view.renderMessage("Welcome to the SimpleEditor program. "
        + "Please use one of the following commands: \n");
    this.view.renderMessage("- load <path> <save-name> - Load an image\n");
    this.view.renderMessage("- save <path> <save-name> - Saves the image to the given path\n");
    this.view.renderMessage("- brighten <value> <image-name> <save-name> - Brightens an image to "
        + "the value (pos/neg) and saves to the new name\n");
    this.view.renderMessage("- vertical-flip <image-name> <save-name> - Flips the image vertically"
        + " and saves to the new name\n");
    this.view.renderMessage("- horizontal-flip <image-name> <save-name> - Flips the image "
        + "horizontally and saves to the new name\n");
    this.view.renderMessage("- value-component <image-name> <save-name> - Converts the image to "
        + "show the value component and saves to the new name\n");
    this.view.renderMessage("- intensity-component <image-name> <save-name> - Converts the image "
        + "to show the intensity component and saves to the new name\n");
    this.view.renderMessage("- luma-component <image-name> <save-name> - Converts the image to "
        + "show the luma component and saves to the new name\n");
    this.view.renderMessage("- red-component <image-name> <save-name> - Converts the image to "
        + "show the red component and saves to the new name\n");
    this.view.renderMessage("- green-component <image-name> <save-name> - Converts the image to "
        + "show the green component and saves to the new name\n");
    this.view.renderMessage("- blue-component <image-name> <save-name> - Converts the image to "
        + "show the blue component and saves to the new name\n");
    this.view.renderMessage("- blur <image-name> <save-name> - Blurs the image\n");
    this.view.renderMessage("- sharpen <image-name> <save-name> - Sharpens the image\n");
    this.view.renderMessage("- sepia <image-name> <save-name> - Color transforms the " +
        "image to sepia\n");
    this.view.renderMessage("- greyscale <image-name> <save-image> - Color transforms the " +
        "image to greyscale\n");
    this.view.renderMessage("- menu - Shows this screen again.\n");
    this.view.renderMessage("Type \"q\" if you would like to quit the program.\n");
  }

  /**
   * Handles errors when an incorrect amount of arguments has been provided. Will then output an
   * error message. Adds one to the amt parameter to account for the command itself.
   * @param args the arguments taken in from the scanner.
   * @param amt the amount of arguments that is expected.
   * @return whether the number of arguments is correct.
   */
  private boolean argsError(String[] args, int amt) {
    int count = 0;
    for (String s : args) {
      count++;
    }
    if (count == amt + 1) {
      return true;
    }
    this.view.renderMessage("You have provided an incorrect amount of arguments. "
        + "Please try again.\n");
    return false;
  }

  /**
   * Handles the loading of images for the controller. Adds the subsequent messages and then calls
   * the model to handle the task internally.
   * @param path the path of the file to load.
   * @param name the name to save the image as in memory.
   */
  private void load(String path, String name) {
    try {
      this.view.renderMessage("Loading...");
      this.model.loadImage(path, name);
      this.view.renderMessage("Successfully loaded image with name \"" + name + "\".\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("The was an error loading the provided file path. Not found or "
          + "empty.\n");
    }
  }

  /**
   * Handles the saving of images for the controller. Adds the subsequent messages and then calls
   * the model to handle the task internally.
   * @param path the path of where to save.
   * @param name the name of the image in memory.
   */
  private void save(String path, String name) {
    try {
      this.view.renderMessage("Saving...");
      this.model.save(path, name);
      this.view.renderMessage("Successfully saved image to path \"" + path + "\".\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: Couldn't save the provided file. Please ensure the name and "
          + "path are correct and try again.\n");
    }
  }

  /**
   * Handles the brightening of images for the controller. Adds the subsequent messages and then
   * calls the model to handle the task internally.
   * @param val the value to brighten by, as a String from the Scanner.
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory.
   */
  private void brighten(String val, String beforeImage, String afterImage) {
    int value = 0;
    try {
      value = Integer.parseInt(val);
    } catch (NumberFormatException e) {
      this.view.renderMessage("You did not enter a valid integer for the first argument.\n");
      return;
    }

    try {
      this.view.renderMessage("Brightening...");
      this.model.brighten(value, beforeImage, afterImage);
      this.view.renderMessage("Successfully brightened image by " + val + ".\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  /**
   * Handles the vertical flip of images for the controller. Adds the subsequent messages and then
   * calls the model to handle the task internally.
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory.
   */
  private void verticalFlip(String beforeImage, String afterImage) {
    try {
      this.view.renderMessage("Flipping vertically...");
      this.model.flipVertical(beforeImage, afterImage);
      this.view.renderMessage("Successfully flipped the image vertically.\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  /**
   * Handles the horizontal flip of images for the controller. Adds the subsequent messages and
   * then calls the model to handle the task internally.
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory.
   */
  private void horizontalFlip(String beforeImage, String afterImage) {
    try {
      this.view.renderMessage("Flipping horizontally...");
      this.model.flipHorizontal(beforeImage, afterImage);
      this.view.renderMessage("Successfully flipped the image horizontally.\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  /**
   * Handles the rendering of components for images for the controller. Adds the subsequent
   * messages and then calls the model to handle the task internally.
   * @param c The component that is being rendered.
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory.
   */
  private void component(Component c, String beforeImage, String afterImage) {
    try {
      this.view.renderMessage("Rendering component " + c + "...");
      this.model.component(c, beforeImage, afterImage);
      this.view.renderMessage("Successfully rendered the component " + c + " for the image.\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  /**
   * Handles the blurring of images for the controller. Adds the subsequent
   * messages and then calls the model to handle the task internally.
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory.
   */
  private void blur(String beforeImage, String afterImage) {
    try {
      this.view.renderMessage("Blurring Image...");
      this.model.blur(beforeImage, afterImage);
      this.view.renderMessage("Successfully blurred the image.\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  /**
   * Handles the sharpening of images for the controller. Adds the subsequent
   * messages and then calls the model to handle the task internally.
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory.
   */
  private void sharpen(String beforeImage, String afterImage) {
    try {
      this.view.renderMessage("Sharpening Image...");
      this.model.sharpen(beforeImage, afterImage);
      this.view.renderMessage("Successfully sharpened the image.\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  private void greyscale(String beforeImage, String afterImage) {
    try {
      this.view.renderMessage("Transforming image to greyscale...");
      this.model.greyscale(beforeImage, afterImage);
      this.view.renderMessage("Successfully transformed the image.\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  private void sepia(String beforeImage, String afterImage) {
    try {
      this.view.renderMessage("Transforming image to sepia...");
      this.model.sepia(beforeImage, afterImage);
      this.view.renderMessage("Successfully transformed the image.\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }
}
