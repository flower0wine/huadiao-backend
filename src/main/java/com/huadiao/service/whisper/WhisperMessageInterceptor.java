package com.huadiao.service.whisper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2023 年 10 月 13 日
 */
@Slf4j
@Component
public class WhisperMessageInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
        HttpServletRequest req = serverHttpRequest.getServletRequest();
        HttpSession session = req.getSession();
        String uidKey = "uid";
        String userIdKey = "userId";
        String nicknameKey = "nickname";
        // 将 HttpSession 中的数据复制到 WebSocketSession 中
        Integer uid = (Integer) session.getAttribute(uidKey);
        boolean allow = uid != null;
        if(allow) {
            String userId = (String) session.getAttribute(userIdKey);
            String nickname = (String) session.getAttribute(nicknameKey);
            attributes.put(uidKey, uid);
            attributes.put(userIdKey, userId);
            attributes.put(nicknameKey, nickname);
            log.trace("uid, userId 分别为 {}, {} 的用户尝试建立 websocket 连接, 处于握手前", uid, userId);
        }
        else {
            log.trace("建立握手失败, 原因为 uid: null, 用户未登录");
        }
        return allow;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
    }
}
