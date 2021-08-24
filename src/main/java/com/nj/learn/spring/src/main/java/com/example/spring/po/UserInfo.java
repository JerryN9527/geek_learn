package com.example.spring.po;


public class UserInfo {

  private long id;
  private String name;
  private String phone;
  private String username;
  private long defaultAddrId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public long getDefaultAddrId() {
    return defaultAddrId;
  }

  public void setDefaultAddrId(long defaultAddrId) {
    this.defaultAddrId = defaultAddrId;
  }

}
