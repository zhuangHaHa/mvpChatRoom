package model.serviceInterface;

import entity.userObj;
import entity.userTokenObj;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAbout {

    //用户登录
    @POST("UserAbout/userlogin/")
    Observable<userObj> userLogin(@Body RequestBody requestBody);
    //获取登录对象的token
    @POST("UserAbout/getToken/")
    Observable<userTokenObj> getToken(@Body RequestBody requestBody);
    //登出 未实现
    @POST("UserAbout/loginOut/")
    Observable<userObj> userLoginOut(@Body RequestBody requestBody);
    //指定查询
    @POST("UserAbout/getUserInfo")
    Observable<userObj> getUserInfo(@Body RequestBody requestBody);
}
