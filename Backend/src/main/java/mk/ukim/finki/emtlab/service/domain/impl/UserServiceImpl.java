package mk.ukim.finki.emtlab.service.domain.impl;

import mk.ukim.finki.emtlab.model.domain.User;
import mk.ukim.finki.emtlab.model.enumerations.Role;
import mk.ukim.finki.emtlab.repository.UserRepository;
import mk.ukim.finki.emtlab.service.domain.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role role) {
        if (!username.isEmpty() || !password.isEmpty() || !repeatPassword.isEmpty() || !name.isEmpty() || !surname.isEmpty() || password.equals(repeatPassword) || this.userRepository.findByUsername(username).isEmpty()) {
            User user = new User(username, this.passwordEncoder.encode(password), name, surname, role);
            return this.userRepository.save(user);
        }
        return null;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new RuntimeException("Invalid Credentials");
        }

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }

        return user;
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
