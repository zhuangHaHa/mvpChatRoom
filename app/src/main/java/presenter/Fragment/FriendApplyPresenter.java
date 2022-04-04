package presenter.Fragment;


import base.IBaseRequestCallBack;
import entity.userObj;

public interface FriendApplyPresenter {

    /**
     *添加好友
     */
    void addNewFriend(userObj cUser, userObj mUser);

    /*
    * 同意好友申请
    * */
    void allowApply(userObj cUser,userObj mUser);

    /*
     * 拒绝好友申请
     * */
    void applyRefuse(userObj cUser,userObj mUser);

    /*
     * 获取好友申请列表
     * */
    void getSelectUserApplyInfoList(userObj cUser);
    /*
     * 注销subscribe
     * */
    void onUnsubscribe();
}
