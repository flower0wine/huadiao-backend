package com.huadiao.service.design.template.register;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.UserOAuthDto;
import com.huadiao.entity.dto.user.UserShareDto;
import com.huadiao.entity.elasticsearch.UserEs;
import com.huadiao.enumeration.AccountTypeEnum;
import com.huadiao.enumeration.LoginTypeEnum;
import com.huadiao.enumeration.SexEnum;
import com.huadiao.service.design.template.AbstractInspector;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author flowerwine
 * @date 2024 年 02 月 17 日
 */
@Getter
@Slf4j
@NoArgsConstructor
@Component
public class GoogleRegisterInspector extends AbstractInspector implements RegisterInspector {

    private ThreadLocal<String> accessTokenThreadLocal = new ThreadLocal<>();

    private ThreadLocal<JSONObject> jsonObjectThreadLocal = new ThreadLocal<>();

    private ThreadLocal<String> codeThreadLocal = new ThreadLocal<>();

    @Value("${oauth.google.grantType}")
    private String grantType;

    @Value("${oauth.google.redirectUri}")
    private String redirectUri;

    @Override
    public Result<?> check() {
        String code = codeThreadLocal.get();
        JSONObject data = getData(code, googleClientId, googleClientSecret, googleAccessTokenUri, grantType, redirectUri);
        String accessToken = data.getStr("access_token");
        accessTokenThreadLocal.set(accessToken);

        String idToken = data.get("id_token").toString();
        String[] split = idToken.split("\\.");
        JSONObject jsonObject = JSONUtil.parseObj(Base64Decoder.decodeStr(split[1]));
        jsonObjectThreadLocal.set(jsonObject);

        Integer googleId = jsonObject.getInt("sub");

        // 查询用户是否存在
        UserShareDto userShareDto = userMapper.selectOAuthUserExist(googleId, LoginTypeEnum.GOOGLE.getType());
        if(userShareDto != null && userShareDto.getUid() != null) {
            log.debug("第三方用户已经在本站注册过, 不再进行注册");
            return Result.ok(userShareDto);
        }

        return Result.ok(null);
    }

    @Override
    public void addUser(Integer uid, String userId, HttpServletRequest request, HttpServletResponse response) {
        String accessToken = accessTokenThreadLocal.get();
        JSONObject jsonObject = jsonObjectThreadLocal.get();

        Integer googleId = jsonObject.getInt("sub");
        String nickname = jsonObject.getStr("name");
        String avatar = jsonObject.getStr("picture");

        UserOAuthDto userOAuthDto = new UserOAuthDto();
        userOAuthDto.setUid(uid);
        userOAuthDto.setUserId(userId);
        userOAuthDto.setAccessToken(accessToken);
        userOAuthDto.setOauthId(googleId);
        userOAuthDto.setAccountType(AccountTypeEnum.USER.getType());
        userOAuthDto.setLoginType(LoginTypeEnum.GOOGLE.getType());
        userMapper.insertOauthUser(userOAuthDto);

        // 新增用户信息
        userInfoMapper.insertOrUpdateUserInfoByUid(uid, nickname, null, SexEnum.NO_KNOWN.value, null, null);
        log.trace("新增用户信息成功 (uid: {}, userId: {})", uid, userId);

        homepageMapper.insertUserHomepageByUid(uid, avatar);
        log.debug("新增用户主页信息成功 (uid: {}, userId: {})", uid, userId);

        UserEs userEs = new UserEs();
        userEs.setUid(uid);
        userEs.setUserId(userId);
        userEs.setAvatar(avatar);
        userEs.setNickname(nickname);
        // 保存至 elasticsearch
        userRepository.save(userEs);
    }

    @Override
    public void flushThreadLocal() {
        accessTokenThreadLocal.remove();
    }


}
