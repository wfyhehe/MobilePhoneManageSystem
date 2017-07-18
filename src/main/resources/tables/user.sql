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