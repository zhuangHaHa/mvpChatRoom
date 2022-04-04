package model.Fragment;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.IBaseRequestCallBack;
import entity.userFriendObj;
import entity.userObj;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import service.Loader.userFriendLoader;
import service.tools.getRequestBody;

public class FriendListModelImp implements FriendListModel<userObj> {
   private userFriendLoader mUserFriendLoader;
   private CompositeDisposable mCompositeDisposable;
   private Gson gson;

   public FriendListModelImp(){
      this.mUserFriendLoader = new userFriendLoader();
      this.mCompositeDisposable = new CompositeDisposable();
      this.gson = new Gson();
   }

   @Override
   public void getFriendList(userObj cUser, final IBaseRequestCallBack iBaseRequestCallBack) {
      String userJsonObj = gson.toJson(cUser,userObj.class);
      RequestBody requestBody = new getRequestBody(userJsonObj).requestBodyBuilder();
      mCompositeDisposable.add(
              mUserFriendLoader.getUserFriendList(requestBody).subscribe(new Consumer<ArrayList<userFriendObj>>() {
                 @Override
                 public void accept(ArrayList<userFriendObj> userFriendObjs) throws Exception {
                    if(userFriendObjs!=null){
                       for(int i=0;i<userFriendObjs.size();i++){
                          iBaseRequestCallBack.requestSuccess(userFriendObjs.get(i));
                       }
                    }
                 }
              })
      );
   }

   @Override
   public void getGroupName(userObj cUser, userObj fUser, final IBaseRequestCallBack iBaseRequestCallBack) {
      String myJsonObj = gson.toJson(cUser,userObj.class);
      String friendJsonObj = gson.toJson(fUser,userObj.class);
      HashMap<String,String> map = new HashMap<>();
      map.put("user",myJsonObj);
      map.put("friend",friendJsonObj);
      mCompositeDisposable.add(
              mUserFriendLoader.getUserFriendsGroupName(map).subscribe(new Consumer<userFriendObj>() {
                 @Override
                 public void accept(userFriendObj userFriendObj) throws Exception {
                    iBaseRequestCallBack.requestSuccess(userFriendObj);
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
