package model.serviceInterface;

import java.util.ArrayList;
import java.util.HashMap;

import base.BaseResponse;
import entity.userFriendObj;
import entity.friendApplyObj;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserFriendAbout {

   //用户登录获取用户的好友列表
   @POST("userFriendList/getUserFriends/")
   Observable<ArrayList<userFriendObj>> getUserFriends(@Body RequestBody requestBody);

   //获取指定好友和用户的groupname
   @POST("userFriendList/getUserFriendsGroupName/")
   Observable<userFriendObj> getUserFriendsGroupName(@Body HashMap requestBody);

   //获取指定用户的好友列表
   @POST("friendApply/getSelectUserApplyInfo/")
   Observable<BaseResponse<friendApplyObj>> getSelectUserFriendApply(@Body RequestBody requestBody);

   //同意好友申请
   @POST("friendApply/allowApply/")
   Observable<BaseResponse<friendApplyObj>> allowApply(@Body HashMap requestBody);

   //拒绝好友申请
   @POST("friendApply/allowRefuse/")
   Observable<BaseResponse<friendApplyObj>> allowRefuse(@Body HashMap requestBody);

   //发送申请
   @POST("friendApply/addFriend/")
   Observable<BaseResponse<friendApplyObj>> addFriend(@Body HashMap requestBody);
}

