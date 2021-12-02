package controller.command;

import controller.ImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the Value command object.
 */
public class ValueTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    return new Value(manager);
  }

  @Override
  public void testExecute() {
    ImageCommand command = new Value(managerMock);
    command.execute("image1", "image2");
    assertEquals("VALUE image1 image2\n", managerMock.getLog());
  }
}