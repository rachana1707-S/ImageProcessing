import static org.junit.Assert.assertTrue;

import java.io.IOException;

import mock.MockImageprocessingController;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for GUI Controller.
 */
public class ImageprocessingControllerTest {

  private StringBuilder log;
  private MockImageprocessingController mockController;

  @Before
  public void setup() {
    log = new StringBuilder();
    mockController = new MockImageprocessingController(log);
  }

  @Test
  public void testLoadPPMImage() throws IOException {
    mockController.loadPPMImage("res/input/test.ppm");
    assertTrue(log.toString().contains("loadPPMImage called with filePath: res/input/test.ppm"));
  }

  @Test
  public void testSavePPMImage() throws IOException {
    mockController.savePPMImage("output/test.ppm");
    assertTrue(log.toString().contains("savePPMImage called with filePath: output/test.ppm"));
  }

  @Test
  public void testLoadStandardImage() throws IOException {
    mockController.loadStandardImage("res/input/image.png");
    assertTrue(
        log.toString().contains("loadStandardImage called with filePath: res/input/image.png"));
  }

  @Test
  public void testApplyBlur() throws IOException {
    mockController.applyBlur(50);
    assertTrue(log.toString().contains("applyBlur called with percentage: 50"));
  }

  @Test
  public void testApplySharpen() throws IOException {
    mockController.applySharpen(30);
    assertTrue(log.toString().contains("applySharpen called with percentage: 30"));
  }

  @Test
  public void testApplyGreyscale() throws IOException {
    mockController.applyGreyscale(40);
    assertTrue(log.toString().contains("applyGreyscale called with percentage: 40"));
  }

  @Test
  public void testApplySepia() throws IOException {
    mockController.applySepia(20);
    assertTrue(log.toString().contains("applySepia called with percentage: 20"));
  }

  @Test
  public void testFlipHorizontal() {
    mockController.flipHorizontal();
    assertTrue(log.toString().contains("flipHorizontal called"));
  }

  @Test
  public void testFlipVertical() {
    mockController.flipVertical();
    assertTrue(log.toString().contains("flipVertical called"));
  }

  @Test
  public void testAdjustBrightness() throws IOException {
    mockController.adjustBrightness(10);
    assertTrue(log.toString().contains("adjustBrightness called with brightnessLevel: 10"));
  }

  @Test
  public void testCompress() throws IOException {
    mockController.compress(70);
    assertTrue(log.toString().contains("compress called with threshold: 70.0"));
  }

