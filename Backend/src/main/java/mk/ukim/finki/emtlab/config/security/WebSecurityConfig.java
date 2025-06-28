package mk.ukim.finki.emtlab.config.security;

import mk.ukim.finki.emtlab.security.CustomUsernamePasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Profile("test")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final CustomUsernamePasswordAuthenticationProvider authenticationProvider;

    public WebSecurityConfig(CustomUsernamePasswordAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Allowed frontend origins
        corsConfiguration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://ecommerce.local"
        ));

        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Authorization config: allow these endpoints without auth; restrict others to role HOST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/accommodations",
                                "/api/countries",
                                "/api/hosts",
                                "/api/user/login",
                                "/api/user/register",
                                "/api/temporary-reservations"
                        ).permitAll()
                        .anyRequest().hasRole("HOST")
                )

                // Configure login endpoint (for REST API, typically returns 200 or 401, no redirect)
                .formLogin(form -> form
                        .loginProcessingUrl("/api/user/login")
                        .permitAll()
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(401);
                            // you can write response body or JSON here if needed
                        })
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(200);
                            // You can write response body or JSON if needed
                        })
                )

                // Logout config
                .logout(logout -> logout
                        .logoutUrl("/api/user/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200);
                        })
                )

                // Exception handling for access denied page (optional)
                .exceptionHandling(handler -> handler.accessDeniedPage("/access_denied"));

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.authenticationProvider(authenticationProvider);
        return authBuilder.build();
    }
}
