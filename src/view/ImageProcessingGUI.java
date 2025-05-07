package view;

import controller.ImageprocessingController.FlipListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 * Class that handles user intercation with the code using a GUI.
 */
public class ImageProcessingGUI extends JFrame {

  private final JButton loadButton;
  private final JButton saveButton;
  public final JComboBox<String> operationsDropdown;
  private final JComboBox<String> FlipDropdown;
  private final JButton applyButton;
  private final JButton FlipButton;
  private final JLabel imageLabel;
  private final JLabel histogramLabel;
  private final JTextField brightnessField;
  private final JButton adjustBrightnessButton;
  private final JButton compressButton;
  private final JTextField thresholdField;
  private final JTextField splitPercentField;
  private final JButton splitFilterButton;
  private JTextField widthField;
  private JTextField heightField;
  private JButton downscaleButton;

  //for status label
  private final JLabel imageSaveStatusLabel;

  // Add new fields for Black, Mid, and White points
  private final JTextField blackPointField;
  private final JTextField midPointField;
  private final JTextField whitePointField;


  /**
   * Constructor for the ImageProcessingGUI.
   */
  public ImageProcessingGUI() {
    super("Image Processing Application");
    setLayout(new BorderLayout());
    setBackground(new Color(245, 245, 245));

    // Image display panel
    // Create the JLabel with an initial message (placeholder text)
    imageLabel = new JLabel(
        "Load the image and click on the 'Load Image' button on the control panel", JLabel.CENTER);
    imageLabel.setFont(new Font("Arial", Font.ITALIC, 14));  // Set a specific font style
    imageLabel.setForeground(Color.GRAY);  // Set the text color to gray for the placeholder message

    // Create a JScrollPane with the imageLabel inside it
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);

    // Set the border of the JScrollPane with a titled border
    imageScrollPane.setBorder(createTitledBorder("Image Display"));

    // Create a new panel to hold the image and the status label
    imageSaveStatusLabel = new JLabel("Image Not Saved");
    imageSaveStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    imageSaveStatusLabel.setFont(new Font("Arial", Font.BOLD, 14));
    imageSaveStatusLabel.setForeground(Color.RED);

    JPanel imagePanel = new JPanel(new BorderLayout());
    imagePanel.add(imageScrollPane, BorderLayout.CENTER);
    imagePanel.add(imageSaveStatusLabel, BorderLayout.SOUTH);  // Add status label here

    add(imagePanel, BorderLayout.CENTER);

    // Main left panel
    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.setBackground(new Color(240, 240, 240));
    add(leftPanel, BorderLayout.WEST);

    // Control panel container
    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
    controlPanel.setBorder(createTitledBorder("Controls"));
    controlPanel.setBackground(new Color(250, 250, 250));
    leftPanel.add(controlPanel, BorderLayout.CENTER);

    // File operations panel
    JPanel filePanel = createGroupPanel("File Operations");
    filePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

    loadButton = createStyledButton("Load Image");
    saveButton = createStyledButton("Save Image");

    filePanel.add(loadButton);
    filePanel.add(saveButton);

    controlPanel.add(filePanel);

    //Basic Operation Panel
    JPanel operationsPanel = createGroupPanel("Basic Operations");
    operationsDropdown = new JComboBox<>(new String[]{"Blur", "Sharpen", "Greyscale", "Sepia",
        "Color Correct", "Level Adjust"});
    applyButton = createStyledButton("Apply");
    splitFilterButton = createStyledButton("Apply on Split");

    splitPercentField = createStyledTextField(5);  // Initialize splitPercentField

    operationsPanel.add(new JLabel("Select Operation:"));
    operationsPanel.add(operationsDropdown);
    operationsPanel.add(new JLabel("Split Percent:"));  // Label for the split percent field
    operationsPanel.add(splitPercentField);  // Add the splitPercentField
    operationsPanel.add(applyButton);
    operationsPanel.add(splitFilterButton);  // Add the splitFilterButton

    controlPanel.add(operationsPanel);

    JPanel levelAdjustPanel = createGroupPanel("Adjust Black, Mid, White Points");
    blackPointField = createStyledTextField(5);
    midPointField = createStyledTextField(5);
    whitePointField = createStyledTextField(5);

    levelAdjustPanel.add(new JLabel("Black Point:"));
    levelAdjustPanel.add(blackPointField);
    levelAdjustPanel.add(new JLabel("Mid Point:"));
    levelAdjustPanel.add(midPointField);
    levelAdjustPanel.add(new JLabel("White Point:"));
    levelAdjustPanel.add(whitePointField);

