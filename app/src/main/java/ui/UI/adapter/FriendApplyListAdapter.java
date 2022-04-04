package ui.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import entity.friendApplyObj;
import entity.userObj;
import ui.UI.fragment.FriendApplyFragment;
import ui.UI.fragment.FriendListFragment;
import zhh.mvpchatroom.R;

public class FriendApplyListAdapter extends RecyclerView.Adapter<FriendApplyListAdapter.ViewHolder> {
   private ArrayList<friendApplyObj> friendApplyList;
   private FriendApplyFragment context;

   public FriendApplyListAdapter(ArrayList<friendApplyObj> dataList, FriendApplyFragment context){
      this.friendApplyList = dataList;
      this.context = context;
   }
   @NonNull
   @Override
   public FriendApplyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendapply_list_item, parent, false);
      return new FriendApplyListAdapter.ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull FriendApplyListAdapter.ViewHolder holder, int position) {
      final friendApplyObj mUser = friendApplyList.get(position);
      if(mUser!=null){
         holder.applyUserNickname.setText(mUser.getNickname());
      }
      //0表示该申请未进行处理
      if(mUser.getApplyStatus() == 0){
         holder.allowApply.setVisibility(View.VISIBLE);
         holder.refuseApply.setVisibility(View.VISIBLE);
         holder.applyRefuseTextShow.setVisibility(View.GONE);
         holder.applyAllowTextShow.setVisibility(View.GONE);
      }
      //1表示该条申请已经通过
      if(mUser.getApplyStatus() == 1){
         holder.allowApply.setVisibility(View.GONE);
         holder.refuseApply.setVisibility(View.GONE);
         holder.applyRefuseTextShow.setVisibility(View.GONE);
         holder.applyAllowTextShow.setVisibility(View.VISIBLE);
      }
      //2表示该条申请已被拒绝
      if(mUser.getApplyStatus() == 2){
         holder.allowApply.setVisibility(View.GONE);
         holder.refuseApply.setVisibility(View.GONE);
         holder.applyRefuseTextShow.setVisibility(View.VISIBLE);
         holder.applyAllowTextShow.setVisibility(View.GONE);
      }
      //同意申请监听
      holder.allowApply.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            context.allowApply(mUser);
         }
      });
      //拒绝申请监听
      holder.refuseApply.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            context.refuseApply(mUser);
         }
      });
   }

   @Override
   public int getItemCount() {
      return friendApplyList.size();
   }

   static class ViewHolder extends RecyclerView.ViewHolder {
      TextView applyUserNickname;
      TextView applyFriendReason;
      Button allowApply;
      Button refuseApply;
      TextView applyAllowTextShow;
      TextView applyRefuseTextShow;

      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         applyUserNickname = (TextView)itemView.findViewById(R.id.id_applyFriendNickName);;
         applyFriendReason = (TextView)itemView.findViewById(R.id.id_applyFriendReason);;
         allowApply = (Button) itemView.findViewById(R.id.id_applyFriendAllow);
         refuseApply = (Button) itemView.findViewById(R.id.id_applyFriendRefuse);
         applyAllowTextShow = (TextView)itemView.findViewById(R.id.id_applyAllowTextShow);
         applyRefuseTextShow = (TextView)itemView.findViewById(R.id.id_applyRefuseTextShow);
       }
   }
}
