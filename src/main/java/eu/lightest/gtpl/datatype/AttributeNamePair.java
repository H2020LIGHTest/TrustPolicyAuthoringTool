/* AttributeNamePair.java 
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

/**
 *
 * @author bnia
 */
public class AttributeNamePair implements Serializable{
  public String displayname;
  public String attributename;

  public AttributeNamePair(){}

  public AttributeNamePair(String attributename){
    this.displayname = attributename;
    this.attributename = attributename;
  }

  public AttributeNamePair(String displayname, String attributename) {
    this.displayname = displayname;
    this.attributename = attributename;
  }
}