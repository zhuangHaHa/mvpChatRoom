package service.Loader;

import java.util.ArrayList;

import entity.userObj;
import entity.userTokenObj;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import model.serviceInterface.UserFriendAbout;
import okhttp3.RequestBody;
import service.RetrofitServiceManager;

public class userFriendLoader extends ObjectLoader{
    private UserFriendAbout userFriendAbout;
    public userFriendLoader(){
        userFriendAbout = RetrofitServiceManager.getInstance().create(UserFriendAbout.class);
    }

    /*
     * 获取用户token
     * */
    public Observable<ArrayList<userObj>> getUserFriendList(RequestBody requestBody){
        return observe(userFriendAbout.getUserFriends(requestBody))
                .map(new Function<ArrayList<userObj>, ArrayList<userObj>>() {
                    @Override
                    public ArrayList<userObj> apply(ArrayList<userObj> userObjs) throws Exception {
                        return userObjs;
                    }
                });
    }


}
