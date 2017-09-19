DROP TABLE IF EXISTS t_comment;

/*==============================================================*/
/* Table: t_comment                                             */
/*==============================================================*/
CREATE TABLE t_comment
(
  id          CHAR(32) NOT NULL,
  ip          VARCHAR(32),
  content     TEXT,
  create_date DATETIME,
  user_id     CHAR(32),
  deleted     TINYINT(1),
  secret      TINYINT(1),
  PRIMARY KEY (id)
);

ALTER TABLE t_comment
  ADD CONSTRAINT FK_fk_comment_user_id FOREIGN KEY (user_id)
REFERENCES t_user (id)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;
