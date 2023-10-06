package com.huadiao.service.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.SexEnum;
import com.huadiao.entity.dto.userinfodto.UserInfoDto;
import com.huadiao.mapper.FollowFanMapper;
import com.huadiao.mapper.UserInfoMapper;
import com.huadiao.mapper.UserMapper;
import com.huadiao.service.AbstractUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    public Result<?> insertOrUpdateUserInfo(Integer uid, String userId, String nickname, String canvases, String sex, Date bornDate, String school) throws Exception {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增或修改自己的用户信息 (nickname: {}, canvases: {}, sex: {}, bornDate: {}, school: {})", uid, userId, nickname, canvases, sex, bornDate, school);
        if (nickname == null) {
            return Result.blankParam();
        } else {
            // 左右空格不计入字数
            if("".equals(nickname.trim())) {
                return Result.blankParam();
            }
        }
        if(nickname.length() > NICKNAME_MAX_LENGTH) {
            return Result.errorParam();
        }
        // 个人简介检查
        if(canvases != null) {
            canvases = canvases.trim();
            if(canvases.length() > CANVASES_MAX_LENGTH) {
                return Result.errorParam();
            }
        }
        SexEnum sexEnum = SexEnum.match(sex);
        // 性别只能三选一
        if (!SexEnum.NO_KNOWN.equals(sexEnum) && !SexEnum.MAN.equals(sexEnum) && !SexEnum.WOMEN.equals(sexEnum)) {
            return Result.errorParam();
        }
        // 学校检查
        if(school != null) {
            school = school.trim();
            if(school.length() > SCHOOL_MAX_LENGTH) {
                return Result.errorParam();
            }
        }
        userInfoJedisUtil.modifyUserInfoByUid(uid, nickname, canvases, sex, bornDate, school);
        log.debug("uid, userId 分别为 {}, {} 的用户新增或修改自己的用户信息成功, (nickname: {}, canvases: {}, sex: {}, bornDate: {}, school： {})", uid, userId, nickname, canvases, sex, bornDate, school);
        return Result.ok(null);
    }

    @Override
    public Result<?> getMineInfo(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户获取了自己的用户信息", uid, userId);
        UserInfoDto userInfoDto = userInfoMapper.selectUserInfoByUid(uid);
        return Result.ok(userInfoDto);
    }

    @Override
    public Result<?> getUserInfo(Integer uid, String userId, Integer viewedUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取用户 {} 的用户信息", uid, userId, viewedUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取用户 {} 的用户信息", uid, userId, viewedUid);
        return null;
    }
}
