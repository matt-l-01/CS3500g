package controller.command;

import controller.ImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the Green command object.
 */
public class GreenTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    return new Green(manager);
  }

  @Override
  public void testExecute() {
    ImageCommand command = new Green(managerMock);
    command.execute("image1", "image2");
    assertEquals("GREEN image1 image2\n", managerMock.getLog());
  }
}