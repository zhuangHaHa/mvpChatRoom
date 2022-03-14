package ui.UI.activity;

import androidx.annotation.Nullable;
import base.BaseActivity;
import entity.userObj;
import presenter.LoginPresenter;
import presenter.LoginPresenterImp;
import view.LoginView;
import zhh.mvpchatroom.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import entity.cUserObj;

public class MainActivity extends BaseActivity<LoginPresenterImp,LoginView> implements LoginView {
    private Button loginBtn;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private String username;
    private String password;
    private LoginPresenterImp mloginPresenterImp;
    private cUserObj cUser;

    private Intent toChatActivity;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁后记得解绑
        mloginPresenterImp.unSubscribe();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mloginPresenterImp = new LoginPresenterImp();
        mloginPresenterImp.attachView(this); //Activity要记得绑定view和presenter,否则会出现获取当前ActivityView为空的情况
        initView();
        loginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            username = usernameEdit.getText().toString();
            password = passwordEdit.getText().toString();
            mloginPresenterImp.login(username,password);
        }
    });
}

    @Override
    public void initView() {
        this.loginBtn = (Button)findViewById(R.id.loginBtn);
        this.usernameEdit = (EditText)findViewById(R.id.getusernameEdit);
        this.passwordEdit = (EditText)findViewById(R.id.getpasswordEdit);
        this.toChatActivity = new Intent(this,MainFaceActivity.class);
        this.cUser = cUserObj.getInstance();
    }


    @Override
    public LoginPresenterImp createPresenter() {
        return new LoginPresenterImp();
    }

    @Override
    public LoginView createView() {
        return this;
    }

    @Override
    public void loadDataSuccess(userObj tData) {
        cUser.setUsername(tData.getUsername());
        cUser.setId(tData.getId());
        cUser.setPassword(tData.getPassword());
        if(tData!=null){
            Log.i("登录成功",tData.getUsername());
            startActivity(toChatActivity);
        }
    }
}
