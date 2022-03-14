package model.Fragment;

import base.IBaseRequestCallBack;
import entity.userObj;

public interface UserSettingModel<T> {
    /*
     * 发送消息
     * */
    void getUserInfo(userObj cUser,IBaseRequestCallBack iBaseRequestCallBack);

    /*
     * 接收消息
     * */
    void updateUserInfo(userObj cUser, IBaseRequestCallBack iBaseRequestCallBack);

    /*
     * 注销subscribe
     * */
    void onUnsubscribe();

}
