package model;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Simple test class for the RGBColorMatrix
 * class.
 */
public class RGBColorMatrixTest {

  // test constructor exception for null matrix
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullMatrix() {
    new RGBColorMatrix(null);
  }

  // test constructor exception for too small matrix
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorSmallMatrix() {
    new RGBColorMatrix(new Double[][] {{1.1, 2.2, 3.3}, {4.4, 5.5, 6.6}, {7.7, 8.8}});
  }

  // test constructor exception for too large matrix
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorLargeMatrix() {
    new RGBColorMatrix(new Double[][] {{1.1, 2.2, 3.3}, {4.4, 5.5, 6.6}, {7.7, 8.8, 9.9, 10.10}});
  }

  // test red coefficient getter
  @Test
  public void testRedCoef() {
    ColorMatrix matrix = new RGBColorMatrix(new Double[][] {
            {1.1, 2.2, 3.3}, {4.4, 5.5, 6.6}, {7.7, 8.8, 9.9}});
    assertArrayEquals(new Double[] {1.1, 2.2, 3.3}, matrix.redCoefficients());
  }

  // test green coefficient getter
  @Test
  public void testGreenCoef() {
    ColorMatrix matrix = new RGBColorMatrix(new Double[][] {
            {1.1, 2.2, 3.3}, {4.4, 5.5, 6.6}, {7.7, 8.8, 9.9}});
    assertArrayEquals(new Double[] {4.4, 5.5, 6.6}, matrix.greenCoefficients());
  }

  // test green coefficient getter
  @Test
  public void testBlueCoef() {
    ColorMatrix matrix = new RGBColorMatrix(new Double[][] {
            {1.1, 2.2, 3.3}, {4.4, 5.5, 6.6}, {7.7, 8.8, 9.9}});
    assertArrayEquals(new Double[] {7.7, 8.8, 9.9}, matrix.blueCoefficients());
  }

  // test toString
  @Test
  public void testToString() {
    ColorMatrix matrix = new RGBColorMatrix(new Double[][] {
            {1.1, 2.2, 3.3}, {4.4, 5.5, 6.6}, {7.7, 8.8, 9.9}});
    assertEquals("[[1.1, 2.2, 3.3], [4.4, 5.5, 6.6], [7.7, 8.8, 9.9]]", matrix.toString());
  }
}