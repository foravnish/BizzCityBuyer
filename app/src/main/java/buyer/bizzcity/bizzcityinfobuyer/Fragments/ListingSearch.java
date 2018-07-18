package buyer.bizzcity.bizzcityinfobuyer.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.squareup.picasso.Picasso;

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
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by Andriod Avnish on 14-Apr-18.
 */

public class ListingSearch extends Fragment {


    public ListingSearch() {
        // Required empty public constructor
    }
    List<HashMap<String,String>> AllProducts ;
    GridView expListView;
    HashMap<String,String> map;
    Dialog dialog;
    JSONObject jsonObject1;
    ImageView imageNoListing;
    FloatingActionButton fabButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_listing, container, false);
        Log.d("fdsgvdfgdfhd",getArguments().getString("fragmentKey"));
        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);
        imageNoListing = (ImageView) view.findViewById(R.id.imageNoListing);
        fabButton = (FloatingActionButton) view.findViewById(R.id.fab);
        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

        fabButton.setVisibility(View.GONE);
        HomeAct.title.setText(getArguments().getString("title"));
//        HashMap<String,String> map=new HashMap<>();
//        for (int i=0;i<20;i++) {
//            map.put("name", "Name");
//            Adapter adapter=new Adapter();
//            expListView.setAdapter(adapter);
//            AllProducts.add(map);
//        }

       // Log.d("fdsgvdfgfdghfgdfhd", MyPrefrences.getUserID(getActivity()));


        HomeAct.title.setText(getArguments().getString("fragmentKey"));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.searchCompany+"?keyword="+getArguments().getString("fragmentKey").replace(" ","%20")+"&cityId="+MyPrefrences.getCityID(getActivity()), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Respose123", response.toString());

                Util.cancelPgDialog(dialog);
                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");

                    if (response.getString("status").equalsIgnoreCase("success")){

                        expListView.setVisibility(View.VISIBLE);
                        imageNoListing.setVisibility(View.GONE);

                        JSONArray jsonArray=response.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            JSONArray jsonArray1=jsonObject.getJSONArray("rating");
                            JSONArray jsonArray2=jsonObject.getJSONArray("payment_mode");

                            for (int j=0;j<jsonArray1.length();j++){
                                jsonObject1=jsonArray1.getJSONObject(j);
                                Log.d("fdsgvfdh",jsonObject1.optString("ratingUser"));
                                Log.d("fdsgvfdh",jsonObject1.optString("rating"));

                            }

                            map=new HashMap();
                            map.put("id",jsonObject.optString("id"));
                            map.put("cat_id",jsonObject.optString("cat_id"));
                            map.put("company_name",jsonObject.optString("company_name"));
                            map.put("address",jsonObject.optString("address"));
                            map.put("c1_fname",jsonObject.optString("c1_fname"));
                            map.put("c1_mname",jsonObject.optString("c1_mname"));
                            map.put("c1_lname",jsonObject.optString("c1_lname"));
                            map.put("c1_email",jsonObject.optString("c1_email"));
                            map.put("c1_mobile1",jsonObject.optString("c1_mobile1"));
                            map.put("c1_mobile2",jsonObject.optString("c1_mobile2"));
                            map.put("website",jsonObject.optString("website"));
                            map.put("totlauser",jsonObject1.optString("ratingUser"));
                            map.put("rating",jsonObject1.optString("rating"));
                            map.put("liking",jsonObject.optString("liking"));
                            map.put("locationName",jsonObject.optString("locationName"));
                            map.put("logo",jsonObject.optString("logo"));
                            map.put("companyLogo",jsonObject.optString("companyLogo"));
                            map.put("premium",jsonObject.optString("premium"));
                            map.put("offer",jsonObject.optString("offer"));
                            map.put("pincode",jsonObject.optString("pincode"));
                            map.put("distance",jsonObject.optString("distance"));

                            map.put("latitude",jsonObject.optString("latitude"));
                            map.put("longitude",jsonObject.optString("longitude"));

                            map.put("payment_mode",jsonObject.optString("payment_mode"));
                            map.put("closing_time",jsonObject.optString("closing_time"));
                            map.put("closing_time2",jsonObject.optString("closing_time2"));
                            map.put("opening_time",jsonObject.optString("opening_time"));
                            map.put("opening_time2",jsonObject.optString("opening_time2"));
                            map.put("min_order_amnt",jsonObject.optString("min_order_amnt"));
                            map.put("min_order_qty",jsonObject.optString("min_order_qty"));
                            map.put("closing_days",jsonObject.optString("closing_days"));

                            if (jsonObject.has("payment_mode1")){
                                map.put("payment_mode1",jsonObject.optString("payment_mode1"));
                            }
                            else{
                                map.put("payment_mode1","nillValue");
                            }

                            if (jsonObject.has("payment_mode2")){
                                map.put("payment_mode2",jsonObject.optString("payment_mode2"));
                            }

                            else{
                                map.put("payment_mode2","");
                            }

                            if (jsonObject.has("payment_mode3")){
                                map.put("payment_mode3",jsonObject.optString("payment_mode3"));
                            }
                            else{
                                map.put("payment_mode3","");
                            }


                            if (jsonObject.has("payment_mode4")){
                                map.put("payment_mode4",jsonObject.optString("payment_mode4"));
                            }
                            else{
                                map.put("payment_mode4","");
                            }


                            if (jsonObject.has("payment_mode5")){
                                map.put("payment_mode5",jsonObject.optString("payment_mode5"));
                            }
                            else{
                                map.put("payment_mode5","");
                            }


                            if (jsonArray2.length() > 0) {

                                map.put("status","1");
                                map.put("jsonArray2",jsonArray2.toString());


                            }
                            else{
                                map.put("status","0");
                                map.put("jsonArray2","nil");
                            }


                            map.put("std_code",jsonObject.optString("std_code")+"-"+jsonObject.optString("phone"));
                            map.put("keywords",jsonObject.optString("keywords"));


                            Adapter adapter=new Adapter();
                            expListView.setAdapter(adapter);
                            AllProducts.add(map);
                        }
                    }
                    else{
                        expListView.setVisibility(View.GONE);
                        imageNoListing.setVisibility(View.VISIBLE);
                        //Toast.makeText(getActivity(), "No Record Found...", Toast.LENGTH_SHORT).show();
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



//        StringRequest postRequest = new StringRequest(Request.Method.GET, Api.searchCompany,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d("Response", response);
//                        Util.cancelPgDialog(dialog);
//
//                        try {
//                            // Parsing json object response
//                            // response will be a json object
////                    String name = response.getString("name");
//
//                            JSONObject jsonObject1 =new JSONObject(response);
//                            if (jsonObject1.getString("status").equalsIgnoreCase("success")){
//
//                                expListView.setVisibility(View.VISIBLE);
//                                imageNoListing.setVisibility(View.GONE);
//
//                                JSONArray jsonArray=jsonObject1.getJSONArray("message");
//                                for (int i=0;i<jsonArray.length();i++){
//                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
//
//                                    JSONArray jsonArray1=jsonObject.getJSONArray("rating");
//                                    for (int j=0;j<jsonArray1.length();j++){
//                                        jsonObject1=jsonArray1.getJSONObject(j);
//                                        Log.d("fdsgvfdh",jsonObject1.optString("ratingUser"));
//                                        Log.d("fdsgvfdh",jsonObject1.optString("rating"));
//                                    }
//
//                                    map=new HashMap();
//                                    map.put("id",jsonObject.optString("id"));
//                                    map.put("cat_id",jsonObject.optString("cat_id"));
//                                    map.put("company_name",jsonObject.optString("company_name"));
//                                    map.put("address",jsonObject.optString("address"));
//                                    map.put("c1_fname",jsonObject.optString("c1_fname"));
//                                    map.put("c1_mname",jsonObject.optString("c1_mname"));
//                                    map.put("c1_lname",jsonObject.optString("c1_lname"));
//                                    map.put("c1_email",jsonObject.optString("c1_email"));
//                                    map.put("c1_mobile1",jsonObject.optString("c1_mobile1"));
//                                    map.put("c1_mobile2",jsonObject.optString("c1_mobile2"));
//                                    map.put("website",jsonObject.optString("website"));
//                                    map.put("totlauser",jsonObject1.optString("ratingUser"));
//                                    map.put("rating",jsonObject1.optString("rating"));
//                                    map.put("liking",jsonObject.optString("liking"));
//                                    map.put("locationName",jsonObject.optString("locationName"));
//                                    map.put("logo",jsonObject.optString("logo"));
//                                    map.put("companyLogo",jsonObject.optString("companyLogo"));
//                                    map.put("premium",jsonObject.optString("premium"));
//                                    map.put("offer",jsonObject.optString("offer"));
//                                    map.put("pincode",jsonObject.optString("pincode"));
//                                    map.put("std_code",jsonObject.optString("std_code")+"-"+jsonObject.optString("phone"));
//                                    map.put("new_keywords",jsonObject.optString("new_keywords"));
//
//                                    Adapter adapter=new Adapter();
//                                    expListView.setAdapter(adapter);
//                                    AllProducts.add(map);
//                                }
//                            }
//                            else{
//                                expListView.setVisibility(View.GONE);
//                                imageNoListing.setVisibility(View.VISIBLE);
//                                Toast.makeText(getActivity(), "No Record Found...", Toast.LENGTH_SHORT).show();
//                            }
//
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getActivity(),
//                                    "Error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
//                            Util.cancelPgDialog(dialog);
//                        }
//
//
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams()
//            {
//
//
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("keyword", getArguments().getString("fragmentKey"));
//                params.put("cityId",MyPrefrences.getCityID(getActivity()));
//
//                return params;
//            }
//        };
//        postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        postRequest.setShouldCache(false);
//
//        AppController.getInstance().addToRequestQueue(postRequest);


        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Fragment fragment=new ListingTabDetails();
                Bundle bundle=new Bundle();
                bundle.putString("id",AllProducts.get(i).get("id"));
                bundle.putString("company_name",AllProducts.get(i).get("company_name"));
                bundle.putString("address",AllProducts.get(i).get("address"));
                bundle.putString("c1_mobile1",AllProducts.get(i).get("c1_mobile1"));
                bundle.putString("rating",AllProducts.get(i).get("rating"));
                bundle.putString("totlauser",AllProducts.get(i).get("totlauser"));
                bundle.putString("locationName",AllProducts.get(i).get("locationName"));
                bundle.putString("keywords",AllProducts.get(i).get("keywords"));
                bundle.putString("liking",AllProducts.get(i).get("liking"));
                bundle.putString("std_code",AllProducts.get(i).get("std_code"));
                bundle.putString("pincode",AllProducts.get(i).get("pincode"));
                bundle.putString("companyLogo",AllProducts.get(i).get("companyLogo"));
                bundle.putString("payment_mode",AllProducts.get(i).get("payment_mode"));
                bundle.putString("closing_time",AllProducts.get(i).get("closing_time"));
                bundle.putString("closing_time2",AllProducts.get(i).get("closing_time2"));
                bundle.putString("opening_time",AllProducts.get(i).get("opening_time"));
                bundle.putString("opening_time2",AllProducts.get(i).get("opening_time2"));
                bundle.putString("min_order_amnt",AllProducts.get(i).get("min_order_amnt"));
                bundle.putString("min_order_qty",AllProducts.get(i).get("min_order_qty"));
                bundle.putString("closing_days",AllProducts.get(i).get("closing_days"));
                bundle.putString("latitude",AllProducts.get(i).get("latitude"));
                bundle.putString("longitude",AllProducts.get(i).get("longitude"));

                bundle.putString("payment_mode",AllProducts.get(i).get("payment_mode"));
//                bundle.putString("closing_time",AllProducts.get(i).get("closing_time"));
//                bundle.putString("opening_time",AllProducts.get(i).get("opening_time"));
                bundle.putString("min_order_amnt",AllProducts.get(i).get("min_order_amnt"));
                bundle.putString("min_order_qty",AllProducts.get(i).get("min_order_qty"));
                bundle.putString("closing_days",AllProducts.get(i).get("closing_days"));

                bundle.putString("status",AllProducts.get(i).get("status"));
                bundle.putString("jsonArray2",AllProducts.get(i).get("jsonArray2"));

                bundle.putString("name",AllProducts.get(i).get("c1_fname")+" "+AllProducts.get(i).get("c1_fname")+" "+AllProducts.get(i).get("c1_lname"));
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getArguments().getString("title"));
    }

    public static Fragment NewInstance(String typeforListing, String title, String searchSt) {
        Bundle args = new Bundle();
        args.putString("fragmentKey", typeforListing);
        args.putString("title", title);
        args.putString("searc",searchSt);

        ListingSearch fragment = new ListingSearch();
        fragment.setArguments(args);

        return fragment;
    }

    public class Viewholder{
        ImageView imgFav,stars;
        TextView address,name,totlareview,area,subcatListing,distance;
        LinearLayout liner,linerLayoutOffer;
        MaterialRatingBar rating;
        NetworkImageView imgaeView;
        CardView cardView;
        ImageView callNow1;
      //  ShimmerTextView offersText;
        Shimmer shimmer;
        ImageView img1,img2,img3,img4,img5;

        LinearLayout footer_layout;

    }
    class Adapter extends BaseAdapter {

        LayoutInflater inflater;


        Boolean flag=false;
        Adapter() {
            inflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//            if (inflater == null) {
//                throw new AssertionError("LayoutInflater not found.");
//            }
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


            convertView=inflater.inflate(R.layout.list_lsiting,parent,false);

            final Viewholder viewholder=new Viewholder();

            viewholder.name=convertView.findViewById(R.id.name);
            viewholder.address=convertView.findViewById(R.id.address);
            viewholder.imgFav=convertView.findViewById(R.id.imgFav);
            viewholder.stars=convertView.findViewById(R.id.stars);
            viewholder.liner=convertView.findViewById(R.id.liner);
            viewholder.totlareview=convertView.findViewById(R.id.totlareview);
            viewholder.rating=convertView.findViewById(R.id.rating);
            viewholder.area=convertView.findViewById(R.id.area);
            viewholder.callNow1=convertView.findViewById(R.id.callNow1);
            viewholder.imgaeView=convertView.findViewById(R.id.imgaeView);
            viewholder.linerLayoutOffer=convertView.findViewById(R.id.linerLayoutOffer);
            viewholder.cardView=convertView.findViewById(R.id.cardView);
           // viewholder.offersText=convertView.findViewById(R.id.offersText);
            viewholder.subcatListing=convertView.findViewById(R.id.subcatListing);
            viewholder.distance=convertView.findViewById(R.id.distance);

//            viewholder.imgFav.setBackgroundResource(R.drawable.fav2);
//            viewholder.imgFav.getLayoutParams().height = 50;
//            viewholder.imgFav.getLayoutParams().width = 0;

            viewholder.img1=convertView.findViewById(R.id.img1);
            viewholder.img2=convertView.findViewById(R.id.img2);
            viewholder.img3=convertView.findViewById(R.id.img3);
            viewholder.img4=convertView.findViewById(R.id.img4);
            viewholder.img5=convertView.findViewById(R.id.img5);

            viewholder.footer_layout=convertView.findViewById(R.id.footer_layout);

            viewholder.liner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();

                    Fragment fragment=new ListingTabDetails();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",AllProducts.get(position).get("id"));
                    bundle.putString("company_name",AllProducts.get(position).get("company_name"));
                    bundle.putString("address",AllProducts.get(position).get("address"));
                    bundle.putString("c1_mobile1",AllProducts.get(position).get("c1_mobile1"));
                    bundle.putString("rating",AllProducts.get(position).get("rating"));
                    bundle.putString("totlauser",AllProducts.get(position).get("totlauser"));
                    bundle.putString("locationName",AllProducts.get(position).get("locationName"));
                    bundle.putString("keywords",AllProducts.get(position).get("keywords"));
                    bundle.putString("liking",AllProducts.get(position).get("liking"));
                    bundle.putString("std_code",AllProducts.get(position).get("std_code"));
                    bundle.putString("pincode",AllProducts.get(position).get("pincode"));
                    bundle.putString("companyLogo",AllProducts.get(position).get("companyLogo"));
                    bundle.putString("payment_mode",AllProducts.get(position).get("payment_mode"));
                    bundle.putString("closing_time",AllProducts.get(position).get("closing_time"));
                    bundle.putString("closing_time2",AllProducts.get(position).get("closing_time2"));
                    bundle.putString("opening_time",AllProducts.get(position).get("opening_time"));
                    bundle.putString("opening_time2",AllProducts.get(position).get("opening_time2"));
                    bundle.putString("min_order_amnt",AllProducts.get(position).get("min_order_amnt"));
                    bundle.putString("min_order_qty",AllProducts.get(position).get("min_order_qty"));
                    bundle.putString("closing_days",AllProducts.get(position).get("closing_days"));
                    bundle.putString("latitude",AllProducts.get(position).get("latitude"));
                    bundle.putString("longitude",AllProducts.get(position).get("longitude"));

                    bundle.putString("payment_mode",AllProducts.get(position).get("payment_mode"));
//                    bundle.putString("closing_time",AllProducts.get(position).get("closing_time"));
//                    bundle.putString("opening_time",AllProducts.get(position).get("opening_time"));
                    bundle.putString("min_order_amnt",AllProducts.get(position).get("min_order_amnt"));
                    bundle.putString("min_order_qty",AllProducts.get(position).get("min_order_qty"));
                    bundle.putString("closing_days",AllProducts.get(position).get("closing_days"));

                    bundle.putString("status",AllProducts.get(position).get("status"));
                    bundle.putString("jsonArray2",AllProducts.get(position).get("jsonArray2"));


                    bundle.putString("name",AllProducts.get(position).get("c1_fname")+" "+AllProducts.get(position).get("c1_fname")+" "+AllProducts.get(position).get("c1_lname"));
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=manager.beginTransaction();
                    fragment.setArguments(bundle);
                    ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();

                }
            });

            Log.d("fdgdfgdfhgg",AllProducts.get(position).get("liking"));

            viewholder.distance.setVisibility(View.GONE);
            try {
                if (AllProducts.get(position).get("liking").equals("1")){
                    viewholder.imgFav.setBackgroundResource(R.drawable.fav2);
    //                viewholder.imgFav.getLayoutParams().height = 50;
    //                viewholder.imgFav.getLayoutParams().width = 0;
                    flag=true;
                }
                else if (AllProducts.get(position).get("liking").equals("0")){
                    viewholder.imgFav.setBackgroundResource(R.drawable.fav1);
    //                viewholder.imgFav.getLayoutParams().height = 50;
    //                viewholder.imgFav.getLayoutParams().width = 0;
                    flag=false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                if (AllProducts.get(position).get("premium").equalsIgnoreCase("Yes")){
                    viewholder.stars.setVisibility(View.VISIBLE);
                    viewholder.cardView.setCardBackgroundColor(Color.parseColor("#FFFDF4BE"));
                    viewholder.callNow1.setVisibility(View.VISIBLE);
                }
                else if (AllProducts.get(position).get("premium").equalsIgnoreCase("No")){
                    viewholder.stars.setVisibility(View.GONE);
                    viewholder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
                    viewholder.callNow1.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (AllProducts.get(position).get("offer").equalsIgnoreCase("Yes")){
                    viewholder.linerLayoutOffer.setVisibility(View.VISIBLE);
                }
                else if (AllProducts.get(position).get("offer").equalsIgnoreCase("No")){
                    viewholder.linerLayoutOffer.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            viewholder.callNow1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try
                    {
                        if(Build.VERSION.SDK_INT > 22)
                        {
                            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);
                                return;
                            }
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + AllProducts.get(position).get("c1_mobile1")));
                            startActivity(callIntent);
                        }
                        else {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + AllProducts.get(position).get("c1_mobile1")));
                            startActivity(callIntent);
                        }
                    }
                    catch (Exception ex)
                    {ex.printStackTrace();
                    }

                }
            });

            viewholder.imgFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (AllProducts.get(position).get("liking").equals("1") || flag==true ){
                        viewholder.imgFav.setBackgroundResource(R.drawable.fav1);
//                        viewholder.imgFav.getLayoutParams().height = 50;
//                        viewholder.imgFav.getLayoutParams().width = 0;
                        flag=false;
                        favourateApi("0",AllProducts.get(position).get("id"));
                        Log.d("fgdgdfgsdfgsdfg","true");
                    }
                    else if (AllProducts.get(position).get("liking").equals("0")|| flag==false) {
                        viewholder.imgFav.setBackgroundResource(R.drawable.fav2);
//                        viewholder.imgFav.getLayoutParams().height = 50;
//                        viewholder.imgFav.getLayoutParams().width = 0;
                        flag=true;
                        favourateApi("1",AllProducts.get(position).get("id"));
                        Log.d("fgdgdfgsdfgsdfg","false");
                    }
                }
            });



