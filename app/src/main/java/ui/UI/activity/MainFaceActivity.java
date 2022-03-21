package ui.UI.activity;

import androidx.annotation.ArrayRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.widget.ViewPager2;
import base.BaseActivity;
import constants.chatAboutConstants;
import entity.cUserObj;
import entity.userMsgObj;
import presenter.ChatPresenterImp;
import ui.UI.adapter.FriendListAdapter;
import ui.UI.fragment.FriendListFragment;
import ui.UI.fragment.userSettingFragment;
import view.ChatView;
import zhh.mvpchatroom.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import ui.UI.adapter.mainFaceFragmentAdapter;

public class MainFaceActivity extends BaseActivity<ChatPresenterImp, ChatView> implements ChatView {

    private ViewPager2 mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private userSettingFragment mUserSettingFragment;
    private FriendListFragment friendListFragment;
    private ChatPresenterImp chatPresenterImp;
    private cUserObj cUser;
    private ArrayList<Integer> onlineFriends;
    private FriendListAdapter friendListAdapter;

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
        fragments.add(mUserSettingFragment.newInstance());
        mainFaceFragmentAdapter pagerAdapter = new mainFaceFragmentAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        mViewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void loadDataSuccess(userMsgObj tData) {
        if(tData.getCode() == chatAboutConstants.chatMsgObj.ENTERROOM_SUCCESS){
            this.onlineFriends = tData.getUserList();
            ChangeFriendStatus(onlineFriends);
        }
        if(tData.getCode() == chatAboutConstants.chatMsgObj.LEAVEROOM_SUCCESS){
            this.onlineFriends = tData.getUserList();
            ChangeFriendStatus(onlineFriends);
        }
    }

    @Override
    protected void onDestroy() {
        chatPresenterImp.destoryDisconnect(cUserObj.getInstance());
        chatPresenterImp.unSubscribe();//activity注销时释放监听
        super.onDestroy();
    }

    //调用更新Fragment内的用户状态，避免在渲染完成前进行传参
    private void ChangeFriendStatus(ArrayList<Integer> onlineUserList){
        friendListFragment.ChangeFriendStatus(onlineUserList);
    }

}