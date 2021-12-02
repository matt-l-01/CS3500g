package controller.command;

import controller.ImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the Greyscale command object.
 */
public class GreyscaleTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    return new Greyscale(manager);
  }

  @Override
  public void testExecute() {
    ImageCommand command = new Greyscale(managerMock);
    command.execute("image1", "image2");
    assertEquals("colorTransform [[0.2126, 0.7152, 0.0722], "
            + "[0.2126, 0.7152, 0.0722], "
            + "[0.2126, 0.7152, 0.0722]] image1 image2\n",
            managerMock.getLog());
  }
}