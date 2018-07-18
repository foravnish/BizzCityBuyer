package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import buyer.bizzcity.bizzcityinfobuyer.Activites.HomeAct;
import buyer.bizzcity.bizzcityinfobuyer.Activites.Login;
import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static android.text.Html.fromHtml;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListingDetails extends Fragment {
//

    public ListingDetails() {
        // Required empty public constructor
    }
    TextView phone,address,name,comName,review,totlareview,meassage,location,landline,editList,btnGetQuote;
    TextView closingday,timing,min_distance,min_order,textPmt,isTiming;
    Button call;
    List<String> subCat =new ArrayList<>();
    MaterialRatingBar rating;
    GridView listview;
    ImageView imgFav,stars;
    List<HashMap<String,String>> list=new ArrayList<>();
    Boolean flag=false;
    Dialog dialog,dialog4;
    LinearLayout servicesLay;
    NetworkImageView imageView;
    String val="1";
    ViewPager viewPager2;
   // CustomPagerAdapter2 mCustomPagerAdapter2;
    List<HashMap<String,String>> AllBaner;
    ListView  lvExp;
    List<HashMap<String,String>> AllProducts ;
    List<HashMap<String,String>> AllProducts2 ;
    List<HashMap<String,String>> AllProducts3 ;

    RecyclerView mRecyclerView,mRecyclerView3;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManager2;
    RecyclerView.Adapter mAdapter;
    RecyclerView.Adapter mAdapter3;
    RelativeLayout relativeLayout1;
    LinearLayout closingDay,modePaymnt,minOrder,linerMode;
    TextView reviewShow;
    LinearLayout linerlay;
    TextView timeTv1,timing2;
    ImageView shareDetail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_listing_details, container, false);

        getActivity().setTitle(getArguments().getString("company_name"));
        comName=view.findViewById(R.id.comName);
        name=view.findViewById(R.id.name);
        address=view.findViewById(R.id.address);
        phone=view.findViewById(R.id.phone);
        btnGetQuote=view.findViewById(R.id.btnGetQuote);
        review=view.findViewById(R.id.review);
        totlareview=view.findViewById(R.id.totlareview);
        rating=view.findViewById(R.id.rating);
        meassage=view.findViewById(R.id.meassage);
        listview=view.findViewById(R.id.listview);
        call=view.findViewById(R.id.call);
        imgFav=view.findViewById(R.id.imgFav);
        location=view.findViewById(R.id.location);
        landline=view.findViewById(R.id.landline);
        servicesLay=view.findViewById(R.id.servicesLay);
        imageView=view.findViewById(R.id.imageView);
        editList=view.findViewById(R.id.editList);
        lvExp=view.findViewById(R.id.lvExp);
        relativeLayout1=view.findViewById(R.id.relativeLayout1);

        closingDay=view.findViewById(R.id.closingDay);
        modePaymnt=view.findViewById(R.id.modePaymnt);
        minOrder=view.findViewById(R.id.minOrder);
        linerMode=view.findViewById(R.id.linerMode);
        reviewShow=view.findViewById(R.id.reviewShow);
        linerlay=view.findViewById(R.id.linerlay);
        shareDetail=view.findViewById(R.id.shareDetail);


        closingday=(TextView) view.findViewById(R.id.closingday);
        timing=(TextView) view.findViewById(R.id.timing);
        timing2=(TextView) view.findViewById(R.id.timing2);
        timeTv1=(TextView) view.findViewById(R.id.timeTv1);
        min_distance=(TextView) view.findViewById(R.id.min_distance);
        min_order=(TextView) view.findViewById(R.id.min_order);
        //textPmt=(TextView) view.findViewById(R.id.textPmt);
      //  isTiming=(TextView) view.findViewById(R.id.isTiming);

        viewPager2=(ViewPager) view.findViewById(R.id.slider2);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageView.setImageUrl(getArguments().getString("companyLogo"),imageLoader);
