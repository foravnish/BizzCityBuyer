package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public class EmergencyDetail extends Fragment {


    public EmergencyDetail() {
        // Required empty public constructor
    }

    List<HashMap<String,String>> AllProducts ;
    GridView expListView;
    HashMap<String,String> map;
    Dialog dialog;
    JSONObject jsonObject1;
    FloatingActionButton fabButton;
    String value="";
    List<String> data=new ArrayList<>();
    Button bubmit;
    JSONObject jsonObject;
    ImageView imageNoListing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_emergency_detail, container, false);
        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);
        imageNoListing = (ImageView) view.findViewById(R.id.imageNoListing);

        AllProducts = new ArrayList<>();
//        AdView adView = (AdView)view. findViewById(R.id.search_ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        
        //  HomeAct.linerFilter.setVisibility(View.VISIBLE);

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

        HomeAct.title.setText(getArguments().getString("category"));

        Log.d("sfdsfgdgdfgdh",getArguments().getString("id"));
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, Api.emergency+"?catId="+getArguments().getString("id"), null, new Response.Listener<JSONObject>() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, Api.emergencyCategory+"?catId="+getArguments().getString("id")+"&cityId="+ MyPrefrences.getCityID(getActivity()), null, new Response.Listener<JSONObject>() {


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


                        JSONArray jsonArray=response.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                             jsonObject=jsonArray.getJSONObject(i);

                            map=new HashMap();
                            map.put("id",jsonObject.optString("id"));
                            map.put("service_name",jsonObject.optString("service_name"));
                            map.put("landline_no",jsonObject.optString("landline_no"));
                            map.put("mobile",jsonObject.optString("mobile"));
                            map.put("area",jsonObject.optString("area"));
                            map.put("address",jsonObject.optString("address"));


                            Adapter adapter=new Adapter();
                            expListView.setAdapter(adapter);
                            AllProducts.add(map);
                        }
                    }
                    else {
                        expListView.setVisibility(View.GONE);
                        imageNoListing.setVisibility(View.VISIBLE);
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

        jsonObjReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(jsonObjReq);



        return view;
    }


//    public class Viewholder{
//        NetworkImageView iamge;
//        TextView etxtview;
//        LinearLayout liner;
//        MaterialRatingBar rating;
//    }


    class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        TextView textView2,phone,mobile,address,calltext;
        Boolean flag=false;
        LinearLayout calllayout,layout3;
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


            convertView=inflater.inflate(R.layout.list_emergency_cat_detail,parent,false);


            textView2=convertView.findViewById(R.id.textView2);
            phone=convertView.findViewById(R.id.phone);
            mobile=convertView.findViewById(R.id.mobile);
            address=convertView.findViewById(R.id.address);
            calllayout=convertView.findViewById(R.id.calllayout);
            calltext=convertView.findViewById(R.id.calltext);
            layout3=convertView.findViewById(R.id.layout3);

            textView2.setText(AllProducts.get(position).get("service_name"));


            if (AllProducts.get(position).get("address").equals("")) {
                address.setText(AllProducts.get(position).get("area"));
            }
            else{
                address.setText(AllProducts.get(position).get("address"));
            }

            if (AllProducts.get(position).get("landline_no").equals("")) {
                phone.setText("N/A");
            }
            else{
                phone.setText(AllProducts.get(position).get("landline_no"));
            }



//            if (AllProducts.get(position).get("area").equals("")) {
//                address.setText("N/A");
//            }
//            else{
//                address.setText(AllProducts.get(position).get("area"));
//            }


            if (AllProducts.get(position).get("mobile").equals("")) {
                mobile.setText("N/A");
                layout3.setVisibility(View.GONE);
            }
            else{
                mobile.setText(AllProducts.get(position).get("mobile"));
                layout3.setVisibility(View.VISIBLE);
            }

            textView2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "muli_bold.ttf"));
            phone.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "muli_semibold.ttf"));
            mobile.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "muli_semibold.ttf"));
            address.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "muli_semibold.ttf"));
            calltext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "muli_bold.ttf"));

            calllayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (!AllProducts.get(position).get("landline_no").equals("")) {
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
                                callIntent.setData(Uri.parse("tel:" + AllProducts.get(position).get("landline_no")));
                                startActivity(callIntent);
                            }
                            else {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + AllProducts.get(position).get("landline_no")));
                                startActivity(callIntent);
                            }
                        }
                        catch (Exception ex)
                        {ex.printStackTrace();
                        }

                    }
                    else{
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
                                callIntent.setData(Uri.parse("tel:" + AllProducts.get(position).get("mobile")));
                                startActivity(callIntent);
                            }
                            else {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + AllProducts.get(position).get("mobile")));
                                startActivity(callIntent);
                            }
                        }
                        catch (Exception ex)
                        {ex.printStackTrace();
                        }

                    }

                }
            });


//            viewholder.liner.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
//
//                    Fragment fragment=new ListingDetails();
//                    Bundle bundle=new Bundle();
//                    bundle.putString("id",AllProducts.get(position).get("id"));
//                    bundle.putString("company_name",AllProducts.get(position).get("company_name"));
//                    bundle.putString("address",AllProducts.get(position).get("address"));
//                    bundle.putString("c1_mobile1",AllProducts.get(position).get("c1_mobile1"));
//                    bundle.putString("name",AllProducts.get(position).get("c1_fname")+" "+AllProducts.get(position).get("c1_fname")+" "+AllProducts.get(position).get("c1_lname"));
//                    FragmentManager manager=getActivity().getSupportFragmentManager();
//                    FragmentTransaction ft=manager.beginTransaction();
//                    fragment.setArguments(bundle);
//                    ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
//
//                }
//            });

            //  Log.d("fdgdfgdfhgg",AllProducts.get(position).get("liking"));




//            viewholder.name.setText(AllProducts.get(position).get("company_name"));
//            viewholder.etxtview.setText(AllProducts.get(position).get("category"));
//
//
//            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//
//            viewholder.iamge.setImageUrl(AllProducts.get(position).get("logo").toString().replace(" ","%20"),imageLoader);

            return convertView;
        }
    }


}
