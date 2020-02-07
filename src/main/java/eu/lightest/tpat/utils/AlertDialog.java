package eu.lightest.tpat.utils;

import javafx.scene.control.Alert;


public class AlertDialog {
  public enum Type {
    info,
    warning,
    error
  }

  private Alert mAlert;

  public AlertDialog(String windowTitel, String text, String context, Type type){
    switch (type) {
      case info:
        mAlert = new Alert(Alert.AlertType.INFORMATION);
        break;
      case warning:
        mAlert = new Alert(Alert.AlertType.WARNING);
        break;
      case error:
        mAlert = new Alert(Alert.AlertType.ERROR);
        break;
    }

    mAlert.setTitle(windowTitel);
    mAlert.setHeaderText(text);
    mAlert.setContentText(context);

    mAlert.showAndWait();
  }
}
