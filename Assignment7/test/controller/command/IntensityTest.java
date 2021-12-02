package controller.command;

import controller.ImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the Intensity command object.
 */
public class IntensityTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    return new Intensity(manager);
  }

  @Override
  public void testExecute() {
    ImageCommand command = new Intensity(managerMock);
    command.execute("image1", "image2");
    assertEquals("INTENSITY image1 image2\n", managerMock.getLog());
  }
}
