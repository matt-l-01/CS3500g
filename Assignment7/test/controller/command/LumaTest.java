package controller.command;

import controller.ImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the Luma command object.
 */
public class LumaTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    return new Luma(manager);
  }

  @Override
  public void testExecute() {
    ImageCommand command = new Luma(managerMock);
    command.execute("image1", "image2");
    assertEquals("LUMA image1 image2\n", managerMock.getLog());
  }
}