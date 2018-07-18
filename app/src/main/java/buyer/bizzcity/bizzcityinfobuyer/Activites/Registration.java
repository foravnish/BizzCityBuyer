package buyer.bizzcity.bizzcityinfobuyer.Activites;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;

import static android.text.format.DateFormat.is24HourFormat;

public class Registration extends AppCompatActivity {

    Button btnSubReg;
    EditText editTextname,editmobile,editPassword,editPasswordCon;
    TextView editTextdob,skipNow;
    //Spinner editLocation;
    Dialog dialog;
    List<String> location = new ArrayList<String>();
    List<HashMap<String, String>> DataLoc;
    ArrayAdapter aa,subcat;
    DatePickerDialog  datePickerDialog;
    TextView loginNow;
    Spinner gender;
    String[] cat ={"Male","Female"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnSubReg=findViewById(R.id.btnSubReg);

       // editLocation=findViewById(R.id.editLocation);
        editPassword=findViewById(R.id.editPassword);
        editPasswordCon=findViewById(R.id.editPasswordCon);
        editmobile=findViewById(R.id.editmobile);
        editTextname=findViewById(R.id.editTextname);
        skipNow=findViewById(R.id.skipNow);

        editTextdob=findViewById(R.id.editTextdob);
        loginNow=findViewById(R.id.loginNow);
        gender=findViewById(R.id.gender);

        dialog=new Dialog(Registration.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        DataLoc=new ArrayList<>();



        skipNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(Registration.this, "Please wait...while opening home page", Toast.LENGTH_SHORT).show();

                ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                if (netInfo == null){
                    Toast.makeText(getApplicationContext(), "Please Connect to the internet...", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(Registration.this,HomeAct.class);
                    intent.putExtra("type","0");
                    startActivity(intent);
                }

            }
        });
        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
        });

        editTextdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Calendar c = Calendar.getInstance();

                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(Registration.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                editTextdob.setText(i2 + "/" + (i1 + 1)+ "/" + i);
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });
        btnSubReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("sdfvdgdfghfg",gender.getSelectedItem().toString());
                if(validate()){

                    otpAPi(editmobile.getText().toString());



                    //submitRegistration();
                }
            }
        });


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item2, cat);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(dataAdapter);









