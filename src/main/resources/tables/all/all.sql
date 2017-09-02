CREATE TABLE t_role (
  id     CHAR(32) PRIMARY KEY,
  name   VARCHAR(32) UNIQUE NOT NULL,
  remark TEXT,
  status TINYINT            NOT NULL # 0:启用,1:停用,2:删除
);

CREATE INDEX index_role_name
  ON t_role (name);

CREATE TABLE t_user (
  id              CHAR(32) PRIMARY KEY,
  username        VARCHAR(18) UNIQUE NOT NULL,
  password        CHAR(32)           NOT NULL,
  remark          TEXT,
  create_time     DATETIME,
  last_login_time DATETIME,
  status          TINYINT            NOT NULL # 0:启用,1:停用,2:删除
);

CREATE INDEX index_user_username
  ON t_user (username);
CREATE TABLE t_dept (
  id      CHAR(32) PRIMARY KEY,
  name    VARCHAR(100) NOT NULL,
  address VARCHAR(500) NOT NULL,
  remark  TEXT,
  tel     VARCHAR(20),
  contact VARCHAR(32),
  status  TINYINT      NOT NULL # 0: 正常, 1: 已删除
);

CREATE INDEX index_dept_name
  ON t_dept (name);

CREATE INDEX index_dept_contact
  ON t_dept (address);

CREATE TABLE t_action (
  id      CHAR(32) PRIMARY KEY,
  name    VARCHAR(18) UNIQUE NOT NULL,
  remark  TEXT,
  type    TINYINT            NOT NULL, # 0:普通动作,1:授权动作
  menu_id CHAR(32)           NULL,
  url     VARCHAR(32)        NOT NULL,
  CONSTRAINT fk_action_menu_id
  FOREIGN KEY (menu_id) REFERENCES ssm.t_menu (id)
);

CREATE INDEX index_action_name
  ON t_action (name);

CREATE TABLE t_employee (
  id      CHAR(32) PRIMARY KEY,
  name    VARCHAR(30),
  tel     VARCHAR(20),
  remark  TEXT,
  type    TINYINT    NOT NULL, # 0:销售员 1:其他
  dept_id CHAR(32)   NOT NULL,
  user_id CHAR(32),
  deleted TINYINT(1) NOT NULL,
  CONSTRAINT fk_employee_dept_id
  FOREIGN KEY (dept_id) REFERENCES ssm.t_dept (id),
  CONSTRAINT fk_employee_user_id
  FOREIGN KEY (user_id) REFERENCES ssm.t_user (id)
);

CREATE INDEX index_employee_dept_id
  ON t_employee (dept_id);

CREATE INDEX index_employee_user_id
  ON t_employee (user_id);

CREATE TABLE t_menu (
  id         CHAR(32) PRIMARY KEY,
  name       VARCHAR(32) UNIQUE NOT NULL,
  remark     TEXT,
  type       TINYINT            NOT NULL, # 0:父菜单,1:叶子菜单,2:分割线
  path       VARCHAR(32)        NULL,
  sort_order TINYINT            NOT NULL,
  parent_id  CHAR(32) REFERENCES t_menu (id),
  CONSTRAINT fk_menu_parent_id
  FOREIGN KEY (parent_id) REFERENCES ssm.t_menu (id)
);

CREATE INDEX index_menu_parent_id
  ON t_menu (parent_id);

CREATE TABLE t_role_action (
  id        CHAR(32) PRIMARY KEY AUTO_INCREMENT,
  action_id CHAR(32) NOT NULL,
  role_id   CHAR(32) NOT NULL,
  CONSTRAINT fk_ra_action_id
  FOREIGN KEY (action_id) REFERENCES ssm.t_action (id),
  CONSTRAINT fk_ra_role_id
  FOREIGN KEY (role_id) REFERENCES ssm.t_role (id)
);

CREATE INDEX index_ra_action_id
  ON t_role_action (action_id);

CREATE INDEX index_ra_role_id
  ON t_role_action (role_id);

# CREATE TABLE t_role_employee (
#   id          CHAR(32) PRIMARY KEY,
#   user_id     CHAR(32) NOT NULL,
#   employee_id CHAR(32) NOT NULL,
#   CONSTRAINT fk_re_user_id
#   FOREIGN KEY (user_id) REFERENCES ssm.t_user (id),
#   CONSTRAINT fk_re_employee_id
#   FOREIGN KEY (employee_id) REFERENCES ssm.t_employee (id)
# );
#
# CREATE INDEX index_re_user_id
#   ON t_role_employee (user_id);
#
# CREATE INDEX index_re_employee_id
#   ON t_role_employee (employee_id);

