CREATE TABLE t_supplier_type (
  id      VARCHAR(15) PRIMARY KEY,
  name    VARCHAR(32) NOT NULL,
  remark  TEXT,
  deleted BOOLEAN
);
