package presenter.Fragment;

import base.BasePresenter;
import model.Fragment.UserSettingModelImp;
import view.Fragment.UserSettingView;
import entity.userObj;

public class UserSettingPresenterImp extends BasePresenter<UserSettingView,userObj> implements UserSettingPresenter{
    private UserSettingModelImp userSettingModelImp;

    public UserSettingPresenterImp(){
        this.userSettingModelImp = new UserSettingModelImp();
    }
    @Override
    public void getUserInfo(userObj mUser) {
        if(mUser!=null && getmView()!=null){
            userSettingModelImp.getUserInfo(mUser,this);
        }
    }

    @Override
    public void updateUserInfo(userObj mUser) {
        if(mUser!=null && getmView()!=null){
            userSettingModelImp.updateUserInfo(mUser,this);
        }
    }

    @Override
    public void unSubscribe() {
        userSettingModelImp.onUnsubscribe();
    }
}
