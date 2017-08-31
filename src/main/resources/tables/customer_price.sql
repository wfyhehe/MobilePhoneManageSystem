CREATE TABLE t_customer_price (
  id          CHAR(32) PRIMARY KEY,
  model       VARCHAR(64)    NOT NULL,
  color       VARCHAR(64),
  customer_id VARCHAR(15)    NOT NULL,
  price       DECIMAL(20, 2) NOT NULL,
  CONSTRAINT fk_customer_price_model
  FOREIGN KEY (model) REFERENCES ssm.t_mobile_model (model),
  CONSTRAINT fk_customer_price_color
  FOREIGN KEY (color) REFERENCES ssm.t_color (name),
  CONSTRAINT fk_customer_price_customer_id
  FOREIGN KEY (customer_id) REFERENCES ssm.t_color (id)
);

CREATE INDEX index_customer_price_model
  ON t_customer_price (model);
CREATE INDEX index_customer_price_color
  ON t_customer_price (color);
CREATE INDEX index_customer_price_customer_id
  ON t_customer_price (customer_id);