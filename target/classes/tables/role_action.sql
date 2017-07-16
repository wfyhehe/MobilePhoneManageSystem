CREATE TABLE t_role_action (
  id int PRIMARY KEY AUTO_INCREMENT,
  action_id INT NOT NULL,
  role_id INT NOT NULL,
  constraint fk_ra_action_id
  foreign key (action_id) references ssm.t_action (id),
  constraint fk_ra_role_id
  foreign key (role_id) references ssm.t_role (id)
);

create index index_ra_action_id
  on t_role_action (action_id)
;

create index index_ra_role_id
  on t_role_action (role_id)
;