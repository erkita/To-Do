package controller;

import controller.exceptions.InvalidArgumentException;
import controller.exceptions.InvalidPairException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static controller.utilities.Constants.*;

/**
 * Parses and validates command line arguments from controller.Main.
 */
public class CommandLineParser {

  private Options options;
  private Map<String, Option> optionMap;
  private String csvPath, dueDate, textDescription, priority, categoryName, categoryDisplay, markComplete;
  private List<String> completeID;
  private static final int ZERO = 0;


  /**
   * Constructs CommandLineParser by instantiating command line argument and options.
   *
   * @param args    a user's input from the command line.
   * @param options recognized options.
   * @throws InvalidArgumentException if the command line input is invalid.
   * @throws InvalidPairException if arguments expected to be input as a pair is not in a pair.
   */
  public CommandLineParser(String[] args, Options options)
      throws InvalidArgumentException, InvalidPairException {
    this.options = options;
    this.optionMap = new HashMap<>();
    this.parse(args);
    if (isValidInput()) {
      setUpTextDescription();
      setUpDueDate();
      setUpPriority();
      setUpCategory();
      setUpCompleteId();
      setUpCategoryDisplay();
    }
  }

  /**
   * Sets up text description from command line argument by assigning its proper Option value.
   * @throws InvalidArgumentException if the command line input is invalid.
   * @throws InvalidPairException if arguments expected to be input as a pair is not in a pair.
   */
  private void setUpTextDescription() throws InvalidPairException, InvalidArgumentException {
    if (this.hasPairedOption(ADD_TODO, TODO_TEXT)) {
      this.textDescription = this.getOptionValue(this.optionMap.get(TODO_TEXT));
    }
  }

  /**
   * Sets up due date from command line argument by assigning its proper Option value.
   * @throws InvalidArgumentException if the command line input is invalid.
   */
  private void setUpDueDate() throws InvalidArgumentException {
    if (this.hasOption(DUE)) {
      this.dueDate = this.getOptionValue(this.optionMap.get(DUE));
    }
  }

  /**
   * Sets up priority from command line argument by assigning its proper Option value.
   * @throws InvalidArgumentException if the command line input is invalid.
   */
  private void setUpPriority() throws InvalidArgumentException {
    if (this.hasOption(PRIORITY)) {
      this.priority = this.getOptionValue(optionMap.get(PRIORITY));
    }
  }

  /**
   * Sets up category option from command line argument by assigning its proper Option value.
   * @throws InvalidArgumentException if the command line input is invalid.
   */
  private void setUpCategory() throws InvalidArgumentException {
    if (this.hasOption(CATEGORY)) {
      this.categoryName = this.getOptionValue(optionMap.get(CATEGORY));
    }
  }

  /**
   * Sets up complete id option from command line argument by assigning its proper Option value.
   * @throws InvalidArgumentException if the command line input is invalid.
   */
  private void setUpCompleteId() throws InvalidArgumentException {
    if (this.hasOption(COMPLETE_TODO)) {
      this.completeID = this.getOptionValues(optionMap.get(COMPLETE_TODO));
    }
  }

  /**
   * Sets up category display option from command line argument by assigning its proper Option value.
   * @throws InvalidArgumentException if the command line input is invalid.
   */
  private void setUpCategoryDisplay() throws InvalidArgumentException {
    if (this.hasOption(SHOW_CATEGORY)) {
      this.categoryDisplay = this.getOptionValue(optionMap.get(SHOW_CATEGORY));
    }
  }

  /**
   * Returns CSV file path specified from command line argument.
   *
   * @return CSV file path specified from command line argument.
   */
  public String getCsvPath() {
    return this.csvPath;
  }

  /**
   * Returns due date specified from command line argument.
   *
   * @return due date path specified from command line argument.
   */
  public String getDueDate() {
    return this.dueDate;
  }

  /**
   * Returns To-Do text description specified from command line argument.
   *
   * @return To-Do text description specified from command line argument.
   */
  public String getTextDescription() {
    return this.textDescription;
  }

  /**
   * Returns To-Do priority specified from command line argument.
   *
   * @return To-Do priority specified from command line argument.
   */
  public String getPriority() {
    return this.priority;
  }

  /**
   * Returns To-Do category name specified from command line argument.
   *
   * @return To-Do category name specified from command line argument.
   */
  public String getCategoryName() {
    return this.categoryName;
  }

  /**
   * get complete ID list of string
   * @return complete ID list of string
   */
  public List<String> getCompleteID() {
    return this.completeID;
  }

  /**
   * Returns the ID of To-Do to be marked completed.
   * @return the ID of To-Do to be marked completed.
   */
  public String getMarkComplete() {
    return this.markComplete;
  }

  /**
   * get category display string from user input
   * @return category display string from user input
   */
  public String getCategoryDisplay() {
    return this.categoryDisplay;
  }

