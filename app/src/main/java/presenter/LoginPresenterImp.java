package presenter;

import android.util.Log;

import com.google.gson.Gson;

import base.BasePresenter;
import model.LoginModelImp;
import entity.userObj;
import okhttp3.RequestBody;
import service.tools.getRequestBody;
import view.LoginView;


//持有V层的引用
//持有M层的引用
//对M层和V层进行关联
public class LoginPresenterImp extends BasePresenter<LoginView,userObj> implements LoginPresenter {
    private LoginModelImp loginModelImp;
    private userObj mUser;

    public LoginPresenterImp(){
        this.loginModelImp = new LoginModelImp();
        this.mUser = new userObj();
    }

    @Override
    public void login(String username,String password){
        //处理model的逻辑 回调给Activity
        if(getmView()!=null){
            loginModelImp.userLogin(username,password,this);
        }
    }


    @Override
    public void userLoginOut(String username) {

    }

    @Override
    public void unSubscribe() {
        loginModelImp.onUnsubscribe();
    }


}
