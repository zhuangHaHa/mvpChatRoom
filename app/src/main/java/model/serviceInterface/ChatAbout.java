package model.serviceInterface;

import java.util.ArrayList;

import base.BaseResponse;
import entity.userMsgObj;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatAbout {
  //用户登录
  @POST("ChatAbout/getChatMsgData/")
  Observable<BaseResponse<userMsgObj>> getChatMsg(@Body RequestBody requestBody);
}
