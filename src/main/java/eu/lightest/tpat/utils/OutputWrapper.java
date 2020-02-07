package eu.lightest.tpat.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

public class OutputWrapper {


  private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final static PrintStream oldOut = System.out;

  public static<T, K> Map.Entry<T, String> wrap(Function<K, T> func, K arg) {
    outContent.reset();
    System.setOut(new PrintStream(outContent));
    T res = func.apply(arg);
    System.setOut(oldOut);
    System.out.println(outContent);
    return new AbstractMap.SimpleEntry<>(res, outContent.toString());
  }
}
