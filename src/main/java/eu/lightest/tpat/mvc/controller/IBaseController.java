package eu.lightest.tpat.mvc.controller;

import eu.lightest.tpat.mvc.view.ILanguageView;
import eu.lightest.tpat.utils.Exceptions.UserException;

public interface IBaseController {
  void closeDetailView();//used the other way around than IDetailController

  void handleUserException(UserException ex);

  void refreshTPLandSave(boolean forHistory);

  ILanguageView getBaseView();
}
