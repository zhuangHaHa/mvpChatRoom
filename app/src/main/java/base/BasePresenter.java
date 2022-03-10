package base;

import android.util.Log;

public class BasePresenter<V extends IBaseView,T> implements IBaseRequestCallBack<T>{

    //数据管理层
//    protected DataManager mDataManager;
    //V层引用
    protected V mView;


    public V getmView(){
        return mView;
    }

    /*
    * 解绑view
    * */
    public void detachView(){
        this.mView = null;
    }

    /*
    *绑定view
    * @param view
    * */
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void requestError(Throwable throwable) {

    }

    @Override
    public void requestComplete() {

    }

   /*
   * 请求成功后的回调数据
   * */
    @Override
    public void requestSuccess(T callBack) {
        mView.loadDataSuccess(callBack);
    }
}
