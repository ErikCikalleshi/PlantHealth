package at.qe.backend.repositories;

import at.qe.backend.models.Greenhouse;

import java.util.Optional;

public interface GreenhouseRepository extends AbstractRepository<Greenhouse, Long> {

    Greenhouse findFirstByIdInClusterAndAccesspoint_Uuid(long idInCluster,long accesspoint_uuid);
    Optional<Greenhouse> findByUuid(long uuid);
}
