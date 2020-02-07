package eu.lightest.tpat.utils;

import eu.lightest.tpat.mvc.model.TrustPolicyModel;
import javafx.stage.Stage;

public class CustomStage extends Stage {
    private TrustPolicyModel.TplType mTplType;

    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public TrustPolicyModel.TplType getTPLtype() {
        return mTplType;
    }

    public void setStyle(TrustPolicyModel.TplType mEdit) {
        this.mTplType = mEdit;
    }
}
