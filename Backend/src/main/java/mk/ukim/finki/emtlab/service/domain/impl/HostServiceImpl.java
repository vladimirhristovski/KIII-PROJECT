package mk.ukim.finki.emtlab.service.domain.impl;

import mk.ukim.finki.emtlab.events.HostCreatedEvent;
import mk.ukim.finki.emtlab.events.HostDeletedEvent;
import mk.ukim.finki.emtlab.events.HostUpdatedEvent;
import mk.ukim.finki.emtlab.model.domain.Host;
import mk.ukim.finki.emtlab.repository.HostRepository;
import mk.ukim.finki.emtlab.repository.HostsPerCountryViewRepository;
import mk.ukim.finki.emtlab.service.domain.CountryService;
import mk.ukim.finki.emtlab.service.domain.HostService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostServiceImpl implements HostService {

    private final CountryService countryService;
    private final HostRepository hostRepository;
    private final HostsPerCountryViewRepository hostsPerCountryViewRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public HostServiceImpl(CountryService countryService, HostRepository hostRepository, HostsPerCountryViewRepository hostsPerCountryViewRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.countryService = countryService;
        this.hostRepository = hostRepository;
        this.hostsPerCountryViewRepository = hostsPerCountryViewRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Host> findAll() {
        return this.hostRepository.findAll();
    }

    @Override
    public Optional<Host> save(Host host) {
        Optional<Host> savedHost = Optional.empty();

        if (host.getCountry() != null &&
                this.countryService.findById(host.getCountry().getId()).isPresent()) {
            savedHost = Optional.of(hostRepository.save(new Host(
                    host.getName(),
                    host.getSurname(),
                    this.countryService.findById(host.getCountry().getId()).get()
            )));
            this.applicationEventPublisher.publishEvent(new HostCreatedEvent(host));
        }
        return savedHost;
    }

    @Override
    public Optional<Host> findById(Long id) {
        return this.hostRepository.findById(id);
    }

    @Override
    public Optional<Host> update(Long id, Host host) {
        return this.hostRepository.findById(id).map(existingHost -> {
            if (host.getName() != null) {
                existingHost.setName(host.getName());
            }
            if (host.getSurname() != null) {
                existingHost.setSurname(host.getSurname());
            }
            if (host.getCountry() != null && this.countryService.findById(host.getCountry().getId()).isPresent()) {
                existingHost.setCountry(this.countryService.findById(host.getCountry().getId()).get());
            }

            Host updatedHost = this.hostRepository.save(existingHost);
            this.applicationEventPublisher.publishEvent(new HostUpdatedEvent(host));

            return updatedHost;
        });
    }

    @Override
    public void deleteById(Long id) {
        Host host = this.findById(id).orElse(null);
        if (host != null) {
            this.hostRepository.deleteById(id);
            this.applicationEventPublisher.publishEvent(new HostDeletedEvent(host));
        }
    }

    @Override
    public void refreshMaterializedView() {
        this.hostsPerCountryViewRepository.refreshMaterializedView();
    }
}
