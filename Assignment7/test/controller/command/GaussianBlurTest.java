package controller.command;

import controller.ImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the GaussianBlur command object.
 */
public class GaussianBlurTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    return new GaussianBlur(manager);
  }

  @Override
  public void testExecute() {
    ImageCommand command = new GaussianBlur(managerMock);
    command.execute("image1", "image2");
    assertEquals("filter [[0.0625, 0.125, 0.0625], [0.125, 0.25, 0.125], "
            + "[0.0625, 0.125, 0.0625]] image1 image2\n", managerMock.getLog());
  }
}