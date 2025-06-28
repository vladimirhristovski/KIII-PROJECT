package mk.ukim.finki.emtlab.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.emtlab.dto.CreateAccommodationDto;
import mk.ukim.finki.emtlab.dto.DisplayAccommodationDto;
import mk.ukim.finki.emtlab.model.views.AccommodationsPerHostView;
import mk.ukim.finki.emtlab.repository.AccommodationsPerHostViewRepository;
import mk.ukim.finki.emtlab.service.application.AccommodationApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
@Tag(name = "Accommodation API", description = "Endpoints for managing accommodations")
public class AccommodationController {

    private final AccommodationApplicationService accommodationApplicationService;
    private final AccommodationsPerHostViewRepository accommodationsPerHostViewRepository;

    public AccommodationController(AccommodationApplicationService accommodationApplicationService, AccommodationsPerHostViewRepository accommodationsPerHostViewRepository) {
        this.accommodationApplicationService = accommodationApplicationService;
        this.accommodationsPerHostViewRepository = accommodationsPerHostViewRepository;
    }

    @Operation(summary = "Get all accommodations", description = "Retrieves a list of all available accommodations.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully.")
    @GetMapping
    public List<DisplayAccommodationDto> findAll() {
        return this.accommodationApplicationService.findAll();
    }

    @Operation(summary = "Get accommodation by ID", description = "Finds an accommodation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Accommodation found successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Accommodation not found."
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DisplayAccommodationDto> findById(@PathVariable Long id) {
        return this.accommodationApplicationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new accommodation", description = "Creates a new accommodation.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Accommodation created successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Accommodation not created successfully."
            )
    })
    @PostMapping("/add")
    public ResponseEntity<DisplayAccommodationDto> save(@RequestBody CreateAccommodationDto createAccommodationDto) {
        return this.accommodationApplicationService.save(createAccommodationDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an existing accommodation", description = "Updates an accommodation by ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Accommodation updated successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Accommodation not found."
            )
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayAccommodationDto> update(@PathVariable Long id, @RequestBody CreateAccommodationDto createAccommodationDto) {
        return this.accommodationApplicationService.update(id, createAccommodationDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Rent an existing non-rented accommodation", description = "Rents a non-rented accommodation by ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Accommodation rented successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Accommodation not found."
            )
    })
    @PutMapping("/rent/{id}")
    public ResponseEntity<DisplayAccommodationDto> rent(@PathVariable Long id) {
        return this.accommodationApplicationService.setRented(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an accommodation", description = "Deletes an accommodation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Accommodation deleted successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Accommodation not found."
            )
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (this.accommodationApplicationService.findById(id).isPresent()) {
            this.accommodationApplicationService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all accommodations per host", description = "Retrieves a list of all accommodations per host.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully.")
    @GetMapping("/by-host")
    public List<AccommodationsPerHostView> findAllAccommodationsPerHost() {
        return this.accommodationsPerHostViewRepository.findAll();
    }

    @Operation(summary = "Pagination for accommodations", description = "Retrieves a paginated list of all accommodations.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully.")
    @GetMapping("/paginated")
    public ResponseEntity<Page<DisplayAccommodationDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(this.accommodationApplicationService.findAll(pageable));
    }

}
