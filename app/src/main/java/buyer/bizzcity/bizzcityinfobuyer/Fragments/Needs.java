package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
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
import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class Needs extends Fragment {


    public Needs() {
        // Required empty public constructor
    }
    Dialog dialog;
    List<HashMap<String,String>> AllProducts ;
    List<HashMap<String,String>> AllProducts2 ;
    GridView expListView;
    ImageView imageNoListing;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_needs, container, false);

        AllProducts = new ArrayList<>();
        AllProducts2 = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);
        imageNoListing = (ImageView) view.findViewById(R.id.imageNoListing);


        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

       // Log.d("dsfsdfsdfsdgs",MyPrefrences.getMobile(getActivity()));
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.myQuery+"?mobile="+ MyPrefrences.getMobile(getActivity()), null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d("Respose", response.toString());
                Util.cancelPgDialog(dialog);
                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");
                    HashMap<String,String> hashMap = null;
                    if (response.getString("status").equalsIgnoreCase("success")){

                        expListView.setVisibility(View.VISIBLE);
                        imageNoListing.setVisibility(View.GONE);


                        JSONArray jsonArray=response.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            if (jsonObject.optString("want_more").equalsIgnoreCase("1")) {
                                hashMap = new HashMap<>();
                                hashMap.put("id", jsonObject.optString("id"));
                                hashMap.put("subcat_id", jsonObject.optString("subcat_id"));
                                hashMap.put("uname", jsonObject.optString("uname"));
                                hashMap.put("city", jsonObject.optString("city"));
                                hashMap.put("email", jsonObject.optString("email"));
                                hashMap.put("requirement", jsonObject.optString("requirement"));
                                hashMap.put("want_more", jsonObject.optString("want_more"));
                                hashMap.put("posted_date", jsonObject.optString("posted_date"));
                                hashMap.put("subCatDetails", jsonObject.optString("subCatDetails"));
                                hashMap.put("current_status", jsonObject.optString("current_status"));

                                hashMap.put("purchasedCompany",jsonObject.optString("purchasedCompany"));


                                //Log.d("fgfdgdfgdfgdfgdfg",jsonObject.optString("purchasedCompany"));

                                if (!jsonObject.optString("purchasedCompany").equalsIgnoreCase("1")){

                                   // hashMap.put("purchasedCompany", jsonObject.optString("purchasedCompany"));

                                        //HashMap<String,String> hashMap2 = new HashMap<>();
                                        JSONObject jsonObject1=jsonObject.getJSONObject("purchasedCompany");

                                        hashMap.put("company1",jsonObject1.optString("company1"));
                                        hashMap.put("company2",jsonObject1.optString("company2"));
                                        hashMap.put("company3",jsonObject1.optString("company3"));
                                        hashMap.put("company4",jsonObject1.optString("company4"));

                                    //AllProducts2.add(hashMap2);
                                }
                                else if (jsonObject.optString("purchasedCompany").equalsIgnoreCase("1")){
                                    hashMap.put("purchasedCompany",jsonObject.optString("purchasedCompany"));

                                }

                                Adapter adapter = new Adapter();
                                expListView.setAdapter(adapter);
                                AllProducts.add(hashMap);
                            }
                            else{
                                expListView.setVisibility(View.GONE);
                                imageNoListing.setVisibility(View.VISIBLE);
                            }

                        }
                        //  AllEvents.add(hashMap);
                    }
                    else if (response.getString("status").equalsIgnoreCase("failure")){
                        expListView.setVisibility(View.GONE);
                        imageNoListing.setVisibility(View.VISIBLE);

                       // Toast.makeText(getActivity(), "login", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new LoginNow();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = manager.beginTransaction();
                        ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
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


//        HashMap<String,String> map=new HashMap<>();
//        for (int i=0;i<20;i++) {
//            map.put("name", "Name");
//            Adapter adapter=new Adapter();
//            expListView.setAdapter(adapter);
//            AllProducts.add(map);
//        }

        return view;
    }


    class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        TextView title,active;
        TextView reqirement,date,subcatname,location,close,bulding1,bulding2,bulding3,bulding4,nbmatch;
        ImageView catimage;
        ImageView imgb1,imgb2,imgb3,imgb4;
        Adapter() {
            inflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return AllProducts.size();
        }

        @Override
        public Object getItem(int position) {
            return AllProducts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            convertView=inflater.inflate(R.layout.list_my_needs,parent,false);


            reqirement=convertView.findViewById(R.id.needmsg);
            date=convertView.findViewById(R.id.date);
            subcatname=convertView.findViewById(R.id.cattext);
            location=convertView.findViewById(R.id.loctext);
            close=convertView.findViewById(R.id.close);

            bulding1=convertView.findViewById(R.id.bulding1);
            bulding2=convertView.findViewById(R.id.bulding2);
            bulding3=convertView.findViewById(R.id.bulding3);
            bulding4=convertView.findViewById(R.id.bulding4);
            nbmatch=convertView.findViewById(R.id.nbmatch);
            catimage=convertView.findViewById(R.id.catimage);

            imgb1=convertView.findViewById(R.id.imgb1);
            imgb2=convertView.findViewById(R.id.imgb2);
            imgb3=convertView.findViewById(R.id.imgb3);
            imgb4=convertView.findViewById(R.id.imgb4);

            active=convertView.findViewById(R.id.active);

            reqirement.setText(AllProducts.get(position).get("requirement"));
            date.setText(AllProducts.get(position).get("posted_date"));

            if (AllProducts.get(position).get("subCatDetails").equalsIgnoreCase("")){
                catimage.setVisibility(View.GONE);
                subcatname.setVisibility(View.GONE);
            }
            else{
                catimage.setVisibility(View.VISIBLE);
                subcatname.setVisibility(View.VISIBLE);
                subcatname.setText(AllProducts.get(position).get("subCatDetails"));
            }


            location.setText(AllProducts.get(position).get("city"));

            if (!AllProducts.get(position).get("purchasedCompany").equalsIgnoreCase("1")){

                Log.d("fdsdfgsddfggfggfgdf",AllProducts.get(position).get("company1"));
                nbmatch.setVisibility(View.VISIBLE);
                bulding1.setText(AllProducts.get(position).get("company1"));
                bulding2.setText(AllProducts.get(position).get("company2"));
                bulding3.setText(AllProducts.get(position).get("company3"));
                bulding4.setText(AllProducts.get(position).get("company4"));
            }


            else if (AllProducts.get(position).get("purchasedCompany").equalsIgnoreCase("1")){

                Log.d("fdsdfgsddfggfggfgdf","else");
                nbmatch.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(bulding1.getText().toString())){
                bulding1.setVisibility(View.VISIBLE);
                imgb1.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(bulding2.getText().toString())){
                bulding2.setVisibility(View.VISIBLE);
                imgb2.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(bulding3.getText().toString())){
                bulding3.setVisibility(View.VISIBLE);
                imgb3.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(bulding4.getText().toString())){
                bulding4.setVisibility(View.VISIBLE);
                imgb4.setVisibility(View.VISIBLE);
            }


//            if (!AllProducts.get(position).get("purchasedCompany").equalsIgnoreCase("1")){
//
//                bulding1.setText(AllProducts.get(position).get("company_name"));
//                bulding2.setText(AllProducts.get(position).get("company_name"));
//            }


            if (AllProducts.get(position).get("current_status").equalsIgnoreCase("Closed")){

                close.setText("Closed");
                active.setText("Closed");
            }
            else{
                close.setText("Close ?");
                active.setText("Active");
            }

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("fgfdgdfgdfgdfhd",AllProducts.get(position).get("id"));

                    StringRequest postRequest = new StringRequest(Request.Method.POST, Api.stopRequirement,
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

                                            close.setText("Closed");
                                            active.setText("Closed");

                                            Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                                    Util.cancelPgDialog(dialog);
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("requirementId", AllProducts.get(position).get("id"));
                            params.put("sellerComment", "abc");

                            return params;
                        }
                    };
                    postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    postRequest.setShouldCache(false);

                    AppController.getInstance().addToRequestQueue(postRequest);

                }
            });
//
//            final Typeface tvFont = Typeface.createFromAsset(getActivity().getAssets(), "comicz.ttf");
//            title.setTypeface(tvFont);


            return convertView;
        }
    }


}
