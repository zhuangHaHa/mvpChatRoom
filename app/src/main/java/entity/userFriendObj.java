package entity;

public class userFriendObj extends userObj {
   private String groupName;

   public String getGroupName() {
      return groupName;
   }

   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   public Integer getFriendsId() {
      return friendsId;
   }

   public void setFriendsId(Integer friendsId) {
      this.friendsId = friendsId;
   }

   private Integer friendsId;

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   private Integer userId;
}
