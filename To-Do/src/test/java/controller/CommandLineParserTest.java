package controller;

import static controller.utilities.Constants.*;
import static org.junit.Assert.*;

import controller.exceptions.InvalidArgumentException;
import controller.exceptions.InvalidPairException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CommandLineParserTest {

  String[] missingRequired, noText, validArgs, notOption, complete, noCombine, noID;
  CommandLineParser parser, copy, parser1, parser2;
  String csvFile, textDescription;
  Options options;


  @Before
  public void setUp() throws Exception {
    csvFile = "todos.csv";
    textDescription = "finish hw9";
    validArgs = new String[]{TODO_TEXT, textDescription, ADD_TODO, CSV, csvFile};
    notOption = new String[]{textDescription, CSV};
    complete = new String[]{CSV, csvFile, COMPLETE_TODO, "1"};
    noID = new String[]{CSV, csvFile, COMPLETE_TODO};
    options = new Options();
    options.addOption(CSV, new Option(CSV, true));
    options.addOption(ADD_TODO, new Option(ADD_TODO, false));
    options.addOption(TODO_TEXT, new Option(TODO_TEXT, true));
    options.addOption(SORT_BY_DATE, new Option(SORT_BY_DATE, false));
    options.addOption(SORT_BY_PRIORITY, new Option(SORT_BY_PRIORITY, false));
    options.addOption(COMPLETE_TODO, new Option(COMPLETE_TODO, true));
    parser = new CommandLineParser(validArgs, options);
    copy = new CommandLineParser(validArgs, options);
    parser1 = new CommandLineParser(complete, options);
    parser2 = new CommandLineParser(noID, options);

  }

  @Test
  public void hasOption() {
    assertTrue(parser.hasOption(ADD_TODO));
  }

  @Test(expected = InvalidArgumentException.class)
  public void missingRequired() throws InvalidArgumentException, InvalidPairException {
    missingRequired = new String[]{ADD_TODO, TODO_TEXT, textDescription};
    new CommandLineParser(missingRequired, options);
  }

  @Test(expected = InvalidPairException.class)
  public void noTodoText() throws InvalidArgumentException, InvalidPairException {
    noText = new String[]{CSV, csvFile, ADD_TODO};
    new CommandLineParser(noText, options);
  }

  @Test(expected = InvalidArgumentException.class)
  public void notOption() throws InvalidArgumentException, InvalidPairException {
    new CommandLineParser(notOption, options);
  }

  @Test(expected = InvalidArgumentException.class)
  public void noCombine() throws InvalidArgumentException, InvalidPairException {
    noCombine = new String[]{CSV, csvFile, SORT_BY_DATE, SORT_BY_PRIORITY};
    new CommandLineParser(noCombine, options);
  }

  @Test(expected = InvalidArgumentException.class)
  public void noID() throws InvalidArgumentException, InvalidPairException {
    parser2.getOptionValues(new Option(COMPLETE_TODO, true));
  }

  @Test
  public void testToString() {
    String expected =
        "CommandLineParser{csvPath='todos.csv', dueDate='null', textDescription='finish hw9',"
            + " priority='null', categoryName='null', categoryDisplay='null', markComplete='null', completeID=null}";
    assertEquals(parser.toString(), expected);
  }

  @Test
  public void getCsvPath() {
    assertEquals(csvFile, parser.getCsvPath());
  }

  @Test
  public void getDueDate() {
    assertNull(parser.getDueDate());
  }

  @Test
  public void getTextDescription() {
    assertEquals("finish hw9", parser.getTextDescription());
  }

  @Test
  public void getPriority() {
    assertNull(parser.getPriority());
  }

  @Test
  public void getCategoryName() {
    assertNull(parser.getCategoryName());
  }

  @Test
  public void getCompleteID() {
    List<String> expected = new ArrayList<>();
    expected.add("1");
    assertEquals(expected, parser1.getCompleteID());
  }

  @Test
  public void testEquals() {
    assertTrue(parser.equals(parser));
    assertFalse(parser.equals(parser2));
    assertFalse(parser.equals(""));
    assertFalse(parser.equals(options));
    assertTrue(parser.equals(copy));
  }

  @Test
  public void testHashCode() {
    assertTrue(parser.hashCode() == copy.hashCode());
  }


  @Test
  public void getCategoryDisplay() {
    assertNull(parser.getCategoryDisplay());
  }
}