package service.Loader;

import android.util.Log;

import java.util.ArrayList;


import base.BaseResponse;
import entity.userObj;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import model.serviceInterface.ChatAbout;
import entity.userMsgObj;
import okhttp3.RequestBody;
import service.RetrofitServiceManager;

public class chatLoader extends ObjectLoader{
   private ChatAbout chatAbout;
   public chatLoader(){
      chatAbout = RetrofitServiceManager.getInstance().create(ChatAbout.class);
   }

   /*
    * 获取当前页数的聊天数据
    * */
   public Observable<BaseResponse<userMsgObj>> getChatMsg(RequestBody requestBody){
      return observe(chatAbout.getChatMsg(requestBody))
              .map(new Function<BaseResponse<userMsgObj>, BaseResponse<userMsgObj>>() {
                  @Override
                  public BaseResponse<userMsgObj> apply(BaseResponse<userMsgObj> userMsgObjBaseResponse) throws Exception {
                      return userMsgObjBaseResponse;
                  }
              });
   }

}
