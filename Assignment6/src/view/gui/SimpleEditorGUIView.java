package view.gui;

import view.ImageEditorView;

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
  public void show() {
    ev.setVisible(true);
  }
}
