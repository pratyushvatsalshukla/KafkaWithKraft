package producer.Producer;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import tools.jackson.databind.ObjectMapper;

//import org.apache.kafka.clients.consumer.internals.events.BackgroundEventHandler;
@Slf4j
@RequiredArgsConstructor
public class WikimediaChangeHandlers implements BackgroundEventHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onOpen() {
        log.info("Stream Opened...");
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) throws Exception {
        String data = messageEvent.getData();
        log.info("Event Data -> {}", data);
        kafkaTemplate.send("wikimedia-topic", data);
    }

    @Override public void onClosed() {}
    @Override public void onComment(String comment) {}
    @Override public void onError(Throwable t) { log.error("Error", t); }
}
