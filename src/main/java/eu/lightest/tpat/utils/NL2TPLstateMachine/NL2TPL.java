package eu.lightest.tpat.utils.NL2TPLstateMachine;

import eu.lightest.gtpl.tools.NL2TPLTools;
import eu.lightest.tpat.mvc.view.MainView;
import eu.lightest.tpat.utils.DragDrop.SnapBlockModel;
import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.gtpl.parser.nlException;
import org.apache.log4j.Logger;

public class NL2TPL {

  public SnapBlockModel mStart;

  private static Logger logger = Logger.getLogger(NL2TPL.class);

  public NL2TPL(SnapBlockModel block) {
    this.mStart = block;
  }

  public void check() throws UserException {
    SnapBlockModel actual = mStart;

    while (actual.mNext != null){

      logger.debug(actual.getDetailModel().getSubState());
      actual.getDetailModel().getSubState().checkSuccessor(actual.mNext.getDetailModel().getSubState());
      actual = actual.mNext;
    }
    //final check (EOF)
    actual.getDetailModel().getSubState().checkSuccessor(null);
  }

  public UserException nlExceptionToUserException (nlException e) {
    return (new UserException(true) {
      @Override
      public String getMessage() {
    return e.getMessage();
  }

      @Override
      public String getLocalizedMessage() {
        return e.getLocalizedMessage();
      }

      @Override
      public String toString() {
    return e.toString();
  }
      });
  }

  public String translate(String domain) throws UserException {
    SnapBlockModel actual = mStart;
    String clearText = "if input is format of " + domain + " ";

    do {
      clearText += actual.getState().translate();
      actual = actual.mNext;
    } while (actual != null);

    logger.debug("ClearText: " + clearText);

    try {
      return NL2TPLTools.translateToTPLinternally(clearText);
    }
    catch(nlException e) {
      throw nlExceptionToUserException(e);
    }
  }
}
