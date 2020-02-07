/* Valuetype.java 
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


public abstract class Valuetype extends AST implements Serializable{

  abstract public ArrayList<String> getVars();

  abstract public String toDebugString();
}
