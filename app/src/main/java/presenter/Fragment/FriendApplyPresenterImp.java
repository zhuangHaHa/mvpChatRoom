package presenter.Fragment;

import base.BasePresenter;
import base.IBaseRequestCallBack;
import model.Fragment.FriendApplyModelImp;
import view.Fragment.FriendApplyView;
import entity.userObj;
import base.BaseResponse;
import entity.friendApplyObj;

public class FriendApplyPresenterImp extends BasePresenter<FriendApplyView,BaseResponse<friendApplyObj>> implements FriendApplyPresenter{
    private FriendApplyModelImp friendApplyModelImp;
    private userObj mUser;

    public FriendApplyPresenterImp(){
        this.friendApplyModelImp = new FriendApplyModelImp();
        this.mUser = new userObj();
    }

    @Override
    public void addNewFriend(userObj cUser, userObj mUser) {
        if(mView!=null && cUser!=null && mUser!=null){
            friendApplyModelImp.addNewFriend(cUser,mUser,this);
        }
    }

    @Override
    public void allowApply(userObj cUser,userObj mUser) {
        if(mView!=null && cUser!=null && mUser!=null){
            friendApplyModelImp.applyAllow(cUser,mUser,this);
        }
    }

    @Override
    public void applyRefuse(userObj cUser,userObj mUser) {
        if(mView!=null&&mUser!=null&& cUser!=null ){
            friendApplyModelImp.applyRefuse(cUser,mUser,this);
        }
    }


    @Override
    public void getSelectUserApplyInfoList(userObj cUser) {
        if(mView!=null && cUser!=null){
            mUser.setId(cUser.getId());
            friendApplyModelImp.getSelectUserApplyInfoList(mUser,this);
        }

    }

    @Override
    public void onUnsubscribe() {
        friendApplyModelImp.onUnsubscribe();
    }
}
