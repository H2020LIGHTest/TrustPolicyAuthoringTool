package eu.lightest.tpat.utils.Parser;

import eu.lightest.tpat.utils.TreeItemModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.w3c.dom.Node;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public abstract class Parser {

  public static Map<String, Parser> sDomainList = new HashMap<>();

  public static List<String> sTrustschemes = asList("Australian Universities", "Australian Government", "eIDAS",
      "European Union Companies" ,"PSA Internal", "Bologna Process Universities", "US Universities", "Audi Certified Vehicles",
      "Continental Automated Driving System", "Vienna Car Parking Service", "AZ Trust Scheme", "CPC Trust Scheme",
      "Russian Trust Scheme", "United Nations", "UNHCR Dafi", "Turkish Trust Scheme", "Nato", "Mercosur", "ASEAN", "PRC Trust Scheme",
      "pumpkin_Oil_Federation");

  public static void discover() {
    File dir = new File("Transactions/Formats_DomainTemplates");

    File[] files = dir.listFiles((dir1, filename) -> filename.endsWith(".xml"));
    if (files != null) {
      for (File f : files) {
        Parser m = new GenericParser(f).parse(); // TODO: Add parser plugin functionality
        sDomainList.put(m.getName(), m);
      }
    }
  }


  public abstract Parser parse();

  abstract TreeItem<TreeItemModel> parseChild(Node node);

  public static void buildUiTree(String parserName, TreeView<TreeItemModel> treeView) {
    Parser parser = sDomainList.get(parserName);
    parser.buildConcreteUITree(treeView);
  }

  abstract void buildConcreteUITree(TreeView<TreeItemModel> treeView);

  abstract String getName();

  public abstract String getSpec();
}
