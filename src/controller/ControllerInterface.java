package controller;

import java.io.IOException;

/**
 * Interface for managing image operations and user interactions.
 */
public interface ControllerInterface {

  /**
   * Reads a script file from the specified path and executes the commands within it.
   *
   * @param scriptFilePath The path to the script file containing commands.
   * @throws IllegalArgumentException If the script file cannot be found or read.
   */
  void executeScriptFromFile(String scriptFilePath);

  /**
   * Reads commands from a specified script file and returns them as an array of command strings.
   * This method ignores comment lines (starting with '#') and empty lines.
   *
   * @param scriptFilePath The path to the script file.
   * @return An array of command strings to be executed.
   * @throws IllegalArgumentException If the script file is not found.
   */
  String[] readScript(String scriptFilePath) throws IllegalArgumentException;

  /**
   * Executes a series of image commands from a script.
   *
   * @param commands an array of command strings to execute
   * @throws IOException if an I/O error occurs during image processing
   */
  void executeScript(String[] commands) throws IOException;

  /**
   * Loads an image from the specified path and stores it with a given name.
   *
   * @param parts the command parts containing path and name
   */
  void handleLoad(String[] parts);

  /**
   * Saves the specified image to the given path.
   *
   * @param parts the command parts containing save path and image name
   */
  void handleSave(String[] parts);

  /**
   * Flips the specified image horizontally or vertically and saves it with a new name.
   *
   * @param parts        the command parts containing image names
   * @param isHorizontal true for horizontal flip, false for vertical flip
   */
  void handleFlip(String[] parts, boolean isHorizontal);

  /**
   * Extracts a specific component (value, intensity, or luma) from the image.
   *
   * @param parts         the command parts containing image names
   * @param componentType the type of component to extract
   */
  void handleComponent(String[] parts, String componentType);

  /**
   * Brightens the specified image by a given increment and saves it with a new name.
   *
   * @param parts the command parts containing increment and image names
   */
  void handleBrighten(String[] parts);

  /**
   * Extracts a specific color component (red, green, or blue) from the image.
   *
   * @param parts          the command parts containing image names
   * @param colorComponent the color component to extract
   */
  void handleColorComponent(String[] parts, String colorComponent);

  /**
   * Splits the specified image into its red, green, and blue components.
   *
   * @param parts the command parts containing image names for the split
   */
  void handleRgbSplit(String[] parts);

  /**
   * Combines specified red, green, and blue images into a single RGB image.
   *
   * @param parts the command parts containing image names for combining
   */
  void handleRgbCombine(String[] parts);

  /**
   * Applies sepia or greyscale effect to the specified image.
   *
   * @param parts   the command parts containing image names
   * @param isSepia true for sepia effect, false for greyscale
   */
  void handleSepiaOrGreyscale(String[] parts, boolean isSepia);

  /**
   * Applies blur or sharpen effect to the specified image.
   *
   * @param parts  the command parts containing image names
   * @param isBlur true for blur effect, false for sharpen
   */
  void handleBlurOrSharpen(String[] parts, boolean isBlur);

  /**
   * Generates a histogram for the specified image.
   *
   * @param parts the command parts containing image names for the histogram
   */
  void handleHistogram(String[] parts);

  /**
   * Applies color correction to the specified image.
   *
   * @param parts the command parts containing image names and split percentage for color
   *              correction
   */
  void handleColorCorrect(String[] parts);

  /**
   * Adjusts levels in the specified image using given black, middle, and white points.
   *
   * @param parts the command parts containing levels adjustment parameters
   */
  void handleLevelsAdjust(String[] parts);

  /**
   * Compresses the specified image using the Haar wavelet transform.
   *
   * @param parts the command parts containing threshold percentage and image names
   */
  void handleCompress(String[] parts);
}
