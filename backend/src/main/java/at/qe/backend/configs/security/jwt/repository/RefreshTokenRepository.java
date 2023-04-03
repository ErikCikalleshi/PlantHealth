package at.qe.backend.configs.security.jwt.repository;
import java.util.Optional;

import at.qe.backend.configs.security.jwt.models.RefreshToken;
import at.qe.backend.models.Userx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(Userx user);
}
