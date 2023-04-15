package com.huadiao.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.huadiao.controller.ErrorController;
import com.huadiao.entity.dto.UserBaseInfoDto;
import com.huadiao.entity.dto.UserDetailDto;
import com.huadiao.entity.dto.UserIndexDto;
import com.huadiao.entity.dto.UserShareDto;
import com.huadiao.mapper.FollowFanMapper;
import com.huadiao.mapper.UserInfoMapper;
import com.huadiao.mapper.UserMapper;
import com.huadiao.service.AbstractFollowFanService;
import com.huadiao.service.AbstractUserService;
import com.huadiao.service.FollowFanService;
import com.huadiao.utils.CreateUserId;
import com.huadiao.utils.GeneratorCookie;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 业务层: 与用户信息, 想用账号相关的操作的接口
 */
@Slf4j
@Service
public class UserServiceImpl extends AbstractUserService {
    private UserMapper userMapper;
    private FollowFanMapper followFanMapper;
    private UserInfoMapper userInfoMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, FollowFanMapper followFanMapper, UserInfoMapper userInfoMapper) {
        this.userMapper = userMapper;
        this.followFanMapper = followFanMapper;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public UserIndexDto getUserHuadiaoIndexInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Object temp = session.getAttribute("uid");
        Integer uid = temp == null ? null : (Integer) temp;
        log.debug("尝试获取 session 中的用户 uid 并且获取结果为 {}", uid);
        // 用户具有登录状态
        if (uid != null) {
            UserIndexDto userIndexDto = new UserIndexDto();
            // 获取并填充用户信息
            UserShareDto userShareDto = userMapper.selectUserShareDtoByUid(uid);
            userIndexDto.setUid(userShareDto.getUid());
            userIndexDto.setUserId(userShareDto.getUserId());
            userIndexDto.setNickname(userShareDto.getNickname());
            userIndexDto.setUserAvatar(userShareDto.getUserAvatar());

            // 用户关注与粉丝
            List<Integer> followFans = followFanMapper.countFollowAndFansByUid(uid);
            userIndexDto.setFollow(followFans.get(FollowFanService.FOLLOW_INDEX));
            userIndexDto.setFan(followFans.get(FollowFanService.FAN_INDEX));
            // 用户已登录
            userIndexDto.setLogin(USER_LOGIN_STATUS);
            log.info("用户具有登录状态, uid 为 {} 的用户获取了自己在花凋首页的相关信息", uid);
            return userIndexDto;
        }
        log.debug("用户没有登录状态, 向前端返回 404 资源未找到状态码");
        // 没有登录状态, 执行 404 错误请求处理
        request.getRequestDispatcher(ErrorController.NOT_FOUND_DISPATCHER_PATH).forward(request, response);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) throws ServletException, IOException {
        UserBaseInfoDto userBaseInfoDto = userMapper.selectUserByUsernameAndPassword(username, password);
        log.debug("用户使用用户名为 {} 和密码为 {} 的账号进行登录", username, password);
        // 不存在该用户
        if (userBaseInfoDto == null) {
            log.debug("用户名为 {} 和密码为 {} 的账号不存在", username, password);
            // 返回 404 状态码
            request.getRequestDispatcher(ErrorController.NOT_FOUND_DISPATCHER_PATH).forward(request, response);
        }
        // 存在该用户
        else {
            log.info("用户名为 {} 的用户登录成功!", username);
            Integer uid = userBaseInfoDto.getUid();
            // 更新登录时间
            userMapper.updateUserLatestLoginTime(uid);
            // 添加 cookie 维持用户登录状态
            response.addCookie(GeneratorCookie.newMoreProCookie(COOKIE_KEY_USER_ID, userBaseInfoDto.getUserId(), COOKIE_SURVIVAL_TIME, COOKIE_HTTP_ONLY, COOKIE_PATH));
            HttpSession session = request.getSession();
            session.setAttribute("uid", uid);
            session.setAttribute("userId", userBaseInfoDto.getUserId());
            session.setAttribute("nickname", userBaseInfoDto.getUsername());
            session.setMaxInactiveInterval(SESSION_SURVIVAL_TIME);
        }
    }

    @Override
    public void logoutHuadiao(Cookie cookie, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        Object userId = session.getAttribute("userId");
        Object nickname = session.getAttribute("nickname");
        log.info("uid 为 {}, nickname 为 {}, userId 为 {} 的用户尝试退出登录", uid, nickname, userId);
        // 销毁 session
        session.invalidate();
        // 删除 cookie
        cookie.setMaxAge(0);
        log.info("uid 为 {}, nickname 为 {}, userId 为 {} 的用户退出登录成功", uid, nickname, userId);
    }

    @Override
    public void getCheckCode(HttpSession session, HttpServletResponse response) throws Exception {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(CODE_IMAGE_WIDTH, CODE_IMAGE_HEIGHT, CODE_LENGTH, CODE_DISTURB_COUNT);
        circleCaptcha.write(response.getOutputStream());
        String code = circleCaptcha.getCode();
        session.setAttribute("checkCode", code);
        log.debug("用户获取验证码为 {}", code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String registerHuadiao(HttpSession session, String username, String password, String confirmPassword, String checkCode) throws Exception {
        log.info("用户尝试注册花凋账号, 用户名 {}, 验证码 {}", username, checkCode);
        if (username == null) {
            return NULL_USERNAME;
        } else if (password == null) {
            return NULL_PASSWORD;
        } else if (checkCode == null) {
            return NULL_CHECK_CODE;
        }
        // 检查用户名
        String checkUsername = checkUsername(username);
        if (checkUsername != null) {
            log.debug("用户输入的用户名不符合要求, 为 {}, 错误标识为 {}", username, checkUsername);
            return checkUsername;
        }
        // 检查密码
        String checkPassword = checkPassword(password, confirmPassword);
        if (checkPassword != null) {
            log.debug("用户输入的密码不符合要求, 第一次密码为 {}, 第二次密码为 {}, 错误标识为 {}", password, confirmPassword, checkPassword);
            return checkPassword;
        }
        // 检查验证码
        Object code = session.getAttribute("checkCode");
        if (!checkCode.equals(code)) {
            log.debug("用户输入的验证码不一致, 正确的验证码为 {}, 用户输入的验证码为 {}", code, checkCode);
            return WRONG_CODE;
        }

        // 检查用户名是否重复
        UserBaseInfoDto userBaseInfoDto = userMapper.selectUserByUsername(username);
        if (userBaseInfoDto != null) {
            log.debug("用户名输入的用户名已存在, 用户名为 {}", username);
            return SAME_USERNAME;
        }
        log.debug("用户通过所有的注册检查! 下面开始注册新花凋用户");
        boolean end = false;
        String userId = CreateUserId.createUserId();
        Integer uid;
        while (!end) {
            try {
                // 新增花凋用户
                userMapper.insertNewHuadiaoUser(userId, username, password);
                end = true;
            } catch (Exception e) {
                log.trace("为新用户创建的 userId {} 重复, 准备再次创建", userId);
                userId = CreateUserId.createUserId();
            }
        }
        // 获取新增的用户的 uid
        uid = userMapper.selectUidByUserId(userId);
        // 新增用户信息
        insertOrUpdateUserInfo(uid, userId, userId, DEFAULT_USER_CANVASES, DEFAULT_USER_SEX, DEFAULT_USER_BORN_DATE, DEFAULT_USER_SCHOOL);
        log.info("用户 nickname: {}, userId: {} 注册成功", username, userId);
        return SUCCEED_REGISTER;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOrUpdateUserInfo(Integer uid, String userId, String nickname, String canvases, String sex, String bornDate, String school) throws Exception {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增或修改自己的用户信息", uid, userId);
        userInfoMapper.insertOrUpdateUserInfoByUid(uid, nickname, canvases, sex, bornDate, school);
        log.debug("uid, userId 分别为 {}, {} 的用户新增或修改自己的用户信息成功, (nickname: {}, canvases: {}, sex: {}, bornDate: {}, school： {})", uid, userId, nickname, canvases, sex, bornDate, school);
    }

    @Override
    public UserDetailDto getUserInfo(Integer uid) throws Exception {
        UserShareDto userShareDto = userMapper.selectUserShareDtoByUid(uid);
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取自己的用户信息", uid, userShareDto.getUserId());
        UserDetailDto userDetailDto = userInfoMapper.selectUserInfoByUid(uid);
        List<Integer> list = followFanMapper.countFollowAndFansByUid(uid);
        userDetailDto.setUid(userShareDto.getUid());
        userDetailDto.setUserId(userShareDto.getUserId());
        userDetailDto.setNickname(userShareDto.getNickname());
        userDetailDto.setUserAvatar(userShareDto.getUserAvatar());
        userDetailDto.setFollow(list.get(AbstractFollowFanService.FOLLOW_INDEX));
        userDetailDto.setFan(list.get(AbstractFollowFanService.FAN_INDEX));
        userDetailDto.setLogin(true);
        log.debug("uid, userId 分别为 {}, {} 的用户获取了自己的用户信息 {}", uid, userShareDto.getUserId(), userDetailDto);
        return userDetailDto;
    }

}
