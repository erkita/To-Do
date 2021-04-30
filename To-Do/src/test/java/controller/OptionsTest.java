package controller;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OptionsTest {
  Options options, copy, nullOption;
  Map<String,Option> aMap;

  @Before
  public void setUp() throws Exception {
    aMap = new HashMap<>();
    options = new Options(aMap);
    copy = new Options(aMap);
  }

  @Test
  public void getOptions() {
    assertEquals( aMap,options.getOptions());
  }

  @Test
  public void testEquals() {
    assertTrue(options.equals(options));
    assertTrue(options.equals(copy));
    assertFalse(options.equals(""));
    assertFalse(options.equals(nullOption));
  }

  @Test
  public void testHashCode() {
    assertTrue(options.hashCode()== copy.hashCode());
  }
}