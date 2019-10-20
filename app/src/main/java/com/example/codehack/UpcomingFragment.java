package com.example.codehack;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {

    TextView txtv;
    ImageButton favourite;
    static RecyclerView contestList=null;
    static View myView;
    SwipeRefreshLayout swipeRefreshLayout;
    private static String URL="https://clist.by:443/api/v1/contest/?start__gte=2019-10-20&limit=100&order_by=start&username=shubham13&api_key=f65ef54dd252de3177a053b85efd7817eb1cd169";
    @RequiresApi(api = Build.VERSION_CODES.O)
    public UpcomingFragment() {
        // Required empty public constructor
        //URL="https://clist.by:443/api/v1/contest/?start__gte="+ LocalDate.now() +"&order_by=start&username=shubham13&api_key=f65ef54dd252de3177a053b85efd7817eb1cd169";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //TextView t=container.findViewById(R.id.textv);
        //t.setText("Hehehe");

        View v=inflater.inflate(R.layout.fragment_upcoming,container,false);
        swipeRefreshLayout=v.findViewById(R.id.swipeLayoutUpcoming);
        contestList=v.findViewById(R.id.contestList);
        contestList.setLayoutManager(new LinearLayoutManager(getContext()));
        final RequestQueue queue= Volley.newRequestQueue(getContext());
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        contestList.setLayoutManager(new LinearLayoutManager(getContext()));
                        final String requestResult;
                        StringRequest request=new StringRequest(URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                GsonBuilder gsonBuilder=new GsonBuilder();
                                Gson gson=gsonBuilder.create();
                                //Toast.makeText(MainActivity.this,"yoyo",Toast.LENGTH_SHORT).show();
                                Contest contest=gson.fromJson(response,Contest.class);
                                contestList.setAdapter(new ContestAdapter(getContext(),contest));
                                storeAsLocalData(getContext(),contest);
                                Toast.makeText(getContext(),"Refreshing",Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getContext(),"hehehehe22222",Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Contest contest=getLocalSavedContestData();
                                if(contest!=null)
                                contestList.setAdapter(new ContestAdapter(getContext(),contest));
                                Toast.makeText(getContext(),"Local data",Toast.LENGTH_SHORT).show();
                            }
                        });
                        queue.add(request);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        },4000);
                    }
                });
        StringRequest request=new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder=new GsonBuilder();
                Gson gson=gsonBuilder.create();
                //Toast.makeText(MainActivity.this,"yoyo",Toast.LENGTH_SHORT).show();
                Contest contest=gson.fromJson(response,Contest.class);
                contestList.setAdapter(new ContestAdapter(getContext(),contest));
                storeAsLocalData(getContext(),contest);
                //Toast.makeText(getContext(),"hehehehe1111",Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this,"hehe",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Contest contest=getLocalSavedContestData();
                if(contest!=null)
                contestList.setAdapter(new ContestAdapter(getContext(),contest));
            }
        });
        queue.add(request);
        return v;
    }

    private void storeAsLocalData(Context context,Contest contest) {

        MyDatabaseHelper helper=new MyDatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete("CONTEST",null,null);
        List<Object> listObjects=contest.getObjects();
        for(Object o:listObjects){
            helper.insertData(o.getEvent(),o.getResource().getName(),o.getStart(),o.getEnd(),o.getDuration(),db);
        }
    }

    private Contest getLocalSavedContestData() {

        Contest contest = new Contest();
        Cursor cursor=null;
        try {
            MyDatabaseHelper helper = new MyDatabaseHelper(getContext());
            SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT EVENT,ABOUT,START,ENDD,DURATION FROM CONTEST", new String[]{});
            final StringBuilder builder=new StringBuilder("");
            List<Object> objectList=new ArrayList<Object>();
            if(cursor!=null) cursor.moveToFirst();
            do{
                String eventName=cursor.getString(0);
                String eventAbout=cursor.getString(1);
                String eventStartDateTme=cursor.getString(2);
                String eventEndDateTme=cursor.getString(3);
                int eventDuration=cursor.getInt(4);
                Object newObject=new Object();
                newObject.setEvent(eventName);
                Resource resource=new Resource();
                resource.setName(eventAbout);
                newObject.setResource(resource);
                newObject.setStart(eventStartDateTme);
                newObject.setEnd(eventEndDateTme);
                newObject.setDuration(eventDuration);
                objectList.add(newObject);
                builder.append(eventName+" "+eventAbout+"\n");
            }while(cursor.moveToNext());
            contest.setObjects(objectList);
        }catch (Exception ex){
            Toast.makeText(getContext(),"Error in retriving",Toast.LENGTH_SHORT).show();
        }
        return contest;
    }

}
