package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * This class provides various image processing operations for the {@link Image} class.
 */
public class ImageOperations implements ImageOperationsInterface {

  /**
   * Flips the given image horizontally.
   *
   * @param image the image to be flipped
   * @return a new image that is the horizontal flip of the original
   */
  public Image flipHorizontal(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    int[][][] flipped = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        flipped[i][j] = image.getPixel(i, width - 1 - j);
      }
    }
    return new Image(width, height, flipped);
  }

  /**
   * Flips the given image vertically.
   *
   * @param image the image to be flipped
   * @return a new image that is the vertical flip of the original
   */
  public Image flipVertical(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    int[][][] flipped = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        flipped[height - 1 - i][j] = image.getPixel(i, j);
      }
    }
    return new Image(width, height, flipped);
  }

  /**
   * Visualizes the red component of the image.
   *
   * @param image the image to visualize
   * @return a new image with only the red component displayed
   */
  public Image visualizeRedComponent(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    int[][][] result = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = image.getPixel(i, j)[0];
        result[i][j] = new int[]{red, red, red};
      }
    }
    return new Image(width, height, result);
  }

  /**
   * Visualizes the green component of the image.
   *
   * @param image the image to visualize
   * @return a new image with only the green component displayed
   */
  public Image visualizeGreenComponent(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    int[][][] result = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int green = image.getPixel(i, j)[1];
        result[i][j] = new int[]{green, green, green};
      }
    }
    return new Image(width, height, result);
  }

  /**
   * Visualizes the blue component of the image.
   *
   * @param image the image to visualize
   * @return a new image with only the blue component displayed
   */
  public Image visualizeBlueComponent(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    int[][][] result = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int blue = image.getPixel(i, j)[2];
        result[i][j] = new int[]{blue, blue, blue};
      }
    }

    return new Image(width, height, result);
  }


  /**
   * Combines red, green, and blue images into a single image.
   *
   * @param redImage   the image containing the red component
   * @param greenImage the image containing the green component
   * @param blueImage  the image containing the blue component
   * @return a new image with combined RGB values
   */
  public Image combineRGB(Image redImage, Image greenImage, Image blueImage) {
    if (redImage.getWidth() != greenImage.getWidth() || redImage.getWidth() != blueImage.getWidth()
        ||
        redImage.getHeight() != greenImage.getHeight()
        || redImage.getHeight() != blueImage.getHeight()) {
      throw new IllegalArgumentException("All images must have the same dimensions to combine.");
    }

    int height = redImage.getHeight();
    int width = redImage.getWidth();
    int[][][] combinedPixels = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        combinedPixels[i][j][0] = redImage.getPixel(i, j)[0];
        combinedPixels[i][j][1] = greenImage.getPixel(i, j)[1];
        combinedPixels[i][j][2] = blueImage.getPixel(i, j)[2];
      }
    }

    return new Image(width, height, combinedPixels);
  }

  /**
   * Gets the maximum value from each pixel's RGB components.
   *
   * @param image the image to process
   * @return a new image where each pixel is the maximum RGB value
   */
  public Image getValue(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    int[][][] result = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] rgb = image.getPixel(i, j);
        int maxComponent = Math.max(rgb[0], Math.max(rgb[1], rgb[2]));
        result[i][j] = new int[]{maxComponent, maxComponent, maxComponent};
      }
    }

    return new Image(width, height, result);
  }

  /**
   * Computes the intensity of each pixel in the given image. The intensity is calculated as the
   * average of the RGB components.
   *
   * @param image The input image to process.
   * @return A new image where each pixel's RGB values represent its intensity.
   */
  public Image getIntensity(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    int[][][] result = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] rgb = image.getPixel(i, j);
        int intensity = (rgb[0] + rgb[1] + rgb[2]) / 3;
        result[i][j] = new int[]{intensity, intensity, intensity};
      }
    }

    return new Image(width, height, result);
  }

  /**
   * Calculates the luma of each pixel in the provided image. Luma is derived from the RGB
   * components using standard weights for perceived brightness.
   *
   * @param image The input image to process.
   * @return A new image where each pixel's RGB values represent its luma.
   */
  public Image getLuma(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    int[][][] result = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] rgb = image.getPixel(i, j);
        int luma = (int) (0.2126 * rgb[0] + 0.7152 * rgb[1] + 0.0722 * rgb[2]);
        result[i][j] = new int[]{luma, luma, luma};
      }
    }

    return new Image(width, height, result);
  }

  /**
   * Applies a sepia filter to the image.
   *
   * @param image the image to apply the sepia effect
   * @return a new image with the sepia filter applied
   */
  public Image applySepia(Image image, int splitPercent) {
    int height = image.getHeight();
    int width = image.getWidth();
    int splitColumn = (width * splitPercent) / 100;
    int[][][] sepia = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] rgb = image.getPixel(i, j);
        if (j >= splitColumn) {
          int red = rgb[0];
          int green = rgb[1];
          int blue = rgb[2];

          sepia[i][j][0] = (int) Math.min(255, 0.393 * red + 0.769 * green + 0.189 * blue);
          sepia[i][j][1] = (int) Math.min(255, 0.349 * red + 0.686 * green + 0.168 * blue);
          sepia[i][j][2] = (int) Math.min(255, 0.272 * red + 0.534 * green + 0.131 * blue);
        } else {
          sepia[i][j] = rgb.clone();
        }
      }
    }
    return new Image(width, height, sepia);
  }


  /**
   * Converts the image to greyscale.
   *
   * @param image the image to convert
   * @return a new image in greyscale
   */
  public Image applyGreyscale(Image image, int splitPercent) {
    int height = image.getHeight();
    int width = image.getWidth();
    int splitColumn = (width * splitPercent) / 100;
    int[][][] greyscale = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] rgb = image.getPixel(i, j);
        if (j >= splitColumn) {
          int grey = (int) Math.min(255, 0.2126 * rgb[0] + 0.7152 * rgb[1] + 0.0722 * rgb[2]);
          greyscale[i][j] = new int[]{grey, grey, grey};
        } else {
          greyscale[i][j] = rgb.clone();
        }
      }
    }
    return new Image(width, height, greyscale);
  }


  /**
   * Adjusts the brightness of the image by a given increment.
   *
   * @param image     the image to adjust
   * @param increment the amount to adjust brightness (can be negative)
   * @return a new image with adjusted brightness
   */
  public Image adjustBrightness(Image image, int increment) {
    int height = image.getHeight();
    int width = image.getWidth();
    int[][][] adjusted = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] rgb = image.getPixel(i, j);
        adjusted[i][j] = new int[]{
            Math.max(0, Math.min(255, rgb[0] + increment)),
            Math.max(0, Math.min(255, rgb[1] + increment)),
            Math.max(0, Math.min(255, rgb[2] + increment))
        };
      }
    }
    return new Image(width, height, adjusted);
  }

  /**
   * Applies a Gaussian blur to the specified image. The method uses a 3x3 kernel to reduce image
   * noise and detail.
   *
   * @param image The input image to blur.
   * @return A new image that has been blurred.
   */
  public Image blur(Image image, int splitPercent) {
    double[][] kernel = {
        {0.0625, 0.125, 0.0625},
        {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}
    };
    return applySplitFilter(image, kernel, splitPercent);
  }


  /**
   * Enhances the sharpness of the given image. A 5x5 sharpening kernel is applied to emphasize
   * edges and fine details.
   *
   * @param image The input image to sharpen.
   * @return A new image with enhanced sharpness.
   */
  public Image sharpen(Image image, int splitPercent) {
    double[][] kernel = {
        {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0},
        {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0},
        {-1.0 / 8.0, 1.0 / 4.0, 1.0, 1.0 / 4.0, -1.0 / 8.0},
        {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0},
        {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0}
    };
    return applySplitFilter(image, kernel, splitPercent);
  }


  /**
   * Applies a convolution filter to the input image using the specified kernel. This method
   * processes each pixel based on the kernel's weights, resulting in a new image.
   *
   * @param image  The input image to filter.
   * @param kernel The convolution kernel to apply.
   * @return A new image after applying the filter.
   */
  public Image applyFilter(Image image, double[][] kernel) {
    int height = image.getHeight();
    int width = image.getWidth();
    int[][][] filtered = new int[height][width][3];

    int kernelHeight = kernel.length;
    int kernelWidth = kernel[0].length;
    int offset = kernelHeight / 2;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int c = 0; c < 3; c++) {
          double newValue = 0.0;

          for (int ki = 0; ki < kernelHeight; ki++) {
            for (int kj = 0; kj < kernelWidth; kj++) {
              int pixelRow = i + ki - offset;
              int pixelCol = j + kj - offset;

              if (pixelRow >= 0 && pixelRow < height && pixelCol >= 0 && pixelCol < width) {
                newValue += image.getPixel(pixelRow, pixelCol)[c] * kernel[ki][kj];
              }
            }
          }

          filtered[i][j][c] = Math.max(0, Math.min(255, (int) Math.round(newValue)));
        }
      }
    }

    return new Image(width, height, filtered);
  }


  /**
   * Creates a histogram based on the pixel intensity spread across the red, green, and blue
   * channels of the provided image.
   *
   * @param image The image to generate the histogram from.
   * @return A new Image object representing the generated histogram.
   * @throws IllegalArgumentException If the input image is null or has invalid RGB values.
   * @throws IllegalStateException    If an error occurs while creating the Graphics2D object.
   */

  public Image generateHistogram(Image image) {
    // Check for null image
    if (image == null) {
      throw new IllegalArgumentException("Input image cannot be null");
    }

    int[][] histogram = new int[3][256];  // Arrays for R, G, B channel histograms

    try {
      // Process each pixel of the image
      for (int i = 0; i < image.getHeight(); i++) {
        for (int j = 0; j < image.getWidth(); j++) {
          int[] rgb = image.getPixel(i, j);

          // Check if RGB values are within valid range
          if (rgb[0] < 0 || rgb[0] > 255 || rgb[1] < 0 || rgb[1] > 255 || rgb[2] < 0
              || rgb[2] > 255) {
            throw new IllegalArgumentException("RGB values must be in the range 0-255");
          }

          // Update histogram for each channel
          histogram[0][rgb[0]]++;  // Red
          histogram[1][rgb[1]]++;  // Green
          histogram[2][rgb[2]]++;  // Blue
        }
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Pixel values out of bounds", e);
    }

    // Create 256x256 BufferedImage for histogram visualization
    BufferedImage histogramImage = new BufferedImage(256, 256,
        BufferedImage.TYPE_INT_RGB);
    Graphics2D g = histogramImage.createGraphics();

    if (g == null) {
      throw new IllegalStateException("Failed to create Graphics2D object");
    }

    try {
      g.setColor(Color.WHITE);  // Background color
      g.fillRect(0, 0, 256, 256);  // Fill the image with white background

      // Scale histogram to fit 256x256 image
      for (int c = 0; c < 3; c++) {
        g.setColor(c == 0 ? Color.RED
            : (c == 1 ? Color.GREEN : Color.BLUE));  // Set color for each channel
        int max = Arrays.stream(histogram[c]).max()
            .orElse(1);  // Find maximum frequency for scaling
        for (int i = 0; i < 256; i++) {
          int scaledHeight =
              (histogram[c][i] * 255) / max;  // Scale the height of the histogram bar
          g.drawLine(i, 255, i, 255 - scaledHeight);  // Draw the histogram bar
        }
      }
    } finally {
      g.dispose();  // Release resources
    }

    // Convert BufferedImage to custom Image
    return convertBufferedImageToImage(histogramImage);
  }

  /**
   * Transforms a BufferedImage into a custom Image object.
   *
   * @param bufferedImage The BufferedImage to be converted.
   * @return A new Image object with the pixel data from the BufferedImage.
   * @throws IllegalArgumentException If the BufferedImage is null or has invalid dimensions.
   * @throws IllegalStateException    If an error occurs while processing the BufferedImage.
   */

  private Image convertBufferedImageToImage(BufferedImage bufferedImage) {
    // Check for null BufferedImage
    if (bufferedImage == null) {
      throw new IllegalArgumentException("BufferedImage cannot be null");
    }

    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();

    // Ensure valid image dimensions
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid image dimensions");
    }

    int[][][] pixels = new int[height][width][3];  // Array to store pixel data

    try {
      // Convert BufferedImage to custom pixel array
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int rgb = bufferedImage.getRGB(j, i);
          pixels[i][j][0] = (rgb >> 16) & 0xFF; // Red
          pixels[i][j][1] = (rgb >> 8) & 0xFF;  // Green
          pixels[i][j][2] = rgb & 0xFF;         // Blue
        }
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalStateException("Error processing BufferedImage", e);
    }

    // Return new Image with the pixel data
    return new Image(width, height, pixels);
  }


  /**
   * Corrects the colors of an image by modifying the RGB values based on the histogram's peak
   * values. The correction is applied to a portion of the image determined by the specified split
   * percentage.
   *
   * @param image        The image to apply color correction on.
   * @param splitPercent The percentage of the image to apply the correction to.
   * @return A new Image with adjusted colors.
   * @throws IllegalArgumentException If the image is null or the splitPercent is outside the valid
   *                                  range.
   */

  public Image colorCorrect(Image image, int splitPercent) throws IllegalArgumentException {
    // Validate image and splitPercent
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }

    if (splitPercent < 0 || splitPercent > 100) {
      throw new IllegalArgumentException("splitPercent must be between 0 and 100.");
    }

    // Calculate histogram and find peaks
    int[][] histogram = calculateHistogram(image);
    int[] peaks = findPeaks(histogram);
    int averagePeak = Arrays.stream(peaks).sum() / peaks.length;

    int height = image.getHeight();
    int width = image.getWidth();
    int splitColumn =
        (width * splitPercent) / 100; // Determine the column to start color correction
    int[][][] correctedPixels = new int[height][width][3];  // Array to store corrected pixels

    // Process each pixel for color correction
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        try {
          int[] rgb = image.getPixel(i, j);  // Get RGB values of the current pixel

          // Apply color correction only to pixels after the splitColumn
          if (j >= splitColumn) {
            for (int k = 0; k < 3; k++) {
              int offset = averagePeak - peaks[k];  // Calculate offset based on peak difference
              correctedPixels[i][j][k] = Math.max(0,
                  Math.min(255, rgb[k] + offset));  // Apply offset and clamp to valid range
            }
          } else {
            correctedPixels[i][j] = rgb.clone();  // Copy original pixel if not affected
          }
        } catch (IndexOutOfBoundsException e) {
          throw new IllegalArgumentException(
              "Error accessing pixel data at (" + i + ", " + j + ").", e);
        }
      }
    }

    // Return the new Image with corrected pixels
    return new Image(width, height, correctedPixels);
  }


  /**
   * Computes the histogram for each RGB channel of an image.
   *
   * @param image The input image for which the histogram will be calculated.
   * @return A 2D array representing the histogram for each of the image's RGB channels.
   * @throws IllegalArgumentException If the provided image is null.
   */

  private int[][] calculateHistogram(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }

    int[][] histogram = new int[3][256];  // Initialize histogram arrays for R, G, B channels

    // Iterate over all pixels to compute histogram
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        try {
          int[] rgb = image.getPixel(i, j);  // Get RGB values of the current pixel
          histogram[0][rgb[0]]++;  // Increment count for red channel
          histogram[1][rgb[1]]++;  // Increment count for green channel
          histogram[2][rgb[2]]++;  // Increment count for blue channel
        } catch (IndexOutOfBoundsException e) {
          throw new IllegalArgumentException(
              "Error accessing pixel data at (" + i + ", " + j + ").", e);
        }
      }
    }

    // Return the computed histogram
    return histogram;
  }

  /**
   * Finds the peak values of the histogram for each color channel (ignoring extreme ends).
   *
   * @param histogram The histogram data of the image.
   * @return An array of peak values for each RGB channel.
   * @throws IllegalArgumentException If the histogram data is invalid.
   */
  private int[] findPeaks(int[][] histogram) {
    if (histogram == null || histogram.length != 3) {
      throw new IllegalArgumentException("Invalid histogram data.");
    }

    int[] peaks = new int[3];  // Array to store peak values for R, G, B channels

    // Find peak values by looking for the highest frequency in the middle range
    for (int c = 0; c < 3; c++) {
      int maxCount = 0;
      for (int i = 10; i <= 245; i++) {  // Ignore extreme pixel values to focus on middle range
        if (histogram[c][i] > maxCount) {
          maxCount = histogram[c][i];
          peaks[c] = i;  // Update peak value for the channel
        }
      }
    }

    // Return the peak values for all channels
    return peaks;
  }


  /**
   * Modifies the levels of an image's RGB channels based on the specified black, middle, and white
   * points, affecting only a portion of the image defined by the split percentage.
   *
   * @param image        The input image to be modified.
   * @param b            The black point used for the level adjustment.
   * @param m            The middle point used for the level adjustment.
   * @param w            The white point used for the level adjustment.
   * @param splitPercent The percentage of the image where the adjustment will be applied.
   * @return A new Image with modified levels.
   * @throws IllegalArgumentException If any of the input parameters are invalid.
   */

  public Image levelsAdjust(Image image, int b, int m, int w, int splitPercent) {
    // Validate input parameters
    if (splitPercent < 0) {
      throw new IllegalArgumentException("Split percent cannot be negative.");
    }
    if (splitPercent > 100) {
      throw new IllegalArgumentException("Split percent cannot be greater than 100.");
    }
    if (b > w) {
      throw new IllegalArgumentException("Black point cannot be greater than white point.");
    }

    int height = image.getHeight();
    int width = image.getWidth();
    int splitColumn =
        (width * splitPercent) / 100;  // Determine column to start applying adjustment
    int[][][] adjustedPixels = new int[height][width][3];  // Array to store adjusted pixel values

    // Process each pixel and adjust levels if within the specified split range
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] rgb = image.getPixel(i, j);  // Get RGB values of the current pixel

        if (j >= splitColumn) {
          // Apply levels adjustment only to the specified portion of the image
          for (int k = 0; k < 3; k++) {
            adjustedPixels[i][j][k] = adjustLevel(rgb[k], b, m, w);  // Adjust each color channel
          }
        } else {
          // Keep original pixels in the unadjusted part
          adjustedPixels[i][j] = rgb.clone();
        }
      }
    }

    // Return the new Image with adjusted pixel values
    return new Image(width, height, adjustedPixels);
  }

  /**
   * Adjusts a single pixel's value based on the black, middle, and white points.
   *
   * @param value The original pixel value.
   * @param b     The black point for the levels adjustment.
   * @param m     The middle point for the levels adjustment.
   * @param w     The white point for the levels adjustment.
   * @return The adjusted pixel value.
   */
  private int adjustLevel(int value, int b, int m, int w) {
    // Special case: if b, m, and w are all set to 255, return 255 (white) for all adjusted pixels
    if (b == 255 && m == 255 && w == 255) {
      return 255;
    }

    // Special case: if all levels are set to 0, return 0 for all adjusted pixels
    if (b == 0 && m == 0 && w == 0) {
      return 0;
    }

    // Calculate scaling factors based on black, middle, and white points
    double a = (128.0 - 0) / (m - b);  // Scaling factor for the region between b and m
    double b_coeff = (255.0 - 128) / (w - m);  // Scaling factor for the region between m and w

    // Adjust the pixel value based on the defined thresholds (b, m, w)
    if (value <= b) {
      return 0;  // Below black point, set to 0
    } else if (value <= m) {
      return (int) (a * (value - b));  // Between black and middle, scale linearly
    } else if (value <= w) {
      return (int) (128 + b_coeff * (value - m));  // Between middle and white, scale linearly
    }

    return 255;  // Above white point, set to 255
  }


  /**
   * Applies a filter to an image using a specified kernel, affecting only the portion of the image
   * beyond the split point.
   *
   * @param image        The image to be processed.
   * @param kernel       The filter kernel to be applied.
   * @param splitPercent The percentage of the image to apply the filter to.
   * @return A new Image with the filter applied.
   */

  public Image applySplitFilter(Image image, double[][] kernel, int splitPercent) {
    int height = image.getHeight();
    int width = image.getWidth();
    int splitColumn = (width * splitPercent) / 100;  // Column to start applying filter
    int[][][] filtered = new int[height][width][3];  // Array to store filtered pixel values

    int kernelHeight = kernel.length;
    int kernelWidth = kernel[0].length;
    int offset = kernelHeight / 2;  // Offset to handle kernel center position

    // Iterate over each pixel in the image
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (j < splitColumn) {
          // Keep original pixel values for the unfiltered portion
          filtered[i][j] = image.getPixel(i, j);
        } else {
          // Apply filter to the pixels after the split point
          for (int c = 0; c < 3; c++) {
            double newValue = 0.0;

            // Apply kernel to each pixel's RGB channel
            for (int ki = 0; ki < kernelHeight; ki++) {
              for (int kj = 0; kj < kernelWidth; kj++) {
                int pixelRow = i + ki - offset;
                int pixelCol = j + kj - offset;

                // Check if the neighboring pixel is within bounds
                if (pixelRow >= 0 && pixelRow < height && pixelCol >= 0 && pixelCol < width) {
                  newValue += image.getPixel(pixelRow, pixelCol)[c] * kernel[ki][kj];
                }
              }
            }

            // Store the filtered value after clamping it to the valid range [0, 255]
            filtered[i][j][c] = Math.max(0, Math.min(255, (int) Math.round(newValue)));
          }
        }
      }
    }

    // Return the new Image with applied filter
    return new Image(width, height, filtered);
  }


  /**
   * Calculates the next power of two that is greater than or equal to the provided number.
   *
   * @param n The number to be processed.
   * @return The smallest power of two greater than or equal to n.
   */

  private int nextPowerOfTwo(int n) {
    int power = 1;
    while (power < n) {
      power *= 2;
    }
    return power;
  }

  /**
   * Compresses an image using the Haar wavelet transform and applies a threshold to eliminate small
   * values.
   *
   * @param image          The image to be compressed.
   * @param thresholdValue The threshold below which pixel values will be set to zero.
   * @return A new Image with the compressed pixel values.
   */

  public Image compress(Image image, double thresholdValue) {
    int originalHeight = image.getHeight();
    int originalWidth = image.getWidth();

    // Pad the image dimensions to the next power of two for efficient Haar transform
    int paddedHeight = nextPowerOfTwo(originalHeight);
    int paddedWidth = nextPowerOfTwo(originalWidth);

    int[][][] compressedPixels = new int[originalHeight][originalWidth][3];

    // Process each color channel (R, G, B)
    for (int channel = 0; channel < 3; channel++) {
      double[][] channelData = new double[paddedHeight][paddedWidth];

      // Step 1: Copy pixel data to a padded double array for the transform
      for (int i = 0; i < originalHeight; i++) {
        for (int j = 0; j < originalWidth; j++) {
          channelData[i][j] = image.getPixel(i, j)[channel];
        }
      }

      // Step 2: Apply multiple levels of Haar transform (compression step)
      for (int level = 0; level < 3; level++) {  // Increase for more visible compression
        channelData = haarTransform2D(channelData);
      }

      // Step 3: Zero out values below the threshold
      for (int i = 0; i < paddedHeight; i++) {
        for (int j = 0; j < paddedWidth; j++) {
          if (Math.abs(channelData[i][j]) < thresholdValue) {
            channelData[i][j] = 0;
          }
        }
      }

      // Step 4: Apply inverse Haar transform to reconstruct the image
      for (int level = 0; level < 3; level++) {
        channelData = inverseHaarTransform2D(channelData);
      }

      // Copy the processed pixel data back to the compressed image array
      for (int i = 0; i < originalHeight; i++) {
        for (int j = 0; j < originalWidth; j++) {
          compressedPixels[i][j][channel] = Math.max(0,
              Math.min(255, (int) Math.round(channelData[i][j])));
        }
      }
    }

    return new Image(originalWidth, originalHeight, compressedPixels);
  }

  /**
   * Performs a 2D Haar wavelet transform on the input 2D array.
   *
   * @param input The 2D array representing the image data to be transformed.
   * @return A new 2D array with the Haar transform applied.
   */
  private double[][] haarTransform2D(double[][] input) {
    int rows = input.length;
    int cols = input[0].length;
    double[][] temp = new double[rows][cols];

    // Step 1: Apply Haar transform on rows
    for (int i = 0; i < rows; i++) {
      temp[i] = haarTransform1D(input[i]);
    }

    // Step 2: Apply Haar transform on columns
    for (int j = 0; j < cols; j++) {
      double[] column = new double[rows];
      for (int i = 0; i < rows; i++) {
        column[i] = temp[i][j];
      }
      column = haarTransform1D(column);
      for (int i = 0; i < rows; i++) {
        temp[i][j] = column[i];
      }
    }

    return temp;
  }

  /**
   * Performs the inverse of a 2D Haar wavelet transform on the input 2D array.
   *
   * @param input The 2D array representing the image data to be inverse-transformed.
   * @return A new 2D array with the inverse Haar transform applied.
   */
  private double[][] inverseHaarTransform2D(double[][] input) {
    int rows = input.length;
    int cols = input[0].length;
    double[][] temp = new double[rows][cols];

    // Step 1: Inverse Haar transform on columns
    for (int j = 0; j < cols; j++) {
      double[] column = new double[rows];
      for (int i = 0; i < rows; i++) {
        column[i] = input[i][j];
      }
      column = inverseHaarTransform1D(column);
      for (int i = 0; i < rows; i++) {
        temp[i][j] = column[i];
      }
    }

    // Step 2: Inverse Haar transform on rows
    for (int i = 0; i < rows; i++) {
      temp[i] = inverseHaarTransform1D(temp[i]);
    }

    return temp;
  }

  /**
   * Performs the 1D Haar wavelet transform on an input array.
   *
   * @param input The 1D array representing pixel data for a row or column.
   * @return A new 1D array with the Haar transform applied.
   */
  private double[] haarTransform1D(double[] input) {
    int n = input.length;  // Get the length of the input array
    double[] output = new double[n];  // Create an output array of the same size as the input
    System.arraycopy(input, 0, output, 0, n);

    // Perform the Haar wavelet transform until the array is reduced to a single element
    while (n > 1) {
      double[] temp = new double[n];  // Temporary array to hold the transformed values

      // Perform the Haar transformation on pairs of elements
      for (int i = 0; i < n / 2; i++) {
        // Calculate the average (sum) of the pair of elements
        // store in the first half of temp array
        temp[i] = (output[2 * i] + output[2 * i + 1]) / Math.sqrt(2);

        // Calculate the difference (subtraction) of the pair of elements
        // store in the second half of the temporary array
        temp[n / 2 + i] = (output[2 * i] - output[2 * i + 1]) / Math.sqrt(2);
      }

      // Copy the transformed temporary array back to the output array for the next iteration
      System.arraycopy(temp, 0, output, 0, n);
      n /= 2;
    }

    // Return the transformed array after reducing it to the final state
    return output;
  }

  /**
   * Performs the inverse of the 1D Haar wavelet transform on an input array.
   *
   * @param input The 1D array representing transformed pixel data for a row or column.
   * @return A new 1D array with the inverse Haar transform applied.
   */
  private double[] inverseHaarTransform1D(double[] input) {
    int n = 1;
    double[] output = new double[input.length];
    System.arraycopy(input, 0, output, 0, input.length);

    while (n < output.length) {
      double[] temp = new double[2 * n];
      for (int i = 0; i < n; i++) {
        temp[2 * i] = (output[i] + output[n + i]) / Math.sqrt(2);
        temp[2 * i + 1] = (output[i] - output[n + i]) / Math.sqrt(2);
      }
      System.arraycopy(temp, 0, output, 0, 2 * n);
      n *= 2;
    }

    return output;
  }

  /**
   * Shrinks the dimensions of an image.
   */
  public Image downscale(Image image, int newWidth, int newHeight) {
    int oldWidth = image.getWidth();
    int oldHeight = image.getHeight();
    int[][][] newPixels = new int[newHeight][newWidth][3];

    for (int y = 0; y < newHeight; y++) {
      for (int x = 0; x < newWidth; x++) {
        double xPrime = x * (double) oldWidth / newWidth;
        double yPrime = y * (double) oldHeight / newHeight;

        int x1 = (int) Math.floor(xPrime);
        int x2 = (int) Math.ceil(xPrime);
        int y1 = (int) Math.floor(yPrime);
        int y2 = (int) Math.ceil(yPrime);

        if (x2 >= oldWidth) {
          x2 = oldWidth - 1;
        }
        if (y2 >= oldHeight) {
          y2 = oldHeight - 1;
        }

        for (int c = 0; c < 3; c++) {
          double fQ1 =
              (x2 - xPrime) * image.getPixel(y1, x1)[c] + (xPrime - x1) * image.getPixel(y1, x2)[c];
          double fQ2 =
              (x2 - xPrime) * image.getPixel(y2, x1)[c] + (xPrime - x1) * image.getPixel(y2, x2)[c];
          newPixels[y][x][c] = (int) ((y2 - yPrime) * fQ1 + (yPrime - y1) * fQ2);
        }
      }
    }

    return new Image(newWidth, newHeight, newPixels);
  }


}