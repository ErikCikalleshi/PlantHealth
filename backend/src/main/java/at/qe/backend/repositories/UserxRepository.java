package at.qe.backend.repositories;

import java.util.List;

import at.qe.backend.models.Userx;
import at.qe.backend.models.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for managing {@link Userx} entities.
 *
 * This class is part of the skeleton project provided for students of the
 * course "Software Architecture" offered by Innsbruck University.
 */
public interface UserxRepository extends AbstractRepository<Userx, String> {

    Userx findFirstByUsername(String username);

    List<Userx> findByUsernameContaining(String username);

    Integer countUserxByRolesContaining(UserRole role);

    @Query("SELECT u FROM Userx u WHERE CONCAT(u.firstName, ' ', u.lastName) = :wholeName")
    List<Userx> findByWholeNameConcat(@Param("wholeName") String wholeName);

    @Query("SELECT u FROM Userx u WHERE :role MEMBER OF u.roles")
    List<Userx> findByRole(@Param("role") UserRole role);
}
