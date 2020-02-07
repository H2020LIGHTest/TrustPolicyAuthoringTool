/* Operator.java
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

public class Operator extends Valuetype implements Serializable {
  public String op;

  public Operator(String op){
    this.op = op;
  }

  @Override
  public ArrayList<String> getVars() {
    return new ArrayList<>();
  }

  @Override
  public String toDebugString() {
    return "Operator(" + op + ")";
  }
}