package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * To-Do keeps track of task's information.
 */
public class ToDo {

  private final static boolean DEFAULT_COMPLETED = false;
  private final static int LOW_PRIORITY = 3;
  private final static int HIGH_PRIORITY = 1;
  private final static int MDDYYYY_FORMAT = 9;
  private final static int MMDDYYYY_FORMAT = 10;
  private final String text;
  private final LocalDate dueDate;
  private final Integer priority;
  private final String category;
  private Boolean completed;
  private final String id;

  /**
   * Constructs To-Do object by instantiating task text description, due date, priority, category, completion, and id.
   *
   * @param builder - a To-Do builder
   */
  private ToDo(Builder builder) {
    this.text = builder.text;
    this.dueDate = convertToLocalDate(builder.dueDate);
    this.priority = validPriority(builder.priority);
    this.category = builder.category;
    this.completed = builder.completed;
    this.id = builder.id;
  }

  /**
   * Returns the text description of the To-Do.
   *
   * @return the text description of the To-Do.
   */
  public String getText() {
    return this.text;
  }

  /**
   * Returns the due date of the To-Do.
   *
   * @return the due date of the To-Do.
   */
  public LocalDate getDueDate() {
    return this.dueDate;
  }

  /**
   * Returns the priority of the To-Do.
   *
   * @return the priority of the To-Do.
   */
  public Integer getPriority() {
    return this.priority;
  }

  /**
   * Returns the category of the To-Do.
   *
   * @return the category of the To-Do.
   */
  public String getCategory() {
    return this.category;
  }

  /**
   * Returns true if To-Do is completed, false otherwise.
   *
   * @return true if To-Do is completed, false otherwise.
   */
  public Boolean isCompleted() {
    return this.completed;
  }

  /**
   * Returns the id of the To-Do.
   *
   * @return the id of the To-Do.
   */
  public String getId() {
    return this.id;
  }

  /**
   * Changes the boolean completed.
   */
  public void setCompleted() {
    this.completed = true;
  }

  /**
   * NOTE: Preliminary validation method. Confirm this design choice is okay Returns valid priority (integer between 1 -
   * 3 or unassigned) of the To-Do.
   *
   * @param priority - priority of the To-Do.
   * @return valid priority (integer between 1 - 3 or unassigned) of the To-Do.
   */
  private Integer validPriority(String priority) {
    if (priority == null) {
      return null;
    }
    Integer intPriority = Integer.parseInt(priority);
    if (intPriority > LOW_PRIORITY || intPriority < HIGH_PRIORITY) {
      throw new IllegalArgumentException("Priority must be between 1 - 3 or null.");
    }
    return intPriority;
  }

  /**
   * NOTE: Preliminary validation method. Confirm this design choice is okay Returns MM/dd/yyyy formatted LocalDate.
   *
   * @param date due date of the To-Do
   * @return MM/dd/yyyy formatted LocalDate.
   */
  private LocalDate convertToLocalDate(String date) {
    try {
      DateTimeFormatter formatter = null;
      if (date == null) {
        return null;
      }
      if (date.length() == MDDYYYY_FORMAT) {
        formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
      } else if (date.length() == MMDDYYYY_FORMAT) {
        formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
      }
      return LocalDate.parse(date, formatter);
    } catch (NullPointerException e) {
      throw new NullPointerException("Invalid date format, must be in MM/dd/yyyy");
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
    ToDo toDo = (ToDo) o;
    return Objects.equals(this.text, toDo.text) && Objects.equals(this.dueDate, toDo.dueDate)
        && Objects.equals(this.priority, toDo.priority) && Objects.equals(this.category, toDo.category)
        && Objects.equals(this.completed, toDo.completed) && Objects.equals(this.id, toDo.id);
  }

  /**
   * Generates a hash code for the To-Do object.
   *
   * @return the hash code for the To-Do object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(text, dueDate, priority, category, completed, id);
  }

  /**
   * Generates a string representing the To-Do object.
   *
   * @return a string representing the To-Do object.
   */
  @Override
  public String toString() {
    return "ToDo{" +
        "text='" + this.text + '\'' +
        ", dueDate=" + this.dueDate +
        ", priority=" + this.priority +
        ", category='" + this.category + '\'' +
        ", completed=" + this.completed +
        ", id='" + this.id + '\'' +
        '}';
  }


  /**
   * Builder class of To-Do object.
   */
  public static class Builder {

    private boolean completed; // default false
    private final String text; // required parameter
    private String dueDate;
    private String priority;
    private String category;
    private String id;

    /**
     * Constructs Builder by instantiating required or default fields of To-Do.
     *
     * @param text text description of To-Do
     */
    public Builder(String text) {
      this.text = text;
      this.completed = DEFAULT_COMPLETED;
    }

    /**
     * Adds due date of the To-Do.
     *
     * @param due due date of the To-Do
     * @return To-Do Builder with newly specified due date.
     */
    public Builder addDueDate(String due) {
      this.dueDate = due;
      return this;
    }

    /**
     * Adds valid priority (integer between 1 - 3 or unassigned) to the To-Do.
     *
     * @param priority integer indicating priority of To=Do (1-3); 1 being highest priority
     * @return To-Do Builder with newly specified priority.
     */
    public Builder addPriority(String priority) {
      this.priority = priority;
      return this;
    }

    /**
     * Adds category to the To-Do.
     *
     * @param category user-specified group related To-Do
     * @return To-Do Builder with newly specified category.
     */
    public Builder addCategory(String category) {
      this.category = category;
      return this;
    }

    /**
     * Adds To-Do's ID.
     *
     * @param id identification of the single To-Do task
     * @return To-Do Builder with newly specified ID.
     */
    public Builder addId(String id) {
      this.id = id;
      return this;
    }

    /**
     * Marks task completed by boolean.
     *
     * @param completed indicates completion of the task
     * @return true if completed, false otherwise.
     */
    public Builder markComplete(boolean completed) {
      this.completed = completed;
      return this;
    }

    /**
     * Builds the To-Do object.
     *
     * @return the To-Do object.
     */
    public ToDo build() {
      return new ToDo(this);
    }
  }
}