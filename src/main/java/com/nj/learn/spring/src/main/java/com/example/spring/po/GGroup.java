package com.example.spring.po;


public class GGroup {

  private long id;
  private String groupName;
  private String gCommit;
  private String gAbout;
  private String gImageUrl;
  private String delSign;
  private java.sql.Timestamp creatTime;
  private java.sql.Timestamp updateTime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }


  public String getGCommit() {
    return gCommit;
  }

  public void setGCommit(String gCommit) {
    this.gCommit = gCommit;
  }


  public String getGAbout() {
    return gAbout;
  }

  public void setGAbout(String gAbout) {
    this.gAbout = gAbout;
  }


  public String getGImageUrl() {
    return gImageUrl;
  }

  public void setGImageUrl(String gImageUrl) {
    this.gImageUrl = gImageUrl;
  }


  public String getDelSign() {
    return delSign;
  }

  public void setDelSign(String delSign) {
    this.delSign = delSign;
  }


  public java.sql.Timestamp getCreatTime() {
    return creatTime;
  }

  public void setCreatTime(java.sql.Timestamp creatTime) {
    this.creatTime = creatTime;
  }


  public java.sql.Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Timestamp updateTime) {
    this.updateTime = updateTime;
  }

}
