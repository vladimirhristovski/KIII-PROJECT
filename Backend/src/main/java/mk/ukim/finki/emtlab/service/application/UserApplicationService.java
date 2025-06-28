package mk.ukim.finki.emtlab.service.application;

import mk.ukim.finki.emtlab.dto.DisplayUserDto;
import mk.ukim.finki.emtlab.dto.LoginResponseDto;
import mk.ukim.finki.emtlab.dto.LoginUserDto;
import mk.ukim.finki.emtlab.dto.RegisterUserDto;

import java.util.Optional;

public interface UserApplicationService {

    Optional<DisplayUserDto> register(RegisterUserDto registerUserDto);

    Optional<LoginResponseDto> login(LoginUserDto loginUserDto);

    Optional<DisplayUserDto> findByUsername(String username);

}
