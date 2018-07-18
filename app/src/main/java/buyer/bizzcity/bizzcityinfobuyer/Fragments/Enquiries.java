package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Enquiries extends Fragment {


    public Enquiries() {
        // Required empty public constructor
    }
    Dialog dialog;
    List<HashMap<String,String>> AllProducts ;
    GridView expListView;
    JSONObject jsonObject1;
    ImageView imageNoListing;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_enquiries, container, false);

        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);
        imageNoListing = (ImageView) view.findViewById(R.id.imageNoListing);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.myQuery+"?mobile="+ MyPrefrences.getMobile(getActivity()), null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d("Respose123", response.toString());
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


                            if (jsonObject.optString("want_more").equalsIgnoreCase("0")) {

                                JSONArray jsonArray1=jsonObject.getJSONArray("rating");
                                for (int j=0;j<jsonArray1.length();j++){
                                    jsonObject1=jsonArray1.getJSONObject(j);
                                    Log.d("fdsgvfdh",jsonObject1.optString("ratingUser"));
                                    Log.d("fdsgvfdh",jsonObject1.optString("rating"));
                                }


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
                                hashMap.put("total_member_use", jsonObject.optString("total_member_use"));


                                JSONObject jsonObject2=jsonObject.getJSONObject("companyDetails");
                                hashMap.put("companyDetails", jsonObject2.optString("company_name"));

                                hashMap.put("totlauser",jsonObject1.optString("ratingUser"));
                                hashMap.put("rating",jsonObject1.optString("rating"));


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

//                        Toast.makeText(getActivity(), "please login", Toast.LENGTH_SHORT).show();
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
                    Log.d("fdsgsdfgdfgdfg",e.getMessage());
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
        TextView Cname,date,requirement,subcatName,location,total_member_use;
        MaterialRatingBar rating;
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
        public View getView(int position, View convertView, ViewGroup parent) {


            convertView=inflater.inflate(R.layout.list_my_enquiry,parent,false);

            Cname=convertView.findViewById(R.id.heading);
            date=convertView.findViewById(R.id.date);
            requirement=convertView.findViewById(R.id.message);
            subcatName=convertView.findViewById(R.id.cattext);
            location=convertView.findViewById(R.id.loctext);
            rating=convertView.findViewById(R.id.rating);
            total_member_use=convertView.findViewById(R.id.total_member_use);

            Cname.setText(AllProducts.get(position).get("companyDetails"));
            date.setText(AllProducts.get(position).get("posted_date"));
            requirement.setText(AllProducts.get(position).get("requirement"));
            subcatName.setText(AllProducts.get(position).get("subCatDetails"));
            location.setText(AllProducts.get(position).get("city"));


            if (!AllProducts.get(position).get("totlauser").equals("0")) {
                total_member_use.setText("( "+AllProducts.get(position).get("totlauser")+" Rating)");
                rating.setRating(Float.parseFloat(AllProducts.get(position).get("rating")));
            }
            else{
                total_member_use.setText("No Reviews");
            }



//            final Typeface tvFont = Typeface.createFromAsset(getActivity().getAssets(), "comicz.ttf");
//            title.setTypeface(tvFont);


            return convertView;
        }
    }



}
