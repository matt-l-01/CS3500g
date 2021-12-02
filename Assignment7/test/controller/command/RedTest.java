package controller.command;

import controller.ImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the Red command object.
 */
public class RedTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    return new Red(manager);
  }

  @Override
  public void testExecute() {
    ImageCommand command = new Red(managerMock);
    command.execute("image1", "image2");
    assertEquals("RED image1 image2\n", managerMock.getLog());
  }
}
