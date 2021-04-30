//package controller.sorts;
//
//import static org.junit.Assert.*;
//
//import java.time.LocalDate;
//import model.ToDo;
//import model.ToDo.Builder;
//import org.junit.Before;
//import org.junit.Test;
//
//public class AscendingDateSortTest {
//  ToDo todo1;
//  ToDo todo2;
//  ToDo todo3;
//  ToDo nullDate;
//  Builder builder1;
//  Builder builder2;
//  Builder builder3;
//  Builder builder4;
//  AscendingDateSort dateSort;
//
//  @Before
//  public void setUp() {
//    String date1 = "4/29/2021";
//    String date2 = "04/16/2021";
//    String date3 = "4/29/2021";
//
//    builder1 = new ToDo.Builder("todo1");
//    builder1.addDueDate(date1);
//    todo1 = builder1.build();
//
//    builder2 = new ToDo.Builder("todo2");
//    builder2.addDueDate(date2);
//    todo2 = builder2.build();
//
//    builder3 = new ToDo.Builder("todo3");
//    builder3.addDueDate(date3);
//    todo3 = builder3.build();
//
//    builder4 = new ToDo.Builder("todo4");
//    nullDate = builder4.build();
//
//    dateSort = new AscendingDateSort();
//  }
//
//  @Test
//  public void compare() {
//    assertTrue(dateSort.compare(todo1, todo2) > 0);
//    assertTrue(dateSort.compare(todo2, todo3) < 0);
//    assertEquals(0, dateSort.compare(todo1, todo3));
//  }
//
//  @Test
//  public void compareNull() {
//    assertEquals(0, dateSort.compare(nullDate, nullDate));
//    assertEquals(-1, dateSort.compare(nullDate, todo2));
//    assertEquals(1, dateSort.compare(todo1, nullDate));
//  }
//}