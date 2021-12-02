package controller.command;

import controller.ImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the Blue command object.
 */
public class BlueTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    return new Blue(manager);
  }

  @Override
  public void testExecute() {
    ImageCommand command = new Blue(managerMock);
    command.execute("image1", "image2");
    assertEquals("BLUE image1 image2\n", managerMock.getLog());
  }
}
