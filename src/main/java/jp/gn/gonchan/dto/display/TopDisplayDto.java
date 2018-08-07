package jp.gn.gonchan.dto.display;

import jp.gn.gonchan.dto.MatomeDto;

import java.util.List;

public class TopDisplayDto extends AbstractDisplayDto {

    private List<MatomeDto> singleMatomeDtos;

    public List<MatomeDto> getSingleMatomeDtos() {
        return singleMatomeDtos;
    }

    public void setSingleMatomeDtos(List<MatomeDto> singleMatomeDtos) {
        this.singleMatomeDtos = singleMatomeDtos;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TopDisplayDto [singleMatomeDtos=");
        builder.append(singleMatomeDtos);
        builder.append("]");
        return builder.toString();
    }
}
