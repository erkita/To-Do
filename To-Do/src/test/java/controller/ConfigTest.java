package controller;

import static controller.utilities.Constants.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConfigTest {
  Config configTest;
  Options optionsTest;

  @Before
  public void setUp() throws Exception {
    configTest = new Config();
    optionsTest = new Options();
    optionsTest.addOption(ADD_TODO, new Option(ADD_TODO, false));
    optionsTest.addOption(TODO_TEXT, new Option(TODO_TEXT, true));
    optionsTest.addOption(CSV,new Option(CSV, true));
    optionsTest.addOption(DUE,new Option(DUE, true));
    optionsTest.addOption(COMPLETED, new Option(COMPLETED, false));
    optionsTest.addOption(PRIORITY,new Option(PRIORITY, true));
    optionsTest.addOption(COMPLETE_TODO, new Option(COMPLETE_TODO, true));
    optionsTest.addOption(SORT_BY_DATE, new Option(SORT_BY_DATE, false));
    optionsTest.addOption(SORT_BY_PRIORITY,new Option(SORT_BY_PRIORITY, false));
    optionsTest.addOption(CATEGORY, new Option(CATEGORY, true));
    optionsTest.addOption(DISPLAY, new Option(DISPLAY,false));
    optionsTest.addOption(SHOW_INCOMPLETE, new Option(SHOW_INCOMPLETE,false));
    optionsTest.addOption(SHOW_CATEGORY,new Option(SHOW_CATEGORY,true));
  }
  @Test
  public void getOptions() {
    assertEquals(optionsTest, configTest.getOptions());
  }
}