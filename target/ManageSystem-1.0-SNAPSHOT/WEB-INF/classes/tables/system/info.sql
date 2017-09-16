DROP TABLE IF EXISTS t_info;

/*==============================================================*/
/* Table: t_info                                                */
/*==============================================================*/
CREATE TABLE t_info
(
  id       CHAR(32) NOT NULL,
  content  TEXT,
  position VARCHAR(32),
  PRIMARY KEY (id)
);
