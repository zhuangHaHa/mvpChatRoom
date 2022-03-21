package presenter;

import base.IBaseRequestCallBack;
import entity.userObj;

public interface ChatPresenter {


    /*
    * 发送聊天消息
    * */
    void sendMsg(userObj cUser,String Msg,Integer friendId,String groupName);

    /*
    * 建立连接&接收消息
    * */
    void receiveMsg(userObj cUser);

    /*
    * 销毁断开连接
    * */
    void destoryDisconnect(userObj cUser);

    /*
     * 注销subscribe
     * */
    void unSubscribe();
}
