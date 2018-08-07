package jp.gn.gonchan.logics;

import jp.gn.gonchan.dao.MProvidingDestinationDao;
import jp.gn.gonchan.dao.TArticleDao;
import jp.gn.gonchan.dto.MatomeDto;
import jp.gn.gonchan.dto.display.TopDisplayDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

public class TopLogic {

    private static final Logger logger = LoggerFactory.getLogger(TopLogic.class);

    @Inject
    MProvidingDestinationDao providingDestinationDao;

    @Inject
    TArticleDao articleDao;

    public TopDisplayDto getTopDto() {
        List<MatomeDto> articles = articleDao.selectListRandamtest();
        TopDisplayDto topDto = new TopDisplayDto();
        topDto.setSingleMatomeDtos(articles);
        return topDto;
    }
}
