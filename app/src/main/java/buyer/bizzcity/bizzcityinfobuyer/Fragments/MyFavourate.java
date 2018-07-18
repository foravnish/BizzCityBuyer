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
import com.squareup.picasso.Picasso;

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
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFavourate extends Fragment {


    public MyFavourate() {
        // Required empty public constructor
    }
    List<HashMap<String,String>> AllProducts ;
    GridView expListView;
    HashMap<String,String> map;
    Dialog dialog;
    JSONObject jsonObject1;
    ImageView imageNoListing;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_favourate, container, false);

        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);
        imageNoListing = (ImageView) view.findViewById(R.id.imageNoListing);

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

        HomeAct.title.setText("My Favourites");

//        if (MyPrefrences.getUserLogin(getActivity())==false){
//            // Toast.makeText(getActivity(), "login", Toast.LENGTH_SHORT).show();
//            Fragment fragment = new LoginNow();
//            FragmentManager manager = getActivity().getSupportFragmentManager();
//            FragmentTransaction ft = manager.beginTransaction();
//            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
//        }


        Log.d("dsfsdfsdfsdgfsd",MyPrefrences.getUserID(getActivity()));


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.favoriteList+"?myId="+ MyPrefrences.getUserID(getActivity())+"&cityId="+MyPrefrences.getCityID(getActivity())
                , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.d("Respose", response.toString());

                Util.cancelPgDialog(dialog);
                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");

                    if (response.getString("status").equalsIgnoreCase("success")){

                        expListView.setVisibility(View.VISIBLE);
                        imageNoListing.setVisibility(View.GONE);

                        JSONArray jsonArray0=response.getJSONArray("message");
                        for (int i=0;i<jsonArray0.length();i++) {
                            JSONObject jsonObject0 = jsonArray0.getJSONObject(i);

                            JSONArray jsonArray = jsonObject0.getJSONArray("companyDetails");

                            JSONArray jsonArray1 = jsonObject0.getJSONArray("rating");


                            for (int j = 0; j < jsonArray1.length(); j++) {
                                jsonObject1 = jsonArray1.getJSONObject(j);
                                Log.d("fdsgvfdh", jsonObject1.optString("ratingUser"));
                                Log.d("fdsgvfdh", jsonObject1.optString("rating"));
                            }



                            for (int k = 0; k < jsonArray.length(); k++) {

                                JSONObject jsonObject=jsonArray.getJSONObject(k);


                                JSONArray jsonArray2=jsonObject.getJSONArray("payment_mode");

                                Log.d("sdfsdfsdgsdg",jsonObject.optString("id"));

                                map = new HashMap();
                                map.put("id", jsonObject.optString("id"));
                                map.put("cat_id", jsonObject.optString("cat_id"));
                                map.put("company_name", jsonObject.optString("company_name"));
                                map.put("address", jsonObject.optString("address"));
                                map.put("c1_fname", jsonObject.optString("c1_fname"));
                                map.put("c1_mname", jsonObject.optString("c1_mname"));
                                map.put("c1_lname", jsonObject.optString("c1_lname"));
                                map.put("c1_email", jsonObject.optString("c1_email"));
                                map.put("c1_mobile1", jsonObject.optString("c1_mobile1"));
                                map.put("c1_mobile2", jsonObject.optString("c1_mobile2"));
                                map.put("website", jsonObject.optString("website"));
                                map.put("totlauser", jsonObject1.optString("ratingUser"));
                                map.put("rating", jsonObject1.optString("rating"));
                                map.put("liking", jsonObject.optString("liking"));
                                map.put("locationName",jsonObject.optString("locationName"));
                                map.put("logo",jsonObject.optString("logo"));
                                map.put("companyLogo",jsonObject.optString("companyLogo"));
                                map.put("premium",jsonObject0.optString("premium"));
                                map.put("offer",jsonObject0.optString("offer"));
                                map.put("pincode",jsonObject.optString("pincode"));
                                map.put("keywords",jsonObject.optString("keywords"));
                                map.put("distance",jsonObject.optString("distance"));
                                map.put("std_code",jsonObject.optString("std_code")+"-"+jsonObject.optString("phone"));

                                map.put("latitude",jsonObject.optString("latitude"));
                                map.put("longitude",jsonObject.optString("longitude"));

                                map.put("closing_time",jsonObject.optString("closing_time"));
                                map.put("closing_time2",jsonObject.optString("closing_time2"));
                                map.put("opening_time",jsonObject.optString("opening_time"));
                                map.put("opening_time2",jsonObject.optString("opening_time2"));
                                map.put("min_order_amnt",jsonObject.optString("min_order_amnt"));
                                map.put("min_order_qty",jsonObject.optString("min_order_qty"));
                                map.put("closing_days",jsonObject.optString("closing_days"));


                                if (jsonObject0.has("payment_mode1")){
                                    map.put("payment_mode1",jsonObject0.optString("payment_mode1"));
                                }
                                else{
                                    map.put("payment_mode1","nillValue");
                                }

                                if (jsonObject0.has("payment_mode2")){
                                    map.put("payment_mode2",jsonObject0.optString("payment_mode2"));
                                }

                                else{
                                    map.put("payment_mode2","");
                                }

                                if (jsonObject0.has("payment_mode3")){
                                    map.put("payment_mode3",jsonObject0.optString("payment_mode3"));
                                }
                                else{
                                    map.put("payment_mode3","");
                                }


                                if (jsonObject0.has("payment_mode4")){
                                    map.put("payment_mode4",jsonObject0.optString("payment_mode4"));
                                }
                                else{
                                    map.put("payment_mode4","");
                                }


                                if (jsonObject0.has("payment_mode5")){
                                    map.put("payment_mode5",jsonObject0.optString("payment_mode5"));
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


                                Adapter adapter = new Adapter();
                                expListView.setAdapter(adapter);
                                AllProducts.add(map);
                            }
                        }
                    }
                    else if (response.getString("status").equalsIgnoreCase("failure")){


                        if (response.getString("message").equalsIgnoreCase("Liking Data Not available")){
                            expListView.setVisibility(View.GONE);
                            imageNoListing.setVisibility(View.VISIBLE);
                        }
                        else if (response.getString("message").equalsIgnoreCase("please enter own id")) {
                            Fragment fragment = new LoginNow();
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = manager.beginTransaction();
                            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                        }
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


//        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//
//                Fragment fragment=new ListingDetails();
//                Bundle bundle=new Bundle();
//                bundle.putString("company_name",AllProducts.get(i).get("company_name"));
//                bundle.putString("address",AllProducts.get(i).get("address"));
//                bundle.putString("c1_mobile1",AllProducts.get(i).get("c1_mobile1"));
//                bundle.putString("name",AllProducts.get(i).get("c1_fname")+" "+AllProducts.get(i).get("c1_fname")+" "+AllProducts.get(i).get("c1_lname"));
//                FragmentManager manager=getActivity().getSupportFragmentManager();
//                FragmentTransaction ft=manager.beginTransaction();
//                fragment.setArguments(bundle);
//                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
//            }
//        });



        return view;
    }


    public class Viewholder{
        ImageView imgFav,stars;
        TextView address,name,totlareview,subcatListing,distance;
        LinearLayout liner,linerLayoutOffer;
        MaterialRatingBar rating;
        CardView cardView;
        ImageView callNow1;
        NetworkImageView imgaeView;
        ImageView img1,img2,img3,img4,img5;
        LinearLayout footer_layout;


    }
    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Boolean flag = true;

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


            convertView = inflater.inflate(R.layout.list_lsiting, parent, false);

            final Viewholder viewholder = new Viewholder();

            viewholder.name = convertView.findViewById(R.id.name);
            viewholder.address = convertView.findViewById(R.id.address);
            viewholder.imgFav = convertView.findViewById(R.id.imgFav);
            viewholder.liner = convertView.findViewById(R.id.liner);
            viewholder.totlareview = convertView.findViewById(R.id.totlareview);
            viewholder.rating = convertView.findViewById(R.id.rating);
            viewholder.stars = convertView.findViewById(R.id.stars);
            viewholder.cardView=convertView.findViewById(R.id.cardView);
            viewholder.callNow1=convertView.findViewById(R.id.callNow1);
            viewholder.linerLayoutOffer=convertView.findViewById(R.id.linerLayoutOffer);
            viewholder.imgaeView=convertView.findViewById(R.id.imgaeView);
            viewholder.subcatListing=convertView.findViewById(R.id.subcatListing);
            viewholder.distance=convertView.findViewById(R.id.distance);

            viewholder.img1=convertView.findViewById(R.id.img1);
            viewholder.img2=convertView.findViewById(R.id.img2);
            viewholder.img3=convertView.findViewById(R.id.img3);
            viewholder.img4=convertView.findViewById(R.id.img4);
            viewholder.img5=convertView.findViewById(R.id.img5);

            viewholder.footer_layout=convertView.findViewById(R.id.footer_layout);

            viewholder.imgFav.setBackgroundResource(R.drawable.fav2);
//            viewholder.imgFav.getLayoutParams().height = 50;
//            viewholder.imgFav.getLayoutParams().width = 0;

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewholder.imgaeView.setImageUrl(AllProducts.get(position).get("logo"),imageLoader);


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
                    bundle.putString("liking","0");
                    bundle.putString("std_code",AllProducts.get(position).get("std_code"));
                    bundle.putString("pincode",AllProducts.get(position).get("pincode"));
                    bundle.putString("companyLogo",AllProducts.get(position).get("companyLogo"));

                    bundle.putString("latitude",AllProducts.get(position).get("latitude"));
                    bundle.putString("longitude",AllProducts.get(position).get("longitude"));

                    bundle.putString("status",AllProducts.get(position).get("status"));
                    bundle.putString("jsonArray2",AllProducts.get(position).get("jsonArray2"));

                    bundle.putString("payment_mode",AllProducts.get(position).get("payment_mode"));
                    bundle.putString("closing_time",AllProducts.get(position).get("closing_time"));
                    bundle.putString("closing_time2",AllProducts.get(position).get("closing_time2"));
                    bundle.putString("opening_time",AllProducts.get(position).get("opening_time"));
                    bundle.putString("opening_time2",AllProducts.get(position).get("opening_time2"));
                    bundle.putString("min_order_amnt",AllProducts.get(position).get("min_order_amnt"));
                    bundle.putString("min_order_qty",AllProducts.get(position).get("min_order_qty"));
                    bundle.putString("closing_days",AllProducts.get(position).get("closing_days"));



                    bundle.putString("name",AllProducts.get(position).get("c1_fname")+" "+AllProducts.get(position).get("c1_fname")+" "+AllProducts.get(position).get("c1_lname"));
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=manager.beginTransaction();
                    fragment.setArguments(bundle);
                    ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();

                }
            });

          //viewholder.imgFav.setBackgroundResource(R.drawable.fav2);


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

            //  Log.d("fdgdfgdfhgg", AllProducts.get(position).get("liking"));

