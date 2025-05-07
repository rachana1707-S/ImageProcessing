package model;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Represents an image with pixel data, width, and height.
 */
public class Image implements ImageInterface {

  private final int width;
  private final int height;
  private final int[][][] pixels;

  /**
   * Constructs an Image with the specified width, height, and pixel data.
   *
   * @param width  the width of the image
   * @param height the height of the image
   * @param pixels the pixel data in RGB format
   */
  public Image(int width, int height, int[][][] pixels) {
    this.width = width;
    this.height = height;
    this.pixels = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.pixels[i][j] = pixels[i][j].clone();
      }
    }
  }

  /**
   * Returns the width of the image.
   *
   * @return the image width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns the height of the image.
   *
   * @return the image height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Retrieves the RGB pixel values at the specified row and column.
   *
   * @param row the row index of the pixel
   * @param col the column index of the pixel
   * @return an array containing the RGB values of the pixel
   */
  public int[] getPixel(int row, int col) {
    return pixels[row][col];
  }

  /**
   * Sets the RGB values for the pixel at the specified row and column.
   *
   * @param row the row index of the pixel
   * @param col the column index of the pixel
   * @param rgb an array containing the RGB values to set
   */
  public void setPixel(int row, int col, int[] rgb) {
    pixels[row][col] = rgb;
  }

  /**
   * Compares this Image to another object for equality based on width, height, and pixel data.
   *
   * @return true or false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Image image = (Image) o;
    return width == image.width && height == image.height && Arrays.deepEquals(pixels,
        image.pixels);
  }

  /**
   * Returns the hash code for this Image, based on its width, height, and pixel data.
   */
  @Override
  public int hashCode() {
    int result = Integer.hashCode(width);
    result = 31 * result + Integer.hashCode(height);
    result = 31 * result + Arrays.deepHashCode(pixels);
    return result;
  }

  /**
   * Converts to Buffered Image.
   */
  public BufferedImage toBufferedImage() {
    BufferedImage bufferedImage = new BufferedImage(this.width, this.height,
        BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        int[] pixel = this.getPixel(i, j);
        int rgb = (pixel[0] << 16) | (pixel[1] << 8) | pixel[2];
        bufferedImage.setRGB(j, i, rgb);
      }
    }
    return bufferedImage;
  }
}
