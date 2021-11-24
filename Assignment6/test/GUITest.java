import org.junit.Test;

import controller.ImageEditorController;
import controller.gui.SimpleEditorGUIController;
import mocks.MockActionEvent;
import mocks.MockGUIController;
import mocks.MockGUIView;
import model.ImageEditorModel;
import model.SimpleEditorModel;
import view.gui.ImageEditorGUIView;

import static org.junit.Assert.assertEquals;

public class GUITest {
  @Test
  public void testGUIController() {
    StringBuilder log = new StringBuilder();
    ImageEditorGUIView view = new MockGUIView(log);
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorController cont = new SimpleEditorGUIController(model, view);
    cont.start();
    assertEquals("RELEASED FRAME\nSHOWN\n", log.toString());
  }

  @Test
  public void testEvents() {
    StringBuilder log = new StringBuilder();
    MockGUIController cont = new MockGUIController(log);
    cont.actionPerformed(new MockActionEvent("ABC"));
    cont.actionPerformed(new MockActionEvent("1234"));
    assertEquals("EVENT: ABC\nEVENT: 1234\n", log.toString());
  }
}
