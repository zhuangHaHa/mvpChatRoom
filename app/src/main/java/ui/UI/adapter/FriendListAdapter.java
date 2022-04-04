package ui.UI.adapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import entity.userMsgObj;
import entity.userObj;
import ui.UI.activity.ChatActivity;
import ui.UI.activity.MainFaceActivity;
import ui.UI.fragment.FriendListFragment;
import zhh.mvpchatroom.R;


public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder>  {

   ArrayList<userObj> friendUserList;
   FriendListFragment context;


   public FriendListAdapter(ArrayList<userObj> dataList,FriendListFragment context){
      this.friendUserList = dataList;
      this.context = context;
   }

   @NonNull
   @Override
   public FriendListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
      return new FriendListAdapter.ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull FriendListAdapter.ViewHolder holder, int position) {
      final userObj friendUser = friendUserList.get(position);
      holder.friendNickName.setText(friendUser.getNickname());
      if(friendUser!=null){
         if(friendUser.getStatus() == 0){
            holder.friendStatus.setText("离线");
         }
         if(friendUser.getStatus() == 1){
            holder.friendStatus.setText("在线");
         }
      }
      holder.friendListItemLayout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            context.getChatFriendUser(friendUser);
         }
      });
   }

   @Override
   public int getItemCount() {
      return friendUserList.size();
   }


   static class ViewHolder extends RecyclerView.ViewHolder {
      TextView friendNickName;
      LinearLayout friendListItemLayout;
      TextView friendStatus;

      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         friendNickName = (TextView) itemView.findViewById(R.id.friendList_friendNickName);
         friendListItemLayout = (LinearLayout) itemView.findViewById(R.id.friendListItemLayout);
         friendStatus = (TextView) itemView.findViewById(R.id.friendList_friendStatus);
      }
   }
}
