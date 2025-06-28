package mk.ukim.finki.emtlab.service.application.impl;

import mk.ukim.finki.emtlab.dto.DisplayAccommodationDto;
import mk.ukim.finki.emtlab.dto.TemporaryReservationsDto;
import mk.ukim.finki.emtlab.service.application.TemporaryReservationsApplicationService;
import mk.ukim.finki.emtlab.service.domain.TemporaryReservationsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemporaryReservationsApplicationServiceImpl implements TemporaryReservationsApplicationService {

    private final TemporaryReservationsService temporaryReservationsService;

    public TemporaryReservationsApplicationServiceImpl(TemporaryReservationsService temporaryReservationsService) {
        this.temporaryReservationsService = temporaryReservationsService;
    }

    @Override
    public List<DisplayAccommodationDto> listAllAccommodationsInTemporaryReservations(Long temporaryReservationsId) {
        return DisplayAccommodationDto.from(this.temporaryReservationsService.listAllAccommodationsInTemporaryReservations(temporaryReservationsId));
    }

    @Override
    public Optional<TemporaryReservationsDto> getActiveTemporaryReservations(String username) {
        return this.temporaryReservationsService.getActiveTemporaryReservations(username).map(TemporaryReservationsDto::from);
    }

    @Override
    public Optional<TemporaryReservationsDto> addAccommodationToTemporaryReservations(String username, Long accommodationId) {
        return this.temporaryReservationsService.addAccommodationToTemporaryReservations(username, accommodationId).map(TemporaryReservationsDto::from);
    }

    @Override
    public Optional<TemporaryReservationsDto> addAllFreeAccommodationsToTemporaryReservations(String username) {
        return this.temporaryReservationsService.addAllFreeAccommodationsToTemporaryReservations(username).map(TemporaryReservationsDto::from);
    }

    @Override
    public Optional<TemporaryReservationsDto> confirmTemporaryReservations(String username) {
        return this.temporaryReservationsService.confirmTemporaryReservations(username).map(TemporaryReservationsDto::from);
    }
}
