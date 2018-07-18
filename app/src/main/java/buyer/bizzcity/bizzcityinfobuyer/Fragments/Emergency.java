package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Emergency extends Fragment {


    public Emergency() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_emergency, container, false);

        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);

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

        HomeAct.title.setText("Emergency");

        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Fragment fragment=new EmergencyDetail();
                Bundle bundle=new Bundle();
                bundle.putString("id",AllProducts.get(i).get("id"));
                bundle.putString("category",AllProducts.get(i).get("category"));

                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
            }
        });



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.emergencyCategory+"?cityId="+ MyPrefrences.getCityID(getActivity()), null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d("Respose", response.toString());

                Util.cancelPgDialog(dialog);
                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");

                    if (response.getString("status").equalsIgnoreCase("success")){

                        JSONArray jsonArray=response.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);



                            map=new HashMap();
                            map.put("id",jsonObject.optString("id"));
                            map.put("category",jsonObject.optString("category"));
                            map.put("logo",jsonObject.optString("logo"));


                            Adapter adapter=new Adapter();
                            expListView.setAdapter(adapter);
                            AllProducts.add(map);
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


        return  view;
    }

    public class Viewholder{
        NetworkImageView iamge;
        TextView etxtview;
        LinearLayout liner;
        MaterialRatingBar rating;
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


            convertView=inflater.inflate(R.layout.list_emergency_cat,parent,false);

            final Viewholder viewholder=new Viewholder();


            viewholder.etxtview=convertView.findViewById(R.id.etxtview);
            viewholder.iamge=convertView.findViewById(R.id.iamge);



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
            viewholder.etxtview.setText(AllProducts.get(position).get("category"));


            ImageLoader imageLoader = AppController.getInstance().getImageLoader();

            viewholder.iamge.setImageUrl(AllProducts.get(position).get("logo").toString().replace(" ","%20"),imageLoader);

            return convertView;
        }
    }


}
