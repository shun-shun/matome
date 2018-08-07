package jp.gn.gonchan.dto.display;

import jp.gn.gonchan.dto.MatomeDto;

import java.util.List;

public class NewArrivalsDisplayDto extends AbstractDisplayDto {

    private List<MatomeDto> newArrivalsDto;

    private List<Integer> pageList;

    private boolean targetSelf;

    public List<MatomeDto> getNewArrivalsDto() {
        return newArrivalsDto;
    }

    public void setNewArrivalsDto(List<MatomeDto> newArrivalsDto) {
        this.newArrivalsDto = newArrivalsDto;
    }

    public List<Integer> getPageList() {
        return pageList;
    }

    public void setPageList(List<Integer> pageList) {
        this.pageList = pageList;
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
        builder.append("NewArrivalsDisplayDto [newArrivalsDto=");
        builder.append(newArrivalsDto);
        builder.append(", pageList=");
        builder.append(pageList);
        builder.append(", targetSelf=");
        builder.append(targetSelf);
        builder.append("]");
        return builder.toString();
    }

}
