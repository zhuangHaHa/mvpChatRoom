package model.serviceInterface;

import java.util.ArrayList;

import entity.userObj;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserFriendAbout {

   //用户登录
   @POST("userFriendList/getUserFriends/")
   Observable<ArrayList<userObj>> getUserFriends(@Body RequestBody requestBody);
}

