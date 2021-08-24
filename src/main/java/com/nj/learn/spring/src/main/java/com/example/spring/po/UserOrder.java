package com.example.spring.po;

import lombok.Data;

@Data
public class UserOrder {

  private long id;
  private long userId;
  private String userInfoName;
  private String userInfoPhone;
  private String groupId;
  private String groupName;
  private String goodsId;
  private String goodsName;
  private String orderAddr;
  private String orderStatus;
  private long count;
  private double totalPirce;
  private double pirce;
  private java.sql.Timestamp creatTime;
  private java.sql.Timestamp updateTime;



}
