/* Identifier.java 
 *
 * Copyright (C) 2018
 * Copyright (C) DTU(Technical University of Denmark) 2018
 * All rights reserved 
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
public class Identifier implements Serializable{
  public String dispalyName;
  public String tplIdentifier;

  public Identifier(){}

  public Identifier(String tplIdentifier){
    this.dispalyName = tplIdentifier;
    this.tplIdentifier = tplIdentifier;
  }

  public Identifier(String dispalyName, String tplIdentifier) {
    this.dispalyName = dispalyName;
    this.tplIdentifier = tplIdentifier;
  }
}