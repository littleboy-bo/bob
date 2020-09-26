package cn.com.bob.learn.service.model;

import java.util.Date;

public class BobLearn {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID<br>
     * USER_ID<br>
     * seqNo:1<br>
     * dataType:String<br>
     * length:20<br>
     * cons:
     */
    private String userId;
    private Integer age;
    private String userName;
    private String address;
    private String phone;
    private Date tranDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Number getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }
}
