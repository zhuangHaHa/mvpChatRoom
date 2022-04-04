package ui.UI.activity;

import base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import base.BaseResponse;
import entity.userFriendObj;
import entity.userMsgObj;
import entity.userObj;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import model.ChatModelImp;
import presenter.ChatPresenter;
import presenter.ChatPresenterImp;
import ui.UI.adapter.ChatAdapter;
import view.ChatView;
import zhh.mvpchatroom.R;
import constants.chatAboutConstants;
import entity.cUserObj;
public class ChatActivity extends BaseActivity<ChatPresenterImp, ChatView> implements ChatView {

    private Button getChatMsgInfo;
    private Button sendMsgBtn;
    private EditText msgTextEt;
    private LinearLayoutManager layoutManager;

    RecyclerView recyclerView;
    ChatAdapter chatAdapter;
    ArrayList<userMsgObj> msgList;

    private ChatPresenterImp chatPresenterImp;
    private cUserObj cUser;
    private userMsgObj userMsgObj;

    private String chatMsg;

    private Integer friendId;
    private String groupName;
    private Bundle getChatDataBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        receiveConnect();

        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg();
            }
        });
    }
    //初始化监听、并且接收消息,发送连接消息
    private void receiveConnect(){
        userFriendObj userObj = new userFriendObj();
        userObj.setId(cUser.getId());
        chatPresenterImp.getOneToOneChat(userObj,this.friendId);
    }

    private void sendMsg(){
        chatMsg = msgTextEt.getText().toString();
        chatPresenterImp.sendMsg(cUser,chatMsg,friendId,groupName);
        msgTextEt.setText("");
    }

    @Override
    public void initView() {
        this.sendMsgBtn = (Button)findViewById(R.id.sendMsg);
        this.msgTextEt = (EditText)findViewById(R.id.msgText);
        this.msgList = new ArrayList<>();
        this.recyclerView = (RecyclerView)findViewById(R.id.chatRecycleView);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        //创建适配器
        this.chatAdapter = new ChatAdapter(msgList);
        this.recyclerView.setAdapter(chatAdapter);
        this.cUser = cUserObj.getInstance();
        this.userMsgObj = new userMsgObj();

        this.getChatDataBundle = getIntent().getExtras();
        this.groupName = getChatDataBundle.getString("groupName");
        this.friendId = getChatDataBundle.getInt("friendId");
        chatPresenterImp = new ChatPresenterImp();
        this.chatPresenterImp.attachView(this);

        this.getChatMsgInfo = (Button)findViewById(R.id.id_chatGetChatInfo);
        getChatMsgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatPresenterImp.getChatMsgData(groupName,1);
            }
        });
    }

    @Override
    public ChatPresenterImp createPresenter() {
        return new ChatPresenterImp();
    }

    @Override
    public ChatView createView() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatPresenterImp.unSubscribe();//activity注销时释放监听
    }

    /*
    * 请求成功的回调
    * q*/
    @Override
    public void loadDataSuccess(BaseResponse<userMsgObj> tData) {
        //更新房间用户
        if(tData.getCode() == chatAboutConstants.chatMsgObj.ENTERROOM_SUCCESS){
            return;
        }

        //处理收到的最新消息
        if(tData.getCode() == chatAboutConstants.chatMsgObj.CHATMSG_RECEIVE_SUCCESS){
            userMsgObj = tData.getData();
            userMsgObj.setNickname(tData.getData().getNickname());
            userMsgObj.setId(tData.getData().getId());
            userMsgObj.setMsg(tData.getData().getMsg());
            msgList.add(userMsgObj);
            chatAdapter.notifyItemInserted(msgList.size()-1);
            recyclerView.scrollToPosition(msgList.size()-1);
        }

        if(tData.getCode() == chatAboutConstants.chatMsgObj.GETCHATMSGDATA_SUCCESS){
            ArrayList<userMsgObj> userMsgObjs = tData.getDataList();
            for(int i=userMsgObjs.size()-1;i>0;i--){
                msgList.add(userMsgObjs.get(i));
            }
            chatAdapter.notifyItemInserted(msgList.size()-1);
            recyclerView.scrollToPosition(msgList.size()-1);
        }

    }
}
