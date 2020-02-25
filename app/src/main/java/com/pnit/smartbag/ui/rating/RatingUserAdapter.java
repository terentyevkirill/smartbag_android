package com.pnit.smartbag.ui.rating;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pnit.smartbag.R;
import com.pnit.smartbag.data.user.model.User;

import java.util.ArrayList;

public class RatingUserAdapter extends RecyclerView.Adapter<RatingUserAdapter.UserViewHolder> {
    private ArrayList<User> ratingList;
    public RatingUserAdapter(ArrayList<User> ratingList, Context context){
        this.ratingList = ratingList;
    }
    @Override
    public int getItemCount(){
        return ratingList.size();
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rating_card, viewGroup, false);
        v.getBackground().setAlpha(0);
        UserViewHolder holder = new UserViewHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        userViewHolder.position.setText("1");
        userViewHolder.userName.setText(ratingList.get(i).getUsername());
        userViewHolder.steps.setText("20000");
    }
    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView position;
        TextView userName;
        TextView steps;
        public UserViewHolder(@NonNull View itemView){
            super(itemView);
            position = itemView.findViewById(R.id.position_tv);
            userName = itemView.findViewById(R.id.username_tv);
            steps = itemView.findViewById(R.id.steps_tv);
        }
    }
}