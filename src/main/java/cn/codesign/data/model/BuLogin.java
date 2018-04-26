package cn.codesign.data.model;

import java.util.Date;

public class BuLogin {
    private String loginName;

    private Integer loginStatus;

    private Date loginData;

    private Integer loginCount;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Date getLoginData() {
        return loginData;
    }

    public void setLoginData(Date loginData) {
        this.loginData = loginData;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }
}