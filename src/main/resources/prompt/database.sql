create table province
(
    id          bigint auto_increment comment '省份ID'
        primary key,
    name        varchar(50)                         not null comment '省份名称',
    map_code    varchar(20)                         not null comment 'ECharts地图代码',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    constraint name
        unique (name)
)
    comment '省份表' charset = utf8mb4;

create table city
(
    id          bigint auto_increment comment '城市ID'
        primary key,
    province_id bigint                              not null comment '所属省份ID',
    name        varchar(50)                         not null comment '城市名称',
    adcode      varchar(20)                         null comment '高德地图行政区划代码',
    description text                                null comment '城市描述',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    constraint city_ibfk_1
        foreign key (province_id) references province (id)
            on delete cascade
)
    comment '城市表' charset = utf8mb4;

create table attraction
(
    id             bigint auto_increment comment '景点ID'
        primary key,
    city_id        bigint                                  not null comment '所属城市ID',
    name           varchar(100)                            not null comment '景点名称',
    image_url      varchar(500)                            null comment '景点图片URL',
    description    text                                    null comment '景点描述',
    view_count     int           default 0                 null comment '浏览次数',
    rating         decimal(3, 1) default 0.0               null comment '评分(0.0-5.0)',
    create_time    timestamp     default CURRENT_TIMESTAMP null comment '创建时间',
    strategy_guide text                                    null comment '旅游攻略',
    constraint attraction_ibfk_1
        foreign key (city_id) references city (id)
            on delete cascade
)
    comment '景点表' charset = utf8mb4;

create index idx_city_id
    on attraction (city_id);

create index idx_rating
    on attraction (rating);

create index idx_province_id
    on city (province_id);

create index idx_map_code
    on province (map_code);

create table users
(
    id          bigint auto_increment comment '用户ID'
        primary key,
    username    varchar(50)                         not null comment '用户名',
    password    varchar(255)                        not null comment '密码',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_time timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint username
        unique (username)
)
    comment '用户表' charset = utf8mb4;

create table comment
(
    id            bigint auto_increment comment '评论ID'
        primary key,
    attraction_id bigint                              not null comment '景点ID',
    user_id       bigint                              not null comment '用户ID',
    content       text                                not null comment '评论内容',
    create_time   timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    constraint comment_ibfk_1
        foreign key (attraction_id) references attraction (id)
            on delete cascade,
    constraint comment_ibfk_2
        foreign key (user_id) references users (id)
            on delete cascade
)
    comment '评论表' charset = utf8mb4;

create index idx_attraction_id
    on comment (attraction_id);

create index idx_create_time
    on comment (create_time);

create index idx_user_id
    on comment (user_id);

create index idx_username
    on users (username);

create
definer = root@localhost procedure InsertAllComments()
BEGIN
    DECLARE v_user_id INT DEFAULT 1;
    DECLARE v_attraction_id INT DEFAULT 1;
    -- 获取最大景点ID（假设你已插入所有省份的景点，总数量=34*15=510）
    DECLARE max_attraction_id INT DEFAULT 510;

    -- 循环每个用户
    WHILE v_user_id <= 10 DO
        -- 循环每个景点
        SET v_attraction_id = 1;
        WHILE v_attraction_id <= max_attraction_id DO
            -- 插入随机评论内容（模拟不同评论）
            INSERT INTO comment (attraction_id, user_id, content)
            VALUES (v_attraction_id, v_user_id,
                CONCAT('用户', v_user_id, '评价：这个景点非常不错，值得一去！评分', FLOOR(RAND()*10)/2 + 4, '分，推荐游览时间', FLOOR(RAND()*5)+1, '小时。'));
            SET v_attraction_id = v_attraction_id + 1;
END WHILE;
        SET v_user_id = v_user_id + 1;
END WHILE;
END;

