package service.Loader;

import java.util.ArrayList;
import java.util.HashMap;

import entity.commonResponse;
import entity.friendApplyObj;
import entity.userObj;
import entity.userFriendObj;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import model.serviceInterface.UserFriendAbout;
import okhttp3.RequestBody;
import service.RetrofitServiceManager;
import base.BaseResponse;

public class userFriendLoader extends ObjectLoader{
    private UserFriendAbout userFriendAbout;
    public userFriendLoader(){
        userFriendAbout = RetrofitServiceManager.getInstance().create(UserFriendAbout.class);
    }

    /*
     * 获取用户好友列表
     * */
    public Observable<ArrayList<userFriendObj>> getUserFriendList(RequestBody requestBody){
        return observe(userFriendAbout.getUserFriends(requestBody))
                .map(new Function<ArrayList<userFriendObj>, ArrayList<userFriendObj>>() {
                    @Override
                    public ArrayList<userFriendObj> apply(ArrayList<userFriendObj> userFriendObjs) throws Exception {
                        return userFriendObjs;
                    }
                });
    }

    /*
    * 获取用户指定用户的groupname
    * */
    public Observable<userFriendObj> getUserFriendsGroupName(HashMap mapRequest){
        return observe(userFriendAbout.getUserFriendsGroupName(mapRequest))
                .map(new Function<userFriendObj, userFriendObj>() {
                    @Override
                    public userFriendObj apply(userFriendObj userFriendObj) throws Exception {
                        return userFriendObj;
                    }
                });
    }

    /*
    * 获取指定用户的申请列表
    * @param 当前登录的User
    * */
    public Observable<BaseResponse<friendApplyObj>> getSelectUserApplyInfoList(RequestBody requestBody){
        return observe(userFriendAbout.getSelectUserFriendApply(requestBody)).map(new Function<BaseResponse<friendApplyObj>, BaseResponse<friendApplyObj>>() {
            @Override
            public BaseResponse<friendApplyObj> apply(BaseResponse<friendApplyObj> friendApplyObjBaseResponse) throws Exception {
                return friendApplyObjBaseResponse;
            }
        });
    }

    /*
    * 同意当前Id对应的好友请求
    * */
    public Observable<BaseResponse<friendApplyObj>> allowApply(HashMap hashMap){
        return observe(userFriendAbout.allowApply(hashMap).map(new Function<BaseResponse<friendApplyObj>, BaseResponse<friendApplyObj>>() {
            @Override
            public BaseResponse<friendApplyObj> apply(BaseResponse<friendApplyObj> friendApplyObjBaseResponse) throws Exception {
                return friendApplyObjBaseResponse;
            }
        }));
    }

    /*
    * 拒绝当前Id对应的好友请求
    * */
    public Observable<BaseResponse<friendApplyObj>> applyRefuse(HashMap hashMap){
        return observe(userFriendAbout.allowRefuse(hashMap).map(new Function<BaseResponse<friendApplyObj>, BaseResponse<friendApplyObj>>() {
            @Override
            public BaseResponse<friendApplyObj> apply(BaseResponse<friendApplyObj> friendApplyObjBaseResponse) throws Exception {
                return friendApplyObjBaseResponse;
            }
        }));
    }

    /*
    申请添加当前Id的好友
    * */
    public Observable<BaseResponse<friendApplyObj>> addFriend(HashMap hashMap){
        return observe(userFriendAbout.addFriend(hashMap).map(new Function<BaseResponse<friendApplyObj>, BaseResponse<friendApplyObj>>() {
            @Override
            public BaseResponse<friendApplyObj> apply(BaseResponse<friendApplyObj> friendApplyObjBaseResponse) throws Exception {
                return friendApplyObjBaseResponse;
            }
        }));
    }

}
