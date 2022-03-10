package model;

import android.util.Log;

import com.google.gson.Gson;
import com.trello.rxlifecycle2.android.ActivityEvent;

import androidx.annotation.NonNull;
import base.IBaseRequestCallBack;
import config.ApiConfig;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import okhttp3.WebSocket;
import service.RxWebSocket;
import service.WebSocketInfo;
import service.WebSocketSubscriber;
import entity.userObj;
import entity.userMsgObj;
import constants.chatAboutConstants;

public class ChatModelImp implements ChatModel<userMsgObj> {

    private CompositeDisposable mCompositeDisposable;
    private Gson gson;
    private userMsgObj userMsg;
    private userMsgObj revUserMsg;

    public ChatModelImp(){
        this.gson = new Gson();
        this.userMsg = new userMsgObj();
        this.revUserMsg = new userMsgObj();
    }

    @Override
    public void sendMsg(userObj cUser, String msg) {
        userMsg.setCode(chatAboutConstants.chatMsgObj.CHATMSG_SEND_SUCCESS);
        userMsg.setId(cUser.getId());
        userMsg.setMsg(msg);
        String msgJson = gson.toJson(userMsg);
        Log.i("msgJson:",msgJson);
        if(userMsg!=null){
            RxWebSocket.send(ApiConfig.channelURL,msgJson);
        }
    }


    /*
    建立连接，发送通信成功消息
    * */
    @Override
    public void receiveMsg(final userObj cUser, final IBaseRequestCallBack iBaseRequestCallBack) {
        RxWebSocket.get(ApiConfig.channelURL)
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    protected void onOpen(@NonNull WebSocket webSocket) {
                        cUser.setCode(chatAboutConstants.chatMsgObj.ENTERROOM_SUCCESS);
                        String setConnectJson = sendMsgDealer(cUser,null,chatAboutConstants.chatMsgObj.ENTERROOM_SUCCESS);
                        if(setConnectJson!=null){
                            RxWebSocket.send(ApiConfig.channelURL,setConnectJson);
                        }
                        Log.i("建立连接的User",cUser.getUsername());
                    }

                    @Override
                    protected void onMessage(@NonNull String text) {
                        userMsgObj revUserMsgObj = revMsgDealer(text);
                        if(revUserMsgObj.getCode() == chatAboutConstants.chatMsgObj.CHATMSG_RECEIVE_SUCCESS){
                            iBaseRequestCallBack.requestSuccess(revUserMsgObj);
                        }
                    }

                    @Override
                    protected void onClose() {
                        super.onClose();
                    }
                });
    }

    @Override
    public void onUnsubscribe() {
        RxWebSocket.get(ApiConfig.channelURL)
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i("ChatModelImp","注销chat的Observe监听");
                    }
                });
    }

    /*
    * 处理接收消息类
    * */
    private String sendMsgDealer(userObj tUser,userMsgObj tMsg,Integer code){
        if(code == chatAboutConstants.chatMsgObj.ENTERROOM_SUCCESS){
            String userJson = gson.toJson(tUser,userObj.class);
            return userJson;
        }
        if(code == chatAboutConstants.chatMsgObj.CHATMSG_RECEIVE_SUCCESS){
            String msgJson = gson.toJson(tMsg,userMsgObj.class);
            return msgJson;
        }
        return null;
    }
    /**
     * 处理消息类
     */
    private userMsgObj revMsgDealer(String text){
        revUserMsg = gson.fromJson(text,userMsgObj.class);
        return revUserMsg;
    }

}