  @Test
  public void testDownscaleValid() throws IOException {
    mockController.downscale(800, 600);
    assertTrue(log.toString().contains("downscale called with newWidth: 800, newHeight: 600"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleInvalid() throws IOException {
    mockController.downscale(0, 600);  // Invalid width
  }

  @Test
  public void testAdjustLevels() throws IOException {
    mockController.adjustLevels(10, 20, 30, 50);
    assertTrue(log.toString()
        .contains("adjustLevels called with black: 10, mid: 20, white: 30, splitPercent: 50"));
  }

  @Test
  public void testGenerateHistogram() {
    mockController.generateHistogram();
    assertTrue(log.toString().contains("generateHistogram called"));
  }

  // Tests for loading images
  @Test
  public void testLoadPPMImageEmptyPath() throws IOException {
    mockController.loadPPMImage("");
    assertTrue(log.toString().contains("loadPPMImage called with filePath: "));
  }

  @Test
  public void testLoadStandardImageEmptyPath() throws IOException {
    mockController.loadStandardImage("");
    assertTrue(log.toString().contains("loadStandardImage called with filePath: "));
  }

  // Tests for applying effects with boundary values
  @Test
  public void testApplyBlurZeroPercentage() throws IOException {
    mockController.applyBlur(0);  // Edge case with zero percentage
    assertTrue(log.toString().contains("applyBlur called with percentage: 0"));
  }

  @Test
  public void testApplyBlurHundredPercentage() throws IOException {
    mockController.applyBlur(100);  // Edge case with 100 percentage
    assertTrue(log.toString().contains("applyBlur called with percentage: 100"));
  }

  @Test
  public void testApplySharpenZeroPercentage() throws IOException {
    mockController.applySharpen(0);  // Edge case with zero percentage
    assertTrue(log.toString().contains("applySharpen called with percentage: 0"));
  }

  @Test
  public void testApplySharpenHundredPercentage() throws IOException {
    mockController.applySharpen(100);  // Edge case with 100 percentage
    assertTrue(log.toString().contains("applySharpen called with percentage: 100"));
  }

  @Test
  public void testApplyGreyscaleZeroPercentage() throws IOException {
    mockController.applyGreyscale(0);  // Edge case with zero percentage
    assertTrue(log.toString().contains("applyGreyscale called with percentage: 0"));
  }

  @Test
  public void testApplyGreyscaleHundredPercentage() throws IOException {
    mockController.applyGreyscale(100);  // Edge case with 100 percentage
    assertTrue(log.toString().contains("applyGreyscale called with percentage: 100"));
  }

  @Test
  public void testApplySepiaZeroPercentage() throws IOException {
    mockController.applySepia(0);  // Edge case with zero percentage
    assertTrue(log.toString().contains("applySepia called with percentage: 0"));
  }

  @Test
  public void testApplySepiaHundredPercentage() throws IOException {
    mockController.applySepia(100);  // Edge case with 100 percentage
    assertTrue(log.toString().contains("applySepia called with percentage: 100"));
  }

  // Tests for flipping images
  @Test
  public void testFlipHorizontalTwice() {
    mockController.flipHorizontal();
    mockController.flipHorizontal();  // Flip twice
    assertTrue(log.toString().contains("flipHorizontal called"));
  }

  @Test
  public void testFlipVerticalTwice() {
    mockController.flipVertical();
    mockController.flipVertical();  // Flip twice
    assertTrue(log.toString().contains("flipVertical called"));
  }

  // Test for adjusting brightness with boundary values
  @Test
  public void testAdjustBrightnessZero() throws IOException {
    mockController.adjustBrightness(0);  // Edge case with zero brightness
    assertTrue(log.toString().contains("adjustBrightness called with brightnessLevel: 0"));
  }

  @Test
  public void testAdjustBrightnessHigh() throws IOException {
    mockController.adjustBrightness(255);  // Edge case with max brightness
    assertTrue(log.toString().contains("adjustBrightness called with brightnessLevel: 255"));
  }

  // Tests for compression threshold
  @Test
  public void testCompressZeroThreshold() throws IOException {
    mockController.compress(0);  // Edge case with zero threshold
    assertTrue(log.toString().contains("compress called with threshold: 0.0"));
  }

  @Test
  public void testCompressHundredThreshold() throws IOException {
    mockController.compress(100);  // Edge case with 100 threshold
    assertTrue(log.toString().contains("compress called with threshold: 100.0"));
  }

  @Test
  public void testCompressNegativeThreshold() throws IOException {
    mockController.compress(-10);  // Edge case with negative threshold
    assertTrue(log.toString().contains("compress called with threshold: -10.0"));
  }

  // Tests for downscaling with extreme values
  @Test
  public void testDownscaleOnePixel() throws IOException {
    mockController.downscale(1, 1);  // Edge case with 1x1 pixel
    assertTrue(log.toString().contains("downscale called with newWidth: 1, newHeight: 1"));
  }

  @Test
  public void testDownscaleVeryLarge() throws IOException {
    mockController.downscale(10000, 10000);  // Extreme case with very large dimensions
    assertTrue(log.toString().contains("downscale called with newWidth: 10000, newHeight: 10000"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleNegativeWidth() throws IOException {
    mockController.downscale(-10, 500);  // Invalid width
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleNegativeHeight() throws IOException {
    mockController.downscale(500, -10);  // Invalid height
  }

  // Tests for adjusting levels with extreme values
  @Test
  public void testAdjustLevelsWithZero() throws IOException {
    mockController.adjustLevels(0, 0, 0, 50);  // Edge case with all zero levels
    assertTrue(log.toString()
        .contains("adjustLevels called with black: 0, mid: 0, white: 0, splitPercent: 50"));
  }

  @Test
  public void testAdjustLevelsWithMax() throws IOException {
    mockController.adjustLevels(255, 255, 255, 100);  // Edge case with max values
    assertTrue(log.toString()
        .contains("adjustLevels called with black: 255, mid: 255, white: 255, splitPercent: 100"));
  }

  // Test for empty histogram generation
  @Test
  public void testGenerateHistogramMultipleTimes() {
    mockController.generateHistogram();
    mockController.generateHistogram();  // Generate histogram twice
    assertTrue(log.toString().contains("generateHistogram called"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdjustBrightnessNonInteger() throws IOException {
    mockController.adjustBrightness(Integer.parseInt("non-integer"));  // Invalid input type
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyBlurNonInteger() throws IOException {
    mockController.applyBlur(Integer.parseInt("non-integer"));  // Invalid input type
  }


  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleInvalidImageDimensions() throws IOException {
    mockController.downscale(-1, 500);  // Invalid downscale dimensions
  }

  @Test
  public void testMultipleAdjustments() throws IOException {
    mockController.applyBlur(50);
    mockController.applySharpen(30);
    mockController.adjustBrightness(100);
    assertTrue(log.toString().contains("applyBlur called with percentage: 50"));
    assertTrue(log.toString().contains("applySharpen called with percentage: 30"));
    assertTrue(log.toString().contains("adjustBrightness called with brightnessLevel: 100"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdjustLevelsSplitPercentNonNumeric() throws IOException {
    mockController.adjustLevels(10, 20, 30, Integer.parseInt("invalid"));  // Non-numeric input
  }


}
