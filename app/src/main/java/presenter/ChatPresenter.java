package presenter;

import base.IBaseRequestCallBack;
import entity.userObj;

public interface ChatPresenter {


    /*
    * 发送聊天消息
    * */
    void sendMsg(userObj cUser,String Msg);

    void receiveMsg(userObj cUser);

    /*
     * 注销subscribe
     * */
    void unSubscribe();
}
