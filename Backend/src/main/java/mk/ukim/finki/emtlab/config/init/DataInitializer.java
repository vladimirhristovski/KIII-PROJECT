package mk.ukim.finki.emtlab.config.init;

import mk.ukim.finki.emtlab.model.domain.Accommodation;
import mk.ukim.finki.emtlab.model.domain.Country;
import mk.ukim.finki.emtlab.model.domain.Host;
import mk.ukim.finki.emtlab.model.domain.User;
import mk.ukim.finki.emtlab.model.enumerations.AccommodationCategory;
import mk.ukim.finki.emtlab.model.enumerations.Role;
import mk.ukim.finki.emtlab.repository.AccommodationRepository;
import mk.ukim.finki.emtlab.repository.CountryRepository;
import mk.ukim.finki.emtlab.repository.HostRepository;
import mk.ukim.finki.emtlab.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Component
public class DataInitializer {

    private final AccommodationRepository accommodationRepository;
    private final CountryRepository countryRepository;
    private final HostRepository hostRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AccommodationRepository accommodationRepository, CountryRepository countryRepository, HostRepository hostRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.accommodationRepository = accommodationRepository;
        this.countryRepository = countryRepository;
        this.hostRepository = hostRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @PostConstruct
    public void init() {

        Country country1 = new Country("Macedonia", "Europe");
        Country country2 = new Country("Germany", "Europe");
        Country country3 = new Country("Japan", "Asia");
        this.countryRepository.save(country1);
        this.countryRepository.save(country2);
        this.countryRepository.save(country3);

        Host host1 = new Host("Vladimir", "Hristovski", country1);
        Host host2 = new Host("Hans", "Schneider", country2);
        Host host3 = new Host("Oto", "Harishima", country3);
        this.hostRepository.save(host1);
        this.hostRepository.save(host2);
        this.hostRepository.save(host3);

        Accommodation accommodation1 = new Accommodation("Hotel Molika", AccommodationCategory.HOTEL, host1, 13);
        Accommodation accommodation2 = new Accommodation("Motel Berlin", AccommodationCategory.MOTEL, host2, 7);
        Accommodation accommodation3 = new Accommodation("Flat Tokyo", AccommodationCategory.FLAT, host3, 1);
        this.accommodationRepository.save(accommodation1);
        this.accommodationRepository.save(accommodation2);
        this.accommodationRepository.save(accommodation3);

        this.userRepository.save(new User(
                "host",
                passwordEncoder.encode("host"),
                "Host",
                "Host",
                Role.ROLE_HOST
        ));
        this.userRepository.save(new User(
                "user",
                passwordEncoder.encode("user"),
                "User",
                "User",
                Role.ROLE_USER
        ));

    }

}
