package mk.ukim.finki.emtlab.repository;

import mk.ukim.finki.emtlab.model.domain.TemporaryReservations;
import mk.ukim.finki.emtlab.model.domain.User;
import mk.ukim.finki.emtlab.model.enumerations.TemporaryReservationsStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemporaryReservationsRepository extends JpaRepository<TemporaryReservations, Long> {
    Optional<TemporaryReservations> findByUserAndStatus(User user, TemporaryReservationsStatus status);
}
