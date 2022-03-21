package ui.UI.fragment;

import android.content.Context;
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
import entity.userObj;
import presenter.Fragment.FriendListPresenterImp;
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
        this.friendListAdapter = new FriendListAdapter(myFriendUserList);
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
    }

    private void getUserFriendList() {
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

    @Override
    public void loadDataSuccess(ArrayList<userObj> tData) {
       for(int i=0;i<tData.size();i++){
           myFriendUserList.add(tData.get(i));
       }
       friendListAdapter.notifyItemInserted(myFriendUserList.size()-1);
    }


    public void ChangeFriendStatus(ArrayList<Integer> friendStatus) {
        this.userFriendsStatus = friendStatus;
        updateFriendStatus(userFriendsStatus);
    }
    //先将玩家数据保存在内存，fragment实例化后再更新状态
    //friendStatus 在线的好友列表
    //myFriendUserList 所有的好友列表
    private void updateFriendStatus(ArrayList<Integer> onLineUsers){
        Log.i("当前在线的玩家用户",onLineUsers.toString());
        Log.i("用户好友列表",myFriendUserList.toString());
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
}