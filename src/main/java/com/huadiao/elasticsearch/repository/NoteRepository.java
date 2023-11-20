package com.huadiao.elasticsearch.repository;

import com.huadiao.entity.elasticsearch.NoteEs;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
@Repository
public interface NoteRepository extends ElasticsearchRepository<NoteEs, Integer> {

    /**
     * 根据 noteId 获取笔记
     *
     * @param title    笔记标题
     * @param pageable 分页规则
     * @return 返回匹配的笔记
     */
    List<NoteEs> findByTitle(String title, Pageable pageable);

    /**
     * 根据 summary 获取笔记
     *
     * @param summary  笔记摘要
     * @param pageable 分页规则
     * @return 返回匹配的笔记
     */
    List<NoteEs> findBySummary(String summary, Pageable pageable);
}
