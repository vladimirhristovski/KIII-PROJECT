package mk.ukim.finki.emtlab.dto;

import mk.ukim.finki.emtlab.model.domain.Accommodation;
import mk.ukim.finki.emtlab.model.domain.Host;
import mk.ukim.finki.emtlab.model.enumerations.AccommodationCategory;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayAccommodationDto(Long id, String name, AccommodationCategory category, Long hostId,
                                      Integer numRooms, Boolean rented) {

    public static DisplayAccommodationDto from(Accommodation accommodation) {
        return new DisplayAccommodationDto(accommodation.getId(), accommodation.getName(), accommodation.getCategory(), accommodation.getHost().getId(), accommodation.getNumRooms(), accommodation.getRented());
    }

    public static List<DisplayAccommodationDto> from(List<Accommodation> accommodations) {
        return accommodations.stream().map(DisplayAccommodationDto::from).collect(Collectors.toList());
    }

    public Accommodation toAccommodation(Host host) {
        return new Accommodation(name, category, host, numRooms, rented);
    }

}
