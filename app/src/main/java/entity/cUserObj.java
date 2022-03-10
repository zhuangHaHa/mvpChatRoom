package entity;

public class cUserObj extends userObj {
   private cUserObj(){}

   private static class cUserObjInstance{
      private static final cUserObj Instance = new cUserObj();
   }

   public static cUserObj getInstance(){
      return cUserObjInstance.Instance;
   }

}