//        imageView.setImageUrl("http://bizzcityinfo.com/upload_images/businessImages/newtiny/1528199268IMG_20180514_132639.jpg",imageLoader);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view2);
        mRecyclerView3 = (RecyclerView) view.findViewById(R.id.recycler_view3);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView3.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
       // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView3.setLayoutManager(mLayoutManager2);

        if (getArguments().getString("closing_days").equalsIgnoreCase("")){
            closingday.setText("All Days open");
        }
        else {
            closingday.setText(getArguments().getString("closing_days"));
        }
        timing.setText(""+getArguments().getString("opening_time")+" - "+getArguments().getString("closing_time"));
        timing2.setText(""+getArguments().getString("opening_time2")+" - "+getArguments().getString("closing_time2"));
        if (getArguments().getString("opening_time2").equalsIgnoreCase("")){
            timing2.setVisibility(View.GONE);
            timeTv1.setVisibility(View.GONE);
        }
        else{
            timing2.setVisibility(View.VISIBLE);
            timeTv1.setVisibility(View.VISIBLE);
        }
        min_distance.setText("Max Distance - "+getArguments().getString("min_order_qty")+" km");
        min_order.setText("Min Price ₹ "+getArguments().getString("min_order_amnt"));
        //textPmt.setText(getArguments().getString("payment_mode").replace(",","\n"));

        if (getArguments().getString("closing_time").equalsIgnoreCase("")){
            closingDay.setVisibility(View.GONE);
        }
        else{
            closingDay.setVisibility(View.VISIBLE);
        }

        if (getArguments().getString("min_order_amnt").equalsIgnoreCase("")){
            minOrder.setVisibility(View.GONE);
            linerMode.setVisibility(View.GONE);

        }
        else{
            minOrder.setVisibility(View.VISIBLE);
            linerMode.setVisibility(View.VISIBLE);
        }

        Log.d("sdfsdfsdfsfs",getArguments().getString("status"));

        if (getArguments().getString("status").equalsIgnoreCase("0")){
            modePaymnt.setVisibility(View.GONE);
        }
        else{
            modePaymnt.setVisibility(View.VISIBLE);
        }


        if (getArguments().getString("c1_mobile1").equalsIgnoreCase("")){
            phone.setVisibility(View.GONE);
        }
        else{
            phone.setVisibility(View.VISIBLE);
        }


        Log.d("sdfsdfsdfsfgs",getArguments().getString("std_code"));
        if (getArguments().getString("std_code").equalsIgnoreCase("-")){
            landline.setVisibility(View.GONE);
        }
        else{
            landline.setVisibility(View.VISIBLE);
        }


        shareDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
