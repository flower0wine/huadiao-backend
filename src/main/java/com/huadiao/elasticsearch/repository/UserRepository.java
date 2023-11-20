package com.huadiao.elasticsearch.repository;

import com.huadiao.entity.elasticsearch.UserEs;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
@Repository
public interface UserRepository extends ElasticsearchRepository<UserEs, Integer> {

    /**
     * 根据 昵称 分词查找用户
     * @param nickname 昵称
     * @param pageable 分页信息
     * @return 返回匹配的用户
     */
    List<UserEs> findByNickname(String nickname, Pageable pageable);

    /**
     * 根据 userid 获取 用户信息
     * @param userId 用户 id
     * @return 返回匹配的用户
     */
    Optional<UserEs> findUserByUserId(String userId);

}
