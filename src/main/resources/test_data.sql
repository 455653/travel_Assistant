-- 插入测试用户数据
INSERT INTO users (username, password) VALUES ('admin', '123456');
INSERT INTO users (username, password) VALUES ('testuser', 'test123');

-- 为现有景点增加更多浏览量和评分数据，让热门景点效果更明显
UPDATE attraction SET view_count = 15000, rating = 4.8 WHERE name = '黄鹤楼';

-- 插入更多热门景点示例数据（如果需要的话）
-- 以下是一些示例，你可以根据实际需要调整

-- 北京市的景点
INSERT INTO city (province_id, name, description) 
VALUES (1, '北京', '中国的首都，拥有丰富的历史文化遗产') 
ON DUPLICATE KEY UPDATE description = description;

SET @beijing_city_id = (SELECT id FROM city WHERE name = '北京' LIMIT 1);

INSERT INTO attraction (city_id, name, image_url, description, view_count, rating) VALUES
(@beijing_city_id, '故宫', 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d?w=800', '明清两代的皇家宫殿，世界上现存规模最大、保存最完整的木质结构古建筑群', 25000, 4.9),
(@beijing_city_id, '长城', 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d?w=800', '世界七大奇迹之一，中国古代军事防御工程', 28000, 4.8),
(@beijing_city_id, '天坛', 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d?w=800', '明清两代皇帝祭天祈谷的场所', 18000, 4.7);

-- 上海市的景点
INSERT INTO city (province_id, name, description) 
VALUES (1, '上海', '国际化大都市，中国的经济中心') 
ON DUPLICATE KEY UPDATE description = description;

SET @shanghai_city_id = (SELECT id FROM city WHERE name = '上海' LIMIT 1);

INSERT INTO attraction (city_id, name, image_url, description, view_count, rating) VALUES
(@shanghai_city_id, '外滩', 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d?w=800', '上海的标志性景观，汇集了各种风格的建筑', 22000, 4.7),
(@shanghai_city_id, '东方明珠', 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d?w=800', '上海的地标性建筑，亚洲第三高塔', 20000, 4.6);

-- 浙江杭州的景点
INSERT INTO city (province_id, name, description) 
VALUES (1, '杭州', '人间天堂，以西湖闻名于世') 
ON DUPLICATE KEY UPDATE description = description;

SET @hangzhou_city_id = (SELECT id FROM city WHERE name = '杭州' LIMIT 1);

INSERT INTO attraction (city_id, name, image_url, description, view_count, rating) VALUES
(@hangzhou_city_id, '西湖', 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d?w=800', '世界文化遗产，中国最著名的淡水湖之一', 30000, 4.9),
(@hangzhou_city_id, '灵隐寺', 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d?w=800', '江南佛教名刹，中国佛教禅宗十大古刹之一', 16000, 4.6);

-- 四川成都的景点
INSERT INTO city (province_id, name, description) 
VALUES (1, '成都', '天府之国，熊猫的故乡') 
ON DUPLICATE KEY UPDATE description = description;

SET @chengdu_city_id = (SELECT id FROM city WHERE name = '成都' LIMIT 1);

INSERT INTO attraction (city_id, name, image_url, description, view_count, rating) VALUES
(@chengdu_city_id, '大熊猫繁育研究基地', 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d?w=800', '世界著名的大熊猫迁地保护基地', 32000, 4.9);