  /**
   * Converts a command line input into a HashMap that stores use an option's name as key, and an Option as value.
   *
   * @param args a user's command line input.
   * @throws InvalidArgumentException if the command line input is invalid.
   */
  private void parse(String[] args) throws InvalidArgumentException {
    if (!args[0].startsWith("--")) {
      throw new InvalidArgumentException("The command line should start with an option.");
    }
    Option option = null;
    for (String arg : args) {
      // if an element in args an option, but is an unrecognized one, the input is invalid.
      if (arg.startsWith("--")) {
        if (!this.options.getOptions().containsKey(arg)) {
          throw new InvalidArgumentException("An unrecognized option is given!");
        }
        // if this is an option, put its name and Option into the OptionsMap.
        else {
          option = this.options.getOptions().get(arg);
          this.optionMap.put(arg, option);
        }
      } else
        // if this is not an option, add it to the current option's values.
        if (option != null) {
          option.getValue().add(arg);
        }
    }
  }

  /**
   * Returns true if option exists, false otherwise.
   *
   * @param opt option to be identified
   * @return true if option exists, false otherwise.
   */
  public boolean hasOption(String opt) {
    return this.optionMap.containsKey(opt);
  }

  /**
   * Ensures command line argument has minimum required arguments.
   *
   * @return true if command line argument has minimum required arguments, false otherwise.
   */
  private boolean hasRequiredOptions() {

    return this.optionMap.containsKey(CSV);
  }

  /**
   * Returns true if the argument is expected to have a required command that follows immediately. False otherwise.
   *
   * @param opt1 - first command
   * @param opt2 - second command immediately following the first command
   * @return true if the argument is expected to have a required command that follows immediately. False otherwise.
   * @throws InvalidPairException if paired option is not provided
   */
  private boolean hasPairedOption(String opt1, String opt2) throws InvalidPairException {
    if (this.hasOption(opt1)) {
      if (this.hasOption(opt2)) {
        return true;
      } else {
        throw new InvalidPairException(
            opt1 + " provided but no" + opt2
                + " was given");
      }
    }
    return false;
  }

  /**
   * Returns the option value.
   *
   * @param option - option to be retrieve the value for
   * @return the option value.
   * @throws InvalidArgumentException if the option is missing or has no argument.
   */
  private String getOptionValue(Option option) throws InvalidArgumentException {
    if (option.hasArg()) {
      if (option.getValue().size() != 0) { // list is not empty
        return option.getValue().get(ZERO);
      }
      throw new InvalidArgumentException("Option is missing an argument");
    }
    throw new InvalidArgumentException("option has no argument!");
  }

  /**
   * get list of string option value
   * @param option option to be retrieve the value for
   * @return option value
   * @throws InvalidArgumentException if the option is missing or has no argument.
   */
  public List<String> getOptionValues(Option option) throws InvalidArgumentException {
    if (option.hasArg()) {
      if (option.getValue().size() != 0) { // list is not empty
        return option.getValue();
      } else {
        throw new InvalidArgumentException("Option is missing an argument");
      }
    } else {
      throw new InvalidArgumentException("option has no argument!");
    }
  }

  /**
   * Returns true if 2 options are mutually exclusive, false otherwise.
   *
   * @param opt1 first option to be compared
   * @param opt2 second option to be compared
   * @return true if 2 options are mutually exclusive, false otherwise.
   */
  private boolean hasMutuallyExclusive(String opt1, String opt2) {
    return this.hasOption(opt1) && this.hasOption(opt2);
  }

  /**
   * Ensures input has required pair of command line arguments.
   *
   * @throws InvalidArgumentException if required option is missing.
   */
  private boolean isValidInput() throws InvalidArgumentException {
    if (this.hasMutuallyExclusive(SORT_BY_DATE, SORT_BY_PRIORITY)) {
      throw new InvalidArgumentException("Sort-by-date and Sort-by priority cannot be combined!");
    }
    if (this.hasRequiredOptions()) {
      this.csvPath = this.getOptionValue(optionMap.get(CSV));
      return true;
    } else {
      throw new InvalidArgumentException("Required option missing!");
    }
  }

  /**
   * Checks equality of a To-Do object.
   *
   * @param o the object to check
   * @return true if the objects are the same, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommandLineParser that = (CommandLineParser) o;
    return Objects.equals(this.options, that.options) && Objects.equals(this.optionMap, that.optionMap)
        && Objects.equals(this.csvPath, that.csvPath) && Objects.equals(this.dueDate, that.dueDate)
        && Objects.equals(this.textDescription, that.textDescription) && Objects
        .equals(this.priority, that.priority) && Objects.equals(this.categoryName, that.categoryName) && Objects
        .equals(this.categoryDisplay, that.categoryDisplay) && Objects.equals(this.markComplete, that.markComplete)
        && Objects.equals(this.completeID, that.completeID);
  }

  /**
   * Generates a hash code for the To-Do object.
   *
   * @return the hash code for the To-Do object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.options, this.optionMap, this.csvPath, this.dueDate, this.textDescription, this.priority,
        this.categoryName, this.categoryDisplay, this.markComplete, this.completeID);
  }

  /**
   * Return string representation of a CommandLineParser instance.
   * @return string representation of a CommandLineParser instance.
   */
  @Override
  public String toString() {
    return "CommandLineParser{" +
        "csvPath='" + this.csvPath + '\'' +
        ", dueDate='" + this.dueDate + '\'' +
        ", textDescription='" + this.textDescription + '\'' +
        ", priority='" + this.priority + '\'' +
        ", categoryName='" + this.categoryName + '\'' +
        ", categoryDisplay='" + this.categoryDisplay + '\'' +
        ", markComplete='" + this.markComplete + '\'' +
        ", completeID=" + this.completeID +
        '}';
  }
}

