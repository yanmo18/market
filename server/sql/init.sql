-- =============================================
-- 数据库: smarket
-- =============================================

CREATE DATABASE IF NOT EXISTS `smarket` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `smarket`;

-- ----------------------------
-- Table structure for sys_file (Files)
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
    `id`        INT(11)      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`      VARCHAR(255) DEFAULT NULL COMMENT '文件名',
    `type`      VARCHAR(100) DEFAULT NULL COMMENT '文件类型',
    `size`      BIGINT(20)   DEFAULT NULL COMMENT '文件大小(字节)',
    `url`       VARCHAR(500) DEFAULT NULL COMMENT '文件URL',
    `md5`       VARCHAR(32)  DEFAULT NULL COMMENT 'MD5校验值',
    `is_delete` TINYINT(1)   DEFAULT 0 COMMENT '是否删除(0:否,1:是)',
    `enable`    TINYINT(1)   DEFAULT 1 COMMENT '是否启用(0:否,1:是)',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统文件表';

-- ----------------------------
-- Table structure for sys_order (Order)
-- ----------------------------
DROP TABLE IF EXISTS `sys_order`;
CREATE TABLE `sys_order` (
    `id`           INT(11)       NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `product_id`   INT(11)       DEFAULT NULL COMMENT '商品ID',
    `no`           VARCHAR(100)  DEFAULT NULL COMMENT '订单编号',
    `name`         VARCHAR(255)  DEFAULT NULL COMMENT '商品名称',
    `num`          INT(11)       DEFAULT NULL COMMENT '数量',
    `state`        VARCHAR(50)   DEFAULT NULL COMMENT '支付状态',
    `total`        DECIMAL(10,2) DEFAULT NULL COMMENT '订单总价',
    `goods_img`    VARCHAR(500)  DEFAULT NULL COMMENT '商品图片',
    `user_id`      INT(11)       DEFAULT NULL COMMENT '用户ID',
    `user_name`    VARCHAR(100)  DEFAULT NULL COMMENT '用户名',
    `user_phone`   VARCHAR(20)   DEFAULT NULL COMMENT '用户电话',
    `payment_time` VARCHAR(50)   DEFAULT NULL COMMENT '支付时间',
    `take_time`    DATETIME      DEFAULT NULL COMMENT '取货时间',
    `over_time`    DATETIME      DEFAULT NULL COMMENT '完成时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Table structure for sys_product (Product)
-- ----------------------------
DROP TABLE IF EXISTS `sys_product`;
CREATE TABLE `sys_product` (
    `id`          INT(11)       NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    `name`        VARCHAR(255)  DEFAULT NULL COMMENT '商品名称',
    `price`       DECIMAL(10,2) DEFAULT NULL COMMENT '价格',
    `picture`     VARCHAR(500)  DEFAULT NULL COMMENT '商品图片',
    `company`     VARCHAR(255)  DEFAULT NULL COMMENT '公司/品牌',
    `madein`      VARCHAR(255)  DEFAULT NULL COMMENT '产地',
    `info`        TEXT          DEFAULT NULL COMMENT '商品描述',
    `type_id`     INT(11)       DEFAULT NULL COMMENT '分类ID',
    `type_name`   VARCHAR(100)  DEFAULT NULL COMMENT '分类名称',
    `create_time` DATETIME      DEFAULT NULL COMMENT '创建时间',
    `store`       INT(11)       DEFAULT 0 COMMENT '库存',
    `unit`        VARCHAR(50)   DEFAULT NULL COMMENT '单位',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ----------------------------
-- Table structure for sys_type (Type)
-- ----------------------------
DROP TABLE IF EXISTS `sys_type`;
CREATE TABLE `sys_type` (
    `id`      INT(11)      NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `type`    VARCHAR(100) DEFAULT NULL COMMENT '分类名称',
    `message` VARCHAR(500) DEFAULT NULL COMMENT '分类描述',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- ----------------------------
-- Table structure for sys_user (User)
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id`          INT(11)      NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    VARCHAR(100) DEFAULT NULL COMMENT '用户名',
    `password`    VARCHAR(255) DEFAULT NULL COMMENT '密码',
    `nickname`    VARCHAR(100) DEFAULT NULL COMMENT '昵称',
    `email`       VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `phone`       VARCHAR(20)  DEFAULT NULL COMMENT '电话',
    `address`     VARCHAR(500) DEFAULT NULL COMMENT '地址',
    `create_time` DATETIME     DEFAULT NULL COMMENT '创建时间',
    `avatar_url`  VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Table structure for sys_users (Users)
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users` (
    `id`          INT(11)       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    VARCHAR(100)  DEFAULT NULL COMMENT '账号',
    `password`    VARCHAR(255)  DEFAULT NULL COMMENT '密码',
    `realname`    VARCHAR(100)  DEFAULT NULL COMMENT '真实姓名',
    `age`         INT(11)       DEFAULT NULL COMMENT '年龄',
    `sex`         VARCHAR(10)   DEFAULT NULL COMMENT '性别',
    `phone`       VARCHAR(20)   DEFAULT NULL COMMENT '电话',
    `money`       DECIMAL(10,2) DEFAULT NULL COMMENT '余额',
    `address`     VARCHAR(500)  DEFAULT NULL COMMENT '地址',
    `email`       VARCHAR(100)  DEFAULT NULL COMMENT '邮箱',
    `photo`       VARCHAR(500)  DEFAULT NULL COMMENT '照片URL',
    `create_tume` DATETIME      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表(多用户)';

-- =============================================
-- 测试数据
-- =============================================

-- ----------------------------
-- 分类数据 sys_type (5条)
-- ----------------------------
INSERT INTO `sys_type` (`id`, `type`, `message`) VALUES
(1, '水果蔬菜', '新鲜时令果蔬，产地直供'),
(2, '乳制品',   '牛奶、酸奶、奶酪等乳制产品'),
(3, '零食饮料', '休闲零食、碳酸饮料、果汁'),
(4, '米面粮油', '大米、面粉、食用油等主食'),
(5, '日用百货', '日化、清洁、纸品等生活用品');

-- ----------------------------
-- 管理员数据 sys_user (3条)
-- ----------------------------
INSERT INTO `sys_user` (`username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`) VALUES
('admin',    'e10adc3949ba59abbe56e057f20f883e', '超级管理员', 'admin@supermarket.com',    '13800000001', '北京市朝阳区总部大楼', '2025-01-10 09:00:00', 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=200&h=200&fit=crop&crop=face'),
('manager',  'e10adc3949ba59abbe56e057f20f883e', '门店店长',   'manager@supermarket.com', '13800000002', '北京市海淀区中关村店', '2025-02-15 10:30:00', 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200&h=200&fit=crop&crop=face'),
('operator', 'e10adc3949ba59abbe56e057f20f883e', '前台营业员', 'operator@supermarket.com', '13800000003', '北京市西城区西单店',  '2025-03-20 08:45:00', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200&h=200&fit=crop&crop=face');

-- ----------------------------
-- 顾客数据 sys_users (6条)
-- ----------------------------
INSERT INTO `sys_users` (`username`, `password`, `realname`, `age`, `sex`, `phone`, `money`, `address`, `email`, `photo`, `create_tume`) VALUES
('zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '张三', 28, '男', '13911111111',  500.00, '北京市朝阳区望京SOHO', 'zhangsan@qq.com',  'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200&h=200&fit=crop&crop=face', '2025-04-01 12:00:00'),
('lisi',     'e10adc3949ba59abbe56e057f20f883e', '李四', 32, '女', '13922222222', 1200.50, '北京市海淀区五道口',   'lisi@qq.com',      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=200&h=200&fit=crop&crop=face', '2025-04-15 15:20:00'),
('wangwu',   'e10adc3949ba59abbe56e057f20f883e', '王五', 25, '男', '13933333333',   88.00, '北京市丰台区六里桥',  'wangwu@163.com',   'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200&h=200&fit=crop&crop=face', '2025-05-03 09:30:00'),
('zhaoliu',  'e10adc3949ba59abbe56e057f20f883e', '赵六', 41, '女', '13944444444', 3000.00, '北京市昌平区回龙观',  'zhaoliu@sina.com', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=200&h=200&fit=crop&crop=face', '2025-05-18 18:45:00'),
('qianqi',   'e10adc3949ba59abbe56e057f20f883e', '钱七', 19, '男', '13955555555',   20.30, '北京市通州区万达广场', 'qianqi@gmail.com', 'https://images.unsplash.com/photo-1463453091185-61582044d556?w=200&h=200&fit=crop&crop=face', '2025-06-02 11:10:00'),
('sunba',    'e10adc3949ba59abbe56e057f20f883e', '孙八', 36, '女', '13966666666',  650.80, '北京市大兴区黄村',    'sunba@126.com',    'https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200&h=200&fit=crop&crop=face', '2025-06-05 14:25:00');

-- ----------------------------
-- 商品数据 sys_product (18条)
-- ----------------------------
INSERT INTO `sys_product` (`name`, `price`, `picture`, `company`, `madein`, `info`, `type_id`, `type_name`, `create_time`, `store`, `unit`) VALUES
('红富士苹果',          5.80,  'https://images.unsplash.com/photo-1568702846914-96b305d2aaeb?w=300&h=300&fit=crop',    '烟台果业',    '山东烟台',    '脆甜多汁，产地直发',              1, '水果蔬菜', '2025-05-10 09:00:00', 500, '斤'),
('海南香蕉',            3.50,  'https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=300&h=300&fit=crop',  '海南农场',    '海南三亚',    '自然成熟，香甜软糯',              1, '水果蔬菜', '2025-05-12 10:00:00', 300, '斤'),
('东北大米 5kg',        49.90, 'https://images.unsplash.com/photo-1586201375761-83865001e31c?w=300&h=300&fit=crop',  '北大荒',      '黑龙江',      '东北黑土地长粒香米，口感软糯',      4, '米面粮油', '2025-04-20 08:30:00', 200, '袋'),
('蒙牛纯牛奶 250ml*16', 56.00, 'https://images.unsplash.com/photo-1563636619-e9143da7973b?w=300&h=300&fit=crop',   '蒙牛乳业',    '内蒙古',      '常温纯牛奶，优质奶源',              2, '乳制品',   '2025-05-25 14:00:00', 150, '箱'),
('可口可乐 330ml*24',   65.00, 'https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=300&h=300&fit=crop', '太古可口可乐','上海',        '经典原味碳酸饮料',                  3, '零食饮料', '2025-05-30 11:00:00', 100, '箱'),
('奥利奥饼干 3包装',    19.90, 'https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=300&h=300&fit=crop', '亿滋食品',    '江苏苏州',    '巧克力味夹心饼干',                  3, '零食饮料', '2025-06-01 09:00:00', 400, '包'),
('西红柿',              4.20,  'https://images.unsplash.com/photo-1592924357228-91a4daadcfea?w=300&h=300&fit=crop', '寿光蔬菜基地','山东寿光',    '新鲜采摘，沙瓤多汁',                1, '水果蔬菜', '2025-06-03 08:00:00', 350, '斤'),
('福临门花生油 5L',     119.90,'https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?w=300&h=300&fit=crop', '中粮集团',    '河北秦皇岛',  '物理压榨一级花生油',                4, '米面粮油', '2025-04-25 10:00:00',  80, '桶'),
('心相印纸巾 3提',      39.90, 'https://images.unsplash.com/photo-1584556812952-905ffd0c611a?w=300&h=300&fit=crop', '恒安集团',    '福建泉州',    '原生木浆，柔韧亲肤',                5, '日用百货', '2025-05-28 13:00:00', 250, '组'),
('蓝月亮洗衣液 3kg',    49.90, 'https://images.unsplash.com/photo-1583947581924-860bda6a26df?w=300&h=300&fit=crop','蓝月亮',      '广东广州',    '亮白增艳，薰衣草香',                5, '日用百货', '2025-06-04 15:00:00', 180, '瓶'),
('新疆库尔勒香梨',        8.80, 'https://images.unsplash.com/photo-1570913149827-d2ac84ab3f9a?w=300&h=300&fit=crop',  '新疆果业',    '新疆库尔勒',  '皮薄肉细，汁多香甜',                1, '水果蔬菜', '2025-06-01 08:00:00', 200, '斤'),
('伊利酸奶 100g*12',     18.50,'https://images.unsplash.com/photo-1488477181946-6428a0291777?w=300&h=300&fit=crop',  '伊利乳业',    '内蒙古',      '原味酸奶，促进消化',                2, '乳制品',   '2025-05-20 10:00:00', 300, '盒'),
('农夫山泉 550ml*24',   35.00,'https://images.unsplash.com/photo-1548839140-29a749e1cf4d?w=300&h=300&fit=crop',  '农夫山泉',    '浙江杭州',    '天然弱碱性水，健康饮水',            3, '零食饮料', '2025-05-15 09:00:00', 500, '箱'),
('旺旺雪饼 200g',        12.80,'https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=300&h=300&fit=crop',  '旺旺集团',    '湖南长沙',    '轻焙非油炸，酥脆健康',              3, '零食饮料', '2025-05-22 11:00:00', 400, '袋'),
('金龙鱼调和油 5L',      79.90,'https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=300&h=300&fit=crop',  '益海嘉里',    '广东深圳',    '营养均衡，适合全家',                4, '米面粮油', '2025-04-28 14:00:00', 120, '桶'),
('维达手帕纸 10包',      15.90,'https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=300&h=300&fit=crop',  '维达集团',    '广东江门',    '便携式手帕纸，纸质柔滑',            5, '日用百货', '2025-05-29 08:30:00', 600, '组'),
('立白洗洁精 1.5kg',     22.50,'https://images.unsplash.com/photo-1582722872445-44dc5f7e3c8f?w=300&h=300&fit=crop',  '立白集团',    '广东广州',    '高效去油，易冲洗',                  5, '日用百货', '2025-06-02 16:00:00', 350, '瓶'),
('荷兰土豆 3斤',          9.90,'https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=300&h=300&fit=crop', '荷兰农场',    '山东潍坊',    '荷兰品种，粉糯香甜',                1, '水果蔬菜', '2025-06-05 07:00:00', 400, '袋'),
('康师傅方便面 5连包',   14.50,'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=300&h=300&fit=crop',  '康师傅',      '天津',        '红烧牛肉味，方便快捷',              3, '零食饮料', '2025-06-06 10:00:00', 350, '包');


-- ----------------------------
-- 订单数据 sys_order (22条)
-- ----------------------------
INSERT INTO `sys_order` (`product_id`, `no`, `name`, `num`, `state`, `total`, `goods_img`, `user_id`, `user_name`, `user_phone`, `payment_time`, `take_time`, `over_time`) VALUES
(1,  'ORD20250607001', '红富士苹果',          3, '待发货',  17.40, 'https://images.unsplash.com/photo-1568702846914-96b305d2aaeb?w=300&h=300&fit=crop', 1, '张三', '13911111111', '2025-06-07 09:15:00', '2025-06-07 10:00:00', NULL),
(2,  'ORD20250607002', '海南香蕉',            2, '待收货',   7.00, 'https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=300&h=300&fit=crop', 2, '李四', '13922222222', '2025-06-07 10:30:00', '2025-06-07 11:00:00', '2025-06-07 11:30:00'),
(3,  'ORD20250607003', '东北大米 5kg',        1, '待收货',  49.90, 'https://images.unsplash.com/photo-1586201375761-83865001e31c?w=300&h=300&fit=crop', 3, '王五', '13933333333', '2025-06-06 14:20:00', '2025-06-06 15:00:00', '2025-06-07 09:00:00'),
(4,  'ORD20250607004', '蒙牛纯牛奶 250ml*16', 1, '待收货',  56.00, 'https://images.unsplash.com/photo-1563636619-e9143da7973b?w=300&h=300&fit=crop',  4, '赵六', '13944444444', NULL,                 NULL,                 NULL),
(5,  'ORD20250607005', '可口可乐 330ml*24',   1, '待收货',  65.00, 'https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=300&h=300&fit=crop', 5, '钱七', '13955555555', '2025-06-07 11:00:00', '2025-06-07 12:00:00', NULL),
(6,  'ORD20250607006', '奥利奥饼干 3包装',    5, '待收货',  99.50, 'https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=300&h=300&fit=crop', 6, '孙八', '13966666666', '2025-06-07 12:30:00', '2025-06-07 13:30:00', NULL),
(7,  'ORD20250607007', '福临门花生油 5L',     1, '待收货', 119.90, 'https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?w=300&h=300&fit=crop', 1, '张三', '13911111111', NULL,                 NULL,                 NULL),
(8,  'ORD20250607008', '蓝月亮洗衣液 3kg',    2, '待收货',  99.80, 'https://images.unsplash.com/photo-1583947581924-860bda6a26df?w=300&h=300&fit=crop',2, '李四', '13922222222', '2025-06-05 16:40:00', '2025-06-05 17:30:00', '2025-06-06 10:00:00'),
(11, 'ORD20250606001', '新疆库尔勒香梨',     4, '待收货',  35.20, 'https://images.unsplash.com/photo-1570913149827-d2ac84ab3f9a?w=300&h=300&fit=crop', 1, '张三', '13911111111', '2025-06-06 08:30:00', '2025-06-06 09:30:00', '2025-06-06 10:00:00'),
(12, 'ORD20250606002', '伊利酸奶 100g*12',   2, '待收货',  37.00, 'https://images.unsplash.com/photo-1488477181946-6428a0291777?w=300&h=300&fit=crop', 2, '李四', '13922222222', '2025-06-06 09:00:00', '2025-06-06 10:00:00', '2025-06-06 10:30:00'),
(13, 'ORD20250605001', '农夫山泉 550ml*24',  1, '待收货',  35.00, 'https://images.unsplash.com/photo-1548839140-29a749e1cf4d?w=300&h=300&fit=crop', 3, '王五', '13933333333', '2025-06-05 14:00:00', '2025-06-05 15:00:00', '2025-06-05 16:00:00'),
(14, 'ORD20250605002', '旺旺雪饼 200g',      3, '待收货',  38.40, 'https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=300&h=300&fit=crop', 4, '赵六', '13944444444', '2025-06-05 15:30:00', '2025-06-05 16:30:00', NULL),
(15, 'ORD20250604001', '金龙鱼调和油 5L',    1, '待收货',  79.90, 'https://images.unsplash.com/photo-1635870723802-e88d76fbd4b0?w=300&h=300&fit=crop', 5, '钱七', '13955555555', '2025-06-04 10:00:00', '2025-06-04 11:00:00', '2025-06-04 12:00:00'),
(16, 'ORD20250604002', '红富士苹果',         5, '已完成',  29.00, 'https://images.unsplash.com/photo-1568702846914-96b305d2aaeb?w=300&h=300&fit=crop', 5, '钱七', '13955555555', '2025-06-04 10:30:00', '2025-06-04 11:30:00', '2025-06-04 12:30:00'),
(17, 'ORD20250603001', '维达手帕纸 10包',    2, '已完成',  31.80, 'https://images.unsplash.com/photo-1583947215257-38ec6f56f8de?w=300&h=300&fit=crop', 6, '孙八', '13966666666', '2025-06-03 11:00:00', '2025-06-03 12:00:00', '2025-06-03 13:00:00'),
(18, 'ORD20250602001', '立白洗洁精 1.5kg',   1, '已完成',  22.50, 'https://images.unsplash.com/photo-1582722872445-44dc5f7e3c8f?w=300&h=300&fit=crop', 1, '张三', '13911111111', '2025-06-02 16:00:00', '2025-06-02 17:00:00', '2025-06-02 17:30:00'),
(19, 'ORD20250602002', '荷兰土豆 3斤',       2, '已完成',  19.80, 'https://images.unsplash.com/photo-1518977676601-b53f82bf1f51?w=300&h=300&fit=crop', 2, '李四', '13922222222', NULL,                 NULL,                 NULL),
(20, 'ORD20250601001', '康师傅方便面 5连包', 4, '已完成',  58.00, 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=300&h=300&fit=crop', 3, '王五', '13933333333', '2025-06-01 12:00:00', '2025-06-01 13:00:00', '2025-06-01 13:30:00'),
(21, 'ORD20250601002', '可口可乐 330ml*24',  1, '已完成',  65.00, 'https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=300&h=300&fit=crop', 4, '赵六', '13944444444', '2025-06-01 14:30:00', '2025-06-01 15:30:00', '2025-06-01 16:00:00'),
(22, 'ORD20250530001', '东北大米 5kg',       1, '已完成',  49.90, 'https://images.unsplash.com/photo-1586201375761-83865001e31c?w=300&h=300&fit=crop', 5, '钱七', '13955555555', '2025-05-30 09:00:00', '2025-05-30 10:00:00', '2025-05-30 10:30:00');

-- ----------------------------
-- 文件上传记录 sys_file (5条)
-- ----------------------------
INSERT INTO `sys_file` (`name`, `type`, `size`, `url`, `md5`, `is_delete`, `enable`) VALUES
('apple.jpg',  'image/jpeg', 10240, 'https://images.unsplash.com/photo-1568702846914-96b305d2aaeb?w=300&h=300&fit=crop', 'a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6', 0, 1),
('milk.jpg',   'image/jpeg', 15360, 'https://images.unsplash.com/photo-1563636619-e9143da7973b?w=300&h=300&fit=crop', 'b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7', 0, 1),
('cola.jpg',   'image/jpeg',  8192, 'https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=300&h=300&fit=crop', 'c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8', 0, 1),
('rice.jpg',   'image/jpeg', 12288, 'https://images.unsplash.com/photo-1586201375761-83865001e31c?w=300&h=300&fit=crop', 'd4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9', 0, 1),
('tissue.jpg', 'image/png',  20480, 'https://images.unsplash.com/photo-1584556812952-905ffd0c611a?w=300&h=300&fit=crop', 'e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9b0', 0, 1);