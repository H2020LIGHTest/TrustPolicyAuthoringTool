/* TranslationFormat2Tpl.java
 *
 * Copyright (C) 2018
 * Copyright (C) DTU(Technical University of Denmark) 2018
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license.  See the LICENSE file for details.
 */
package eu.lightest.gtpl.tools;

import eu.lightest.gtpl.datatype.*;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author bnia, andschl
 */
public class TranslationFormat2Tpl {
  private boolean isToplevel = true;
  private VariableFactory varFactory = new VariableFactory();
  private ArrayList<String> signatureVerification_ = new ArrayList<>();

  public TranslationFormat2Tpl() {
  }

  public String createNewVariable(String suggestion) {
    return varFactory.createVariable(suggestion);
  }

  public String translationToplevel(Form form) {
    varFactory = new VariableFactory();
    varFactory.registerVariables(form.getVars());

    isToplevel = true;
    signatureVerification_.clear();
    String newVariable = createNewVariable("Form");
    String tpl = "accept(" + newVariable + "):-\n";
    tpl += fAttlistFormlevel(form, newVariable);
    tpl += String.join("", signatureVerification_);
    tpl += "    .\n\n";
    return tpl;
  }

  public String fAttlistFormlevel(Form form, String formvar) {
    varFactory.registerVariable(formvar);

    String tpl;
    if (isToplevel) {
      isToplevel = false;
      tpl = "    extract(" + formvar + ",format," + form.identifier.tplIdentifier + ")\n";
    } else {
      if (form.genericFormat) {
        tpl = "";
      } else {
        tpl = "    ,extract(" + formvar + ",format," + form.identifier.tplIdentifier + ")\n";
      }
    }
    Stack<AttributeValuePair> atts = createStack(form.attributes);
    tpl += fAttlistAttlevel(atts, formvar);
    return tpl;
  }

