package mock;

import java.io.IOException;

/**
 * Mock Image processing Controller class for testing.
 */
public class MockImageprocessingController {

  private StringBuilder log;

  public MockImageprocessingController(StringBuilder log) {
    this.log = log;
  }

  public void loadPPMImage(String filePath) throws IOException {
    log.append("loadPPMImage called with filePath: ").append(filePath).append("\n");
  }

  public void savePPMImage(String filePath) throws IOException {
    log.append("savePPMImage called with filePath: ").append(filePath).append("\n");
  }

  public void loadStandardImage(String filePath) throws IOException {
    log.append("loadStandardImage called with filePath: ").append(filePath).append("\n");
  }

  public void applyBlur(int percentage) throws IOException {
    log.append("applyBlur called with percentage: ").append(percentage).append("\n");
  }

  public void applySharpen(int percentage) throws IOException {
    log.append("applySharpen called with percentage: ").append(percentage).append("\n");
  }

  public void applyGreyscale(int percentage) throws IOException {
    log.append("applyGreyscale called with percentage: ").append(percentage).append("\n");
  }

  public void applySepia(int percentage) throws IOException {
    log.append("applySepia called with percentage: ").append(percentage).append("\n");
  }

  public void flipHorizontal() {
    log.append("flipHorizontal called\n");
  }

  public void flipVertical() {
    log.append("flipVertical called\n");
  }

  public void adjustBrightness(int brightnessLevel) throws IOException {
    log.append("adjustBrightness called with brightnessLevel: ").append(brightnessLevel)
        .append("\n");
  }

  public void compress(double threshold) throws IOException {
    log.append("compress called with threshold: ").append(threshold).append("\n");
  }

  /**
   * Downscale method to shrink dimensions of an image.
   */
  public void downscale(int newWidth, int newHeight) throws IOException {
    if (newWidth <= 0 || newHeight <= 0) {
      throw new IllegalArgumentException("Width and height must be greater than zero.");
    }
    log.append("downscale called with newWidth: ").append(newWidth)
        .append(", newHeight: ").append(newHeight).append("\n");
  }

  /**
   * Adjusts levels according to values given by the user.
   */
  public void adjustLevels(int black, int mid, int white, int splitPercent) throws IOException {
    log.append("adjustLevels called with black: ").append(black)
        .append(", mid: ").append(mid)
        .append(", white: ").append(white)
        .append(", splitPercent: ").append(splitPercent).append("\n");
  }

  public void generateHistogram() {
    log.append("generateHistogram called\n");
  }
}
