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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class ListingDetailsOffers extends Fragment {


    public ListingDetailsOffers() {
        // Required empty public constructor
    }
    TextView phone,address,name,comName,review,meassage,call,location,landline;
    Button btnGetQuote;
    Dialog dialog;
    HashMap<String,String> map;
    JSONObject jsonObject1;
    GridView listview;
    List<String> subCat =new ArrayList<>();
    LinearLayout servicesLay;
    List<HashMap<String,String>> list=new ArrayList<>();
    NetworkImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_listing_details2, container, false);

        Log.d("fgfdgdfgdfhgdf",getArguments().getString("company_name"));
        Log.d("fgfdgdfgdfhgdfgfgfg",getArguments().getString("id"));
        Log.d("fgfdgdfgdfhgdfgfgfg",MyPrefrences.getUserID(getActivity()));

//        dialog=new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setCancelable(false);
      //  Util.showPgDialog(dialog);

        HomeAct.title.setText(getArguments().getString("company_name"));

        comName=view.findViewById(R.id.comName);
        name=view.findViewById(R.id.name);
        address=view.findViewById(R.id.address);
        phone=view.findViewById(R.id.phone);
        btnGetQuote=view.findViewById(R.id.btnGetQuote);
        review=view.findViewById(R.id.review);
        meassage=view.findViewById(R.id.meassage);
        call=view.findViewById(R.id.call);
        listview=view.findViewById(R.id.listview);
        servicesLay=view.findViewById(R.id.servicesLay);
        location=view.findViewById(R.id.location);
        imageView=view.findViewById(R.id.imageView);
        landline=view.findViewById(R.id.landline);
        landline.setVisibility(View.GONE);
        comName.setText(getArguments().getString("company_name"));
        name.setText(getArguments().getString("c1_fname"));
        address.setText(getArguments().getString("address"));
        phone.setText(getArguments().getString("c1_mobile1"));

        location.setText(getArguments().getString("locationName"));

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageView.setImageUrl(getArguments().getString("image"),imageLoader);

        meassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Fragment fragment=new GetQuotes();
//                Bundle bundle=new Bundle();
//                bundle.putString("type","details");
//                bundle.putString("id",getArguments().getString("id"));
//                bundle.putString("comName",getArguments().getString("company_name"));
//                bundle.putStringArrayList("array", (ArrayList<String>) subCat );
//                FragmentManager manager=getActivity().getSupportFragmentManager();
//                FragmentTransaction ft=manager.beginTransaction();
//                fragment.setArguments(bundle);
//                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new WriteReview();
                Bundle bundle=new Bundle();
                bundle.putString("id",getArguments().getString("id"));
                bundle.putString("company_name", getArguments().getString("company_name").toString());
                bundle.putString("address", getArguments().getString("address").toString());
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
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
        });


        if (getArguments().getString("new_keywords").equals("")){
            //Toast.makeText(getActivity(), "blank", Toast.LENGTH_SHORT).show();
            servicesLay.setVisibility(View.GONE);
        }
        else{
            servicesLay.setVisibility(View.VISIBLE);
            // Toast.makeText(getActivity(), "not blank", Toast.LENGTH_SHORT).show();
        }

        String str = getArguments().getString("new_keywords");
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


//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                Api.subcategoryToCompany+"?subcatId="+getArguments().getString("id")+"&myId="+ MyPrefrences.getUserID(getActivity()), null, new Response.Listener<JSONObject>() {
//
////        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
////                Api.subcategoryToCompany+"?subcatId="+"378"+"&myId="+MyPrefrences.getUserID(getActivity()), null, new Response.Listener<JSONObject>() {
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
//
//                    if (response.getString("status").equalsIgnoreCase("success")) {
//
//                        JSONArray jsonArray = response.getJSONArray("message");
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                            Log.d("fgdgdfgdfghdfghdhg1",jsonObject.optString("id").toString());
//                            Log.d("fgdgdfgdfghdfghdhg2",getArguments().getString("company_id"));
//
//
//                            if (jsonObject.optString("id").toString().equals(getArguments().getString("company_id"))) {
//                                JSONArray jsonArray1 = jsonObject.getJSONArray("rating");
//                                for (int j = 0; j < jsonArray1.length(); j++) {
//                                    jsonObject1 = jsonArray1.getJSONObject(j);
//                                    Log.d("fdsgvfdh", jsonObject1.optString("ratingUser"));
//                                    Log.d("fdsgvfdh", jsonObject1.optString("rating"));
//                                }
//
//
//                                comName.setText(jsonObject.optString("company_name"));
//                                name.setText(jsonObject.optString("c1_fname"));
//                                address.setText(jsonObject.optString("address"));
//                                phone.setText(jsonObject.optString("c1_mobile1"));
//
////                            map=new HashMap();
////                            map.put("id",jsonObject.optString("id"));
////                            map.put("cat_id",jsonObject.optString("cat_id"));
////                            map.put("company_name",jsonObject.optString("company_name"));
////                            map.put("address",jsonObject.optString("address"));
////                            map.put("c1_fname",jsonObject.optString("c1_fname"));
////                            map.put("c1_mname",jsonObject.optString("c1_mname"));
////                            map.put("c1_lname",jsonObject.optString("c1_lname"));
////                            map.put("c1_email",jsonObject.optString("c1_email"));
////                            map.put("c1_mobile1",jsonObject.optString("c1_mobile1"));
////                            map.put("c1_mobile2",jsonObject.optString("c1_mobile2"));
////                            map.put("website",jsonObject.optString("website"));
////                            map.put("totlauser",jsonObject1.optString("ratingUser"));
////                            map.put("rating",jsonObject1.optString("rating"));
////                            map.put("liking",jsonObject.optString("liking"));
//
//
//                            }
//                        }
//                    }
//                    else{
//                        Toast.makeText(getActivity(), "No Record Available.", Toast.LENGTH_SHORT).show();
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
//
//        // Adding request to request queue
//        jsonObjReq.setShouldCache(false);
//        AppController.getInstance().addToRequestQueue(jsonObjReq);

        btnGetQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new GetQuotes();
                Bundle bundle=new Bundle();
                bundle.putString("type","details");
                bundle.putString("id",getArguments().getString("id"));
                bundle.putString("comName",comName.getText().toString());
                bundle.putStringArrayList("array", (ArrayList<String>) subCat );
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
            }
        });



        return view;
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

            Typeface face=Typeface.createFromAsset(getActivity().getAssets(), "muli_bold.ttf");
            textView1.setTypeface(face);

            return convertView;
        }
    }


}
