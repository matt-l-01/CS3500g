package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.filters.Blur;
import model.filters.Filter;
import model.filters.Sharpen;

/**
 * Represents a SimpleImageEditor. Can load, conduct operations on, and save images. This editor
 * can also store multiple images within its memory on a single concurrent run. Then, allowing
 * the user to save any of these representations of the images to a given path.
 */
public class SimpleEditorModel implements ImageEditorModel {
  private final Map<String, Pixel[][]> images;

  /**
   * Constructs an instance of the model to represent the image at the given file name for the
   * parameter. After checking to ensure the file name is not null, the fileName field will be
   * instantiated and the image 2D array will be instantiated as well.
   */
  public SimpleEditorModel() {
    this.images = new HashMap<>();
  }

  /**
   * Checks to see if the file name ends with ppm or not and depends the image file accordingly.
   * @param path the path at which the file is loaded from.
   * @param name the name the file is being stored as in memory.
   * @throws IllegalArgumentException if the path or name is null
   * @throws IllegalStateException if the program cannot read the file provided
   */
  @Override
  public void loadImage(String path, String name)
      throws IllegalArgumentException, IllegalStateException {
    if (path == null || name == null) {
      throw new IllegalArgumentException("Path and name must both not be null.");
    }

    if (path.endsWith(".ppm")) {
      this.loadPPM(path, name);
    } else {
      this.loadNotPPM(path, name);
    }
  }

