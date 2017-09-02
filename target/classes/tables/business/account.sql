CREATE TABLE t_account(
  id VARCHAR(15) PRIMARY KEY ,
  name    VARCHAR(32) NOT NULL,
  balance DECIMAL(20,2) NOT NULL,
  remark  TEXT,
  dept_id CHAR(32) NOT NULL,
  CONSTRAINT fk_account_dept_id
  FOREIGN KEY (dept_id) REFERENCES ssm.t_dept (id)
);

CREATE INDEX index_account_dept_id
  ON t_customer (dept_id);