CREATE TABLE t_action (
  id int PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR (18) UNIQUE NOT NULL ,
  remark TEXT,
  type TINYINT NOT NULL # 0:普通动作,1:授权动作
);

create index index_action_name
  on t_action (name)
;