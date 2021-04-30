package controller.utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.ToDo;

/**
 * CsvWriter class to update the To-Do CSV file when updated.
 */
public final class CsvWriter {
  private static final String HEADER = "\"id\",\"text\",\"completed\",\"due\",\"priority\",\"category\"";
  private static final String START_QUOTE = "\"";
  private static final String END_QUOTE_COMMA = "\",";
  private static final String MM_DD_YYYY_FORMAT = "MM/dd/yyyy";
  private static final String NO_INPUT = "?";

  /**
   * Constructs CsvWriter.
   */
  public CsvWriter() {
  }

  /**
   * Write lines of required fields such as task ID, text description, and state of completion.
   * @param writer - buffered character-output stream
   * @param task - a single task in the To-Do list
   * @throws IOException if there is a Input Output error.
   */
  private void writeRequiredFields(BufferedWriter writer, ToDo task) throws IOException {
    writer.write(START_QUOTE + task.getId() + END_QUOTE_COMMA);
    writer.write(START_QUOTE + task.getText() + END_QUOTE_COMMA);
    writer.write(START_QUOTE + task.isCompleted() + END_QUOTE_COMMA);
  }

  /**
   * Write lines of empty fields as "?".
   * @param writer - buffered character-output stream
   * @throws IOException if there is a Input Output error.
   */
  private static void writeNoValueCell(BufferedWriter writer) throws IOException {
    writer.write(START_QUOTE + NO_INPUT + END_QUOTE_COMMA);
  }

  /**
   * Writes line for due date. "?" if due date has empty input.
   * @param writer - buffered character-output stream
   * @throws IOException if there is a Input Output error.
   */
  private void writeDueDate(BufferedWriter writer, ToDo task) throws IOException {
    if ((task.getDueDate() != null)) {
      writer.write(START_QUOTE + task.getDueDate().format(DateTimeFormatter.ofPattern(MM_DD_YYYY_FORMAT))
          + END_QUOTE_COMMA);
    } else {
      writeNoValueCell(writer);
    }
  }

  /**
   * Writes line for priority. "?" if priority has empty input.
   * @param writer - buffered character-output stream
   * @throws IOException if there is a Input Output error.
   */
  private void writePriority(BufferedWriter writer, ToDo task) throws IOException {
    if ((task.getPriority()) != null) {
      writer.write(START_QUOTE + task.getPriority() + END_QUOTE_COMMA);
    } else {
      writeNoValueCell(writer);
    }
  }

  /**
   * Writes line for category. "?" if category has empty input.
   * @param writer - buffered character-output stream
   * @throws IOException if there is a Input Output error.
   */
  private void writeCategory(BufferedWriter writer, ToDo task) throws IOException {
    if ((task.getCategory()) != null) {
      writer.write(START_QUOTE + task.getCategory() + END_QUOTE_COMMA);
    } else {
      writeNoValueCell(writer);
    }
  }

  /**
   * Writes all the To-Do's in a given CSV file with a header.
   * @param toDoList - list of all To-Do's
   * @param csvFile - CSV file to be written in
   * @throws IOException if there is a Input Output error.
   */
  public void writeToDoCsv(List<ToDo> toDoList, String csvFile) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
      writer.write(HEADER);
      writer.newLine();
      for (ToDo task : toDoList) {
        writeRequiredFields(writer, task);
        writeDueDate(writer, task);
        writePriority(writer, task);
        writeCategory(writer, task);
        writer.newLine();
      }
    } catch (IOException e) {
      throw new IOException("Input output error.");
    }
  }
}
