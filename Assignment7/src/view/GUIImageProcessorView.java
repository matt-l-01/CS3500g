package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controller.ImageProcessorFeatures;
import model.ImageModel;

/**
 * This class represents a graphical user interface for a simple image processor. It displays
 * one image at a time, along with a histogram of its components and options for the user to load,
 * save, and manipulate the image.
 */
public class GUIImageProcessorView extends JFrame implements ImageGUIView {
  private JLabel imageLabel;
  private JPanel optionPanel;
  private JPanel histogramPanel;
  private JButton loadButton;
  private JButton saveButton;
  private JComboBox<String> componentSelector;
  private JButton greyscaleButton;
  private JButton sepiaButton;
  private JButton horizFlipButton;
  private JButton vertFlipButton;
  private JButton blurButton;
  private JButton sharpenButton;
  private JButton brightenButton;
  private JTextField brightenField;
  private JButton darkenButton;
  private JTextField darkenField;
  private JButton mosaicButton;
  private JTextField mosaicField;
  private JFileChooser fileChooser;
  private Image currentImage;

  /**
   * Constructor creates all the components
   * on the JFrame gui window.
   * @param caption title of the frame
   */
  public GUIImageProcessorView(String caption) {
    super(caption);

    setSize(800, 500);
    setLocation(0,0);
    setResizable(true);

    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // set image initially to contain nothing
    this.currentImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

    // hold current image in a panel as an ImageIcon
    JPanel imagePanel = new JPanel();
    this.imageLabel = new JLabel();
    imageLabel.setIcon(new ImageIcon(this.currentImage));
    imageLabel.setVisible(false); //no image to display yet, so show nothing
    imagePanel.add(imageLabel);

    // make image panel scrollable and in the center
    JScrollPane imageScrollPane = new JScrollPane(imagePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    imageScrollPane.setPreferredSize(new Dimension(500, 500));
    this.add(imageScrollPane, BorderLayout.CENTER);

    // add a panel in the right-hand side of the layout
    this.optionPanel = new JPanel();
    optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
    optionPanel.setPreferredSize(new Dimension(270,500));
    this.add(optionPanel, BorderLayout.LINE_END);

    // display the histogram in the top of the right-hand panel
    this.histogramPanel = new HistogramPanel(currentImage);
    histogramPanel.setPreferredSize(new Dimension(270, 250));
    optionPanel.add(histogramPanel);

    // create a grid to hold the buttons in
    JPanel buttonGrid = new JPanel(new GridLayout(5,2, 5, 5));
    optionPanel.add(buttonGrid);

    // create each button/text field/etc and add them in the grid
    // load button
    this.loadButton = new JButton("Load");
    this.loadButton.setActionCommand("load button");
    this.fileChooser = new JFileChooser();
    buttonGrid.add(loadButton);

    // save button
    this.saveButton = new JButton("Save");
    this.saveButton.setActionCommand("save button");
    buttonGrid.add(saveButton);

    // visualize component prompt
    JLabel componentPrompt = new JLabel("Visualize component: ");
    buttonGrid.add(componentPrompt);

    // visualize component selection menu
    String[] components = new String[] {"Red", "Green", "Blue", "Value", "Intensity", "Luma"};
    this.componentSelector = new JComboBox<>(components);
    componentSelector.setSelectedIndex(0);
    buttonGrid.add(componentSelector);

    // greyscale button
    this.greyscaleButton = new JButton("Greyscale");
    this.greyscaleButton.setActionCommand("greyscale button");
    buttonGrid.add(greyscaleButton);

    // sepia button
    this.sepiaButton = new JButton("Sepia");
    this.sepiaButton.setActionCommand("sepia button");
    buttonGrid.add(sepiaButton);

    // horizontal flip button
    this.horizFlipButton = new JButton("Horizontal flip");
    this.horizFlipButton.setActionCommand("horizontal flip button");
    buttonGrid.add(horizFlipButton);

    // vertical flip button
    this.vertFlipButton = new JButton("Vertical flip");
    this.vertFlipButton.setActionCommand("vertical flip button");
    buttonGrid.add(vertFlipButton);

    // blur button
    this.blurButton = new JButton("Blur");
    this.blurButton.setActionCommand("blur button");
    buttonGrid.add(blurButton);

    // sharpen button
    this.sharpenButton = new JButton("Sharpen");
    this.sharpenButton.setActionCommand("sharpen button");
    buttonGrid.add(sharpenButton);

    // add panel for the brighten features
    JPanel brightenGrid = new JPanel();
    brightenGrid.setLayout(new BoxLayout(brightenGrid, BoxLayout.LINE_AXIS));
    optionPanel.add(brightenGrid);

    // brighten button
    this.brightenButton = new JButton("Brighten");
    this.brightenButton.setActionCommand("brighten button");
    brightenGrid.add(brightenButton);
    brightenGrid.add(Box.createHorizontalGlue());

    // brighten prompt
    JLabel brightenPrompt = new JLabel("Amount:");
    brightenGrid.add(brightenPrompt);
    brightenGrid.add(Box.createHorizontalGlue());

    // brighten text field
    this.brightenField = new JTextField(10);
    brightenField.setMaximumSize(new Dimension(100, 60));
    brightenGrid.add(brightenField);

    // add panel for darken features
    JPanel darkenGrid = new JPanel();
    darkenGrid.setLayout(new BoxLayout(darkenGrid, BoxLayout.LINE_AXIS));
    optionPanel.add(darkenGrid);
    optionPanel.add(Box.createVerticalGlue()); // put extra space at the bottom

    // darken button
    this.darkenButton = new JButton("Darken");
    this.darkenButton.setActionCommand("darken button");
    darkenGrid.add(darkenButton);
    darkenGrid.add(Box.createHorizontalGlue());

    // darken prompt
    JLabel darkenPrompt = new JLabel("Amount: ");
    darkenGrid.add(darkenPrompt);
    darkenGrid.add(Box.createHorizontalGlue());

    // darken text field
    this.darkenField = new JTextField(10);
    darkenField.setMaximumSize(new Dimension(100,60));
    darkenGrid.add(darkenField);

    // add panel for mosaic features
    JPanel mosaicGrid = new JPanel();
    mosaicGrid.setLayout(new BoxLayout(mosaicGrid, BoxLayout.LINE_AXIS));
    optionPanel.add(mosaicGrid);
    optionPanel.add(Box.createVerticalGlue()); // put extra space at the bottom

    // mosaic button
    this.mosaicButton = new JButton("Mosaic");
    this.mosaicButton.setActionCommand("mosaic button");
    mosaicGrid.add(mosaicButton);
    mosaicGrid.add(Box.createHorizontalGlue());
    //r
    // mosaic prompt
    JLabel mosaicPrompt = new JLabel("Amount of seeds: ");
    mosaicGrid.add(mosaicPrompt);
    mosaicGrid.add(Box.createHorizontalGlue());

    // mosaic text field
    this.mosaicField = new JTextField(10);
    mosaicField.setMaximumSize(new Dimension(100,60));
    mosaicGrid.add(mosaicField);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(ImageProcessorFeatures features) {
    loadButton.addActionListener(e -> {
      int returnVal = fileChooser.showOpenDialog(GUIImageProcessorView.this.optionPanel);
      if (returnVal == JFileChooser.APPROVE_OPTION) { // if the user hits "open", load the file
        String filePath = fileChooser.getSelectedFile().getPath();
        features.loadImage(filePath);
      } // otherwise do nothing
    });
    saveButton.addActionListener(e -> {
      int returnVal = fileChooser.showSaveDialog(GUIImageProcessorView.this.optionPanel);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        String filePath = fileChooser.getSelectedFile().getPath();
        features.saveImage(filePath);
      }
    });
    componentSelector.addActionListener(e -> {
      JComboBox<String> options = (JComboBox<String>) e.getSource();
      String component = (String) options.getSelectedItem();
      switch (component) {
        case "Red":
          features.visualizeRed(); // call the corresponding features method for each menu item
          break;
        case "Green":
          features.visualizeGreen();
          break;
        case "Blue":
          features.visualizeBlue();
          break;
        case "Value":
          features.visualizeValue();
          break;
        case "Intensity":
          features.visualizeIntensity();
          break;
        case "Luma":
          features.visualizeLuma();
          break;
        default:
          break; // do nothing if there is no corresponding features item
      }
    });
    horizFlipButton.addActionListener(evt -> features.flipHorizontal());
    vertFlipButton.addActionListener(evt -> features.flipVertical());
    greyscaleButton.addActionListener(evt -> features.greyscale());
    sepiaButton.addActionListener(evt -> features.sepia());
    blurButton.addActionListener(evt -> features.blur());
    sharpenButton.addActionListener(evt -> features.sharpen());
    brightenButton.addActionListener(
            evt -> features.brighten(brightenField.getText()));
    darkenButton.addActionListener(evt -> features.darken(darkenField.getText()));
    mosaicButton.addActionListener(
            evt -> features.mosaic(mosaicField.getText()));

  }

  @Override
  public void setDisplayImage(ImageModel model) {
    this.currentImage = new ImageModelAdapter(model);
    this.imageLabel.setIcon(new ImageIcon(currentImage));
    this.imageLabel.setVisible(true); //make image visible now that it has something in it
    ((HistogramPanel) this.histogramPanel).setImage(currentImage); // update histogram panel
    this.histogramPanel.setVisible(true); // make histogram visible
  }

  @Override
  public void renderMessage(String message) { // makes a pop-up dialog
    JOptionPane.showMessageDialog(this, message);
  }

  @Override
  public void refresh() {
    this.repaint();
  }
}
