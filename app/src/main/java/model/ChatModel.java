package model;

import base.IBaseRequestCallBack;
import entity.userObj;
import entity.userFriendObj;
/*
* 用来收发chatroom的消息
* */
public interface ChatModel<T> {

    /*
    * 发送消息
    * */
    void sendMsg(userObj cUser,String msg,Integer friendId,String groupName);


    /*
     * 连接进入一对一聊天服务器
     * */
    void getOneToOneChat(userFriendObj cUser,Integer friendId,IBaseRequestCallBack iBaseRequestCallBack);


    /*
    * Activity销毁时断开连接
    * */
    void destoryDisconnect(userObj cUser);

    /*
    * 接收消息
    * */
    void receiveMsg(userObj cUser,IBaseRequestCallBack iBaseRequestCallBack);

    /*
    * 获取数据库保存的消息
    * groupName:当前一起通话的用户，page:当前请求的聊天消息页数
    * */
    void getChatMsgData(String groupName,Integer page, IBaseRequestCallBack iBaseRequestCallBack);

    /*
     * 注销subscribe
     * */
    void onUnsubscribe();

}
