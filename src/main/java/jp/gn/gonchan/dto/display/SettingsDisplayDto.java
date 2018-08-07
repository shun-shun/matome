package jp.gn.gonchan.dto.display;

public class SettingsDisplayDto extends AbstractDisplayDto {

    private boolean targetSelf;

    public boolean isTargetSelf() {
        return targetSelf;
    }

    public void setTargetSelf(boolean targetSelf) {
        this.targetSelf = targetSelf;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SettingsDisplayDto [targetSelf=");
        builder.append(targetSelf);
        builder.append("]");
        return builder.toString();
    }

}
