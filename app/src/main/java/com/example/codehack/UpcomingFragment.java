package com.example.codehack;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {

    TextView txtv;
    static RecyclerView contestList=null;
    static View myView;
    SwipeRefreshLayout swipeRefreshLayout;
    private static final String URL="https://clist.by:443/api/v1/contest/?start__gte=2019-10-19&username=shubham13&api_key=f65ef54dd252de3177a053b85efd7817eb1cd169";
    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //TextView t=container.findViewById(R.id.textv);
        //t.setText("Hehehe");
        View v=inflater.inflate(R.layout.fragment_upcoming,container,false);
        myView=v;
        final LayoutInflater tempinflater=inflater;
        contestList=v.findViewById(R.id.contestList);
        contestList.setLayoutManager(new LinearLayoutManager(getContext()));
        final String requestResult;
        StringRequest request=new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder=new GsonBuilder();
                Gson gson=gsonBuilder.create();
                int a=57;
                //Toast.makeText(MainActivity.this,"yoyo",Toast.LENGTH_SHORT).show();
                Contest contest=gson.fromJson(response,Contest.class);
                contestList.setAdapter(new ContestAdapter(contest));
                //Toast.makeText(MainActivity.this,"hehe",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);

        swipeRefreshLayout=MainActivity.swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                View v=tempinflater.inflate(R.layout.fragment_upcoming,container,false);
                myView=v;
                contestList=v.findViewById(R.id.contestList);
                contestList.setLayoutManager(new LinearLayoutManager(getContext()));
                final String requestResult;
                StringRequest request=new StringRequest(URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder=new GsonBuilder();
                        Gson gson=gsonBuilder.create();
                        int a=57;
                        //Toast.makeText(MainActivity.this,"yoyo",Toast.LENGTH_SHORT).show();
                        Contest contest=gson.fromJson(response,Contest.class);
                        contestList.setAdapter(new ContestAdapter(contest));
                        Toast.makeText(getContext(),"hehehehe",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);
            }
        });
       // contestList.setAdapter(new ContestAdapter(contest));
        return v;
    }

}
