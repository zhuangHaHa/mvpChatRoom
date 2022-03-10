package model;

import base.IBaseRequestCallBack;
import entity.userObj;
/*
* 用来收发chatroom的消息
* */
public interface ChatModel<T> {

    /*
    * 发送消息
    * */
    void sendMsg(userObj cUser,String msg);

    /*
    * 接收消息
    * */
    void receiveMsg(userObj cUser,IBaseRequestCallBack iBaseRequestCallBack);

    /*
     * 注销subscribe
     * */
    void onUnsubscribe();

}
