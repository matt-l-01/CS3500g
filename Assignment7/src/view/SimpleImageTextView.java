package view;

import java.io.IOException;

/**
 * Represents a view for a text-based image processing program that simply prints
 * text output to the specified destination.
 */
public class SimpleImageTextView implements ImageTextView {
  private final Appendable output;

  /**
   * Creates a text-based view that prints to the specified location.
   *
   * @param out the appendable where output will be printed
   * @throws IllegalArgumentException if the input is null
   */
  public SimpleImageTextView(Appendable out) throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Argument cannot be null.");
    }
    this.output = out;
  }

  /**
   * Creates a text-based view that prints to the console by default.
   */
  public SimpleImageTextView() {
    this.output = System.out;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.output.append(message);
  }
}
