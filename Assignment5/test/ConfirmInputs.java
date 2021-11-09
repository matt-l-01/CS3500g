import model.Component;
import model.ImageEditorModel;
import model.Pixel;

/**
 * A mock model.ImageEditorModel class in order to test the inputs of the program. Will allow any
 * inputs to be outputted to a StringBuilder log in order to confirm correctness.
 */
public class ConfirmInputs implements ImageEditorModel {
  private final StringBuilder log;

  /**
   * Constructs a new ConfirmInputsSolitaire mock model. Instantiates a new StringBuilder given
   * as a log in order to keep track of the inputs provided to the program, and confirm they are
   * correctly being used.
   * @param log the StringBuilder log provided in which to use as a log.
   */
  public ConfirmInputs(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void acceptNewImage(Pixel[][] image, String name) throws IllegalArgumentException {
    log.append("ACCEPT NEW IMAGE ").append(name).append("\n");
  }

  @Override
  public Pixel[][] releaseImage(String name) throws IllegalStateException {
    log.append("RELEASE IMAGE ").append(name).append("\n");
    return null;
  }

  @Override
  public void component(Component type, String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException {
    log.append("COMPONENT ").append(type).append(" ").append(fromImageName).append(" ")
        .append(toImageName).append("\n");
  }

  @Override
  public void flipHorizontal(String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException {
    log.append("FLIP-H ").append(fromImageName).append(" ").append(toImageName).append("\n");
  }

  @Override
  public void flipVertical(String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException {
    log.append("FLIP-V ").append(fromImageName).append(" ").append(toImageName).append("\n");
  }

  @Override
  public void brighten(int value, String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException {
    log.append("BRIGHTEN ").append(value).append(" ").append(fromImageName).append(" ")
        .append(toImageName).append("\n");
  }

  @Override
  public void blur(String fromImageName, String toImageName) {
    
  }

  @Override
  public void sharpen(String fromImageName, String toImageName) {

  }

  @Override
  public void greyscale(String fromImageName, String toImageName) {

  }

  @Override
  public void sepia(String fromImageName, String toImageName) {

  }
}
