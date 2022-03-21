package entity;

import java.util.ArrayList;

public class userMsgObj extends userObj{
   public userMsgObj(){}

   public String getMsg() {
      return msg;
   }
   public void setMsg(String msg) {
      this.msg = msg;
   }

   private String msg;

   public ArrayList<Integer> getUserList() {
      return userList;
   }

   public void setUserList(ArrayList<Integer> userList) {
      this.userList = userList;
   }

   private ArrayList<Integer> userList;

   public String getGroupName() {
      return groupName;
   }

   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   private String groupName;

   public Integer getFriendId() {
      return friendId;
   }

   public void setFriendId(Integer friendId) {
      this.friendId = friendId;
   }

   private Integer friendId;
}
