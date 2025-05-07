package controller;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.Image;
import model.ImageOperations;
import view.ImageProcessingGUI;

/**
 * Image processing controller class that handles the GUI.
 */
public class ImageprocessingController {

  private final ImageProcessingGUI view;
  private final ImageOperations model;
  private Image currentImage;

  /**
   * Constructs the Image Processing controller.
   */
  public ImageprocessingController(ImageProcessingGUI view, ImageOperations model) {
    this.view = view;
    this.model = model;
    this.view.addLoadListener(new LoadListener());
    this.view.addSaveListener(new SaveListener());
    this.view.addApplyListener(new ApplyListener());
    this.view.addFlipListener(new FlipListener());
    this.view.addAdjustBrightnessListener(new AdjustBrightnessListener());
    this.view.addCompressListener(new CompressListener());
    this.view.addDownscaleListener(new DownscaleListener());
    this.view.addSplitListener(new SplitListener());

  }

  class LoadListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();
      if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        try {
          String filePath = file.getAbsolutePath();
          if (filePath.endsWith(".ppm")) {
            currentImage = loadPPMImage(filePath);
          } else {
            currentImage = loadStandardImage(filePath);
          }

          // Display the loaded image
          ImageIcon imageIcon = new ImageIcon(currentImage.toBufferedImage());
          view.setImageIcon(imageIcon);

          // Generate and display the histogram
          Image histogramImage = model.generateHistogram(currentImage);
          view.setHistogramImage(new ImageIcon(histogramImage.toBufferedImage()));

        } catch (Exception ex) {
          JOptionPane.showMessageDialog(view, "Error loading image: " + ex.getMessage());
        }
      }
    }
  }


  class SaveListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      // Open the file dialog for saving the image
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Save Image");
      int userSelection = fileChooser.showSaveDialog(view);

      if (userSelection == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();

        try {
          String filePath = file.getAbsolutePath();

          // Saving the image - Check for PPM or other formats
          if (filePath.endsWith(".ppm")) {
            savePPMImage(filePath, currentImage);
          } else {
            saveStandardImage(filePath, currentImage);
          }

          // After saving, update the status label in the view to indicate success
          view.updateSaveStatus(true);  // Image saved successfully

          JOptionPane.showMessageDialog(view, "Image saved successfully.");
        } catch (IOException ex) {
          // If error occurs while saving, show error message
          JOptionPane.showMessageDialog(view, "Error saving image: " + ex.getMessage());

          // Update the status label in case of failure
          view.updateSaveStatus(false);  // Image not saved
        }
      }
    }
  }


  class ApplyListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        JOptionPane.showMessageDialog(view, "No image loaded.");
        return;
      }

      // Get selected operation from the dropdown
      String operation = (String) view.operationsDropdown.getSelectedItem();
      System.out.println("Selected operation: " + operation);  // Debugging

      try {
        switch (operation) {
          case "Blur":
            currentImage = model.blur(currentImage, 0); // Default parameter
            break;
          case "Sharpen":
            currentImage = model.sharpen(currentImage, 0); // Default parameter
            break;
          case "Greyscale":
            currentImage = model.applyGreyscale(currentImage, 0); // Default parameter
            break;
          case "Sepia":
            currentImage = model.applySepia(currentImage, 0); // Default parameter
            break;
          case "Color Correct":
            currentImage = model.colorCorrect(currentImage, 0); // Default parameter
            break;
          case "Level Adjust":
            // Get the parameters for levels adjustment from the view
            int blackPoint = view.getBlackPoint();
            int midPoint = view.getMidPoint();
            int whitePoint = view.getWhitePoint();
            int splitPercent = view.getSplitPercent();

            // Debugging print statements to confirm values are correct
            System.out.println("Black Point: " + blackPoint);
            System.out.println("Mid Point: " + midPoint);
            System.out.println("White Point: " + whitePoint);
            System.out.println("Split Percent: " + splitPercent);

            // Check if values are valid (e.g., splitPercent between 0 and 100)
            if (splitPercent < 0 || splitPercent > 100) {
              JOptionPane.showMessageDialog(view, "Split percent must be between 0 "
                  + "and 100.");
              return;
            }

            if (blackPoint > whitePoint) {
              JOptionPane.showMessageDialog(view,
                  "Black point cannot be greater than white point.");
              return;
            }

            // Apply the levels adjustment
            currentImage = model.levelsAdjust(currentImage, blackPoint, midPoint, whitePoint,
                splitPercent);
            break;
          default:
            JOptionPane.showMessageDialog(view, "Unsupported operation.");
            return;
        }

        // Update the image display
        view.setImageIcon(new ImageIcon(currentImage.toBufferedImage()));

        // Generate and display the histogram
        Image histogramImage = model.generateHistogram(currentImage);
        view.setHistogramImage(new ImageIcon(histogramImage.toBufferedImage()));

      } catch (Exception ex) {
        ex.printStackTrace(); // Print the stack trace for debugging
        JOptionPane.showMessageDialog(view, "Error applying operation: " + ex.getMessage());
      }
    }
  }


  class SplitListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        JOptionPane.showMessageDialog(view, "No image loaded.");
        return;
      }

      // Get the split percent from the text field in the view
      String splitPercentText = view.getSplitPercentField().getText().trim();
      int splitPercent;
      try {
        splitPercent = Integer.parseInt(splitPercentText);
        if (splitPercent < 0 || splitPercent > 100) {
          JOptionPane.showMessageDialog(view, "Split percent must be between 0 and 100.");
          return;
        }
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(view, "Invalid split percent value.");
        return;
      }

      // Get the selected operation from the operations dropdown
      String operation = (String) view.operationsDropdown.getSelectedItem();
      System.out.println("Selected operation: " + operation);  // Debugging

      try {
        switch (operation) {
          case "Blur":
            currentImage = model.blur(currentImage, splitPercent);
            break;
          case "Sharpen":
            currentImage = model.sharpen(currentImage, splitPercent);
            break;
          case "Greyscale":
            currentImage = model.applyGreyscale(currentImage, splitPercent);
            break;
          case "Sepia":
            currentImage = model.applySepia(currentImage, splitPercent);
            break;
          case "Color Correct":
            currentImage = model.colorCorrect(currentImage, splitPercent);
            break;
          case "Level Adjust":
            // Get the levels adjustment parameters from the view
            int blackPoint = view.getBlackPoint();
            int midPoint = view.getMidPoint();
            int whitePoint = view.getWhitePoint();

            // Ensure valid black and white points
            if (blackPoint > whitePoint) {
              JOptionPane.showMessageDialog(view,
                  "Black point cannot be greater than white point.");
              return;
            }

            // Apply the levels adjustment with split percent
            currentImage = model.levelsAdjust(currentImage, blackPoint, midPoint, whitePoint,
                splitPercent);
            break;
          default:
            JOptionPane.showMessageDialog(view, "Unsupported operation.");
            return;
        }

        // Update the image display
        view.setImageIcon(new ImageIcon(currentImage.toBufferedImage()));

        // Generate and display the histogram
        Image histogramImage = model.generateHistogram(currentImage);
        view.setHistogramImage(new ImageIcon(histogramImage.toBufferedImage()));

      } catch (Exception ex) {
        JOptionPane.showMessageDialog(view, "Error applying split operation: "
            + ex.getMessage());
      }
    }
  }


  /**
   * The class handles user actions for applying flip operations (horizontal or vertical) to the
   * currently loaded image in the application.
   */
  public class FlipListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        JOptionPane.showMessageDialog(view, "No image loaded.");
        return;
      }

      // Handle the Flip button from flip operations dropdown
      String flipOperation = (String) view.getFlipDropdown()
          .getSelectedItem(); // Get selected flip operation

      try {
        if ("Flip Horizontal".equals(flipOperation)) {
          currentImage = model.flipHorizontal(currentImage);
        } else if ("Flip Vertical".equals(flipOperation)) {
          currentImage = model.flipVertical(currentImage);
        }

        // Update image display
        view.setImageIcon(new ImageIcon(currentImage.toBufferedImage()));

        // Generate and display histogram
        Image histogramImage = model.generateHistogram(currentImage);
        view.setHistogramImage(new ImageIcon(histogramImage.toBufferedImage()));

      } catch (Exception ex) {
        JOptionPane.showMessageDialog(view, "Error applying flip operation: "
            + ex.getMessage());
      }
    }
  }


  class AdjustBrightnessListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        JOptionPane.showMessageDialog(view, "No image loaded.", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      int brightnessLevel;
      try {
        String input = view.getBrightnessField().getText()
            .trim(); // Retrieve input and trim extra spaces.

        if (input.isEmpty()) {
          throw new NumberFormatException(
              "Input cannot be empty."); // Custom message for empty input.
        }

        brightnessLevel = Integer.parseInt(input); // Try to parse input as an integer.

      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(view,
            "Invalid input. Please enter a numeric value for brightness.",
            "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      try {
        // Adjust the brightness using the ImageOperations' adjustBrightness method.
        currentImage = model.adjustBrightness(currentImage, brightnessLevel);

        // Update the view with the adjusted image.
        view.setImageIcon(new ImageIcon(currentImage.toBufferedImage()));
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(view, "Error adjusting brightness: "
                + ex.getMessage(),
            "Processing Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }


  class CompressListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        JOptionPane.showMessageDialog(view, "No image loaded.");
        return;
      }
      try {
        // Get the threshold value from the JTextField in the view
        String thresholdText = view.getThresholdValueText();
        double threshold = Double.parseDouble(thresholdText);

        // Validate the threshold value
        if (threshold < 0 || threshold > 100) {
          JOptionPane.showMessageDialog(view,
              "Invalid threshold value. Please enter a value within 0 - 100");
          return;
        }

        // Call the compress method with the threshold value
        Image compressedImage = model.compress(currentImage, threshold);

        // Update the view with the compressed image
        view.setImageIcon(new ImageIcon(compressedImage.toBufferedImage()));

        // Optionally, display a message
        JOptionPane.showMessageDialog(view, "Image compression successful.");

      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(view,
            "Please enter a valid numeric value for the threshold.");
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(view, "Error during compression: "
            + ex.getMessage());
      }
    }
  }


  class DownscaleListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        JOptionPane.showMessageDialog(view, "No image loaded.");
        return;
      }

      try {
        // Validate the width and height inputs to ensure they are numeric and positive
        String widthInput = view.getWidthField().getText();
        String heightInput = view.getHeightField().getText();

        int newWidth = Integer.parseInt(widthInput);
        int newHeight = Integer.parseInt(heightInput);

        if (newWidth <= 0 || newHeight <= 0) {
          JOptionPane.showMessageDialog(view, "Width and Height must be positive numbers.",
              "Input Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        // Perform the downscaling operation
        currentImage = model.downscale(currentImage, newWidth, newHeight);

        // Update the view with the new downscaled image
        view.setImageIcon(new ImageIcon(currentImage.toBufferedImage()));
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(view,
            "Invalid input. Please enter numeric values for Width and Height.",
            "Input Error",
            JOptionPane.ERROR_MESSAGE);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(view, "Error during downscaling: "
                + ex.getMessage(), "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }


  private Image loadPPMImage(String filePath) throws IOException {
    Scanner sc = new Scanner(new FileInputStream(filePath));
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (!s.isEmpty() && s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }
    sc = new Scanner(builder.toString());
    if (!sc.next().equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    sc.nextInt(); // max color value (assumed to be 255)
    int[][][] pixels = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixels[i][j][0] = sc.nextInt(); // Red
        pixels[i][j][1] = sc.nextInt(); // Green
        pixels[i][j][2] = sc.nextInt(); // Blue
      }
    }
    return new Image(width, height, pixels);
  }

  private void savePPMImage(String filePath, Image image) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(filePath)) {
      fos.write(("P3\n" + image.getWidth() + " " + image.getHeight() + "\n255\n").getBytes());
      for (int i = 0; i < image.getHeight(); i++) {
        for (int j = 0; j < image.getWidth(); j++) {
          int[] pixel = image.getPixel(i, j);
          fos.write((pixel[0] + " " + pixel[1] + " " + pixel[2] + "\n").getBytes());
        }
      }
    }
  }

  private Image loadStandardImage(String filePath) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new File(filePath));
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();

    int[][][] pixels = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgb = bufferedImage.getRGB(j, i);
        pixels[i][j][0] = (rgb >> 16) & 0xFF; // Red
        pixels[i][j][1] = (rgb >> 8) & 0xFF;  // Green
        pixels[i][j][2] = rgb & 0xFF;         // Blue
      }
    }

    return new Image(width, height, pixels);
  }

  private void saveStandardImage(String filePath, Image image) throws IOException {
    BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int[] pixel = image.getPixel(i, j);
        int rgb = (pixel[0] << 16) | (pixel[1] << 8) | pixel[2];
        bufferedImage.setRGB(j, i, rgb);
      }
    }

    ImageIO.write(bufferedImage, "png", new File(filePath));
  }
}
