package controller.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import controller.ImageEditorController;
import controller.SimpleEditorController;
import model.ImageEditorModel;
import view.gui.ImageEditorGUIView;

/**
 * Description of class goes here.
 *
 * @author Matthew Love
 */
public class SimpleEditorGUIController extends SimpleEditorController
    implements ImageEditorController, ActionListener {
  private final ImageEditorGUIView view;
  private final JFrame frame;
  private String currentImage;

  public SimpleEditorGUIController(ImageEditorModel model, ImageEditorGUIView view) {
    super(model, view);
    this.view = view;
    this.frame = view.releaseFrame();
    this.currentImage = "";
  }

  @Override
  public void start() throws IllegalStateException {
    this.view.show(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Load":

        FileDialog fd = new FileDialog(this.frame, "Select an Image", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setFile("*.jpeg;*.jpg;*.ppm;*.bmp;*.png");
        fd.setMultipleMode(false);
        fd.setVisible(true);

        String filename = fd.getFile();
        String path = fd.getDirectory() + filename;

        if (filename != null) {
          System.out.println(filename);
          System.out.println(path);
        }
//        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//        int returnValue = jfc.showOpenDialog(null);
//        jfc.setDialogType(JFileChooser.FILES_ONLY);
//        if (returnValue == JFileChooser.APPROVE_OPTION) {
//          File selectedFile = jfc.getSelectedFile();
//          System.out.println(selectedFile.getAbsolutePath());
//        }
        break;
      case "Save":
        System.out.println(this.showPopUp("Ayo Save"));
        break;
      case "Change Layer":
        System.out.println(this.showListOfLayers());
        break;
      case "Brighten":
        System.out.println("Brightening");
        break;
      case "Vertical Flip":
        System.out.println("Vertical Flip");
        break;
      case "Horizontal Flip":
        System.out.println("Horizontal Flip");
        break;
      case "Component":
        System.out.println("Component");
        break;
      case "Filter":
        System.out.println("Filter");
        break;
      default:
        throw new IllegalStateException("Unknown action performed");
    }
  }

  private String showPopUp(String str) {
    return JOptionPane.showInputDialog(this.frame, str);
  }

  private void showError(String error) {
    JOptionPane.showMessageDialog(null, error, "ERROR",
        JOptionPane.ERROR_MESSAGE);
  }

  private String showListOfLayers() {
    String[] layers = this.model.getListOfLayers();
    if (layers.length == 0) {
      return "";
    }
    Object selectionObject = JOptionPane.showInputDialog(null, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, layers, layers[0]);
    return selectionObject.toString();
  }
}
