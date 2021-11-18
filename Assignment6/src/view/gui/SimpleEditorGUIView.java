package view.gui;

import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Description of class goes here.
 *
 * @author Matthew Love
 */
public class SimpleEditorGUIView implements ImageEditorGUIView {
  private final EditorView ev;

  public SimpleEditorGUIView() {
    this.ev = new EditorView();
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {

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
}