//                String shareBody =comName.getText().toString()+ " "+phone.getText().toString();
                String shareBody ="Hey, This BCI service agent provides awesome Services. Contact him/her Now: "+comName.getText().toString()+ " "+phone.getText().toString()+", Download BizzCityInfo app: https://goo.gl/MgMyfW.";

                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Details");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });

        AllBaner=new ArrayList<>();
        AllProducts = new ArrayList<>();
        AllProducts2 = new ArrayList<>();
        AllProducts3 = new ArrayList<>();
       // new GalleryApi(getActivity(), MyPrefrences.getUserID(getActivity())).execute();

        if (MyPrefrences.getUserLogin(getActivity())!=true) {

            Drawable img = getContext().getResources().getDrawable( R.drawable.lock );
            img.setBounds( 0, 0, 30, 30 );
            call.setCompoundDrawables( img, null, null, null );
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.ratingByCompany+"?companyId="+ getArguments().getString("id"), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("ResposeReview", response.toString());

                Util.cancelPgDialog(dialog);
                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");

                    if (response.getString("status").equalsIgnoreCase("success")){
                        reviewShow.setVisibility(View.VISIBLE);
                        lvExp.setVisibility(View.VISIBLE);


                        JSONArray jsonArray=response.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);

//                            totalReviwe.setText("Total Reviews ("+jsonArray.length()+")");

                            //   JSONObject jsonObject2=jsonObject.getJSONObject("comapnyDetails");

                            HashMap map=new HashMap();

                            map.put("id",jsonObject.optString("id"));
                            map.put("mobile",jsonObject.optString("mobile"));
                            map.put("rating_score",jsonObject.optString("rating_score"));
                            map.put("rating_date",jsonObject.optString("rating_date"));
                            map.put("review",jsonObject.optString("review"));
                            map.put("uname",jsonObject.optString("uname"));
                            //    map.put("company_name",jsonObject2.optString("company_name"));
//                            map.put("c1_fname",jsonObject2.optString("c1_fname")+" "+jsonObject2.optString("c1_mname")+" "+jsonObject2.optString("c1_lname"));
//                            map.put("c1_email",jsonObject2.optString("c1_email"));
//                            map.put("c1_mobile1",jsonObject2.optString("c1_mobile1"));
//                            map.put("keywords",jsonObject2.optString("keywords"));
//                            map.put("new_keywords",jsonObject2.optString("new_keywords"));


                            Adapter2 adapter=new Adapter2();
                            lvExp.setAdapter(adapter);
                            AllProducts.add(map);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else{
                        reviewShow.setVisibility(View.GONE);
                        lvExp.setVisibility(View.GONE);

                       // Toast.makeText(getActivity(), "No Reviews in this Company...", Toast.LENGTH_SHORT).show();
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




        // TODO P Mode
        try {
            Log.d("sdfsdfsdfsgsdgsfgdfgdd",getArguments().getString("jsonArray2"));


            JSONArray jsonArray=new JSONArray(getArguments().getString("jsonArray2"));
            AllProducts3.clear();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Log.d("sdfsdfgsdgdfgd", String.valueOf(jsonArray.length()));


                HashMap<String,String> map=new HashMap<>();
                map.put("id", jsonObject.optString("id"));
                map.put("image", jsonObject.optString("image"));
                map.put("title", jsonObject.optString("title"));

                mAdapter3 = new HLVAdapter2(getActivity());

                mRecyclerView3.setAdapter(mAdapter3);
                mAdapter3.notifyDataSetChanged();
                AllProducts3.add(map);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




        ///TODO Gallery

           JsonObjectRequest jsonObjReqOffers = new JsonObjectRequest(Request.Method.GET,
                "http://bizzcityinfo.com/AndroidApi.php?function=getBusinessGalleryImages&companyId="+getArguments().getString("id"), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Respose12123", response.toString());
                Util.cancelPgDialog(dialog);
                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");
                    HashMap<String,String> hashMap = null;
                    if (response.getString("status").equalsIgnoreCase("success")){
                        relativeLayout1.setVisibility(View.VISIBLE);
//                        cardView1.setVisibility(View.VISIBLE);
                        AllProducts2.clear();
                        JSONArray jsonArray=response.getJSONArray("message");
//                        int len=5;
//                        if (jsonArray.length()<=5){
//                            len=jsonArray.length();
//                        }

                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);


                            HashMap<String,String> map=new HashMap<>();
                            map.put("id", jsonObject.optString("id"));
                            map.put("photo", jsonObject.optString("photo"));
                            map.put("created", jsonObject.optString("created"));



//                                map.put("heading", jsonObject.optString("heading"));
//                                map.put("description", jsonObject.optString("description"));
//                                map.put("offer_type", jsonObject.optString("offer_type"));
//                                map.put("discount", jsonObject.optString("discount"));
//                                map.put("actual_price", jsonObject.optString("actual_price"));
//                                map.put("offer_price", jsonObject.optString("offer_price"));
//                                map.put("coupon_code", jsonObject.optString("coupon_code"));
//                                map.put("offer_from", jsonObject.optString("offer_from"));
//                                map.put("offer_to", jsonObject.optString("offer_to"));
//                                map.put("image", jsonObject.optString("image"));
//                                map.put("posted_date", jsonObject.optString("posted_date"));
//
//                             jsonObject2=jsonObject.getJSONObject("comapnyDetails");
//
//                            map.put("new_keywords", jsonObject2.optString("new_keywords"));
//                            map.put("company_name", jsonObject2.optString("company_name"));
//                            map.put("address", jsonObject2.optString("address"));
//                            map.put("c1_mobile1", jsonObject2.optString("c1_mobile1"));
//                            map.put("c1_fname", jsonObject2.optString("c1_fname")+" "+jsonObject2.optString("c1_mname")+" "+jsonObject2.optString("c1_lname"));


                            mAdapter = new HLVAdapter(getActivity());

                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                            AllProducts2.add(map);


//
////                            hashMap = new HashMap<>();
////                            hashMap.put("id",jsonObject.optString("id"));
////                            hashMap.put("cat_id",jsonObject.optString("cat_id"));
////                            hashMap.put("eventName",jsonObject.optString("eventName"));
////                            hashMap.put("photo",jsonObject.optString("photo"));
////
////                            viewPager.setAdapter(mCustomPagerAdapter);
////                            indicator.setViewPager(viewPager);
////                            mCustomPagerAdapter.notifyDataSetChanged();


                            //AllEvents.add(new Const(jsonObject.optString("id"),jsonObject.optString("eventVen"),jsonObject.optString("eventName"),jsonObject.optString("photo"),jsonObject.optString("eventDate"),jsonObject.optString("meta_description"),jsonObject.optString("orgBy")));

//                            viewPager.setAdapter(mCustomPagerAdapter);
//                            indicator.setViewPager(viewPager);
//                            //mCustomPagerAdapter.notifyDataSetChanged();

                        }
                    }
                    else if (response.getString("status").equalsIgnoreCase("failure")){
                        relativeLayout1.setVisibility(View.GONE);
//                        cardView1.setVisibility(View.GONE);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("dfsdfsdfsdfgsd",e.getMessage());
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
        jsonObjReqOffers.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(jsonObjReqOffers);


        //// Todo Gallery  end
        if (getArguments().getString("liking").toString().equals("1")){
            imgFav.setImageResource(R.drawable.fav2);

           // imgFav.getLayoutParams().height = 80;
           // imgFav.getLayoutParams().width = 80;
            flag=true;
        }
        else if (getArguments().getString("liking").toString().equals("0")){
            imgFav.setImageResource(R.drawable.fav1);
           // imgFav.getLayoutParams().height = 80;
           // imgFav.getLayoutParams().width = 80;
            flag=false;
        }


        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MyPrefrences.getUserLogin(getActivity())==true) {
                    if (getArguments().getString("liking").toString().equals("1") || flag == true) {

                        favourateApi("0", getArguments().getString("id"));
                        Log.d("fgdgdfgsdfgsdfg", "true");
                        imgFav.setImageResource(R.drawable.fav1);
                        // imgFav.getLayoutParams().height = 50;
                        //imgFav.getLayoutParams().width = 0;
                        flag = false;
                    } else if (getArguments().getString("liking").toString().equals("0") || flag == false) {

                        favourateApi("1", getArguments().getString("id"));
                        Log.d("fgdgdfgsdfgsdfg", "false");
                        imgFav.setImageResource(R.drawable.fav2);
                        //imgFav.getLayoutParams().height = 50;
                        // imgFav.getLayoutParams().width = 0;
                        flag = true;
                    }
                }
                else{
                    Util.errorDialog(getActivity(),"Please Login First!");
                }
            }
        });

        editList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                popLocation();
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (MyPrefrences.getUserLogin(getActivity())==true) {

                    Fragment fragment = new WriteReview();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", getArguments().getString("id"));
                    bundle.putString("company_name", getArguments().getString("company_name").toString());
                    bundle.putString("address", getArguments().getString("address").toString() + ", " + getArguments().getString("locationName").toString() + ", " + getArguments().getString("pincode").toString());
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();
                    fragment.setArguments(bundle);
                    ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                }
                else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    //Uncomment the below code to Set the message and title from the strings.xml file
                    //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                    //Setting message manually and performing action on button click
                    builder.setMessage("Please Login to post review!")

                            .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    startActivity(new Intent(getActivity(),Login.class));
                                }
                            })
                            .setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("BizzCityInfo");
                    alert.show();


                    //Util.errorDialog(getActivity(),"Please Login First!");
                }
            }
        });

        Log.d("dsfsdfgsdgsdfgdfg",getArguments().getString("latitude"));
        Log.d("dsfsdfgsdgsdfgdfg",getArguments().getString("longitude"));

        meassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("sdfsfsdfsdfgfsdgdfg", String.valueOf(Float.parseFloat(getArguments().getString("latitude"))));
                Log.d("sdfsfsdfsdfgfsdgdfg", String.valueOf(Float.parseFloat(getArguments().getString("longitude"))));

