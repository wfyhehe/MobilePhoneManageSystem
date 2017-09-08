CREATE TABLE t_role_action (
  id        INT PRIMARY KEY AUTO_INCREMENT,
  action_url VARCHAR(64) NOT NULL,
  role_id   CHAR(32) NOT NULL,
  CONSTRAINT fk_ra_action_id
  FOREIGN KEY (action_url) REFERENCES ssm.t_action (url),
  CONSTRAINT fk_ra_role_id
  FOREIGN KEY (role_id) REFERENCES ssm.t_role (id)
);

CREATE INDEX index_ra_action_url
  ON t_role_action (action_url);

CREATE INDEX index_ra_role_id
  ON t_role_action (role_id);