package com.huadiao.elasticsearch.repository;

import com.huadiao.entity.elasticsearch.Anime;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
@Repository
public interface AnimeRepository extends ElasticsearchRepository<Anime, Integer> {
}
