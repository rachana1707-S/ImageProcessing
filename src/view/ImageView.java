package view;

/**
 * The `ImageView` class implements the `ViewInterface` and is responsible for displaying messages
 * and reading commands from a script file. It provides methods to read a script file as an array of
 * commands and display messages or errors to the user.
 */
public class ImageView implements ViewInterface {

  /**
   * Displays a general message to the user.
   *
   * @param message The message to display.
   */
  public void displayMessage(String message) {
    System.out.println(message);
  }

  /**
   * Displays an error message to the user.
   *
   * @param errorMessage The error message to display.
   */
  public void displayError(String errorMessage) {
    System.err.println(errorMessage);
  }
}
