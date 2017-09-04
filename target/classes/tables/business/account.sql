CREATE TABLE t_account(
  id CHAR(32) PRIMARY KEY ,
  name    VARCHAR(32) NOT NULL,
  balance DECIMAL(20,2) NOT NULL,
  remark  TEXT,
  dept_id CHAR(32) NOT NULL,
  deleted TINYINT(1),
  CONSTRAINT fk_account_dept_id
  FOREIGN KEY (dept_id) REFERENCES ssm.t_dept (id)
);

CREATE INDEX index_account_dept_id
  ON t_account (dept_id);