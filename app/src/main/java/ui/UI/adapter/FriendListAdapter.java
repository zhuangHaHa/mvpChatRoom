package ui.UI.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import entity.userMsgObj;
import entity.userObj;
import zhh.mvpchatroom.R;


public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

   ArrayList<userObj> friendUserList;

   public FriendListAdapter(ArrayList<userObj> dataList){
      this.friendUserList = dataList;
   }

   @NonNull
   @Override
   public FriendListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
      return new FriendListAdapter.ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull FriendListAdapter.ViewHolder holder, int position) {
      userObj friendUser = friendUserList.get(position);
      holder.friendNickName.setText(friendUser.getNickname());
   }

   @Override
   public int getItemCount() {
      return friendUserList.size();
   }

   static class ViewHolder extends RecyclerView.ViewHolder {
      TextView friendNickName;

      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         friendNickName = (TextView) itemView.findViewById(R.id.friendList_friendNickName);
      }
   }
}
