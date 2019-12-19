package bs.experiments.sse.controller;

import bs.experiments.sse.service.SseService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@Slf4j
public class SseController {
    @Autowired
    private SseService sseService;

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> subsEvents() {
        return sseService.getEvents()
                .map(s -> ServerSentEvent.<String>builder().data(s).build());
    }

}