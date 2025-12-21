-- ====================================================
-- 中国旅游助手数据库结构
-- ====================================================

-- 1. 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 2. 省份表
CREATE TABLE IF NOT EXISTS province (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '省份ID',
    name VARCHAR(50) NOT NULL UNIQUE COMMENT '省份名称',
    map_code VARCHAR(20) NOT NULL COMMENT 'ECharts地图代码',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_map_code (map_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='省份表';

-- 3. 城市表
CREATE TABLE IF NOT EXISTS city (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '城市ID',
    province_id BIGINT NOT NULL COMMENT '所属省份ID',
    name VARCHAR(50) NOT NULL COMMENT '城市名称',
    adcode VARCHAR(20) COMMENT '高德地图行政区划代码',
    description TEXT COMMENT '城市描述',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (province_id) REFERENCES province(id) ON DELETE CASCADE,
    INDEX idx_province_id (province_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='城市表';

-- 4. 景点表
CREATE TABLE IF NOT EXISTS attraction (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '景点ID',
    city_id BIGINT NOT NULL COMMENT '所属城市ID',
    name VARCHAR(100) NOT NULL COMMENT '景点名称',
    image_url VARCHAR(500) COMMENT '景点图片URL',
    description TEXT COMMENT '景点描述',
    strategy_guide TEXT COMMENT '旅游攻略',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    rating DECIMAL(3,1) DEFAULT 0.0 COMMENT '评分(0.0-5.0)',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE CASCADE,
    INDEX idx_city_id (city_id),
    INDEX idx_rating (rating)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='景点表';

-- 5. 评论表
CREATE TABLE IF NOT EXISTS comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评论ID',
    attraction_id BIGINT NOT NULL COMMENT '景点ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    content TEXT NOT NULL COMMENT '评论内容',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (attraction_id) REFERENCES attraction(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_attraction_id (attraction_id),
    INDEX idx_user_id (user_id),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';
