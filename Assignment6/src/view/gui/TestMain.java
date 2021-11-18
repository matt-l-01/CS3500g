package view.gui;

import java.awt.event.ActionListener;

import controller.ImageEditorController;
import controller.gui.SimpleEditorGUIController;
import model.ImageEditorModel;
import model.SimpleEditorModel;

/**
 * Description of class goes here.
 *
 * @author Matthew Love
 */
public class TestMain {
  public static void main(String[] args) {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorGUIView view = new SimpleEditorGUIView();
    ImageEditorController cont = new SimpleEditorGUIController(model, view);
    cont.start();
  }
}
