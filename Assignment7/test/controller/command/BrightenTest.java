package controller.command;

import org.junit.Test;

import controller.ImageManager;
import controller.RasterImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the Brighten command object.
 */
public class BrightenTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    String[] args = new String[] {"10"};
    return new Brighten(args, manager);
  }

  /**
   * Test that the Brighten constructor throws an IllegalArgumentException
   * when given an array of arguments that does not include an integer increment.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBrightenIncrementException() {
    new Brighten(new String[] {"some", "0.57", "less"}, new RasterImageManager());
  }

  /**
   * Test that the Brighten constructor throws an IllegalArgumentException
   * when given an array of arguments that is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBrightenNullArgsException() {
    new Brighten(null, new RasterImageManager());
  }

  @Override
  public void testExecute() {
    ImageCommand command = new FlipHorizontal(managerMock);
    command.execute("image1", "image2");
    assertEquals("flip horizontal image1 image2\n", managerMock.getLog());
  }
}
