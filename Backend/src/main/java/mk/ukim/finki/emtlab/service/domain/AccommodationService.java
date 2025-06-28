package mk.ukim.finki.emtlab.service.domain;

import mk.ukim.finki.emtlab.model.domain.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccommodationService {

    List<Accommodation> findAll();

    Optional<Accommodation> save(Accommodation accommodation);

    Optional<Accommodation> findById(Long id);

    Optional<Accommodation> update(Long id, Accommodation accommodation);

    void deleteById(Long id);

    Optional<Accommodation> setRented(Long id);

    void refreshMaterializedView();

    Page<Accommodation> findAll(Pageable pageable);

}
