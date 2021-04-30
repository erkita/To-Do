package controller;

import static controller.utilities.Constants.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class OptionTest {
  Option addTodo, todoText, copy;
  private List<String> value;

  @Before
  public void setUp() throws Exception {
   addTodo = new Option(ADD_TODO, false);
   todoText = new Option(TODO_TEXT, true);
    copy = new Option(ADD_TODO, false);
    value = new ArrayList<>();

  }

  @Test
  public void getOpt() {
    assertEquals(ADD_TODO, addTodo.getOpt());
  }

  @Test
  public void hasArg() {
    assertFalse(addTodo.hasArg());
  }

  @Test
  public void getValue() {
    assertEquals(new ArrayList<>(), todoText.getValue());
  }

  @Test
  public void testToString() {
    assertEquals("Option{opt='--add-todo', hasArg=false, value=[]}",addTodo.toString());
  }

  @Test
  public void testEquals() {
    assertTrue(addTodo.equals(addTodo));
    assertFalse(addTodo.equals(todoText));
    assertFalse(addTodo.equals(""));
    assertTrue(addTodo.equals(copy));
  }

  @Test
  public void testHashCode() {
    assertTrue(addTodo.hashCode()==copy.hashCode());
  }
}