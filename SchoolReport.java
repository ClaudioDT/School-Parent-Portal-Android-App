package com.example.engineer.parent_portal;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class SchoolReport extends AppCompatActivity {

    private ImageButton getStudentNumber;
    private TextView txtStdName,txtStdNumber,txtMark_1,txtMark_2,txtMark_3;
    private EditText editStudentNumber;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_report);

        txtStdName = (TextView)findViewById(R.id.std_name);
        txtStdNumber = (TextView)findViewById(R.id.std_number);
        txtMark_1 = (TextView)findViewById(R.id.mark_1);
        txtMark_2 = (TextView)findViewById(R.id.mark_2);
        txtMark_3 = (TextView)findViewById(R.id.mark_3);
        getStudentNumber = (ImageButton)findViewById(R.id.buttonGet);
        editStudentNumber = (EditText)findViewById(R.id.editorGetStdNumber);


        getStudentNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call the getData Function Here
                getServerData();
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    private void getServerData(){
        //Define the String to hold the id
        String getId = editStudentNumber.getText().toString().trim();
        if(TextUtils.isEmpty(getId)){
            Toast.makeText(this, "Please enter student number", Toast.LENGTH_LONG).show();
        }
        loadingBar = ProgressDialog.show(this,"Please wait...","Loading data",false,false);
        //Load the url
        String URL = ServerConfigurationForReports.Data_Url+editStudentNumber.getText().toString().trim();
        //Make the Http Request to the Server
        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        loadingBar.dismiss();
                    }
                }, 2000);
                //Call the function ShowServerData which allow user to see the Details on the App
                showServerData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SchoolReport.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showServerData(String response){
        String std_number  ="";
        String std_name    ="";
        String mark_1 ="";
        String mark_2 ="";
        String mark_3 ="";
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray(ServerConfigurationForReports.Json_Array);
            JSONObject collectData = jsonArray.getJSONObject(0);
            std_number =collectData.getString(ServerConfigurationForReports.key_std_num);
            std_name = collectData.getString(ServerConfigurationForReports.key_name);
            mark_1 =collectData.getString(ServerConfigurationForReports.key_mark_1);
            mark_2=collectData.getString(ServerConfigurationForReports.key_mark_2);
            mark_3=collectData.getString(ServerConfigurationForReports.key_mark_3);
        }catch (JSONException e){
            e.printStackTrace();
        }
        txtStdNumber.setText("\nStudent Number\t" +"\n"+std_number);
        txtStdName.setText("\nStudent Name\t"+"\n"+std_name);
        txtMark_1.setText("\nSemester I\t" +"\n"+ mark_1);
        txtMark_2.setText("\nSemester II\t"+"\n"+mark_2);
        txtMark_3.setText("\nSemester III\t" +"\n"+ mark_3);

    }

}
