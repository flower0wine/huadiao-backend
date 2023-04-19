package com.huadiao.service.impl;

import com.huadiao.entity.dto.userinfodto.UserInfoDto;
import com.huadiao.mapper.FollowFanMapper;
import com.huadiao.mapper.UserInfoMapper;
import com.huadiao.mapper.UserMapper;
import com.huadiao.service.AbstractUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 业务层: 与用户信息相关的操作的实现类
 */
@Slf4j
@Service
public class UserInfoServiceIml extends AbstractUserInfoService {
    private UserInfoMapper userInfoMapper;
    private UserMapper userMapper;
    private FollowFanMapper followFanMapper;

    @Autowired
    public UserInfoServiceIml(UserInfoMapper userInfoMapper, UserMapper userMapper, FollowFanMapper followFanMapper) {
        this.userInfoMapper = userInfoMapper;
        this.userMapper = userMapper;
        this.followFanMapper = followFanMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertOrUpdateUserInfo(Integer uid, String userId, String nickname, String canvases, String sex, String bornDate, String school) throws Exception {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增或修改自己的用户信息 (nickname: {}, canvases: {}, sex: {}, bornDate: {}, school: {})", uid, userId, nickname, canvases, sex, bornDate, school);
        if (bornDate == null || "".equals(bornDate.trim())) {
            bornDate = "";
        } else {
            // 检查出生日期是否符合要求
            Matcher matcher = bornDateReg.matcher(bornDate);
            if (!matcher.matches()) {
                log.trace("uid, userId 分别为 {}, {} 的用户的出生日期不符合要求, bornDate: {}", uid, userId, bornDate);
                return WRONG_BORN_DATE;
            }
        }
        if (nickname == null) {
            return WRONG_NULL_NICKNAME;
        }
        // 左右空格不计入字数
        nickname = nickname.trim();
        if("".equals(nickname)) {
            return WRONG_NULL_NICKNAME;
        }
        if(nickname.length() > NICKNAME_MAX_LENGTH) {
            return WRONG_LENGTH_NICKNAME;
        }
        // 个人简介检查
        if(canvases != null) {
            canvases = canvases.trim();
            if(canvases.length() > CANVASES_MAX_LENGTH) {
                return WRONG_LENGTH_CANVASES;
            }
        }
        // 性别只能三选一
        if (!DEFAULT_USER_SEX.equals(sex) && !SEX_MAN.equals(sex) && !SEX_WOMEN.equals(sex)) {
            return WRONG_SEX;
        }
        // 学校检查
        if(school != null) {
            school = school.trim();
            if(school.length() > SCHOOL_MAX_LENGTH) {
                return WRONG_LENGTH_SCHOOL;
            }
        }
        userInfoMapper.insertOrUpdateUserInfoByUid(uid, nickname, canvases, sex, bornDate, school);
        log.debug("uid, userId 分别为 {}, {} 的用户新增或修改自己的用户信息成功, (nickname: {}, canvases: {}, sex: {}, bornDate: {}, school： {})", uid, userId, nickname, canvases, sex, bornDate, school);
        return null;
    }

    @Override
    public UserInfoDto getUserInfo(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户获取了自己的用户信息", uid, userId);
        return userInfoMapper.selectUserInfoByUid(uid);
    }



}
