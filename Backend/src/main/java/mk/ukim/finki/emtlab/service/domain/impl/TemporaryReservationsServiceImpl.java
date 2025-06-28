package mk.ukim.finki.emtlab.service.domain.impl;

import mk.ukim.finki.emtlab.model.domain.Accommodation;
import mk.ukim.finki.emtlab.model.domain.TemporaryReservations;
import mk.ukim.finki.emtlab.model.domain.User;
import mk.ukim.finki.emtlab.model.enumerations.TemporaryReservationsStatus;
import mk.ukim.finki.emtlab.repository.TemporaryReservationsRepository;
import mk.ukim.finki.emtlab.service.domain.AccommodationService;
import mk.ukim.finki.emtlab.service.domain.TemporaryReservationsService;
import mk.ukim.finki.emtlab.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemporaryReservationsServiceImpl implements TemporaryReservationsService {

    private final TemporaryReservationsRepository temporaryReservationsRepository;
    private final UserService userService;
    private final AccommodationService accommodationService;

    public TemporaryReservationsServiceImpl(TemporaryReservationsRepository temporaryReservationsRepository, UserService userService, AccommodationService accommodationService) {
        this.temporaryReservationsRepository = temporaryReservationsRepository;
        this.userService = userService;
        this.accommodationService = accommodationService;
    }

    @Override
    public List<Accommodation> listAllAccommodationsInTemporaryReservations(Long temporaryReservationsId) {
        if (this.temporaryReservationsRepository.findById(temporaryReservationsId).isEmpty()) {
            throw new RuntimeException("Temporary reservations not found");
        }
        return this.temporaryReservationsRepository.findById(temporaryReservationsId).get().getAccommodations();
    }

    @Override
    public Optional<TemporaryReservations> getActiveTemporaryReservations(String username) {
        User user = this.userService.findByUsername(username);

        return Optional.of(this.temporaryReservationsRepository.findByUserAndStatus(user, TemporaryReservationsStatus.CREATED)
                .orElseGet(() -> this.temporaryReservationsRepository.save(new TemporaryReservations(user))));
    }

    @Override
    public Optional<TemporaryReservations> addAccommodationToTemporaryReservations(String username, Long accommodationId) {
        if (this.getActiveTemporaryReservations(username).isPresent()) {
            TemporaryReservations temporaryReservations = this.getActiveTemporaryReservations(username).get();
            Accommodation accommodation = this.accommodationService.findById(accommodationId).orElseThrow(() -> new RuntimeException("Accommodation not found"));
            List<Accommodation> allNotRentedAccommodations = this.accommodationService.findAll().stream().filter(a -> !a.getRented()).toList();

            if (allNotRentedAccommodations.stream().filter(a -> a.getId().equals(accommodationId)).toList().isEmpty()) {
                throw new RuntimeException("Cannot add a rented accommodation to temporary reservations");
            }

            if (!temporaryReservations.getAccommodations().stream().filter(a -> a.getId().equals(accommodationId)).toList().isEmpty()) {
                throw new RuntimeException("Reservation already in temporary reservations");
            }

            temporaryReservations.getAccommodations().add(accommodation);
            return Optional.of(this.temporaryReservationsRepository.save(temporaryReservations));
        }
        return Optional.empty();
    }

    @Override
    public Optional<TemporaryReservations> addAllFreeAccommodationsToTemporaryReservations(String username) {
        if (this.getActiveTemporaryReservations(username).isPresent()) {
            TemporaryReservations temporaryReservations = this.getActiveTemporaryReservations(username).get();
            List<Accommodation> allNotRentedAccommodations = this.accommodationService.findAll().stream().filter(a -> !a.getRented()).toList();

            if (!allNotRentedAccommodations.isEmpty()) {
                allNotRentedAccommodations.forEach(accommodation -> {
                    if (!temporaryReservations.getAccommodations().contains(accommodation)) {
                        this.addAccommodationToTemporaryReservations(username, accommodation.getId());
                    }
                });
                this.confirmTemporaryReservations(username);
                return Optional.of(this.temporaryReservationsRepository.save(temporaryReservations));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<TemporaryReservations> confirmTemporaryReservations(String username) {
        if (this.getActiveTemporaryReservations(username).isPresent()) {
            TemporaryReservations temporaryReservations = this.getActiveTemporaryReservations(username).get();

            if (!temporaryReservations.getAccommodations().isEmpty()) {
                temporaryReservations.getAccommodations().forEach(accommodation -> {
                    this.accommodationService.findById(accommodation.getId()).ifPresent(a -> {
                        a.setRented(true);
                        this.accommodationService.update(a.getId(), a);
                    });
                });
                temporaryReservations.setStatus(TemporaryReservationsStatus.FINISHED);
                return Optional.of(this.temporaryReservationsRepository.save(temporaryReservations));
            }
        }
        return Optional.empty();
    }
}
