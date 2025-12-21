-- 插入测试用户数据
INSERT INTO users (username, password) VALUES ('admin', '123456');
INSERT INTO users (username, password) VALUES ('testuser', 'test123');

-- 为现有景点增加更多浏览量和评分数据，让热门景点效果更明显
UPDATE attraction SET view_count = 15000, rating = 4.8 WHERE name = '黄鹤楼';

-- 为所有景点添加旅游攻略数据
UPDATE attraction SET strategy_guide = '
**最佳游览时间**：春秋两季，天气宜人。建议早上8点前到达，避免人流高峰。

**门票信息**：成人票￥70/人，学生票半价。建议提前网上购票，可享受优惠。

**交通指南**：
- 公交：乘址10、401、402路公交车到黄鹤楼站下车
- 地铁：2号线黄鹤楼站D出口
- 自驾：景区有停车场，车位有限建议公共交通前往

**游览路线**：
1. 从南门入口进入，先参观白云阁
2. 登上黄鹤楼主楼，俐瞰长江美景
3. 游览千禧亭、鹅池等景点
4. 最后可到落梅轩品尝武汉特色小吃

**游玩贴士**：
- 景区内有导游服务，建议请导游讲解历史文化
- 登楼可以使用电梯，但建议步行体验
- 带好相机，楼顶是最佳拍照点
- 周边有户部巷、汉街等美食街，可一并游览
' WHERE name = '黄鹤楼';

UPDATE attraction SET strategy_guide = '
**最佳游览时间**：全年皆宜，春季樱花季最佳（3-4月）。建议早上或黄昏时分前往，光线柔和。

**门票信息**：免费开放，无需门票。

**交通指南**：
- 公交：乘址13、8、72路等到武大牙医学院站
- 地铁：2号线光谷广场站

**游览路线**：
1. 从正门入口进入，参观牌坊
2. 沿樱花大道欣赏樱花（春季）
3. 参观老图书馆、行政楼等建筑
4. 前往珞珈山登高俐瞰

**游玩贴士**：
- 樱花季人流巨大，建议工作日前往
- 校园内可以骑行或步行，建议穿舒适的鞋子
- 校园内有多个食堂，可以体验大学食堂
- 注意保持安静，尊重学生学习
' WHERE name = '武汉大学';

UPDATE attraction SET strategy_guide = '
**最佳游览时间**：全年皆宜，春秋季最佳。建议预留半天以上时间。

**门票信息**：
- 成人票￥60/人（淡季￥40）
- 学生票半价
- 建议购买联票，含午门、太和殿等

**交通指南**：
- 公交：1、2、4、5、52等多路公交到天安门东/西站
- 地铁：1号线天安门东站/西站

**游览路线**：
1. 午门 → 中山公园 → 故宫北门（约半天）
2. 或从天安门入，顺序游览

**游玩贴士**：
- 提前网上预约门票，高峰期需要预约
- 建议请讲解员或租赁语音导览
- 建筑内禁止拍照，请注意遵守规定
- 参观需要长时间步行，建议穿舒适的鞋子
' WHERE name = '故宫';

UPDATE attraction SET strategy_guide = '
**最佳游览时间**：春秋季，避开夏季高温和冬季严寒。建议早上或黄昏时分前往。

**门票信息**：
- 旺季￥45/人（4-10月）
- 淡季￥40/人（11-3月）
- 学生票半价

**交通指南**：
- 公交：877、919路公交到八达岭长城站
- 缆车：建议乘坐缆车上山，节省体力

**游览路线**：
1. 从八达岭入口进入（最经典段）
2. 登上敌楼俐瞰全景
3. 沿城墙步行或乘缆车

**游玩贴士**：
- 周末和节假日人流巨大，建议工作日前往
- 穿防滑舒适的运动鞋
- 带足食物和水，山上物价较高
- 注意防晒和保暖，山上气温较低
' WHERE name = '长城';

UPDATE attraction SET strategy_guide = '
**最佳游览时间**：春秋季，建议预留半天时间。黄昏时分祭天仪式最为壮观。

**门票信息**：
- 旺季￥15/人（4-10月）
- 淡季￥10/人（11-3月）

**交通指南**：
- 公交：6〇34〈35路等到天坛公园站
- 地铁：5号线天坛东门站

