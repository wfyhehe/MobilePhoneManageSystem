CREATE TABLE t_mobile_model (
  id           VARCHAR(15) PRIMARY KEY ,
  model        VARCHAR(128),
  brand        VARCHAR(64) NOT NULL,
  buying_price DECIMAL(20, 2),
  remark       TEXT,
  CONSTRAINT fk_mobile_model_brand
  FOREIGN KEY (brand) REFERENCES ssm.t_brand (name)
);
