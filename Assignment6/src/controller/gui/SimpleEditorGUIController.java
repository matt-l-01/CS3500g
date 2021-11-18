package controller.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

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
  private String currentPath;

  public SimpleEditorGUIController(ImageEditorModel model, ImageEditorGUIView view) {
    super(model, view);
    this.view = view;
    this.frame = view.releaseFrame();
    this.currentImage = null;
    this.currentPath = "C:\\";
  }

  @Override
  public void start() throws IllegalStateException {
    this.view.show(this);
  }

  /**
   * This static inner class represents a FilenameFilter implementation that ensures the file
   * types when saving and loading are one of the following: png, jpg, jpeg, ppm, or bmp.
   */
  private static class EditorFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
      return name.endsWith(".png")
          || name.endsWith(".jpg")
          || name.endsWith(".jpeg")
          || name.endsWith(".ppm")
          || name.endsWith(".bmp");
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Load":
        String newName = this.showPopUp("Enter a new name for the layer");
        if (newName == null) {
          break;
        }
        FileDialog fd = new FileDialog(this.frame, "Select an Image", FileDialog.LOAD);
        fd.setDirectory(this.currentPath);
        fd.setFilenameFilter(new EditorFilter());
        fd.setMultipleMode(false);
        fd.setVisible(true);

        String path = fd.getDirectory() + fd.getFile();
        if (fd.getFile() != null) {
          System.out.println(path);
          super.loadHelp(path, newName);
          this.currentPath = path;
          this.currentImage = newName;
        }
        break;
      case "Save":
        if (this.currentImage == null) {
          this.showError("No Layers Loaded");
          break;
        }
        FileDialog save = new FileDialog(this.frame, "Select an Image", FileDialog.SAVE);
        save.setDirectory(this.currentPath);
        save.setFilenameFilter(new EditorFilter());
        //save.setFile("*.jpg;*.jpeg;*.png;*.bmp;*.ppm");
        save.setName(this.currentImage);
        save.setMultipleMode(false);
        save.setVisible(true);

        String savePath = save.getDirectory() + save.getFile();
        if (save.getFile() != null) {
          System.out.println(savePath);
          super.saveHelp(savePath, this.currentImage);
          this.currentPath = savePath;
        }
        break;
      case "Change Layer":
        String selected = this.showListOfLayers();
        if (selected == null) {
          break;
        }
        this.currentImage = selected;
        // Change here TODO
        break;
      case "Brighten":
        if (this.currentImage == null) {
          this.showError("No Layers Loaded");
          break;
        }
        String brightenNewName = this.showPopUp("Enter New Layer Name");
        super.brighten(this.showPopUp("Enter Amount"), currentImage, brightenNewName);
        this.currentImage = brightenNewName;
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
      this.showError("No Layers Loaded");
      return null;
    }
    Object selectionObject = JOptionPane.showInputDialog(null, "Choose a layer",
        "Layer Selection", JOptionPane.PLAIN_MESSAGE, null, layers, "");
    if (selectionObject == null) {
      return null;
    }
    return selectionObject.toString();
  }
}
