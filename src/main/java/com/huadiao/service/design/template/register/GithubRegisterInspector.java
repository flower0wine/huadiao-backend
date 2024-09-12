package com.huadiao.service.design.template.register;

import cn.hutool.json.JSONObject;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.UserOAuthDto;
import com.huadiao.entity.dto.user.UserShareDto;
import com.huadiao.enumeration.AccountTypeEnum;
import com.huadiao.enumeration.LoginTypeEnum;
import com.huadiao.enumeration.SexEnum;
import com.huadiao.service.design.template.AbstractInspector;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author flowerwine
 * @date 2024 年 02 月 10 日
 */
@Getter
@Slf4j
@NoArgsConstructor
@Component
public class GithubRegisterInspector extends AbstractInspector implements RegisterInspector {

    private ThreadLocal<String> codeThreadLocal = new ThreadLocal<>();

    private ThreadLocal<String> accessTokenThreadLocal = new ThreadLocal<>();

    private ThreadLocal<JSONObject> jsonObjectThreadLocal = new ThreadLocal<>();

    @Override
    public void flushThreadLocal() {
        codeThreadLocal.remove();
        accessTokenThreadLocal.remove();
        jsonObjectThreadLocal.remove();
    }

    @Override
    public Result<?> check() {
        String code = codeThreadLocal.get();
        log.debug("Github 第三方登录, code: {}", code);

        JSONObject data = getData(code, githubClientId, githubClientSecret, githubAccessTokenUri);
        String accessToken = data.getStr("access_token");
        accessTokenThreadLocal.set(accessToken);

        JSONObject jsonObject = getUserInfo("token " + accessToken, githubApiUser);
        jsonObjectThreadLocal.set(jsonObject);

        Integer githubId = jsonObject.getInt("id");

        // 查询用户是否存在
        UserShareDto userShareDto = userMapper.selectOAuthUserExist(githubId, LoginTypeEnum.GITHUB.getType());
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

        Integer githubId = jsonObject.getInt("id");
        String nickname = jsonObject.getStr("name");
        String canvases = jsonObject.getStr("bio");
        String avatar = jsonObject.getStr("avatar_url");

        UserOAuthDto userOAuthDto = new UserOAuthDto();
        userOAuthDto.setUid(uid);
        userOAuthDto.setUserId(userId);
        userOAuthDto.setAccessToken(accessToken);
        userOAuthDto.setOauthId(githubId);
        userOAuthDto.setAccountType(AccountTypeEnum.USER.getType());
        userOAuthDto.setLoginType(LoginTypeEnum.GITHUB.getType());
        userMapper.insertOauthUser(userOAuthDto);

        // 新增用户信息
        userInfoMapper.insertOrUpdateUserInfoByUid(uid, nickname, canvases, SexEnum.NO_KNOWN.value, null, null);
        log.trace("新增用户信息成功 (uid: {}, userId: {})", uid, userId);

        homepageMapper.insertUserHomepageByUid(uid, avatar);
        log.debug("新增用户主页信息成功 (uid: {}, userId: {})", uid, userId);

        /*UserEs userEs = new UserEs();
        userEs.setUid(uid);
        userEs.setUserId(userId);
        userEs.setAvatar(avatar);
        userEs.setCanvases(canvases);
        userEs.setNickname(nickname);
        // 保存至 elasticsearch
        userRepository.save(userEs);*/

        log.debug("Github 第三方登录成功");
    }
}
