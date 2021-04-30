package controller.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CsvReader reads the csv file given from the command and makes it into a list.
 */
public final class CsvReader {

  /**
   * Construct private constructor to restrict CsvReader object creation.
   */
  public CsvReader() {
  }

  /**
   * Reads given csvFile and converts it into a string array, where each element in an line of csvFile in an array of
   * strings. (ex. [{"id", "text", ...}, {"1", ...}])
   *
   * @param csvFile given csv file from the command line argument.
   * @return a list of to-dos where each element is an array of strings of csvFile line.
   * @throws IOException if there is an input output error.
   */
  public static List<String[]> readCsv(String csvFile) throws IOException {
    List<String[]> toDoList;
    try (Stream<String> lines = Files.lines(Paths.get(csvFile))) {
      toDoList = lines
          .map(line -> line.replaceAll("^\"|\"$", ""))
          .map(line -> line.split("\",\""))
          .collect(Collectors.toList());
      for (String[] csvLine : toDoList) {
        csvLine[csvLine.length - 1] = csvLine[csvLine.length - 1].replace("\",", "");
      }
    } catch (IOException e) {
      throw new IOException("Input output error.");
    }
    return toDoList;
  }
}