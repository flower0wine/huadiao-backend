package com.huadiao.service.websocket;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.huadiao.entity.Result;
import com.huadiao.entity.message.whisper.MessageType;
import com.huadiao.entity.message.whisper.WhisperMessage;
import com.huadiao.mapper.WhisperMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author flowerwine
 * @date 2023 年 10 月 13 日
 */
@Slf4j
@Component
public class WhisperMessageHandler extends AbstractWhisperMessageHandler {
    private WhisperMessageMapper whisperMessageMapper;

    private final static Map<Integer, WebSocketSession> SESSION_MAP = new ConcurrentHashMap<>();

    @Autowired
    public WhisperMessageHandler(WhisperMessageMapper whisperMessageMapper) {
        this.whisperMessageMapper = whisperMessageMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        Integer uid = (Integer) attributes.get(uidKey);
        String userId = (String) attributes.get(userIdKey);
        SESSION_MAP.put(uid, session);
        log.trace("uid, userId 分别为 {}, {} 的用户成功建立 websocket 连接, 当前连接数: {}", uid, userId, SESSION_MAP.size());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        // 将消息内容转化为 java 对象
        Integer myUid = (Integer) attributes.get(uidKey);
        String myUserId = (String) attributes.get(userIdKey);

        JSONObject jsonObject = new JSONObject(message.getPayload().toString());
        Integer receiveUid = (Integer) jsonObject.get("receiveUid");
        String messageContent = (String) jsonObject.get("messageContent");
        WebSocketSession receiverSession = SESSION_MAP.get(receiveUid);
        int whisperMessageId = messageJedisUtil.generateWhisperMessageId();
        log.trace("uid, userId 分别为 {}, {} 的用户发送消息给 uid 分别为 {} 的用户, 消息内容为 {}, messageId: {}", myUid, myUserId, receiveUid, messageContent, whisperMessageId);
        // 发送消息
        saveMessageToDatabase(myUid, receiveUid, messageContent, whisperMessageId);
        sendMessageToOnlineReceiver(receiverSession, receiveUid, jsonObject.get("messageContent").toString(), whisperMessageId);
        giveSenderMessageId(session, whisperMessageId);
    }

    /**
     * 发送者向接收者发送消息 (一对一), 仅当接收者在线
     *
     * @param receiverSession 接收者 session
     * @param receiveUid 接收者 uid
     * @param messageContent  消息内容
     * @param messageId       消息 id
     * @throws IOException 消息可能发送失败
     */
    private void sendMessageToOnlineReceiver(WebSocketSession receiverSession, Integer receiveUid, String messageContent, Integer messageId) throws IOException {
        if (receiverSession != null) {
            WhisperMessage whisperMessage = new WhisperMessage();
            whisperMessage.setMessageId(messageId);
            whisperMessage.setMessageContent(messageContent);
            whisperMessage.setMessageType(MessageType.NORMAL.type);
            whisperMessage.setSendTime(new Date());
            whisperMessage.setReceiveUid(receiveUid);
            Map<String, Object> map = new HashMap<>(4);
            map.put("type", "receive");
            map.put("whisperMessage", whisperMessage);
            receiverSession.sendMessage(new TextMessage(JSONUtil.toJsonStr(Result.ok(map))));
        }
    }

    /**
     * 保存消息到数据库
     * @param senderUid 发送者 uid
     * @param receiveUid 接收者 uid
     * @param messageContent 消息内容
     * @param messageId 消息 id
     * @return 返回更新条数
     */
    private Integer saveMessageToDatabase(Integer senderUid, Integer receiveUid, String messageContent, Integer messageId) {
        return whisperMessageMapper.insertWhisperMessage(senderUid, receiveUid, messageContent, messageId, MessageType.NORMAL.type);
    }

    /**
     * 将消息 id 返回给发送者
     * @param senderSession 发送者 session
     * @param messageId 消息 id
     * @throws IOException 发送消息可能失败
     */
    private void giveSenderMessageId(WebSocketSession senderSession, Integer messageId) throws IOException {
        Map<String, Object> map = new HashMap<>(4);
        map.put("messageId", messageId);
        map.put("type", "send");
        senderSession.sendMessage(new TextMessage(JSONUtil.toJsonStr(Result.ok(map))));
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        Integer uid = (Integer) attributes.get(uidKey);
        SESSION_MAP.remove(uid);
    }
}
