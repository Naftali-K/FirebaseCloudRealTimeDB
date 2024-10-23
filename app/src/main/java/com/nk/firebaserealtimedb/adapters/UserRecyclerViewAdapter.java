package com.nk.firebaserealtimedb.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nk.firebaserealtimedb.R;
import com.nk.firebaserealtimedb.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: naftalikomarovski
 * @Date: 2024/10/18
 */
public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserRecyclerViewHolder> {

    private static final String TAG = "Test_code";
    private List<User> usersList = new ArrayList<>();
    private UserActionsCallback callback;

    public UserRecyclerViewAdapter(UserActionsCallback callback) {
        this.callback = callback;
    }

    public UserRecyclerViewAdapter(List<User> usersList, UserActionsCallback callback) {
        this(callback);
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public UserRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_user, parent, false);
        return new UserRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerViewHolder holder, int position) {
        holder.bind(usersList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class UserRecyclerViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout container;
        private TextView userNameTv, familyNameTv, ageTv;

        public UserRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            userNameTv = itemView.findViewById(R.id.user_name_tv);
            familyNameTv = itemView.findViewById(R.id.family_name_tv);
            ageTv = itemView.findViewById(R.id.age_tv);
        }

        void bind(User user, int position) {
            if (user.getUser_name() != null) {
                userNameTv.setText(user.getUser_name());
            }

            if (user.getFamily_name() != null) {
                familyNameTv.setText(user.getFamily_name());
            }

            ageTv.setText(String.valueOf(user.getAge()));

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.openUser(user.getId());
                }
            });

            container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    callback.openMenuDialog(user.getId());
                    return false;
                }
            });
        }
    }

    public void setUsersList(List<User> usersList) {
        if (usersList == null) {
            return;
        }

        this.usersList = usersList;
        notifyDataSetChanged();
    }

    public interface UserActionsCallback {
        void openUser(String userID);
        void openMenuDialog(String userID);
    }
}
