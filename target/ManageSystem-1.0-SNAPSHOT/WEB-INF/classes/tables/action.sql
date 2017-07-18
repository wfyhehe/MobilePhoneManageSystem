CREATE TABLE t_action (
  id     CHAR(32) PRIMARY KEY,
  name   VARCHAR(18) UNIQUE NOT NULL,
  remark TEXT,
  type   TINYINT            NOT NULL # 0:普通动作,1:授权动作
);

CREATE INDEX index_action_name
  ON t_action (name);