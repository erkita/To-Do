package controller;

import controller.utilities.CsvWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.ToDo;
import model.ToDoList;

/**
 * TaskManager manages the ToDoList.
 */
public class TaskManager {

  private CommandLineParser parser;
  private List<ToDo> toDoList;

  /**
   * Constructs TaskManager by initializing parser and toDoList.
   *
   * @param parser    command line parser instance
   * @param toDoList  list of To-Do's
   */
  public TaskManager(CommandLineParser parser, List<ToDo> toDoList) {
    this.parser = parser;
    this.toDoList = toDoList;
  }

  /**
   * Helper method to add new To-Do if specified by command line argument.
   */
  private void updateNewToDo() {
    if (this.parser.getTextDescription() != null) {
      this.addNewToDo();
    }
  }

  /**
   * Helper method to complete To-Do if specified by command line argument.
   */
  private void updateTaskComplete() {
    if (this.parser.getCompleteID() != null) {
      for (String toComplete : this.parser.getCompleteID()) {
        markTaskComplete(Integer.parseInt(toComplete));
      }
    }
  }

  /**
   * Updates CSV file, writes the latest To-Do list.
   *
   * @throws IOException if there is an input output error.
   */
  public void updateCsvFile() throws IOException {
    this.updateNewToDo();
    this.updateTaskComplete();
    CsvWriter writer = new CsvWriter();
    writer.writeToDoCsv(this.toDoList, this.parser.getCsvPath());
  }

  /**
   * Returns the most up to date To-Do list.
   *
   * @return the most up to date To-Do list.
   */
  public List<ToDo> getCurrentToDoList() {
    return this.toDoList;
  }

  /**
   * Adds a new To-Do instance to the To-Do list.
   */
  private void addNewToDo() {
    List<String> task = new ArrayList<>();
    task.add(null);
    task.add(this.parser.getTextDescription());
    task.add(this.parser.getMarkComplete());
    task.add(this.parser.getDueDate());
    task.add(this.parser.getPriority());
    task.add(this.parser.getCategoryName());
    ToDo newToDo = ToDoList.createToDo(task);
    this.toDoList.add(newToDo);
  }

  /**
   * Marks task of given ID is complete.
   *
   * @param id identification of the task
   */
  public void markTaskComplete(Integer id) {
    if (id > this.toDoList.size() || id < 0) {
      throw new IllegalArgumentException("Invalid To-Do ID.");
    } else if (this.toDoList.get(id - 1).isCompleted()) {
      throw new IllegalArgumentException("Provided ID's To-Do is already completed.");
    }
    this.toDoList.get(id - 1).setCompleted();
  }

  // Note: no equals or hash since this class is dependent on a Singleton Class, ToDoList.
  /**
   * Return string representation of a TaskManager instance.
   *
   * @return string representation of a TaskManager instance.
   */
  @Override
  public String toString() {
    return "TaskManager{" +
        "toDoList=" + this.toDoList +
        '}';
  }
}
