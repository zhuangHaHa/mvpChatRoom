package presenter.Fragment;

import entity.userObj;

interface FriendListPresenter {

  /*
  * 获取当前账号的所有好友数据
  * */
  void getFriendList(userObj mUser);
  /*
   * 注销subscribe
   * */
  void unSubscribe();
}
