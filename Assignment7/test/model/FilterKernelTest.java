package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class holds tests for methods of the FilterKernel class.
 */
public class FilterKernelTest {

  // test constructor exception for null array
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullArray() {
    new FilterKernel(null);
  }

  // test constructor exception for empty array
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEmptyArray() {
    new FilterKernel(new Double[][]{});
  }

  // test constructor exception for non-rectangular array
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNonRectArray() {
    new FilterKernel(new Double[][]{{.1, .2, .3}, {.1, .2, .3}, {.1, .2}});
  }

  // test constructor exception for even-dimension array
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEvenArray() {
    new FilterKernel(new Double[][]{{.1, .2}, {.3, .4}, {.5, .6}, {.7, .8}});
  }

  // test height getter
  @Test
  public void testGetHeight() {
    Kernel kernel = new FilterKernel(new Double[][] {{.1}, {.2}, {.3}});
    assertEquals(3, kernel.getHeight());
  }

  // test width getter
  @Test
  public void testGetWidth() {
    Kernel kernel = new FilterKernel(new Double[][] {{.1}, {.2}, {.3}});
    assertEquals(1, kernel.getWidth());
  }

  // test element getter exception
  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetException() {
    Kernel kernel = new FilterKernel(new Double[][] {{.1, .2, .3}, {.2, .3, .4}, {.3, .4, .5}});
    kernel.get(2, 2);
  }

  // test element getter
  @Test
  public void testGet() {
    Kernel kernel = new FilterKernel(new Double[][] {{.1, .2, .3}, {.2, .3, .4}, {.3, .4, .5}});
    assertEquals(.3, kernel.get(0,0), 0.01);
    assertEquals(.5, kernel.get(1,1), 0.01);
    assertEquals(.2, kernel.get(-1,0), 0.01);
  }

  // test toString
  @Test
  public void testToString() {
    Kernel kernel = new FilterKernel(new Double[][] {{.1, .2, .3}, {.2, .3, .4}, {.3, .4, .5}});
    assertEquals("[[0.1, 0.2, 0.3], [0.2, 0.3, 0.4], [0.3, 0.4, 0.5]]", kernel.toString());
  }

}