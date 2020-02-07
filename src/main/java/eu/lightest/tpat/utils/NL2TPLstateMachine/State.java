package eu.lightest.tpat.utils.NL2TPLstateMachine;

public interface State {

  String translate();

  String getName();

  boolean fullySpecified();
}
