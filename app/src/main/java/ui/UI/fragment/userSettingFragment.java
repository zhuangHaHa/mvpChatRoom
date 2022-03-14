package ui.UI.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import base.BaseFragment;
import entity.cUserObj;
import entity.userObj;
import presenter.Fragment.UserSettingPresenterImp;
import presenter.LoginPresenterImp;
import ui.UI.activity.MainFaceActivity;
import view.Fragment.UserSettingView;
import zhh.mvpchatroom.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link userSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class userSettingFragment extends BaseFragment<UserSettingPresenterImp, UserSettingView> implements UserSettingView {

    View rootView;
    private UserSettingPresenterImp userSettingPresenterImp;
    private Button userSettingSubmitBtn;
    private EditText userNickNameEdit;

    private static final String ARG_TEXT = "param1";
    // TODO: Rename and change types of parameters
    private String mTextStr;

    public userSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment userSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static userSettingFragment newInstance() {
        userSettingFragment fragment = new userSettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_user_setting, container, false);
        }
        initView();
        getUserInfo();
        return rootView;
    }

    public void initView() {
        this.userSettingPresenterImp = new UserSettingPresenterImp();
        userSettingPresenterImp.attachView(this);
        this.userNickNameEdit = (EditText)rootView.findViewById(R.id.id_userSettingNickname);
        this.userSettingSubmitBtn = (Button)rootView.findViewById(R.id.id_userSettingSubmit);
        userSettingSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserInfo();
            }
        });
    }

    private void getUserInfo(){
        userObj mUser = cUserObj.getInstance();
        userSettingPresenterImp.getUserInfo(mUser);
    }

    private void updateUserInfo(){
        String userNickName = userNickNameEdit.getText().toString();
        userObj mUser = cUserObj.getInstance();
        mUser.setNickname(userNickName);
        userSettingPresenterImp.updateUserInfo(mUser);
    }

    @Override
    public UserSettingPresenterImp createPresenter() {
        return new UserSettingPresenterImp();
    }

    @Override
    public UserSettingView createView() {
        return this;
    }

    @Override
    public void loadDataSuccess(userObj tData) {
        if(tData.getNickname()!=userNickNameEdit.getText().toString()){
            Toast.makeText(getContext(),"更新成功",Toast.LENGTH_SHORT).show();
        }
        userNickNameEdit.setText(tData.getNickname());
    }
}