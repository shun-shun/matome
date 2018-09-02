package jp.gn.gonchan.dao;

import jp.gn.gonchan.Entity.ProvidingDestination;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import java.sql.Timestamp;
import java.util.List;

@RegisterMapperFactory(BeanMapperFactory.class)
public interface MProvidingDestinationDao {

    @SqlQuery("SELECT * FROM M_PROVIDING_DESTINATION")
    public List<ProvidingDestination> selectAllDistination();

    @SqlQuery("SELECT * FROM M_PROVIDING_DESTINATION ORDER BY destinationId ASC LIMIT 10")
    public List<ProvidingDestination> selectDistinationByLimit();

    @SqlQuery("SELECT COUNT(*) FROM M_PROVIDING_DESTINATION")
    public int selectAllCount();


    @SqlUpdate("UPDATE M_PROVIDING_DESTINATION SET "
            + "lastModified = :lastModified , etag = :etag WHERE destinationId = :destinationId ")
    public void updateModified(@Bind("lastModified") Timestamp lastModified, @Bind("etag") String etag,
            @Bind("destinationId") int destinationId);

}
