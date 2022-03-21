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
import service.Config;
import service.RxWebSocket;
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
    public void sendMsg(userObj cUser, String msg,Integer friendId,String groupName) {
        userMsg.setCode(chatAboutConstants.chatMsgObj.CHATMSG_SEND_SUCCESS);
        userMsg.setId(cUser.getId());
        userMsg.setMsg(msg);
        userMsg.setFriendId(friendId);
        userMsg.setGroupName(groupName);
        String msgJson = gson.toJson(userMsg);
        Log.i("msgJson:",msgJson);
        if(userMsg!=null){
            RxWebSocket.send(ApiConfig.channelURL,msgJson);
        }
    }

    //玩家Activity销毁，通知服务器下线
    @Override
    public void destoryDisconnect(userObj cUser) {
        cUser.setCode(chatAboutConstants.chatMsgObj.LEAVEROOM_SUCCESS);
        String disConnectJson = sendMsgDealer(cUser,null,chatAboutConstants.chatMsgObj.LEAVEROOM_SUCCESS);
        if (disConnectJson != null) {
            RxWebSocket.send(ApiConfig.channelURL,disConnectJson);
        }else{
            Log.e("ChatModelImp","断开cs请求发送失败，检查发送协议");
        }
    }


    /*
    建立连接，发送通信成功消息
    * */
    @Override
    public void  receiveMsg(final userObj cUser, final IBaseRequestCallBack iBaseRequestCallBack) {
        RxWebSocket.get(ApiConfig.channelURL)
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    protected void onOpen(@NonNull WebSocket webSocket) {
                        cUser.setCode(chatAboutConstants.chatMsgObj.ENTERROOM_SUCCESS);
                        String setConnectJson = sendMsgDealer(cUser,null,chatAboutConstants.chatMsgObj.ENTERROOM_SUCCESS);
                        if(setConnectJson!=null){
                            RxWebSocket.send(ApiConfig.channelURL,setConnectJson);
                        }else{
                            Log.e("ChatModelImp","连接cs请求发送失败，检查发送协议");
                        }
                    }

                    @Override
                    protected void onMessage(@NonNull String text) {
                        userMsgObj revUserMsgObj = revMsgDealer(text);
                        if(revUserMsgObj.getCode() == chatAboutConstants.chatMsgObj.CHATMSG_RECEIVE_SUCCESS){
                            iBaseRequestCallBack.requestSuccess(revUserMsgObj);
                        }
                        if(revUserMsgObj.getCode() == chatAboutConstants.chatMsgObj.ENTERROOM_SUCCESS){
                            iBaseRequestCallBack.requestSuccess(revUserMsgObj);
                        }
                        if(revUserMsgObj.getCode() == chatAboutConstants.chatMsgObj.LEAVEROOM_SUCCESS){
                            iBaseRequestCallBack.requestSuccess(revUserMsgObj); //玩家好友下线通知
                        }
                    }

                    //服务器挂掉回调
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
    * 发送消息类
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
        if(code == chatAboutConstants.chatMsgObj.LEAVEROOM_SUCCESS){
            String userJson = gson.toJson(tUser,userObj.class);
            return userJson;
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
