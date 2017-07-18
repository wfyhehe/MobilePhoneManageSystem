CREATE TABLE t_role_user (
  id      INT PRIMARY KEY AUTO_INCREMENT,
  user_id CHAR(32) NOT NULL,
  role_id CHAR(32) NOT NULL,
  CONSTRAINT fk_ru_user_id
  FOREIGN KEY (user_id) REFERENCES ssm.t_user (id),
  CONSTRAINT fk_ru_role_id
  FOREIGN KEY (role_id) REFERENCES ssm.t_role (id)
);

CREATE INDEX index_ru_user_id
  ON t_role_user (user_id);

CREATE INDEX index_ru_role_id
  ON t_role_user (role_id);