package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Options maps String to Option object
 */
public class Options {

  private Map<String, Option> options;

  /**
   * Constructor for Options.
   *
   * @param options all options that can be recognized.
   */
  public Options(Map<String, Option> options) {
    this.options = options;
  }

  /**
   * Constructor for Options.
   */
  public Options() {
    this.options = new HashMap<>();
  }

  /**
   * Adds an Option into Options' hashMap.
   *
   * @param optionName the name of an Option.
   * @param option     an Option.
   */
  public void addOption(String optionName, Option option) {
    this.options.put(optionName, option);
  }


  /**
   * Gets all options that can be recognized.
   *
   * @return all options that can be recognized.
   */
  public Map<String, Option> getOptions() {
    return this.options;
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
    Options options1 = (Options) o;
    return getOptions().equals(options1.getOptions());
  }

  /**
   * Return integer representation of an Option instance.
   *
   * @return the integer representation of an Option instance.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getOptions());
  }
}
