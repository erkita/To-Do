package model;

import controller.utilities.CsvReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.ToDo.Builder;

/**
 * ToDoList is a singleton class that creates only one list of To-Do's at a time.
 */
public class ToDoList {

  private static final String NO_INPUT = "?";
  private static final int TEXT = 1;
  private static final int COMPLETED = 2;
  private static final int DUE = 3;
  private static final int PRIORITY = 4;
  private static final int CATEGORY = 5;
  private static int idCount = 1;
  private static List<ToDo> toDoList;
  private static ToDoList instance;

  /**
   * Private constructor restricted to ToDoList class itself.
   */
  private ToDoList() {
  }

  /**
   * Static method to create instance of Singleton ToDoList class.
   *
   * @return instance of ToDoList.
   */
  public static ToDoList getInstance() {
    if (instance == null) {
      instance = new ToDoList();
    }
    return instance;
  }

  /**
   * Returns a list containing all To-Do's.
   *
   * @return a list containing all To-Do's.
   */
  public List<ToDo> getToDoList() {
    return this.toDoList;
  }

  /**
   * Creates a To-Do instance given the fields of task.
   *
   * @param task a task in To-Do
   * @return a To-Do instance given the fields of task.
   */
  public static ToDo createToDo(List<String> task) {
    Builder toDoBuilder = new ToDo.Builder(task.get(TEXT));
    toDoBuilder.markComplete(Boolean.parseBoolean(task.get(COMPLETED)));
    toDoBuilder.addDueDate(task.get(DUE));
    toDoBuilder.addPriority(task.get(PRIORITY));
    toDoBuilder.addCategory(task.get(CATEGORY));
    toDoBuilder.addId(Integer.toString(idCount++));
    return toDoBuilder.build();
  }

  /**
   * Assigns null if input was "?".
   *
   * @param csvLine a line from csv file
   * @return a list of tasks with proper format. Contains null for empty input.
   */
  private static List<String> assignNullToEmptyInput(String[] csvLine) {
    List<String> task = new ArrayList<>();
    for (String item : csvLine) {
      task.add(item.equals(NO_INPUT) ? null : item);
    }
    return task;
  }

  /**
   * Creates a list containing all To-Do's.
   *
   * @param csvFile csv file given from command line parser
   * @return list containing all To-Do's.
   * @throws IOException if there is an input output error.
   */
  public static List<ToDo> createToDoList(String csvFile) throws IOException {
    List<String[]> currentToDos = CsvReader.readCsv(csvFile);
    toDoList = new ArrayList<>();
    boolean isFirstRow = true;
    for (String[] csvLine : currentToDos) {
      if (isFirstRow) {
        isFirstRow = false;
        continue;
      }
      List<String> task = assignNullToEmptyInput(csvLine);
      toDoList.add(createToDo(task));
    }
    return toDoList;
  }

  /**
   * Returns boolean for current object is equivalent to parameter object.
   *
   * @param o the object to be determined equal.
   * @return boolean for current object is equivalent to parameter object.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ToDoList toDoList1 = (ToDoList) o;
    return Objects.equals(this.toDoList, toDoList1.toDoList);
  }

  /**
   * Return integer representation of a ToDoList instance.
   *
   * @return integer representation of a ToDoList instance.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.toDoList);
  }

  /**
   * Return string representation of a ToDoList instance.
   *
   * @return string representation of a ToDoList instance.
   */
  @Override
  public String toString() {
    return "ToDoList{" +
        "toDoList=" + this.toDoList +
        '}';
  }
}
