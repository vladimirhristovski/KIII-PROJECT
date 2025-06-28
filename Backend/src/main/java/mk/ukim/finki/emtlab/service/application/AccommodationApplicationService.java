package mk.ukim.finki.emtlab.service.application;

import mk.ukim.finki.emtlab.dto.CreateAccommodationDto;
import mk.ukim.finki.emtlab.dto.DisplayAccommodationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccommodationApplicationService {

    Optional<DisplayAccommodationDto> update(Long id, CreateAccommodationDto createAccommodationDto);

    Optional<DisplayAccommodationDto> save(CreateAccommodationDto createAccommodationDto);

    Optional<DisplayAccommodationDto> findById(Long id);

    List<DisplayAccommodationDto> findAll();

    void deleteById(Long id);

    Optional<DisplayAccommodationDto> setRented(Long id);

    Page<DisplayAccommodationDto> findAll(Pageable pageable);

}
