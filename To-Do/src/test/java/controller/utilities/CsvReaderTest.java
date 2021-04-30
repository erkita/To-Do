package controller.utilities;

import controller.utilities.CsvReader;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvReaderTest {
  String[] firstRow;
  String[] secondRow;

  @Before
  public void setUp() throws Exception {
    firstRow = new String[]{"id", "text", "completed", "due", "priority", "category"};
    secondRow = new String[]{"1", "Finish HW9", "false", "3/22/2020", "1", "school"};
  }

  @Test
  public void readCsv() throws IOException {
    List<String[]> out = CsvReader.readCsv("todos.csv");
//    assertArrayEquals(out.get(0), firstRow);
//    assertArrayEquals(out.get(1), secondRow);
  }
}