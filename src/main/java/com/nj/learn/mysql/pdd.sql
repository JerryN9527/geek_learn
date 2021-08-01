/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/8/1 12:33:24                            */
/*==============================================================*/


drop table if exists account;

drop table if exists g_goods;

drop table if exists g_goods_group;

drop table if exists g_group;

drop table if exists user_addr;

drop table if exists user_info;

drop table if exists user_order;

/*==============================================================*/
/* Table: account                                               */
/*==============================================================*/
create table account
(
   id                   int not null,
   username             varchar(64),
   password             varchar(64),
   user_type            bit,
   status               bit,
   primary key (id)
);

alter table account comment '账户';

/*==============================================================*/
/* Table: g_goods                                               */
/*==============================================================*/
create table g_goods
(
   id                   int not null,
   g_name               varchar(64),
   g_unit_price         double,
   g_commit             varchar(200),
   g_about              varchar(100),
   g_image_url          varchar(1000),
   del_sign             bit,
   creat_time           datetime,
   update_time          datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: g_goods_group                                         */
/*==============================================================*/
create table g_goods_group
(
   id                   int not null,
   goods_id             int,
   group_id             int,
   primary key (id)
);

/*==============================================================*/
/* Table: g_group                                               */
/*==============================================================*/
create table g_group
(
   id                   int not null,
   group_name           varchar(64),
   g_commit             varchar(200),
   g_about              varchar(100),
   g_image_url          varchar(1000),
   del_sign             bit,
   creat_time           datetime,
   update_time          datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: user_addr                                             */
/*==============================================================*/
create table user_addr
(
   id                   int not null,
   addr_content         varchar(1000),
   user_id              int,
   user_phone           varchar(11),
   user_contact         varchar(64),
   sign                 bit,
   is_default           boolean,
   primary key (id)
);

/*==============================================================*/
/* Table: user_info                                             */
/*==============================================================*/
create table user_info
(
   id                   int not null,
   name                 varchar(64),
   phone                varchar(11),
   username             varchar(64),
   default_addr_id      int,
   primary key (id)
);

alter table user_info comment '用户基本详情表';

/*==============================================================*/
/* Table: user_order                                            */
/*==============================================================*/
create table user_order
(
   id                   int not null,
   user_id              int,
   user_info_name       varchar(64),
   user_info_phone      varchar(11),
   group_id             char(10),
   group_name           varchar(64),
   goods_id             char(10),
   goods_name           varchar(64),
   order_addr           varchar(1000),
   order_status         bit,
   count                int,
   total_pirce          double,
   pirce                double,
   creat_time           datetime,
   update_time          datetime,
   primary key (id)
);

