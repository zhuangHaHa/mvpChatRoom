package ui.UI.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import base.BaseFragment;
import base.BaseResponse;
import entity.friendApplyObj;
import entity.userObj;
import entity.cUserObj;
import constants.chatAboutConstants;
import presenter.Fragment.FriendApplyPresenterImp;
import presenter.Fragment.FriendListPresenterImp;
import ui.UI.adapter.FriendApplyListAdapter;
import view.Fragment.FriendApplyView;
import zhh.mvpchatroom.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendApplyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendApplyFragment extends BaseFragment<FriendApplyPresenterImp, FriendApplyView> implements FriendApplyView {
    View rootView;
    private LinearLayout friendApplyLineaLayout;
    private TextView friendApplyNewFriendNum;
    private TextView friendApplySearchUser;
    private TextView friendApplyApplySend;
    private FriendApplyPresenterImp friendApplyPresenterImp;
    private Integer applyNum;// 申请记录条数
    private RecyclerView applyFriendListRecycleView;
    private LinearLayoutManager layoutManager;
    private FriendApplyListAdapter friendApplyListAdapter;
    private ArrayList<friendApplyObj> applyFriendUserList;
    private CallBackListener callBackListener;
    private Context mContext;

    public FriendApplyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FriendApplyFragment newInstance() {
        FriendApplyFragment fragment = new FriendApplyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callBackListener= (CallBackListener) getActivity();
        this.mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_friend_apply, container, false);
        }
        initView();
        this.getApplyFriendInfo(cUserObj.getInstance());
        initAdapter();
        return rootView;
    }

    public void initAdapter(){
        this.friendApplyListAdapter = new FriendApplyListAdapter(applyFriendUserList,this);
        this.applyFriendListRecycleView.setAdapter(friendApplyListAdapter);
    }


    @Override
    public void initView() {
        this.friendApplyLineaLayout = rootView.findViewById(R.id.id_friendApplyNewFriend);
        this.friendApplyNewFriendNum = rootView.findViewById(R.id.id_friendApplyNewFriendNum);
        this.friendApplyPresenterImp = new FriendApplyPresenterImp();
        friendApplyPresenterImp.attachView(this);
        this.applyNum = 0;
        this.applyFriendListRecycleView = rootView.findViewById(R.id.id_applyFriendListRecycleView);
        this.layoutManager = new LinearLayoutManager(getContext());
        this.applyFriendListRecycleView.setLayoutManager(layoutManager);
        this.applyFriendUserList = new ArrayList<>();
        this.friendApplySearchUser = rootView.findViewById(R.id.id_fiendApplySearchUser);
        this.friendApplyApplySend = rootView.findViewById(R.id.id_friendApplySendApply);
        friendApplyApplySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendApply();
            }
        });
    }

    //发送申请
    private void sendApply(){
        int mUserId = Integer.parseInt(friendApplySearchUser.getText().toString());
        userObj mUser = new userObj();
        mUser.setId(mUserId);
        userObj cUser = cUserObj.getInstance();
        friendApplyPresenterImp.addNewFriend(cUser,mUser);
    }

    //初始化主页时调用
    public void getApplyFriendInfo(userObj mUser){
        if(mUser!=null){
            friendApplyPresenterImp.getSelectUserApplyInfoList(mUser);
        }else{
            Log.e("FriendApplyFragment","初始化user数据为空");
        }
    }

    //同意好友申请
    //mUser:申请人的user对象
    public void allowApply(userObj mUser){
        userObj cUser = cUserObj.getInstance();
        if(mUser!=null){
            friendApplyPresenterImp.allowApply(cUser,mUser);
        }
    }

    //拒绝好友申请
    public void refuseApply(userObj mUser){
        userObj cUser = cUserObj.getInstance();
        if(mUser!=null){
            friendApplyPresenterImp.applyRefuse(cUser,mUser);
        }
    }

    @Override
    public FriendApplyPresenterImp createPresenter() {
        return new FriendApplyPresenterImp();
    }

    @Override
    public FriendApplyView createView() {
        return this;
    }


    @Override
    public void loadDataSuccess(BaseResponse<friendApplyObj> tData) {
        if(tData.getCode() == chatAboutConstants.friendApply.GETSELECTEDUSER_SUCCESS){
            for(int i=0;i<tData.getDataList().size();i++){
                applyNum = applyNum + 1;
                applyFriendUserList.add(tData.getDataList().get(i));
            }
            friendApplyNewFriendNum.setText(Integer.toString(applyNum));
            friendApplyListAdapter.notifyDataSetChanged();
        }
        if(tData.getCode() == chatAboutConstants.friendApply.ADDFRIEND_SUCCESS){
            for(int i=0;i<applyFriendUserList.size();i++){
                if(applyFriendUserList.get(i).getId() == tData.getData().getUserId()){
                    int a = tData.getData().getApplyStatus();
                    applyFriendUserList.get(i).setApplyStatus(a);
                }
            }
            callBackListener.updateFriendList();
            friendApplyListAdapter.notifyDataSetChanged();
        }
        if(tData.getCode() == chatAboutConstants.friendApply.DELETFRIEND_SUCCESS){
            for(int i=0;i<applyFriendUserList.size();i++){
                if(applyFriendUserList.get(i).getId() == tData.getData().getUserId()){
                    int a = tData.getData().getApplyStatus();
                    applyFriendUserList.get(i).setApplyStatus(a);
                }
            }
            friendApplyListAdapter.notifyDataSetChanged();
        }
        if(tData.getCode() == chatAboutConstants.friendApply.ADDFRIEND_FAILED){
            Log.i("FriendAplyFragment","添加好友失败");
        }
        if(tData.getCode() == chatAboutConstants.friendApply.APPLYSEND_SUCCESS){
            Log.i("FriendAplyFragment","发送好友申请成功");
            Toast.makeText(mContext,"发送成功",Toast.LENGTH_LONG).show();
            friendApplySearchUser.setText("");
        }
        if(tData.getCode() == chatAboutConstants.friendApply.APPLYSEND_EXIST){
            Toast.makeText(mContext,"当前申请已存在",Toast.LENGTH_LONG).show();
            friendApplySearchUser.setText("");
        }
    }

    public static interface CallBackListener{
        public void updateFriendList();
    }
}