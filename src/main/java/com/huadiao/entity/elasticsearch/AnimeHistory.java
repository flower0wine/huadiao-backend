package com.huadiao.entity.elasticsearch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@Document(indexName = "anime_history")
@Setting(shards = 1, replicas = 1)
public class AnimeHistory {
    @Id
    @Field(type = FieldType.Integer, index = false)
    private Integer animeId;
}
