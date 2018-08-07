package jp.gn.gonchan.logics;

import jp.gn.gonchan.Entity.ProvidingDestination;
import jp.gn.gonchan.dao.MProvidingDestinationDao;
import jp.gn.gonchan.dto.BlogFourListDto;
import jp.gn.gonchan.dto.display.BlogIndexDisplayDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BlogIndexLogic {

    private static final Logger logger = LoggerFactory.getLogger(BlogIndexLogic.class);

    private static final int BLOG_INDEX_COLUMN_NUMBER = 4;

    @Inject
    MProvidingDestinationDao providingDestinationDao;

    public BlogIndexDisplayDto getBlogIndexDisplayDto() {
        List<ProvidingDestination> destinations = providingDestinationDao.selectAllDistination();
        int remainingNumber = destinations.size();
        int index = 0;

        List<BlogFourListDto> blogFourListDtos = new ArrayList<>();
        while (true) {
            List<ProvidingDestination> destination = null;
            if (remainingNumber >= BLOG_INDEX_COLUMN_NUMBER) {
                destination = destinations.subList(index, index += BLOG_INDEX_COLUMN_NUMBER);
                remainingNumber -= BLOG_INDEX_COLUMN_NUMBER;
            } else {
                destination = destinations.subList(index, index += remainingNumber);
            }

            BlogFourListDto fourListDto = new BlogFourListDto();
            fourListDto.setFourDestinationList(destination);
            blogFourListDtos.add(fourListDto);
            if (remainingNumber < BLOG_INDEX_COLUMN_NUMBER) {
                break;
            }
        }
        BlogIndexDisplayDto displayDto = new BlogIndexDisplayDto();
        displayDto.setBlogFourListDtos(blogFourListDtos);
        return displayDto;
    }
}
