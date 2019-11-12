package com.example.pallavi.navigationdrawerapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import org.w3c.dom.Text;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView tvnameDrawer;
    String username,useremail,usercontact,useraddress,usergender;
    Button uploadmh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




      /*  tvnameDrawer = (TextView) findViewById(R.id.name_drawer);
        tvnameDrawer.setText("Rutuja");
        */
      username = getIntent().getStringExtra("username");
      useremail = getIntent().getStringExtra("useremail");
      usercontact = getIntent().getStringExtra("usercontact");
      useraddress = getIntent().getStringExtra("useraddress");
      usergender = getIntent().getStringExtra("usergender");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationVie = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationVie.getHeaderView(0);

        TextView nav_user = (TextView)hView.findViewById(R.id.name_drawer);
        TextView nav_email = (TextView)hView.findViewById(R.id.email_drawer);
        nav_user.setText(username);
        nav_email.setText(useremail);

        navigationVie.setNavigationItemSelectedListener(this);

      //  TextView welcome = (TextView) findViewById(R.id.tvWelcome);
      //  welcome.setText("Hello ! "+username);
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
        getMenuInflater().inflate(R.menu.navigation, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void DisplaySelected(int id)
    {

        Fragment fragment = null;

        switch(id)
        {
            case R.id.nav_details:
            {

                Bundle bundle = new Bundle();
                bundle.putString("name",username);
                bundle.putString("email",useremail);
                bundle.putString("contact",usercontact);
                bundle.putString("address",useraddress);
                bundle.putString("gender",usergender);
                fragment = new user_details();
                fragment.setArguments(bundle);

                break;
            }
            case R.id.nav_doc:
            {
                fragment = new searchdoctor();
                break;
            }
            case R.id.nav_disease:
            {
                fragment = new searchdisease();
                break;
            }
            case R.id.nav_feedback:
            {
                fragment = new feedbackf();
                break;
            }
            case R.id.nav_logout:
            {
                Intent i = new Intent(NavigationActivity.this,LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                this.finish();
                break;
            }
            case R.id.home_page:
            {
                Bundle bundle = new Bundle();
                bundle.putString("name", username);
                bundle.putString("email", useremail);
                bundle.putString("contact", usercontact);
                bundle.putString("address", useraddress);
                bundle.putString("gender", usergender);
                fragment = new Home_fragment();
                fragment.setArguments(bundle);
            }
        }

        if(fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DisplaySelected(id);

        return true;
    }
}
