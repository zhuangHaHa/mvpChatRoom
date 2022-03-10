package base;

public interface  IBaseRequestCallBack<T> {
    /**
     * @descriptoin 请求之前的操作
     * @date 2017/2/16 11:34
     */
    void beforeRequest();

    /**
     * @descriptoin 请求异常
     * @param throwable 异常类型
     * @date 2017/2/16 11:34
     */
    void requestError(Throwable throwable);

    /**
     * @descriptoin 请求完成
     * @date 2017/2/16 11:35
     */
    void requestComplete();

    /**
     * @descriptoin 请求成功
     * @param callBack 根据业务返回相应的数据
     * @date 2017/2/16 11:35
     */
    void requestSuccess(T callBack);

}
