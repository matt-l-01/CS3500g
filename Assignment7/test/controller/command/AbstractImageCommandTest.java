package controller.command;

import org.junit.Test;

import controller.ImageManager;
import controller.TextImageControllerTest.ManagerLogMock;

/**
 * Holds abstracted tests for the function objects
 * that implement the ImageCommand interface.
 */
public abstract class AbstractImageCommandTest {
  protected ManagerLogMock managerMock = new ManagerLogMock();

  /**
   * Factory method to return ImageCommands of different types.
   * @param manager the manager through which the created command will act
   * @return the created ImageCommand object
   */
  protected abstract AbstractImageCommand createCommand(ImageManager manager);

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException() {
    this.createCommand(null);
  }

  @Test
  public abstract void testExecute();
}
