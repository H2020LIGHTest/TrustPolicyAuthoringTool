package eu.lightest.tpat.utils.Parser;

import eu.lightest.tpat.utils.TreeItemModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import java.io.File;
import java.io.IOException;

public class GenericParser extends Parser {
  private final File mFile;


  private String mName;

  private String mSpec;

  private TreeItem<TreeItemModel> mRoot;

  public GenericParser(File f) {
    mFile = f;
  }

  public Parser parse() {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(mFile);
      doc.getDocumentElement().normalize();
      doc.getElementsByTagName("Transaction").item(0);
      Element root = doc.getDocumentElement();
      mName = root.getAttributes().getNamedItem("format").getNodeValue();
      mSpec = root.getAttributes().getNamedItem("specification").getNodeValue();
      mRoot = parseChild(root);

      mRoot.getChildren().add(new TreeItem<>(new TreeItemModel("Certificate", ("Certificate").toLowerCase(), "cert")));

    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
    return this;
  }

  //TODO make abstract parser interface!
  TreeItem<TreeItemModel> parseChild(Node node) {
    TreeItem<TreeItemModel> item = null;


    if (!node.getNodeName().equals("#text")) {
      String path = "";
      for (Node i = node; i.getParentNode().getParentNode() != null; i = i.getParentNode())
        path = i.getNodeName().toLowerCase() + ((!path.equals("")) ? "." + path : "");

      String type;
      if (node.getAttributes() != null && node.getAttributes().getNamedItem("type") != null) {
        type = node.getAttributes().getNamedItem("type").getNodeValue();
      } else {
        type = "";
      }

      item = new TreeItem<>(new TreeItemModel(node.getNodeName().replace("_", " "), path, type));
    }

    NodeList childrenList = node.getChildNodes();
    for (int i = 0; i < childrenList.getLength(); i++) {
      TreeItem<TreeItemModel> child = parseChild(childrenList.item(i));
      if (item != null && child != null) {
        item.getChildren().add(child);
      } else if (child != null) {
        item = child;
      }
    }

    return item;
  }

  @Override
  void buildConcreteUITree(TreeView<TreeItemModel> treeView) {
    treeView.setRoot(mRoot);

  }

  public String getName() {
    return mName;
  }

  public String getSpec() {
    return mSpec;
  }
}