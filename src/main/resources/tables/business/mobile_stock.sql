CREATE TABLE t_mobile_stock (
  id                VARCHAR(32) PRIMARY KEY,
  model_id          VARCHAR(15) NOT NULL,
  color             VARCHAR(64) NOT NULL,
  config            VARCHAR(64),
  first_supplier_id VARCHAR(15),
  first_in_time     DATETIME,
  last_supplier_id  VARCHAR(15),
  last_in_time      DATETIME,
  inbound_id        CHAR(32),
  buy_price         DECIMAL(20, 2),
  cost              DECIMAL(20, 2), # 由于返利和保价存在，成本可能比入库价低
  loss_amount       DECIMAL(20, 2), # 次品的损失金额
  dept_id           CHAR(32),
  status            TINYINT, # 0: 未审核 1: 已审核 2: 删除
  CONSTRAINT fk_mobile_stock_model_id
  FOREIGN KEY (model_id) REFERENCES ssm.t_mobile_model (id),
  CONSTRAINT fk_mobile_stock_first_supplier_id
  FOREIGN KEY (first_supplier_id) REFERENCES ssm.t_supplier (id),
  CONSTRAINT fk_mobile_stock_last_supplier_id
  FOREIGN KEY (last_supplier_id) REFERENCES ssm.t_supplier (id),
  CONSTRAINT fk_mobile_stock_inbound_id
  FOREIGN KEY (inbound_id) REFERENCES ssm.t_mobile_inbound (id),
  CONSTRAINT fk_mobile_stock_dept_id
  FOREIGN KEY (dept_id) REFERENCES ssm.t_dept (id)
);

CREATE INDEX index_model_stock_model_id
  ON t_mobile_stock (model_id);
CREATE INDEX index_model_stock_first_supplier_id
  ON t_mobile_stock (first_supplier_id);
CREATE INDEX index_model_stock_last_supplier_id
  ON t_mobile_stock (last_supplier_id);
CREATE INDEX index_model_stock_dept_id
  ON t_mobile_stock (dept_id);
CREATE INDEX index_model_stock_inbound_id
  ON t_mobile_stock (inbound_id);