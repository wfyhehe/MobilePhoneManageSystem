CREATE TABLE t_supplier (
  id      VARCHAR(15) PRIMARY KEY ,
  type    CHAR(32),
  name    VARCHAR(32) NOT NULL,
  contact VARCHAR(32),
  tel     VARCHAR(30),
  email   VARCHAR(64),
  address VARCHAR(200),
  remark  TEXT,
  CONSTRAINT fk_supplier_type
  FOREIGN KEY (type) REFERENCES ssm.t_supplier_type (id)
);

CREATE INDEX index_supplier_type
  ON t_supplier (type);