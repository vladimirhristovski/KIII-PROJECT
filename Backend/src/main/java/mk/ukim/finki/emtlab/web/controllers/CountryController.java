package mk.ukim.finki.emtlab.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.emtlab.dto.CreateCountryDto;
import mk.ukim.finki.emtlab.dto.DisplayCountryDto;
import mk.ukim.finki.emtlab.service.application.CountryApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@Tag(name = "Country API", description = "Endpoints for managing host countries")
public class CountryController {

    private final CountryApplicationService countryApplicationService;

    public CountryController(CountryApplicationService countryApplicationService) {
        this.countryApplicationService = countryApplicationService;
    }

    @Operation(summary = "Get all countries", description = "Retrieves a list of all available countries.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully.")
    @GetMapping
    public List<DisplayCountryDto> findAll() {
        return this.countryApplicationService.findAll();
    }

    @Operation(summary = "Get country by ID", description = "Finds a country by its ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Country found successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not found."
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DisplayCountryDto> findById(@PathVariable Long id) {
        return this.countryApplicationService.findById(id)
                .map(country -> ResponseEntity.ok().body(country))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new country", description = "Creates a new country.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Country created successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not created successfully."
            )
    })
    @PostMapping("/add")
    public ResponseEntity<DisplayCountryDto> save(@RequestBody CreateCountryDto createCountryDto) {
        return this.countryApplicationService.save(createCountryDto)
                .map(country -> ResponseEntity.ok().body(country))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an existing country", description = "Updates a country by ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Country updated successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not found."
            )
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayCountryDto> update(@PathVariable Long id, @RequestBody CreateCountryDto createCountryDto) {
        return this.countryApplicationService.update(id, createCountryDto)
                .map(country -> ResponseEntity.ok().body(country))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a country", description = "Deletes a country by its ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Country deleted successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not found."
            )
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (this.countryApplicationService.findById(id).isPresent()) {
            this.countryApplicationService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
