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
        setNavigationHeaderDto(displayDto, cokkieInfo);
        setSidebarDto(displayDto);
    }

    private void setSidebarDto(AbstractDisplayDto displayDto) {
        SidebarDto sidebarDto = new SidebarDto();
        final int pgAllCount = abstractLogic.getPDAllCount();
        final int articleCount = abstractLogic.getTodayArticles();
        sidebarDto.setRegisteredBlogCount(pgAllCount);
        sidebarDto.setNewArrivalArticleCount(articleCount);
        displayDto.setSidebarDto(sidebarDto);
    }

    private void setNavigationHeaderDto(AbstractDisplayDto displayDto, CokkieInfo cokkieInfo) {
        NavigationHeaderDto headerDto = new NavigationHeaderDto();
        if (cokkieInfo != null && cokkieInfo.getCokkieArticleDto() != null) {
            final int favoritArticleCount = cokkieInfo.getCokkieArticleDto().size();
            headerDto.setFavoritArticleCount(favoritArticleCount);
        }
        displayDto.setNavDto(headerDto);
    }

}