CREATE TABLE t_role_menu (
  id      CHAR(32) PRIMARY KEY AUTO_INCREMENT,
  menu_id CHAR(32) NOT NULL,
  role_id CHAR(32) NOT NULL,
  CONSTRAINT fk_rm_menu_id
  FOREIGN KEY (menu_id) REFERENCES ssm.t_menu (id),
  CONSTRAINT fk_rm_role_id
  FOREIGN KEY (role_id) REFERENCES ssm.t_role (id)
);

CREATE INDEX index_rm_menu_id
  ON t_role_menu (menu_id);

CREATE INDEX index_rm_role_id
  ON t_role_menu (role_id);

CREATE TABLE t_role_user (
  id      CHAR(32) PRIMARY KEY AUTO_INCREMENT,
  user_id CHAR(32) NOT NULL,
  role_id CHAR(32) NOT NULL,
  CONSTRAINT fk_ru_user_id
  FOREIGN KEY (user_id) REFERENCES ssm.t_user (id),
  CONSTRAINT fk_ru_role_id
  FOREIGN KEY (role_id) REFERENCES ssm.t_role (id)
);

CREATE INDEX index_ru_user_id
  ON t_role_user (user_id);

CREATE INDEX index_ru_role_id
  ON t_role_user (role_id);

CREATE TABLE t_supplier_type (
  id      VARCHAR(15) PRIMARY KEY,
  name    VARCHAR(32) NOT NULL,
  remark  TEXT,
  deleted BOOLEAN
);

CREATE TABLE t_supplier (
  id      VARCHAR(15) PRIMARY KEY,
  type    CHAR(32),
  name    VARCHAR(32) NOT NULL,
  contact VARCHAR(32),
  tel     VARCHAR(30),
  email   VARCHAR(64),
  address VARCHAR(200),
  remark  TEXT,
  deleted BOOLEAN,
  CONSTRAINT fk_supplier_type
  FOREIGN KEY (type) REFERENCES ssm.t_supplier_type (id)
);

CREATE INDEX index_supplier_type
  ON t_supplier (type);

CREATE TABLE t_rebate_type (
  id      CHAR(32) PRIMARY KEY,
  name    VARCHAR(64),
  remark  TEXT,
  deleted BOOLEAN
);

CREATE TABLE t_color (
  name VARCHAR(64) PRIMARY KEY
);

CREATE TABLE t_brand (
  name VARCHAR(64) PRIMARY KEY
);

CREATE TABLE t_config (
  name VARCHAR(64) PRIMARY KEY
);

CREATE TABLE t_mobile_model (
  id           VARCHAR(15) PRIMARY KEY ,
  name        VARCHAR(128),
  brand        VARCHAR(64) NOT NULL,
  buying_price DECIMAL(20, 2),
  remark       TEXT,
  CONSTRAINT fk_mobile_model_brand
  FOREIGN KEY (brand) REFERENCES ssm.t_brand (name)
);

CREATE INDEX index_mobile_model_brand
  ON t_mobile_model (brand);

CREATE TABLE t_rebate_price (
  id              CHAR(32) PRIMARY KEY,
  rebate_type_id  VARCHAR(15)  NOT NULL,
  mobile_model_id VARCHAR(128) NOT NULL,
  price           DECIMAL(20, 2),
  CONSTRAINT fk_rebate_price_model
  FOREIGN KEY (mobile_model_id) REFERENCES ssm.t_mobile_model (id),
  CONSTRAINT fk_rebate_price_rebate_type
  FOREIGN KEY (rebate_type_id) REFERENCES ssm.t_rebate_type (id)
);

CREATE INDEX index_rebate_price_mobile_model_id
  ON t_rebate_price (mobile_model_id);
CREATE INDEX index_rebate_price_rebate_type_id
  ON t_rebate_price (rebate_type_id);

CREATE TABLE t_mobile_inbound (
  id            VARCHAR(32) PRIMARY KEY,
  supplier_id   VARCHAR(15) NOT NULL,
  model_id      VARCHAR(15) NOT NULL,
  color         VARCHAR(64),
  config        VARCHAR(64),
  buy_price     DECIMAL(20, 2),
  quantity      INT,
  amount        DECIMAL(20, 2), # 冗余字段，增加效率
  input_time    DATETIME,
  input_user_id CHAR(32),
  check_time    DATETIME,
  check_user_id CHAR(32),
  check_status  TINYINT, # 0: 未审核 1: 已审核 2: 删除
  remark        TEXT,
  dept_id       CHAR(32),
  CONSTRAINT fk_mobile_inbound_model_id
  FOREIGN KEY (model_id) REFERENCES ssm.t_mobile_model (id),
  CONSTRAINT fk_mobile_inbound_supplier_id
  FOREIGN KEY (supplier_id) REFERENCES ssm.t_supplier (id),
  CONSTRAINT fk_mobile_inbound_color
  FOREIGN KEY (color) REFERENCES ssm.t_color (name),
  CONSTRAINT fk_mobile_inbound_config
  FOREIGN KEY (config) REFERENCES ssm.t_config (name),
  CONSTRAINT fk_mobile_inbound_input_user_id
  FOREIGN KEY (input_user_id) REFERENCES ssm.t_user (id),
  CONSTRAINT fk_mobile_inbound_check_user_id
  FOREIGN KEY (check_user_id) REFERENCES ssm.t_user (id),
  CONSTRAINT fk_mobile_inbound_stock_dept_id
  FOREIGN KEY (dept_id) REFERENCES ssm.t_dept (id)
);

