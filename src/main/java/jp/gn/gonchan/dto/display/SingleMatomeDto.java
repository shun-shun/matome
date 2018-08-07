package jp.gn.gonchan.dto.display;

import jp.gn.gonchan.dto.MatomeDto;

import java.util.List;

public class SingleMatomeDto extends AbstractDisplayDto {

    private List<MatomeDto> matomeDtos;

    public List<MatomeDto> getMatomeDtos() {
        return matomeDtos;
    }

    public void setMatomeDtos(List<MatomeDto> matomeDtos) {
        this.matomeDtos = matomeDtos;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SingleMatomeDto [matomeDtos=");
        builder.append(matomeDtos);
        builder.append("]");
        return builder.toString();
    }

}