//            if (AllProducts.get(position).get("liking").equals("1")){
//                viewholder.imgFav.setBackgroundResource(R.drawable.fav1);
//                viewholder.imgFav.getLayoutParams().height = 50;
//                viewholder.imgFav.getLayoutParams().width = 0;
//
//            }
//            else if (AllProducts.get(position).get("liking").equals("0")){
//                viewholder.imgFav.setBackgroundResource(R.drawable.fav2);
//                viewholder.imgFav.getLayoutParams().height = 50;
//                viewholder.imgFav.getLayoutParams().width = 0;
//
//            }
           // viewholder.callNow1.setVisibility(View.VISIBLE);

            if (AllProducts.get(position).get("premium").equalsIgnoreCase("Yes")){
                viewholder.stars.setVisibility(View.VISIBLE);
                viewholder.cardView.setCardBackgroundColor(Color.parseColor("#FFFDF4BE"));
                //viewholder.callNow1.setVisibility(View.VISIBLE);
            }
            else if (AllProducts.get(position).get("premium").equalsIgnoreCase("No")){
                viewholder.stars.setVisibility(View.GONE);
                viewholder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
               // viewholder.callNow1.setVisibility(View.GONE);
            }


            if (AllProducts.get(position).get("offer").equalsIgnoreCase("Yes")){
                viewholder.linerLayoutOffer.setVisibility(View.VISIBLE);


            }
            else if (AllProducts.get(position).get("offer").equalsIgnoreCase("No")){
                viewholder.linerLayoutOffer.setVisibility(View.GONE);

            }


            viewholder.imgFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if ( flag==true ){
                        favourateApi("0",AllProducts.get(position).get("id"));
                        viewholder.imgFav.setBackgroundResource(R.drawable.fav1);
//                        viewholder.imgFav.getLayoutParams().height = 50;
//                        viewholder.imgFav.getLayoutParams().width = 0;
                        flag=false;


                        Log.d("fgdgdfgsdfgsdfg","true");
                    }
                    else if ( flag==false) {
                        favourateApi("1",AllProducts.get(position).get("id"));
                        viewholder.imgFav.setBackgroundResource(R.drawable.fav2);
//                        viewholder.imgFav.getLayoutParams().height = 50;
//                        viewholder.imgFav.getLayoutParams().width = 0;
                        flag=true;

                        Log.d("fgdgdfgsdfgsdfg","false");
                    }
                }
            });

//            viewholder.name.setText(AllProducts.get(position).get("company_name"));
            viewholder.name.setText(AllProducts.get(position).get("company_name"));
            viewholder.address.setText(AllProducts.get(position).get("address"));
            viewholder.totlareview.setText(AllProducts.get(position).get("totlauser") + " Reviews");
//
            viewholder.subcatListing.setText(AllProducts.get(position).get("keywords"));

            viewholder.distance.setVisibility(View.GONE);

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
            viewholder.subcatListing.setTypeface(face2);
            viewholder.distance.setTypeface(face2);

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

                               // Toast.makeText(getActivity(),jsonObject.getString("message") , Toast.LENGTH_SHORT).show();

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



