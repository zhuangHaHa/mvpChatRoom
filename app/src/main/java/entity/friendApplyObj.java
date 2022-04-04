package entity;

public class friendApplyObj extends userFriendObj {

   public Integer getApplyStatus() {
      return applyStatus;
   }

   public void setApplyStatus(Integer applyStatus) {
      this.applyStatus = applyStatus;
   }

   private Integer applyStatus;//0 表示申请未处理，1表示申请已同意，2表示申请拒绝
}
