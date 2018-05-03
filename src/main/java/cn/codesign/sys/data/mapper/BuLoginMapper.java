package cn.codesign.sys.data.mapper;


import cn.codesign.sys.data.model.BuLogin;

public interface BuLoginMapper {

    BuLogin getLogin(String uid);

    void insertLoginInfo(String uid);

    void updateLoginInfo(String uid);

    void clearLoginInfo();
}