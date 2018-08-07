package jp.gn.gonchan.Entity;

public class ProvidingDestination {

    private int DestinationId;

    private int CategoryId;

    private String DiestinationName;

    private String url;

    private String rssUrl;

    private String lastModified;

    private String eTag;

    public int getDestinationId() {
        return DestinationId;
    }

    public void setDestinationId(int destinationId) {
        DestinationId = destinationId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getDiestinationName() {
        return DiestinationName;
    }

    public void setDiestinationName(String diestinationName) {
        DiestinationName = diestinationName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
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
        builder.append("ProvidingDestination [DestinationId=");
        builder.append(DestinationId);
        builder.append(", CategoryId=");
        builder.append(CategoryId);
        builder.append(", DiestinationName=");
        builder.append(DiestinationName);
        builder.append(", url=");
        builder.append(url);
        builder.append(", rssUrl=");
        builder.append(rssUrl);
        builder.append(", lastModified=");
        builder.append(lastModified);
        builder.append(", eTag=");
        builder.append(eTag);
        builder.append("]");
        return builder.toString();
    }
}
