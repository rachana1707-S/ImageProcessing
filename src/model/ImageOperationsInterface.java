package model;

/**
 * This interface provides various image processing operations for the {@link Image} class.
 */
public interface ImageOperationsInterface {

  /**
   * Flips the given image horizontally.
   *
   * @param image the image to be flipped
   * @return a new image that is the horizontal flip of the original
   */
  Image flipHorizontal(Image image);

  /**
   * Flips the given image vertically.
   *
   * @param image the image to be flipped
   * @return a new image that is the vertical flip of the original
   */
  Image flipVertical(Image image);

  /**
   * Visualizes the red component of the image.
   *
   * @param image the image to visualize
   * @return a new image with only the red component displayed
   */
  Image visualizeRedComponent(Image image);

  /**
   * Visualizes the green component of the image.
   *
   * @param image the image to visualize
   * @return a new image with only the green component displayed
   */
  Image visualizeGreenComponent(Image image);

  /**
   * Visualizes the blue component of the image.
   *
   * @param image the image to visualize
   * @return a new image with only the blue component displayed
   */
  Image visualizeBlueComponent(Image image);

  /**
   * Combines red, green, and blue images into a single image.
   *
   * @param redImage   the image containing the red component
   * @param greenImage the image containing the green component
   * @param blueImage  the image containing the blue component
   * @return a new image with combined RGB values
   */
  Image combineRGB(Image redImage, Image greenImage, Image blueImage);

  /**
   * Gets the maximum value from each pixel's RGB components.
   *
   * @param image the image to process
   * @return a new image where each pixel is the maximum RGB value
   */
  Image getValue(Image image);

  /**
   * Computes the intensity of each pixel in the given image.
   *
   * @param image The input image to process.
   * @return A new image where each pixel's RGB values represent its intensity.
   */
  Image getIntensity(Image image);

  /**
   * Calculates the luma of each pixel in the provided image.
   *
   * @param image The input image to process.
   * @return A new image where each pixel's RGB values represent its luma.
   */
  Image getLuma(Image image);

  /**
   * Applies a sepia filter to the image.
   *
   * @param image        the image to apply the sepia effect
   * @param splitPercent the percentage of the image to apply the sepia effect to
   * @return a new image with the sepia filter applied
   */
  Image applySepia(Image image, int splitPercent);

  /**
   * Converts the image to greyscale.
   *
   * @param image        the image to convert
   * @param splitPercent the percentage of the image to apply the greyscale effect to
   * @return a new image in greyscale
   */
  Image applyGreyscale(Image image, int splitPercent);

  /**
   * Adjusts the brightness of the image by a given increment.
   *
   * @param image     the image to adjust
   * @param increment the amount to adjust brightness (can be negative)
   * @return a new image with adjusted brightness
   */
  Image adjustBrightness(Image image, int increment);

  /**
   * Applies a Gaussian blur to the specified image.
   *
   * @param image        The input image to blur.
   * @param splitPercent the percentage of the image to apply the blur effect to
   * @return A new image that has been blurred.
   */
  Image blur(Image image, int splitPercent);

  /**
   * Enhances the sharpness of the given image.
   *
   * @param image        The input image to sharpen.
   * @param splitPercent the percentage of the image to apply the sharpen effect to
   * @return A new image with enhanced sharpness.
   */
  Image sharpen(Image image, int splitPercent);

  /**
   * Applies a convolution filter to the input image using the specified kernel.
   *
   * @param image  The input image to filter.
   * @param kernel The convolution kernel to apply.
   * @return A new image after applying the filter.
   */
  Image applyFilter(Image image, double[][] kernel);

  /**
   * Creates a histogram based on the pixel intensity spread across the red, green, and blue
   * channels.
   *
   * @param image The image to generate the histogram from.
   * @return A new Image object representing the generated histogram.
   */
  Image generateHistogram(Image image);

  /**
   * Corrects the colors of an image by modifying the RGB values based on the histogram's peak
   * values.
   *
   * @param image        The image to apply color correction on.
   * @param splitPercent The percentage of the image to apply the correction to.
   * @return A new Image with adjusted colors.
   */
  Image colorCorrect(Image image, int splitPercent);

  /**
   * Modifies the levels of an image's RGB channels based on the specified black, middle, and white
   * points.
   *
   * @param image        The input image to be modified.
   * @param b            The black point used for the level adjustment.
   * @param m            The middle point used for the level adjustment.
   * @param w            The white point used for the level adjustment.
   * @param splitPercent The percentage of the image where the adjustment will be applied.
   * @return A new Image with modified levels.
   */
  Image levelsAdjust(Image image, int b, int m, int w, int splitPercent);

  /**
   * Applies a filter to an image using a specified kernel, affecting only the portion of the image
   * beyond the split point.
   *
   * @param image        The image to be processed.
   * @param kernel       The filter kernel to be applied.
   * @param splitPercent The percentage of the image to apply the filter to.
   * @return A new Image with the filter applied.
   */
  Image applySplitFilter(Image image, double[][] kernel, int splitPercent);

  /**
   * Compresses an image using the Haar wavelet transform and applies a threshold to eliminate small
   * values.
   *
   * @param image          The image to be compressed.
   * @param thresholdValue The threshold below which pixel values will be set to zero.
   * @return A new Image with the compressed pixel values.
   */
  Image compress(Image image, double thresholdValue);
}
