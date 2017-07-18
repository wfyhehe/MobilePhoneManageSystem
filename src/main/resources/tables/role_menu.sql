CREATE TABLE t_role_menu (
  id        INT PRIMARY KEY AUTO_INCREMENT,
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