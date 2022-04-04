package model;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import base.BaseResponse;
import base.IBaseRequestCallBack;
import config.ApiConfig;
import constants.chatAboutConstants;
import entity.userFriendObj;
import entity.userMsgObj;
import entity.userObj;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import okhttp3.WebSocket;
import service.Loader.chatLoader;
import service.RxWebSocket;
import service.WebSocketSubscriber;
import service.tools.getRequestBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;

public class ChatModelImp implements ChatModel<BaseResponse<userMsgObj>> {

    private CompositeDisposable mCompositeDisposable;
    private Gson gson;
    private userMsgObj userMsg;
    private BaseResponse<userMsgObj> userMsgObj;
    private chatLoader mchatLoader;

    public ChatModelImp(){
        this.gson = new Gson();
        this.userMsg = new userMsgObj();
        this.userMsgObj = new BaseResponse<>();
        this.mCompositeDisposable = new CompositeDisposable();
        this.mchatLoader = new chatLoader();
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
            RxWebSocket.send(ApiConfig.chatChannelUrl,msgJson);
        }
    }

    @Override
    public void getOneToOneChat(final userFriendObj cUser, final Integer friendId,final IBaseRequestCallBack iBaseRequestCallBack) {
        RxWebSocket.get(ApiConfig.chatChannelUrl)
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    protected void onOpen(@NonNull WebSocket webSocket) {
                        cUser.setCode(chatAboutConstants.chatMsgObj.CHAT_WITHOTHERS);
                        cUser.setFriendsId(friendId);
                        String setChatConnectJson = sendMsgDealer(cUser,null,chatAboutConstants.chatMsgObj.CHAT_WITHOTHERS);
                        if(setChatConnectJson!=null){
                            RxWebSocket.send(ApiConfig.chatChannelUrl,setChatConnectJson);
                        }else{
                            Log.e("ChatModelImp","连接cs请求发送失败，检查发送协议");
                        }
                    }

                    @Override
                    protected void onMessage(@NonNull String text) {
                        BaseResponse<userMsgObj> revUserMsgObj = revMsgDealer(text);
                        iBaseRequestCallBack.requestSuccess(revUserMsgObj);
                    }

                    @Override
                    protected void onClose() {
                        super.onClose();
                    }
                });
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
    连接进入用户服务器，用来更新当前服务器用户列表
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
                        System.out.println(text);
                        BaseResponse<userMsgObj> revUserMsgObj = revMsgDealer(text);
                        iBaseRequestCallBack.requestSuccess(revUserMsgObj);
                    }
                    //服务器挂掉回调
                    @Override
                    protected void onClose() {
                        super.onClose();
                    }
                });
    }

    @Override
    public void getChatMsgData(String groupName, Integer page, final IBaseRequestCallBack iBaseRequestCallBack) {
        HashMap<String,String> map = new HashMap<>();
        map.put("groupName",groupName);
        map.put("page",Integer.toString(page));
        RequestBody requestBody = new getRequestBody(gson.toJson(map).toString()).requestBodyBuilder();
        mCompositeDisposable.add(
                mchatLoader.getChatMsg(requestBody).subscribe(new Consumer<BaseResponse<userMsgObj>>() {
                    @Override
                    public void accept(BaseResponse<userMsgObj> userMsgObjBaseResponse) throws Exception {
                        iBaseRequestCallBack.requestSuccess(userMsgObjBaseResponse);
                    }
                })
        );

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
        if(mCompositeDisposable.isDisposed()){
            mCompositeDisposable.clear();//注销subscribe
            mCompositeDisposable.dispose();
        }
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
        if(code == chatAboutConstants.chatMsgObj.CHAT_WITHOTHERS){
            String userJson = gson.toJson(tUser,userFriendObj.class);
            return userJson;
        }
        return null;
    }
    /**
     * 处理消息类
     */
    public BaseResponse<userMsgObj> revMsgDealer(String text){
        //{"code": 2000, "data": {"id": 1, "userList": [1]}}
        //将该数据转变成BaseResponse<userMsgObj>对象
        try{
            BaseResponse<userMsgObj> msgObj = gson.fromJson(text, new TypeToken<BaseResponse<userMsgObj>>(){}.getType());
            return msgObj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
