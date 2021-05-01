package view;

import static controller.utilities.Constants.ADD_TODO;
import static controller.utilities.Constants.CATEGORY;
import static controller.utilities.Constants.COMPLETED;
import static controller.utilities.Constants.COMPLETE_TODO;
import static controller.utilities.Constants.CSV;
import static controller.utilities.Constants.DISPLAY;
import static controller.utilities.Constants.DUE;
import static controller.utilities.Constants.PRIORITY;
import static controller.utilities.Constants.SHOW_CATEGORY;
import static controller.utilities.Constants.SHOW_INCOMPLETE;
import static controller.utilities.Constants.SORT_BY_DATE;
import static controller.utilities.Constants.SORT_BY_PRIORITY;
import static controller.utilities.Constants.TODO_TEXT;
import static org.junit.Assert.*;

import controller.CommandLineParser;
import controller.Option;
import controller.Options;
import controller.TaskManager;
import controller.exceptions.InvalidArgumentException;
import controller.exceptions.InvalidPairException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.ToDo;
import model.ToDoList;
import org.junit.Before;
import org.junit.Test;

public class ToDoDisplayTest {

  ToDoDisplay testDisplay, testDisplay2, copy;
  CommandLineParser parser;
  Options options;

  @Before
  public void setUp() throws Exception {
    String csvFile = "todos.csv";
    String textDescription = "finish hw9";
    String[] validArgs = new String[]{TODO_TEXT, textDescription, ADD_TODO, CSV, csvFile};

    Option addTodo = new Option(ADD_TODO, false);
    Option toDoText = new Option(TODO_TEXT, true);
    Option csv = new Option(CSV, true);
    Option due = new Option(DUE, true);
    Option completed = new Option(COMPLETED, false);
    Option priority = new Option(PRIORITY, true);
    Option completeToDo = new Option(COMPLETE_TODO, true);
    Option sortByDate = new Option(SORT_BY_DATE, false);
    Option sortByPriority = new Option(SORT_BY_PRIORITY, false);
    Option category = new Option(CATEGORY, true);
    Option display = new Option(DISPLAY, false);
    Option showIncomplete = new Option(SHOW_INCOMPLETE, false);
    Option showCategory = new Option(SHOW_CATEGORY, true);
    options = new Options();
    options.addOption(ADD_TODO, addTodo);
    options.addOption(TODO_TEXT, toDoText);
    options.addOption(CSV, csv);
    options.addOption(DUE, due);
    options.addOption(COMPLETED, completed);
    options.addOption(PRIORITY, priority);
    options.addOption(COMPLETE_TODO, completeToDo);
    options.addOption(SORT_BY_DATE, sortByDate);
    options.addOption(SORT_BY_PRIORITY, sortByPriority);
    options.addOption(CATEGORY, category);
    options.addOption(DISPLAY, display);
    options.addOption(SHOW_INCOMPLETE, showIncomplete);
    options.addOption(SHOW_CATEGORY, showCategory);
    parser = new CommandLineParser(validArgs, options);

    ToDo todo1 = new ToDo.Builder("buy tickets").addDueDate("11/06/2021").addPriority("1")
        .addCategory("personal").markComplete(false).addId("1").build();
    ToDo todo2 = new ToDo.Builder("buy cell phone").addDueDate("04/17/2021").addPriority("3")
        .addCategory("personal").markComplete(true).addId("2").build();
    List<ToDo> test1 = new ArrayList<>();
    List<ToDo> test2 = new ArrayList<>();
    test1.add(todo1);
    test1.add(todo2);
    test2.add(todo1);
    testDisplay = new ToDoDisplay(test1);
    testDisplay2 = new ToDoDisplay(test2);
    copy = new ToDoDisplay(test1);
  }

  @Test
  public void getToDosToDisplay() throws IOException {
    ToDo todo1 = new ToDo.Builder("buy tickets").addDueDate("11/06/2021").addPriority("1")
        .addCategory("personal").markComplete(false).addId("1").build();
    ToDo todo2 = new ToDo.Builder("buy cell phone").addDueDate("04/17/2021").addPriority("3")
        .addCategory("personal").markComplete(true).addId("2").build();
    ToDo todo3 = new ToDo.Builder("clean house").addDueDate("10/21/2021").addPriority("2")
        .addCategory("home").markComplete(false).addId("3").build();
    ToDo todo4 = new ToDo.Builder("pay bills").addDueDate("5/29/2021").addPriority("1")
        .addCategory("home").markComplete(false).addId("4").build();

    List<ToDo> test1 = new ArrayList<>();
    List<ToDo> test2 = new ArrayList<>();

    test1.add(todo1);
    test1.add(todo2);
    test2.add(todo3);
    test2.add(todo4);

    ToDoDisplay myDisplay = new ToDoDisplay(test1);
    ToDoDisplay anotherDisplay = new ToDoDisplay(test2);

    myDisplay.displayManager(parser);

    //assertEquals(myDisplay, anotherDisplay);

  }

