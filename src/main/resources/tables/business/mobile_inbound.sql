CREATE TABLE t_mobile_inbound (
  id            CHAR(32) PRIMARY KEY,
  supplier_id   VARCHAR(15) NOT NULL,
  model_id      VARCHAR(15) NOT NULL,
  color         VARCHAR(64),
  config        VARCHAR(64),
  buy_price     DECIMAL(20, 2),
  quantity      INT,
  amount        DECIMAL(20, 2), # 冗余字段，增加效率
  input_time    DATETIME,
  input_user_id CHAR(32),
  check_time    DATETIME,
  check_user_id CHAR(32),
  status  TINYINT, # 0: 未审核 1: 已审核 2: 删除
  remark        TEXT,
  dept_id       CHAR(32),
  CONSTRAINT fk_mobile_inbound_model_id
  FOREIGN KEY (model_id) REFERENCES ssm.t_mobile_model (id),
  CONSTRAINT fk_mobile_inbound_supplier_id
  FOREIGN KEY (supplier_id) REFERENCES ssm.t_supplier (id),
  CONSTRAINT fk_mobile_inbound_color
  FOREIGN KEY (color) REFERENCES ssm.t_color (name),
  CONSTRAINT fk_mobile_inbound_config
  FOREIGN KEY (config) REFERENCES ssm.t_config (name),
  CONSTRAINT fk_mobile_inbound_input_user_id
  FOREIGN KEY (input_user_id) REFERENCES ssm.t_user (id),
  CONSTRAINT fk_mobile_inbound_check_user_id
  FOREIGN KEY (check_user_id) REFERENCES ssm.t_user (id),
  CONSTRAINT fk_mobile_inbound_stock_dept_id
  FOREIGN KEY (dept_id) REFERENCES ssm.t_dept (id)
);

CREATE INDEX index_mobile_inbound_model_id
  ON t_mobile_inbound (model_id);
CREATE INDEX index_mobile_inbound_supplier_id
  ON t_mobile_inbound (supplier_id);
CREATE INDEX index_mobile_inbound_color
  ON t_mobile_inbound (color);
CREATE INDEX index_mobile_inbound_config
  ON t_mobile_inbound (config);
CREATE INDEX index_mobile_inbound_input_user_id
  ON t_mobile_inbound (input_user_id);
CREATE INDEX index_mobile_inbound_check_user_id
  ON t_mobile_inbound (check_user_id);
CREATE INDEX index_mobile_inbound_dept_id
  ON t_mobile_inbound (dept_id);