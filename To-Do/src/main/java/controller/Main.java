package controller;

import java.io.IOException;
import java.util.List;
import model.ToDo;
import model.ToDoList;
import view.ToDoDisplay;

public class Main {

  public static void main(String[] args) throws IOException {
    CommandLineParser parser = null;
    Config config = new Config();
    try {
      parser = new CommandLineParser(args, config.getOptions());
      List<ToDo> toDoList = ToDoList.createToDoList(parser.getCsvPath());
      TaskManager task = new TaskManager(parser, toDoList);
      task.updateCsvFile();
      ToDoDisplay display = new ToDoDisplay(toDoList);
      for (ToDo filteredTask : display.displayManager(parser)) {
        System.out.println(filteredTask);
      }
      System.out.println(System.lineSeparator());
    } catch (Exception ioe) {
      System.out.println("Something went wrong! " + ioe.getMessage());
      ioe.printStackTrace();
    }
  }

}
