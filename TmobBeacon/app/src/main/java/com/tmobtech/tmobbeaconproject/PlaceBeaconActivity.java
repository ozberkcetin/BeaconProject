package com.tmobtech.tmobbeaconproject;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tmobtech.tmobbeaconproject.entity.BeaconMap;

import java.util.List;

/**
 * Created by Ozberk on 15.7.2015.
 */
public class PlaceBeaconActivity extends ActionBarActivity implements View.OnClickListener {
    static String mapID;
    static String imagePath;
    private String TAG = "PlaceBeaconActivityError";

    private static final int CONTENT_VIEW_ID = 10101010;

    private Button buttonPrev;
    private Button buttonNext;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_beacon);

        initialize();
        initViews();

        buttonPrev.setVisibility(View.GONE);
        buttonNext.setVisibility(View.VISIBLE);
        setTitle("Set your beacons");
        setCurrentFragment(new SetBeaconFragment());
    }

    private void initViews() {
        buttonPrev = (Button) findViewById(R.id.buttonPrev);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonPrev.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        frameLayout = (FrameLayout) findViewById(R.id.framePlaceBeacon);

    }

    private void initialize() {
        mapID = getIntent().getStringExtra("mapId");
        imagePath=getIntent().getStringExtra("imagePath");
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getMapID() {
        return mapID;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonNext:
                buttonNext.setVisibility(View.GONE);
                buttonPrev.setVisibility(View.VISIBLE);
                setTitle("Set your places");
                setCurrentFragment(new SetPlaceFragment());

                break;
            case R.id.buttonPrev:
                buttonPrev.setVisibility(View.GONE);
                buttonNext.setVisibility(View.VISIBLE);
                setTitle("Set your beacons");
                setCurrentFragment(new SetBeaconFragment());
                break;
        }
    }

    public void setCurrentFragment(Fragment fragment) {

        Fragment fragment1=getSupportFragmentManager().findFragmentById(R.id.fragmentPlace);

        if (fragment1==null) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);


            transaction.replace(R.id.framePlaceBeacon, fragment);

            transaction.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_beacon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {


            case R.id.logOutId :
                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(PlaceBeaconActivity.this);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.remove("Login");
                editor.remove("Name");
                editor.remove("Password");
                editor.commit();
                Intent i=new Intent(PlaceBeaconActivity.this,SignInActivity.class);
                startActivity(i);
                this.finish();
                break;

            case R.id.exitId:
                int p = android.os.Process.myPid();
                android.os.Process.killProcess(p);
                break;

        }





        return super.onOptionsItemSelected(item);
    }

    private void editMap(final String mapID) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_map);
        dialog.setTitle("Edit Map");

        final EditText editText = (EditText) dialog.findViewById(R.id.editText_map_name_edit_dialog);
        final Button button = (Button) dialog.findViewById(R.id.button_edit_dialog);

        ParseQuery<BeaconMap> query = ParseQuery.getQuery("BeaconMap");
        query.getInBackground(mapID, new GetCallback<BeaconMap>() {
            @Override
            public void done(final BeaconMap beaconMap, ParseException e) {
                if (e == null) {
                    editText.setText(beaconMap.getName());
                    editText.setSelection(editText.getText().toString().length());

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!editText.getText().toString().equals("")) {
                                beaconMap.setName(editText.getText().toString());
                                beaconMap.saveInBackground();
                                dialog.cancel();
                            }
                        }
                    });
                } else {
                    // something went wrong
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
