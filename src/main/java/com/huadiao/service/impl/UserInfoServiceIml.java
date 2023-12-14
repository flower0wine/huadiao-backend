package com.huadiao.service.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.SexEnum;
import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.entity.dto.userdto.UserShareDto;
import com.huadiao.entity.dto.userinfodto.UserInfoDto;
import com.huadiao.entity.elasticsearch.UserEs;
import com.huadiao.mapper.FollowFanMapper;
import com.huadiao.mapper.UserInfoMapper;
import com.huadiao.mapper.UserMapper;
import com.huadiao.service.AbstractUserInfoService;
import com.huadiao.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.repository.config.CustomRepositoryImplementationDetector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public Result<?> insertOrUpdateUserInfo(Integer uid, String userId, String nickname, String canvases, String sex, Long bornDate, String school) throws Exception {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增或修改自己的用户信息 (nickname: {}, canvases: {}, sex: {}, bornDate: {}, school: {})", uid, userId, nickname, canvases, sex, bornDate, school);
        if (nickname == null || "".equals(nickname.trim())) {
            return Result.errorParam(nullNickname);
        }
        if (nickname.length() > NICKNAME_MAX_LENGTH) {
            return Result.errorParam(wrongNicknameLength);
        }
        // 个人简介检查
        if (canvases != null) {
            canvases = canvases.trim();
            if (canvases.length() > CANVASES_MAX_LENGTH) {
                return Result.errorParam(wrongNicknameLength);
            }
        }
        if (sex != null) {
            SexEnum sexEnum = SexEnum.match(sex);
            // 性别只能三选一
            if (!SexEnum.NO_KNOWN.equals(sexEnum) && !SexEnum.MAN.equals(sexEnum) && !SexEnum.WOMEN.equals(sexEnum)) {
                return Result.errorParam(wrongSex);
            }
        }
        Date date = null;
        // 检查日期
        if (bornDate != null) {
            date = new Date(bornDate);
            if (date.getTime() > System.currentTimeMillis()) {
                return Result.errorParam(wrongBornDate);
            }
        }
        // 学校检查
        if (school != null) {
            school = school.trim();
            if (school.length() > SCHOOL_MAX_LENGTH) {
                return Result.errorParam(wrongSchoolLength);
            }
        }
        userInfoJedisUtil.modifyUserInfoByUid(uid, nickname, canvases, sex, date, school);
        // 修改 elasticsearch 中的用户信息
        Optional<UserEs> optionalUserEs = userRepository.findById(uid);
        UserEs sourceUserEs;
        UserEs userEs = new UserEs();
        userEs.setUid(uid);
        userEs.setNickname(nickname);
        userEs.setCanvases(canvases);
        if (optionalUserEs.isPresent()) {
            sourceUserEs = optionalUserEs.get();
            BeanUtil.moveProperties(userEs, sourceUserEs);
        }
        userRepository.save(userEs);
        log.debug("uid, userId 分别为 {}, {} 的用户新增或修改自己的用户信息成功, nickname: {}, canvases: {}, sex: {}, bornDate: {}, school： {}", uid, userId, nickname, canvases, sex, bornDate, school);
        return Result.ok(null);
    }

    @Override
    public Result<?> getUserInfo(Integer myUid, Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的用户信息", myUid, userId, uid);
        Integer tempUid = uid == null ? myUid : uid;
        UserAbstractDto userAbstractDto = userInfoJedisUtil.getUserAbstractDto(tempUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取 uid 为 {} 的用户信息", myUid, userId, uid);

        if (uid == null) {
            return Result.ok(userAbstractDto);
        } else {
            Map<String, Object> map = new HashMap<>(4);
            map.put("userInfo", userAbstractDto);
            map.put("me", myUid.equals(uid));
            return Result.ok(map);
        }
    }


    @Override
    public UserShareDto getUserShareInfo(Integer uid) {
        log.debug("uid 为 {} 的用户尝试获取共享信息", uid);
        UserShareDto userShareDto = userMapper.selectUserShareDtoByUid(uid);
        log.debug("uid 为 {} 的用户成功获取共享信息, userShareDto: {}", uid, userShareDto);
        return userShareDto;
    }

}
