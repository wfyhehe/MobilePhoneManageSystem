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