package presenter;

public interface LoginPresenter {

    void login(String username,String password);

    void userLoginOut(String username);

    /*
    * 注销subscribe
    * */
    void unSubscribe();
}
