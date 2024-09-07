# 创建用户表
CREATE TABLE user (
                      id VARCHAR(225) PRIMARY KEY COMMENT '用户ID，非自增',
                      username VARCHAR(225) NOT NULL COMMENT '用户名，必须唯一',
                      password VARCHAR(225) NOT NULL COMMENT '用户密码',
                      email VARCHAR(225)  NULL COMMENT '用户邮箱',
                      phone VARCHAR(225)  NULL COMMENT '用户手机号',
                      surname VARCHAR(225)  NULL COMMENT '用户姓氏',
                      name VARCHAR(225)  NULL COMMENT '用户名字',
                      create_time DATETIME NOT NULL COMMENT '创建时间',
                      update_time DATETIME NOT NULL COMMENT '更新时间'
);

# 创建数据表
CREATE TABLE data (
                      id VARCHAR(225) PRIMARY KEY COMMENT '用户ID，非自增',
                      user_id VARCHAR(225) NOT NULL COMMENT '用户ID',
                      phone VARCHAR(225) NOT NULL COMMENT '号码',
                      username VARCHAR(225) NULL COMMENT '用户姓名',
                      status VARCHAR(225) NULL COMMENT '状态',
                      results VARCHAR(225) NULL COMMENT '反馈结果',
                      create_time DATETIME NULL COMMENT '创建时间',
                      update_time DATETIME NULL COMMENT '更新时间'
);

SHOW CREATE TABLE data;