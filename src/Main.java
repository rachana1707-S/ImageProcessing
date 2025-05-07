import controller.ImageController;
import controller.ImageprocessingController;
import model.ImageOperations;
import view.ImageProcessingGUI;
import view.ImageView;

import java.io.File;

/**
 * The main method that initiates the program execution.
 */
public class Main {

  /**
   * The main class which handles logic.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      // Open the graphical user interface
      ImageProcessingGUI view = new ImageProcessingGUI();
      ImageOperations model = new ImageOperations();
      new ImageprocessingController(view, model);
      view.setVisible(true);
    } else if (args.length == 2 && args[0].equals("-file")) {
      // Execute the script file
      String scriptFilePath = args[1];
      File scriptFile = new File(scriptFilePath);
      if (!scriptFile.exists()) {
        System.err.println("Error: Script file not found.");
        return;
      }

      // Use the text-based controller to process the script file
      ImageView view = new ImageView();
      ImageController controller = new ImageController(view);

      try {
        controller.executeScriptFromFile(scriptFilePath);
      } catch (IllegalArgumentException e) {
        System.err.println("Error processing script file: " + e.getMessage());
      }
    } else if (args.length == 1 && args[0].equals("-text")) {
      // Open interactive text mode
      ImageView view = new ImageView();
      ImageController controller = new ImageController(view);

      // Start the interactive mode
      System.out.println("Interactive text mode. Type commands to execute, or 'exit' to quit:");
      try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
        while (true) {
          System.out.print("> ");
          String input = scanner.nextLine();
          if (input.equalsIgnoreCase("exit")) {
            System.out.println("Exiting interactive text mode.");
            break;
          }
          try {
            controller.executeScript(new String[]{input});
          } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
          }
        }
      }
    } else {
      // Invalid arguments
      System.err.println("Invalid command-line arguments. Usage:");
      System.err.println("  java -jar Archive.jar -file path-of-script-file");
      System.err.println("  java -jar Archive.jar -text");
      System.err.println("  java -jar Archive.jar");
    }
  }
}
