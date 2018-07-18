package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Offers extends Fragment {


    public Offers() {
        // Required empty public constructor
    }

    Dialog dialog;
    List<HashMap<String,String>> AllProducts ;
    GridView expListView;
    JSONObject jsonObject1;
    TextView textView;
    ImageView imageNoListing;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_offers, container, false);
        Log.d("fgfdgdfgdfhgd",getArguments().getString("id"));
        Log.d("sdfsdfsdfsdgfsdfgs", HomeAct.title.getText().toString());
        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);
        textView = (TextView) view.findViewById(R.id.textView);
        imageNoListing = (ImageView) view.findViewById(R.id.imageNoListing);
        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

//        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
//                    Fragment fragment=new ListingDetailsOffers();
//                    Bundle bundle=new Bundle();
//                    bundle.putString("company_id",AllProducts.get(i).get("company_id"));
//                    bundle.putString("id",getArguments().getString("id"));
////                bundle.putString("company_name",AllProducts.get(i).get("company_name"));
////                bundle.putString("address",AllProducts.get(i).get("address"));
////                bundle.putString("c1_mobile1",AllProducts.get(i).get("c1_mobile1"));
////                bundle.putString("name",AllProducts.get(i).get("c1_fname")+" "+AllProducts.get(i).get("c1_fname")+" "+AllProducts.get(i).get("c1_lname"));
//                    FragmentManager manager=getActivity().getSupportFragmentManager();
//                    FragmentTransaction ft=manager.beginTransaction();
//                    fragment.setArguments(bundle);
//                    ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
//            }
//        });





        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.offerByCategory+"?cat_id="+ getArguments().getString("id")+"&cityId="+ MyPrefrences.getCityID(getActivity()), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("ResposeOffer", response.toString());
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

                            JSONObject jsonObject2=jsonObject.getJSONObject("comapnyDetails");


                                hashMap = new HashMap<>();
                                hashMap.put("id", jsonObject.optString("id"));
                                hashMap.put("cat_id", jsonObject.optString("cat_id"));
                                hashMap.put("company_id", jsonObject.optString("company_id"));
                                hashMap.put("membership", jsonObject.optString("membership"));
                                hashMap.put("heading", jsonObject.optString("heading"));
                                hashMap.put("description", jsonObject.optString("description"));
                                hashMap.put("offer_type", jsonObject.optString("offer_type"));
                                hashMap.put("discount", jsonObject.optString("discount"));
                                hashMap.put("actual_price", jsonObject.optString("actual_price"));
                                hashMap.put("offer_price",jsonObject.optString("offer_price"));
                                hashMap.put("coupon_code",jsonObject.optString("coupon_code"));
                                hashMap.put("offer_from",jsonObject.optString("offer_from"));
                                hashMap.put("offer_to",jsonObject.optString("offer_to"));
                                hashMap.put("image",jsonObject.optString("image"));
                                hashMap.put("cat_name",jsonObject.optString("cat_name"));
                                hashMap.put("posted_date",jsonObject.optString("posted_date"));
                                hashMap.put("premium",jsonObject2.optString("premium"));
                            hashMap.put("company_name", jsonObject2.optString("company_name"));
                            hashMap.put("address", jsonObject2.optString("address"));
                            hashMap.put("c1_mobile1", jsonObject2.optString("c1_mobile1"));
                            hashMap.put("new_keywords", jsonObject2.optString("new_keywords"));
                            hashMap.put("c1_fname", jsonObject2.optString("c1_fname")+" "+jsonObject2.optString("c1_mname")+" "+jsonObject2.optString("c1_lname"));

                                Adapter adapter = new Adapter();
                                expListView.setAdapter(adapter);
                                AllProducts.add(hashMap);


                        }
                        //  AllEvents.add(hashMap);
                    }
                    else if (response.getString("status").equalsIgnoreCase("failure")){
                        //Toast.makeText(getActivity(), "offer list not available", Toast.LENGTH_SHORT).show();
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

        // Adding request to request queue
        jsonObjReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(jsonObjReq);

//        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
//            }
//        });





        return view;
    }

    public static Fragment NewInstance(String typeforListing,String searchSt,String id) {
        Bundle args = new Bundle();
        args.putString("fragmentKey", typeforListing);
        args.putString("searc",searchSt);
        args.putString("id",id);

        Offers fragment = new Offers();
        fragment.setArguments(args);

        return fragment;
    }



    class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        TextView name,date,discount,desc,catName,address,sub_cat;
        MaterialRatingBar rating;
        NetworkImageView banner;
        LinearLayout linerLayout;
        CardView cardView;
        ImageView prem;
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


            convertView=inflater.inflate(R.layout.list_my_offers,parent,false);

            name=convertView.findViewById(R.id.name);
            date=convertView.findViewById(R.id.date);
            discount=convertView.findViewById(R.id.discount);
            desc=convertView.findViewById(R.id.desc);
          //  banner=convertView.findViewById(R.id.banner);
            catName=convertView.findViewById(R.id.catName);
            linerLayout=convertView.findViewById(R.id.linerLayout);
            cardView=convertView.findViewById(R.id.cardView);
            address=convertView.findViewById(R.id.address);
            prem=convertView.findViewById(R.id.prem);
            sub_cat=convertView.findViewById(R.id.sub_cat);

            catName.setText(HomeAct.title.getText().toString());
            name.setText(AllProducts.get(position).get("heading"));
            date.setText(AllProducts.get(position).get("posted_date"));
            sub_cat.setText(AllProducts.get(position).get("company_name"));
            catName.setText(AllProducts.get(position).get("cat_name"));
            address.setText(AllProducts.get(position).get("address"));
            if (AllProducts.get(position).get("discount").equalsIgnoreCase("Select Discount")){
                discount.setText("₹ "+AllProducts.get(position).get("offer_price"));
            }
            else {
                discount.setText(AllProducts.get(position).get("discount"));
            }
            desc.setText(AllProducts.get(position).get("description"));

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
           // banner.setImageUrl(AllProducts.get(position).get("image"),imageLoader);


            if (AllProducts.get(position).get("premium").equalsIgnoreCase("Yes")){

                cardView.setCardBackgroundColor(Color.parseColor("#FFFDF4BE"));
                linerLayout.setBackgroundColor(Color.parseColor("#FFFDF4BE"));
                prem.setVisibility(View.VISIBLE);
            }
            else if (AllProducts.get(position).get("premium").equalsIgnoreCase("No")){

                cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
                linerLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                prem.setVisibility(View.GONE);
            }

