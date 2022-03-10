package base;

import android.os.Bundle;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


//抽象绑定和解绑操作
//为了兼容多个Activity模块，采用泛型
public abstract class BaseActivity<P extends BasePresenter,V extends IBaseView> extends RxAppCompatActivity {

    //P层的应用
    protected P mPresenter;

    //V层的应用
    protected V mView;
    public P getmPresenter(){
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.mPresenter!=null && this.mView!=null){
            this.mPresenter.detachView();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //绑定view
        if(mPresenter == null){
            this.mPresenter = createPresenter();
        }
        if(mView == null){
            this.mView = createView();
        }
        if (this.mPresenter!=null && this.mView!=null){
            this.mPresenter.attachView(this.mView);
        }
    }

    public abstract void initView();
    public abstract P createPresenter();
    public abstract V createView();

}
