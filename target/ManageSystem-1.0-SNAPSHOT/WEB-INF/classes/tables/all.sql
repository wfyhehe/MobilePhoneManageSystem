CREATE TABLE t_user (
  id INT AUTO_INCREMENT PRIMARY KEY ,
  username VARCHAR (18) UNIQUE NOT NULL ,
  password VARCHAR (32) NOT NULL,
  remark TEXT,
  create_time DATETIME,
  last_login_time DATETIME,
  status TINYINT NOT NULL # 0:启用,1:停用,2:删除
);

create index index_user_username
  on t_user (username)
;

CREATE TABLE t_action (
  id int PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR (18) UNIQUE NOT NULL ,
  remark TEXT,
  type TINYINT NOT NULL # 0:普通动作,1:授权动作
);

create index index_action_name
  on t_action (name)
;

CREATE TABLE t_role (
  id INT AUTO_INCREMENT PRIMARY KEY ,
  name VARCHAR (18) UNIQUE NOT NULL ,
  remark TEXT,
  status TINYINT NOT NULL # 0:启用,1:停用,2:删除
);

create index index_role_name
  on t_role (name)
;

CREATE TABLE t_menu (
  id INT AUTO_INCREMENT PRIMARY KEY ,
  name VARCHAR (18) UNIQUE NOT NULL ,
  remark TEXT,
  type TINYINT NOT NULL, # 0:父菜单,1:叶子菜单,2:分割线
  sort_order TINYINT NOT NULL ,
  parent_id INT REFERENCES t_menu(id),
  action_id INT REFERENCES t_action(id),
  constraint fk_menu_action_id
  foreign key (action_id) references ssm.t_action (id),
  constraint fk_menu_parent_id
  foreign key (parent_id) references ssm.t_menu (id)
);

create index index_menu_action_id
  on t_menu (action_id)
;

create index index_menu_parent_id
  on t_menu (parent_id)
;

CREATE TABLE t_role_action (
  id int PRIMARY KEY AUTO_INCREMENT,
  action_id INT NOT NULL,
  role_id INT NOT NULL,
  constraint fk_ra_action_id
  foreign key (action_id) references ssm.t_action (id),
  constraint fk_ra_role_id
  foreign key (role_id) references ssm.t_role (id)
);

create index index_ra_action_id
  on t_role_action (action_id)
;

create index index_ra_role_id
  on t_role_action (role_id)
;

CREATE TABLE t_role_menu (
  id int PRIMARY KEY AUTO_INCREMENT,
  menu_id INT NOT NULL,
  role_id INT NOT NULL,
  constraint fk_rm_menu_id
  foreign key (menu_id) references ssm.t_menu (id),
  constraint fk_rm_role_id
  foreign key (role_id) references ssm.t_role (id)
);

create index index_rm_menu_id
  on t_role_menu (menu_id)
;

create index index_rm_role_id
  on t_role_menu (role_id)
;

CREATE TABLE t_role_user (
  id int PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  constraint fk_ru_user_id
  foreign key (user_id) references ssm.t_user (id),
  constraint fk_ru_role_id
  foreign key (role_id) references ssm.t_role (id)
);

create index index_ru_user_id
  on t_role_user (user_id)
;

create index index_ru_role_id
  on t_role_user (role_id)
;