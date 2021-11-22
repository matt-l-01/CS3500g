package view.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

import model.ImageEditorState;
import model.Pixel;

/**
 * Description of class goes here.
 *
 * @author Matthew Love
 */
public class EditorView extends JFrame {
  private final JPanel leftPanel;
  private final JPanel rightPanel;
  private final JPanel container;
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

  private JScrollPane lastUsed;

  public EditorView(ImageEditorState state) {
    super();
    if (state == null) {
      throw new IllegalArgumentException("State may not be null");
    }
    this.leftPanel = new JPanel();
    this.rightPanel = new JPanel();
    this.container = new JPanel();

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
    this.container.setLayout(new GridLayout(1, 2));
    this.leftPanel.setLayout(new BorderLayout());
    this.rightPanel.setLayout(new BorderLayout());
    leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Image View"));
    rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Histogram"));
    this.container.add(this.leftPanel);
    this.container.add(this.rightPanel);
    this.add(this.container);

    this.setResizable(false);
    this.setTitle("Simple Image Editor GUI");
    this.pack();
    this.setSize(1000, 500);
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

  protected void drawImage(Pixel[][] image) {
    BufferedImage img = new BufferedImage(image[0].length, image.length, 1);

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        Pixel p = image[i][j];
        int color = (p.getRed() << 16) | (p.getGreen() << 8) | p.getBlue();
        img.setRGB(j, i, color);
        System.out.println("1Drawing " + i);
      }
    }

    JLabel picLabel = new JLabel(new ImageIcon(img));
    JScrollPane scrollPane = new JScrollPane(picLabel);
    if (this.lastUsed != null) {
      this.leftPanel.remove(this.lastUsed);
    }
    this.leftPanel.add(scrollPane);
    this.lastUsed = scrollPane;
    scrollPane.setPreferredSize(new Dimension(500,500));
    this.pack();
    System.out.println("1Drawn");
  }
}
