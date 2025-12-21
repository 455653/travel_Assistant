-- ====================================================
-- 创建数据库
-- ====================================================
-- 注意：执行schema.sql和data.sql之前，请先创建数据库

CREATE DATABASE IF NOT EXISTS traveling_assistant 
DEFAULT CHARACTER SET utf8mb4 
DEFAULT COLLATE utf8mb4_unicode_ci;

USE traveling_assistant;

-- 之后执行schema.sql创建表结构
-- 然后执行data.sql插入初始数据
