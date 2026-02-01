package producer.Producer;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.HttpConnectStrategy;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.concurrent.TimeUnit;
@Service
@RequiredArgsConstructor
@Slf4j
public class WikimediaChangesProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafkaProducer.topic.name}")
    private String topic;

    public void startStream() throws Exception {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://stream.wikimedia.org/v2/stream/recentchange")
                .header("User-Agent", "pratyush-kafka-learning-app/1.0")
                .build();

        Response response = client.newCall(request).execute();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(response.body().byteStream())
        );

        log.info("Connected to Wikimedia stream");

        String line;
        while ((line = reader.readLine()) != null) { //Reads one line from the live HTTP stream
            if (line.startsWith("data: ")) { // Filters only actual event payloads because data is the actual json
                String json = line.substring(6); //Removes "data: " prefix
                kafkaTemplate.send(topic, json); //Pushes event JSON into Kafka
                log.info("Sent to Kafka -> {}", json);
            }
        }
    }
}

