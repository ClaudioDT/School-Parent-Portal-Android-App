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

public class SchoolStudents extends AppCompatActivity {
    private TextView txtStdName,txtStdSurname,txtStdEmail,txtStdGrade,txtStdNumber;
    private TextView txtPrtName,txtPrtEmail,txtPrtContact,txtPrtAddress;
    private EditText editorGetEmail;
    private ImageButton btnSearch;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_students);

        txtStdName    = (TextView)findViewById(R.id.std_name);
        txtStdSurname = (TextView)findViewById(R.id.std_surname);
        txtStdEmail   = (TextView)findViewById(R.id.std_email);
        txtStdGrade   = (TextView)findViewById(R.id.std_grade);
        txtStdNumber  = (TextView)findViewById(R.id.std_number);
        txtPrtName    = (TextView)findViewById(R.id.prt_name);
        txtPrtEmail   = (TextView)findViewById(R.id.prt_email);
        txtPrtContact = (TextView)findViewById(R.id.prt_contact);
        txtPrtAddress = (TextView)findViewById(R.id.prt_address);
        editorGetEmail= (EditText)findViewById(R.id.editorGetEmail);
        btnSearch     = (ImageButton)findViewById(R.id.buttonGet);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call the Get Data Function Here
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
        String getEmail = editorGetEmail.getText().toString().trim();
        if(TextUtils.isEmpty(getEmail)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
        }


        loadingBar = ProgressDialog.show(this,"Please wait...","Loading data",false,false);
        //Load the url
        String URL = ServerConfigurationForStudents.Data_Url+editorGetEmail.getText().toString().trim();

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
                Toast.makeText(SchoolStudents.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Creating a function that Gets the data From the Server Configuration File
    private void showServerData(String response){
        String std_number ="";
        String std_name   ="";
        String std_surname="";
        String std_email  ="";
        String std_grade  ="";
        String prt_name   ="";
        String prt_email  ="";
        String prt_contact="";
        String prt_address="";
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray(ServerConfigurationForStudents.Json_Array);
            JSONObject collectData = jsonArray.getJSONObject(0);
            std_number =collectData.getString(ServerConfigurationForStudents.key_std_num);
            std_name = collectData.getString(ServerConfigurationForStudents.key_student_name);
            std_surname = collectData.getString(ServerConfigurationForStudents.key_student_surname);
            std_email = collectData.getString(ServerConfigurationForStudents.key_student_email);
            std_grade = collectData.getString(ServerConfigurationForStudents.key_student_grade);
            prt_name  = collectData.getString(ServerConfigurationForStudents.key_parent_name);
            prt_email = collectData.getString(ServerConfigurationForStudents.key_parent_email);
            prt_contact = collectData.getString(ServerConfigurationForStudents.key_parent_contact);
            prt_address = collectData.getString(ServerConfigurationForStudents.key_parent_address);
        }catch (JSONException e){
            e.printStackTrace();
        }
        //Setting the Text into the TextViews for Results
        txtStdNumber.setText("\nStudent#\t" +"\n"+ std_number);
        txtStdName.setText("\nName\t"+"\n"+std_name);
        txtStdSurname.setText("\nSurname\t"+"\n"+std_surname);
        txtStdEmail.setText("\nEmail\t"+"\n"+std_email);
        txtStdGrade.setText("\nGrade\t"+"\n"+std_grade);
        txtPrtName.setText("\nName\t"+"\n"+prt_name);
        txtPrtEmail.setText("\nEmail\t"+"\n"+prt_email);
        txtPrtContact.setText("\nContact\t"+"\n"+prt_contact);
        txtPrtAddress.setText("\nAddress\t"+"\n"+prt_address);
    }

}
