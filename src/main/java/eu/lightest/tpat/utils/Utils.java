package eu.lightest.tpat.utils;

import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.DivState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.MinusState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.MultipState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.PlusState;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public class Utils {

  public static boolean containedInView(Node container, Node target) {
    Node act = target;
    if (act == container)
      return true;
    while (act.getParent() != null) {
      act = act.getParent();
      if (act == container)
        return true;
    }
    return false;
  }


  public static <T> TreeItem<T> getTreeViewItem(TreeItem<T> item, T value)
  {
    if (item != null && item.getValue().equals(value))
      return  item;

    for (TreeItem<T> child : item.getChildren()){
      TreeItem<T> s = getTreeViewItem(child, value);
      if(s!=null)
        return s;

    }
    return null;
  }

  public static boolean isArith(State nextState) {
    return nextState instanceof DivState ||
        nextState instanceof MultipState ||
        nextState instanceof PlusState ||
        nextState instanceof MinusState;
  }
}
