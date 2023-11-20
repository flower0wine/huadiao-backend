package com.huadiao.entity.elasticsearch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Setting(shards = 1, replicas = 1)
@Document(indexName = "note")
public class NoteEs {
    @Id
    @Field(type = FieldType.Integer, index = false)
    private Integer noteId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String summary;

    @Field(type = FieldType.Date, index = false)
    private Date time;

    @Field(type = FieldType.Integer, index = false)
    private Integer authorUid;

    private UserEs author;
}
