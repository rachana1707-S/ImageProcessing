import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import controller.ImageController;
import view.ImageView;

/**
 * Unit tests for the ImageController class, ensuring correct execution of image processing
 * commands.
 */
public class ImageControllerTest {

  private ImageController controller;
  private ImageView view;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

  /**
   * Sets up the test environment, initializing the controller and view.
   */
  @Before
  public void setUp() {
    ImageView view = new ImageView();
    controller = new ImageController(view);
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  /**
   * Tests the horizontal flip command functionality.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testHorizontalFlipCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "horizontal-flip original flipped-horizontal",
        "save res/JPG/Output/flipped-horizontal.jpg flipped-horizontal"
    };
    try {
      controller.executeScript(commands);
      File flippedFile = new File("res/JPG/Output/flipped-horizontal.jpg");
      assertTrue("Flipped image should be saved to file", flippedFile.exists());
    } catch (Exception e) {
      fail("Horizontal flip command failed: " + e.getMessage());
    }
  }

  /**
   * Tests the application of sepia tone to an image.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testSepiaToneCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "sepia original sepia-tone",
        "save res/JPG/Output/sepia-tone.jpg sepia-tone"
    };
    try {
      controller.executeScript(commands);
      File sepiaFile = new File("res/JPG/Output/sepia-tone.jpg");
      assertTrue("Sepia-toned image should be saved to file", sepiaFile.exists());
    } catch (Exception e) {
      fail("Sepia tone command failed: " + e.getMessage());
    }
  }

  /**
   * Tests the brightness adjustment command.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testBrightnessCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "brighten 50 original brightened",
        "save res/JPG/Output/brightened.jpg brightened"
    };
    try {
      controller.executeScript(commands);
      File brightenedFile = new File("res/JPG/Output/brightened.jpg");
      assertTrue("Brightened image should be saved to file", brightenedFile.exists());
    } catch (Exception e) {
      fail("Brightness command failed: " + e.getMessage());
    }
  }

  /**
   * Tests the RGB split command functionality.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testRgbSplitCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "rgb-split original red-split green-split blue-split",
        "save res/JPG/Output/red-split.jpg red-split",
        "save res/JPG/Output/green-split.jpg green-split",
        "save res/JPG/Output/blue-split.jpg blue-split"
    };
    try {
      controller.executeScript(commands);
      File redSplitFile = new File("res/JPG/Output/red-split.jpg");
      File greenSplitFile = new File("res/JPG/Output/green-split.jpg");
      File blueSplitFile = new File("res/JPG/Output/blue-split.jpg");
      assertTrue("Red split image should be saved", redSplitFile.exists());
      assertTrue("Green split image should be saved", greenSplitFile.exists());
      assertTrue("Blue split image should be saved", blueSplitFile.exists());
    } catch (Exception e) {
      fail("RGB split command failed: " + e.getMessage());
    }
  }

  /**
   * Tests the behavior of the controller with an invalid command.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testInvalidCommand() throws IOException {
    String[] commands = {
        "invalid-command"
    };

    controller.executeScript(commands);

    assertTrue(errContent.toString().contains("Unknown command: invalid-command"));
  }

  /**
   * Tests the vertical flip command functionality.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testVerticalFlipCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "vertical-flip original flipped-vertical",
        "save res/JPG/Output/flipped-vertical.jpg flipped-vertical"
    };
    controller.executeScript(commands);
    File flippedFile = new File("res/JPG/Output/flipped-vertical.jpg");
    assertTrue("Vertical flipped image should be saved to file", flippedFile.exists());
  }

  /**
   * Tests the greyscale command functionality.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testGreyscaleCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "greyscale original greyscale-image",
        "save res/JPG/Output/greyscale-image.jpg greyscale-image"
    };
    controller.executeScript(commands);
    File greyscaleFile = new File("res/JPG/Output/greyscale-image.jpg");
    assertTrue("Greyscale image should be saved to file", greyscaleFile.exists());
  }

  /**
   * Tests the value component command functionality.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testValueComponentCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "value-component original value-image",
        "save res/JPG/Output/value-image.jpg value-image"
    };
    controller.executeScript(commands);
    File valueImageFile = new File("res/JPG/Output/value-image.jpg");
    assertTrue("Value component image should be saved to file", valueImageFile.exists());
  }

  /**
   * Tests the intensity component command functionality.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testIntensityComponentCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "intensity-component original intensity-image",
        "save res/JPG/Output/intensity-image.jpg intensity-image"
    };
    controller.executeScript(commands);
    File intensityFile = new File("res/JPG/Output/intensity-image.jpg");
    assertTrue("Intensity component image should be saved to file", intensityFile.exists());
  }

  /**
   * Tests the luma component command functionality.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testLumaComponentCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "luma-component original luma-image",
        "save res/JPG/Output/luma-image.jpg luma-image"
    };
    controller.executeScript(commands);
    File lumaFile = new File("res/JPG/Output/luma-image.jpg");
    assertTrue("Luma component image should be saved to file", lumaFile.exists());
  }

  /**
   * Tests the blur command functionality.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testBlurCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "blur original blurred-image",
        "save res/JPG/Output/blurred-image.jpg blurred-image"
    };
    controller.executeScript(commands);
    File blurredFile = new File("res/JPG/Output/blurred-image.jpg");
    assertTrue("Blurred image should be saved to file", blurredFile.exists());
  }

  /**
   * Tests the sharpen command functionality.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testSharpenCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "sharpen original sharpened-image",
        "save res/JPG/Output/sharpened-image.jpg sharpened-image"
    };
    controller.executeScript(commands);
    File sharpenedFile = new File("res/JPG/Output/sharpened-image.jpg");
    assertTrue("Sharpened image should be saved to file", sharpenedFile.exists());
  }

  /**
   * Tests the behavior of the controller when attempting to load a nonexistent image.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testInvalidLoadCommand() throws IOException {
    String[] commands = {
        "load res/JPG/nonexistent.jpg original"
    };
    controller.executeScript(commands);

    assertTrue(errContent.toString().contains("Error loading image"));
  }

  /**
   * Tests the behavior of the controller when attempting to save an image with an invalid path.
   *
   * @throws IOException If an error occurs during command execution.
   */
  @Test
  public void testInvalidSaveCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "save res/JPG/Output/nonexistent.jpg nonexistent"
    };
    controller.executeScript(commands);

