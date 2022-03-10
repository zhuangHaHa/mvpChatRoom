package model;

import android.util.Log;

import com.google.gson.Gson;
import com.trello.rxlifecycle2.android.ActivityEvent;

import base.IBaseRequestCallBack;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import service.Loader.userLoader;
import constants.chatAboutConstants;
import entity.userObj;
import service.tools.getRequestBody;

public class LoginModelImp implements LoginModel<userObj>  {

    private userLoader mUserLoader;
    private userObj loginUser;
    private CompositeDisposable mCompositeDisposable;


    public LoginModelImp(){
        this.mUserLoader = new userLoader();
        this.loginUser = new userObj();
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void userLogin(String username, String password, final IBaseRequestCallBack<userObj> iBaseRequestCallBack) {
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        String loginUserJson = new Gson().toJson(loginUser);
        RequestBody requestBody = new getRequestBody(loginUserJson).requestBodyBuilder();
        mCompositeDisposable.add(mUserLoader.userLogin(requestBody)
                .subscribe(new Consumer<userObj>() {
                    @Override
                    public void accept(userObj userObj) throws Exception {
                        iBaseRequestCallBack.requestSuccess(userObj);
                    }
                })
        );

    }

    @Override
    public void userLoginOut(String username) {

    }

    @Override
    public void onUnsubscribe() {
        if(mCompositeDisposable.isDisposed()){
            mCompositeDisposable.clear();//注销subscribe
            mCompositeDisposable.dispose();
        }
    }
}
