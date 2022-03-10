package model;

import base.IBaseRequestCallBack;
import entity.userObj;

public interface LoginModel<T> {


    /*
    登录功能
    * */
    void userLogin(String username, String password, IBaseRequestCallBack<T> iBaseRequestCallBack);

    /*
    * 注销功能
    * */
    void userLoginOut(String username);

    /*
    * 注销subscribe
    * */
    void onUnsubscribe();
}
