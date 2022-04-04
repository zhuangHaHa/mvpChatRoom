package model.Fragment;

import base.IBaseRequestCallBack;
import entity.userObj;

interface FriendApplylModel<T> {

 /**
  *添加好友
  */
 void addNewFriend(userObj cUser,userObj mUser,IBaseRequestCallBack iBaseRequestCallBack);

 /*
 * 获取好友申请列表
 * */
 void getSelectUserApplyInfoList(userObj cUser,IBaseRequestCallBack iBaseRequestCallBack);

 /*
 * 同意好友申请
 * */
 void applyAllow(userObj cUser,userObj mUser,IBaseRequestCallBack iBaseRequestCallBack);


 /*
 * 拒绝好友申请
 * */
 void applyRefuse(userObj cUser,userObj mUser,IBaseRequestCallBack iBaseRequestCallBack);

 /*
  * 注销subscribe
  * */
 void onUnsubscribe();


}
