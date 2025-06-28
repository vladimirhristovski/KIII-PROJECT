package mk.ukim.finki.emtlab.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.emtlab.dto.DisplayAccommodationDto;
import mk.ukim.finki.emtlab.dto.TemporaryReservationsDto;
import mk.ukim.finki.emtlab.model.domain.User;
import mk.ukim.finki.emtlab.service.application.TemporaryReservationsApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temporary-reservations")
@Tag(name = "Temporary Reservations API", description = "Endpoints for managing the temporary reservations")
public class TemporaryReservationsController {

    private final TemporaryReservationsApplicationService temporaryReservationsApplicationService;

    public TemporaryReservationsController(TemporaryReservationsApplicationService temporaryReservationsApplicationService) {
        this.temporaryReservationsApplicationService = temporaryReservationsApplicationService;
    }

    @Operation(summary = "Get active temporary reservations list", description = "Retrieves the active temporary reservations list for the logged-in user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Temporary reservations list retrieved successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Temporary reservations list not found."
            )
    })
    @GetMapping
    public ResponseEntity<TemporaryReservationsDto> getActiveTemporaryReservations(HttpServletRequest request) {
        String username = request.getRemoteUser();

        return this.temporaryReservationsApplicationService.getActiveTemporaryReservations(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add accommodation to temporary reservations list", description = "Adds an accommodation to the temporary reservations list for the logged-in user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Accommodation added to temporary reservations list successfully."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Accommodation not found."
            )
    })
    @PostMapping("/add-accommodation/{id}")
    public ResponseEntity<TemporaryReservationsDto> addAccommodationToTemporaryReservations(@PathVariable Long id, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return this.temporaryReservationsApplicationService.addAccommodationToTemporaryReservations(user.getUsername(), id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Confirm all reservations in the temporary reservations list", description = "Confirm all reservations in the temporary reservations list for the logged-in user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reservations confirmed successfully."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Temporary reservations list not found."
            )
    })
    @PatchMapping("/confirm-reservations")
    public ResponseEntity<TemporaryReservationsDto> confirmTemporaryReservations(HttpServletRequest request) {
        try {
            String username = request.getRemoteUser();

            return this.temporaryReservationsApplicationService.confirmTemporaryReservations(username)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Reserve all not rented accommodations to temporary reservations list", description = "Reserves all not rented accommodations to the temporary reservations list for the logged-in user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Accommodations reserved successfully."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Accommodations not found."
            )
    })
    @PostMapping("/reserve-all-non-reserved-accommodations")
    public ResponseEntity<TemporaryReservationsDto> addAllNonReservedAccommodationsToTemporaryReservations(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return this.temporaryReservationsApplicationService.addAllFreeAccommodationsToTemporaryReservations(user.getUsername())
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get all reservations from temporary reservations list", description = "Retrieves all reservations from the temporary reservations list for the logged-in user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reservations list retrieved successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reservations list not found."
            )
    })
    @GetMapping("/get-temporary-reservations")
    public List<DisplayAccommodationDto> getTemporaryReservations(HttpServletRequest request) {
        String username = request.getRemoteUser();
        if (this.temporaryReservationsApplicationService.getActiveTemporaryReservations(username).isPresent()) {
            TemporaryReservationsDto temporaryReservationsDto = this.temporaryReservationsApplicationService.getActiveTemporaryReservations(username).get();

            return this.temporaryReservationsApplicationService.listAllAccommodationsInTemporaryReservations(temporaryReservationsDto.id());
        }
        return null;
    }

}
