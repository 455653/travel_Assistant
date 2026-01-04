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
    account_id  varchar(30)                         null comment '用户自定义帐号ID',
    password    varchar(255)                        not null comment '密码',
    real_name   varchar(50)                         null comment '真实姓名',
    id_card     varchar(20)                         null comment '身份证号',
    phone       varchar(20)                         null comment '手机号',
    email       varchar(100)                        null comment '邮箱',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_time timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint idx_account_id
        unique (account_id),
    constraint idx_id_card
        unique (id_card),
    constraint idx_phone
        unique (phone),
    constraint username
        unique (username)
)
    comment '用户表';

create table comment
(
    id            bigint auto_increment comment '评论ID'
        primary key,
    attraction_id bigint                              not null comment '景点ID',
    user_id       bigint                              not null comment '用户ID',
    content       text                                not null comment '评论内容',
    create_time   timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    image_url     varchar(500)                        null comment '评论图片URL',
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

create table friend_request
(
    id          bigint auto_increment comment '请求ID'
        primary key,
    sender_id   bigint                              not null comment '发送者用户ID',
    receiver_id bigint                              not null comment '接收者用户ID',
    status      tinyint   default 0                 null comment '状态: 0=待处理, 1=已同意, 2=已拒绝',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_time timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint friend_request_ibfk_1
        foreign key (sender_id) references users (id)
            on delete cascade,
    constraint friend_request_ibfk_2
        foreign key (receiver_id) references users (id)
            on delete cascade
)
    comment '好友请求表' charset = utf8mb4;

create index idx_receiver_status
    on friend_request (receiver_id, status);

create index idx_sender
    on friend_request (sender_id);

create table friendship
(
    id          bigint auto_increment comment '关系ID'
        primary key,
    user_id     bigint                              not null comment '用户ID',
    friend_id   bigint                              not null comment '好友ID',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    constraint idx_user_friend
        unique (user_id, friend_id),
    constraint friendship_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade,
    constraint friendship_ibfk_2
        foreign key (friend_id) references users (id)
            on delete cascade
)
    comment '好友关系表' charset = utf8mb4;

create index idx_friend
    on friendship (friend_id);

create table travel_footprint
(
    id          bigint auto_increment comment '足迹ID'
        primary key,
    creator_id  bigint                              not null comment '创建者用户ID',
    province_id bigint                              not null comment '所属省份ID',
    title       varchar(100)                        not null comment '相册标题',
    start_date  date                                null comment '开始日期',
    end_date    date                                null comment '结束日期',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    constraint travel_footprint_ibfk_1
        foreign key (creator_id) references users (id)
            on delete cascade,
    constraint travel_footprint_ibfk_2
        foreign key (province_id) references province (id)
            on delete cascade
)
    comment '旅行足迹相册表' charset = utf8mb4;

create table expense_record
(
    id           bigint auto_increment comment '记录ID'
        primary key,
    footprint_id bigint                              not null comment '所属足迹相册ID',
    payer_id     bigint                              not null comment '支付人用户ID',
    category     varchar(50)                         not null comment '消费分类',
    amount       decimal(10, 2)                      not null comment '金额',
    expense_date date                                not null comment '消费日期',
    description  varchar(255)                        null comment '备注描述',
    create_time  timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    constraint expense_record_ibfk_1
        foreign key (footprint_id) references travel_footprint (id)
            on delete cascade,
    constraint expense_record_ibfk_2
        foreign key (payer_id) references users (id)
            on delete cascade
)
    comment '旅行记账表' charset = utf8mb4;

create index idx_category
    on expense_record (category);

create index idx_expense_date
    on expense_record (expense_date);

create index idx_footprint
    on expense_record (footprint_id);

create index idx_payer
    on expense_record (payer_id);

create table footprint_collaborator
(
    id           bigint auto_increment comment '协作ID'
        primary key,
    footprint_id bigint                              not null comment '足迹相册ID',
    user_id      bigint                              not null comment '协作者用户ID',
    create_time  timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    constraint idx_footprint_user
        unique (footprint_id, user_id),
    constraint footprint_collaborator_ibfk_1
        foreign key (footprint_id) references travel_footprint (id)
            on delete cascade,
    constraint footprint_collaborator_ibfk_2
        foreign key (user_id) references users (id)
            on delete cascade
)
    comment '足迹协作者表' charset = utf8mb4;

create index idx_user
    on footprint_collaborator (user_id);

create table footprint_note
(
    id           bigint auto_increment comment '记录ID'
        primary key,
    footprint_id bigint                              not null comment '所属足迹相册ID',
    author_id    bigint                              not null comment '发布者用户ID',
    content      text                                not null comment '文字内容',
    create_time  timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    constraint footprint_note_ibfk_1
        foreign key (footprint_id) references travel_footprint (id)
            on delete cascade,
    constraint footprint_note_ibfk_2
        foreign key (author_id) references users (id)
            on delete cascade
)
    comment '足迹文字记录表' charset = utf8mb4;

create index idx_author
    on footprint_note (author_id);

create index idx_create_time
    on footprint_note (create_time);

create index idx_footprint
    on footprint_note (footprint_id);

create table footprint_photo
(
    id           bigint auto_increment comment '照片ID'
        primary key,
    footprint_id bigint                              not null comment '所属足迹相册ID',
    uploader_id  bigint                              not null comment '上传者用户ID',
    image_url    varchar(500)                        not null comment '照片OSS地址',
    create_time  timestamp default CURRENT_TIMESTAMP null comment '上传时间',
    constraint footprint_photo_ibfk_1
        foreign key (footprint_id) references travel_footprint (id)
            on delete cascade,
    constraint footprint_photo_ibfk_2
        foreign key (uploader_id) references users (id)
            on delete cascade
)
    comment '足迹照片表' charset = utf8mb4;

create index idx_footprint
    on footprint_photo (footprint_id);

create index idx_uploader
    on footprint_photo (uploader_id);

create index idx_creator
    on travel_footprint (creator_id);

create index idx_province
    on travel_footprint (province_id);

create index idx_username
    on users (username);

-- 旅行规划表 (添加到 database.sql 中)
create table travel_plan
(
    id          bigint auto_increment comment '规划ID'
        primary key,
    user_id     bigint                              not null comment '创建者用户ID',
    title       varchar(200)                        not null comment '规划标题',
    destination varchar(100)                        not null comment '目的地',
    start_date  date                                not null comment '开始日期',
    end_date    date                                not null comment '结束日期',
    people_count int                                null comment '出行人数',
    budget_type varchar(50)                         null comment '预算类型(穷游/舒适/豪华)',
    content     longtext                            not null comment '攻略详细内容(Markdown)',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_time timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint travel_plan_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade
)
    comment '旅行规划表' charset = utf8mb4;

create index idx_user_id
    on travel_plan (user_id);

create index idx_create_time
    on travel_plan (create_time);