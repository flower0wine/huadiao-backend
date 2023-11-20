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
@Document(indexName = "note_history")
@Setting(shards = 1, replicas = 1)
public class NoteHistoryEs {
    /**
     * 复合主键, uid 和 noteId 组成
     */
    @Id
    @Field(type = FieldType.Integer, index = false)
    private String compositionId;
    @Field(type = FieldType.Keyword)
    private Integer uid;
    @Field(type = FieldType.Integer, index = false)
    private Integer noteId;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String noteTitle;
    @Field(type = FieldType.Integer, index = false)
    private Integer authorUid;
    @Field(type = FieldType.Date, index = false)
    private Date time;

    public void setCompositionId(Integer uid, Integer noteId) {
        this.compositionId = generateCompositionId(uid, noteId);
    }

    /**
     * 生成 compositionId
     * @param uid 用户 uid
     * @param noteId 笔记 id
     * @return 返回生成的 id
     */
    public static String generateCompositionId(Integer uid, Integer noteId) {
        return uid + "_" + noteId;
    }
}