//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Float.parseFloat(getArguments().getString("latitude")),Float.parseFloat(getArguments().getString("longitude")));
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);


                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+getArguments().getString("latitude")+","+getArguments().getString("longitude")+"&daddr="+getArguments().getString("latitude")+","+getArguments().getString("longitude")));
//                Uri.parse("http://maps.google.com/maps?daddr="+getArguments().getString("latitude")+","+getArguments().getString("longitude")));
                startActivity(intent);


//                Fragment fragment=new Gallery();
//                Bundle bundle=new Bundle();
////                bundle.putString("type","details");
//                bundle.putString("id",getArguments().getString("id"));
////                bundle.putString("comName",getArguments().getString("company_name"));
////                bundle.putStringArrayList("array", (ArrayList<String>) subCat );
//                FragmentManager manager=getActivity().getSupportFragmentManager();
//                FragmentTransaction ft=manager.beginTransaction();
//                fragment.setArguments(bundle);
//                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
            }
        });


        linerlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MyPrefrences.getUserLogin(getActivity())==true) {

                    Fragment fragment = new WriteReview();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", getArguments().getString("id"));
                    bundle.putString("company_name", getArguments().getString("company_name").toString());
                    bundle.putString("address", getArguments().getString("address").toString() + ", " + getArguments().getString("locationName").toString() + ", " + getArguments().getString("pincode").toString());
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();
                    fragment.setArguments(bundle);
                    ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                }
                else{
                    Util.errorDialog(getActivity(),"Please Login First!");
                }
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (MyPrefrences.getUserLogin(getActivity())==true) {




                    maintainCallHistory();

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
                            callIntent.setData(Uri.parse("tel:" + getArguments().getString("c1_mobile1")));
                            startActivity(callIntent);
                        }
                        else {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + getArguments().getString("c1_mobile1")));
                            startActivity(callIntent);
                        }
                    }
                    catch (Exception ex)
                    {ex.printStackTrace();
                    }

                }
                else{


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    //Uncomment the below code to Set the message and title from the strings.xml file
                    //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                    //Setting message manually and performing action on button click
                    builder.setMessage("Please Login to activate calling feature. ")
                            .setCancelable(false)
                            .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    startActivity(new Intent(getActivity(),Login.class));
                                }
                            })
                            .setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("BizzCityInfo");
                    alert.show();


                    //Util.errorDialog(getActivity(),"Please Login First!");
                }



            }
        });
        HomeAct.title.setText(getArguments().getString("company_name"));

        Log.d("sdfsdfsdfsgsdgsd",getArguments().getString("id"));
        Log.d("sdfsdfsdfsgsdgsd",getArguments().getString("rating"));
        Log.d("sdfsdfsdfsgsdgsd",getArguments().getString("totlauser"));
        Log.d("sdfsdfsdfsgsdgsd",getArguments().getString("keywords"));




        if (getArguments().getString("keywords").equals("")){
            //Toast.makeText(getActivity(), "blank", Toast.LENGTH_SHORT).show();
            servicesLay.setVisibility(View.GONE);
        }
        else{
            servicesLay.setVisibility(View.VISIBLE);
           // Toast.makeText(getActivity(), "not blank", Toast.LENGTH_SHORT).show();
        }
        comName.setText(getArguments().getString("company_name"));
        landline.setText(getArguments().getString("std_code"));
        String str = getArguments().getString("keywords");

