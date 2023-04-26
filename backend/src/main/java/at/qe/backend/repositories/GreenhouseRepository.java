package at.qe.backend.repositories;

import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.Greenhouse;

public interface GreenhouseRepository extends AbstractRepository<Greenhouse, String> {

    Greenhouse findFirstByIdInClusterAndAccesspoint_Uuid(long idInCluster,long accesspoint_uuid);

    int countGreenhouseByAccesspoint(AccessPoint accesspoint);
}
