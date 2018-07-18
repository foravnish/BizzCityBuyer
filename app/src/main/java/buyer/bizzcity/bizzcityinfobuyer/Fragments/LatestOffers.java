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
import android.widget.BaseAdapter;
import android.widget.Button;
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

import static android.text.Html.fromHtml;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestOffers extends Fragment {


    public LatestOffers() {
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
        View view= inflater.inflate(R.layout.fragment_latest_offers, container, false);

        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);
        imageNoListing = (ImageView) view.findViewById(R.id.imageNoListing);

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

        HomeAct.title.setText("Offers & New Arrivals");

//        AdView adView = (AdView)view. findViewById(R.id.search_ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.offer+"?cityId="+ MyPrefrences.getCityID(getActivity()), null, new Response.Listener<JSONObject>() {

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
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            JSONObject jsonObject2=jsonObject.getJSONObject("comapnyDetails");

                            HashMap<String,String> map=new HashMap<>();

                            map.put("id", jsonObject.optString("id"));
                            map.put("cat_id", jsonObject.optString("cat_id"));
                            map.put("heading", jsonObject.optString("heading"));
                            map.put("description", jsonObject.optString("description"));
                            map.put("offer_type", jsonObject.optString("offer_type"));
                            map.put("discount", jsonObject.optString("discount"));
                            map.put("actual_price", jsonObject.optString("actual_price"));
                            map.put("offer_price", jsonObject.optString("offer_price"));
                            map.put("coupon_code", jsonObject.optString("coupon_code"));
                            map.put("offer_from", jsonObject.optString("offer_from"));
                            map.put("offer_to", jsonObject.optString("offer_to"));
                            map.put("image", jsonObject2.optString("image"));
                            map.put("cat_name", jsonObject.optString("cat_name"));
                            map.put("posted_date", jsonObject.optString("posted_date"));
                            map.put("company_name", jsonObject2.optString("company_name"));
                            map.put("address", jsonObject2.optString("address"));
                            map.put("c1_mobile1", jsonObject2.optString("c1_mobile1"));
                            map.put("new_keywords", jsonObject2.optString("new_keywords"));
                            map.put("premium", jsonObject2.optString("premium"));
                            map.put("c1_fname", jsonObject2.optString("c1_fname")+" "+jsonObject2.optString("c1_mname")+" "+jsonObject2.optString("c1_lname"));


                            Adapter adapter = new Adapter();
                            expListView.setAdapter(adapter);
                            AllProducts.add(map);


                        }
                        //  AllEvents.add(hashMap);
                    }

                    else if (response.getString("status").equalsIgnoreCase("failure")){
                        expListView.setVisibility(View.GONE);
                        imageNoListing.setVisibility(View.VISIBLE);

                        //Toast.makeText(getActivity(), "Offer list not available", Toast.LENGTH_SHORT).show();
                        //errorDialog("Offer list not available");


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        TextView name,date,discount,desc,comName,catName,address,sub_cat;
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


//            map.put("id", jsonObject.optString("id"));
//            map.put("cat_id", jsonObject.optString("cat_id"));
//            map.put("heading", jsonObject.optString("heading"));
//            map.put("description", jsonObject.optString("description"));
//            map.put("offer_type", jsonObject.optString("offer_type"));
//            map.put("discount", jsonObject.optString("discount"));
//            map.put("actual_price", jsonObject.optString("actual_price"));
//            map.put("offer_price", jsonObject.optString("offer_price"));
//            map.put("coupon_code", jsonObject.optString("coupon_code"));
//            map.put("offer_from", jsonObject.optString("offer_from"));
//            map.put("offer_to", jsonObject.optString("offer_to"));
//            map.put("image", jsonObject.optString("image"));
//            map.put("posted_date", jsonObject.optString("posted_date"));

            convertView=inflater.inflate(R.layout.list_my_offers2,parent,false);

            name=convertView.findViewById(R.id.name);
            date=convertView.findViewById(R.id.date);
            discount=convertView.findViewById(R.id.discount);
            desc=convertView.findViewById(R.id.desc);
            linerLayout=convertView.findViewById(R.id.linerLayout);
            comName=convertView.findViewById(R.id.comName);
            catName=convertView.findViewById(R.id.catName);
            cardView=convertView.findViewById(R.id.cardView);
            address=convertView.findViewById(R.id.address);
            prem=convertView.findViewById(R.id.prem);
            sub_cat=convertView.findViewById(R.id.sub_cat);
           // banner=convertView.findViewById(R.id.banner);


            if (AllProducts.get(position).get("premium").equalsIgnoreCase("Yes")){

                linerLayout.setBackgroundColor(Color.parseColor("#FFFDF4BE"));
                cardView.setCardBackgroundColor(Color.parseColor("#FFFDF4BE"));
                prem.setVisibility(View.VISIBLE);

            }
            else if (AllProducts.get(position).get("premium").equalsIgnoreCase("No")){

                linerLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
                prem.setVisibility(View.GONE);

            }

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

//            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//            banner.setImageUrl(AllProducts.get(position).get("image"),imageLoader);

            linerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment=new ListingDetailsOffers();
                    Bundle bundle=new Bundle();
//                    bundle.putString("company_id",AllProducts.get(position).get("company_id"));
//                    bundle.putString("id",AllProducts.get(position).get("company_id"));

                    bundle.putString("id",AllProducts.get(position).get("id"));
                    bundle.putString("company_name",AllProducts.get(position).get("company_name"));
                    bundle.putString("address",AllProducts.get(position).get("address"));
                    bundle.putString("c1_mobile1",AllProducts.get(position).get("c1_mobile1"));
                   // bundle.putString("rating",AllProducts.get(position).get("rating"));
                    //bundle.putString("totlauser",AllProducts.get(position).get("totlauser"));
                    bundle.putString("c1_fname",AllProducts.get(position).get("c1_fname"));
                    bundle.putString("new_keywords",AllProducts.get(position).get("new_keywords"));
                    bundle.putString("image",AllProducts.get(position).get("image"));


//                bundle.putString("company_name",AllProducts.get(i).get("company_name"));
//                bundle.putString("address",AllProducts.get(i).get("address"));
//                bundle.putString("c1_mobile1",AllProducts.get(i).get("c1_mobile1"));
//                bundle.putString("name",AllProducts.get(i).get("c1_fname")+" "+AllProducts.get(i).get("c1_fname")+" "+AllProducts.get(i).get("c1_lname"));
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=manager.beginTransaction();
                    fragment.setArguments(bundle);
                    ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
                }
            });


            final Typeface tvFont = Typeface.createFromAsset(getActivity().getAssets(), "muli_bold.ttf");
            final Typeface tvFont2 = Typeface.createFromAsset(getActivity().getAssets(), "muli.ttf");
            name.setTypeface(tvFont);
            catName.setTypeface(tvFont2);
            sub_cat.setTypeface(tvFont2);
            address.setTypeface(tvFont2);



            return convertView;
        }
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