**游览路线**：
1. 从南门入口进入
2. 参观圆丘坛、皇穹宇
3. 游览祁年殿、回音壁

**游玩贴士**：
- 建议请讲解员了解祭天文化
- 回音壁是必体验景点
- 公园内可以放松散步，环境优美
' WHERE name = '天坛';

UPDATE attraction SET strategy_guide = '
**最佳游览时间**：全年皆宜，晚上灯光秀最为壮观。建议黄昏时分前往。

**门票信息**：沿江步行区免费开放，无需门票。

**交通指南**：
- 公交：20〇37等多路公交到中山东一路站
- 地铁：2、10号线南京东路站

**游览路线**：
1. 从外白渡桥开始步行
2. 欣赏两岸建筑群
3. 到东方明珠下打卡

**游玩贴士**：
- 晚上灯光秀是最佳拍照时间
- 沿江有多处观景平台
- 周边有南京路步行街，可一并游览
' WHERE name = '外滩';

UPDATE attraction SET strategy_guide = '
**最佳游览时间**：全年皆宜，晚上灯光秀最为壮观。建议黄昏时分前往。

**门票信息**：
- 观光厅￥160/人
- 建议提前网上购票

**交通指南**：
- 公交：870、81路到东方明珠站
- 地铁：2号线陆家嘴站

**游览路线**：
1. 登上263米观光厅
2. 360度俐瞰上海全景
3. 可体验透明玻璃地板

**游玩贴士**：
- 高峰期需要排队，建议错峰出行
- 天气好时视野最佳
- 带好相机，夜景非常震撑
' WHERE name = '东方明珠';

UPDATE attraction SET strategy_guide = '
**最佳游览时间**：春秋季，建议预留全天时间。清晨或黄昏光线最美。

**门票信息**：免费开放，无需门票。

**交通指南**：
- 公交：多路公交到西湖周边站点
- 地铁：1号线凤起路站
- 租赁：可租赁共享单车环湖骑行

**游览路线**：
1. 从断桥开始环湖步行
2. 参观苏堤、白堤
3. 游览三潭印月、花港观鱼等景点
4. 乘船游湖（可选）

**游玩贴士**：
- 春季桃花、夏季荷花、秋季桂花、冬季雪景各有特色
- 建议租赁自行车或电动车环湖
- 周边有河坊街、南山路等特色街区
- 品尝杭州特色菜：西湖醋鱼、龙井茶
' WHERE name = '西湖';

UPDATE attraction SET strategy_guide = '
**最佳游览时间**：春秋季，建议预留半天时间。清晨最为宁静。

**门票信息**：
- 成人票￥45/人
- 学生票半价

**交通指南**：
- 公交：7〇324K路到灵隐寺站
- 地铁：转公交前往

**游览路线**：
1. 从山门入口进入
2. 参观大雄宝殿、天王殿
3. 游览飞来峰景区

**游玩贴士**：
- 寺庙内可以礼佛，请尊重宗教信仰
- 周边有多处茶园，可品龙井茶
- 建议穿舒适的鞋子，需要步行
' WHERE name = '灵隐寺';

UPDATE attraction SET strategy_guide = '
**最佳游览时间**：全年皆宜，建议早上前往观看熊猫活动。春秋季气候最适宜。

**门票信息**：
- 成人票￥58/人
- 学生票半价

**交通指南**：
- 公交：87、198路到熊猫基地站
- 地铁：3号线熊猫大道站

**游览路线**：
1. 从大门入口进入
2. 参观成年熊猫别墅
3. 观看熊猫幼儿园
4. 游览科普馆

**游玩贴士**：
- 早上8-10点是熊猫最活跃的时段
- 建议租赁语音导览了解熊猫知识
- 禁止投喂食物，请文明参观
- 周边可品尝成都特色美食：火锅、串串
' WHERE name = '大熊猫繁育研究基地';

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

-- 更新城市的高德地图Adcode（行政区划代码）
UPDATE city SET adcode = '420100' WHERE name = '武汉';
UPDATE city SET adcode = '110000' WHERE name = '北京';
UPDATE city SET adcode = '310000' WHERE name = '上海';
UPDATE city SET adcode = '330100' WHERE name = '杭州';
UPDATE city SET adcode = '510100' WHERE name = '成都';