//        List<String> elephantList = Arrays.asList(str.split(","));
//        Log.d("dfsdfsdgfsdgdfgdf",elephantList.toString());


        String[] words=str.split(",");//Separating the word using delimiter Comma and stored in an array
        for(int k=0;k<words.length;k++)
        {
            Log.d("dsfsdfsdgfsdgdsf",words[k]);
//           list.add(words[k]);

            HashMap<String,String>map=new HashMap<>();
            map.put("text",words[k]);
            Adapter adapter=new Adapter();
            listview.setAdapter(adapter);
            list.add(map);
        }
        Log.d("dfsfsdfsdfsdfsdf",list.toString());


        if (getArguments().getString("name").toString().equals(null)){
            name.setVisibility(View.GONE);
        }
        else {
            name.setText(getArguments().getString("name"));
        }
        location.setText(getArguments().getString("locationName"));
        address.setText(getArguments().getString("address") +", "+getArguments().getString("locationName"));

        phone.setText("+91 "+getArguments().getString("c1_mobile1"));
        if (!getArguments().getString("totlauser").equals("0")) {
            rating.setRating(Float.parseFloat(getArguments().getString("rating")));
        }
        if (getArguments().getString("totlauser").equalsIgnoreCase("")){
            totlareview.setText("No Reviews");
        }
        else {
            totlareview.setText(getArguments().getString("totlauser") + " Rating(s)");
        }
        btnGetQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new GetQuotes();
                Bundle bundle=new Bundle();
                bundle.putString("type","details");
                bundle.putString("id",getArguments().getString("id"));
                bundle.putString("comName",getArguments().getString("company_name"));
                bundle.putStringArrayList("array", (ArrayList<String>) subCat );
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
            }
        });


        return view;
    }

    private void maintainCallHistory() {

        StringRequest postRequest = new StringRequest(Request.Method.POST, Api.dataToBuyerCallHistory,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("ResponseCall", response);
                        Util.cancelPgDialog(dialog);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                       // Toast.makeText(getActivity(), "Error! Please connect to the Internet.", Toast.LENGTH_SHORT).show();
                        Util.cancelPgDialog(dialog);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {

                Log.d("from_buyer_id",MyPrefrences.getUserID(getActivity()));
                Log.d("to_seller_id",getArguments().getString("id"));

                Map<String, String>  params = new HashMap<String, String>();
                params.put("from_buyer_id", MyPrefrences.getUserID(getActivity()));
                params.put("to_seller_id", getArguments().getString("id"));

                return params;
            }
        };


        postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        postRequest.setShouldCache(false);

        AppController.getInstance().addToRequestQueue(postRequest);


    }

    public static Fragment NewInstance(String id, String company_name, String address,String c1_mobile1,String rating,String totlauser,String name,String keywords,String locationName,String liking,String std_code,String pincode,String companyLogo,
                                       String payment_mode,String closing_time,String opening_time,String closing_time2,String opening_time2,String min_order_amnt,String min_order_qty,String closing_days,String latitude, String longitude,String status,String jsonArray2) {
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("company_name", company_name);
        args.putString("address",address);
        args.putString("c1_mobile1",c1_mobile1);
        args.putString("rating",rating);
        args.putString("totlauser",totlauser);
        args.putString("name",name);
        args.putString("keywords",keywords);
        args.putString("locationName",locationName);
        args.putString("liking",liking);
        args.putString("std_code",std_code);
        args.putString("pincode",pincode);
        args.putString("companyLogo",companyLogo);

        args.putString("payment_mode",payment_mode);
        args.putString("closing_time",closing_time);
        args.putString("closing_time2",closing_time2);
        args.putString("opening_time",opening_time);
        args.putString("opening_time2",opening_time2);
        args.putString("min_order_amnt",min_order_amnt);
        args.putString("min_order_qty",min_order_qty);
        args.putString("closing_days",closing_days);

        args.putString("latitude",latitude);
        args.putString("longitude",longitude);

        args.putString("status",status);
        args.putString("jsonArray2",jsonArray2);

        ListingDetails fragment = new ListingDetails();
        fragment.setArguments(args);

        return fragment;
    }


    class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        TextView textView1;

        Adapter() {
            inflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView=inflater.inflate(R.layout.list_s_list2,parent,false);
            textView1=convertView.findViewById(R.id.textView1);
            textView1.setText(list.get(position).get("text"));


            textView1.setTextColor(Color.parseColor("#333333"));
            Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "muli_semibold.ttf");
            textView1.setTypeface(face);

            return convertView;
        }
    }

    private void favourateApi(final String stat, final String id ) {

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

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

//                                if (stat.equalsIgnoreCase("0")) {
//                                    imgFav.setBackgroundResource(R.drawable.fav2);
//                                    imgFav.getLayoutParams().height = 50;
//                                    imgFav.getLayoutParams().width = 0;
//                                    flag = false;
//                                }
//
//                                else if (stat.equalsIgnoreCase("1")) {
//                                    imgFav.setBackgroundResource(R.drawable.fav1);
//                                    imgFav.getLayoutParams().height = 50;
//                                    imgFav.getLayoutParams().width = 0;
//                                    flag = true;
//                                }
                                Toast.makeText(getActivity(),jsonObject.getString("message") , Toast.LENGTH_SHORT).show();

                            }
                            else{

                                if (jsonObject.getString("message").equalsIgnoreCase("please enter own id")){
                                    Toast.makeText(getActivity(), "Please Login", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getActivity(),jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                                }
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


    private void popLocation() {


//        dialog1 = new Dialog(getActivity());
//        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog1.setContentView(R.layout.alert_edit_list);
//        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        //TextView text = (TextView) dialog.findViewById(R.id.msg_txv);
//
//
////        Button bubmit2 = (Button) dialog1.findViewById(R.id.bubmit2);
////        TextView text1 = (TextView) dialog1.findViewById(R.id.text1);
////        TextView text2 = (TextView) dialog1.findViewById(R.id.text2);
////        final LinearLayout linearGPS = (LinearLayout) dialog1.findViewById(R.id.linearGPS);
////        final RadioButton nearShow = (RadioButton) dialog1.findViewById(R.id.nearShow);
////        final RadioButton areaShow = (RadioButton) dialog1.findViewById(R.id.areaShow);
//
//
//        dialog.show();


        Button Yes_action,No_action;
        TextView heading;
        dialog4 = new Dialog(getActivity());
        dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog4.setContentView(R.layout.alert_edit_list);
        dialog4.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button bubmit=(Button)dialog4.findViewById(R.id.bubmit);
        final EditText editText=(EditText)dialog4.findViewById(R.id.editText);
        final RadioButton rb1=(RadioButton)dialog4.findViewById(R.id.rb1);
        final RadioButton rb2=(RadioButton)dialog4.findViewById(R.id.rb2);

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val="1";
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val="2";
            }
        });

        bubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().isEmpty()){
                    
                    submitEditing(editText.getText().toString(),val);
                }
                else
                {
                    editText.setError("Oops! Empty Comment");
                }
            }
        });



