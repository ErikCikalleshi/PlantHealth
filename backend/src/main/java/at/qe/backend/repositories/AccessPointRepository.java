package at.qe.backend.repositories;

import at.qe.backend.models.AccessPoint;
public interface AccessPointRepository extends AbstractRepository<AccessPoint, String> {

    AccessPoint findFirstByUuid(long uuid);
}
