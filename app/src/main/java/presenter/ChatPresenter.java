package presenter;

import base.IBaseRequestCallBack;
import entity.userFriendObj;
import entity.userObj;

public interface ChatPresenter {


    /*
    * 发送聊天消息
    * */
    void sendMsg(userObj cUser,String Msg,Integer friendId,String groupName);

    /*
    * 连接进入一对一聊天服务器
    * */
    void getOneToOneChat(userFriendObj cUser, Integer friendId);

    /*
    * 连接用户服
    * */
    void receiveMsg(userObj cUser);

    /*
    * 销毁断开连接
    * */
    void destoryDisconnect(userObj cUser);

    /*
    * 指定页数获取当前聊天记录
    * */
    void getChatMsgData(String groupName,Integer page);

    /*
     * 注销subscribe
     * */
    void unSubscribe();
}
