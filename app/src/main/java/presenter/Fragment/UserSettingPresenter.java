package presenter.Fragment;

import entity.userObj;

interface UserSettingPresenter {

    /*
    * 获取用户数据
    * */
    void getUserInfo(userObj mUser);

    /*
    * 更新用户数据
    * */
    void updateUserInfo(userObj mUser);

    /*
     * 注销subscribe
     * */
    void unSubscribe();
}