    levelAdjustPanel.setVisible(false); // Hide it by default
    controlPanel.add(levelAdjustPanel);

    operationsDropdown.addActionListener(e -> {
      String selectedOperation = (String) operationsDropdown.getSelectedItem();
      if ("Level Adjust".equals(selectedOperation)) {
        levelAdjustPanel.setVisible(true);  // Show input fields if "Level Adjust" is selected
      } else {
        levelAdjustPanel.setVisible(false); // Hide input fields for other operations
      }
    });

    //Flip Operations
    JPanel flipPanel = createGroupPanel("Flip Operations");
    FlipDropdown = new JComboBox<>(new String[]{"Flip Horizontal", "Flip Vertical"});
    FlipButton = createStyledButton("Flip");
    flipPanel.add(new JLabel("Select Flip Operation:"));
    flipPanel.add(FlipDropdown);
    flipPanel.add(FlipButton);
    controlPanel.add(flipPanel);

    // Brightness adjustment panel
    JPanel brightnessPanel = createGroupPanel("Brightness Adjustment");
    brightnessField = createStyledTextField(5);
    adjustBrightnessButton = createStyledButton("Adjust");
    brightnessPanel.add(new JLabel("Brightness Level:"));
    brightnessPanel.add(brightnessField);
    brightnessPanel.add(adjustBrightnessButton);
    controlPanel.add(brightnessPanel);

    // Compression panel
    JPanel compressPanel = createGroupPanel("Image Compression");
    thresholdField = createStyledTextField(5);
    compressButton = createStyledButton("Compress");
    compressPanel.add(new JLabel("Threshold:"));
    compressPanel.add(thresholdField);
    compressPanel.add(compressButton);
    controlPanel.add(compressPanel);

    JPanel downscalePanel = new JPanel();
    downscalePanel.setBorder(
        createTitledBorder("Downscale Image"));  // Using your custom method for titled border
    downscalePanel.setBackground(
        new Color(250, 250, 250));  // Set background color to match the other panels

    widthField = createStyledTextField(5);
    heightField = createStyledTextField(5);

    downscaleButton = createStyledButton("Downscale");

    downscalePanel.add(new JLabel("Width:"));
    downscalePanel.add(widthField);
    downscalePanel.add(new JLabel("Height:"));
    downscalePanel.add(heightField);
    downscalePanel.add(downscaleButton);

    controlPanel.add(downscalePanel);

