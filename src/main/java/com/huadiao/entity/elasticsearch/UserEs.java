package com.huadiao.entity.elasticsearch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@Document(indexName = "user")
@Setting(shards = 1, replicas = 1)
public class UserEs {
    @Id
    @Field(type = FieldType.Integer)
    private Integer uid;

    @Field(type = FieldType.Keyword)
    private String userId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String nickname;

    @Field(type = FieldType.Text, index = false)
    private String canvases;

    @Field(type = FieldType.Text, index = false)
    private String avatar;

}
