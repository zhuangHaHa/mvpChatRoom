package base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<P extends BasePresenter,V extends IBaseView> extends Fragment {

   //P层的应用
   protected P mPresenter;

   //V层的应用
   protected V mView;

   public P getmPresenter() {
      return mPresenter;
   }

   @Override
   public void onDestroyView() {
      super.onDestroyView();
      if (this.mPresenter != null && this.mView != null) {
         this.mPresenter.detachView();
      }
   }

   public abstract void initView();

   public abstract P createPresenter();

   public abstract V createView();

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      //绑定view
      if (mPresenter == null) {
         this.mPresenter = createPresenter();
      }
      if (mView == null) {
         this.mView = createView();
      }
      if (this.mPresenter != null && this.mView != null) {
         this.mPresenter.attachView(this.mView);
      }
   }
}
