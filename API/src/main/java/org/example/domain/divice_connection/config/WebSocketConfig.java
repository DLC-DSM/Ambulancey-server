package org.example.domain.divice_connection.config;

import org.example.domain.divice_connection.Hendler.SocketDiviceConnectHendler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketDiviceConnectHendler(),"/connect")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
