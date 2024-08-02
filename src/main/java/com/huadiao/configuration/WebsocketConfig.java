package com.huadiao.configuration;

import com.huadiao.service.whisper.WhisperMessageHandler;
import com.huadiao.service.whisper.WhisperMessageInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * @author flowerwine
 * @date 2023 年 10 月 13 日
 */
@Slf4j
@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {
    private WhisperMessageInterceptor whisperMessageInterceptor;
    private WhisperMessageHandler whisperMessageHandler;

    @Autowired
    public WebsocketConfig(WhisperMessageInterceptor whisperMessageInterceptor, WhisperMessageHandler whisperMessageHandler) {
        this.whisperMessageInterceptor = whisperMessageInterceptor;
        this.whisperMessageHandler = whisperMessageHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        String path = "/message/whisper/talk";
        String allowOrigin = "*";
        String classPackagePath = whisperMessageHandler.getClass().getName();
        log.trace("尝试添加 websocket 处理类 [{}], 请求路径为: {}, 允许访问的域: {}", classPackagePath, path, allowOrigin);
        registry.addHandler(whisperMessageHandler, path)
                .addInterceptors(whisperMessageInterceptor)
                .setHandshakeHandler(new DefaultHandshakeHandler())
                .setAllowedOrigins(allowOrigin);
        log.trace("添加 websocket 处理类 [{}] 成功!", classPackagePath);
    }
}