//        No_action=(Button)dialog4.findViewById(R.id.No_action);
//        heading=(TextView)dialog4.findViewById(R.id.heading);
//
//
//        heading.setText("Are you sure you want to exit?");
//        Yes_action.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //System.exit(0);
//                //getActivity().finish();
//                getActivity().finishAffinity();
//
//            }
//        });
//
//        No_action.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog4.dismiss();
//            }
//        });
        dialog4.show();



    }

    private void submitEditing(final String comment, final String mode) {


        StringRequest postRequest = new StringRequest(Request.Method.POST, Api.profileIncorrectRqst,
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

                                     errorDialog(jsonObject.getString("message"));
                                    dialog4.dismiss();
                            }

                            else{
                                errorDialog(jsonObject.getString("message"));
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
                params.put("companyCode", getArguments().getString("id"));
                params.put("visitorComment", comment);
                params.put("visitorType", mode);

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


            }
        });
        dialog.show();

    }

//    private class GalleryApi extends AsyncTask<String, Void, String> {
//        Context context;
//        String id;
//        public GalleryApi(Context context,String id) {
//            this.context = context;
//            this.id=id;
//            dialog=new Dialog(ProfileAct.this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            //Util.showPgDialog(dialog);
//        }
//
//
//        @Override
//        protected String doInBackground(String... strings) {
//            HashMap<String,String> map=new HashMap<>();
//
//            map.put("function","getBusinessGalleryImages");
//            map.put("companyId", MyPrefrences.getUserID(getApplicationContext()));
//
//            JSONParser jsonParser=new JSONParser();
//            String result =jsonParser.makeHttpRequest(Api.Login,"GET",map);
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            Log.e("response", ": " + s);
//            Util.cancelPgDialog(dialog);
//            try {
//                final JSONObject jsonObject = new JSONObject(s);
//                if (jsonObject != null) {
//                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
//
//                        final JSONArray jsonArray=jsonObject.getJSONArray("message");
//                        for (int i=0;i<jsonArray.length();i++){
//                            final JSONObject jsonObject1=jsonArray.getJSONObject(i);
//
//
//                            HashMap<String,String> map=new HashMap<>();
//                            map.put("id",jsonObject1.optString("id"));
//                            map.put("photo",jsonObject1.optString("photo"));
//
//                            AllBaner.add(map);
//
//
//                            mCustomPagerAdapter2=new CustomPagerAdapter2(getActivity());
//                            viewPager2.setAdapter(mCustomPagerAdapter2);
//                            //indicat2.setViewPager(viewPager2);
//                            mCustomPagerAdapter2.notifyDataSetChanged();
//
////                            pId=jsonObject1.optString("id");
//
//                            //  Picasso.with(context).load(jsonObject1.optString("companyLogo")).into(imageView);
//
//
////                            String[] items2 = insertDate.split(" ");
//
////                            String d1=items1[0];
////                            String m1=items1[1];
////                            String y1=items1[2];
////                            for(String ssss : items2){
////                                Log.d("vfgvfgdfdfdfdfgbd",ssss);
////                            }
////                            String ss =items2[0];
////                            String mm =items2[1];
////                            String hh =items2[2];
//
//
////                            Log.d("vfgvfgdfdfdfdfgbd",d1+" "+ m1+" "+y1+"h> "+hh+"m> "+mm+" s> "+ss);
////                            Log.d("vfgvfgdfdfdfdfgbd",d1+" "+m1);
////
////                            JSONArray jsonArray1 =jsonObject1.getJSONArray("subcategory");
////
//////                            for (int j=0;j<jsonArray1.length();j++){
////
//////                                String s1=jsonObject11.optString("");
////                                Log.d("sfgsdgdfgdfgdfh",jsonArray1.toString());
//////                            }
////                            Log.d("dfsdfgsdgfdsgdfg",jsonObject1.optString("subcategory"));
//                        }
//                    }
//                    else {
//                        Util.errorDialog(context,jsonObject.optString("message"));
//                    }
//                }
//            } catch (JSONException e) {
//                Util.errorDialog(context,"Some Error! Please try again...");
//                e.printStackTrace();
//            }
//        }
//
//    }

