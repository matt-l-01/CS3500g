package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import javax.imageio.ImageIO;

import model.ARGBPixel;
import model.ColorMatrix;
import model.ImageModel;
import model.Kernel;
import model.RasterImageModel;
import model.Pixel;
import model.RGBPixel;
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

/**
 * This class represents the stored images of an image processing program dealing with pixel-based
 * images and the ability to load and save images through the program.
 */
public class RasterImageManager implements ImageManager {
  private final Map<String, ImageModel> storedImages;
  private final Map<String, Function<String[], ImageCommand>> knownCommands;

  /**
   * Constructs a new object to process images,
   * which starts with no stored images and all the commands
   * that implement the ImageCommand interface.
   */
  public RasterImageManager() {
    this.storedImages = new HashMap<>();
    this.knownCommands = new HashMap<>();
    this.addCommands();
  }

  // initializes the map of known commands to contain all known commands,
  // creating command objects that refer to this model
  private void addCommands() {
    this.knownCommands.put("red-component",s -> new Red(this));
    this.knownCommands.put("green-component",s -> new Green(this));
    this.knownCommands.put("blue-component",s -> new Blue(this));
    this.knownCommands.put("value-component",s -> new Value(this));
    this.knownCommands.put("intensity-component",s -> new Intensity(this));
    this.knownCommands.put("luma-component",s -> new Luma(this));
    this.knownCommands.put("brighten",s -> new Brighten(s,this));
    this.knownCommands.put("horizontal-flip",s -> new FlipHorizontal(this));
    this.knownCommands.put("vertical-flip",s -> new FlipVertical(this));
    this.knownCommands.put("blur",s -> new GaussianBlur(this));
    this.knownCommands.put("sharpen",s -> new Sharpen(this));
    this.knownCommands.put("greyscale",s -> new Greyscale(this));
    this.knownCommands.put("sepia",s -> new Sepia(this));
  }

  /**
   * Loads the specified file into this model's set of stored images
   * as a RasterImageModel. (Currently only supports loading PPM files.)
   *
   * @param filePath the file path where the image is found
   * @throws IOException if the specified path cannot be found.
   */
  private void loadPPM(String filePath, String name) throws IOException, IllegalArgumentException {
    if (filePath == null || name == null) {
      throw new IllegalArgumentException("File path or name cannot be null");
    }
    File imageFile = new File(getAbsoluteFilePath(filePath)); // start from current directory
    StringBuilder fileText = new StringBuilder();
    if (imageFile.exists()) {
      Scanner inFile = new Scanner(imageFile);
      while (inFile.hasNextLine()) {
        String line = inFile.nextLine();
        if (!line.startsWith("#")) {
          fileText.append(line).append(System.lineSeparator());
        }
      }
      inFile.close();
    } else {
      throw new IOException(String.format(
              "File: %s could not be found", filePath));
    }
    this.storedImages.put(name, parseDataToModel(fileText));
  }

  private static ImageModel parseDataToModel(StringBuilder fileText)
          throws IllegalArgumentException {
    Scanner inText = new Scanner(fileText.toString());
    if (inText.next().equals("P3")) {
      try {
        int imageWidth = inText.nextInt();
        int imageHeight = inText.nextInt();
        int maxImageRGB = inText.nextInt();
        List<List<Pixel>> pixelGrid = new ArrayList<>();
        for (int row = 0; row < imageHeight; row++) {
          pixelGrid.add(new ArrayList<>());
          for (int col = 0; col < imageWidth; col++) {
            int red = inText.nextInt();
            int green = inText.nextInt();
            int blue = inText.nextInt();
            if (red < 0 || red > 255
                    || green < 0 || green > 255
                    || blue < 0 || blue > 255) {
              throw new IllegalArgumentException("Invalid RGB value");
            } else if (red > maxImageRGB || green > maxImageRGB || blue > maxImageRGB) {
              throw new IllegalArgumentException(
                      "Found number larger than maximum expected RGB value");
            }
            pixelGrid.get(row).add(new RGBPixel(red, green, blue));
          }
        }
        inText.close();
        return new RasterImageModel(imageWidth, imageHeight, maxImageRGB, pixelGrid);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid dimensions/RGB value");
      } catch (NoSuchElementException e) {
        throw new IllegalArgumentException("Not enough pixel values supplied");
      }
    } else {
      throw new IllegalArgumentException("Image must start with"
              + " correct identifying number (P3)");
    }
  }

