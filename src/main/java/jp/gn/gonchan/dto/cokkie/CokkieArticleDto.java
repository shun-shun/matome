package jp.gn.gonchan.dto.cokkie;

import java.io.Serializable;

public class CokkieArticleDto implements Serializable {

    private static final long serialVersionUID = 0;

    private int destinationId;

    private int articleId;

    public CokkieArticleDto() {}

    public CokkieArticleDto(int destinationId, int articleId) {
        this.articleId = articleId;
        this.destinationId = destinationId;
    }

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CokkieArticleDto [destinationId=");
        builder.append(destinationId);
        builder.append(", articleId=");
        builder.append(articleId);
        builder.append("]");
        return builder.toString();
    }
}
