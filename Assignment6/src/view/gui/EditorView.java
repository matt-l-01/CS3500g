package view.gui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Description of class goes here.
 *
 * @author Matthew Love
 */
public class EditorView extends JFrame {
  private final JMenuBar menuBar;

  private final JMenu file;
  private final JMenuItem load;
  private final JMenuItem save;
  private final JMenuItem changeLayer;

  private final JMenu edit;
  private final JMenuItem brighten;
  private final JMenuItem vFlip;
  private final JMenuItem hFlip;
  private final JMenuItem component;
  private final JMenuItem filter;

  public EditorView() {
    super();
    this.menuBar = new JMenuBar();
    this.file = new JMenu("File");
    this.load = new JMenuItem("Load");
    this.save = new JMenuItem("Save");
    this.changeLayer = new JMenuItem("Change Layer");

    this.edit = new JMenu("Edit");
    this.brighten = new JMenuItem("Brighten");
    this.vFlip = new JMenuItem("Vertical Flip");
    this.hFlip = new JMenuItem("Horizontal Flip");
    this.component = new JMenuItem("Component");
    this.filter = new JMenuItem("Filter");

    this.initialize();
  }

  private void initialize() {
    this.setLayout(new BorderLayout(10, 5));
    this.setTitle("Simple Image Editor GUI");
    this.setSize(800, 500);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBackground(Color.darkGray);
    this.setUpMenu();
  }

  private void setUpMenu() {
    this.menuBar.add(this.file);
    this.menuBar.add(this.edit);
    this.file.add(this.load);
    this.file.add(this.save);
    this.file.add(this.changeLayer);
    this.edit.add(this.brighten);
    this.edit.add(this.vFlip);
    this.edit.add(this.hFlip);
    this.edit.add(this.component);
    this.edit.add(this.filter);

    this.setJMenuBar(this.menuBar);
  }

  protected void setUpListeners(ActionListener a) {
    this.load.addActionListener(a);
    this.save.addActionListener(a);
    this.changeLayer.addActionListener(a);
    this.brighten.addActionListener(a);
    this.vFlip.addActionListener(a);
    this.hFlip.addActionListener(a);
    this.component.addActionListener(a);
    this.filter.addActionListener(a);
  }
}
