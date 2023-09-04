/*
 Navicat Premium Data Transfer

 Source Server         : tms learning int
 Source Server Type    : PostgreSQL
 Source Server Version : 110016
 Source Host           : pgm-2zeb0v6cch2ktv90117930.pg.rds.aliyuncs.com:5432
 Source Catalog        : serviceplatform
 Source Schema         : ali-tmslearning-int

 Target Server Type    : PostgreSQL
 Target Server Version : 110016
 File Encoding         : 65001

 Date: 01/09/2023 17:51:26
*/


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "user";
CREATE TABLE "user"
(
    "id"   int8                                        NOT NULL  CONSTRAINT user_pk PRIMARY KEY,
    "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "age"  int4                                        NOT NULL
);
