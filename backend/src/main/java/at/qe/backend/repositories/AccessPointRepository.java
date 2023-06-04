package at.qe.backend.repositories;

import at.qe.backend.models.AccessPoint;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccessPointRepository extends AbstractRepository<AccessPoint, String> {

    Optional<AccessPoint> findFirstByUuid(long uuid);

    @Query("SELECT ap FROM AccessPoint ap JOIN Greenhouse g ON ap.uuid = g.accesspoint.uuid WHERE g.uuid=?1")
    AccessPoint findFirstByGreenhouseUuid(long uuid);
}
