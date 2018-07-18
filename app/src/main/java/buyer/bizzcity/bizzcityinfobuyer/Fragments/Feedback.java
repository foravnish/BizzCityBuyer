package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buyer.bizzcity.bizzcityinfobuyer.Activites.HomeAct;
import buyer.bizzcity.bizzcityinfobuyer.Activites.Login;
import buyer.bizzcity.bizzcityinfobuyer.Activites.Splash;
import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;

import static android.text.Html.fromHtml;

/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment {


    public Feedback() {
        // Required empty public constructor
    }

    Spinner spinerCat,state,discountSpinner;
    Dialog dialog;
    List<String> data=new ArrayList<>();
    List<String> stateDataList=new ArrayList<>();
    List<HashMap<String,String>> All;
    LinearLayout linearDiscount,linearPrice;
    TextView dateFrom,dateTill,imageBtn;
    DatePickerDialog datePickerDialog;
    Button bubmit;
    EditText name,mobileNo,emailID,city,desigantion,orgarnisa,message;
    ImageView image1;
    String filepath1,fileName1;
    int check = 0;
    ProgressDialog progress;
    String offerval,disVal;
    String offerId="";
    String firstData="",stateData="";
    ArrayAdapter  aa;
    List<HashMap<String,String>> DataLoc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_feedback2, container, false);

        spinerCat= (Spinner) view.findViewById(R.id.spinerCat);
//        state= (Spinner) view.findViewById(R.id.state);
//        name=(EditText)view.findViewById(R.id.name);
//        mobileNo=(EditText)view.findViewById(R.id.mobileNo);
//        emailID=(EditText)view.findViewById(R.id.emailID);
//        city=(EditText)view.findViewById(R.id.city);
//        desigantion=(EditText)view.findViewById(R.id.desigantion);
//        orgarnisa=(EditText)view.findViewById(R.id.orgarnisa);
        message=(EditText)view.findViewById(R.id.message);
        bubmit=(Button) view.findViewById(R.id.bubmit);

        DataLoc=new ArrayList<>();
        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        data.clear();
        data.add("Suggestion");
        data.add("Complaint");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerCat.setAdapter(dataAdapter);

        HomeAct.title.setText("Feedback");


        if (MyPrefrences.getUserLogin(getActivity())==false){
            // Toast.makeText(getActivity(), "login", Toast.LENGTH_SHORT).show();
            Fragment fragment = new LoginNow();
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        }


        spinerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                firstData=spinerCat.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//               if (!state.getSelectedItem().toString().equalsIgnoreCase("Select State")) {
//                   stateData = DataLoc.get(i).get("id").toString();
//               }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        bubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendDataFeedback();
            }
        });


//        Util.showPgDialog(dialog);
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                Api.state, null, new Response.Listener<JSONObject>() {
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
//                        stateDataList.clear();
//                        HashMap<String, String> map2 = new HashMap<>();
//                        map2.put("category", "");
//                        DataLoc.add(map2);
//                        stateDataList.add("Select State");
//
//                        JSONArray jsonArray=response.getJSONArray("message");
//                        for (int i=0;i<jsonArray.length();i++){
//                            JSONObject jsonObject=jsonArray.getJSONObject(i);
//                            JSONObject jsonObject=jsonArray.getJSONObject(i);
//
//                            HashMap<String,String> map=new HashMap();
//                            map.put("id",jsonObject.optString("id"));
//                            map.put("state",jsonObject.optString("state"));
//
//
//                            stateDataList.add(jsonObject.optString("state"));
//
//                            DataLoc.add(map);
//
//                            aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,stateDataList);
//                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            state.setAdapter(aa);
//
//                        }
//
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getActivity(),
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
//                Toast.makeText(getActivity(),
//                        "Error! Please Connect to the internet", Toast.LENGTH_SHORT).show();
//                // hide the progress dialog
//                Util.cancelPgDialog(dialog);
//
//            }
//        });

        // Adding request to request queue
//        jsonObjReq.setShouldCache(false);
//        AppController.getInstance().addToRequestQueue(jsonObjReq);


        return view;
    }

    private void SendDataFeedback() {

//        Log.d("dfssdfgsdgsdfgsdfg",stateData);
//        Log.d("dfssdfgsdgsdfgsdfg",firstData);
        Util.showPgDialog(dialog);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Api.feedbackSubmit,
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

                                
                                    //Toast.makeText(getActivity(),jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                                    errorDialog(jsonObject.getString("message"));

                                

                            }
                            else{
                                Toast.makeText(getActivity(),jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "Error! Please connect to the Internet.", Toast.LENGTH_SHORT).show();
                        Log.d("dfsdfsdfsdgs",error.toString());
                        Util.cancelPgDialog(dialog);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {


//                Log.d("fggdgdfgdfgdfgd",firstData.toString());
//                Log.d("fullName", name.getText().toString());
//                Log.d("mobile", mobileNo.getText().toString());
//                Log.d("email", emailID.getText().toString());
//                Log.d("city", city.getText().toString());
//                Log.d("department", desigantion.getText().toString());
//                Log.d("orgnization", orgarnisa.getText().toString());
//                Log.d("message", message.getText().toString());
//                Log.d("myId", MyPrefrences.getUserID(getActivity()));
//                Log.d("stateId", stateData.toString());


                Map<String, String>  params = new HashMap<String, String>();
                params.put("messageType", firstData.toString());
                params.put("fullName", MyPrefrences.getUSENAME(getActivity()));
                params.put("mobile", MyPrefrences.getUSENAME(getActivity()));
                params.put("email", MyPrefrences.getUSENAME(getActivity()));
                params.put("city", "city");
                params.put("department","desc" );
                params.put("orgnization", "org");
                params.put("message", message.getText().toString());
                params.put("myId", MyPrefrences.getUserID(getActivity()));
                params.put("stateId", "stateId");

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        postRequest.setShouldCache(false);

        AppController.getInstance().addToRequestQueue(postRequest);

    }

    private void errorDialog(String res) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialogcustom2);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView text = (TextView) dialog.findViewById(R.id.msg_txv);
        text.setText(fromHtml(res));
        Button ok = (Button) dialog.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                Fragment fragment = new Home();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.content_frame, fragment).commit();


            }
        });
        dialog.show();

    }

}
