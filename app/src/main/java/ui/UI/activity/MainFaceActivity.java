package ui.UI.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.widget.ViewPager2;
import ui.UI.fragment.FriendListFragment;
import ui.UI.fragment.userSettingFragment;
import zhh.mvpchatroom.R;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import ui.UI.adapter.mainFaceFragmentAdapter;

public class MainFaceActivity extends FragmentActivity {

    private ViewPager2 mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private userSettingFragment mUserSettingFragment;
    private FriendListFragment friendListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_face);
        initPager();
    }

    private void initPager(){
        mUserSettingFragment = new userSettingFragment();
        mViewPager = findViewById(R.id.id_fragmentShow);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(mUserSettingFragment.newInstance());
        fragments.add(friendListFragment.newInstance());
        mainFaceFragmentAdapter pagerAdapter = new mainFaceFragmentAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        mViewPager.setAdapter(pagerAdapter);
    }

}