//    class CustomPagerAdapter2 extends PagerAdapter {
//
//        Context mContext;
//        LayoutInflater mLayoutInflater;
//
//        public CustomPagerAdapter2(Context context) {
//            mContext = context;
//            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
//        @Override
//        public int getCount() {
//            return AllBaner.size();
//        }
//
//
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == ((RelativeLayout) object);
//        }
//
//
//
//        @Override
//        public Object instantiateItem(ViewGroup container, final int position) {
//            View itemView = mLayoutInflater.inflate(R.layout.page_item, container, false);
//
//            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            Picasso.with(mContext).load(AllBaner.get(position).get("photo")).into(imageView);
//
//
////            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
////
////            imageView.setImageUrl(AllBaner.get(position).getPhoto().toString().replace(" ","%20"),imageLoader);
//
//
////            imageView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    if (AllBaner.get(position).getOrgby().toString().isEmpty() ) {
////
////                        //  Toast.makeText(getActivity(), "blank", Toast.LENGTH_SHORT).show();
////                    }
////                    else{
//////                        Toast.makeText(getActivity(), AllBaner.get(position).getOrgby().toString(), Toast.LENGTH_SHORT).show();
////
////
////                        Intent intent=new Intent(getActivity(), WebViewOpen.class);
////                        intent.putExtra("link",AllBaner.get(position).getOrgby().toString());
////                        startActivity(intent);
////
//////                        Fragment fragment=new WebViewOpen();
//////                        Bundle bundle=new Bundle();
//////                        bundle.putString("link",AllBaner.get(position).getOrgby().toString());
//////                        FragmentManager manager=getActivity().getSupportFragmentManager();
//////                        FragmentTransaction ft=manager.beginTransaction();
//////                        fragment.setArguments(bundle);
//////                        ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
////                    }
////                }
////            });
//
//            container.addView(itemView);
//
//            return itemView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((RelativeLayout) object);
//        }
//    }

    public class Viewholder{
        NetworkImageView iamge;
        TextView comName,c1_fname,c1_email,c1_mobile1,uname,review,user_icon,date_txt;
        LinearLayout liner;
        MaterialRatingBar rtbar2;
    }


    class Adapter2 extends BaseAdapter {

        LayoutInflater inflater;


        Boolean flag=false;
        Adapter2() {
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

            convertView=inflater.inflate(R.layout.list_view_rating,parent,false);

            final Viewholder viewholder=new Viewholder();

            viewholder.comName=convertView.findViewById(R.id.comName);
            viewholder.c1_mobile1=convertView.findViewById(R.id.c1_mobile1);
            viewholder.review=convertView.findViewById(R.id.review);
            viewholder.user_icon=convertView.findViewById(R.id.user_icon);
            viewholder.rtbar2=convertView.findViewById(R.id.rtbar2);
            viewholder.date_txt=convertView.findViewById(R.id.date_txt);

//            viewholder.comName.setText(AllProducts.get(position).get("uname"));
            viewholder.comName.setText(AllProducts.get(position).get("uname").substring(0,1).toUpperCase()+AllProducts.get(position).get("uname").substring(1).toLowerCase());
            viewholder.user_icon.setText(AllProducts.get(position).get("uname"));
            viewholder.c1_mobile1.setText(AllProducts.get(position).get("mobile"));
            viewholder.date_txt.setText(AllProducts.get(position).get("rating_date"));
            String number;
            number="+91-XXXXXXX"+AllProducts.get(position).get("mobile").substring(AllProducts.get(position).get("mobile").length() - 3);
            viewholder.c1_mobile1.setText(number);
            viewholder.review.setText(AllProducts.get(position).get("review"));
            viewholder.rtbar2.setRating(Float.parseFloat(AllProducts.get(position).get("rating_score")));

            return convertView;
        }
    }


    public class HLVAdapter extends RecyclerView.Adapter<HLVAdapter.ViewHolder> {

        ArrayList<String> alName;
        ArrayList<Integer> alImage;
        Context context;

        public HLVAdapter(Context context) {
            super();
            this.context = context;
            this.alName = alName;
            this.alImage = alImage;
        }

        @Override
        public HLVAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_gallery, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(HLVAdapter.ViewHolder viewHolder, int i) {


            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewHolder.imgThumbnail.setImageUrl(AllProducts2.get(i).get("photo"),imageLoader);

//            viewHolder.actPrice.setPaintFlags(viewHolder.actPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            final Typeface tvFont = Typeface.createFromAsset(getActivity().getAssets(), "muli_bold.ttf");

        }

        @Override
        public int getItemCount() {
            return AllProducts2.size();
        }



        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

            public NetworkImageView imgThumbnail;

            //private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);
                imgThumbnail = (NetworkImageView) itemView.findViewById(R.id.s1_15);
                tvSpecies = (TextView) itemView.findViewById(R.id.tv_species);
                actPrice = (TextView) itemView.findViewById(R.id.actPrice);
                desc = (TextView) itemView.findViewById(R.id.desc);
                oldPrice = (TextView) itemView.findViewById(R.id.oldPrice);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            public TextView tvSpecies,act_price,oldPrice,actPrice,desc;

            @Override
            public void onClick(View view) {

//

            }

            @Override
            public boolean onLongClick(View view) {
                return false;
            }

//
        }

    }





