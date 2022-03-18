package presenter.Fragment;

import base.BasePresenter;
import entity.userObj;
import model.Fragment.FriendListModelImp;
import view.Fragment.FriendListView;
import view.Fragment.UserSettingView;

public class FriendListPresenterImp extends BasePresenter<FriendListView, userObj> implements FriendListPresenter {
   private FriendListModelImp friendListModelImp;
   public FriendListPresenterImp(){
      this.friendListModelImp = new FriendListModelImp();
   }

   @Override
   public void getFriendList(userObj mUser) {
      if(mUser!=null && getmView()!=null){
         friendListModelImp.getFriendList(mUser,this);
      }
   }

   @Override
   public void unSubscribe() {
      friendListModelImp.onUnsubscribe();
   }
}
