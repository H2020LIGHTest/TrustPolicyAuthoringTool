/* Certificate.java 
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
public class Certificate implements Serializable{
  public Form certificate;

  public Certificate(){}
  public Certificate(Form certificate){
    this.certificate = certificate;
  }

  @Override
  public String toString() {
    return certificate.identifier.dispalyName;
  }
}
