package jp.gn.gonchan.logics;

import jp.gn.gonchan.dao.MProvidingDestinationDao;
import jp.gn.gonchan.dao.TArticleDao;
import jp.gn.gonchan.dto.MatomeDto;
import jp.gn.gonchan.dto.cokkie.CokkieArticleDto;
import jp.gn.gonchan.dto.display.FavoriteDisplayDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

public class FavoriteLogic {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteLogic.class);

    @Inject
    MProvidingDestinationDao providingDestinationDao;

    @Inject
    TArticleDao articleDao;

    public FavoriteDisplayDto getTopDto(List<CokkieArticleDto> cokkieArticles) {
        List<MatomeDto> matomeDtos = new ArrayList<>();
        cokkieArticles.forEach(article -> {
            Optional<MatomeDto> optional =
                    articleDao.selectArticleById(article.getDestinationId(), article.getArticleId());
            if (optional.isPresent()) {
                matomeDtos.add(optional.get());
            }
        });
        FavoriteDisplayDto displayDto = new FavoriteDisplayDto();
        displayDto.setFavoriteMatomeDtos(matomeDtos);
        return displayDto;
    }
}
