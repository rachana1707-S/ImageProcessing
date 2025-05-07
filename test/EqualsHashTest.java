import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import model.Image;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit tests for the Image class, verifying the correct behavior of equals and hashCode methods.
 */
public class EqualsHashTest {

  private Image image1;
  private Image image2;
  private Image image3;

  /**
   * Sets up the test images for each test case.
   */
  @Before
  public void setUp() {
    int[][][] pixels1 = {
        {{255, 0, 0}, {0, 255, 0}},  // Red, Green
        {{0, 0, 255}, {255, 255, 0}} // Blue, Yellow
    };
    int[][][] pixels2 = {
        {{255, 0, 0}, {0, 255, 0}},  // Red, Green
        {{0, 0, 255}, {255, 255, 0}} // Blue, Yellow
    };
    int[][][] pixels3 = {
        {{255, 0, 0}, {0, 255, 0}},  // Red, Green
        {{0, 0, 0}, {255, 255, 0}}   // Black, Yellow
    };

    image1 = new Image(2, 2, pixels1);
    image2 = new Image(2, 2, pixels2);
    image3 = new Image(2, 2, pixels3);
  }

  /**
   * Tests that two images with identical data are considered equal.
   */
  @Test
  public void testEquals_SameData() {
    assertEquals(image1, image2);
  }

  /**
   * Tests that two images with different data are not considered equal.
   */
  @Test
  public void testEquals_DifferentData() {
    assertNotEquals(image1, image3);
  }

  /**
   * Tests that two images with the same data have the same hash code.
   */
  @Test
  public void testHashCode_SameData() {
    assertEquals(image1.hashCode(), image2.hashCode());
  }

  /**
   * Tests that two images with different data have different hash codes.
   */
  @Test
  public void testHashCode_DifferentData() {
    assertNotEquals(image1.hashCode(), image3.hashCode());
  }

  /**
   * Tests that modifying an image makes it unequal to an identical unmodified image.
   */
  @Test
  public void testEquals_ModifyingImage() {
    image1.setPixel(0, 0, new int[]{0, 0, 0});
    assertNotEquals(image1, image2);
  }

  /**
   * Tests that modifying an image changes its hash code.
   */
  @Test
  public void testHashCode_ModifyingImage() {
    int initialHashCode = image1.hashCode();
    image1.setPixel(0, 0, new int[]{0, 0, 0});
    assertNotEquals(initialHashCode, image1.hashCode());
  }

  /**
   * Tests that an image is equal to itself.
   */
  @Test
  public void testEquals_SameReference() {
    assertEquals(image1, image1);
  }

  /**
   * Tests that an image is not equal to null.
   */
  @Test
  public void testEquals_NullComparison() {
    assertNotEquals(image1, null);
  }

  /**
   * Tests that an image is not equal to an object of a different class.
   */
  @Test
  public void testEquals_DifferentClass() {
    String notAnImage = "Not an image";
    assertNotEquals(image1, notAnImage);
  }

  /**
   * Tests that images with different dimensions are not considered equal.
   */
  @Test
  public void testEquals_DifferentDimensions() {
    int[][][] pixels4x4 = {
        {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}, {255, 255, 0}},
        {{255, 255, 255}, {0, 0, 0}, {255, 0, 255}, {0, 255, 255}},
        {{255, 255, 0}, {0, 0, 0}, {255, 255, 255}, {0, 0, 255}},
        {{255, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 0, 255}}
    };
    Image image4x4 = new Image(4, 4, pixels4x4);
    assertNotEquals(image1, image4x4);
  }

  /**
   * Tests that the hash code for an image remains the same when comparing it to itself.
   */
  @Test
  public void testHashCode_SameReference() {
    int initialHashCode = image1.hashCode();
    assertEquals(initialHashCode, image1.hashCode());
  }
}
