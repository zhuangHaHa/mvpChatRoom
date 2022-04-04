package model.Fragment;

import com.google.gson.Gson;
import base.BaseResponse;

import java.util.HashMap;

import base.IBaseRequestCallBack;
import entity.userFriendObj;
import entity.userObj;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import service.Loader.userFriendLoader;
import service.tools.getRequestBody;
import entity.friendApplyObj;

public class FriendApplyModelImp implements FriendApplylModel<BaseResponse<friendApplyObj>> {
   private userFriendLoader mUserFriendLoader;
   private CompositeDisposable mCompositeDisposable;
   private Gson gson;

   public FriendApplyModelImp(){
      this.mUserFriendLoader = new userFriendLoader();
      this.mCompositeDisposable = new CompositeDisposable();
      this.gson = new Gson();
   }

   @Override
   public void addNewFriend(userObj cUser, userObj mUser,final IBaseRequestCallBack iBaseRequestCallBack) {
      String myJsonObj = gson.toJson(cUser,userObj.class);
      String friendJsonObj = gson.toJson(mUser,userObj.class);
      HashMap<String,String> map = new HashMap<>();
      map.put("applyUser",friendJsonObj);
      map.put("applyedUser",myJsonObj);
      mCompositeDisposable.add(mUserFriendLoader.addFriend(map).subscribe(new Consumer<BaseResponse<friendApplyObj>>() {
         @Override
         public void accept(BaseResponse<friendApplyObj> friendApplyObjBaseResponse) throws Exception {
            iBaseRequestCallBack.requestSuccess(friendApplyObjBaseResponse);
         }
      }));
   }

   @Override
   public void getSelectUserApplyInfoList(userObj cUser, final IBaseRequestCallBack iBaseRequestCallBack) {
      userFriendObj selectUser = new userFriendObj();
      selectUser.setId(cUser.getId());
      String selectUserJson = gson.toJson(selectUser,userFriendObj.class);
      RequestBody requestBody = new getRequestBody(selectUserJson).requestBodyBuilder();
      mCompositeDisposable.add(
              mUserFriendLoader.getSelectUserApplyInfoList(requestBody).subscribe(new Consumer<BaseResponse<friendApplyObj>>() {
                 @Override
                 public void accept(BaseResponse<friendApplyObj> friendApplyObjBaseResponse) throws Exception {
                    iBaseRequestCallBack.requestSuccess(friendApplyObjBaseResponse);
                 }
              })
      );

   }

   @Override
   public void applyAllow(userObj cUser, userObj mUser, final IBaseRequestCallBack iBaseRequestCallBack) {
      String myJsonObj = gson.toJson(cUser,userObj.class);
      String friendJsonObj = gson.toJson(mUser,userObj.class);
      HashMap<String,String> map = new HashMap<>();
      map.put("applyUser",friendJsonObj);
      map.put("applyedUser",myJsonObj);
      mCompositeDisposable.add(mUserFriendLoader.allowApply(map).subscribe(new Consumer<BaseResponse<friendApplyObj>>() {
         @Override
         public void accept(BaseResponse<friendApplyObj> friendApplyObjBaseResponse) throws Exception {
            iBaseRequestCallBack.requestSuccess(friendApplyObjBaseResponse);
         }
      }));
   }

   @Override
   public void applyRefuse(userObj cUser,userObj mUser, final IBaseRequestCallBack iBaseRequestCallBack) {
      String myJsonObj = gson.toJson(cUser,userObj.class);
      String friendJsonObj = gson.toJson(mUser,userObj.class);
      HashMap<String,String> map = new HashMap<>();
      map.put("applyUser",friendJsonObj);
      map.put("applyedUser",myJsonObj);
      mCompositeDisposable.add(
              mUserFriendLoader.applyRefuse(map).subscribe(new Consumer<BaseResponse<friendApplyObj>>() {
                 @Override
                 public void accept(BaseResponse<friendApplyObj> friendApplyObjBaseResponse) throws Exception {
                    iBaseRequestCallBack.requestSuccess(friendApplyObjBaseResponse);
                 }
              })
      );
   }

   @Override
   public void onUnsubscribe() {
      if(mCompositeDisposable.isDisposed()){
         mCompositeDisposable.clear();//注销subscribe
         mCompositeDisposable.dispose();
      }
   }
}
