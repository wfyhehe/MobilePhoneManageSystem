CREATE TABLE t_supplier_trade_detail (
  id            VARCHAR(32) PRIMARY KEY,
  status        TINYINT, # 0: 未审核 1: 已审核 2: 删除
  supplier_id   VARCHAR(15) NOT NULL,
  business_type TINYINT,
  model_id      VARCHAR(15) NOT NULL,
  price         DECIMAL(20, 2),
  quantity      INT,
  amount        DECIMAL(20, 2), # 冗余字段，增加效率
  input_time    DATETIME,
  input_user_id CHAR(32),
  check_time    DATETIME,
  check_user_id CHAR(32),
  remark        TEXT,
  dept_id       CHAR(32),
  CONSTRAINT fk_supplier_trade_detail_supplier_id
  FOREIGN KEY (supplier_id) REFERENCES ssm.t_supplier (id),
  CONSTRAINT fk_supplier_trade_detail_input_user_id
  FOREIGN KEY (input_user_id) REFERENCES ssm.t_user (id),
  CONSTRAINT fk_supplier_trade_detail_check_user_id
  FOREIGN KEY (check_user_id) REFERENCES ssm.t_user (id),
  CONSTRAINT fk_supplier_trade_detail_stock_dept_id
  FOREIGN KEY (dept_id) REFERENCES ssm.t_dept (id)
);

CREATE INDEX index_supplier_trade_detail_supplier_id
  ON t_supplier_trade_detail (supplier_id);
CREATE INDEX index_supplier_trade_detail_input_user_id
  ON t_supplier_trade_detail (input_user_id);
CREATE INDEX index_supplier_trade_detail_check_user_id
  ON t_supplier_trade_detail (check_user_id);
CREATE INDEX index_supplier_trade_detail_dept_id
  ON t_supplier_trade_detail (dept_id);