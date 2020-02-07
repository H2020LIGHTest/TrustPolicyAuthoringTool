package eu.lightest.tpat.mvc.controller.DetailControllers;

import eu.lightest.tpat.mvc.controller.IBaseController;
import eu.lightest.tpat.utils.DragDrop.Block;

public interface IDetailController {

   void syncLabel(String name);

   void restoreDetailView();

    void init(Block assocBlock, IBaseController baseController);
}
