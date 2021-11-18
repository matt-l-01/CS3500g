package view.gui;

import java.awt.*;

import javax.swing.*;

/**
 * Description of class goes here.
 *
 * @author Matthew Love
 */
public class EditorView extends JFrame {
  private final JPanel imagePanel;
  private final JPanel otherPanel;

  private final JMenuBar menuBar;

  private final JMenu file;
  private final JMenuItem load;
  private final JMenuItem save;

  private final JMenu edit;
  private final JMenuItem brighten;

  public EditorView() {
    super();
    this.imagePanel = new JPanel();
    this.otherPanel = new JPanel();

    this.menuBar = new JMenuBar();
    this.file = new JMenu("File");
    this.load = new JMenuItem("Load");
    this.save = new JMenuItem("Save");

    this.edit = new JMenu("Edit");
    this.brighten = new JMenuItem("Brighten");

    this.initialize();
  }

  private void initialize() {
    this.setLayout(new BorderLayout(10, 5));
    this.setTitle("Simple Image Editor GUI");
    this.setSize(800, 500);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBackground(Color.darkGray);

    this.imagePanel.setBackground(Color.black);
    this.otherPanel.setBackground(Color.red);
    this.add(this.imagePanel, BorderLayout.WEST);
    this.add(this.otherPanel, BorderLayout.EAST);
    this.setUpMenu();
  }

  private void setUpMenu() {
    this.menuBar.add(this.file);
    this.menuBar.add(this.edit);
    this.file.add(this.load);
    this.file.add(this.save);
    this.edit.add(this.brighten);

    this.setJMenuBar(this.menuBar);
  }
}
