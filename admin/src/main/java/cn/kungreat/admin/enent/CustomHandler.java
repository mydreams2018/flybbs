package cn.kungreat.admin.enent;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomHandler extends AbstractEventNotifier {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomHandler.class);

    protected CustomHandler(InstanceRepository repository) {
        super(repository);
    }
    /**
     * 事件触发时的回调、可以实现自已的业务 如发短信 等
     * */
    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                LOGGER.info(instance.getRegistration().getName(), event.getInstance(),
                        ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
            } else {
                LOGGER.info(instance.getRegistration().getName(), event.getInstance(),
                        event.getType());
            }
        });
    }
}
