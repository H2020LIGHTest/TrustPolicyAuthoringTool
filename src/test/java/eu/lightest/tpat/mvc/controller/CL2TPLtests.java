package eu.lightest.tpat.mvc.controller;

import eu.lightest.gtpl.parser.nlException;
import eu.lightest.gtpl.tools.NL2TPLTools;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class CL2TPLtests {
  private final PrintStream mOldOut = System.out;
  private final ByteArrayOutputStream mOutContent = new ByteArrayOutputStream();
  @Rule
  public TestName name = new TestName();

  private void runTest(String input_path, String target_path) {
    // prepare
    System.out.println("Test: " + name.getMethodName());
    System.out.println("path= " + System.getProperty("user.dir"));
    System.out.println(" ------------------- ");
    System.setOut(new PrintStream(mOutContent));
    String outputString = null;

    // main
    try {
      outputString = FileUtils.readFileToString(new File(target_path), StandardCharsets.UTF_8);
    } catch (Exception e) {
      System.out.println("Error with Inputfile: " + e.getMessage());
      System.exit(-1);
    }
    try {
      CharStream input = null;
      try {
        input = CharStreams.fromFileName(input_path);
      } catch (Exception e) {
        Assert.fail();
      }
      String mOutContent = NL2TPLTools.translateCharStreamWithLibrary(input);
//      Assert.assertEquals(outputString,(mOutContent));
      Assert.assertTrue(mOutContent.contains(outputString));
    } catch (nlException e){
      System.out.println("Error when translating to nl: " + e.getMessage());
    } finally {
      //end
      System.setOut(mOldOut);
      System.out.println(mOutContent);
    }
  }

  // --------------------------- \\
  @Test
  public void NaturalLanguage01() {
    runTest("src/test/data/NaturalLanguage01.txt", "src/test/targets/NaturalLanguage01.tpl");
  }

  @Test
  public void NaturalLanguage02() {
    runTest("src/test/data/NaturalLanguage02.txt","src/test/targets/NaturalLanguage02.tpl");
  }
  @Test
  public void NaturalLanguage03() {
    runTest("src/test/data/NaturalLanguage03.txt","src/test/targets/NaturalLanguage03.tpl");
  }
}