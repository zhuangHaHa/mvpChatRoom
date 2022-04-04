package model.Fragment;

import base.IBaseRequestCallBack;
import entity.userObj;

public interface FriendListModel<T> {
   /*
    * 获取好友列表数据
    * */
   void getFriendList(userObj cUser, IBaseRequestCallBack iBaseRequestCallBack);


   /*
   * 获取groupName数据
   * */
   void getGroupName(userObj cUser,userObj fUser,IBaseRequestCallBack iBaseRequestCallBack);

   /*
    * 注销subscribe
    * */
   void onUnsubscribe();
}
