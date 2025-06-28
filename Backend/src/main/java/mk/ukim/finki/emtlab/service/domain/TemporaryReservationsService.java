package mk.ukim.finki.emtlab.service.domain;

import mk.ukim.finki.emtlab.model.domain.Accommodation;
import mk.ukim.finki.emtlab.model.domain.TemporaryReservations;

import java.util.List;
import java.util.Optional;

public interface TemporaryReservationsService {

    List<Accommodation> listAllAccommodationsInTemporaryReservations(Long temporaryReservationsId);

    Optional<TemporaryReservations> getActiveTemporaryReservations(String username);

    Optional<TemporaryReservations> addAccommodationToTemporaryReservations(String username, Long accommodationId);

    Optional<TemporaryReservations> addAllFreeAccommodationsToTemporaryReservations(String username);

    Optional<TemporaryReservations> confirmTemporaryReservations(String username);

}
