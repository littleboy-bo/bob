package cn.com.bob.learn.base.model;

import java.io.Serializable;

public class BaseData implements Serializable {

  private String id;
  private String desc;
  private String name;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
