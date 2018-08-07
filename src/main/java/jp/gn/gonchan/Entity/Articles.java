package jp.gn.gonchan.Entity;

import java.sql.Timestamp;
import java.util.Date;

public class Articles {

    private int destinationId;

    private String articleId;

    private String title;

    private String author;

    private String link;

    private String uri;

    private String contributors;

    private String enclosures;

    private String links;

    private String contentType;

    private String contentValue;

    private String descriptionType;

    private String descriptionValue;

    private Date publishedDate;

    private Date updatedDate;

    private Timestamp insertDate;

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getContributors() {
        return contributors;
    }

    public void setContributors(String contributors) {
        this.contributors = contributors;
    }

    public String getEnclosures() {
        return enclosures;
    }

    public void setEnclosures(String enclosures) {
        this.enclosures = enclosures;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentValue() {
        return contentValue;
    }

    public void setContentValue(String contentValue) {
        this.contentValue = contentValue;
    }

    public String getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(String descriptionType) {
        this.descriptionType = descriptionType;
    }

    public String getDescriptionValue() {
        return descriptionValue;
    }

    public void setDescriptionValue(String descriptionValue) {
        this.descriptionValue = descriptionValue;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Timestamp getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Articles [destinationId=");
        builder.append(destinationId);
        builder.append(", articleId=");
        builder.append(articleId);
        builder.append(", title=");
        builder.append(title);
        builder.append(", author=");
        builder.append(author);
        builder.append(", link=");
        builder.append(link);
        builder.append(", uri=");
        builder.append(uri);
        builder.append(", contributors=");
        builder.append(contributors);
        builder.append(", enclosures=");
        builder.append(enclosures);
        builder.append(", links=");
        builder.append(links);
        builder.append(", contentType=");
        builder.append(contentType);
        builder.append(", contentValue=");
        builder.append(contentValue);
        builder.append(", descriptionType=");
        builder.append(descriptionType);
        builder.append(", descriptionValue=");
        builder.append(descriptionValue);
        builder.append(", publishedDate=");
        builder.append(publishedDate);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", insertDate=");
        builder.append(insertDate);
        builder.append("]");
        return builder.toString();
    }
}