// TODO P Mode Adapter


    public class HLVAdapter2 extends RecyclerView.Adapter<HLVAdapter2.ViewHolder2> {

        ArrayList<String> alName;
        ArrayList<Integer> alImage;
        Context context;

        public HLVAdapter2(Context context) {
            super();
            this.context = context;
            this.alName = alName;
            this.alImage = alImage;
        }

        @Override
        public HLVAdapter2.ViewHolder2 onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_p_mode, viewGroup, false);
            ViewHolder2 viewHolder = new ViewHolder2(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder2 holder, int position) {

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            holder.imgThumbnail.setImageUrl(AllProducts3.get(position).get("image"),imageLoader);

//            viewHolder.actPrice.setPaintFlags(viewHolder.actPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            final Typeface tvFont = Typeface.createFromAsset(getActivity().getAssets(), "muli_bold.ttf");
        }


        @Override
        public int getItemCount() {
            return AllProducts3.size();
        }



        public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

            public NetworkImageView imgThumbnail;

            //private ItemClickListener clickListener;

            public ViewHolder2(View itemView) {
                super(itemView);
                imgThumbnail = (NetworkImageView) itemView.findViewById(R.id.s1_15);
                tvSpecies = (TextView) itemView.findViewById(R.id.tv_species);
                actPrice = (TextView) itemView.findViewById(R.id.actPrice);
                desc = (TextView) itemView.findViewById(R.id.desc);
                oldPrice = (TextView) itemView.findViewById(R.id.oldPrice);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            public TextView tvSpecies,act_price,oldPrice,actPrice,desc;

            @Override
            public void onClick(View view) {

//

            }

            @Override
            public boolean onLongClick(View view) {
                return false;
            }

//
        }

    }


}
