CREATE TABLE t_dept (
  id      CHAR(32) PRIMARY KEY,
  name    VARCHAR(100) NOT NULL,
  address    VARCHAR(500) NOT NULL,
  remark  TEXT,
  tel     VARCHAR(20),
  contact VARCHAR(32)
);

CREATE INDEX index_dept_name
  ON t_dept (name);

CREATE INDEX index_dept_contact
  ON t_dept (address);