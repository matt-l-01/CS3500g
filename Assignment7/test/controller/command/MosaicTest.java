package controller.command;

import org.junit.Test;

import controller.ImageManager;
import controller.RasterImageManager;

import static org.junit.Assert.assertEquals;

/**
 * Holds tests for the Mosaic command object.
 */
public class MosaicTest extends AbstractImageCommandTest {

  @Override
  protected AbstractImageCommand createCommand(ImageManager manager) {
    String[] args = new String[] {"50"};
    return new Mosaic(args, manager);
  }

  /**
   * Test that the Mosaic constructor throws an IllegalArgumentException
   * when given an array of arguments that does not include an integer increment.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBrightenIncrementException() {
    new Mosaic(new String[] {"some", "0.57", "less"}, new RasterImageManager());
  }

  /**
   * Test that the Mosaic constructor throws an IllegalArgumentException
   * when given an array of arguments that is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMosaicNullArgsException() {
    new Mosaic(null, new RasterImageManager());
  }

  @Override
  public void testExecute() {
    ImageCommand command = new FlipHorizontal(managerMock);
    command.execute("image1", "image2");
    assertEquals("flip horizontal image1 image2\n", managerMock.getLog());
  }
}
