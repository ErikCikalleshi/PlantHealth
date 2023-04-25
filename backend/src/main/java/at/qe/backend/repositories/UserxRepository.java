package at.qe.backend.repositories;

import java.util.List;
import java.util.Optional;

import at.qe.backend.models.Userx;
import at.qe.backend.models.UserRole;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing {@link Userx} entities.
 *
 * This class is part of the skeleton project provided for students of the
 * course "Software Architecture" offered by Innsbruck University.
 */
@Repository
public interface UserxRepository extends JpaRepository<Userx, Long> {

    Userx findFirstByUsername(String username);
    Optional<Userx> findByUsername(String username);

    List<Userx> findByUsernameContaining(String username);

    Integer countUserxByRolesContaining(UserRole role);

    @Query("SELECT u FROM Userx u WHERE CONCAT(u.firstName, ' ', u.lastName) = :wholeName")
    List<Userx> findByWholeNameConcat(@Param("wholeName") String wholeName);

    @Query("SELECT u FROM Userx u WHERE :role MEMBER OF u.roles")
    List<Userx> findByRole(@Param("role") UserRole role);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Userx findByEmail(String email);
}
