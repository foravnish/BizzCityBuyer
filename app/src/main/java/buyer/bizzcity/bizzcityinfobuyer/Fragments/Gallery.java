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
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class Gallery extends Fragment {


    public Gallery() {
        // Required empty public constructor
    }

    Dialog dialog;
    GridView gridview;
    List<HashMap<String,String>> DataList;
    ImageView imageNoListing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gallery, container, false);


        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

        gridview=(GridView) view.findViewById(R.id.gridview);
        imageNoListing = (ImageView) view.findViewById(R.id.imageNoListing);

        DataList=new ArrayList<>();


        Log.d("fgdgdfgdfgdfhdf",getArguments().getString("id"));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://bizzcityinfo.com/AndroidApi.php?function=getBusinessGalleryImages&companyId="+getArguments().getString("id"), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Respose", response.toString());
                Util.cancelPgDialog(dialog);
                try {
                    //final JSONObject jsonObject = new JSONObject();
                    if (response != null) {
                        if (response.optString("status").equalsIgnoreCase("success")) {


                            gridview.setVisibility(View.VISIBLE);
                            imageNoListing.setVisibility(View.GONE);

                            JSONArray jsonArray=response.getJSONArray("message");

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);

//                            num=jsonArray.length();
                                HashMap<String,String> map=new HashMap<>();
                                map.put("id",jsonObject1.optString("id").toString());
                                map.put("photo",jsonObject1.optString("photo").toString());
                                map.put("created",jsonObject1.optString("created").toString());


                                DataList.add(map);

                                Adapter adapter=new Adapter();
                                gridview.setAdapter(adapter);
                            }


//                        if (Double.parseDouble(ver)>Double.parseDouble(latestVersion)){
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                            builder.setMessage("A newer version of BizzCityInfo is available. Would you like to update?")
//                                    .setCancelable(false)
//                                    .setPositiveButton(Html.fromHtml(getResources().getString(R.string.update1)), new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//
//                                            String url = "https://play.google.com/store/apps/details?id=sntinfotech.bizzcityinfo&hl=en";
//                                            Intent i = new Intent(Intent.ACTION_VIEW);
//                                            i.setData(Uri.parse(url));
//                                            startActivity(i);
//                                        }
//                                    })
//                                    .setNegativeButton(Html.fromHtml("<font color='#FF7F27'>NO, THANKS</font>"), new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            //  Action for 'NO' Button
//                                            dialog.cancel();
//                                        }
//                                    });
//
//                            AlertDialog alert = builder.create();
//                            alert.setTitle("BizzCityInfo");
//                            alert.show();
//
//                            Button bq = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
//                            bq.setTextColor(Color.parseColor("#026db4"));
//                            Button bq1 = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//                            bq1.setTextColor(Color.parseColor("#026db4"));
//
//                        }


                        }
                        else {
                            //Util.errorDialog(context,jsonObject.optString("message"));


                            imageNoListing.setVisibility(View.VISIBLE);

                            gridview.setVisibility(View.GONE);
//                        textView.setVisibility(View.VISIBLE);
//                        textView.setText(jsonObject.optString("message").toString());

                        }
                    }
                } catch (JSONException e) {
                    Util.errorDialog(getActivity(),"Please connect to the Internet...");
                    e.printStackTrace();
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
        TextView points,require,name,address,postedDate,subCat,leadNo;
        LayoutInflater inflater;
        ImageView lock1,lock2,lock3,lock4,lock5;
        NetworkImageView image1;
        LinearLayout qImage1;
        Adapter(){
            inflater=(LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return DataList.size();
        }

        @Override
        public Object getItem(int position) {
            return DataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custon_list_gallery, parent, false);

            }
            image1= (NetworkImageView) convertView.findViewById(R.id.image1);

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            image1.setImageUrl(DataList.get(position).get("photo").toString().replace(" ", "%20"), imageLoader);

           // Picasso.with(EditPhotos.this).load(DataList.get(position).get("photo")).into(image1);


//            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    imageOne(1, 2);
//                    id=DataList.get(i).get("id");
//                    Log.d("dfdsgfdgdfghdfhdg",DataList.get(i).get("id"));
//
//
//                }
//            });






//            imageLoader = AppController.getInstance().getImageLoader();
//
//            name.setText(DataList.get(position).getName().toString());
//            image.setImageUrl(DataList.get(position).getDesc().toLowerCase(),imageLoader);

            return convertView;
        }
    }

}
