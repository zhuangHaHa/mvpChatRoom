package ui.UI.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import entity.userMsgObj;
import service.Loader.userLoader;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import entity.userMsgObj;
import entity.userObj;
import zhh.mvpchatroom.R;
import entity.cUserObj;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    ArrayList<userMsgObj> chatModelList;
    userLoader mUserLoader;
    private cUserObj cUser;

    public ChatAdapter(ArrayList<userMsgObj> dataList){
        this.chatModelList = dataList;
        this.cUser = cUserObj.getInstance();
    }


    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        userMsgObj userMsgObj = chatModelList.get(position);
        if(cUser.getId()!=userMsgObj.getId()){
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.leftNameTextView.setVisibility(View.VISIBLE);
            holder.leftContentTextView.setVisibility(View.VISIBLE);
            holder.leftNameTextView.setText(userMsgObj.getNickname());
            holder.leftContentTextView.setText(userMsgObj.getMsg());
        }else {
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightNameTextView.setVisibility(View.VISIBLE);
            holder.rightContentTextView.setVisibility(View.VISIBLE);
            holder.rightNameTextView.setText(userMsgObj.getNickname());
            holder.rightContentTextView.setText(userMsgObj.getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return chatModelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView leftNameTextView;
        TextView leftContentTextView;
        LinearLayout leftLayout;

        TextView rightNameTextView;
        TextView rightContentTextView;
        LinearLayout rightLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftNameTextView = (TextView) itemView.findViewById(R.id.left_name);
            leftContentTextView = (TextView) itemView.findViewById(R.id.left_content);
            leftLayout = (LinearLayout) itemView.findViewById(R.id.leftChatFace);

            rightNameTextView = (TextView) itemView.findViewById(R.id.right_name);
            rightContentTextView = (TextView) itemView.findViewById(R.id.right_content);
            rightLayout = (LinearLayout) itemView.findViewById(R.id.rightChatFace);
        }
    }

}
