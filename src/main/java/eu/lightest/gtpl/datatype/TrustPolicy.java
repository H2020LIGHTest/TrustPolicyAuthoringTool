/* TrustPolicy.java 
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
public class TrustPolicy implements Serializable{
  public Form form;
  public String ruleName;
  public String tpl;

  public TrustPolicy(){}
  public TrustPolicy(Form form, String tpl, String ruleName) {
    this.form = form;
    this.tpl = tpl;
    this.ruleName = ruleName;
  }


  @Override
  public String toString() {
    return ruleName;
  }
}
