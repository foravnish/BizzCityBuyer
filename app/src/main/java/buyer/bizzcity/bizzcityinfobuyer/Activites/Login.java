package buyer.bizzcity.bizzcityinfobuyer.Activites;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.test.espresso.core.internal.deps.guava.base.Strings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import buyer.bizzcity.bizzcityinfobuyer.Fragments.SubCategory;
import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Const;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;

public class Login extends AppCompatActivity {

    EditText mobile,password;
    Button btnLogin ;
    TextView btnReg,skipNow,forgt;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobile=findViewById(R.id.mobile);
        password=findViewById(R.id.password);

        btnLogin=findViewById(R.id.btnLogin);
        btnReg=findViewById(R.id.btnReg);
        skipNow=findViewById(R.id.skipNow);
        forgt=findViewById(R.id.forgt);

        dialog=new Dialog(Login.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        skipNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                if (netInfo == null){
                    Toast.makeText(getApplicationContext(), "Please Connect to the internet...", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(Login.this,HomeAct.class);
                    intent.putExtra("type","0");
                    startActivity(intent);
                }

            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });

        forgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Login.this);
//                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.otp_dialog);

                final EditText mobile_edit= (EditText) dialog.findViewById(R.id.mobile_edit);
                Button submit=(Button)dialog.findViewById(R.id.submit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (TextUtils.isEmpty(mobile_edit.getText().toString())){

                            Toast.makeText(Login.this, "Please Enter Mobile No.", Toast.LENGTH_SHORT).show();
                        }
                        else if (!(mobile_edit.getText().length() ==10)){
                            Toast.makeText(Login.this, "Please Enter 10 digit Mobile No.", Toast.LENGTH_SHORT).show();
                        }
                        else {

                           otpAPi(mobile_edit.getText().toString(),dialog);

                           Log.d("sdfsdfsdfsdgsfdgsdg",mobile_edit.getText().toString());

                        }
                    }
                });

                dialog.show();

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(validate()){

                    Util.showPgDialog(dialog);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, Api.login,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.d("Response", response);
                                    Util.cancelPgDialog(dialog);
                                    try {
                                        JSONObject jsonObject=new JSONObject(response);
                                        if (jsonObject.getString("status").equalsIgnoreCase("success")){

                                            JSONArray jsonArray=jsonObject.getJSONArray("message");
                                            for (int i=0;i<jsonArray.length();i++) {
                                                JSONObject jsonObject1 = jsonArray.optJSONObject(i);


                                                Toast.makeText(getApplicationContext(), "Login Successfully...", Toast.LENGTH_SHORT).show();

                                                MyPrefrences.setUserLogin(getApplicationContext(), true);
                                                MyPrefrences.setUserID(getApplicationContext(), jsonObject1.optString("id").toString());
//                                            MyPrefrences.setCatID(getApplicationContext(),jsonObject1.optString("cat_id").toString());
//                                            MyPrefrences.setSCatID(getApplicationContext(),jsonObject1.optString("subcat").toString());
                                                MyPrefrences.setUSENAME(getApplicationContext(), jsonObject1.optString("buyer_name").toString());
                                            MyPrefrences.setEMAILID(getApplicationContext(),jsonObject1.optString("email").toString());
                                            MyPrefrences.setMobile(getApplicationContext(),jsonObject1.optString("mobile").toString());
                                            MyPrefrences.setImage(getApplicationContext(),jsonObject1.optString("image").toString());

                                            }
                                            Intent intent=new Intent(Login.this,HomeAct.class);
                                            intent.putExtra("type","0");
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(Login.this, "Error! Please connect to the Internet.", Toast.LENGTH_SHORT).show();
                                    Util.cancelPgDialog(dialog);
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("mobile", mobile.getText().toString());
                            params.put("password", password.getText().toString());

                            return params;
                        }
                    };
                    postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    postRequest.setShouldCache(false);

                    AppController.getInstance().addToRequestQueue(postRequest);

                }

            }
        });

    }

    private void otpAPi(final String mob, final Dialog dialog2) {

        Log.d("sdfsdgfvsdfgsdgvsg",mob);

        Util.showPgDialog(dialog);
        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(Request.Method.GET,
                Api.buyerForgetPasswordOtp+"?mobileNumber="+mob, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("ResposeBaner", response.toString());
                Util.cancelPgDialog(dialog);
                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");
                    HashMap<String,String> hashMap = null;
                    if (response.getString("status").equalsIgnoreCase("success")){

                        JSONObject jsonObject=response.getJSONObject("message");

                        String  otp=jsonObject.optString("otp");
                        dialog2.dismiss();
                        otpVerfy(mob.toString(),otp);

                    }
                    else if (response.getString("status").equalsIgnoreCase("failure")){
                        Toast.makeText(Login.this, ""+response.optString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Util.cancelPgDialog(dialog);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Respose", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Error! Please Connect to the internet", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                Util.cancelPgDialog(dialog);

            }
        });

        // Adding request to request queue
        jsonObjReq2.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(jsonObjReq2);
    }

    private void otpVerfy(final String mob, final String otp) {

        Log.d("sdfdsfsdfsdfsgsg",mob);
        final Dialog dialog2 = new Dialog(Login.this);
//                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.otp_dialog_verfy);
        dialog2.setCancelable(false);

        final EditText otp_edit= (EditText) dialog2.findViewById(R.id.otp_edit);
        TextView recieve= (TextView) dialog2.findViewById(R.id.recieve);
        recieve.setText("Sent OTP on "+mob);
        Button submit2=(Button)dialog2.findViewById(R.id.submit2);
        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(otp_edit.getText().toString())){

                    Toast.makeText(Login.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }

                else {
                    if (otp.equalsIgnoreCase(otp_edit.getText().toString())){
                        newpassword(mob);
                        dialog2.dismiss();
                    }
                    else {
                        Util.errorDialog(Login.this,"Enter Correct OTP");
                    }

                }
            }
        });

        dialog2.show();



    }

    private void newpassword(final String mob) {

        Log.d("dsfsdfsdgfsdgdf",mob);
        final Dialog dialog2 = new Dialog(Login.this);
//                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.new_password);

        final EditText password= (EditText) dialog2.findViewById(R.id.password);
        final EditText passwordConfirm= (EditText) dialog2.findViewById(R.id.passwordConfirm);

        Button submitPassword=(Button)dialog2.findViewById(R.id.submitPassword);
        submitPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(password.getText().toString())) {

                    Util.errorDialog(Login.this,"Please Enter New Password");
                }
                else if (TextUtils.isEmpty(passwordConfirm.getText().toString())) {

                    Util.errorDialog(Login.this,"Please Enter Confirm Password");
                }
                else if (!password.getText().toString().equals(passwordConfirm.getText().toString())){

                    Util.errorDialog(Login.this,"Confirm Password must be same !");
                }
                else {

                   // new CreatePassword(Login.this,password.getText().toString(),dialog2,mob).execute();
                    createPasswordApi(password.getText().toString(),dialog2,mob);
                }
            }
        });

        dialog2.show();

    }

    private void createPasswordApi(final String password, final Dialog dialog2, final String mob) {


        StringRequest postRequest = new StringRequest(Request.Method.POST, Api.createNewPassword,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        Util.cancelPgDialog(dialog);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase("success")){


                                if (jsonObject != null) {
                                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {

                                        Util.errorDialog(Login.this,"Password Successfully Created...");
                                        dialog2.dismiss();

                                    }
                                    else {
                                        Util.errorDialog(Login.this,jsonObject.optString("message"));
                                    }
                                }


                            }
                            else{
                                Toast.makeText(getApplicationContext(),jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(Login.this, "Error! Please connect to the Internet.", Toast.LENGTH_SHORT).show();
                        Util.cancelPgDialog(dialog);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("mobileNumber", mob);
                params.put("newPassword", password.toString());

                Log.d("dsfsdfsdfsdgfsdgdfgd",mob);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        postRequest.setShouldCache(false);

        AppController.getInstance().addToRequestQueue(postRequest);




    }

    private boolean validate(){

        if (TextUtils.isEmpty(mobile.getText().toString()))
        {
            mobile.setError("Oops! Mobile field blank");
            mobile.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(password.getText().toString()))
        {
            password.setError("Oops! Password field blank");
            password.requestFocus();
            return false;
        }

        return true;

    }
}
