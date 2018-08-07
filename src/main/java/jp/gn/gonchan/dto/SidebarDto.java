package jp.gn.gonchan.dto;

public class SidebarDto {

    private int favoritArticleCount;

    public int getFavoritArticleCount() {
        return favoritArticleCount;
    }

    public void setFavoritArticleCount(int favoritArticleCount) {
        this.favoritArticleCount = favoritArticleCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SidebarDto [favoritArticleCount=");
        builder.append(favoritArticleCount);
        builder.append("]");
        return builder.toString();
    }

}
