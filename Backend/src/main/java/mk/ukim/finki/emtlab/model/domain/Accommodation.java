package mk.ukim.finki.emtlab.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import mk.ukim.finki.emtlab.model.enumerations.AccommodationCategory;

@Data
@Entity
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AccommodationCategory category;

    @ManyToOne
    private Host host;

    private Integer numRooms;

    private Boolean rented;

    public Accommodation() {
    }

    public Accommodation(String name, AccommodationCategory category, Host host, Integer numRooms) {
        this.name = name;
        this.category = category;
        this.host = host;
        this.numRooms = numRooms;
        this.rented = false;
    }

    public Accommodation(String name, AccommodationCategory category, Host host, Integer numRooms, Boolean rented) {
        this.name = name;
        this.category = category;
        this.host = host;
        this.numRooms = numRooms;
        this.rented = rented;
    }
}
