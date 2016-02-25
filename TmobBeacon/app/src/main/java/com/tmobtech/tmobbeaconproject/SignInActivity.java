package com.tmobtech.tmobbeaconproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;

import com.tmobtech.tmobbeaconproject.utility.ParseCore;

/*
* Created by Deniz Katipoglu
 * */
public class SignInActivity extends ActionBarActivity {
    private EditText userName, password;
    private Button signIn;
    private TextView signUp;
    ParseCore parseCore;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);




        initialize();



        //giriş button
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(SignInActivity.this);
                pd.setMessage("Loading");



                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected void onPreExecute() {
                        //Show UI
                        //Start your progress bar

                        pd.show();
                    }

                    @Override
                    protected Void doInBackground(Void... arg0) {

                        try {
                            parseCore.authenticateUser(userName.getText().toString(),password.getText().toString(),SignInActivity.this,rememberMe.isChecked());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                    }

                };
                task.execute((Void[]) null);


            }
        });
        //kayıt olma ekranını açar..
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initialize() {

        userName = (EditText) findViewById(R.id.etUserName);
        password = (EditText) findViewById(R.id.etPass);
        signIn = (Button) findViewById(R.id.btnSingIn);
        signUp = (TextView) findViewById(R.id.singUp);
        rememberMe=(CheckBox)findViewById(R.id.checkBox2);
        parseCore=new ParseCore(SignInActivity.this);
        try {
            parseCore.authenticateUser(userName.getText().toString(),password.getText().toString(),SignInActivity.this,rememberMe.isChecked());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {


            case R.id.exitId:
                int p = android.os.Process.myPid();
                android.os.Process.killProcess(p);
                break;

        }


        return super.onOptionsItemSelected(item);
    }
}
