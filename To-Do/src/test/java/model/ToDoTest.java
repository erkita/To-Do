package model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import model.ToDo.Builder;
import org.junit.Before;
import org.junit.Test;

public class ToDoTest {
  ToDo todo;
  ToDo todo2;
  ToDo todo3;
  ToDo todoDuplicate;
  Builder builder1;
  Builder builder2;
  Builder builder3;
  String date1 = "4/18/2021";
  String date2 = "04/01/2021";
  LocalDate localDate1 = LocalDate.of(2021, 4, 18);
  LocalDate localDate2 = LocalDate.of(2021, 4, 1);
  ToDoList toDoList;

  @Before
  public void setUp() {
    builder1 = new ToDo.Builder("task1");
    builder1.addDueDate(date1);
    builder1.addPriority("3");
    builder1.addId("1");
    todo = builder1.build();
    todoDuplicate = builder1.build();

    builder2 = new ToDo.Builder("task2");
    builder2.addDueDate(date2);
    builder2.addPriority("2");
    builder2.addCategory("home");
    builder2.addId("2");
    builder2.markComplete(true);
    todo2 = builder2.build();

    builder3 = new ToDo.Builder("task3");
    todo3 = builder3.build();
  }

  @Test
  public void getText() {
    assertEquals("task1", todo.getText());
  }

  @Test (expected = NullPointerException.class)
  public void invalidDateFormat() throws NullPointerException {
    builder3 = new ToDo.Builder("task3");
    builder3.addDueDate("4419");
    todo3 = builder3.build();
  }

  @Test
  public void getDueDate() {
    assertEquals(localDate1, todo.getDueDate());
    assertEquals(localDate2, todo2.getDueDate());
    assertNull(todo3.getDueDate());
  }

  @Test (expected = IllegalArgumentException.class)
  public void negativePriority() throws IllegalArgumentException {
    builder1.addPriority("-1");
    builder1.build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidPriority() throws IllegalArgumentException {
    builder1.addPriority("5");
    builder1.build();
  }

  @Test
  public void getPriority() {
    assertEquals(3, todo.getPriority(), 0.01);
    assertNull(todo3.getPriority());
  }

  @Test
  public void getCategory() {
    assertNull(todo.getCategory());
    assertEquals("home", todo2.getCategory());
  }

  @Test
  public void isCompleted() {
    assertFalse(todo.isCompleted());
  }

  @Test
  public void getId() {
    assertEquals("1", todo.getId());
  }

  @Test
  public void setCompleted() {
    todo.setCompleted();
    assertTrue(todo.isCompleted());
  }

  @Test
  public void testEquals() {
    assertTrue(todo.equals(todo));
    assertFalse(todo.equals(todo2));
    assertFalse(todo.equals(""));
    assertFalse(todo.equals(toDoList));
    assertTrue(todo.equals(todoDuplicate));
  }

  @Test
  public void testHashCode() {
    assertTrue(todo.hashCode() == todoDuplicate.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("ToDo{text='task1', dueDate=2021-04-18, priority=3, category='null', completed=false, id='1'}",
        todo.toString());
  }
}