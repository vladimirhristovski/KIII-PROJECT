package mk.ukim.finki.emtlab.service.application.impl;

import mk.ukim.finki.emtlab.dto.CreateAccommodationDto;
import mk.ukim.finki.emtlab.dto.DisplayAccommodationDto;
import mk.ukim.finki.emtlab.model.domain.Host;
import mk.ukim.finki.emtlab.service.application.AccommodationApplicationService;
import mk.ukim.finki.emtlab.service.domain.AccommodationService;
import mk.ukim.finki.emtlab.service.domain.HostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccommodationApplicationServiceImpl implements AccommodationApplicationService {

    private final AccommodationService accommodationService;
    private final HostService hostService;

    public AccommodationApplicationServiceImpl(AccommodationService accommodationService, HostService hostService) {
        this.accommodationService = accommodationService;
        this.hostService = hostService;
    }

    @Override
    public Optional<DisplayAccommodationDto> update(Long id, CreateAccommodationDto createAccommodationDto) {
        Optional<Host> host = this.hostService.findById(createAccommodationDto.hostId());

        return this.accommodationService.update(id,
                createAccommodationDto.toAccommodation(
                        host.orElse(null)
                )
        ).map(DisplayAccommodationDto::from);
    }

    @Override
    public Optional<DisplayAccommodationDto> save(CreateAccommodationDto createAccommodationDto) {
        Optional<Host> host = this.hostService.findById(createAccommodationDto.hostId());

        if (host.isPresent()) {
            return this.accommodationService.save(createAccommodationDto.toAccommodation(host.get())).map(DisplayAccommodationDto::from);
        }
        return Optional.empty();
    }

    @Override
    public Optional<DisplayAccommodationDto> findById(Long id) {
        return this.accommodationService.findById(id).map(DisplayAccommodationDto::from);
    }

    @Override
    public List<DisplayAccommodationDto> findAll() {
        return this.accommodationService.findAll().stream().map(DisplayAccommodationDto::from).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        this.accommodationService.deleteById(id);
    }

    @Override
    public Optional<DisplayAccommodationDto> setRented(Long id) {
        return this.accommodationService.setRented(id).map(DisplayAccommodationDto::from);
    }

    @Override
    public Page<DisplayAccommodationDto> findAll(Pageable pageable) {
        return this.accommodationService.findAll(pageable)
                .map(DisplayAccommodationDto::from);
    }
}
