package com.example.codehack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.ContestViewHolder> {
    private Contest contest;
    public ContestAdapter(Contest contest){
        this.contest=contest;
    }
    @NonNull
    @Override
    public ContestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.contest_item_layout,parent,false);
        return new ContestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContestViewHolder holder, int position) {
        Object contestObject=contest.getObjects().get(position);
        holder.textView.setText(contestObject.getEvent());
        holder.about.setText(contestObject.getResource().getName());
        String contestName=contestObject.getResource().getName();
        String link="";
        String randomColors[]={"0D8ABC","D35400","F1C40F","17A589","7D3C98","E74C3C","145A32","7F8C8D","2C3E50","922B21"};
        Random random=new Random();
        if(contestName.contains("hackerearth"))
            link="https://static-fastly.hackerearth.com/static/hackerearth/images/logo/HE_identity.png";
        else if(contestName.contains("hackerrank"))
            link="https://hrcdn.net/fcore/assets/brand/h_mark_sm-966d2b45e3.svg";
        else if(contestName.contains("codeforce"))
            link="https://sta.codeforces.com/s/80648/images/codeforces-logo-with-telegram.png";
        else if(contestName.contains("codechef"))
            link="https://s3.amazonaws.com/codechef_shared/misc/favicon.ico";
        else if(contestName.contains("topcoder"))
            link="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJcAAAAiCAMAAABlRruNAAABAlBMVEX///+MxUP/8gAoqeD2kyIHc7rJycrU1NTX19fDw8T4+Pj/9QD8/Pzv7++Pj5D19fXk5OWxsbJXV1mqqqp3d3ibm5xGRkkAoN31iwD2jiMAa7cCb7i9vb63t7dSUlShoaI+PkDp897D36Sm0XSVyVSgzmm725je7c7U6L+CwSqIwzmYy17S6Lev1orP6PVxwOhGseOLtcVnqWOCvk2QyTCAwUaozynZ6GD7toH3oEr3plf70K2i0+4LiMoAbMFIkZhrqXbR4Cnx7RX95QA0hadipHz83BD86tx8ptnL4Mb59pP5vRr//eOevNv++73/8zf6x53/+8r6yBf73MT/+nlnZ2knpLAUAAACrUlEQVRYhe2WfVfTMBTGU2FJX9K0a7Iyur4MRGVTARUQEaeIL4gKvu37fxXvTbqBg3OUw9p6jj5/LOlNlvvrc9O0hNxcK6u31yzrzt17c1hrflpfGwwt1HAwXG0aZqqV+wPrXENrvWkgrn8flF5NNXhoRnkTTBubC6itzUePn2wPL5Dt7Fi7T/eeLaL2n9eNdbC0YPQiGY1aL19Z20MDtXv42vO8o2XNtdjdr5lrgrXUa7VavSQZvXkLe3730PNuobx3E7D3zXAhFqo36h1/ODm7NZH3cbkRLrO7FkqqpHe8ocOfTqZgxq/FerHIxtK0iq2k9dk9Hzk9u1DJ7peauYxhJdXM0Nezc8PqxiJkS9uV9GapUEjmfVuufXehAjwjkquoUFDNo273e61EpYKD44v7alanP/YacOu//iWx8FKIMzvw55yGXnfBDr0cs+30Mu3NJNg1/3AVF+dz/jDjvnBCXNJ2bLx2fRI6JSlnjjEBImVWGyKGq5zFfeLbcyUyCsRYCZeEeScTEVSUCSEyESMPzYtMKE64UlmRI4wfwZhALncs0iKHWaGSKquAC+oIt+tqEDsGrtwve2GOHtGAqDa0HGdEaFGAiGPsuTHcUP+62+CPuWBhWZiuA37pnqJElQnDSDdUEaZ0r6CEmlmOBL8qwtJcymwRpzPhaksSBWY87egmiDGIgjoWwkFJVTtXMcvl9n/holp2xVyFySjSCRdUtGQl4Vg3UEQ6W0ccrY4L8vsxmuP3OWERn/Q0j+2SsT4XIpimnw59nWvosEouqeAkYnGbSUwL54Rk7T6mzaKUFpFLgqhD0wgfyjCGMSWAy88LRoWaulmF0jaY5abSwQuoI5Op+QLzM2mOdiYz87LiKURC3aeyje65VRyqV4iJ389pQn8rV5g2TdCQfgLkoTfu+oZw9wAAAABJRU5ErkJggg==";
        else if(contestName.contains("kaggle"))
            link="https://www.kaggle.com/static/images/site-logo.png";
        else if(contestName.contains("leetcode"))
            link="https://assets.leetcode.com/static_assets/public/webpack_bundles/images/LeetCode_nav.4d940ca72.png";
        else
            link="https://ui-avatars.com/api/?background="+ randomColors[random.nextInt(10)]+"&color=fff&name="+contestName;
        Glide.with(holder.returnHolderView()).load(link).into(holder.imgIcon);
    }

    @Override
    public int getItemCount() {
        return contest.getObjects().size();
    }

    public class ContestViewHolder extends RecyclerView.ViewHolder{
        ImageView imgIcon;
        TextView textView,about;
        View view;
        public ContestViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            imgIcon=itemView.findViewById(R.id.contestListImage);
            textView=itemView.findViewById(R.id.contestListText);
            about=itemView.findViewById(R.id.aboutcompany);
        }
        public View returnHolderView(){
            return view;
        }
    }
}
