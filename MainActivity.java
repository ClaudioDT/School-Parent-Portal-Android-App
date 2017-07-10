package com.example.engineer.parent_portal;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btn_notification;
    private Button btn_attendance;
    private Button btn_student;
    private Button btn_schoolReport;
    private TextView textViewToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewToken = (TextView) findViewById(R.id.textViewToken);
        //Find Element by id
        btn_attendance = (Button)findViewById(R.id.bt_attendance);
        btn_notification = (Button)findViewById(R.id.bt_notification);
        btn_student = (Button)findViewById(R.id.bt_student_infor);
        btn_schoolReport = (Button)findViewById(R.id.bt_school_report);

        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Attendance Activity in order to view the attendance per student
                //Search for a student number and the click button view and display the attendance of student
                Intent intentAttendance = new Intent(MainActivity.this, SchoolAttendance.class);
                startActivity(intentAttendance);
            }
        });

        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Notification Activity Displaying all the list of activities for school
                Intent intentNotification  = new Intent(MainActivity.this, SchoolNotification.class);
                startActivity(intentNotification);
            }
        });

        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open an activity were parents can search for a student then display the information about the student
                Intent intentStudent = new Intent(MainActivity.this, SchoolStudents.class);
                startActivity(intentStudent);
            }
        });

        btn_schoolReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open activity school reports open a webView were parents can download the reports for each month
                Intent intentSchoolReport = new Intent(MainActivity.this, SchoolReport.class);
                startActivity(intentSchoolReport);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Compose an email Intent to allow parents to send an email to school for any enquiries
               /* Snackbar.make(view, "Send an Email to School", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
                intentEmail.setData(Uri.parse("mailto:"));
                intentEmail.putExtra(Intent.EXTRA_EMAIL, "Email Address");
                intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                if(intentEmail.resolveActivity(getPackageManager())!=null){
                    startActivity(intentEmail);
                }*/



            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intentSettings = new Intent(MainActivity.this, SettingInstractions.class);
            startActivity(intentSettings);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about ) {
            //Call the about Activity
            Intent intentAbout = new Intent(MainActivity.this, About.class);
            startActivity(intentAbout);
        } else if (id == R.id.nav_contact) {
            Intent intentContact = new Intent(MainActivity.this, SchoolContact.class);
            startActivity(intentContact);

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "I would like to share with you this App. www.myApp.com ");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_send) {
            Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
            intentEmail.setData(Uri.parse("mailto:"));
            intentEmail.putExtra(Intent.EXTRA_EMAIL, "Email Address");
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            if(intentEmail.resolveActivity(getPackageManager())!=null){
                startActivity(intentEmail);
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
