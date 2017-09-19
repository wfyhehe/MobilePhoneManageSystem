CREATE TABLE t_customer (
  id VARCHAR(15) PRIMARY KEY ,
  type      VARCHAR(15),
  name    VARCHAR(32) NOT NULL,
  contact VARCHAR(32),
  tel VARCHAR(30),
  email VARCHAR(64),
  fax VARCHAR(64),
  address VARCHAR(200),
  content  TEXT,
  CONSTRAINT fk_customer_type
  FOREIGN KEY (type) REFERENCES ssm.t_customer_type (id)
);

CREATE INDEX index_customer_type
  ON t_customer (type);