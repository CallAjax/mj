package cn.codesign.data.mapper;

import cn.codesign.data.model.BuLogin;

public interface BuLoginMapper {

    BuLogin getLogin(String uid);

    void insertLoginInfo(String uid);

    void updateLoginInfo(String uid);

    void clearLoginInfo();
}