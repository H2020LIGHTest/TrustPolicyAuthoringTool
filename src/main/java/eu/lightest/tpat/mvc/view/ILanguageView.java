package eu.lightest.tpat.mvc.view;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public interface ILanguageView {
  Pane getDetailPane();

  @FXML
  void undo();
  @FXML
  void redo();
}
