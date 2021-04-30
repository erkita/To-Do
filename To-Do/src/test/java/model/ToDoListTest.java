package model;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class ToDoListTest {
  ToDoList todoTest;
  ToDo testToDo;

  @Before
  public void setUp() throws IOException {
  }

  @Test
  public void getToDoList() throws IOException {
    ToDoList todo2 = ToDoList.getInstance();
    todo2.createToDoList("todosTest.csv");
    ToDoList todoDuplicate = ToDoList.getInstance();
    assertEquals(todo2.getToDoList(), todoDuplicate.getToDoList());
  }

  @Test
  public void testEquals() throws IOException {
    ToDoList todo3 = ToDoList.getInstance();
    todo3.createToDoList("todosTest.csv");
    ToDoList todoDuplicate = ToDoList.getInstance();
    todoDuplicate.createToDoList("todosTest.csv");
    assertTrue(todo3.equals(todo3));
    assertFalse(todo3.equals(todoTest));
    assertFalse(todo3.equals(""));
    assertFalse(todo3.equals(testToDo));
    assertTrue(todo3.equals(todoDuplicate));
  }

  @Test
  public void testHashCode() throws IOException {
    ToDoList todo4 = ToDoList.getInstance();
    todo4.createToDoList("todosTest.csv");
    ToDoList todoDuplicate2 = ToDoList.getInstance();
    todoDuplicate2.createToDoList("todosTest.csv");
    assertTrue(todo4.hashCode() == todoDuplicate2.hashCode());
  }

  //  @Test
//  public void testToString() throws IOException {
//    ToDoList todo1 = ToDoList.getInstance();
//    todo1.createToDoList("todos.csv");
//    assertEquals("ToDoList{toDoList=[ToDo{text='Finish HW9', dueDate=2020-03-22, priority=1, category='school',"
//        + " completed=false, id='1'}, ToDo{text='Mail passport', dueDate=2020-02-28, priority=null, category='null', "
//        + "completed=true, id='2'}, ToDo{text='Study for finals', dueDate=null, priority=2, category='school',"
//        + " completed=false, id='3'}, ToDo{text='Clean the house', dueDate=2020-03-22, priority=3, category='home',"
//        + " completed=false, id='4'}, ToDo{text='Buy yarn for blanket, stuffed toy', dueDate=null, priority=null, "
//        + "category='home', completed=true, id='5'}]}", todo1.toString());
//  }
}