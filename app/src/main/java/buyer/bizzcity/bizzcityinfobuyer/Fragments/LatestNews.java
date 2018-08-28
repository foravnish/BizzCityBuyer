package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
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

import static android.text.Html.fromHtml;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestNews extends Fragment {


    public LatestNews() {
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
        View view= inflater.inflate(R.layout.fragment_latest_news, container, false);

        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);
        imageNoListing = (ImageView) view.findViewById(R.id.imageNoListing);

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

        HomeAct.title.setText("Latest News & Updates");

//        AdView adView = (AdView)view. findViewById(R.id.search_ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        Log.d("dfsdfsdfsdgsdfg",MyPrefrences.getCityID(getActivity()));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.news+"?cityId="+ MyPrefrences.getCityID(getActivity()), null, new Response.Listener<JSONObject>() {

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

                            HashMap<String,String> map=new HashMap<>();

                            map.put("id", jsonObject.optString("id"));
                            map.put("heading", jsonObject.optString("heading"));
                            map.put("created", jsonObject.optString("created"));
                            map.put("image_thumb", jsonObject.optString("image_thumb"));
                            map.put("image_tiny", jsonObject.optString("image_tiny"));
                            map.put("tag", jsonObject.optString("tag"));
//                            map.put("discount", jsonObject.optString("discount"));
//                            map.put("actual_price", jsonObject.optString("actual_price"));
//                            map.put("offer_price", jsonObject.optString("offer_price"));
//                            map.put("coupon_code", jsonObject.optString("coupon_code"));
//                            map.put("offer_from", jsonObject.optString("offer_from"));
//                            map.put("offer_to", jsonObject.optString("offer_to"));
//                            map.put("image", jsonObject.optString("image"));
//                            map.put("cat_name", jsonObject.optString("cat_name"));
//                            map.put("posted_date", jsonObject.optString("posted_date"));
//                            map.put("company_name", jsonObject2.optString("company_name"));
//                            map.put("address", jsonObject2.optString("address"));
//                            map.put("c1_mobile1", jsonObject2.optString("c1_mobile1"));
//                            map.put("new_keywords", jsonObject2.optString("new_keywords"));
//                            map.put("c1_fname", jsonObject2.optString("c1_fname")+" "+jsonObject2.optString("c1_mname")+" "+jsonObject2.optString("c1_lname"));


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
                       // errorDialog("Offer list not available");


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


//        expListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
//                showFullImageDialog(getActivity(),AllProducts.get(i).get("image_tiny").toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


//        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                //Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
//                showFullImageDialog(getActivity(),AllProducts.get(i).get("image_tiny").toString(),AllProducts.get(i).get("heading").toString());
//            }
//        });


        return view;
    }


    public static void showFullImageDialog(Context context,String image,String heading) {
        final Dialog dialog = new Dialog(context);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.showfullimage);
        ImageView back_img = (ImageView) dialog.findViewById(R.id.back_img);
        NetworkImageView fact_image = (NetworkImageView) dialog.findViewById(R.id.fact_image);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        fact_image.setImageUrl(image.replace(" ","%20"),imageLoader);

        //showImage(context, "https://www.pinerria.com/upload_images/posting/1522574016img14.jpg", fact_image);
        //PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(fact_image);
        //photoViewAttacher.onDrag(2,2);
       // photoViewAttacher.update();
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText("News");

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        TextView name,offersText,tag,desc,comName,catName;
        MaterialRatingBar rating;
        NetworkImageView imgaeView;
        LinearLayout linerLayout;

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


            convertView=inflater.inflate(R.layout.list_my_news,parent,false);

            name=convertView.findViewById(R.id.name);
            imgaeView=convertView.findViewById(R.id.imgaeView);
            offersText=convertView.findViewById(R.id.offersText);
            tag=convertView.findViewById(R.id.tag);

            name.setText(Html.fromHtml(AllProducts.get(position).get("heading")));
            offersText.setText(AllProducts.get(position).get("created"));
            tag.setText(AllProducts.get(position).get("tag")+"");

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            imgaeView.setImageUrl(AllProducts.get(position).get("image_thumb").replace(" ","%20"),imageLoader);

//            final Typeface tvFont = Typeface.createFromAsset(getActivity().getAssets(), "muli_bold.ttf");
//            name.setTypeface(tvFont);
//            tag.setTypeface(tvFont);
//            offersText.setTypeface(tvFont);



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
