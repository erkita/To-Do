package controller.exceptions;

/**
 * Exception thrown if invalid arguments are provided at the command line.
 */
public class InvalidArgumentException extends Exception {
  public InvalidArgumentException(String message) {
    super("Error: " + message + System.lineSeparator() + "Usage: " + System.lineSeparator()
        + "--add-todo Add a new todo. If this option is provided, then --todo-test must also be provided." + System.lineSeparator()
        + "--todo-text <description of todo> A description of the todo " + System.lineSeparator()
        + "--due<due date> (Optional) Sets the due date of a new todo" + System.lineSeparator()
        + "--priority <1,2, or 3> (Optional) Sets the priority of a new todo.The value can be 1,2,3" + System.lineSeparator()
        + "--category <a category name> (Optional) Sets the category of a new todo." + System.lineSeparator()
        + "--csv-file <path/to/folder> The CSV file to process. This option is required." + System.lineSeparator()
        + "--complete-todo<id> Mark the todo with provided ID as complete." + System.lineSeparator()
        + "Examples: " + System.lineSeparator()
        + "--add-todo --todo-text finish hw9 --due 2021-04-23 todos.csv" + System.lineSeparator()
        );
  }
}