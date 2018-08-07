package jp.gn.gonchan.dto;

import jp.gn.gonchan.Entity.ProvidingDestination;

import java.util.List;

public class BlogFourListDto {

    private List<ProvidingDestination> fourDestinationList;

    public List<ProvidingDestination> getFourDestinationList() {
        return fourDestinationList;
    }

    public void setFourDestinationList(List<ProvidingDestination> fourDestinationList) {
        this.fourDestinationList = fourDestinationList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BlogFourListDto [fourDestinationList=");
        builder.append(fourDestinationList);
        builder.append("]");
        return builder.toString();
    }

}
