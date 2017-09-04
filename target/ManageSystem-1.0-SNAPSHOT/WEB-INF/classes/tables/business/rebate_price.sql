CREATE TABLE t_rebate_price (
  id              CHAR(32) PRIMARY KEY,
  rebate_type_id  CHAR(32)  NOT NULL,
  mobile_model_id VARCHAR(128) NOT NULL,
  price           DECIMAL(20, 2),
  CONSTRAINT fk_rebate_price_model_id
  FOREIGN KEY (mobile_model_id) REFERENCES ssm.t_mobile_model (id),
  CONSTRAINT fk_rebate_price_rebate_type_id
  FOREIGN KEY (rebate_type_id) REFERENCES ssm.t_rebate_type (id)
);

CREATE INDEX index_rebate_price_mobile_model_id
ON t_rebate_price (mobile_model_id);
CREATE INDEX index_rebate_price_rebate_type_id
  ON t_rebate_price (rebate_type_id);