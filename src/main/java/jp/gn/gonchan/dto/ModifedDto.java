package jp.gn.gonchan.dto;

public class ModifedDto {

    private String lastModifed;

    private String eTag;

    public String getLastModifed() {
        return lastModifed;
    }

    public void setLastModifed(String lastModifed) {
        this.lastModifed = lastModifed;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ModifedDto [lastModifed=");
        builder.append(lastModifed);
        builder.append(", eTag=");
        builder.append(eTag);
        builder.append("]");
        return builder.toString();
    }
}
