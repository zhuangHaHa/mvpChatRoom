package model.Fragment;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import base.IBaseRequestCallBack;
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
              mUserFriendLoader.getUserFriendList(requestBody).subscribe(new Consumer<ArrayList<userObj>>() {
                 @Override
                 public void accept(ArrayList<userObj> userObjs) throws Exception {
                    if(userObjs!=null){
                       iBaseRequestCallBack.requestSuccess(userObjs);
                    }
                    else {
                       Log.i("FriendListModelImp","当前没好友，防空报错");
                    }
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