  public String fAttlistAttlevel(Stack<AttributeValuePair> attributes, String formvar) {
    if (attributes.empty()) {
      return "";
    }
    AttributeValuePair pair = attributes.pop();
    String attribute = pair.attributeNamePair.attributename;
    Valuetype value = pair.value;
    String tpl = "";

    if (value == null || attribute == null) {
      throw new NullPointerException();

    } else if (value instanceof Blank) {
      return fAttlistAttlevel(attributes, formvar);

    } else if (attribute.equals("signature") && value instanceof Variable) {
      tpl += fAttlistAttlevel(attributes, formvar);
      String verify_signature = "    ,verify_signature(" + formvar + "," + ((Variable) value).variable + ")\n";
      signatureVerification_.add(verify_signature);
      return tpl;

    } else if (attribute.equals("signature") && value instanceof Value) {
      tpl += fAttlistAttlevel(attributes, formvar);
      String verify_signature = "    ,verify_signature(" + formvar + "," + ((Value) value).value + ")\n";
      signatureVerification_.add(verify_signature);
      return tpl;

    } else if (attribute.equals("signature") && value instanceof Trustscheme) {
      AttributeValuePair issuerTrustScheme = new AttributeValuePair(new AttributeNamePair("issuer"), new ClaimTrustList(value));

      Variable PKvar = new Variable(createNewVariable("PK"));
      AttributeValuePair pubKeyPK = new AttributeValuePair(new AttributeNamePair("pubKey"), PKvar);

      Form certificateForm = new Form(new Identifier("x509cert"), new ArrayList<>());
      certificateForm.attributes.add(pubKeyPK);
      certificateForm.attributes.add(issuerTrustScheme);

      AttributeValuePair certificateCertificateForm = new AttributeValuePair(new AttributeNamePair("certificate"), certificateForm);
      AttributeValuePair signaturePK = new AttributeValuePair(new AttributeNamePair("signature"), PKvar);

      attributes.push(certificateCertificateForm);
      attributes.push(signaturePK);

      String tpl2 = fAttlistAttlevel(attributes, formvar);
      tpl += tpl2;
      return tpl;

    } else if (attribute.equals("signature") && value instanceof TrustschemeX) {
      AttributeValuePair issuerTrustScheme = new AttributeValuePair(new AttributeNamePair("issuer"), new ClaimTrustList(value));

      Variable PKvar = new Variable(createNewVariable("PK"));
      AttributeValuePair pubKeyPK = new AttributeValuePair(new AttributeNamePair("pubKey"), PKvar);

      Form certificateForm = new Form(new Identifier("x509cert"), new ArrayList<>());
      certificateForm.attributes.add(pubKeyPK);
      certificateForm.attributes.add(issuerTrustScheme);

      AttributeValuePair certificateCertificateForm = new AttributeValuePair(new AttributeNamePair("certificate"), certificateForm);
      AttributeValuePair signaturePK = new AttributeValuePair(new AttributeNamePair("signature"), PKvar);

      attributes.push(certificateCertificateForm);
      attributes.push(signaturePK);

      String tpl2 = fAttlistAttlevel(attributes, formvar);
      tpl += tpl2;
      return tpl;

    } else if (attribute.equals("issuer") && value instanceof ClaimTrustList
        && ((ClaimTrustList) value).trustListValue instanceof Trustscheme
        && ((ClaimTrustList) value).form == null) {
      Variable PKvar = new Variable(createNewVariable("PKIss"));

      ArrayList<AttributeValuePair> entryFormAttributes = new ArrayList<>();
      entryFormAttributes.add(new AttributeValuePair(new AttributeNamePair("pubKey"), PKvar));
      Form entryForm = new Form(new Identifier("trustlist_entry"), entryFormAttributes);
      Valuetype trustscheme = ((ClaimTrustList) value).trustListValue;
      ClaimTrustList valueEntryForm = new ClaimTrustList(trustscheme, entryForm);
      AttributeValuePair issuerValueEntryForm = new AttributeValuePair(new AttributeNamePair("issuer"), valueEntryForm);
      AttributeValuePair signaturePK = new AttributeValuePair(new AttributeNamePair("signature"), PKvar);
      attributes.push(signaturePK);
      attributes.push(issuerValueEntryForm);
      tpl += fAttlistAttlevel(attributes, formvar);
      return tpl;

    } else if (attribute.equals("issuer") && value instanceof ClaimTrustList
        && ((ClaimTrustList) value).trustListValue instanceof TrustschemeX
        && ((ClaimTrustList) value).form == null) {
      Variable PKvar = new Variable(createNewVariable("PKIss"));

      ArrayList<AttributeValuePair> entryFormAttributes = new ArrayList<>();
      entryFormAttributes.add(new AttributeValuePair(new AttributeNamePair("pubKey"), PKvar));
      Form entryForm = new Form(new Identifier("trustlist_entry"), entryFormAttributes);
      Valuetype trustscheme = ((ClaimTrustList) value).trustListValue;
      ClaimTrustList valueEntryForm = new ClaimTrustList(trustscheme, entryForm);
      AttributeValuePair issuerValueEntryForm = new AttributeValuePair(new AttributeNamePair("issuer"), valueEntryForm);
      AttributeValuePair signaturePK = new AttributeValuePair(new AttributeNamePair("signature"), PKvar);
      attributes.push(signaturePK);
      attributes.push(issuerValueEntryForm);
      tpl += fAttlistAttlevel(attributes, formvar);
      return tpl;

    } else if (attribute.equals("issuer") && value instanceof ClaimTrustList
        && ((ClaimTrustList) value).trustListValue instanceof Trustscheme
        && ((ClaimTrustList) value).form != null) {
      String TrustMemClaim = createNewVariable("TrustMemClaim");
      String TrustListEntry = createNewVariable("TrustListEntry");
      String IssuerCertificate = createNewVariable("Issuer");

      tpl += "    ,extract(" + formvar + ",issuer," + IssuerCertificate + ")\n";
      tpl += "    ,extract(" + IssuerCertificate + ",trustScheme," + TrustMemClaim + ")\n";
      tpl += "    ,trustscheme(" + TrustMemClaim + "," + ((Trustscheme) ((ClaimTrustList) value).trustListValue).value + ")\n";
      tpl += "    ,trustlist(" + TrustMemClaim + "," + IssuerCertificate + "," + TrustListEntry + ")\n";
      tpl += fAttlistFormlevel(((ClaimTrustList) value).form, TrustListEntry);
      tpl += fAttlistAttlevel(attributes, formvar);
      return tpl;

    } else if (attribute.equals("issuer") && value instanceof ClaimTrustList
        && ((ClaimTrustList) value).trustListValue instanceof TrustschemeX
        && ((ClaimTrustList) value).form != null) {
      String TrustMemClaim = createNewVariable("TrustMemClaim");
      String TrustListEntry = createNewVariable("TrustListEntry");
      String IssuerCertificate = createNewVariable("Issuer");

      tpl += "    ,extract(" + formvar + ",issuer," + IssuerCertificate + ")\n";
      tpl += "    ,extract(" + IssuerCertificate + ",trustScheme," + TrustMemClaim + ")\n";
      tpl += "    ,trustschemeX(" + IssuerCertificate + "," + ((TrustschemeX) ((ClaimTrustList) value).trustListValue).value + "," + TrustListEntry + ")\n";
      tpl += fAttlistFormlevel(((ClaimTrustList) value).form, TrustListEntry);
      tpl += fAttlistAttlevel(attributes, formvar);
      return tpl;
    } else if (attribute.equals("delegation") && value instanceof Trustscheme) {
      String Delegation = createNewVariable("Delegation");
      tpl += "    ,extract(" + formvar + ",delegation," + Delegation + ")\n";
      tpl += "    ,extract(" + Delegation + ",format," + "'delegationxml'" + ")\n";
      tpl += "    ,checkMandate(" + Delegation + "," + formvar + ")\n";
      tpl += "    ,checkMandatorKey(" + Delegation + "," + ((Trustscheme) value).value + ")\n";
      tpl += "    ,validDelegation(" + Delegation + ")\n";
      //if (((ClaimTrustList) value).form != null) {
      //  tpl += fAttlistFormlevel(((ClaimTrustList) value).form, Delegation);
      //}
      tpl += fAttlistAttlevel(attributes, formvar);
      return tpl;
//      String TrustListEntry = createNewVariable("TrustListEntry");
//      String TrustMemClaim = ((Trustscheme) ((ClaimTrustList) value).trustListValue).value;
//      tpl += "    ,extract(" + formvar + "," + TrustMemClaim + ")\n";
//      tpl += "    ,lookup(" + TrustMemClaim + "," + TrustListEntry + ")\n";
//      if (((ClaimTrustList) value).form != null) {
//        tpl += fAttlistFormlevel(((ClaimTrustList) value).form, TrustListEntry);
//        for (AttributeValuePair attValuePair : ((ClaimTrustList) value).form.attributes) {
//          if (attValuePair.attributeNamePair.attributename.equals("fingerprint")) {
//            if (attValuePair.value instanceof Variable) {
//              tpl += "    ,verify_hash(" + formvar + "," + ((Variable) attValuePair.value).variable + ")\n";
//            }
//          }
//        }
//      }
//      tpl += fAttlistAttlevel(attributes, formvar);
//      return tpl;

    } else if (value instanceof Variable) {
      tpl += "    ,extract(" + formvar + "," + attribute + "," + ((Variable) value).variable + ")\n";
      tpl += fAttlistAttlevel(attributes, formvar);
      return tpl;

    } else if (value instanceof Value) {
      tpl += "    ,extract(" + formvar + "," + attribute + "," + ((Value) value).value + ")\n";
      tpl += fAttlistAttlevel(attributes, formvar);
      return tpl;

    } else if (value instanceof Int) {
      tpl += "    ,extract(" + formvar + "," + attribute + "," + ((Int) value).value + ")\n";
      tpl += fAttlistAttlevel(attributes, formvar);
      return tpl;

    } else if (value instanceof CompValue) {
      String newVariable = createNewVariable(attribute);
      tpl += "    ,extract(" + formvar + "," + attribute + "," + newVariable + "),"
          + newVariable + ((CompValue) value).comp + ((CompValue) value).value + "\n";
      tpl += fAttlistAttlevel(attributes, formvar);
      return tpl;

    } else if (value instanceof Form) {
      String subForm = createNewVariable(attribute);
      tpl += "    ,extract(" + formvar + "," + attribute + "," + subForm + ")\n";
      tpl += fAttlistFormlevel((Form) value, subForm);
      tpl += fAttlistAttlevel(attributes, formvar);
      return tpl;

    } else if (value instanceof ClaimTrustList && ((ClaimTrustList) value).trustListValue instanceof Blank) {
      tpl += fAttlistAttlevel(attributes, formvar);
      return tpl;

    } else if (value instanceof AddableField){
      tpl += fAttlistAttlevel(attributes, formvar);
      return tpl;
    }
    throw new RuntimeException("Attribute value pair was not expected. Attribute: " + attribute + " Value: " + value.getClass());
  }

  private static ArrayList<AttributeValuePair> singleton(AttributeValuePair e) {
    ArrayList<AttributeValuePair> l = new ArrayList<>();
    l.add(e);
    return l;
  }

  private Stack<AttributeValuePair> createStack(ArrayList<AttributeValuePair> attributes) {
    Stack<AttributeValuePair> atts = new Stack<>();
    for (int i = attributes.size() - 1; i > -1; i--) {
      atts.add(attributes.get(i));
    }
    return atts;
  }
}