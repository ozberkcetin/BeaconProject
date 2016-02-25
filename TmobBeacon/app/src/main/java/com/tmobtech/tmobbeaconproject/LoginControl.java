package com.tmobtech.tmobbeaconproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.parse.Parse;
import com.parse.ParseException;
import com.tmobtech.tmobbeaconproject.utility.ParseCore;


public class LoginControl extends ActionBarActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_control);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                try {
                    ParseCore parseCore=new ParseCore(LoginControl.this);
                    SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(LoginControl.this);
                    boolean isLogin=sharedPreferences.getBoolean("Login", false);
                    String name=sharedPreferences.getString("Name", "Bos");
                    String passWord=sharedPreferences.getString("Password","Bos");
                    if (isLogin)
                    {
                        parseCore.authenticateUser(name,passWord,LoginControl.this,false);
                    }
                    else {
                        Intent i = new Intent(LoginControl.this, SignInActivity.class);
                        startActivity(i);
                        LoginControl.this.finish();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, 0);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_control, menu);
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
}
