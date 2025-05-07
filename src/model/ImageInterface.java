package model;


/**
 * Represents an image with pixel data, width, and height. Defines the contract for manipulating
 * image properties and pixel data.
 */
public interface ImageInterface {

  /**
   * Returns the width of the image.
   *
   * @return the image width
   */
  int getWidth();

  /**
   * Returns the height of the image.
   *
   * @return the image height
   */
  int getHeight();

  /**
   * Retrieves the RGB pixel values at the specified row and column.
   *
   * @param row the row index of the pixel
   * @param col the column index of the pixel
   * @return an array containing the RGB values of the pixel
   */
  int[] getPixel(int row, int col);

  /**
   * Sets the RGB values for the pixel at the specified row and column.
   *
   * @param row the row index of the pixel
   * @param col the column index of the pixel
   * @param rgb an array containing the RGB values to set
   */
  void setPixel(int row, int col, int[] rgb);

  /**
   * Compares this Image to another object for equality based on width, height, and pixel data.
   *
   * @return true or false
   */
  boolean equals(Object o);

  /**
   * Returns the hash code for this Image, based on its width, height, and pixel data.
   */
  int hashCode();
}
