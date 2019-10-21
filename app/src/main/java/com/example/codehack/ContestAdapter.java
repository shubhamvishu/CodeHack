package com.example.codehack;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.ContestViewHolder> {
    private Contest contest;
    private Context context;
    static int c=0,rand=0;
    public ContestAdapter(Context context,Contest contest){
        this.context=context;
        this.contest=contest;
    }
    @NonNull
    @Override
    public ContestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.contest_item_layout,parent,false);
        /*final ImageView imgbtn= view.findViewById(R.id.favorite_star);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c==0){
                    imgbtn.setImageResource(R.drawable.star_button_pressed);
                    c=1;
                }
                else {
                    imgbtn.setImageResource(R.drawable.star_button_normal);
                    c=0;
                }
            }
        });*/
        return new ContestViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final ContestViewHolder holder, int position) {
        final Object contestObject=contest.getObjects().get(position);
        holder.textView.setText(contestObject.getEvent());
        holder.textView.setBackgroundColor(0xFFFFFFFF);
        holder.imgIcon.setBackgroundColor(0xFFFFFFFF);
        holder.about.setText(contestObject.getResource().getName());
        holder.date.setText(contestObject.getStart().substring(0,contestObject.getStart().indexOf("T")));
        holder.timings.setText(contestObject.getStart().substring(contestObject.getStart().indexOf("T")+2));
        Log.d("MSG","Check current"+LocalDate.now().toString()+" "+contestObject.getStart().substring(0,contestObject.getStart().indexOf("T"))+String.valueOf(LocalDate.now().toString()==contestObject.getStart().substring(0,contestObject.getStart().indexOf("T"))));
        if(compareDates(LocalDate.now().toString(),contestObject.getStart().substring(0,contestObject.getStart().indexOf("T")))) {
            Log.d("MSG","Check current"+" I");
            holder.btn.setBackground(context.getResources().getDrawable(R.drawable.btn_black));
            holder.circleIcon.setImageResource(R.drawable.light_blue_box);
        }
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContestPageActivity.contestObject=contestObject;
                context.startActivity(new Intent(context,ContestPageActivity.class));
                Toast.makeText(context,contestObject.getStart(),Toast.LENGTH_SHORT).show();
            }
        });
        final String contestName=contestObject.getResource().getName();
        boolean bool=true;
        for(int i=0;i<contestObject.getEvent().length();i++){
            if((int)contestObject.getEvent().charAt(i)>255 ){
                bool=false;
                break;
            }
        }
        if(!bool){
            //holder.view.setVisibility(View.GONE);
            //holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        Glide.with(holder.returnHolderView()).load(getImageLink(contestName)).into(holder.imgIcon);

    }

    @Override
    public int getItemCount() {
        return contest.getObjects().size();
    }

    public class ContestViewHolder extends RecyclerView.ViewHolder{
        ImageView imgIcon;
        CircleImageView circleIcon;
        TextView textView,about,timings,date;
        Button btn;
        View view;
        public ContestViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            imgIcon=itemView.findViewById(R.id.contestListImage);
            circleIcon=itemView.findViewById(R.id.contestListOnline);
            textView=itemView.findViewById(R.id.contestListText);
            about=itemView.findViewById(R.id.aboutcompany);
            date=itemView.findViewById(R.id.contestDate);
            timings=itemView.findViewById(R.id.contestTime);
            btn=itemView.findViewById(R.id.details_btn);
        }
        public View returnHolderView(){
            return view;
        }
    }
    public boolean compareDates(String d1,String d2){
        for(int i=0;i<d1.length();i++){
            if(d1.charAt(i)>d2.charAt(i))
                return true;
            if(d1.charAt(i)<d2.charAt(i))
                return false;
        }
        return true;
    }
    public static String getImageLink(String contestName){
        String randomColors[]={"0D8ABC","D35400","F1C40F","17A589","7D3C98","E74C3C","145A32","7F8C8D","2C3E50","922B21"};
        Random random=new Random();
        rand=random.nextInt(10);
        String link="";
        if(contestName.contains("hackerearth"))
            link="https://static-fastly.hackerearth.com/static/hackerearth/images/logo/HE_identity.png";
        else if(contestName.contains("hackerrank"))
            link="https://upload.wikimedia.org/wikipedia/commons/6/65/HackerRank_logo.png";
        else if(contestName.contains("codeforce"))
            link="https://1.bp.blogspot.com/-pBimI1ZhYAA/Wnde0nmCz8I/AAAAAAAABPI/5LZ2y9tBOZIV-pm9KNbyNy3WZJkGS54WgCPcBGAYYCw/s1600/codeforce.png";
        else if(contestName.contains("codechef"))
            link="https://pbs.twimg.com/profile_images/1051765509912449025/0iFDzwqr.jpg";
        else if(contestName.contains("topcoder"))
            link="https://www.topcoder.com/wp-content/uploads/2016/01/topcoder-logo.png";
        else if(contestName.contains("kaggle"))
            link="https://www.kaggle.com/static/images/site-logo.png";
        else if(contestName.contains("leetcode"))
            link="https://leetcode.com/static/images/LeetCode_logo.png";
        else if(contestName.contains("google"))
            link="https://yt3.ggpht.com/a/AGF-l7-BBIcC888A2qYc3rB44rST01IEYDG3uzbU_A=s900-c-k-c0xffffffff-no-rj-mo";
        else
            link="https://ui-avatars.com/api/?background="+randomColors[rand]+"&color=fff&name="+contestName;

        return link;
    }
}
