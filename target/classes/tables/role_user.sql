CREATE TABLE t_role_user (
  id int PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  constraint fk_ru_user_id
  foreign key (user_id) references ssm.t_user (id),
  constraint fk_ru_role_id
  foreign key (role_id) references ssm.t_role (id)
);

create index index_ru_user_id
  on t_role_user (user_id)
;

create index index_ru_role_id
  on t_role_user (role_id)
;