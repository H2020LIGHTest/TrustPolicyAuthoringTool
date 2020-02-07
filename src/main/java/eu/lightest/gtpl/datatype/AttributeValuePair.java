/* AttributeValuePair.java 
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
public class AttributeValuePair implements Serializable{
  public AttributeNamePair attributeNamePair = new AttributeNamePair();
  public Valuetype value;

  public AttributeValuePair(){}
  public AttributeValuePair(AttributeNamePair attributeNamePair, Valuetype value) {
    this.attributeNamePair = attributeNamePair;
    this.value = value;
  }

  public ArrayList<String> getVars(){
    return value.getVars();
  }

  @Override
  public String toString() {
    return "(" + attributeNamePair.attributename + "," + value.toDebugString() + ")" ;
  }
}
