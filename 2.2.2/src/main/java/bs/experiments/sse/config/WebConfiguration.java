package bs.experiments.sse.config;

import io.netty.channel.ChannelHandler;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import reactor.netty.NettyPipeline;

@Configuration
public class WebConfiguration implements WebFluxConfigurer,
        WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

    @Override
    public void customize(NettyReactiveWebServerFactory factory) {
        NettyServerCustomizer customizer = httpServer -> httpServer
                .tcpConfiguration(tcpServer -> tcpServer
                        .doOnConnection(c -> {
                            ChannelHandler channelHandler = c.channel().pipeline()
                                    .get(NettyPipeline.ReactiveBridge);
                            //if (channelHandler instanceof NettyPipeline.SendOptions) {
                                //NettyPipeline.SendOptions sendOption =
                                //        (NettyPipeline.SendOptions) channelHandler;
                                // flush on each message => fast fail on broken channel
                                //sendOption.flushOnEach(false);
                            //}
                        })
                );

        factory.addServerCustomizers(customizer);
    }

}
