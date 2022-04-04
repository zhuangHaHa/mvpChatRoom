package ui.UI.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import base.BaseFragment;
import entity.cUserObj;
import entity.userFriendObj;
import entity.userObj;
import presenter.Fragment.FriendListPresenterImp;
import ui.UI.activity.ChatActivity;
import ui.UI.adapter.FriendListAdapter;
import view.Fragment.FriendListView;
import zhh.mvpchatroom.R;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendListFragment extends BaseFragment<FriendListPresenterImp, FriendListView> implements FriendListView  {
    View rootView;
    ArrayList<userObj> myFriendUserList;
    private LinearLayoutManager layoutManager;
    private RecyclerView friendListRecyclerView;
    private FriendListAdapter friendListAdapter;
    private FriendListPresenterImp friendListPresenterImp;
    private ArrayList<Integer> userFriendsStatus  = new ArrayList<>();
    private Context fragmentContext;
    private Bundle chatBundle;
    private Intent toChatActivity;


    public FriendListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FriendListBlank.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendListFragment newInstance() {
        FriendListFragment fragment = new FriendListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_friend_list_blank, container, false);
        }
        initView();
        initAdapter();
        return rootView;
    }

    private void initAdapter() {
        this.friendListAdapter = new FriendListAdapter(myFriendUserList,this);
        this.friendListRecyclerView.setAdapter(friendListAdapter);
    }

    public void initView(){
        myFriendUserList = new ArrayList<>();
        this.friendListRecyclerView = rootView.findViewById(R.id.friendListRecycleView);
        this.layoutManager = new LinearLayoutManager(getContext());
        this.friendListRecyclerView.setLayoutManager(layoutManager);
        this.friendListPresenterImp = new FriendListPresenterImp();
        friendListPresenterImp.attachView(this);
        getUserFriendList();
        this.toChatActivity = new Intent(getContext(), ChatActivity.class);
        this.chatBundle = new Bundle();
    }

    /*
    * 获得好友列表
    * */
    public void getUserFriendList() {
        myFriendUserList.clear();
        userObj mUser = cUserObj.getInstance();
        if(mUser!=null){
            friendListPresenterImp.getFriendList(mUser);
        }
    }


    @Override
    public FriendListPresenterImp createPresenter() {
        return new FriendListPresenterImp();
    }

    @Override
    public FriendListView createView() {
        return this;
    }

    /*
    * 收发当前好友状态
    * */
    public void ChangeFriendStatus(ArrayList<Integer> friendStatus) {
        this.userFriendsStatus = friendStatus;
        updateFriendStatus(userFriendsStatus);
    }
    //先将玩家数据保存在内存，fragment实例化后再更新状态
    //friendStatus 在线的好友列表
    //myFriendUserList 所有的好友列表
    private void updateFriendStatus(ArrayList<Integer> onLineUsers){
        for(int i=0;i<myFriendUserList.size();i++){
            for(int j=0;j<onLineUsers.size();j++){
                if(onLineUsers.get(j) == myFriendUserList.get(i).getId()){
                    myFriendUserList.get(i).setStatus(1);
                    break;
                }
                if(onLineUsers.get(j) != myFriendUserList.get(i).getId()){
                    myFriendUserList.get(i).setStatus(0);
                }
            }
        }
        friendListAdapter.notifyDataSetChanged();
    }

    public void getChatFriendUser(userObj tUser){
        if(tUser!=null){
            friendListPresenterImp.getGroupName(cUserObj.getInstance(),tUser);
        }else{
            Log.e("FriendListFragment","你选的好友他妈的有毒赶快查查");
        }
    }


    @Override
    public void loadDataSuccess(userFriendObj tData) {
        if(tData.getGroupName()==null){
            myFriendUserList.add(tData);
            friendListAdapter.notifyItemInserted(myFriendUserList.size()-1);
        }
        if(tData.getGroupName()!=null){
            chatBundle.putString("groupName",tData.getGroupName());
            chatBundle.putInt("friendId",tData.getFriendsId());
            toChatActivity.putExtras(chatBundle);
            startActivity(toChatActivity);
        }

    }

}