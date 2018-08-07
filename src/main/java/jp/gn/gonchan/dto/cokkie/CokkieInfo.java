package jp.gn.gonchan.dto.cokkie;

import java.io.Serializable;
import java.util.List;

public class CokkieInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CokkieArticleDto> cokkieArticles;

    private boolean targetSelf;

    public CokkieInfo() {}

    public CokkieInfo(List<CokkieArticleDto> cokkieArticles) {
        this.cokkieArticles = cokkieArticles;
    }

    public CokkieInfo(boolean targetSelf) {
        this.targetSelf = targetSelf;
    }

    public CokkieInfo(List<CokkieArticleDto> cokkieArticles, boolean targetSelf) {
        this.cokkieArticles = cokkieArticles;
        this.targetSelf = targetSelf;
    }

    public List<CokkieArticleDto> getCokkieArticleDto() {
        return cokkieArticles;
    }

    public void setCokkieArticleDto(List<CokkieArticleDto> cokkieArticles) {
        this.cokkieArticles = cokkieArticles;
    }

    public boolean isTargetSelf() {
        return targetSelf;
    }

    public void setTargetSelf(boolean targetSelf) {
        this.targetSelf = targetSelf;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CokkieInfo [cokkieArticles=");
        builder.append(cokkieArticles);
        builder.append(", targetSelf=");
        builder.append(targetSelf);
        builder.append("]");
        return builder.toString();
    }
}
