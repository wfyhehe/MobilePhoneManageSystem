DROP INDEX index_action_name
ON t_action_menu;

DROP TABLE IF EXISTS t_action_menu;

/*==============================================================*/
/* Table: t_action_menu                                         */
/*==============================================================*/
CREATE TABLE t_action_menu
(
  id         INT NOT NULL AUTO_INCREMENT,
  action_url VARCHAR(64),
  menu_id    CHAR(32),
  PRIMARY KEY (id)
);

/*==============================================================*/
/* Index: index_action_name                                     */
/*==============================================================*/
CREATE INDEX index_action_name
  ON t_action_menu
  (
    action_url
  );

ALTER TABLE t_action_menu
  ADD CONSTRAINT FK_fk_am_action_id FOREIGN KEY (action_url)
REFERENCES t_action (url)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE t_action_menu
  ADD CONSTRAINT FK_fk_am_menu_id FOREIGN KEY (menu_id)
REFERENCES t_menu (id)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;
