package com.technowavegroup.ordertrackingsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



/**
 * Created by technoway on 5/9/2016.
 */
public class PassCodeActivity extends AppCompatActivity {


    //ActionBar bar;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String PassCode = "passCode";
    SharedPreferences sharedpreferences;
    EditText passcode, passcodeSecond;
    Button quick_okbtn;
    Button notnow, ok_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // bar=getSupportActionBar();
        // bar.hide();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();

        if (sharedpreferences.getString(Name, "").equals("")) {


            setContentView(R.layout.passcode);
            notnow = (Button) findViewById(R.id.notnow);
            ok_btn = (Button) findViewById(R.id.ok_btn);
            passcode = (EditText) findViewById(R.id.passcode);
            passcode.setImeOptions(EditorInfo.IME_ACTION_DONE);

            notnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(PassCodeActivity.this, Home.class);
                    startActivity(i);
                    i.putExtra("key", "true");
                    finish();

                }
            });

            ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {






                        editor.putString(Name, "passcode");
                        editor.putString(PassCode, passcode.getText().toString());
                        editor.commit();
                        Intent i = new Intent(PassCodeActivity.this, Home.class);
                        startActivity(i);
                        i.putExtra("key", "false");
                        finish();

                }
            });


        } else {
            setContentView(R.layout.quicklog);
            passcodeSecond = (EditText) findViewById(R.id.passcode_second);
           // passcodeSecond.setImeOptions(EditorInfo.IME_ACTION_DONE);
            quick_okbtn = (Button) findViewById(R.id.quicklogScreenbtn);
            quick_okbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(checkInternetConenction()==true) {
                        if (sharedpreferences.getString(PassCode, "").equals(passcodeSecond.getText().toString())) {
                            Intent i = new Intent(PassCodeActivity.this, Home.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect PassCode", Toast.LENGTH_LONG).show();
                        }


                    }else{


                        Toast.makeText( PassCodeActivity.this, "No Internet Connection ", Toast.LENGTH_LONG).show();
                    }



                }
            });


            //Toast.makeText(PassCode.this,sharedpreferences.getString("Name",""),Toast.LENGTH_LONG).show();


        }


    }




    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            //  Toast.makeText(PassCodeActivity.this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            return false;
        }
        return false;

    }



}