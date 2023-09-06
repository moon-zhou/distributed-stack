-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "t_user";
CREATE TABLE "t_user"
(
    "id"   int8           NOT NULL CONSTRAINT user_pk PRIMARY KEY,
    "user_no" varchar(64) NOT NULL,
    "name" varchar(255)   NOT NULL,
    "age"  int4           NOT NULL
);

DROP TABLE IF EXISTS "t_user_auth";
CREATE TABLE "t_user_auth"
(
    "id"              int8        NOT NULL CONSTRAINT user_auth_pk PRIMARY KEY,
    "user_no"         varchar(64)  NOT NULL,
    "id_no"           varchar(32)  NOT NULL,
    "id_name"         varchar(256) NOT NULL,
    "id_birth_date"   DATE NOT NULL,
    "id_start_date"   varchar(16),
    "id_end_date"     varchar(16),
    "id_long_term"    char(1),
    "id_native_place" varchar(256) NOT NULL
);