    // Histogram panel
    histogramLabel = new JLabel();
    JScrollPane histogramScrollPane = new JScrollPane(histogramLabel);
    histogramScrollPane.setPreferredSize(new Dimension(280, 150));
    histogramScrollPane.setBorder(createTitledBorder("Histogram"));
    histogramScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    histogramScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    leftPanel.add(histogramScrollPane, BorderLayout.SOUTH);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900, 650);
  }


  // Utility methods
  private JPanel createGroupPanel(String title) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.setBorder(createTitledBorder(title));
    panel.setBackground(new Color(250, 250, 250));
    return panel;
  }

  public int getNewWidth() {
    return Integer.parseInt(widthField.getText());
  }

  public int getNewHeight() {
    return Integer.parseInt(heightField.getText());
  }

  public void addDownscaleListener(ActionListener listener) {
    downscaleButton.addActionListener(listener);
  }


  private TitledBorder createTitledBorder(String title) {
    TitledBorder border = BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)), title);
    border.setTitleFont(new Font("Arial", Font.BOLD, 12));
    border.setTitleColor(new Color(80, 80, 80));
    return border;
  }

  private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 12));
    button.setBackground(new Color(220, 220, 220));
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(180, 180, 180)),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)
    ));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(200, 200, 200));
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(220, 220, 220));
      }
    });
    return button;
  }

  private JTextField createStyledTextField(int columns) {
    JTextField textField = new JTextField(columns);
    textField.setFont(new Font("Arial", Font.PLAIN, 12));
    textField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(3, 5, 3, 5)
    ));
    return textField;
  }

  // Exposing components for controller access
  public void setImageIcon(ImageIcon imageIcon) {
    imageLabel.setIcon(imageIcon);
  }

  public void setHistogramImage(ImageIcon imageIcon) {
    histogramLabel.setIcon(imageIcon);
  }

  public String getSelectedOperation() {
    return (String) operationsDropdown.getSelectedItem();
  }

  /**
   * Retrieves the brightness level entered by the user. If the input is invalid (not a number), a
   * default value of 0 is returned.
   *
   * @return the brightness level as an integer, or 0 if the input is invalid.
   */
  public int getBrightnessLevel() {
    try {
      return Integer.parseInt(brightnessField.getText());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * Retrieves the threshold value entered by the user. If the input is invalid (not a number), a
   * default value of 0 is returned.
   *
   * @return the threshold value as an integer, or 0 if the input is invalid.
   */
  public int getThresholdValue() {
    try {
      return Integer.parseInt(thresholdField.getText());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * Retrieves the split percentage value entered by the user. If the input is invalid (not a
   * number), a default value of 0 is returned.
   *
   * @return the split percentage as an integer, or 0 if the input is invalid.
   */
  public int getSplitPercent() {
    try {
      return Integer.parseInt(splitPercentField.getText());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * Retrieves the black point value entered by the user. If the input is invalid (not a number), a
   * default value of 0 is returned.
   *
   * @return the black point value as an integer, or 0 if the input is invalid.
   */
  public int getBlackPoint() {
    try {
      return Integer.parseInt(blackPointField.getText());
    } catch (NumberFormatException e) {
      return 0; // Default value if invalid input
    }
  }


  /**
   * Retrieves the midpoint value entered by the user. If the input is invalid (not a number), a
   * default value of 50 is returned.
   *
   * @return the midpoint value as an integer, or 50 if the input is invalid.
   */
  public int getMidPoint() {
    try {
      return Integer.parseInt(midPointField.getText());
    } catch (NumberFormatException e) {
      return 50; // Default midpoint
    }
  }


  /**
   * Retrieves the white point value entered by the user. If the input is invalid (not a number), a
   * default value of 100 is returned.
   *
   * @return the white point value as an integer, or 100 if the input is invalid.
   */
  public int getWhitePoint() {
    try {
      return Integer.parseInt(whitePointField.getText());
    } catch (NumberFormatException e) {
      return 100; // Default white point
    }
  }

  public void addLoadListener(ActionListener listener) {
    loadButton.addActionListener(listener);
  }

  public void addSaveListener(ActionListener listener) {
    saveButton.addActionListener(listener);
  }

  public void addApplyListener(ActionListener listener) {
    applyButton.addActionListener(listener);
  }

  public void addSplitListener(ActionListener listener) {
    splitFilterButton.addActionListener(listener);
  }


  public void addAdjustBrightnessListener(ActionListener listener) {
    adjustBrightnessButton.addActionListener(listener);
  }

  public void addCompressListener(ActionListener listener) {
    compressButton.addActionListener(listener);
  }

  public void addSplitFilterListener(ActionListener listener) {
    splitFilterButton.addActionListener(listener);
  }

  public String getThresholdValueText() {
    return thresholdField.getText();
  }


  public double[][] getFilterKernel() {
    // 3x3 kernel for a basic filter
    return new double[][]{
        {0.0, -1.0, 0.0},
        {-1.0, 4.0, -1.0},
        {0.0, -1.0, 0.0}

    };


  }

  /**
   * Method to update the saved status of an image. Changes color to green if saved, changes color
   * to red if not saved.
   */
  public void updateSaveStatus(boolean isSaved) {
    if (isSaved) {
      imageSaveStatusLabel.setText("Image saved successfully");
      imageSaveStatusLabel.setForeground(Color.GREEN); // Change color to green for saved
    } else {
      imageSaveStatusLabel.setText("Image not saved");
      imageSaveStatusLabel.setForeground(Color.RED); // Change color to red for not saved
    }
  }

  public String getBlackPointText() {
    return blackPointField.getText(); // Return the text entered in the black point field
  }

  public String getMidPointText() {
    return midPointField.getText(); // Return the text entered in the mid point field
  }

  public String getWhitePointText() {
    return whitePointField.getText(); // Return the text entered in the white point field
  }

  public String getSplitPercentText() {
    return splitPercentField.getText(); // Return the text entered in the split percent field
  }

  public JTextField getBrightnessField() {
    return brightnessField;
  }

  public JTextField getWidthField() {
    return widthField; // Return the JTextField for width input
  }

  public JTextField getHeightField() {
    return heightField; // Return the JTextField for height input
  }

  public JButton getFlipButton() {
    return FlipButton; // Return the "Flip" button
  }

  public JButton getApplyButton() {
    return applyButton; // Return the "Apply" button
  }

  public ComboBoxModel<String> getFlipDropdown() {
    return FlipDropdown.getModel(); // Return the model of the "Flip Operations" dropdown
  }


  public void addFlipListener(FlipListener flipListener) {
    // Add the flipListener to the FlipButton
    this.FlipButton.addActionListener(flipListener);
  }

  public JTextField getSplitPercentField() {
    return splitPercentField;
  }

}


