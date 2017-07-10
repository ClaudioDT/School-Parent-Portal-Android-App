package com.example.engineer.parent_portal;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class SchoolNotification extends AppCompatActivity {

    List<MyNotificationAdapter> myFirstAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager myRecyclerManager;
    RecyclerView.Adapter myRecyclerAdapter;
    ProgressBar progressBar;
    private Button LoadNotification;
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_school_notification);

        myFirstAdapter   = new ArrayList<>();
        recyclerView     = (RecyclerView)findViewById(R.id.recyclerView1);
        progressBar      = (ProgressBar)findViewById(R.id.progressBar1);
        LoadNotification = (Button)findViewById(R.id.button) ;
        recyclerView.setHasFixedSize(true);
        myRecyclerManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myRecyclerManager);

        LoadNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Connect();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void Connect(){
        jsonArrayRequest = new JsonArrayRequest(ServerConfigurationForNotification.key_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        DisplayData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void DisplayData(JSONArray array){
        for(int i = 0; i<array.length(); i++) {
            MyNotificationAdapter myNotificationAdapter2 = new MyNotificationAdapter();
            JSONObject getJson;
            try {
                getJson = array.getJSONObject(i);
                myNotificationAdapter2.setNotify_name(getJson.getString(ServerConfigurationForNotification.key_name));
                myNotificationAdapter2.setNotify_date(getJson.getString(ServerConfigurationForNotification.key_date));
                myNotificationAdapter2.setNotify_description(getJson.getString(ServerConfigurationForNotification.key_descrption));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            myFirstAdapter.add(myNotificationAdapter2);
        }
        myRecyclerAdapter = new RecyclerViewAdapter(myFirstAdapter, this);
        recyclerView.setAdapter(myRecyclerAdapter);
    }
}