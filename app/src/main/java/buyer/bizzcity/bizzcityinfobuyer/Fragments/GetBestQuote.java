package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;

import static android.text.Html.fromHtml;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetBestQuote extends Fragment {


    public GetBestQuote() {
        // Required empty public constructor
    }

    TextView tv1;
    Button getQuotre;
    EditText otherLoc,requirement,mobile,nameQ;
    Spinner spiner;
    Dialog dialog;
    Boolean falgArea=false;
    LinearLayout linerOther,linerCat;
    List<String> location = new ArrayList<String>();
    List<HashMap<String, String>> DataLoc;
    ArrayAdapter aa,subcat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_get_best_quote, container, false);

        tv1=view.findViewById(R.id.tv1);


        spiner=view.findViewById(R.id.spiner);
        getQuotre=view.findViewById(R.id.getQuotre);
        nameQ=view.findViewById(R.id.nameQ);
        mobile=view.findViewById(R.id.mobile);
        requirement=view.findViewById(R.id.requirement);
        otherLoc=view.findViewById(R.id.otherLoc);
        linerOther=view.findViewById(R.id.linerOther);

        mobile.setText(MyPrefrences.getMobile(getActivity()));
        nameQ.setText(MyPrefrences.getUSENAME(getActivity()).toUpperCase());


        if (MyPrefrences.getUserLogin(getActivity())==true){
            mobile.setEnabled(false);
            nameQ.setEnabled(false);
        }
        else if (MyPrefrences.getUserLogin(getActivity())==false){
            mobile.setEnabled(true);
            nameQ.setEnabled(true);
        }



        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        DataLoc=new ArrayList<>();


        Log.d("fdsgvdfgdfhd3",getArguments().getString("title"));
        Log.d("fdsgvdfgdfhd4",getArguments().getString("id"));
        tv1.setText(getArguments().getString("title"));

        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spiner.getSelectedItem().toString().equalsIgnoreCase("Other Area")) {
                    linerOther.setVisibility(View.VISIBLE);
                    falgArea=true;
                }
                else{
                    linerOther.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        getQuotre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!spiner.getSelectedItem().toString().equalsIgnoreCase("Select Area")){
                    if(checkValidation()) {
                        sendQuotation();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Please select any area.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.location+"?cityId="+ MyPrefrences.getCityID(getActivity()), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Respose", response.toString());

                Util.cancelPgDialog(dialog);
                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");

                    if (response.getString("status").equalsIgnoreCase("success")){

                        location.clear();
                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("category", "");
                        DataLoc.add(map2);
                        location.add("Select Area");

                        JSONArray jsonArray=response.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            HashMap<String,String> map=new HashMap();
                            map.put("id",jsonObject.optString("id"));
                            map.put("state_id",jsonObject.optString("state_id"));
                            map.put("city_id",jsonObject.optString("city_id"));
                            map.put("location",jsonObject.optString("location"));

                            location.add(jsonObject.optString("location"));

                            DataLoc.add(map);

                            aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,location);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spiner.setAdapter(aa);

                        }
                        HashMap<String, String> map3 = new HashMap<>();
                        map3.put("category", "");
                        DataLoc.add(map3);
                        location.add("Other Area");

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Util.cancelPgDialog(dialog);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Respose", "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Error! Please Connect to the internet", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                Util.cancelPgDialog(dialog);

            }
        });

        // Adding request to request queue
        jsonObjReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(jsonObjReq);

        return view;

    }
    public static Fragment NewInstance(String typeforListing,String title,String searchSt, String id) {
        Bundle args = new Bundle();
        args.putString("fragmentKey", typeforListing);
        args.putString("title", title);
        args.putString("searc",searchSt);
        args.putString("id",id);

        GetBestQuote fragment = new GetBestQuote();
        fragment.setArguments(args);

        return fragment;
    }

    private boolean checkValidation() {
        if (TextUtils.isEmpty(nameQ.getText().toString()))
        {
            nameQ.setError("Oops! Name blank");
            nameQ.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(mobile.getText().toString()))
        {
            mobile.setError("Oops! Mobile blank");
            mobile.requestFocus();
            return false;
        }

        else if (mobile.getText().toString().length()!=10){
            mobile.setError("Oops! Enter Valid Mobile No.");
            return false;
        }

        else if (TextUtils.isEmpty(requirement.getText().toString()))
        {
            requirement.setError("Oops! Requirement blank");
            requirement.requestFocus();
            return false;
        }




        return true;
    }

    private void sendQuotation() {
        Util.showPgDialog(dialog);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Api.categoryQuote,
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

                                errorDialog("Your Quotation successfully posted");

//                                Toast.makeText(getActivity(), "Your Quotation successfully posted", Toast.LENGTH_SHORT).show();
//                                Intent intent=new Intent(getActivity(),HomeAct.class);
//                                startActivity(intent);
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
                        Log.d("fsdgsdgdfg",error.toString());
                        Util.cancelPgDialog(dialog);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();


                params.put("subCatId", getArguments().getString("id"));
                params.put("fullName", nameQ.getText().toString());
                params.put("mobile", mobile.getText().toString());
                params.put("requirement", requirement.getText().toString());
                params.put("email", "abc");

                if (falgArea==true){
                    params.put("city", otherLoc.getText().toString());
                }
                else if (falgArea==false) {
                    params.put("city", spiner.getSelectedItem().toString());
                }

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
