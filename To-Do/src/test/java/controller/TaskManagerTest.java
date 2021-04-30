package controller;

import static controller.utilities.Constants.*;
import static org.junit.Assert.*;

import controller.exceptions.InvalidArgumentException;
import controller.exceptions.InvalidPairException;
import java.io.IOException;
import java.util.List;
import model.ToDo;
import model.ToDoList;
import org.junit.Before;
import org.junit.Test;

public class TaskManagerTest {
  CommandLineParser parser;

  @Before
  public void setUp() throws IOException, InvalidPairException, InvalidArgumentException {
    String csvFile = "todosTest.csv";
    String textDescription = "new task";
    String[] validArgs = new String[]{TODO_TEXT, textDescription, ADD_TODO, CSV, csvFile, COMPLETE_TODO, "1"};

    Option addTodo = new Option(ADD_TODO, false);
    Option toDoText = new Option(TODO_TEXT, true);
    Option csv = new Option(CSV, true);
    Option due = new Option(DUE, true);
    Option completed = new Option(COMPLETED, false);
    Option priority = new Option(PRIORITY, true);
    Option completeToDo = new Option(COMPLETE_TODO,true);
    Option sortByDate = new Option(SORT_BY_DATE, false);
    Option sortByPriority = new Option(SORT_BY_PRIORITY, false);
    Option category = new Option(CATEGORY,true);
    Options options = new Options();
    options.addOption(ADD_TODO, addTodo);
    options.addOption(TODO_TEXT, toDoText);
    options.addOption(CSV, csv);
    options.addOption(DUE, due);
    options.addOption(COMPLETED, completed);
    options.addOption(PRIORITY, priority);
    options.addOption(COMPLETE_TODO, completeToDo);
    options.addOption(SORT_BY_DATE,sortByDate);
    options.addOption(SORT_BY_PRIORITY,sortByPriority);
    options.addOption(CATEGORY,category);
    parser = new CommandLineParser(validArgs, options);
//    taskManager = new TaskManager(parser, toDoList.getToDoList());
//    taskManager2 = new TaskManager(parser, toDoList.getToDoList());
//    taskManager2.addToDo(parser);
//    List<String[]> csvList = CsvReader.readCsv(csvFile);
  }

  @Test
  public void getCurrentToDoList() throws IOException {
    ToDoList toDoList1 = ToDoList.getInstance();
    toDoList1.createToDoList("todosTest.csv");
    TaskManager taskManager1 = new TaskManager(parser, toDoList1.getToDoList());
    assertEquals(toDoList1.getToDoList(), taskManager1.getCurrentToDoList());
  }

  @Test
  public void updateCsvFile() throws IOException {
    ToDoList toDoList6 = ToDoList.getInstance();
    toDoList6.createToDoList("todosTest.csv");
    TaskManager taskManager6 = new TaskManager(parser, toDoList6.getToDoList());
    taskManager6.updateCsvFile();
  }

  @Test
  public void markTaskComplete() throws IOException {
    ToDoList toDoList2 = ToDoList.getInstance();
    toDoList2.createToDoList("todosTest.csv");
    TaskManager taskManager2 = new TaskManager(parser, toDoList2.getToDoList());
    taskManager2.markTaskComplete(1);
    List<ToDo> updatedToDos = taskManager2.getCurrentToDoList();
    assertTrue(updatedToDos.get(1).isCompleted());
  }

  @Test (expected = IllegalArgumentException.class)
  public void alreadyMarkedComplete() throws IllegalArgumentException, IOException {
    ToDoList toDoList3 = ToDoList.getInstance();
    toDoList3.createToDoList("todosTest.csv");
    TaskManager taskManager3 = new TaskManager(parser, toDoList3.getToDoList());
    taskManager3.markTaskComplete(2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidIDMarkComplete() throws IllegalArgumentException, IOException {
    ToDoList toDoList4 = ToDoList.getInstance();
    toDoList4.createToDoList("todosTest.csv");
    TaskManager taskManager4 = new TaskManager(parser, toDoList4.getToDoList());
    taskManager4.markTaskComplete(15);
  }

  @Test (expected = IllegalArgumentException.class)
  public void negativeIDMarkComplete() throws IllegalArgumentException, IOException {
    ToDoList toDoList5 = ToDoList.getInstance();
    toDoList5.createToDoList("todosTest.csv");
    TaskManager taskManager5 = new TaskManager(parser, toDoList5.getToDoList());
    taskManager5.markTaskComplete(-15);
  }

  // Note* issue with assertEquals due to Singleton class duplicates
  //  @Test
//  public void testToString() throws IOException {
//    ToDoList toDoList6 = ToDoList.getInstance();
//    toDoList6.createToDoList("todos.csv");
//    TaskManager taskManager6 = new TaskManager(parser, toDoList6.getToDoList());
//    assertEquals("TaskManager{toDoList=[ToDo{text='new task', dueDate=2020-03-22, priority=1, "
//        + "category='school', completed=false, id='11'}, ToDo{text='Mail passport', dueDate=2020-02-28, "
//        + "priority=null, category='null', completed=true, id='12'}, ToDo{text='Study for finals', "
//        + "dueDate=null, priority=2, category='school', completed=false, id='13'}, ToDo{text='Clean the house', "
//        + "dueDate=2020-03-22, priority=3, category='home', completed=false, id='14'}, "
//        + "ToDo{text='Buy yarn for blanket, stuffed toy', dueDate=null, priority=null, category='home',"
//        + " completed=true, id='15'}]}", taskManager6.toString());
//  }
}