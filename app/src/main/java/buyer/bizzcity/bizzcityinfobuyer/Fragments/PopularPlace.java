package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularPlace extends Fragment {


    public PopularPlace() {
        // Required empty public constructor
    }

    List<HashMap<String,String>> AllProducts ;
    GridView expListView;
    Button btnGetQuote;
    Dialog dialog;
    List<String> subCat =new ArrayList<>();
    ImageView imageNoListing;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_popup_city, container, false);

        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);
        btnGetQuote = (Button) view.findViewById(R.id.btnGetQuote);
        imageNoListing = (ImageView) view.findViewById(R.id.imageNoListing);

        HomeAct.title.setText("Popular Places");

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

//        AdView adView = (AdView)view. findViewById(R.id.search_ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.nearestPopularPlaces+"?cityId="+ MyPrefrences.getCityID(getActivity())+"&lat="+HomeAct.latitude+"&long="+HomeAct.longitude, null, new Response.Listener<JSONObject>() {

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

                            hashMap = new HashMap<>();
                            hashMap.put("id",jsonObject.optString("id"));
                            hashMap.put("photo",jsonObject.optString("photo"));
                            hashMap.put("name",jsonObject.optString("name"));
                            hashMap.put("location",jsonObject.optString("location"));
                            hashMap.put("latitude",jsonObject.optString("latitude"));
                            hashMap.put("longitude",jsonObject.optString("longitude"));
                            hashMap.put("phone",jsonObject.optString("phone"));
                            hashMap.put("telephone",jsonObject.optString("telephone"));
                            hashMap.put("distance",jsonObject.optString("distance"));
                            hashMap.put("created",jsonObject.optString("created"));
                            hashMap.put("closing_days",jsonObject.optString("closing_days"));
                            hashMap.put("tags",jsonObject.optString("tags"));

                            Adapter adapter=new Adapter();
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


        return view;
    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        TextView eventname,datetext,evntLoc,phone,closing_days,tag;
        NetworkImageView eventpic;

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

            convertView=inflater.inflate(R.layout.list_place,parent,false);
            eventname=convertView.findViewById(R.id.eventname);
            eventpic=convertView.findViewById(R.id.eventpic);
            datetext=convertView.findViewById(R.id.datetext);
            evntLoc=convertView.findViewById(R.id.evntLoc);
            phone=convertView.findViewById(R.id.phone);
            closing_days=convertView.findViewById(R.id.closing_days);
            tag=convertView.findViewById(R.id.tag);

            eventname.setText(AllProducts.get(position).get("name"));
            datetext.setText(AllProducts.get(position).get("eventDateTo"));
            evntLoc.setText(AllProducts.get(position).get("location"));
            phone.setText(AllProducts.get(position).get("phone"));
            closing_days.setText(AllProducts.get(position).get("closing_days"));
            tag.setText(AllProducts.get(position).get("tags"));
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            eventpic.setImageUrl(AllProducts.get(position).get("photo").toString().replace(" ","%20"),imageLoader);

            return convertView;
        }
    }


}
