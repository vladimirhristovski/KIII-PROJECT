package mk.ukim.finki.emtlab.events;

import lombok.Getter;
import mk.ukim.finki.emtlab.model.domain.Host;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class HostDeletedEvent extends ApplicationEvent {

    private LocalDateTime when;

    public HostDeletedEvent(Host source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public HostDeletedEvent(Host source, LocalDateTime when) {
        super(source);
        this.when = when;
    }

}
