package mk.ukim.finki.emtlab.listeners;

import mk.ukim.finki.emtlab.events.HostCreatedEvent;
import mk.ukim.finki.emtlab.events.HostDeletedEvent;
import mk.ukim.finki.emtlab.events.HostUpdatedEvent;
import mk.ukim.finki.emtlab.service.domain.HostService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HostEventHandlers {

    private final HostService hostService;

    public HostEventHandlers(HostService hostService) {
        this.hostService = hostService;
    }

    @EventListener
    public void onHostCreated(HostCreatedEvent event) {
        this.hostService.refreshMaterializedView();
    }

    @EventListener
    public void onHostUpdated(HostUpdatedEvent event) {
        this.hostService.refreshMaterializedView();
    }

    @EventListener
    public void onHostDeleted(HostDeletedEvent event) {
        this.hostService.refreshMaterializedView();
    }

}
