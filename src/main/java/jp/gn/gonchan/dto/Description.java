package jp.gn.gonchan.dto;

public class Description {
    private String type;

    private String values;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Description [type=");
        builder.append(type);
        builder.append(", values=");
        builder.append(values);
        builder.append("]");
        return builder.toString();
    }

}