CREATE INDEX index_mobile_inbound_model_id
  ON t_mobile_inbound (model_id);
CREATE INDEX index_mobile_inbound_supplier_id
  ON t_mobile_inbound (supplier_id);
CREATE INDEX index_mobile_inbound_color
  ON t_mobile_inbound (color);
CREATE INDEX index_mobile_inbound_config
  ON t_mobile_inbound (config);
CREATE INDEX index_mobile_inbound_input_user_id
  ON t_mobile_inbound (input_user_id);
CREATE INDEX index_mobile_inbound_check_user_id
  ON t_mobile_inbound (check_user_id);
CREATE INDEX index_mobile_inbound_dept_id
  ON t_mobile_inbound (dept_id);

CREATE TABLE t_mobile_stock (
  id                VARCHAR(32) PRIMARY KEY,
  model_id          VARCHAR(15) NOT NULL,
  color             VARCHAR(64) NOT NULL,
  config            VARCHAR(64),
  first_supplier_id VARCHAR(15),
  first_in_time     DATETIME,
  last_supplier_id  VARCHAR(15),
  last_in_time      DATETIME,
  buy_price         DECIMAL(20, 2),
  cost              DECIMAL(20, 2), # 由于返利和保价存在，成本可能比入库价低
  loss_amount       DECIMAL(20, 2), # 次品的损失金额
  dept_id           CHAR(32),
  status            TINYINT, # 0: 未审核 1: 已审核 2: 删除
  CONSTRAINT fk_mobile_stock_model_id
  FOREIGN KEY (model_id) REFERENCES ssm.t_mobile_model (id),
  CONSTRAINT fk_mobile_stock_first_supplier_id
  FOREIGN KEY (first_supplier_id) REFERENCES ssm.t_supplier (id),
  CONSTRAINT fk_mobile_stock_last_supplier_id
  FOREIGN KEY (last_supplier_id) REFERENCES ssm.t_supplier (id),
  CONSTRAINT fk_mobile_stock_dept_id
  FOREIGN KEY (dept_id) REFERENCES ssm.t_dept (id)
);

CREATE INDEX index_model_stock_model_id
  ON t_mobile_stock (model_id);
CREATE INDEX index_model_stock_first_supplier_id
  ON t_mobile_stock (first_supplier_id);
CREATE INDEX index_model_stock_last_supplier_id
  ON t_mobile_stock (last_supplier_id);
CREATE INDEX index_model_stock_dept_id
  ON t_mobile_stock (dept_id);

CREATE TABLE t_supplier_trade_detail (
  id            VARCHAR(32) PRIMARY KEY,
  status        TINYINT, # 0: 未审核 1: 已审核 2: 删除
  supplier_id   VARCHAR(15) NOT NULL,
  business_type TINYINT,
  model_id      VARCHAR(15) NOT NULL,
  price         DECIMAL(20, 2),
  quantity      INT,
  amount        DECIMAL(20, 2), # 冗余字段，增加效率
  input_time    DATETIME,
  input_user_id CHAR(32),
  check_time    DATETIME,
  check_user_id CHAR(32),
  remark        TEXT,
  dept_id       CHAR(32),
  CONSTRAINT fk_supplier_trade_detail_supplier_id
  FOREIGN KEY (supplier_id) REFERENCES ssm.t_supplier (id),
  CONSTRAINT fk_supplier_trade_detail_input_user_id
  FOREIGN KEY (input_user_id) REFERENCES ssm.t_user (id),
  CONSTRAINT fk_supplier_trade_detail_check_user_id
  FOREIGN KEY (check_user_id) REFERENCES ssm.t_user (id),
  CONSTRAINT fk_supplier_trade_detail_stock_dept_id
  FOREIGN KEY (dept_id) REFERENCES ssm.t_dept (id)
);

CREATE INDEX index_supplier_trade_detail_supplier_id
  ON t_supplier_trade_detail (supplier_id);
CREATE INDEX index_supplier_trade_detail_input_user_id
  ON t_supplier_trade_detail (input_user_id);
CREATE INDEX index_supplier_trade_detail_check_user_id
  ON t_supplier_trade_detail (check_user_id);
CREATE INDEX index_supplier_trade_detail_dept_id
  ON t_supplier_trade_detail (dept_id);