package view.gui;

import java.awt.event.ActionListener;

import javax.swing.*;

import model.ImageEditorState;

/**
 * Description of class goes here.
 *
 * @author Matthew Love
 */
public class SimpleEditorGUIView implements ImageEditorGUIView {
  private final EditorView ev;
  private final ImageEditorState state;

  public SimpleEditorGUIView(ImageEditorState state) {
    if (state == null) {
      throw new IllegalArgumentException("State may not be null");
    }
    this.ev = new EditorView(state);
    this.state = state;
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    JOptionPane.showMessageDialog(this.ev, message, "Simple Image Editor",
        JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void show(ActionListener a) {
    this.ev.setUpListeners(a);
    this.ev.setVisible(true);
  }

  @Override
  public JFrame releaseFrame() {
    return ev;
  }

  @Override
  public void drawImage(String name) {
    this.ev.drawImage(this.state.releaseImage(name));
  }
}
