import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import controller.GUIImageController;
import controller.ImageController;
import controller.ImageManager;
import controller.ImageProcessorFeatures;
import controller.TextImageController;
import controller.RasterImageManager;
import view.GUIImageProcessorView;
import view.ImageGUIView;
import view.ImageTextView;
import view.SimpleImageTextView;

/**
 * Holds the main method for a simple image processor which can be text-based
 * or have a graphical user interface.
 */
public class ImageProcessor {

  /**
   * Runs a simple image processor. Accepts arguments -file [script file path] to run a script file
   * found at the given path and -text to run the program in text mode. Runs in interactive GUI
   * mode by default.
   *
   * @param args optional command line arguments that can be used to specify a script file to run
   *             or choose to run in text mode rather than GUI mode
   */
  public static void main(String[] args) {

    Readable input = new InputStreamReader(System.in);
    boolean guiMode = true;

    try {
      ArrayList<String> entered = new ArrayList<String>(List.of(args));
      for (int i = 0; i < entered.size(); i++) {
        if (entered.get(i).equals("-file")) {
          try {
            input = new FileReader(entered.remove(i + 1)); // otherwise input will remain system.in
            guiMode = false;
          } catch (FileNotFoundException e) {
            System.out.println("Specified script file not found. Running in interactive mode.\n");
          }
        } else if (entered.get(i).equals("-text")) {
          guiMode = false;
        } else {
          System.out.println("Command line argument not recognized. Quitting.\n");
          return;
        }
      }
    } catch (IndexOutOfBoundsException e) { // list throws this if we access an invalid index
      System.out.println("Insufficient inputs for the given argument. Using default settings.\n");
    }

    if (guiMode) {
      ImageGUIView view = new GUIImageProcessorView("Image Processor");
      ImageProcessorFeatures controller = new GUIImageController(view, new RasterImageManager());
      view.refresh();
    } else {
      ImageManager manager = new RasterImageManager();
      ImageTextView view = new SimpleImageTextView(); // system.out by default
      ImageController controller =
              new TextImageController(input, manager, view);
      controller.runProgram();
    }
  }
}
