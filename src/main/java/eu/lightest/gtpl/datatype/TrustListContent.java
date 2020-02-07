/* TrustListContent.java 
 *
 * Copyright (C) 2018
 * Copyright (C) DTU(Technical University of Denmark) 2018
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license.  See the LICENSE file for details.
 */
package eu.lightest.gtpl.datatype;

import java.util.ArrayList;
import javafx.scene.control.TextField;

/**
 *
 * @author bnia
 */
public class TrustListContent extends Valuetype{
  public TextField textField;
  public ArrayList<Object> textFieldList;

  public TrustListContent() {

  }

  public TrustListContent(TextField textField) {
    this.textField = textField;
  }

  public TrustListContent(TextField textField, ArrayList<Object> textFieldList) {
    this.textField = textField;
    this.textFieldList = textFieldList;
  }


  @Override
  public ArrayList<String> getVars() {
    return new ArrayList<>();
  }

  @Override
  public String toDebugString() {
    String s = "TrustListContent(" + textField + "," + "[";
    for (Object o : textFieldList) {
      s+= o + ",";
    }
    return s + "])";

  }
}