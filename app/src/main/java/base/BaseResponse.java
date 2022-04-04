package base;

import java.util.ArrayList;

public class BaseResponse<T> {
   public int getCode() {
      return code;
   }

   public void setCode(int code) {
      this.code = code;
   }


   private int code;
   private T data;

   public T getData() {
      return data;
   }

   public void setData(T data) {
      this.data = data;
   }

   public ArrayList<T> getDataList() {
      return dataList;
   }

   public void setDataList(ArrayList<T> dataList) {
      this.dataList = dataList;
   }

   private ArrayList<T> dataList;
}
