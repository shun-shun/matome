package jp.gn.gonchan.dto;

public class NavigationHeaderDto {

    private int newArrivalArticleCount;

    private int registeredBlogCount;

    public int getNewArrivalArticleCount() {
        return newArrivalArticleCount;
    }

    public void setNewArrivalArticleCount(int newArrivalArticleCount) {
        this.newArrivalArticleCount = newArrivalArticleCount;
    }

    public int getRegisteredBlogCount() {
        return registeredBlogCount;
    }

    public void setRegisteredBlogCount(int registeredBlogCount) {
        this.registeredBlogCount = registeredBlogCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NavigationHeaderDto [newArrivalArticleCount=");
        builder.append(newArrivalArticleCount);
        builder.append(", registeredBlogCount=");
        builder.append(registeredBlogCount);
        builder.append("]");
        return builder.toString();
    }

}
