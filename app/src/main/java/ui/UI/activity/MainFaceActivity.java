package ui.UI.activity;

import androidx.annotation.ArrayRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.widget.ViewPager2;
import base.BaseActivity;
import base.BaseResponse;
import constants.chatAboutConstants;
import entity.cUserObj;
import entity.userMsgObj;
import presenter.ChatPresenterImp;
import ui.UI.adapter.FriendListAdapter;
import ui.UI.fragment.FriendApplyFragment;
import ui.UI.fragment.FriendListFragment;
import ui.UI.fragment.userSettingFragment;
import view.ChatView;
import zhh.mvpchatroom.R;

import android.os.Bundle;

import com.google.gson.Gson;

import ui.UI.fragment.FriendApplyFragment;

import java.util.ArrayList;
import java.util.List;
import ui.UI.adapter.mainFaceFragmentAdapter;

public class MainFaceActivity extends BaseActivity<ChatPresenterImp, ChatView> implements ChatView,FriendApplyFragment.CallBackListener {

    private ViewPager2 mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private userSettingFragment mUserSettingFragment;
    private FriendListFragment friendListFragment;
    private FriendApplyFragment friendApplyFragment;
    private ChatPresenterImp chatPresenterImp;
    private cUserObj cUser;
    private ArrayList<Integer> onlineFriends;
    private FriendListAdapter friendListAdapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_face);
        initView();
        initPager();
        receiveConnect();

    }

    private void receiveConnect(){
        chatPresenterImp.receiveMsg(cUser);
    }

    @Override
    public void initView() {
        cUser = cUserObj.getInstance();
        chatPresenterImp = new ChatPresenterImp();
        this.onlineFriends = new ArrayList<>();
        this.chatPresenterImp.attachView(this);
        this.friendListFragment = FriendListFragment.newInstance();
        this.friendApplyFragment = FriendApplyFragment.newInstance();//?????????????????????????????????????????????????????????
        this.gson = new Gson();
    }

    @Override
    public ChatPresenterImp createPresenter() {
        return new ChatPresenterImp();
    }

    @Override
    public ChatView createView() {
        return this;
    }

    private void initPager(){
        mUserSettingFragment = new userSettingFragment();
        mViewPager = findViewById(R.id.id_fragmentShow);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(friendListFragment);
        fragments.add(friendApplyFragment);
        fragments.add(mUserSettingFragment.newInstance());
        mainFaceFragmentAdapter pagerAdapter = new mainFaceFragmentAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        mViewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void loadDataSuccess(BaseResponse<userMsgObj> tData) {
        if(tData.getCode() == chatAboutConstants.chatMsgObj.ENTERROOM_SUCCESS){
            this.onlineFriends = tData.getData().getUserList();
            ChangeFriendStatus(onlineFriends);
        }
        if(tData.getCode() == chatAboutConstants.chatMsgObj.LEAVEROOM_SUCCESS){
            this.onlineFriends = tData.getData().getUserList();
            ChangeFriendStatus(onlineFriends);
        }
    }

    @Override
    protected void onDestroy() {
        chatPresenterImp.destoryDisconnect(cUserObj.getInstance());
        chatPresenterImp.unSubscribe();//activity?????????????????????
        super.onDestroy();
    }

    //????????????Fragment?????????????????????????????????????????????????????????
    private void ChangeFriendStatus(ArrayList<Integer> onlineUserList){
        friendListFragment.ChangeFriendStatus(onlineUserList);
    }


    //??????friendApplyFragment??????friendList?????????
    @Override
    public void updateFriendList() {
        friendListFragment.getUserFriendList();
    }
}