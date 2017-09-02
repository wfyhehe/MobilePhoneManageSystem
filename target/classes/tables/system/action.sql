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