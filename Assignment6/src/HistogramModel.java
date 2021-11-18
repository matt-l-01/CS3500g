import model.Component;
import model.Pixel;
import model.SimpleEditorModel;

public class HistogramModel extends SimpleEditorModel implements Histogram {
  private final Pixel[][] image;
  private final Histogram[][] table;

  private HistogramModel(Pixel[][] image) {
    this.image = image;
    this.table = new Histogram[image.length][image.length];
  }

  //have a table for each of the RGB components & frequency (intensity = average of all components)
  //creates a line chart for each of the RGB components
  public void createTable(String imageName) {

    Pixel[][] image = this.releaseImage(imageName);
    Histogram redTable = new HistogramModel(image);
    Histogram greenTable = new HistogramModel(image);
    Histogram blueTable = new HistogramModel(image);

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        redTable[image.getRed()][component(Component.INTENSITY, imageName, imageName)];
        greenTable[image.getGreen()][component(Component.INTENSITY, imageName, imageName)];
        blueTable[image.getBlue()][component(Component.INTENSITY, imageName, imageName)];
      }
    }
  }

  public Pixel[][] releaseImage (String name) throws IllegalStateException {
    Pixel[][] value = this.image.get(name);
    if (value == null) {
      throw new IllegalStateException("Image \"" + name + "\" not found in saved list.");
    }
    return value;
  }

}





