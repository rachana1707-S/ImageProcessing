package view;

/**
 * Interface for handling view-related operations in the application.
 */
public interface ViewInterface {

  /**
   * Displays a message to the user.
   *
   * @param message The message to display.
   */
  void displayMessage(String message);

  /**
   * Displays an error message to the user.
   *
   * @param error The error message to display.
   */
  void displayError(String error);
}
