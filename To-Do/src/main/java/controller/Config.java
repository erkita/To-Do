package controller;

import static controller.utilities.Constants.*;

/**
 * a configuration class to create option settings.
 */
public class Config {
  private Options options;

  /**
   * get all options that's specified
   * @return all options.
   */
  public Options getOptions() {
    return this.options;
  }

  /**
   * constructor for config class
   */
  public Config() {
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
    Option display = new Option(DISPLAY,false);
    Option showIncomplete = new Option(SHOW_INCOMPLETE,false);
    Option showCategory = new Option(SHOW_CATEGORY,true);
    this.options = new Options();
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
    options.addOption(DISPLAY,display);
    options.addOption(SHOW_INCOMPLETE,showIncomplete);
    options.addOption(SHOW_CATEGORY,showCategory);

  }
}
