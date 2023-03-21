package at.qe.backend.repositories;

import at.qe.backend.models.Greenhouse;

public interface GreenhouseRepository extends AbstractRepository<Greenhouse, String> {

    Greenhouse findFirstByUuid(int uuid);
}