//            viewholder.name.setText(AllProducts.get(position).get("company_name"));
            viewholder.name.setText(AllProducts.get(position).get("company_name"));
            viewholder.address.setText(AllProducts.get(position).get("address"));
            viewholder.totlareview.setText(AllProducts.get(position).get("totlauser")+" Reviews");
            viewholder.area.setText(AllProducts.get(position).get("locationName"));
            viewholder.subcatListing.setText(AllProducts.get(position).get("keywords"));
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewholder.imgaeView.setImageUrl(AllProducts.get(position).get("logo"),imageLoader);

            if (String.valueOf(HomeAct.latitude).equals("null")){
                viewholder.distance.setText("");
            }
            else{
                String str=AllProducts.get(position).get("distance");
                String strr=str.length() < 4 ? str : str.substring(0, 4);
                String str1=strr+" Km";
                viewholder.distance.setText(str1);
            }


            if (!AllProducts.get(position).get("totlauser").equals("0")) {
                viewholder.rating.setRating(Float.parseFloat(AllProducts.get(position).get("rating")));
            }

            Typeface face=Typeface.createFromAsset(getActivity().getAssets(), "muli_semibold.ttf");
            Typeface face2=Typeface.createFromAsset(getActivity().getAssets(), "muli.ttf");
            viewholder.name.setTypeface(face);
            viewholder.address.setTypeface(face2);
            viewholder.totlareview.setTypeface(face2);
            viewholder.area.setTypeface(face2);
            viewholder.subcatListing.setTypeface(face2);
            viewholder.distance.setTypeface(face2);

            viewholder.shimmer = new Shimmer();
          //  viewholder.shimmer.start(viewholder.offersText);


            try {
                Log.d("dfvdgdfgdfgdfh",AllProducts.get(position).get("payment_mode1"));
                Log.d("dfvdgdfgdfgdfh",AllProducts.get(position).get("payment_mode2"));
                Log.d("dfvdgdfgdfgdfh",AllProducts.get(position).get("payment_mode3"));
                Log.d("dfvdgdfgdfgdfh",AllProducts.get(position).get("payment_mode4"));
                Log.d("dfvdgdfgdfgdfh",AllProducts.get(position).get("payment_mode5"));

//                viewholder.img1

                if (AllProducts.get(position).get("payment_mode1").equalsIgnoreCase("nillValue")){
                    viewholder.footer_layout.setVisibility(View.GONE);
                }

                else{
                    viewholder.footer_layout.setVisibility(View.VISIBLE);
                }

                Picasso.with(getActivity()).load(AllProducts.get(position).get("payment_mode1")).into( viewholder.img1);
                Picasso.with(getActivity()).load(AllProducts.get(position).get("payment_mode2")).into( viewholder.img2);
                Picasso.with(getActivity()).load(AllProducts.get(position).get("payment_mode3")).into( viewholder.img3);
                Picasso.with(getActivity()).load(AllProducts.get(position).get("payment_mode4")).into( viewholder.img4);
                Picasso.with(getActivity()).load(AllProducts.get(position).get("payment_mode5")).into( viewholder.img5);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    private void favourateApi(final String stat, final String id) {
        Util.showPgDialog(dialog);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Api.favorite,
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

                                Toast.makeText(getActivity(),jsonObject.getString("message") , Toast.LENGTH_SHORT).show();

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
                params.put("myId", MyPrefrences.getUserID(getActivity()));
                params.put("postId", id.toString());
                params.put("favoriteStatus",stat.toString());

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        postRequest.setShouldCache(false);

        AppController.getInstance().addToRequestQueue(postRequest);
    }


}
