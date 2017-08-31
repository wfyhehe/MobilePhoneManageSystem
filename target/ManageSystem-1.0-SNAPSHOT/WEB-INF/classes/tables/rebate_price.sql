CREATE TABLE t_rebate_price (
  id          CHAR(32) PRIMARY KEY,
  rebate_type VARCHAR(15)  NOT NULL,
  model       VARCHAR(128) NOT NULL,
  price       DECIMAL(20, 2),
  CONSTRAINT fk_rebate_price_model
  FOREIGN KEY (model) REFERENCES ssm.t_mobile_model (model),
  CONSTRAINT fk_rebate_price_rebate_type
  FOREIGN KEY (rebate_type) REFERENCES ssm.t_rebate_type (name)
);

CREATE INDEX index_rebate_price_model
  ON t_rebate_price (model);
CREATE INDEX index_rebate_price_rebate_type
  ON t_rebate_price (rebate_type);