  @Test
  public void filterByCategory() throws InvalidPairException, InvalidArgumentException {
    ToDo categorized1 = new ToDo.Builder("Finish HW9").addDueDate("03/22/2020").addPriority("1")
        .addCategory("school").markComplete(false).addId("1").build();
    ToDo categorized2 = new ToDo.Builder("Study for finals").addPriority("2")
        .addCategory("school").markComplete(false).addId("3").build();
    ToDo todo3 = new ToDo.Builder("clean house").addDueDate("10/21/2021").addPriority("2")
        .addCategory("home").markComplete(false).addId("2").build();

    List<ToDo> categorized = new ArrayList<>();
    categorized.add(categorized1);
    categorized.add(categorized2);

    List<ToDo> testCategory = new ArrayList<>();
    testCategory.add(categorized1);
    testCategory.add(todo3);
    testCategory.add(categorized2);
    String[] arg = new String[]{CSV, "todosTest.csv", DISPLAY, SHOW_CATEGORY, "school"};
    CommandLineParser parserCat = new CommandLineParser(arg, options);
    ToDoDisplay categoryDisplay = new ToDoDisplay(testCategory);
    assertEquals(categoryDisplay.displayManager(parserCat), categorized);
  }

  @Test
  public void filterByIncomplete() throws InvalidPairException, InvalidArgumentException {
    ToDo incomplete = new ToDo.Builder("Finish HW9").addDueDate("03/22/2020").addPriority("1")
        .addCategory("school").markComplete(false).addId("1").build();
    ToDo categorized2 = new ToDo.Builder("Study for finals").addPriority("2")
        .addCategory("school").markComplete(false).addId("3").build();
    ToDo todo3 = new ToDo.Builder("clean house").addDueDate("10/21/2021").addPriority("2")
        .addCategory("home").markComplete(true).addId("2").build();

    List<ToDo> incompleteList = new ArrayList<>();
    incompleteList.add(incomplete);
    incompleteList.add(categorized2);

    List<ToDo> testIncomplete = new ArrayList<>();
    testIncomplete.add(incomplete);
    testIncomplete.add(todo3);
    testIncomplete.add(categorized2);
    String[] arg = new String[]{CSV, "todosTest.csv", DISPLAY, SHOW_INCOMPLETE, "school"};
    CommandLineParser parserCat = new CommandLineParser(arg, options);
    ToDoDisplay incompleteDisplay = new ToDoDisplay(testIncomplete);
    assertEquals(incompleteDisplay.displayManager(parserCat), incompleteList);
  }

  @Test
  public void sortByDate() throws InvalidArgumentException, InvalidPairException {
    ToDo todo1 = new ToDo.Builder("Finish HW9").addDueDate("03/22/2020").addPriority("1")
        .addCategory("school").markComplete(false).addId("1").build();
    ToDo todo2 = new ToDo.Builder("Study for finals").addPriority("2")
        .addCategory("school").markComplete(false).addId("3").build();
    ToDo todo3 = new ToDo.Builder("clean house").addDueDate("10/21/2021").addPriority("2")
        .addCategory("home").markComplete(false).addId("2").build();

    List<ToDo> expected = new ArrayList<>();
    expected.add(todo1);
    expected.add(todo3);
    String[] arg = new String[]{CSV, "todosTest.csv", SORT_BY_DATE, DISPLAY};
    CommandLineParser parserDate = new CommandLineParser(arg, options);
    List<ToDo> sortByDate = new ArrayList<>();
    sortByDate.add(todo1);
    sortByDate.add(todo2);
    sortByDate.add(todo3);
    ToDoDisplay myDisplay = new ToDoDisplay(sortByDate);

    assertEquals(expected, myDisplay.displayManager(parserDate));

  }

  @Test
  public void sortByPriority() throws InvalidArgumentException, InvalidPairException {
    ToDo todo1 = new ToDo.Builder("Finish HW9").addDueDate("03/22/2020").addPriority("1")
        .addCategory("school").markComplete(false).addId("1").build();
    ToDo todo2 = new ToDo.Builder("Study for finals").addPriority("2")
        .addCategory("school").markComplete(false).addId("3").build();
    ToDo todo3 = new ToDo.Builder("clean house").addDueDate("10/21/2021").addPriority("2")
        .addCategory("home").markComplete(false).addId("2").build();

    List<ToDo> newList = new ArrayList<>();
    newList.add(todo1);
    newList.add(todo2);
    newList.add(todo3);
    String[] arg = new String[]{CSV, "todosTest.csv", SORT_BY_PRIORITY, "2"};
    CommandLineParser parserDate = new CommandLineParser(arg, options);
    ToDoDisplay myDisplay = new ToDoDisplay(newList);

    assertEquals(myDisplay.displayManager(parserDate), newList);

  }

  @Test
  public void testEquals() {
    assertTrue(testDisplay.equals(testDisplay));
    assertTrue(testDisplay.equals(copy));
    assertFalse(testDisplay.equals(""));
    assertFalse(testDisplay.equals(testDisplay2));
    assertTrue(testDisplay.equals(copy));
  }


  @Test
  public void testHashCode() {
    assertTrue(testDisplay.hashCode() == copy.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("ToDoDisplay{toDos=[ToDo{text='buy tickets', dueDate=2021-11-06, priority=1, "
            + "category='personal', completed=false, id='1'}, ToDo{text='buy cell phone', "
            + "dueDate=2021-04-17, priority=3, category='personal', completed=true, id='2'}]}",
        testDisplay.toString());
  }
}
