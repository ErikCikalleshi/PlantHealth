package at.qe.backend.repositories;

import at.qe.backend.models.AccessPoint;

import java.util.Optional;

public interface AccessPointRepository extends AbstractRepository<AccessPoint, String> {

    Optional<AccessPoint> findFirstByUuid(long uuid);
}
