CREATE TABLE t_role (
  id INT AUTO_INCREMENT PRIMARY KEY ,
  name VARCHAR (18) UNIQUE NOT NULL ,
  remark TEXT,
  status TINYINT NOT NULL # 0:启用,1:停用,2:删除
);

create index index_role_name
  on t_role (name)
;