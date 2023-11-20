package com.huadiao.elasticsearch.repository;

import com.huadiao.entity.elasticsearch.NoteHistoryEs;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
@Repository
public interface NoteHistoryRepository extends ElasticsearchRepository<NoteHistoryEs, Integer> {

    /**
     * 根据 笔记标题 获取笔记历史
     *
     * @param noteTitle 笔记标题
     * @param uid       用户 uid
     * @param pageable  分页规则
     * @return 返回匹配的笔记历史
     */
    List<NoteHistoryEs> findByNoteTitleAndUid(String noteTitle, Integer uid, Pageable pageable);

    /**
     * 根据 uid 查找笔记访问记录
     * @param uid 用户 uid
     * @param pageable 分页规则
     * @return 返回匹配的记录
     */
    List<NoteHistoryEs> findAllByUid(Integer uid, Pageable pageable);

    /**
     * 删除用户的所有笔记访问记录
     * @param uid 用户 uid
     */
    void deleteAllByUid(Integer uid);

    /**
     * 删除某个用户指定的笔记访问记录, compositionId 生成方法使用以下静态方法
     * {@link NoteHistoryEs#generateCompositionId(java.lang.Integer, java.lang.Integer)}
     * @param compositionId 用户 uid 和 笔记 id 的组合
     */
    void deleteByCompositionId(String compositionId);

}
