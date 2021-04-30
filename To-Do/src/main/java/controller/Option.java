package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Option class to represent the command line arguments.
 */
public class Option {

  private String opt;
  private boolean hasArg;
  private List<String> value;

  /**
   * Constructor for Option class.
   *
   * @param opt    the option provided in the command line.
   * @param hasArg boolean flag indicating if the command line has arguments.
   */
  public Option(String opt, boolean hasArg) {
    this.opt = opt;
    this.hasArg = hasArg;
    this.value = new ArrayList<>();

  }

  /**
   * Gets the option from the command line.
   *
   * @return the option from the command line.
   */
  public String getOpt() {
    return this.opt;
  }

  /**
   * Checks if the command line has arguments.
   *
   * @return true if command line has arguments, false otherwise.
   */
  public boolean hasArg() {
    return this.hasArg;
  }

  /**
   * get option value
   *
   * @return list of string with option value
   */
  public List<String> getValue() {
    return this.value;
  }

  /**
   * Returns the Option instance parameters in a string.
   *
   * @return the Option instance parameters in a string.
   */
  @Override
  public String toString() {
    return "Option{" +
        "opt='" + this.opt + '\'' +
        ", hasArg=" + this.hasArg +
        ", value=" + this.value +
        '}';
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
    Option option = (Option) o;
    return hasArg == option.hasArg && this.getOpt().equals(option.getOpt()) && this.getValue()
        .equals(option.getValue());
  }

  /**
   * Return integer representation of an Option instance.
   *
   * @return the integer representation of an Option instance.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.getOpt(), this.hasArg, this.getValue());
  }

}