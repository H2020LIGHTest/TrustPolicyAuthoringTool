package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates;

import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;

import java.io.Serializable;

public abstract class SubState implements State, Serializable {
  public abstract void checkSuccessor(State nextState) throws UserException;
}
