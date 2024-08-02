package com.huadiao.service.whisper;

import com.huadiao.redis.MessageJedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author flowerwine
 * @date 2023 年 10 月 13 日
 */
public abstract class AbstractWhisperMessageHandler implements WebSocketHandler {

    @Autowired
    protected MessageJedisUtil messageJedisUtil;

    @Value("${huadiao.uidKey}")
    protected String uidKey;

    @Value("${huadiao.userIdKey}")
    protected String userIdKey;

    @Value("${huadiao.nicknameKey}")
    protected String nicknameKey;

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
