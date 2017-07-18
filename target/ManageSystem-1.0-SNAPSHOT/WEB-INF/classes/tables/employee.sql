CREATE TABLE t_employee (
  id      CHAR(32) PRIMARY KEY,
  name    VARCHAR(30),
  tel     VARCHAR(20),
  remark  TEXT,
  type    TINYINT NOT NULL, # 0:销售员 1:其他
  dept_id CHAR(32) NOT NULL,
  user_id CHAR(32),
  CONSTRAINT fk_employee_dept_id
  FOREIGN KEY (dept_id) REFERENCES ssm.t_dept (id),
  CONSTRAINT fk_employee_user_id
  FOREIGN KEY (user_id) REFERENCES ssm.t_user (id)
);

CREATE INDEX index_employee_dept_id
  ON t_employee (dept_id);

CREATE INDEX index_employee_user_id
  ON t_employee (user_id);