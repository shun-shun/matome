package jp.gn.gonchan.form;


import jp.gn.gonchan.constant.Constant;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;

public class RegisterArticleForm {

    @FormParam("articleId")
    @NotEmpty(message = Constant.NOT_EMPTY_ARTICLE_ID_MESSAGE)
    @Pattern(regexp = "^[0-9]*$", message = Constant.NOT_MATCH_PATTER_ARTICLE_ID_MESSAGE)
    private String articleId;

    @FormParam("destinationId")
    @NotEmpty(message = Constant.NOT_EMPTY_DESTINATION_ID_MESSAGE)
    @Pattern(regexp = "^[0-9]*$", message = Constant.NOT_MATCH_PATTER_DESTINATION_ID_MESSAGE)
    private String destinationId;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LoginForm [articleId=");
        builder.append(articleId);
        builder.append(", destinationId=");
        builder.append(destinationId);
        builder.append("]");
        return builder.toString();
    }

}
