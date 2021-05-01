package view;

import controller.CommandLineParser;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import model.ToDo;

import static controller.utilities.Constants.*;

/**
 * ToDoDisplays to print out ToDos from the controller.
 */
public class ToDoDisplay {

  List<ToDo> toDos;

  /**
   * Constructs ToDoDisplay by initializing list of to-dos.
   *
   * @param toDos - sorted, filtered list of to-dos
   */
  public ToDoDisplay(List<ToDo> toDos) {
    this.toDos = toDos;
  }

  /**
   * Helper method to create an updated To-Do list if filter by incomplete is applied.
   *
   * @param parser       valid command line arguments
   * @param displayTodos list of To-Dos to be displayed so far
   * @return updated To-Do list if filter by incomplete is applied.
   */
  private List<ToDo> displayFilterByIncomplete(CommandLineParser parser, List<ToDo> displayTodos) {
    if (parser.hasOption(SHOW_INCOMPLETE)) {
      return this.filterByIncomplete(displayTodos);
    }
    return displayTodos;
  }

  /**
   * Helper method to create an updated To-Do list if filter by category is applied.
   *
   * @param parser       valid command line arguments
   * @param displayTodos list of To-Dos to be displayed so far
   * @return updated To-Do list if filter by category is applied.
   */
  private List<ToDo> displayFilterByCategory(CommandLineParser parser, List<ToDo> displayTodos) {
    if (parser.hasOption(SHOW_CATEGORY)) {
      return this.filterByCategory(displayTodos, parser.getCategoryDisplay());
    }
    return displayTodos;
  }

  /**
   * Helper method to create an updated To-Do list if sort by date is applied.
   *
   * @param parser       valid command line arguments
   * @param displayTodos list of To-Dos to be displayed so far
   * @return updated To-Do list if sort by date is applied.
   */
  private List<ToDo> displaySortByDate(CommandLineParser parser, List<ToDo> displayTodos) {
    if (parser.hasOption(SORT_BY_DATE)) {
      return this.sortByDate(displayTodos);
    }
    return displayTodos;
  }

  /**
   * Helper method to create an updated To-Do list if sort by priority is applied.
   *
   * @param parser       valid command line arguments
   * @param displayTodos list of To-Dos to be displayed so far
   * @return updated To-Do list if sort by priority is applied.
   */
  private List<ToDo> displaySortByPriority(CommandLineParser parser, List<ToDo> displayTodos) {
    if (parser.hasOption(SORT_BY_PRIORITY)) {
      return this.sortByPriority(displayTodos);
    }
    return displayTodos;
  }

  /**
   * Displays all to-dos if --display and the given optional category is provided.
   *
   * @param parser valid command line arguments
   * @return updated to-do list to display with applied filter or sort.
   */
  public List<ToDo> displayManager(CommandLineParser parser) {
    if (parser.hasOption(DISPLAY)) {
      List<ToDo> displayTodos = this.toDos;
      List<ToDo> displayIncomplete = displayFilterByIncomplete(parser, displayTodos);
      List<ToDo> displayCategory = displayFilterByCategory(parser, displayIncomplete);
      List<ToDo> sortDate = displaySortByDate(parser, displayCategory);
      List<ToDo> displayTasks = displaySortByPriority(parser, sortDate);
      for (ToDo displayTask : displayTasks) {
        System.out.println(displayTask);
      }
      return displayTasks;
    }
    return this.toDos;
  }

  /**
   * Filters to-dos by category. If the to-do belongs to a given category, then add it to the list.
   *
   * @param toDoList the list of to-dos to be filtered.
   * @param category the category to filter by.
   * @return a list filtered by category.
   */
  private List<ToDo> filterByCategory(List<ToDo> toDoList, String category) {
    return toDoList.stream()
        .filter(i -> (i.getCategory() != null && i.getCategory().equals(category)))
        .collect(Collectors.toList());
  }

  /**
   * Filters to-dos by status. If the to-do has been completed, then add it to the list.
   *
   * @param toDoList the list of to-dos to be filtered.
   * @return a list filtered by incomplete to-dos.
   */
  private List<ToDo> filterByIncomplete(List<ToDo> toDoList) {
    return toDoList.stream().filter(i -> i != null).
        filter(i -> !i.isCompleted()).collect(Collectors.toList());
  }

  /**
   * Sorts to-dos in ascending order by due date.
   *
   * @param toDoList the list of to-dos to be filtered.
   * @return to-dos in ascending order by due date.
   */

  private static List<ToDo> sortByDate(List<ToDo> toDoList) {
    return toDoList.stream().filter(i -> i.getDueDate() != null)
        .sorted(Comparator.comparing(ToDo::getDueDate))
        .collect(Collectors.toList());
  }

  /**
   * Sorts to-dos in ascending order by priority.
   *
   * @param toDoList the list of to-dos to be filtered.
   * @return to-dos in ascending order by priority.
   */

  private static List<ToDo> sortByPriority(List<ToDo> toDoList) {
    return toDoList.stream().filter(i -> i.getPriority() != null)
        .sorted(Comparator.comparing(ToDo::getPriority))
        .collect(Collectors.toList());
  }

  /**
   * Checks equality of a ToDoDisplay object.
   *
   * @param o the object to check
   * @return true if the objects are the same, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ToDoDisplay display = (ToDoDisplay) o;
    return Objects.equals(this.toDos, display.toDos);
  }

  /**
   * Generates a hash code for the ToDoDisplay object.
   *
   * @return the hash code for the ToDoDisplay object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.toDos);
  }

  /**
   * Return string representation of a ToDoDisplay instance.
   *
   * @return string representation of a ToDoDisplay instance.
   */
  @Override
  public String toString() {
    return "ToDoDisplay{" +
        "toDos=" + this.toDos +
        '}';
  }
}
