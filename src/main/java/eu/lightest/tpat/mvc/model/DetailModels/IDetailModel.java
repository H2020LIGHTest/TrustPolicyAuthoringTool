package eu.lightest.tpat.mvc.model.DetailModels;

import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.SubState;

public interface IDetailModel {
  SubState getSubState();
}
