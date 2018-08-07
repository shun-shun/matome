package jp.gn.gonchan.dto.display;

import jp.gn.gonchan.dto.BlogFourListDto;

import java.util.List;

public class BlogIndexDisplayDto extends AbstractDisplayDto {

    private List<BlogFourListDto> blogFourListDtos;

    public List<BlogFourListDto> getBlogFourListDtos() {
        return blogFourListDtos;
    }

    public void setBlogFourListDtos(List<BlogFourListDto> blogFourListDtos) {
        this.blogFourListDtos = blogFourListDtos;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BlogIndexDisplayDto [blogFourListDtos=");
        builder.append(blogFourListDtos);
        builder.append("]");
        return builder.toString();
    }

}
