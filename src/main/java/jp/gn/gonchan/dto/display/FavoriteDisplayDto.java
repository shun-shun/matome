package jp.gn.gonchan.dto.display;

import jp.gn.gonchan.dto.MatomeDto;

import java.util.List;

public class FavoriteDisplayDto extends AbstractDisplayDto {

    private List<MatomeDto> favoriteMatomeDtos;

    private boolean targetSelf;

    public List<MatomeDto> getFavoriteMatomeDtos() {
        return favoriteMatomeDtos;
    }

    public void setFavoriteMatomeDtos(List<MatomeDto> favoriteMatomeDtos) {
        this.favoriteMatomeDtos = favoriteMatomeDtos;
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
        builder.append("FavoriteDisplayDto [favoriteMatomeDtos=");
        builder.append(favoriteMatomeDtos);
        builder.append(", targetSelf=");
        builder.append(targetSelf);
        builder.append("]");
        return builder.toString();
    }
}
