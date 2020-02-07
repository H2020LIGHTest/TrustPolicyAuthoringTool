package eu.lightest.tpat.utils;

import eu.lightest.tpat.mvc.model.MainModel;
import eu.lightest.tpat.mvc.model.TrustPolicyModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.*;

public class SaveManager {
  static public void saveTpl(String name, String code) throws IOException {
    File path = new File("myDocuments/" + name + ".tpl");
    path.getParentFile().mkdirs();
    try (FileOutputStream out = new FileOutputStream(path);) {
      out.write(code.getBytes());
    }
  }

  static public void saveModel(TrustPolicyModel model) throws IOException {
    File path = new File("saves/" + model.mName + ".ser");
    path.getParentFile().mkdirs();
    try (
        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
    ) {
      out.writeObject(model);
    }
  }

  static public void playSaveAnimation(Group target, Label saveLabel) {
    saveLabel.setVisible(true);
    int frames = 10;
    KeyFrame[] images = new KeyFrame[frames];
    for (int i = 1; i <= frames; i++) {
      ImageView img = new ImageView("/images/USTUTT/24x24/Ladeanimation" + i + ".png");
      img.setFitWidth(24);
      img.setFitHeight(24);
      images[i - 1] = new KeyFrame(Duration.seconds(i / 5.0), event -> target.getChildren().setAll(img));
    }
    Timeline timeline = new Timeline(images);
    timeline.setAutoReverse(true);
    timeline.setCycleCount(2);
    timeline.setOnFinished(event -> {
      target.getChildren().clear();
      saveLabel.setVisible(false);
    });
    timeline.play();
  }


  public static void loadSaves(MainModel mainModel) {
    File d = new File("saves");
    File[] files = d.listFiles((dir, name) -> name.endsWith("ser"));
    for (File f : files != null ? files : new File[0]) {
      try (
          FileInputStream fileIn = new FileInputStream(f.getAbsolutePath());
          ObjectInputStream in = new ObjectInputStream(fileIn);
      ) {
        TrustPolicyModel model = (TrustPolicyModel) in.readObject();
        mainModel.getTrustPolicyModels().add(model);
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  public static boolean trash(String mName) {
    File d = new File("saves");
    File[] files = d.listFiles((dir, name) -> name.equals(mName + ".ser"));
      if (files != null && files[0] != null) {
        return files[0].delete();
      }
      return false;
  }
}
