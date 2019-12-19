package bs.experiments.sse.service;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Slf4j
@Service
public class SseService {
    private int counter = 0;

    public Flux<String> getEvents() {
        AtomicInteger lastProcessed = new AtomicInteger();
        return Flux
                .interval(Duration.ofSeconds(1))
                .map(i -> i + " -> " + counter)
                .doOnNext(v -> lastProcessed.set(counter++))
                .doOnEach(sig -> {
                    logger.info("{} vs {}", sig.get(), sig.getThrowable());
                })
                .doFinally(st -> {
                    logger.info("Completed with signal type: {}. lastProcessed: {}", st, lastProcessed);
                    counter = lastProcessed.get();
                });
    }
}
