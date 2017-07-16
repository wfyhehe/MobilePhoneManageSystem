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