    assertTrue(errContent.toString().contains("Image not found: nonexistent"));
  }


  /**
   * Tests the execution of multiple image commands in sequence: loading, flipping, brightening,
   * converting to greyscale, and saving.
   *
   * @throws IOException if command execution fails.
   */
  @Test
  public void testMultipleCommands() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "horizontal-flip original flipped-horizontal",
        "brighten 30 flipped-horizontal brightened",
        "greyscale brightened multiple",
        "save res/JPG/Output/multiple.jpg multiple"
    };
    controller.executeScript(commands);
    File greyscaleFile = new File("res/JPG/Output/multiple.jpg");
    assertTrue("Multiple commands executed successfully, final multiple image saved",
        greyscaleFile.exists());
  }

  @Test
  public void testHistogramCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "histogram original histogram-image",
        "save res/JPG/Output/histogram-image.jpg histogram-image"
    };
    controller.executeScript(commands);
    File histogramFile = new File("res/JPG/Output/histogram-image.jpg");
    assertTrue("Histogram image should be saved to file", histogramFile.exists());
  }

  @Test
  public void testColorCorrectionCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "color-correct original color-corrected split 50",
        "save res/JPG/Output/color-corrected-split-50.jpg color-corrected"
    };
    controller.executeScript(commands);
    File correctedFile = new File("res/JPG/Output/color-corrected-split-50.jpg");
    assertTrue("Color-corrected image should be saved to file", correctedFile.exists());
  }

  @Test
  public void testLevelsAdjustCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "levels-adjust 20 128 230 original levels-adjusted split 75",
        "save res/JPG/Output/levels-adjusted-split-75.jpg levels-adjusted"
    };
    controller.executeScript(commands);
    File adjustedFile = new File("res/JPG/Output/levels-adjusted-split-75.jpg");
    assertTrue("Levels-adjusted image should be saved to file", adjustedFile.exists());
  }

  @Test
  public void testBlurSplitCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "blur original blurred split 50",
        "save res/JPG/Output/blurred-split-50.jpg blurred"
    };
    controller.executeScript(commands);
    File blurredFile = new File("res/JPG/Output/blurred-split-50.jpg");
    assertTrue("Blurred image should be saved to file", blurredFile.exists());
  }

  @Test
  public void testSharpenSplitCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "sharpen original sharpened-image split 75",
        "save res/JPG/Output/sharpened-image.jpg sharpened-image"
    };
    controller.executeScript(commands);
    File sharpenedFile = new File("res/JPG/Output/sharpened-image.jpg");
    assertTrue("Sharpened image should be saved to file", sharpenedFile.exists());
  }

  @Test
  public void testSepiaCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "sepia original sepia-toned split 66",
        "save res/JPG/Output/sepia-toned.jpg sepia-toned"
    };
    controller.executeScript(commands);
    File sepiaFile = new File("res/JPG/Output/sepia-toned.jpg");
    assertTrue("Sepia-toned image should be saved to file", sepiaFile.exists());
  }

  @Test
  public void testGreyscaleSplitommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "greyscale original greyscaled split 90",
        "save res/JPG/Output/greyscaled-split-90.jpg greyscaled"
    };
    controller.executeScript(commands);
    File greyscaleFile = new File("res/JPG/Output/greyscaled-split-90.jpg");
    assertTrue("Greyscaled image should be saved to file", greyscaleFile.exists());
  }

  @Test
  public void testCompressionCommand() throws IOException {
    String[] commands = {
        "load res/JPG/sunflower.jpg original",
        "compress 50 original compressed-image",
        "save res/JPG/Output/compressed-image-50.jpg compressed-image"
    };
    controller.executeScript(commands);
    File compressedFile = new File("res/JPG/Output/compressed-image-50.jpg");
    assertTrue("Compressed image should be saved to file", compressedFile.exists());
  }
}
