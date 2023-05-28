package at.qe.backend.repositories;

import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.Greenhouse;

import java.util.List;
import java.util.Optional;

public interface GreenhouseRepository extends AbstractRepository<Greenhouse, Long> {

    Greenhouse findFirstByIdInClusterAndAccesspoint_Uuid(long idInCluster,long accesspoint_uuid);
    Optional<Greenhouse> findByUuid(long uuid);
    int countGreenhouseByAccesspoint(AccessPoint accesspoint);

    List<Greenhouse> findAllByOwner_Username(String name);

    boolean existsByIdInClusterAndAccesspoint_Uuid(long idInCluster, long accessPointUUID);
}
