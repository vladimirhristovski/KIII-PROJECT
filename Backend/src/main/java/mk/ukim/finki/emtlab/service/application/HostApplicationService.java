package mk.ukim.finki.emtlab.service.application;

import mk.ukim.finki.emtlab.dto.CreateHostDto;
import mk.ukim.finki.emtlab.dto.DisplayHostDto;

import java.util.List;
import java.util.Optional;

public interface HostApplicationService {

    Optional<DisplayHostDto> update(Long id, CreateHostDto createHostDto);

    Optional<DisplayHostDto> save(CreateHostDto createHostDto);

    Optional<DisplayHostDto> findById(Long id);

    List<DisplayHostDto> findAll();

    void deleteById(Long id);

}
