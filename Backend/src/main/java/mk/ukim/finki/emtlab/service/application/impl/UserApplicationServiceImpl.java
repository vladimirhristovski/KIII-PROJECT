package mk.ukim.finki.emtlab.service.application.impl;

import mk.ukim.finki.emtlab.dto.DisplayUserDto;
import mk.ukim.finki.emtlab.dto.LoginResponseDto;
import mk.ukim.finki.emtlab.dto.LoginUserDto;
import mk.ukim.finki.emtlab.dto.RegisterUserDto;
import mk.ukim.finki.emtlab.model.domain.User;
import mk.ukim.finki.emtlab.helpers.JwtHelper;
import mk.ukim.finki.emtlab.service.application.UserApplicationService;
import mk.ukim.finki.emtlab.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserService userService;
    private final JwtHelper jwtHelper;

    public UserApplicationServiceImpl(UserService userService, JwtHelper jwtHelper) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public Optional<DisplayUserDto> register(RegisterUserDto registerUserDto) {
        User user = this.userService.register(registerUserDto.username(), registerUserDto.password(), registerUserDto.repeatPassword(), registerUserDto.name(), registerUserDto.surname(), registerUserDto.role());

        return Optional.of(DisplayUserDto.from(user));
    }

    @Override
    public Optional<LoginResponseDto> login(LoginUserDto loginUserDto) {
        User user = this.userService.login(
                loginUserDto.username(),
                loginUserDto.password()
        );

        String token = this.jwtHelper.generateToken(user);

        return Optional.of(new LoginResponseDto(token));
    }

    @Override
    public Optional<DisplayUserDto> findByUsername(String username) {
        return Optional.of(DisplayUserDto.from(this.userService.findByUsername(username)));
    }

}
