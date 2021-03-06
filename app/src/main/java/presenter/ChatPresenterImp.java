package presenter;

import android.content.Intent;
import android.util.Log;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import androidx.annotation.NonNull;
import base.BasePresenter;
import base.BaseResponse;
import base.IBaseRequestCallBack;
import config.ApiConfig;
import entity.cUserObj;
import entity.userFriendObj;
import entity.userMsgObj;
import entity.userObj;
import model.ChatModelImp;
import model.LoginModelImp;
import service.RxWebSocket;
import service.WebSocketInfo;
import service.WebSocketSubscriber;
import view.ChatView;
import view.LoginView;

public class ChatPresenterImp extends BasePresenter<ChatView, BaseResponse<userMsgObj>> implements ChatPresenter{

    ChatModelImp chatModelImp;

    public ChatPresenterImp(){
        this.chatModelImp = new ChatModelImp();
    }



    @Override
    public void sendMsg(userObj cUser, String Msg,Integer friendId,String groupName) {
        if(cUser!=null && !Msg.equals(null) && getmView()!=null){
                chatModelImp.sendMsg(cUser,Msg,friendId,groupName);
        }
    }

    @Override
    public void getOneToOneChat(userFriendObj cUser, Integer friendId) {
        if(cUser!=null && cUser!=null && friendId!=null){
            chatModelImp.getOneToOneChat(cUser,friendId,this);
        }
    }


    @Override
    public void receiveMsg(userObj cUser) {
        if(getmView()!=null){
            chatModelImp.receiveMsg(cUser,this);
        }
    }

    @Override
    public void destoryDisconnect(userObj cUser) {
        if(getmView()!=null){
            chatModelImp.destoryDisconnect(cUser);
        }
    }

    @Override
    public void getChatMsgData(String groupName, Integer page) {
        if(getmView()!=null){
            chatModelImp.getChatMsgData(groupName,page,this);
        }
    }


    @Override
    public void unSubscribe() {
        chatModelImp.onUnsubscribe();
    }
}
