package model.Fragment;
import com.google.gson.Gson;

import base.IBaseRequestCallBack;
import entity.userObj;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import service.Loader.userSettingLoader;
import service.tools.getRequestBody;

public class UserSettingModelImp implements UserSettingModel<userObj> {

   private userSettingLoader mUserSettingLoader;
   private CompositeDisposable mCompositeDisposable;
   private Gson gson;

   public UserSettingModelImp(){
      this.mUserSettingLoader = new userSettingLoader();
      this.mCompositeDisposable = new CompositeDisposable();
      this.gson = new Gson();
   }

   @Override
   public void getUserInfo(userObj cUser, final IBaseRequestCallBack iBaseRequestCallBack) {
      String userJsonObj = gson.toJson(cUser,userObj.class);
      RequestBody requestBody = new getRequestBody(userJsonObj).requestBodyBuilder();
      mCompositeDisposable.add(mUserSettingLoader.getUserInfo(requestBody).subscribe(new Consumer<userObj>() {
         @Override
         public void accept(userObj userObj) throws Exception {
            iBaseRequestCallBack.requestSuccess(userObj);
         }
      }));
   }

   @Override
   public void updateUserInfo(userObj cUser, final IBaseRequestCallBack iBaseRequestCallBack) {
      String userJsonObj = gson.toJson(cUser,userObj.class);
      RequestBody requestBody = new getRequestBody(userJsonObj).requestBodyBuilder();
      mCompositeDisposable.add(mUserSettingLoader.updateUserInfo(requestBody).subscribe(
              new Consumer<userObj>() {
                 @Override
                 public void accept(userObj userObj) throws Exception {
                    iBaseRequestCallBack.requestSuccess(userObj);
                 }
              }
      ));
   }

   @Override
   public void onUnsubscribe() {
      if(mCompositeDisposable.isDisposed()){
         mCompositeDisposable.clear();//注销subscribe
         mCompositeDisposable.dispose();
      }
   }
}
