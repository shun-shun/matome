package jp.gn.gonchan.dto;

public class MatomeDto {

    private int destinationId;

    private int articleId;

    private String title;

    private String link;

    private String diestinationName;

    private String publishedDate;

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDiestinationName() {
        return diestinationName;
    }

    public void setDiestinationName(String diestinationName) {
        this.diestinationName = diestinationName;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MatomeDto [destinationId=");
        builder.append(destinationId);
        builder.append(", articleId=");
        builder.append(articleId);
        builder.append(", title=");
        builder.append(title);
        builder.append(", link=");
        builder.append(link);
        builder.append(", diestinationName=");
        builder.append(diestinationName);
        builder.append(", publishedDate=");
        builder.append(publishedDate);
        builder.append("]");
        return builder.toString();
    }

}
