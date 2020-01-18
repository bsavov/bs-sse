package bs.experiments.sse.config;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.flush.FlushConsolidationHandler;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfiguration implements WebFluxConfigurer,
        WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

    @Override
    public void customize(NettyReactiveWebServerFactory factory) {
        NettyServerCustomizer customizer = httpServer -> httpServer
                .tcpConfiguration(tcpServer -> tcpServer
                        .doOnConnection(c -> {
                            ChannelPipeline pipeline = c.channel().pipeline();
                            pipeline.addFirst(new FlushConsolidationHandler(1, false));
                        })
                );

        factory.addServerCustomizers(customizer);
    }

}
