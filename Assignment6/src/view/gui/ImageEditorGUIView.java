package view.gui;

import java.awt.event.ActionListener;

import javax.swing.*;

import view.ImageEditorView;

/**
 * Description of interface goes here.
 *
 * @author Matthew Love
 */
public interface ImageEditorGUIView extends ImageEditorView {
  void show(ActionListener a);
  JFrame releaseFrame();
  void drawImage(String name);
}
