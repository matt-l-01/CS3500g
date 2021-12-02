package controller;

import java.io.IOException;
import java.util.function.Function;

import controller.command.ImageCommand;
import view.ImageGUIView;

/**
 * This class represents a controller for an image processing program that takes input
 * from the user via a graphical user interface.
 */
public class GUIImageController extends AbstractImageController implements ImageProcessorFeatures {
  private ImageGUIView view;
  private int currentImageID; // the name of the currently displayed image

  /**
   * Creates an image controller that displays information to the user via the given view
   * and handles image models via the given image manager.
   *
   * @param view the view to display the program to the user
   * @param manager the manager to handle images
   * @throws IllegalArgumentException if the view or manager are null
   */
  public GUIImageController(ImageGUIView view, ImageManager manager)
          throws IllegalArgumentException {
    super(manager);
    if (view == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    this.view = view;
    this.view.addFeatures(this); // sets this as the features source for the provided view
    this.currentImageID = 0;
  }


  @Override
  public void loadImage(String filepath) {
    try {
      manager.load(filepath, Integer.toString(this.currentImageID + 1), // load image w/ next id
              this.getFileFormat(filepath));
      view.setDisplayImage(manager.getStoredImages()
              .get(Integer.toString(this.currentImageID + 1))); // display the newly loaded image
      this.currentImageID += 1; // update the current image to be the newly loaded image
      view.refresh();
    } catch (IOException e) {
      view.renderMessage("Could not load file.");
    } catch (IllegalArgumentException ex) {
      view.renderMessage(ex.getMessage());
    }
  }

  @Override
  protected ImageManager.ImageFileFormat getFileFormat(String filePath)
          throws IllegalArgumentException {
    try {
      return super.getFileFormat(filePath);
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public void saveImage(String filePath) {
    try {
      manager.save(Integer.toString(this.currentImageID), filePath, this.getFileFormat(filePath));
      view.refresh(); //for good measure
    } catch (IOException e) {
      view.renderMessage("Could not save file.");
    } catch (IllegalArgumentException ex) {
      view.renderMessage(ex.getMessage());
    }
  }


  /**
   * Executes the given image manipulation command, sets the resulting image as the current one
   * to be displayed, and tells the view to update to reflect the change.
   *
   * @param command the command to be executed
   */
  private void useCommand(ImageCommand command) {
    try {
      command.execute(Integer.toString(this.currentImageID), // act on the current image
              Integer.toString(this.currentImageID + 1));  // name the result with the next id
      view.setDisplayImage(manager.getStoredImages()
              .get(Integer.toString(this.currentImageID + 1)));
      // set the new image as the one to be displayed
      this.currentImageID += 1; // update the current image to be the new image
      view.refresh(); // update view
    } catch (IllegalArgumentException e) {
      view.renderMessage(e.getMessage()); // display manager's exception message to the user
    } // do not update the image
  }

  @Override
  public void visualizeRed() {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("red-component");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }

  @Override
  public void visualizeGreen() {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("green-component");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }

  @Override
  public void visualizeBlue() {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("blue-component");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }

  @Override
  public void visualizeValue() {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("value-component");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }

  @Override
  public void visualizeIntensity() {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("intensity-component");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }

  @Override
  public void visualizeLuma() {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("luma-component");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }

  @Override
  public void greyscale() {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("greyscale");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }

  @Override
  public void sepia() {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("sepia");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }

  @Override
  public void blur() {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("blur");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }

  @Override
  public void sharpen() {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("sharpen");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }

  @Override
  public void mosaic(String text) {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("mosaic");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }

  @Override
  public void brighten(String amount) {
    try {
      Integer.parseInt(amount); // check that input is an integer amount
    } catch (NumberFormatException e) {
      view.renderMessage("Please enter an integer increment.");
      return; // do not execute the command for invalid input
    }
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("brighten");
    ImageCommand c = cmd.apply(new String[]{amount});
    this.useCommand(c);
  }

  @Override
  public void darken(String amount) {
    try {
      Integer.parseInt(amount);
    } catch (NumberFormatException e) {
      view.renderMessage("Please enter an integer increment.");
      return;
    }
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("brighten");
    ImageCommand c = cmd.apply(new String[]{"-" + amount}); // brighten by negative amount
    this.useCommand(c);
  }

  @Override
  public void flipHorizontal() {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("horizontal-flip");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }

  @Override
  public void flipVertical() {
    Function<String[], ImageCommand> cmd = manager.getKnownCommands().get("vertical-flip");
    ImageCommand c = cmd.apply(new String[0]);
    this.useCommand(c);
  }
  //r


}
