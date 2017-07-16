CREATE TABLE t_user (
  id INT AUTO_INCREMENT PRIMARY KEY ,
  username VARCHAR (18) UNIQUE NOT NULL ,
  password VARCHAR (32) NOT NULL,
  remark TEXT,
  create_time DATETIME,
  last_login_time DATETIME,
  status TINYINT NOT NULL # 0:启用,1:停用,2:删除
);

create index index_user_username
  on t_user (username)
;