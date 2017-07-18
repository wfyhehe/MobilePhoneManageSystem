CREATE TABLE t_role (
  id     CHAR(32) PRIMARY KEY,
  name   VARCHAR(32) UNIQUE NOT NULL,
  remark TEXT,
  status TINYINT            NOT NULL # 0:启用,1:停用,2:删除
);

CREATE INDEX index_role_name
  ON t_role (name);