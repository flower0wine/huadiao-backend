package com.huadiao.service.elasticsearch.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.elasticsearch.UserEs;
import com.huadiao.service.elasticsearch.AbstractUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
@Slf4j
@Service
public class UserEsServiceImpl extends AbstractUserService {

    @Override
    public Result<?> findUserByNickname(Integer uid, String userId, String searchNickname, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试查询用户信息, searchNickname: {}, offset: {}, row: {}", uid, userId, searchNickname, offset, row);
        if (searchNickname == null || searchNickname.isEmpty()) {
            log.debug("uid, userId 分别为 {}, {} 的用户查询用户信息, searchNickname 为空", uid, userId);
            return Result.blankParam();
        }
        Result<?> result = checkOffsetAndRow(offset, row, (o, r) -> {
            Pageable pageable = PageRequest.of(o, r);
            List<UserEs> userEsList = userRepository.findByNickname(searchNickname, pageable);
            return Result.ok(userEsList);
        });
        if (result.succeed()) {
            log.debug("uid, userId 分别为 {}, {} 的用户查询用户信息, searchNickname 为 {} 的用户信息查询成功, offset: {}, row: {}", uid, userId, searchNickname, offset, row);
        }
        return result;
    }

    @Override
    public Result<?> findUserByUserId(Integer uid, String userId, String searchUserId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试查询用户信息, searchUserId: {}", uid, userId, searchUserId);
        if (searchUserId == null || searchUserId.isEmpty()) {
            log.debug("uid, userId 分别为 {}, {} 的用户查询用户信息, searchUserId 为空", uid, userId);
            return Result.blankParam();
        }
        Optional<UserEs> optionalUser = userRepository.findUserByUserId(searchUserId);
        List<UserEs> list = new ArrayList<>();
        optionalUser.ifPresent(list::add);
        log.debug("uid, userId 分别为 {}, {} 的用户查询用户信息, searchUserId 为 {} 的用户信息查询成功", uid, userId, searchUserId);
        return Result.ok(list);
    }
}
