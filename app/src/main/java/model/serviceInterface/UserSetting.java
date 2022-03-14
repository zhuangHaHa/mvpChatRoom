package model.serviceInterface;

import entity.userObj;
import entity.userTokenObj;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserSetting {

  //获取指定用户数据
  @POST("UserSetting/getUserInfo/")
  Observable<userObj> getUserInfo(@Body RequestBody requestBody);

  //更新用户数据
  @POST("UserSetting/updateUserInfo/")
  Observable<userObj> updateUserInfo(@Body RequestBody requestBody);
}
