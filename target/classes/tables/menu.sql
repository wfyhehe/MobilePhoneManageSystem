CREATE TABLE t_menu (
  id         CHAR(32) PRIMARY KEY,
  name       VARCHAR(32) UNIQUE NOT NULL,
  remark     TEXT,
  type       TINYINT            NOT NULL, # 0:父菜单,1:叶子菜单,2:分割线
  sort_order TINYINT            NOT NULL,
  parent_id  CHAR(32) REFERENCES t_menu (id),
  action_id  CHAR(32) REFERENCES t_action (id),
  CONSTRAINT fk_menu_action_id
  FOREIGN KEY (action_id) REFERENCES ssm.t_action (id),
  CONSTRAINT fk_menu_parent_id
  FOREIGN KEY (parent_id) REFERENCES ssm.t_menu (id)
);

CREATE INDEX index_menu_action_id
  ON t_menu (action_id);

CREATE INDEX index_menu_parent_id
  ON t_menu (parent_id);