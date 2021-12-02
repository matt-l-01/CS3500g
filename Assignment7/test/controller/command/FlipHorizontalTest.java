package controller.command;

import controller.ImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the FlipHorizontal command object.
 */
public class FlipHorizontalTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    return new FlipHorizontal(manager);
  }

  @Override
  public void testExecute() {
    ImageCommand command = new FlipHorizontal(managerMock);
    command.execute("image1", "image2");
    assertEquals("flip horizontal image1 image2\n", managerMock.getLog());
  }
}
