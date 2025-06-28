package mk.ukim.finki.emtlab.dto;

import mk.ukim.finki.emtlab.model.domain.TemporaryReservations;
import mk.ukim.finki.emtlab.model.enumerations.TemporaryReservationsStatus;

import java.time.LocalDateTime;
import java.util.List;

public record TemporaryReservationsDto(Long id, LocalDateTime dateCreated, DisplayUserDto user,
                                       List<DisplayAccommodationDto> accommodations,
                                       TemporaryReservationsStatus status) {

    public static TemporaryReservationsDto from(TemporaryReservations temporaryReservations) {
        return new TemporaryReservationsDto(
                temporaryReservations.getId(),
                temporaryReservations.getDateCreated(),
                DisplayUserDto.from(temporaryReservations.getUser()),
                DisplayAccommodationDto.from(temporaryReservations.getAccommodations()),
                temporaryReservations.getStatus()
        );
    }

}
