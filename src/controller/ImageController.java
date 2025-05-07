package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.Image;
import model.ImageOperations;
import model.ImageOperationsInterface;
import view.ImageView;
import view.ViewInterface;

/**
 * Controller class for managing image operations and user interactions.
 */
public class ImageController implements ControllerInterface {

  private final ViewInterface view;
  private final ImageOperationsInterface imageOps;
  private final Map<String, Image> images = new HashMap<>();

  /**
   * Constructs an ImageController with the specified view.
   *
   * @param view the view for displaying messages and errors
   */
  public ImageController(ImageView view) {
    this.view = view;
    this.imageOps = new ImageOperations();
  }

  /**
   * Reads a script file from the specified path and executes the commands within it.
   *
   * @param scriptFilePath The path to the script file containing commands.
   * @throws IllegalArgumentException If the script file cannot be found or read.
   */
  public void executeScriptFromFile(String scriptFilePath) {
    String[] commands = readScript(scriptFilePath);
    try {
      executeScript(commands);
    } catch (IOException e) {
      view.displayError("Error executing script: " + e.getMessage());
    }
  }

  /**
   * Reads commands from a specified script file and returns them as an array of command strings.
   * This method ignores comment lines (starting with '#') and empty lines.
   *
   * @param scriptFilePath The path to the script file.
   * @return An array of command strings to be executed.
   * @throws IllegalArgumentException If the script file is not found.
   */
  public String[] readScript(String scriptFilePath) throws IllegalArgumentException {
    try {
      Scanner scanner = new Scanner(new File(scriptFilePath));
      StringBuilder commands = new StringBuilder();

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        if (!line.startsWith("#") && !line.isEmpty()) {
          commands.append(line).append("\n");
        }
      }
      scanner.close();
      return commands.toString().split("\n");
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Script file not found.");
    }
  }

  /**
   * Executes a series of image commands from a script.
   *
   * @param commands an array of command strings to execute
   * @throws IOException if an I/O error occurs during image processing
   */
  public void executeScript(String[] commands) throws IOException {
    for (String command : commands) {
      String[] parts = command.split(" ");
      String operation = parts[0];

      switch (operation) {
        case "load":
          handleLoad(parts);
          break;

        case "save":
          handleSave(parts);
          break;

        case "horizontal-flip":
          handleFlip(parts, true);
          break;

        case "vertical-flip":
          handleFlip(parts, false);
          break;

        case "value-component":
          handleComponent(parts, "value");
          break;

        case "intensity-component":
          handleComponent(parts, "intensity");
          break;

        case "luma-component":
          handleComponent(parts, "luma");
          break;

        case "brighten":
          handleBrighten(parts);
          break;

        case "red-component":
        case "green-component":
        case "blue-component":
          handleColorComponent(parts, operation);
          break;

        case "rgb-split":
          handleRgbSplit(parts);
          break;

        case "rgb-combine":
          handleRgbCombine(parts);
          break;

        case "sepia":
          handleSepiaOrGreyscale(parts, true);
          break;

        case "greyscale":
          handleSepiaOrGreyscale(parts, false);
          break;

        case "blur":
          handleBlurOrSharpen(parts, true);
          break;

        case "sharpen":
          handleBlurOrSharpen(parts, false);
          break;

        case "histogram":
          handleHistogram(parts);
          break;

        case "color-correct":
          handleColorCorrect(parts);
          break;

        case "levels-adjust":
          handleLevelsAdjust(parts);
          break;

        case "compress":
          handleCompress(parts);
          break;

        default:
          view.displayError("Unknown command: " + operation);
          break;
      }
    }
  }

  /**
   * Loads an image from the specified path and stores it with a given name.
   *
   * @param parts the command parts containing path and name
   */
  public void handleLoad(String[] parts) {
    if (parts.length == 3) {
      String imagePath = parts[1];
      String imageName = parts[2];

      try {
        Image image =
            imagePath.endsWith(".ppm") ? loadPPMImage(imagePath) : loadStandardImage(imagePath);
        images.put(imageName, image);
        view.displayMessage(imageName + " loaded.");
      } catch (IOException e) {
        view.displayError("Error loading image: " + e.getMessage());
      }
    } else {
      view.displayError("Invalid load command.");
    }
  }

  /**
   * Saves the specified image to the given path.
   *
   * @param parts the command parts containing save path and image name
   */
  public void handleSave(String[] parts) {
    if (parts.length == 3) {
      String savePath = parts[1];
      String imageName = parts[2];

      if (images.containsKey(imageName)) {
        try {
          Image image = images.get(imageName);
          if (savePath.endsWith(".ppm")) {
            savePPMImage(savePath, image);
          } else {
            saveStandardImage(savePath, image);
          }
          view.displayMessage(imageName + " saved to " + savePath);
        } catch (IOException e) {
          view.displayError("Error saving image: " + e.getMessage());
        }
      } else {
        view.displayError("Image not found: " + imageName);
      }
    } else {
      view.displayError("Invalid save command.");
    }
  }

  /**
   * Flips the specified image horizontally or vertically and saves it with a new name.
   *
   * @param parts        the command parts containing image names
   * @param isHorizontal true for horizontal flip, false for vertical flip
   */
  public void handleFlip(String[] parts, boolean isHorizontal) {
    if (parts.length == 3) {
      String imageName = parts[1];
      String destName = parts[2];

      if (images.containsKey(imageName)) {
        Image flippedImage = isHorizontal ? imageOps.flipHorizontal(images.get(imageName))
            : imageOps.flipVertical(images.get(imageName));
        images.put(destName, flippedImage);
        view.displayMessage(
            imageName + (isHorizontal ? " flipped horizontally" : " flipped vertically")
                + " and saved as " + destName);
      } else {
        view.displayError("Image not found: " + imageName);
      }
    } else {
      view.displayError("Invalid flip command.");
    }
  }

  /**
   * Extracts a specific component (value, intensity, or luma) from the image.
   *
   * @param parts         the command parts containing image names
   * @param componentType the type of component to extract
   */
  public void handleComponent(String[] parts, String componentType) {
    if (parts.length == 3) {
      String imageName = parts[1];
      String destName = parts[2];

      if (images.containsKey(imageName)) {
        Image componentImage;
        switch (componentType) {
          case "value":
            componentImage = imageOps.getValue(images.get(imageName));
            break;
          case "intensity":
            componentImage = imageOps.getIntensity(images.get(imageName));
            break;
          case "luma":
            componentImage = imageOps.getLuma(images.get(imageName));
            break;
          default:
            componentImage = null;
            break;
        }

        if (componentImage != null) {
          images.put(destName, componentImage);
          view.displayMessage(componentType + " image of " + imageName + " saved as " + destName);
        }
      } else {
        view.displayError("Image not found: " + imageName);
      }
    } else {
      view.displayError("Invalid " + componentType + " command.");
    }
  }

  /**
   * Brightens the specified image by a given increment and saves it with a new name.
   *
   * @param parts the command parts containing increment and image names
   */
  public void handleBrighten(String[] parts) {
    if (parts.length == 4) {
      int increment = Integer.parseInt(parts[1]);
      String imageName = parts[2];
      String destName = parts[3];

      if (images.containsKey(imageName)) {
        Image brightenedImage = imageOps.adjustBrightness(images.get(imageName), increment);
        images.put(destName, brightenedImage);
        view.displayMessage(
            imageName + " brightened by " + increment + " and saved as " + destName);
      } else {
        view.displayError("Image not found: " + imageName);
      }
    } else {
      view.displayError("Invalid brighten command.");
    }
  }

  /**
   * Extracts a specific color component (red, green, or blue) from the image.
   *
   * @param parts          the command parts containing image names
   * @param colorComponent the color component to extract
   */
  public void handleColorComponent(String[] parts, String colorComponent) {
    if (parts.length == 3) {
      String imageName = parts[1];
      String destName = parts[2];

      if (images.containsKey(imageName)) {
        Image componentImage;
        switch (colorComponent) {
          case "red-component":
            componentImage = imageOps.visualizeRedComponent(images.get(imageName));
            break;
          case "green-component":
            componentImage = imageOps.visualizeGreenComponent(images.get(imageName));
            break;
          case "blue-component":
            componentImage = imageOps.visualizeBlueComponent(images.get(imageName));
            break;
          default:
            componentImage = null;
            break;
        }

        if (componentImage != null) {
          images.put(destName, componentImage);
          view.displayMessage(colorComponent + " of " + imageName + " saved as " + destName);
        } else {
          view.displayError("Invalid color component: " + colorComponent);
        }
      } else {
        view.displayError("Image not found: " + imageName);
      }
    } else {
      view.displayError("Invalid " + colorComponent + " command.");
    }
  }

  /**
   * Splits the specified image into its red, green, and blue components.
   *
   * @param parts the command parts containing image names for the split
   */
  public void handleRgbSplit(String[] parts) {
    if (parts.length == 5) {
      String imageName = parts[1];
      String destNameRed = parts[2];
      String destNameGreen = parts[3];
      String destNameBlue = parts[4];

      if (images.containsKey(imageName)) {
        images.put(destNameRed, imageOps.visualizeRedComponent(images.get(imageName)));
        images.put(destNameGreen, imageOps.visualizeGreenComponent(images.get(imageName)));
        images.put(destNameBlue, imageOps.visualizeBlueComponent(images.get(imageName)));

        view.displayMessage(imageName + " split into RGB components.");
      } else {
        view.displayError("Image not found: " + imageName);
      }
    } else {
      view.displayError("Invalid RGB split command.");
    }
  }

  /**
   * Combines specified red, green, and blue images into a single RGB image.
   *
   * @param parts the command parts containing image names for combining
   */
  public void handleRgbCombine(String[] parts) {
    if (parts.length == 5) {
      String destName = parts[1];
      String redImageName = parts[2];
      String greenImageName = parts[3];
      String blueImageName = parts[4];

      if (images.containsKey(redImageName) && images.containsKey(greenImageName)
          && images.containsKey(blueImageName)) {
        Image combinedImage = imageOps.combineRGB(images.get(redImageName),
            images.get(greenImageName), images.get(blueImageName));
        images.put(destName, combinedImage);
        view.displayMessage("RGB components combined and saved as " + destName);
      } else {
        view.displayError("One or more images not found.");
      }
    } else {
      view.displayError("Invalid rgb-combine command.");
    }
  }

  /**
   * Applies sepia or greyscale effect to the specified image.
   *
   * @param parts   the command parts containing image names
   * @param isSepia true for sepia effect, false for greyscale
   */
  public void handleSepiaOrGreyscale(String[] parts, boolean isSepia) {
    if (parts.length >= 3) {
      String imageName = parts[1];
      String destName = parts[2];
      int splitPercent =
          parts.length == 5 && "split".equals(parts[3]) ? Integer.parseInt(parts[4]) : 100;

      if (images.containsKey(imageName)) {
        Image processedImage = isSepia
            ? imageOps.applySepia(images.get(imageName), splitPercent)
            : imageOps.applyGreyscale(images.get(imageName), splitPercent);
        images.put(destName, processedImage);
        view.displayMessage(
            (isSepia ? "Sepia" : "Greyscale") + " applied to " + imageName + " with split at "
                + splitPercent + "% and saved as " + destName);
      } else {
        view.displayError("Image not found: " + imageName);
      }
    } else {
      view.displayError("Invalid " + (isSepia ? "sepia" : "greyscale") + " command.");
    }
  }


  /**
   * Applies blur or sharpen effect to the specified image.
   *
   * @param parts  the command parts containing image names
   * @param isBlur true for blur effect, false for sharpen
   */
  public void handleBlurOrSharpen(String[] parts, boolean isBlur) {
    if (parts.length >= 3) {
      String imageName = parts[1];
      String destName = parts[2];
      int splitPercent =
          parts.length == 5 && "split".equals(parts[3]) ? Integer.parseInt(parts[4]) : 100;

      if (images.containsKey(imageName)) {
        Image processedImage = isBlur
            ? imageOps.blur(images.get(imageName), splitPercent)
            : imageOps.sharpen(images.get(imageName), splitPercent);
        images.put(destName, processedImage);
        view.displayMessage(
            (isBlur ? "Blur" : "Sharpen") + " applied to " + imageName + " with split at "
                + splitPercent + "% and saved as " + destName);
      } else {
        view.displayError("Image not found: " + imageName);
      }
    } else {
      view.displayError("Invalid " + (isBlur ? "blur" : "sharpen") + " command.");
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

    String format = filePath.substring(filePath.lastIndexOf(".") + 1);
    ImageIO.write(bufferedImage, format, new File(filePath));
  }

  /**
   * Generates a histogram for the specified image.
   *
   * @param parts the command parts containing image names for the histogram
   */
  public void handleHistogram(String[] parts) {
    if (parts.length == 3) {
      String imageName = parts[1];
      String destName = parts[2];

      if (images.containsKey(imageName)) {
        Image histogramImage = imageOps.generateHistogram(images.get(imageName));
        images.put(destName, histogramImage);
        view.displayMessage("Histogram for " + imageName + " saved as " + destName);
      } else {
        view.displayError("Image not found: " + imageName);
      }
    } else {
      view.displayError("Invalid histogram command.");
    }
  }

  /**
   * Applies color correction to the specified image.
   *
   * @param parts the command parts containing image names and split percentage for color
   *              correction
   */
  public void handleColorCorrect(String[] parts) {
    if (parts.length >= 3) {
      String imageName = parts[1];
      String destName = parts[2];
      int splitPercent =
          parts.length == 5 && "split".equals(parts[3]) ? Integer.parseInt(parts[4]) : 100;

      if (images.containsKey(imageName)) {
        Image colorCorrectedImage = imageOps.colorCorrect(images.get(imageName), splitPercent);
        images.put(destName, colorCorrectedImage);
        view.displayMessage(
            "Color-corrected image of " + imageName + " with split at " + splitPercent
                + "% saved as " + destName);
      } else {
        view.displayError("Image not found: " + imageName);
      }
    } else {
      view.displayError("Invalid color-correct command.");
    }
  }

  /**
   * Adjusts levels in the specified image using given black, middle, and white points.
   *
   * @param parts the command parts containing levels adjustment parameters
   */
  public void handleLevelsAdjust(String[] parts) {
    if (parts.length >= 6) {
      int b = Integer.parseInt(parts[1]);
      int m = Integer.parseInt(parts[2]);
      int w = Integer.parseInt(parts[3]);
      String imageName = parts[4];
      String destName = parts[5];
      int splitPercent =
          parts.length == 8 && "split".equals(parts[6]) ? Integer.parseInt(parts[7]) : 100;

      if (b >= 0 && b < m && m < w && w <= 255) {
        if (images.containsKey(imageName)) {
          Image levelsAdjusted = imageOps.levelsAdjust(images.get(imageName), b, m, w,
              splitPercent);
          images.put(destName, levelsAdjusted);
          view.displayMessage(
              "Levels-adjusted image of " + imageName + " with split at " + splitPercent
                  + "% saved as " + destName);
        } else {
          view.displayError("Image not found: " + imageName);
        }
      } else {
        view.displayError("Invalid levels-adjust parameters.");
      }
    } else {
      view.displayError("Invalid levels-adjust command.");
    }
  }

  /**
   * Compresses the specified image using the Haar wavelet transform.
   *
   * @param parts the command parts containing threshold percentage and image names
   */
  public void handleCompress(String[] parts) {
    if (parts.length == 4) {
      double thresholdPercentage = Double.parseDouble(parts[1]);
      String imageName = parts[2];
      String destName = parts[3];

      if (images.containsKey(imageName)) {
        Image compressedImage = imageOps.compress(images.get(imageName), thresholdPercentage);
        images.put(destName, compressedImage);
        view.displayMessage("Compressed image " + imageName + " saved as " + destName);
      } else {
        view.displayError("Image not found: " + imageName);
      }
    } else {
      view.displayError("Invalid compress command.");
    }
  }


}
