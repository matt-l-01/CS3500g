package controller.command;

import controller.ImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the FlipVertical command object.
 */
public class FlipVerticalTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    return new FlipVertical(manager);
  }

  @Override
  public void testExecute() {
    ImageCommand command = new FlipVertical(managerMock);
    command.execute("image1", "image2");
    assertEquals("flip vertical image1 image2\n", managerMock.getLog());
  }
}
