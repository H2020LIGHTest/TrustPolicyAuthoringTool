package eu.lightest.tpat.utils;

import javafx.scene.control.TreeItem;

import java.io.Serializable;

public class TreeItemModel implements Serializable {
  public String mName;
  public String mPath;
  public String mType;

  public TreeItemModel(String name, String path, String type) {
    this.mName = name;
    this.mPath = path;
    this.mType = type;
  }

  @Override
  public String toString() {
    return mName;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof TreeItemModel) {
      TreeItemModel other = (TreeItemModel) obj;

      return other.mName.equals(this.mName) &&
          other.mPath.equals(this.mPath) &&
          other.mType.equals(this.mType);
    }
    return false;
  }
}