//            linerLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Fragment fragment=new ListingDetailsOffers();
//                    Bundle bundle=new Bundle();
////                    bundle.putString("company_id",AllProducts.get(position).get("company_id"));
////                    bundle.putString("id",getArguments().getString("id"));
//
//
//                    bundle.putString("id",AllProducts.get(position).get("id"));
//                    bundle.putString("company_name",AllProducts.get(position).get("company_name"));
//                    bundle.putString("address",AllProducts.get(position).get("address"));
//                    bundle.putString("c1_mobile1",AllProducts.get(position).get("c1_mobile1"));
//                    // bundle.putString("rating",AllProducts.get(position).get("rating"));
//                    //bundle.putString("totlauser",AllProducts.get(position).get("totlauser"));
//                    bundle.putString("c1_fname",AllProducts.get(position).get("c1_fname"));
//
//
//                bundle.putString("company_name",AllProducts.get(position).get("company_name"));
//                bundle.putString("new_keywords",AllProducts.get(position).get("new_keywords"));
////                bundle.putString("address",AllProducts.get(i).get("address"));
////                bundle.putString("c1_mobile1",AllProducts.get(i).get("c1_mobile1"));
////                bundle.putString("name",AllProducts.get(i).get("c1_fname")+" "+AllProducts.get(i).get("c1_fname")+" "+AllProducts.get(i).get("c1_lname"));
//                    FragmentManager manager=getActivity().getSupportFragmentManager();
//                    FragmentTransaction ft=manager.beginTransaction();
//                    fragment.setArguments(bundle);
//                    ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
//                }
//            });
//            final Typeface tvFont = Typeface.createFromAsset(getActivity().getAssets(), "comicz.ttf");
//            title.setTypeface(tvFont);
            final Typeface tvFont = Typeface.createFromAsset(getActivity().getAssets(), "muli_bold.ttf");
            final Typeface tvFont2 = Typeface.createFromAsset(getActivity().getAssets(), "muli.ttf");
            name.setTypeface(tvFont);
            desc.setTypeface(tvFont2);
            date.setTypeface(tvFont2);




            return convertView;
        }
    }



}
