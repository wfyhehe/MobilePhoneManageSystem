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