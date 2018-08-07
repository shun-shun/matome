package jp.gn.gonchan.form;


import jp.gn.gonchan.constant.Constant;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;

public class RegisterSettingsForm {

    @FormParam("targetSelf")
    @NotEmpty(message = Constant.NOT_EMPTY_TARGET_SELF_MESSAGE)
    @Pattern(regexp = "^true$|^false$", message = Constant.NOT_MATCH_PATTER_TARGET_SELF_MESSAGE)
    private String targetSelf;

    public String getTargetSelf() {
        return targetSelf;
    }

    public void setTargetSelf(String targetSelf) {
        this.targetSelf = targetSelf;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RegisterSettingsForm [targetSelf=");
        builder.append(targetSelf);
        builder.append("]");
        return builder.toString();
    }

}
