package controller.command;

import controller.ImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the Sepia command object.
 */
public class SepiaTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    return new Sepia(manager);
  }

  @Override
  public void testExecute() {
    ImageCommand command = new Sepia(managerMock);
    command.execute("image1", "image2");
    assertEquals("colorTransform [[0.393, 0.769, 0.189], [0.349, 0.686, 0.168], "
            + "[0.272, 0.534, 0.131]] image1 image2\n", managerMock.getLog());
  }
}