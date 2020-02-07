/* FormFormat.java 
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
public class FormFormat implements Serializable{
  public Form format;

  public FormFormat(){}
  public FormFormat(Form format){
    this.format = format;
  }

  @Override
  public String toString() {
    return format.identifier.dispalyName;
  }
}
