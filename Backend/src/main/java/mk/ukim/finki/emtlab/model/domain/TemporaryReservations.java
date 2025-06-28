package mk.ukim.finki.emtlab.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import mk.ukim.finki.emtlab.model.enumerations.TemporaryReservationsStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class TemporaryReservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToMany
    private List<Accommodation> accommodations;

    @Enumerated(EnumType.STRING)
    private TemporaryReservationsStatus status;

    public TemporaryReservations() {
    }

    public TemporaryReservations(User user) {
        this.dateCreated = LocalDateTime.now();
        this.user = user;
        this.accommodations = new ArrayList<>();
        this.status = TemporaryReservationsStatus.CREATED;
    }
}
