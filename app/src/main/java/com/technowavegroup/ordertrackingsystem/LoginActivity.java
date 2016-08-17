package com.technowavegroup.ordertrackingsystem;



import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

import barcodeReader.MainActivity;
import database.DBAdapter;
import ip.ServiceIP;
import servicehandlers.ServiceHandler;
import toasts.CustomToast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{



AutoCompleteTextView  username;
    String username_str, password_str;
    EditText password;

    LinearLayout loginlayout;
    //ActionBar bar;
    Button signin;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String PassCode = "passCode";
    public static final String  TenantID= "tenantID";
    public static final String  UserName= "userName";

    SharedPreferences sharedpreferences;
    private static Animation shakeAnimation;


    String AUTHETICATION_URL=ServiceIP.URL+"User/Authenticate?LoginName=";
    private String tenantid="123";
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
       editor = sharedpreferences.edit();
      //  bar=getSupportActionBar();
       // bar.hide();




        if(sharedpreferences.getString(Name,"").equals("passcode")){
            Intent i=new Intent(LoginActivity.this,PassCodeActivity.class);
            startActivity(i);
            finish();
        }


        else {





            setContentView(R.layout.activity_login);
            username = (AutoCompleteTextView) findViewById(R.id.email);
            password = (EditText) findViewById(R.id.password);
             loginlayout= (LinearLayout) findViewById(R.id.login_layout);
            signin = (Button) findViewById(R.id.email_sign_in_button);

            shakeAnimation = AnimationUtils.loadAnimation(LoginActivity.this,
                    R.anim.shake);










            password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {




                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        Log.i("TAG", "Enter pressed");


                       signin.performClick();

                    }
                    return false;
                }
            });








            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if(checkInternetConenction()==true) {

                        username_str = username.getText().toString();
                        password_str = password.getText().toString();

                        new Authenicate().execute();

                    }else{
                        Toast.makeText(LoginActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    }


                }
            });


        }


    }

    public  class Authenicate extends AsyncTask<Void,Void,Void>{

        ProgressDialog pdialog;
        String  jstring;
        JSONArray array;
        String status;
        JSONObject object,res_ob;
        String error="Invalid UserName or PassWord";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdialog=new ProgressDialog(LoginActivity.this);
            pdialog.setMessage("Logging In...........");
            pdialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {


            ServiceHandler handler=new ServiceHandler();

            try {


                object=new JSONObject();
                object.put("LoginName",username_str);
                object.put("Passwd",password_str);
                object.put("Option","1");

                //http://54.251.98.159/OOPSSvc/api/

            jstring=handler.PostBody(ServiceIP.URL+"User/Authenticate",object);

             res_ob=new JSONObject(jstring);
            status=res_ob.getString("Status");
                error=res_ob.getString("ErrorDesc");
            JSONArray array=res_ob.getJSONArray("List");
            JSONObject single=array.getJSONObject(0);
            tenantid=single.getString("TenantID");




            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;







        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);








                pdialog.dismiss();








              /*  AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this) ;
                builder.setMessage(status);
                Dialog dialog=builder.create();
               dialog.show();*/


               // tenantid=res_ob.getString("TenantID");

                if(status.equals("true")){


                    //Toast.makeText(LoginActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(LoginActivity.this,PassCodeActivity.class);
                    editor.putString(TenantID, tenantid);
                    editor.putString(UserName,username_str);

                   editor.commit();
                   startActivity(i);




                    finish();

                }else if(status.equals("false")){


                    try {
                        JSONObject error_ob=new JSONObject(jstring);


                        loginlayout.startAnimation(shakeAnimation);

                        new CustomToast().Show_Toast(LoginActivity.this, loginlayout,
                                error_ob.getString("ErrorDesc"));



                        //Toast.makeText(LoginActivity.this,error_ob.getString("ErrorDesc"),Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }





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