  private String getAbsoluteFilePath(String path) {
    if (path.startsWith("/")) { // unix root directory
      return path;
    }
    else if (path.substring(0, 2).matches("[A-Z]:")) { // windows drives
      return path;
    }
    else {
      return ".\\" + path; // treat it as a relative path from the current directory
    }
  }

  /**
   * Loads the specified file into the model and
   * stores in the map of images.
   *
   * @param filePath the file path where the image is found
   * @param name the name to refer to the image as within the program
   * @throws IOException if the file could not be loaded
   * @throws IllegalArgumentException if any parameters are null
   */
  @Override
  public void load(String filePath, String name, ImageFileFormat fileFormat)
          throws IOException, IllegalArgumentException {
    if (filePath == null || name == null || fileFormat == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    if (fileFormat == ImageFileFormat.PPM) {
      this.loadPPM(filePath, name);
      return;
    }
    File inFile = new File(getAbsoluteFilePath(filePath));
    if (inFile.exists()) {
      BufferedImage im;
      List<List<Pixel>> imageData = new ArrayList<>();
      try {
        im = ImageIO.read(inFile);
        int pixel;
        int max = 0;
        for (int row = 0; row < im.getHeight(); row++) {
          imageData.add(new ArrayList<>());
          for (int col = 0; col < im.getWidth(); col++) {
            pixel = im.getRGB(col, row);
            int r = (pixel >> 16) & 0xFF;
            int g = (pixel >> 8) & 0xFF;
            int b = (pixel & 0xFF);
            max = Math.max(max, Math.max(r, Math.max(g, b)));
            imageData.get(row).add(new ARGBPixel(
                    ((pixel >> 24) & 0xFF), r, g, b));
          }
        }
        this.storedImages.put(name, new RasterImageModel(im.getWidth(),
                im.getHeight(), max, imageData));
      } catch (IOException e) {
        throw new IOException("Could not open file");
      }
    } else {
      throw new IOException("File does not exist.");
    }
  }

  /**
   * Saves the given image to the specified file path, which includes the desired
   * file name and extension. (Currently only supports writing to PPM files.)
   *
   * @param imageName the name of the image to be saved
   * @param filePath the file path to save the image to
   * @throws IOException if file cannot be created
   * @throws IllegalArgumentException if any parameters are null
   * @throws IllegalStateException if image cannot be found
   */
  private void savePPM(String imageName, String filePath)
          throws IOException, IllegalArgumentException {
    if (imageName == null || filePath == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    ImageModel image = storedImages.getOrDefault(imageName, null);
    if (image == null) {
      throw new IllegalStateException("Image not found.");
    }
    String outputText = image.toPPMFile();
    File outputFile = new File(getAbsoluteFilePath(filePath)); // start from current directory
    boolean createdSuccessfully = true;
    if (!outputFile.exists()) {
      try {
        createdSuccessfully = outputFile.createNewFile();
      } catch (IOException e) {
        throw new IOException("Could not create file");
      }
    }
    if (createdSuccessfully) {
      OutputStream outFile = new FileOutputStream(outputFile);
      for (int i = 0; i < outputText.length(); i++) {
        outFile.write(outputText.charAt(i));
      }
      outFile.close();
    } else {
      throw new IOException("Could not create file");
    }
  }

  /**
   * Saves the file with the given extension and name to
   * the specified file path.
   *
   * @param imageName the name of the image to be saved
   * @param filePath the file path to save the image to, including the file name
   * @param fileFormat the format to save the file as
   * @throws IOException if the file could not be saved
   * @throws IllegalArgumentException if the provided values are null/invalid
   */
  @Override
  public void save(String imageName, String filePath, ImageFileFormat fileFormat)
      throws IOException, IllegalArgumentException {
    if (imageName == null || filePath == null || fileFormat == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    if (fileFormat == ImageFileFormat.PPM) {
      this.savePPM(imageName, filePath);
      return;
    }
    ImageModel model = this.getImage(imageName);
    int format;
    if (fileFormat == ImageFileFormat.PNG) {
      format = BufferedImage.TYPE_INT_ARGB;
    } else {
      format = BufferedImage.TYPE_INT_RGB;
    }
    BufferedImage outIm = new BufferedImage(model.getWidth(), model.getHeight(), format);
    int mask = 0xFFFFFFFF;
    Pixel pixel;
    int color;
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        pixel = model.getPixel(row, col);
        color = ((pixel.getAlpha() << 24) & mask)
                + ((pixel.getRed() << 16) & mask)
                + ((pixel.getGreen() << 8) & mask)
                + (pixel.getBlue() & mask);
        outIm.setRGB(col, row, color);
      }
    }
    try {
      File outFile = new File(getAbsoluteFilePath(filePath));
      boolean createdSuccessfully;
      if (!outFile.exists()) {
        createdSuccessfully = outFile.createNewFile();
      } else {
        createdSuccessfully = true;
      }
      if (createdSuccessfully) {
        ImageIO.write(outIm, fileFormat.toString(), outFile);
      } else {
        throw new IOException("Could not write to file");
      }
    } catch (IOException e) {
      throw new IOException("Could not save file");
    }
  }

  @Override
  public Map<String, ImageModel> getStoredImages() {
    Map<String, ImageModel> storedImageCopy = new HashMap<>();
    storedImageCopy.putAll(this.storedImages);
    return storedImageCopy;
  }

  @Override
  public Map<String, Function<String[], ImageCommand>> getKnownCommands() {
    Map<String, Function<String[], ImageCommand>> knownCommandsCopy = new HashMap<>();
    knownCommandsCopy.putAll(this.knownCommands);
    return knownCommandsCopy;
  }

  @Override
  public void visualizeComponent(ImageModel.Component component,
                                 String imageName, String resultName)
          throws IllegalArgumentException {
    checkNull(component,imageName,resultName);
    this.storedImages.put(resultName, this.getImage(imageName).visualizeComponent(component));
  }

  @Override
  public void flip(String direction, String imageName, String resultName)
          throws IllegalArgumentException {
    checkNull(direction, imageName, resultName); // throws an exception/won't go on if any are null
    this.storedImages.put(resultName, this.getImage(imageName).flip(direction));
  }

  @Override
  public void brighten(int increment, String imageName, String resultName)
          throws IllegalArgumentException {
    checkNull(imageName, resultName);
    this.storedImages.put(resultName, this.getImage(imageName).brighten(increment));
  }

  @Override
  public void filter(Kernel kernel, String imageName, String resultName)
          throws IllegalArgumentException {
    checkNull(kernel, imageName, resultName);
    this.storedImages.put(resultName, this.getImage(imageName).filter(kernel));
  }

  @Override
  public void colorTransform(ColorMatrix matrix, String imageName, String resultName)
          throws IllegalArgumentException {
    checkNull(matrix, imageName, resultName);
    this.storedImages.put(resultName, this.getImage(imageName).colorTransform(matrix));
  }

  // throws an exception if any of the arguments is null
  private void checkNull(Object...args) throws IllegalArgumentException {
    for (Object o:args) {
      if (o == null) {
        throw new IllegalArgumentException("Arguments cannot be null.");
      }
    }
  }

  // gets the image corresponding to the given string, or throws an exception if it is not found
  private ImageModel getImage(String imageName) throws IllegalArgumentException {
    ImageModel image = this.storedImages.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("Image not found.");
    }
    return image;
  }
}
