package service.Loader;
import entity.userObj;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import model.serviceInterface.UserAbout;
import model.serviceInterface.UserSetting;
import okhttp3.RequestBody;
import service.RetrofitServiceManager;

public class userSettingLoader extends ObjectLoader {
   private UserSetting mUserSetting;
   public userSettingLoader(){
      mUserSetting = RetrofitServiceManager.getInstance().create(UserSetting.class);
   }

   /*
   * 获取用户数据，用于setting界面的显示
   * */
   public Observable<userObj> getUserInfo(RequestBody requestBody){
      return observe(mUserSetting.getUserInfo(requestBody))
              .map(new Function<userObj, userObj>() {
                 @Override
                 public userObj apply(userObj userObj) throws Exception {
                    return userObj;
                 }
              });
   }

   /*
   * 更新用户数据，用于
   * */
    public Observable<userObj> updateUserInfo(RequestBody requestBody){
        return observe(mUserSetting.updateUserInfo(requestBody)
        .map(new Function<userObj, userObj>() {
            @Override
            public userObj apply(userObj userObj) throws Exception {
                return userObj;
            }
        }));
    }
}
