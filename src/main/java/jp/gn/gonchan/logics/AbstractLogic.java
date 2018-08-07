package jp.gn.gonchan.logics;

import jp.gn.gonchan.dao.MProvidingDestinationDao;
import jp.gn.gonchan.dao.TArticleDao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.inject.Inject;

public class AbstractLogic {

    @Inject
    MProvidingDestinationDao providingDestinationDao;

    @Inject
    TArticleDao articleDao;

    public int getPDAllCount() {
        final int resultCount = providingDestinationDao.selectAllCount();
        return resultCount;
    }

    public int getTodayArticles() {

        LocalDateTime start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = start.plusHours(23).plusMinutes(59).plusSeconds(59);

        final Timestamp startTimeStamp = Timestamp.valueOf(start);
        final Timestamp endTimeStamp = Timestamp.valueOf(end);
        final int resultCount = articleDao.selectTodayArticles(startTimeStamp, endTimeStamp);
        return resultCount;
    }

}
