/* FormPaneContent.java 
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
import javafx.scene.layout.GridPane;

/**
 *
 * @author bnia
 */
public class FormPaneContent {
  public int attributeCounter;
  public GridPane claimPane;
  public ArrayList<Object> textFieldList;

  public FormPaneContent() {}

  public FormPaneContent(int attributeCounter) {
    this.attributeCounter = attributeCounter;
  }

  public FormPaneContent(int attributeCounter, ArrayList<Object> textFieldList) {
    this.attributeCounter = attributeCounter;
    this.textFieldList = textFieldList;
  }

  public FormPaneContent(int attributeCounter, GridPane claimPane, ArrayList<Object> textFieldList) {
    this.attributeCounter = attributeCounter;
    this.claimPane = claimPane;
    this.textFieldList = textFieldList;
  }
}