  /**
   * Loads all other supported file types than PPM through a BufferedImage, and inputs the array
   * of Pixels into the HashMap with the given name.
   * @param path the path of the file to be read.
   * @param name the name to store the array in memory with.
   * @throws IllegalStateException if the program cannot properly read the file provided.
   */
  private void loadNotPPM(String path, String name) throws IllegalStateException {
    BufferedImage img;
    try {
      img = ImageIO.read(new File(path));
    } catch (IOException e) {
      throw new IllegalStateException("Program could not read JPG");
    }

    Pixel[][] image = new Pixel[img.getHeight()][img.getWidth()];
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Color c = new Color(img.getRGB(j, i));
        Pixel p = new Pixel(c.getRed(), c.getGreen(), c.getBlue());
        image[i][j] = p;
      }
    }
    this.images.put(name, image);
  }

  private void loadPPM(String path, String name) {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File is not found");
    }

    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());
    String token;

    try {
      token = sc.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("File is empty");
    }

    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    this.images.put(name, new Pixel[height][width]);

    int maxValue = sc.nextInt(); // Not used, but needs to be scanned

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel p = new Pixel(r, g, b);
        this.getImage(name)[i][j] = p;
      }
    }
  }

  /**
   * Saves the image file if it is a ppm file or another type of file.
   * @param path the path where the file is being saved.
   * @param name the name of the image stored in memory to save.
   * @throws IllegalArgumentException if the path or name is null
   * @throws IllegalStateException if the program could not save the file properly
   */
  @Override
  public void save(String path, String name)
      throws IllegalArgumentException, IllegalStateException {
    if (path == null || name == null) {
      throw new IllegalArgumentException("Path and name must both not be null.");
    }
    if (path.endsWith(".ppm")) {
      this.savePPM(path,name);
    } else {
      this.saveNotPPM(path,name);
    }
  }

  private void savePPM(String path, String name) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(path));
      Pixel[][] image = this.getImage(name);

      writer.write("P3" + System.lineSeparator());
      writer.write("# Created by SimpleImage program. OOD Assignment 4"
          + System.lineSeparator());
      writer.write(image[0].length + " " + image.length + System.lineSeparator());
      writer.write("255" + System.lineSeparator());

      for (int i = 0; i < image.length; i++) {
        for (int j = 0; j < image[i].length; j++) {
          writer.write(image[i][j].getRed() + System.lineSeparator());
          writer.write(image[i][j].getGreen() + System.lineSeparator());
          writer.write(image[i][j].getBlue() + System.lineSeparator());
        }
      }

      writer.close();

    } catch (IOException e) {
      throw new IllegalStateException("Editor could not properly save file.");
    }
  }


  private void saveNotPPM(String path, String name) {
    try {
      Pixel[][] image = this.getImage(name);
      BufferedImage img = new BufferedImage(image[0].length, image.length, 2);

      for (int i = 0; i < image.length; i++) {
        for (int j = 0; j < image[i].length; j++) {
          Pixel p = image[i][j];
          Color c = new Color(p.getRed(), p.getGreen(), p.getBlue());
          img.setRGB(j, i, c.getRGB());
        }
      }

      ImageIO.write(img, "png", new File(path));
    } catch (IOException e) {
      throw new IllegalStateException("Couldn't properly save file");
    }
  }

  @Override
  public void component(Component type, String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException {
    if (type == null || fromImageName == null || toImageName == null) {
      throw new IllegalArgumentException("model.Component type and image names must not be null");
    }

    Pixel[][] image = this.getImage(fromImageName);
    Pixel[][] result = new Pixel[image.length][image[0].length];

    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        Pixel p = image[i][j].clone();
        int component;
        switch (type) {
          case RED:
            component = p.getRed();
            p.setGreen(component);
            p.setBlue(component);
            break;
          case GREEN:
            component = p.getGreen();
            p.setRed(component);
            p.setBlue(component);
            break;
          case BLUE:
            component = p.getBlue();
            p.setRed(component);
            p.setGreen(component);
            break;
          case VALUE:
            int maxVal = Math.max(Math.max(p.getRed(), p.getGreen()), p.getBlue());
            p.setRGB(maxVal, maxVal, maxVal);
            break;
          case INTENSITY:
            double avg = (p.getRed() + p.getGreen() + p.getBlue()) / 3.0;
            int newAvg = (int) Math.floor(avg);
            p.setRGB(newAvg, newAvg, newAvg);
            break;
          case LUMA:
            double val = (p.getRed() * 0.2126) + (p.getGreen() * 0.7152) + (p.getBlue() * 0.0722);
            int newVal = (int) Math.floor(val);
            p.setRGB(newVal, newVal, newVal);
            break;
          default:
            /*
             * No action is intended when no other case applies. The switch statement switches on
             * the model.Component ENUM, and all the options are specified above, so the program
             * will never hit the default case.
             */
            break;
        }
        result[i][j] = p;
      }
    }
    this.images.put(toImageName, result);
  }

  @Override
  public void flipHorizontal(String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException {
    if (fromImageName == null || toImageName == null) {
      throw new IllegalArgumentException("The image from and destination names must not be null.");
    }

    Pixel[][] image = this.getImage(fromImageName);
    Pixel[][] result = new Pixel[image.length][image[0].length];

    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        Pixel temp = image[i][j].clone();
        result[i][j] = image[i][result[i].length - j - 1].clone();
        result[i][result[i].length - j - 1] = temp;
      }
    }
    this.images.put(toImageName, result);
  }

  @Override
  public void flipVertical(String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException {
    if (fromImageName == null || toImageName == null) {
      throw new IllegalArgumentException("The image from and destination names must not be null.");
    }

    Pixel[][] image = this.getImage(fromImageName);
    Pixel[][] result = new Pixel[image.length][image[0].length];

    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        Pixel temp = image[i][j].clone();
        result[i][j] = image[result.length - i - 1][j].clone();
        result[result.length - i - 1][j] = temp;
      }
    }
    this.images.put(toImageName, result);
  }

  @Override
  public void brighten(int value, String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException {
    if (fromImageName == null || toImageName == null) {
      throw new IllegalArgumentException("The image from and destination names must not be null.");
    }

    Pixel[][] image = this.getImage(fromImageName);
    Pixel[][] result = new Pixel[image.length][image[0].length];

    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        Pixel p = image[i][j].clone();

        p.setRGB(this.brightenCalc(p.getRed(), value),
                 this.brightenCalc(p.getGreen(), value),
                 this.brightenCalc(p.getBlue(), value));

        result[i][j] = p;
      }
    }
    this.images.put(toImageName, result);
  }

  /**
   * Supporting method for the this.brighten() method. Given the current value and the change,
   * will cap the value at either 0 or 255 depending on whether the change is negative or
   * positive. Will then return the result.
   * @param before the value before the brighten is added/subtracted.
   * @param change the value to either add or subtract to brighten/darken the image.
   * @return the new value after adding/subtracting, capped between 0 and 255 inclusive.
   */
  private int brightenCalc(int before, int change) {
    int result = before + change;

    if (result < 0) {
      return 0;
    }
    if (result > 255) {
      return 255;
    }
    return result;
  }

  /**
   * Attempts to get the image array from the hash map using the name. If it is not found in the
   * hash map, it will throw an IllegalStateException.
   * @param name the name of the image stored in memory to retrieve.
   * @return the array of Pixels for the given saved image.
   * @throws IllegalStateException if the image is not found in the saved hash map.
   */
  private Pixel[][] getImage(String name) throws IllegalStateException {
    Pixel[][] value = this.images.get(name);
    if (value == null) {
      throw new IllegalStateException("Image \"" + name + "\" not found in saved list.");
    }
    return value;
  }

  @Override
  public void blur(String fromImageName, String toImageName) {
    Filter blur = new Blur(this.getImage(fromImageName));
    this.images.put(toImageName, blur.filter().clone());
  }
}
