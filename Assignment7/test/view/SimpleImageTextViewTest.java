package view;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * Holds tests for methods of the SimpleImageTextView class.
 */
public class SimpleImageTextViewTest {
  private SimpleImageTextView view;
  private Appendable output;

  // throws exceptions for all methods
  private static class AppendableExceptionMock implements Appendable {

    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw new IOException();
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw new IOException();
    }

    @Override
    public Appendable append(char c) throws IOException {
      throw new IOException();
    }
  }

  @Before
  public void setUp() {
    this.output = new StringBuilder();
    this.view = new SimpleImageTextView(this.output);
  }

  // test constructor exception
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException() {
    new SimpleImageTextView(null);
  }

  // test render message exception
  @Test(expected = IOException.class)
  public void testRenderMessageException() throws IOException {
    Appendable outMock = new AppendableExceptionMock();
    ImageTextView view1 = new SimpleImageTextView(outMock);
    view1.renderMessage("hi");
  }

  // test render message
  // also tests that constructor initializes output correctly, as this is the only observer
  @Test
  public void testRenderMessage() throws IOException {
    assertEquals("", this.output.toString());
    this.view.renderMessage("hello world");
    assertEquals("hello world", this.output.toString());
  }

}