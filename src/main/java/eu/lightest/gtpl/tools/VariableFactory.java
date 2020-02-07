package eu.lightest.gtpl.tools;

import java.util.HashSet;
import java.util.List;

/**
 * @author andschl
 */
public class VariableFactory {
  HashSet<String> used = new HashSet<>();

  private String createVariable(String suggestion, Integer i) {
    if (i == null) {
      if (used.contains(suggestion)) {
        return createVariable(suggestion, 2);
      } else {
        used.add(suggestion);
        return suggestion;
      }
    } else {
      if (used.contains(suggestion + i)) {
        return createVariable(suggestion, i + 1);
      } else {
        used.add(suggestion + i);
        return suggestion + i;
      }
    }
  }

  public String createVariable(String suggestion){
    suggestion = suggestion.replaceAll("_","");
    if (suggestion.equals("")){
      suggestion = "Empty";
    }
    suggestion = capitalize(suggestion);
    return createVariable(suggestion,null);
  }

  public void registerVariables(List<String> vars){
    for (String var : vars){
      registerVariable(var);
    }
  }

  public void registerVariable(String var){
    used.add(var);
  }

  private static String capitalize(String s) {
    if (s.length() == 0) {
      return s;
    } else if (s.length() == 1) {
      return s.toUpperCase();
    }
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }
}
