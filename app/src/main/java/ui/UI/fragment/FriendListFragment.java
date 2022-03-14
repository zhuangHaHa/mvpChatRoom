package ui.UI.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import entity.userObj;
import zhh.mvpchatroom.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendListFragment extends Fragment {
    View rootView;
    ArrayList<userObj> myFriendUserList;
    private LinearLayoutManager layoutManager;
    private RecyclerView friendListRecyclerView;

    public FriendListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FriendListBlank.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendListFragment newInstance() {
        FriendListFragment fragment = new FriendListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_friend_list_blank, container, false);
        }
        initView();
        return rootView;
    }

    private void initView(){
        myFriendUserList = new ArrayList<>();
        this.friendListRecyclerView = rootView.findViewById(R.id.friendListRecycleView);
        this.layoutManager = new LinearLayoutManager(getContext());
        this.friendListRecyclerView.setLayoutManager(layoutManager);
    }
}