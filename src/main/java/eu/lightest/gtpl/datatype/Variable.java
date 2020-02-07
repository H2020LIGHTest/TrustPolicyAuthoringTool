/* Variable.java 
 *
 * Copyright (C) 2018
 * Copyright (C) DTU(Technical University of Denmark) 2018
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license.  See the LICENSE file for details.
 */
package eu.lightest.gtpl.datatype;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author bnia
 */
public class Variable extends Valuetype implements Serializable{
  public String variable;

  public Variable() {

  }
  public Variable(String variable) {
    this.variable = variable;
  }

  @Override
  public String toString() {
    return variable;
  }


  @Override
  public ArrayList<String> getVars() {
     ArrayList<String> vars = new ArrayList<>();
     vars.add(variable);
     return vars;
  }

  @Override
  public String toDebugString() {
    return "Variable(" + variable + ")";
  }


}
