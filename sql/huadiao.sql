#
#  __  __     __  __     ______     _____     __     ______     ______
#  /\ \_\ \   /\ \/\ \   /\  __ \   /\  __-.  /\ \   /\  __ \   /\  __ \
#  \ \  __ \  \ \ \_\ \  \ \  __ \  \ \ \/\ \ \ \ \  \ \  __ \  \ \ \/\ \
#   \ \_\ \_\  \ \_____\  \ \_\ \_\  \ \____-  \ \_\  \ \_\ \_\  \ \_____\
#    \/_/\/_/   \/_____/   \/_/\/_/   \/____/   \/_/   \/_/\/_/   \/_____/
#
# 下面是创建花凋数据库表以及相关的对表相关的操作

CREATE DATABASE `huadiao`
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS
`huadiao_user_base`(
    `uid` INT AUTO_INCREMENT COMMENT '用户唯一标识之一',
    `user_id` CHAR(20) UNIQUE NOT NULL COMMENT '用户唯一标识之一',
    `username` VARCHAR(20) UNIQUE COMMENT '用户唯一标识之一(用户名), 可以用于登录',
    `login_type` TINYINT NOT NULL DEFAULT 0 COMMENT '登录类型, 0 为本站账号登录',
    `oauth_id` LONG COMMENT '用户唯一标识之一(第三方账号ID), 用于绑定第三方账号',
    `access_token` VARCHAR(255) COMMENT '用户唯一标识之一(第三方账号访问令牌), 用于绑定第三方账号',
    `account_type` TINYINT NOT NULL DEFAULT 0 COMMENT '账号类型, 0 为普通用户, 1 为官方, 2 为企业, 3 为政府',
    `latest_username` VARCHAR(20) COMMENT '用户最近使用过的用户名',
    `update_username_date` DATETIME COMMENT '用户修改用户名的时间',
    `password` CHAR(32) COMMENT '用户密码',
    `latest_password` CHAR(32) COMMENT '用户最近使用过的密码',
    `update_password_date` DATETIME COMMENT '用户最近修改密码的时间',
    PRIMARY KEY (`uid`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' AUTO_INCREMENT = 1 COMMENT '花凋用户基本信息';

CREATE TABLE IF NOT EXISTS
`huadiao_login_register`(
    `uid` INT NOT NULL COMMENT '用户唯一标识之一',
    `latest_login_time` DATETIME COMMENT '最近登录时间',
    `latest_login_ipv4` INT COMMENT '最近登录的 ipv4',
    `latest_login_device` VARCHAR(200) COMMENT '最近一次登录时使用的设备',
    `latest_login_type` TINYINT COMMENT '最近一次登录使用的方式, 0 为用户名密码登录, 1 为QQ登录, 2 为微信登录, 3 为GitHub 登录, 4 为 Gitee 登录, 5 为 FaceBook 登录, 6 为 Twitter 登录',
    `register_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '注册时间',
    PRIMARY KEY (`uid`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '用户登录注册相关的信息';

CREATE TABLE IF NOT EXISTS
`huadiao_account_status`(
    `uid` INT NOT NULL COMMENT '用户唯一标识之一',
    `account_ban` BIT(1) NOT NULL DEFAULT 0 COMMENT '账号是否封禁, 0 为未封禁, 1 为封禁',
    `account_ban_begin_date` DATETIME COMMENT '账号封禁时间',
    `account_ban_end_date` DATETIME COMMENT '封禁解决时间',
    `account_prohibition` BIT(1) NOT NULL DEFAULT 0 COMMENT '用户是否可以对外发布消息, 即禁言, 0 为 未被禁言, 1 为已禁言',
    `account_prohibition_begin_date` DATETIME COMMENT '禁言开始时间',
    `account_prohibition_end_date` DATETIME COMMENT '禁言结束时间',
    PRIMARY KEY (`uid`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '用户账号状态, 如禁言、封禁';

CREATE TABLE IF NOT EXISTS
`huadiao_relation`(
    `id` INT NOT NULL AUTO_INCREMENT COMMENT '自增 id',
    `uid` INT NOT NULL COMMENT '用户 uid, 被关注',
    `fan_uid` INT NOT NULL COMMENT '粉丝的 uid',
    `status` BIT NOT NULL DEFAULT 1 COMMENT '关注状态, 0 为未关注, 1 为已关注',
    `latest_message_status` BIT NOT NULL DEFAULT 1 COMMENT '是否在最近消息中, 1 为存在',
    `latest_follow_date` DATETIME NOT NULL DEFAULT NOW() COMMENT '最近一次关注时间',
    `group_id` INT NOT NULL COMMENT '关注的人所在分组 id',
    PRIMARY KEY (`id`),
    INDEX `huadiao_relation_index`(`uid`, `fan_uid`, `group_id`),
    UNIQUE (`uid`, `fan_uid`, `group_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '用户关系表, 关注、粉丝';

CREATE TABLE IF NOT EXISTS
`huadiao_relation_group`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT COMMENT '用户 uid',
    `group_id` INT UNIQUE NOT NULL COMMENT '分组 id, 不同的用户可以有相同的 groupId',
    `group_name` VARCHAR(16) NOT NULL COMMENT '组名',
    `create_date` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建日期',
    `status` BIT(1) NOT NULL DEFAULT 1 COMMENT '是否删除, 1 为未删除, 0 为已删除',
    PRIMARY KEY (`id`),
    INDEX `huadiao_relation_group_index`(`uid`, `group_id`, `group_name`, `create_date`),
    UNIQUE (`group_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '用户关系分组';

CREATE TABLE IF NOT EXISTS
`huadiao_user_info`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '用户 uid',
    `nickname` VARCHAR(20) COMMENT '用户昵称',
    `canvases` VARCHAR(50) COMMENT '用户简介',
    `sex` CHAR(1) NOT NULL DEFAULT 0 COMMENT '用户性别, 0 为未知, 1 为男性, 2 为女性',
    `born_date` DATE COMMENT '用户出生日期',
    `school` VARCHAR(30) COMMENT '用户学校',
    `update_time` DATETIME COMMENT '更新信息时间',
    PRIMARY KEY (`id`),
    INDEX `huadiao_user_info_index`(`uid`, `nickname`, `canvases`, `sex`, `born_date`, `school`),
    UNIQUE (`uid`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '用户信息表';

CREATE TABLE IF NOT EXISTS
`huadiao_user_settings`(
    `uid` INT NOT NULL COMMENT '用户 uid',
    `public_star_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '公开收藏',
    `public_born_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '公开出生日期',
    `public_fanju_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '公开番剧',
    `public_note_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '公开笔记',
    `public_school_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '公开学校',
    `public_follow_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '公开关注',
    `public_fan_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '公开粉丝',
    `public_canvases_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '公开个人简介',
    `public_homepage_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '公开个人主页',
    `message_remind_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '消息提醒',
    `message_reply_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '消息回复提醒',
    `message_like_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '收到喜欢提醒',
    PRIMARY KEY (`uid`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '用户账号设置';

CREATE TABLE IF NOT EXISTS
`huadiao_homepage`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '用户 uid',
    `user_avatar` VARCHAR(128) COMMENT '用户头像链接',
    `avatar_change_date` DATETIME COMMENT '用户头像更换时间',
    `homepage_background` VARCHAR(128) COMMENT '用户个人主页背景',
    `background_change_date` DATETIME COMMENT '个人主页背景更换时间',
    PRIMARY KEY (`id`),
    INDEX `huadiao_homepage_index`(`uid`, `user_avatar`, `homepage_background`),
    UNIQUE (`uid`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '用户个人主页信息';

CREATE TABLE IF NOT EXISTS
`huadiao_homepage_visit`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '访问者 uid',
    `visit_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '访问时间',
    `viewed_uid` INT NOT NULL COMMENT '被访问者 uid',
    `status` BIT(1) NOT NULL DEFAULT 1 COMMENT '是否删除, 1 为未删除, 0 为已删除',
    PRIMARY KEY (`id`),
    INDEX `huadiao_homepage_visit_index`(`uid`, `visit_time`, `viewed_uid`, `status`),
    UNIQUE (`uid`, `viewed_uid`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '个人主页访问记录';

CREATE TABLE IF NOT EXISTS
`huadiao_note`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `note_id` INT NOT NULL COMMENT '笔记 id',
    `uid` INT NOT NULL COMMENT '用户 uid',
    `note_title` VARCHAR(100) NOT NULL COMMENT '笔记标题',
    `note_abstract` VARCHAR(300) NOT NULL COMMENT '笔记摘要',
    `note_content` TEXT NOT NULL COMMENT '笔记内容',
    `publish_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '发布时间',
    `status` BIT(1) NOT NULL DEFAULT 1 COMMENT '是否删除, 0 为已删除, 1 为未删除',
    `modify_date` DATETIME COMMENT '修改日期',
    `modify_times` TINYINT NOT NULL DEFAULT 0 COMMENT '修改次数',
    PRIMARY KEY (`id`),
    UNIQUE (`note_id`),
    INDEX `huadiao_note_index`(`note_id`, `uid`, `publish_time`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '用户笔记';

CREATE TABLE IF NOT EXISTS
`huadiao_note_view`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '访问笔记的用户 uid',
    `author_uid` INT NOT NULL COMMENT '作者 uid',
    `note_id` INT NOT NULL COMMENT '笔记 id',
    `status` BIT(1) NOT NULL DEFAULT 1 COMMENT '访问笔记记录的状态, 1 为未删除, 0 为已删除',
    `view_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '查看时间',
    PRIMARY KEY(`id`),
    INDEX `huadiao_note_view_index`(`uid`, `author_uid`, `note_id`, `view_time`),
    UNIQUE (`uid`, `author_uid`, `note_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '用户笔记访问记录';

CREATE TABLE IF NOT EXISTS
`huadiao_note_like`(
    `id` INT NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '点赞者 uid',
    `reply_uid` INT NOT NULL DEFAULT 0 COMMENT '回复者 uid',
    `note_id` INT NOT NULL COMMENT '笔记 id',
    `author_uid` INT NOT NULL COMMENT '作者 uid',
    `replied_uid` INT NOT NULL DEFAULT 0 COMMENT '被回复者 uid',
    `root_comment_id` INT NOT NULL DEFAULT 0 COMMENT '根评论 id',
    `sub_comment_id` INT NOT NULL DEFAULT 0 COMMENT '子评论 id',
    `like_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '点赞时间',
    `status` BIT NOT NULL DEFAULT 1 COMMENT '状态(1:有效,0:无效)',
    `message_status` BIT NOT NULL DEFAULT 1 COMMENT '消息状态(1:有效,0:无效)',
    PRIMARY KEY (`id`),
    UNIQUE (`uid`, `reply_uid`, `note_id`, `author_uid`, `replied_uid`, `root_comment_id`, `sub_comment_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT = '点赞表(点赞评论或笔记)';

CREATE TABLE IF NOT EXISTS
`huadiao_note_unlike`(
    `uid` INT NOT NULL COMMENT '不喜欢笔记的用户 uid',
    `author_uid` INT NOT NULL COMMENT '作者 uid',
    `note_id` INT NOT NULL COMMENT '笔记 id',
    `status` BIT(1) NOT NULL DEFAULT 1 COMMENT '不喜欢笔记记录的状态, 1 为未删除, 0 为已删除',
    `unlike_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '不喜欢时间',
    PRIMARY KEY(`uid`, `author_uid`, `note_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '用户笔记不喜欢记录';

CREATE TABLE IF NOT EXISTS
`huadiao_note_star`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '收藏笔记的用户 uid',
    `author_uid` INT NOT NULL COMMENT '笔记作者的 uid',
    `note_id` INT NOT NULL COMMENT '笔记唯一标识',
    `star_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '收藏时间',
    `group_id` INT NOT NULL COMMENT '分组 id, 为 -1 时则为默认分组',
    `status` BIT(1) NOT NULL DEFAULT 1 COMMENT '收藏状态, 1 为收藏中, 0 为取消收藏',
    `cancel_star_time` DATETIME COMMENT '取消收藏时间',
    PRIMARY KEY (`id`),
    INDEX `huadiao_note_star_index`(`uid`, `author_uid`, `note_id`, `group_id`),
    UNIQUE (`uid`, `author_uid`, `note_id`, `group_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '用户笔记收藏';

CREATE TABLE IF NOT EXISTS
`huadiao_note_history`(
    `uid` INT NOT NULL COMMENT '访问笔记的用户的 uid',
    `note_id` INT NOT NULL COMMENT '笔记唯一标识',
    `author_uid` INT NOT NULL COMMENT '笔记作者 uid',
    `view_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '访问笔记时间',
    `status` BIT(1) NOT NULL DEFAULT 1 COMMENT '访问笔记记录的状态, 1 为未删除, 0 为已删除',
    `delete_time` DATETIME COMMENT '删除访问记录的时间',
    PRIMARY KEY (`uid`, `note_id`, `author_uid`)
) ENGINE = 'Innodb' CHARSET = 'utf8mb4' COMMENT '用户笔记访问历史记录';

CREATE TABLE IF NOT EXISTS
`huadiao_note_comment`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '发布评论的用户 uid',
    `note_id` INT NOT NULL COMMENT '笔记唯一标识',
    `author_uid` INT NOT NULL COMMENT '笔记作者 uid',
    `replied_uid` INT NOT NULL COMMENT '被评论用户 uid',
    `comment_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '评论时间',
    `status` BIT(1) NOT NULL DEFAULT 1 COMMENT '评论状态, 1 为未删除, 0 为已删除',
    `read` BIT(1) NOT NULL DEFAULT 0 COMMENT '评论消息状态, 0 为未读, 1 为已读',
    `message_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '消息存活状态, 1 为存活',
    `comment_content` TEXT NOT NULL COMMENT '评论内容',
    `root_comment_id` INT NOT NULL COMMENT '根评论 id',
    `sub_comment_id` INT NOT NULL COMMENT '子评论 id, 当该评论为父评论时子评论 id 为 0',
    `delete_uid` INT COMMENT '删除评论的用户的 uid',
    `delete_time` DATETIME COMMENT '删除时间',
    PRIMARY KEY (`id`),
    UNIQUE (`uid`, `note_id`, `author_uid`, `replied_uid`, `root_comment_id`, `sub_comment_id`),
    INDEX `note_comment_index`(`author_uid`, `note_id`, `root_comment_id`, `sub_comment_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '笔记评论表';

CREATE TABLE IF NOT EXISTS
`huadiao_note_comment_like`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT ' 的用户',
    `note_id` INT NOT NULL COMMENT '笔记唯一标识',
    `author_uid` INT NOT NULL COMMENT '作者 uid',
    `root_comment_id` INT NOT NULL COMMENT '根评论 id',
    `sub_comment_id` INT NOT NULL DEFAULT 0 COMMENT '子评论, 为 0 则为根评论',
    `status` BIT(1) NOT NULL DEFAULT 1 COMMENT '是否删除, 1 为未删除, 0 为已删除',
    `read` BIT(1) NOT NULL DEFAULT 0 COMMENT '点赞消息状态, 1 为已读, 0 为未读',
    `message_status` BIT(1) NOT NULL DEFAULT 1 COMMENT '消息存活状态, 1 为存活',
    `like_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '点赞时间',
    PRIMARY KEY (`id`),
    UNIQUE (`uid`, `note_id`, `author_uid`, `root_comment_id`, `sub_comment_id`),
    INDEX `note_comment_like_index`(`uid`, `note_id`, `author_uid`, `root_comment_id`, `sub_comment_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '笔记评论点赞表';

CREATE TABLE IF NOT EXISTS
`huadiao_note_comment_unlike`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '不喜欢的用户',
    `note_id` INT NOT NULL COMMENT '笔记唯一标识',
    `author_uid` INT NOT NULL COMMENT '作者 uid',
    `root_comment_id` INT NOT NULL COMMENT '根评论 id',
    `sub_comment_id` INT NOT NULL DEFAULT 0 COMMENT '子评论, 为 0 则为根评论',
    `status` BIT(1) NOT NULL DEFAULT 1 COMMENT '是否删除, 1 为未删除, 0 为已删除',
    `unlike_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '不喜欢时间',
    PRIMARY KEY (`id`),
    UNIQUE (`uid`, `note_id`, `author_uid`, `root_comment_id`, `sub_comment_id`),
    INDEX `note_comment_unlike_index` (`note_id`, `author_uid`, `root_comment_id`, `sub_comment_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '笔记评论不喜欢表';

CREATE TABLE IF NOT EXISTS
`huadiao_note_comment_report`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '举报者 uid',
    `reported_uid` INT NOT NULL COMMENT '被举报者 uid',
    `note_id` INT NOT NULL COMMENT '评论所在的笔记 id',
    `author_uid` INT NOT NULL COMMENT '笔记作者 uid',
    `root_comment_id` INT NOT NULL COMMENT '父评论 id',
    `sub_comment_id` INT NOT NULL COMMENT '子评论 id, 不是子评论则为 0',
    `report_time` DATETIME NOT NULL COMMENT '举报时间',
    PRIMARY KEY(`id`),
    INDEX `comment_report_index`(`uid`, `reported_uid`, `note_id`, `author_uid`, `root_comment_id`, `sub_comment_id`),
    UNIQUE(`uid`, `reported_uid`, `note_id`, `author_uid`, `root_comment_id`, `sub_comment_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '举报 评论表';

CREATE TABLE IF NOT EXISTS
`huadiao_house_info`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '用户 uid',
    `title_color` VARCHAR(9) COMMENT '番剧页面标题文字颜色',
    `title_background` VARCHAR(64) COMMENT '番剧页面标题背景',
    `page_background` VARCHAR(200) COMMENT '番剧页面背景',
    `page_foreground` VARCHAR(200) COMMENT '番剧页面前景',
    `card_border` TINYINT COMMENT '番剧封面边框',
    `card_background` VARCHAR(200) COMMENT '添加番剧卡片背景',
    `modify_time` DATETIME COMMENT '修改时间',
    PRIMARY KEY (`id`),
    INDEX(`uid`, `title_color`, `title_background`, `page_background`, `page_foreground`, `card_border`, `card_background`),
    UNIQUE (`uid`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '番剧页面信息表';

CREATE TABLE IF NOT EXISTS
`huadiao_card_border_image`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `border_image_slice` VARCHAR(15) NOT NULL,
    `border_image_outset` VARCHAR(15) NOT NULL,
    `border_image_width` VARCHAR(15) NOT NULL,
    `border_image_source` VARCHAR(100) NOT NULL,
    `add_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '添加时间',
    PRIMARY KEY (`id`),
    INDEX(`border_image_outset`, `border_image_slice`, `border_image_width`, `border_image_source`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '封面边框表';

CREATE TABLE IF NOT EXISTS
`huadiao_house_anime`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '用户 uid',
    `anime_id` INT NOT NULL COMMENT '番剧 id',
    `anime_title` VARCHAR(15) NOT NULL COMMENT '番剧标题',
    `anime_add_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '添加时间',
    `anime_cover` VARCHAR(200) NOT NULL COMMENT '番剧封面',
    `status` BIT NOT NULL DEFAULT 1 COMMENT '存活状态, 1 存活, 0 已删除',
    PRIMARY KEY (`id`),
    INDEX(`uid`, `anime_id`, `anime_title`, `anime_add_time`, `anime_cover`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '番剧信息表';

CREATE TABLE IF NOT EXISTS
`huadiao_house_visit`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '访问者 uid',
    `viewed_uid` INT NOT NULL COMMENT '被访问者 uid',
    `status` BIT NOT NULL DEFAULT 1 COMMENT '存活状态, 1 为存活',
    `visit_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '访问时间',
    PRIMARY KEY (`id`),
    INDEX `huadiao_house_visit_index`(`uid`, `viewed_uid`, `visit_time`),
    UNIQUE (`uid`, `viewed_uid`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '番剧馆访问记录';

CREATE TABLE IF NOT EXISTS
`huadiao_note_star_group`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '用户 uid',
    `group_name` VARCHAR(16) NOT NULL COMMENT '收藏分组名称',
    `group_id` INT NOT NULL COMMENT '分组 ID',
    `group_description` VARCHAR(50) COMMENT '收藏分组描述',
    `public` BIT NOT NULL DEFAULT 1 COMMENT '是否公开',
    `status` BIT NOT NULL DEFAULT 1 COMMENT '存活状态, 1 为存活, 0 为删除',
    `create_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '分组创建时间',
    `modify_time` DATETIME COMMENT '修改时间',
    PRIMARY KEY (`id`),
    INDEX `huadiao_star_group_index`(`uid`, `group_name`, `group_description`, `create_time`),
    UNIQUE (`group_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '笔记收藏分组信息表';

CREATE TABLE IF NOT EXISTS
`huadiao_system_message`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `message_id` INT NOT NULL COMMENT '消息id',
    `admin_id` INT NOT NULL COMMENT '管理员id',
    `message_title` VARCHAR(255) NOT NULL COMMENT '消息标题',
    `message_content` TEXT NOT NULL COMMENT '系统消息内容',
    `send_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '发送时间',
    `form` TINYINT NOT NULL COMMENT '消息发送形式',
    PRIMARY KEY (`id`),
    INDEX `huadiao_system_message_index`(`message_id`, `send_time`, `form`),
    UNIQUE (`message_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '系统消息表';

CREATE TABLE IF NOT EXISTS
`huadiao_admin`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `admin_id` INT NOT NULL COMMENT '管理员id',
    `level` INT  NOT NULL COMMENT '管理员等级',
    `username` VARCHAR(20) NOT NULL COMMENT '管理员用户名',
    `password` CHAR(32) NOT NULL COMMENT '管理员密码',
    `nickname` VARCHAR(10) NOT NULL COMMENT '管理员昵称',
    `avatar` VARCHAR(255) NOT NULL COMMENT '管理员头像',
    `create_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `huadiao_admin_index`(`admin_id`, `create_time`, `level`, `nickname`, `avatar`, `username`),
    UNIQUE (`admin_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '管理员表';

CREATE TABLE IF NOT EXISTS
`huadiao_latest_user`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '用户 uid',
    `latest_uid` INT NOT NULL COMMENT '近期消息中的用户 uid, (未关注)',
    `status` BIT(1) NOT NULL DEFAULT 1 COMMENT '存活状态, 1 为存活',
    PRIMARY KEY (`id`),
    INDEX `huadiao_unfamiliar_user_index`(`uid`, `latest_uid`),
    UNIQUE (`uid`, `latest_uid`)
) ENGINE = 'InnoDB' DEFAULT CHARSET = 'utf8mb4' COMMENT = '近期消息用户表 (未关注)';

CREATE TABLE IF NOT EXISTS
`huadiao_whisper_message`(
    `id` INT AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '发信者 uid',
    `receive_uid` INT NOT NULL COMMENT '收信者 uid',
    `send_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '发送时间',
    `message_content` TINYTEXT NOT NULL COMMENT '消息内容',
    `message_id` INT NOT NULL COMMENT '消息 id',
    `status` BIT NOT NULL DEFAULT 1 COMMENT '存活状态, 1 为存活',
    `message_type` TINYINT NOT NULL COMMENT '消息类型',
    PRIMARY KEY (`id`),
    UNIQUE (`message_id`),
    INDEX `uid_index`(`uid`, `receive_uid`),
    INDEX `received_uid_index`(`receive_uid`, `uid`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '私信消息表';

CREATE TABLE IF NOT EXISTS
`huadiao_anime_video`(
    `id` INT NOT NULL COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '用户 uid',
    `filename` VARCHAR(42) NOT NULL COMMENT '文件名, uuid(36) + .mp4(4 ~ 5)',
    `upload_succeed` BIT NOT NULL COMMENT '是否上传成功',
    `size` LONG NOT NULL COMMENT '文件大小',
    `upload_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '未上传成功为开始上传的时间, 上传成功为完成上传的时间',
    PRIMARY KEY (`id`),
    UNIQUE (`uid`, `filename`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '番剧视频信息表';

CREATE TABLE IF NOT EXISTS
`huadiao_whisper_latest_message`(
    `id` INT NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
    `message_id` INT NOT NULL COMMENT '已读消息最新 id',
    `receive_uid` INT NOT NULL COMMENT '接收者 uid',
    `send_uid` INT NOT NULL COMMENT '发送者 uid',
    `send_time` DATETIME NOT NULL COMMENT '发送时间',
    PRIMARY KEY (`id`),
    UNIQUE (`message_id`, `send_uid`, `receive_uid`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '私信最新消息表';

CREATE TABLE IF NOT EXISTS
`huadiao_system_latest_message`(
    `id` INT NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '用户 id',
    `message_id` INT NOT NULL COMMENT '已读消息最新 id',
    PRIMARY KEY (`id`),
    UNIQUE (`uid`, `message_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT '系统最新消息表';

CREATE TABLE IF NOT EXISTS
`huadiao_note_like_latest_message`(
    `id` INT NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '点赞者 uid',
    `reply_uid` INT NOT NULL DEFAULT 0 COMMENT '回复者 uid',
    `note_id` INT NOT NULL COMMENT '笔记 id',
    `author_uid` INT NOT NULL COMMENT '作者 uid',
    `replied_uid` INT NOT NULL DEFAULT 0 COMMENT '被回复者 uid',
    `root_comment_id` INT NOT NULL COMMENT '根评论 id',
    `sub_comment_id` INT NOT NULL COMMENT '子评论 id',
    `status` BIT NOT NULL DEFAULT 1 COMMENT '状态 1 有效 0 无效',
    `like_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '点赞时间',
    PRIMARY KEY (`id`),
    UNIQUE (`uid`, `reply_uid`, `note_id`, `author_uid`, `replied_uid`, `root_comment_id`, `sub_comment_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT = '点赞最新消息表';

CREATE TABLE IF NOT EXISTS
`huadiao_note_comment_latest_message`(
    `id` INT NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
    `uid` INT NOT NULL COMMENT '评论者 uid',
    `note_id` INT NOT NULL COMMENT '笔记 id',
    `replied_uid` INT NOT NULL COMMENT '被回复者 uid',
    `author_uid` INT NOT NULL COMMENT '作者 uid',
    `root_comment_id` INT NOT NULL COMMENT '根评论 id',
    `sub_comment_id` INT NOT NULL COMMENT '子评论 id',
    `status` BIT NOT NULL DEFAULT 1 COMMENT '状态 1 有效 0 无效',
    `comment_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '点赞时间',
    PRIMARY KEY (`id`),
    UNIQUE (`uid`, `note_id`, `replied_uid`, `author_uid`, `root_comment_id`, `sub_comment_id`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT = '评论最新消息表';

CREATE TABLE IF NOT EXISTS
`huadiao_emote`(
    `id` INT NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
    `name` VARCHAR(256) NOT NULL COMMENT '表情名称',
    `type` TINYINT NOT NULL COMMENT '表情类型',
    `filename` VARCHAR(256) NOT NULL COMMENT '表情文件名称',
    `gid` INT NOT NULL COMMENT '表情组ID',
    `add_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '添加时间',
    PRIMARY KEY (`id`),
    UNIQUE (`gid`, `name`, `filename`)
) ENGINE = 'Innodb' DEFAULT CHARSET = 'utf8mb4' COMMENT = '表情数据';
