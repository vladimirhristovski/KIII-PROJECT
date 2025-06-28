package mk.ukim.finki.emtlab.service.application;

import mk.ukim.finki.emtlab.dto.DisplayAccommodationDto;
import mk.ukim.finki.emtlab.dto.TemporaryReservationsDto;

import java.util.List;
import java.util.Optional;

public interface TemporaryReservationsApplicationService {

    List<DisplayAccommodationDto> listAllAccommodationsInTemporaryReservations(Long temporaryReservationsId);

    Optional<TemporaryReservationsDto> getActiveTemporaryReservations(String username);

    Optional<TemporaryReservationsDto> addAccommodationToTemporaryReservations(String username, Long accommodationId);

    Optional<TemporaryReservationsDto> addAllFreeAccommodationsToTemporaryReservations(String username);

    Optional<TemporaryReservationsDto> confirmTemporaryReservations(String username);

}
