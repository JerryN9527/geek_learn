package com.example.spring.po;


public class UserAddr {

  private long id;
  private String addrContent;
  private long userId;
  private String userPhone;
  private String userContact;
  private String sign;
  private long isDefault;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getAddrContent() {
    return addrContent;
  }

  public void setAddrContent(String addrContent) {
    this.addrContent = addrContent;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }


  public String getUserContact() {
    return userContact;
  }

  public void setUserContact(String userContact) {
    this.userContact = userContact;
  }


  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }


  public long getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(long isDefault) {
    this.isDefault = isDefault;
  }

}
