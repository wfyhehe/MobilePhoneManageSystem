CREATE TABLE t_mobile_model (
  id           VARCHAR(15) PRIMARY KEY,
  name         VARCHAR(128),
  brand        VARCHAR(64) NOT NULL,
  buying_price DECIMAL(20, 2),
  content       TEXT,
  deleted      TINYINT(1),
  CONSTRAINT fk_mobile_model_brand
  FOREIGN KEY (brand) REFERENCES ssm.t_brand (name)
);

CREATE INDEX index_mobile_model_brand
  ON t_mobile_model (brand);