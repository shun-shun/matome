package jp.gn.gonchan.resources;

import jp.gn.gonchan.dto.NavigationHeaderDto;
import jp.gn.gonchan.dto.SidebarDto;
import jp.gn.gonchan.dto.cokkie.CokkieInfo;
import jp.gn.gonchan.dto.display.AbstractDisplayDto;
import jp.gn.gonchan.logics.AbstractLogic;

import javax.inject.Inject;

public abstract class AbstractResources {

    @Inject
    AbstractLogic abstractLogic;

    public void setDisplayDto(AbstractDisplayDto displayDto, CokkieInfo cokkieInfo) {
        setNavigationHeaderDto(displayDto);
        setSidebarDto(displayDto, cokkieInfo);
    }

    private void setNavigationHeaderDto(AbstractDisplayDto displayDto) {
        NavigationHeaderDto headerDto = new NavigationHeaderDto();
        final int pgAllCount = abstractLogic.getPDAllCount();
        final int articleCount = abstractLogic.getTodayArticles();
        headerDto.setRegisteredBlogCount(pgAllCount);
        headerDto.setNewArrivalArticleCount(articleCount);
        displayDto.setNavDto(headerDto);
    }

    private void setSidebarDto(AbstractDisplayDto displayDto, CokkieInfo cokkieInfo) {
        SidebarDto sidebarDto = new SidebarDto();
        if (cokkieInfo != null && cokkieInfo.getCokkieArticleDto() != null) {
            final int favoritArticleCount = cokkieInfo.getCokkieArticleDto().size();
            sidebarDto.setFavoritArticleCount(favoritArticleCount);
        }
        displayDto.setSidebarDto(sidebarDto);
    }

}