//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                Api.location, null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("Respose", response.toString());
//
//                Util.cancelPgDialog(dialog);
//                try {
//                    // Parsing json object response
//                    // response will be a json object
////                    String name = response.getString("name");
//
//                    if (response.getString("status").equalsIgnoreCase("success")){
//
//
//                        location.clear();
//                        HashMap<String, String> map2 = new HashMap<>();
//                        map2.put("category", "");
//                        DataLoc.add(map2);
//                        location.add("Select Area");
//
//                        JSONArray jsonArray=response.getJSONArray("message");
//                        for (int i=0;i<jsonArray.length();i++){
//                            JSONObject jsonObject=jsonArray.getJSONObject(i);
//
//                            HashMap<String,String> map=new HashMap();
//                            map.put("id",jsonObject.optString("id"));
//                            map.put("state_id",jsonObject.optString("state_id"));
//                            map.put("city_id",jsonObject.optString("city_id"));
//                            map.put("location",jsonObject.optString("location"));
//
//                            location.add(jsonObject.optString("location"));
//
//                            DataLoc.add(map);
//
//                            aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,location);
//                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            editLocation.setAdapter(aa);
//
//                        }
//                        HashMap<String, String> map3 = new HashMap<>();
//                        map3.put("category", "");
//                        DataLoc.add(map3);
//                        location.add("Other Area");
//
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                    Util.cancelPgDialog(dialog);
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("Respose", "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        "Error! Please Connect to the internet", Toast.LENGTH_SHORT).show();
//                // hide the progress dialog
//                Util.cancelPgDialog(dialog);
//
//            }
//        });

        // Adding request to request queue
//        jsonObjReq.setShouldCache(false);
//        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    private void otpAPi(final String mob) {

        Util.showPgDialog(dialog);
        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(Request.Method.GET,
                Api.buyerRegistrationOtp+"?mobileNumber="+mob, null, new Response.Listener<JSONObject>() {

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

                        otpVerfy(mob.toString(),otp);

                    }
                    else if (response.getString("status").equalsIgnoreCase("failure")){
                        //Toast.makeText(Registration.this, ""+response.optString("message"), Toast.LENGTH_SHORT).show();
                        Util.errorDialog(Registration.this,response.optString("message"));
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

        final Dialog dialog2 = new Dialog(Registration.this);
//                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.otp_dialog_verfy);
       // dialog2.setCancelable(false);

        final EditText otp_edit= (EditText) dialog2.findViewById(R.id.otp_edit);
        TextView recieve= (TextView) dialog2.findViewById(R.id.recieve);
        TextView resend= (TextView) dialog2.findViewById(R.id.resend);
        recieve.setText("Sent OTP on "+mob);
        Button submit2=(Button)dialog2.findViewById(R.id.submit2);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Util.showPgDialog(dialog);
                JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(Request.Method.GET,
                        Api.buyerRegistrationOtp+"?mobileNumber="+mob, null, new Response.Listener<JSONObject>() {

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

                                dialog2.dismiss();
                                JSONObject jsonObject=response.getJSONObject("message");

                                String  otp=jsonObject.optString("otp");

                                otpVerfy(mob.toString(),otp);

                            }
                            else if (response.getString("status").equalsIgnoreCase("failure")){
                                //Toast.makeText(Registration.this, ""+response.optString("message"), Toast.LENGTH_SHORT).show();
                                Util.errorDialog(Registration.this,response.optString("message"));
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
        });
        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(otp_edit.getText().toString())){

                    Toast.makeText(Registration.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }

                else {
                    if (otp.equalsIgnoreCase(otp_edit.getText().toString())){
//                        newpassword(mob);
                        submitRegistration();
                        dialog2.dismiss();
                    }
                    else {
                        Util.errorDialog(Registration.this,"Enter Correct OTP");
                    }

                }
            }
        });

        dialog2.show();

    }

    private void submitRegistration() {

        Util.showPgDialog(dialog);

        StringRequest postRequest = new StringRequest(Request.Method.POST, Api.registration,
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

                                Toast.makeText(getApplicationContext(),"Registration Successfully..." , Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Registration.this, Login.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(),"Please Login..." , Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Util.errorDialog(Registration.this,jsonObject.getString("message"));
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
                        Toast.makeText(Registration.this, "Error! Please connect to the Internet.", Toast.LENGTH_SHORT).show();
                        Util.cancelPgDialog(dialog);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                TelephonyManager manager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                String carrierName = manager.getNetworkOperatorName();
                Log.d("dsfsdfgsdgdfgdf",carrierName);

                String reqString = Build.MANUFACTURER
                        + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                        + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
                Log.d("dfdsgvdgdfgd",reqString);

                WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
                Log.d("dfgdfgdfhdhfd",ip);

                Map<String, String>  params = new HashMap<String, String>();
                params.put("userName", editTextname.getText().toString());
                params.put("email","NoEmail");
                params.put("mobile",editmobile.getText().toString());
                params.put("password",editPassword.getText().toString());
                params.put("location","no Location");
                params.put("dateOfBirth","Not Set");
                params.put("gcmId","abc");

                params.put("gender",gender.getSelectedItem().toString());
                params.put("ip",ip);
                params.put("phoneModel",reqString);
                params.put("serviceProvider",carrierName);

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        postRequest.setShouldCache(false);

        AppController.getInstance().addToRequestQueue(postRequest);

    }

    private boolean validate(){



        if (TextUtils.isEmpty(editTextname.getText().toString()))
        {
            editTextname.setError("Oops! Buyer Name blank");
            editTextname.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(editmobile.getText().toString()))
        {
            editmobile.setError("Oops! Mobile blank");
            editmobile.requestFocus();
            return false;
        }

        else if (editmobile.getText().toString().length()!=10){
            editmobile.setError("Oops! Enter Valid Mobile No.");
            return false;
        }

        else if (TextUtils.isEmpty(editPassword.getText().toString()))
        {
            editPassword.setError("Oops! Password blank");
            editPassword.requestFocus();
            return false;
        }
//        else if (TextUtils.isEmpty(editTextdob.getText().toString()))
//        {
//            editTextdob.setError("Oops! Date of Birth blank");
//            editTextdob.requestFocus();
//            return false;
//        }

        else if (!editPassword.getText().toString().equals(editPasswordCon.getText().toString())){
            Toast.makeText(getApplicationContext(), "Both Password should be match", Toast.LENGTH_SHORT).show();
            return false;
        }






        return true;

    }




}
