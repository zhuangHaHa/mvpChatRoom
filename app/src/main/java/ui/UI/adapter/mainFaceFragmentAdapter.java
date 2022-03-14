package ui.UI.adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class mainFaceFragmentAdapter extends FragmentStateAdapter {
   List<Fragment> fragmentList = new ArrayList<>();

   public mainFaceFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,List<Fragment> fragments) {
      super(fragmentManager, lifecycle);
      fragmentList = fragments;
   }

   @NonNull
   @Override
   public Fragment createFragment(int position) {
      return fragmentList.get(position);
   }

   @Override
   public int getItemCount() {
      return fragmentList.size();
   }
}
