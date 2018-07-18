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
import android.widget.AdapterView;
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
public class AllEvents extends Fragment {


    public AllEvents() {
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
        View view= inflater.inflate(R.layout.fragment_all_events, container, false);
        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);
        btnGetQuote = (Button) view.findViewById(R.id.btnGetQuote);

        imageNoListing = (ImageView) view.findViewById(R.id.imageNoListing);

        getActivity().setTitle("Event List");
        HomeAct.title.setText("Event List");

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);
//
//        AdView adView = (AdView)view. findViewById(R.id.search_ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        btnGetQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment=new GetQuotes();
                Bundle bundle=new Bundle();
                bundle.putString("type","allevent");
                bundle.putString("id","");
                bundle.putString("comName","");
                bundle.putStringArrayList("array", (ArrayList<String>) subCat );
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
            }
        });

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.eventFeature+"?cityId="+ MyPrefrences.getCityID(getActivity())+"&lat="+HomeAct.latitude+"&long="+HomeAct.longitude, null, new Response.Listener<JSONObject>() {

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
                            hashMap.put("cat_id",jsonObject.optString("cat_id"));
                            hashMap.put("eventName",jsonObject.optString("eventName"));
                            hashMap.put("photo",jsonObject.optString("photo"));
                            hashMap.put("eventDesc",jsonObject.optString("eventDesc"));
                            hashMap.put("eventDate",jsonObject.optString("eventDate"));
                            hashMap.put("eventDateTo",jsonObject.optString("eventDateTo"));
                            hashMap.put("eventVen",jsonObject.optString("eventVen"));
                            hashMap.put("meta_description",jsonObject.optString("meta_description"));
                            hashMap.put("orgBy",jsonObject.optString("orgBy"));
                            hashMap.put("orgAddress",jsonObject.optString("orgAddress"));
                            hashMap.put("orgMobile",jsonObject.optString("orgMobile"));

                            Adapter adapter=new Adapter();
                            expListView.setAdapter(adapter);
                            AllProducts.add(hashMap);

                        }
                        //  AllEvents.add(hashMap);
                    }
                    else{
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


        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment=new EventDetail();
                Bundle bundle=new Bundle();
                bundle.putString("id",AllProducts.get(i).get("id"));
                bundle.putString("photo",AllProducts.get(i).get("photo"));
                bundle.putString("eventName",AllProducts.get(i).get("eventName"));
                bundle.putString("eventVen",AllProducts.get(i).get("eventVen"));
                bundle.putString("eventDate",AllProducts.get(i).get("eventDate"));
                bundle.putString("eventDateTo",AllProducts.get(i).get("eventDateTo"));
                bundle.putString("meta_description",AllProducts.get(i).get("meta_description"));
                bundle.putString("orgBy",AllProducts.get(i).get("orgBy"));
                bundle.putString("meta_description",AllProducts.get(i).get("eventDesc"));
                bundle.putString("orgAddress",AllProducts.get(i).get("orgAddress"));
                bundle.putString("orgMobile",AllProducts.get(i).get("orgMobile"));
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
        TextView eventname,datetext,evntLoc;
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

            convertView=inflater.inflate(R.layout.list_events,parent,false);
            eventname=convertView.findViewById(R.id.eventname);
            eventpic=convertView.findViewById(R.id.eventpic);
            datetext=convertView.findViewById(R.id.datetext);
            evntLoc=convertView.findViewById(R.id.evntLoc);

            eventname.setText(AllProducts.get(position).get("eventName"));
            datetext.setText(AllProducts.get(position).get("eventDateTo"));
            evntLoc.setText(AllProducts.get(position).get("eventVen"));
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            eventpic.setImageUrl(AllProducts.get(position).get("photo").toString().replace(" ","%20"),imageLoader);

            return convertView;
        }
    }


}
