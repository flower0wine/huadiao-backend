package com.huadiao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户个人数据
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    private Integer uid;
    private String userId;
    private String username;
    /**
     * 是否是官方号, 企业号, 政府号, 普通账号
     */
    private Boolean huadiaoAuthority;
    private Boolean enterprise;
    private Boolean government;
    private Boolean common;
    /**
     * 其他登录方式, 手机号, QQ, 微信, github, gitee, facebook, twitter
     */
    private String phoneNumber;
    private String qqNumber;
    private String weChatNumber;
    private String githubNumber;
    private String giteeNumber;
    private String facebookNumber;
    private String twitterNumber;
    /**
     * 最近一次修改昵称的时间, 及修改的昵称
     */
    private String beforeUsername;
    private Date updateUsernameDate;
    /**
     * 关注和粉丝数量
     */
    private Integer follow;
    private Integer fan;
    /**
     * 其他信息
     */
    private String nickname;
    private String userAvatar;
    private String canvases;
    private String school;
    private Date bornDate;
    /**
     * 最近一次登录时间, 登录 ipv4, 登陆设备, 当前登录方式
     */
    private Date latestLoginTime;
    private String latestLoginIpv4;
    private String latestLoginDevice;
    private String latestLoginType;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 是否注销, 及注销时间
     */
    private Boolean logoff;
    private Date logoffDate;
    /**
     * 三种情况: 0 (未知), 1 (男), 2 (女)
     */
    private String sex;
    /**
     * 账号封禁: 是否封禁, 封禁起始日期, 结束日期
     */
    private Boolean accountBan;
    private Date accountBanBeginDate;
    private Date accountBanEndDate;
    /**
     * 账号禁言(不能发表笔记, 番剧, 评论等, 账号一切交流均禁用): 是否禁言, 禁言起始日期, 禁言终止日期
     */
    private Boolean accountProhibition;
    private Date accountProhibitionBeginDate;
    private Date accountProhibitionEndDate;
}
