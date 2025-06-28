package mk.ukim.finki.emtlab.events;

import lombok.Getter;
import mk.ukim.finki.emtlab.model.domain.Host;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class HostCreatedEvent extends ApplicationEvent {

    private LocalDateTime when;

    public HostCreatedEvent(Host source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public HostCreatedEvent(Host source, LocalDateTime when) {
        super(source);
        this.when = when;
    }

}
