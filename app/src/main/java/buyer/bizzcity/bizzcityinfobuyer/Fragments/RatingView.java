package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.Map;

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
public class RatingView extends Fragment {


    public RatingView() {
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

    ImageView imageNoListing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rating_view, container, false);

        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);

        AllProducts = new ArrayList<>();
        imageNoListing = (ImageView) view.findViewById(R.id.imageNoListing);

        HomeAct.title.setText("View My Rating");



        //  HomeAct.linerFilter.setVisibility(View.VISIBLE);

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

        if (MyPrefrences.getUserLogin(getActivity())!=true){
            Fragment fragment = new LoginNow();
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        }



//        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Fragment fragment=new EmergencyDetail();
//                Bundle bundle=new Bundle();
//                bundle.putString("id",AllProducts.get(i).get("id"));
//                bundle.putString("category",AllProducts.get(i).get("category"));
//
//                FragmentManager manager=getActivity().getSupportFragmentManager();
//                FragmentTransaction ft=manager.beginTransaction();
//                fragment.setArguments(bundle);
//                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
//            }
//        });



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.myRating+"?userId="+ MyPrefrences.getUserID(getActivity()), null, new Response.Listener<JSONObject>() {


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
                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            JSONObject jsonObject2=jsonObject.getJSONObject("comapnyDetails");

                            map=new HashMap();
                            map.put("id",jsonObject.optString("id"));
                            map.put("mobile",jsonObject.optString("mobile"));
                            map.put("rating_score",jsonObject.optString("rating_score"));
                            map.put("rating_date",jsonObject.optString("rating_date"));
                            map.put("review",jsonObject.optString("review"));
                            map.put("uname",jsonObject.optString("uname"));
                            map.put("company_name",jsonObject2.optString("company_name"));
                            map.put("c1_fname",jsonObject2.optString("c1_fname")+" "+jsonObject2.optString("c1_mname")+" "+jsonObject2.optString("c1_lname"));
                            map.put("c1_email",jsonObject2.optString("c1_email"));
                            map.put("c1_mobile1",jsonObject2.optString("c1_mobile1"));
                            map.put("keywords",jsonObject2.optString("keywords"));
                            map.put("new_keywords",jsonObject2.optString("new_keywords"));
                            map.put("address",jsonObject2.optString("address"));


                            Adapter adapter=new Adapter();
                            expListView.setAdapter(adapter);
                            AllProducts.add(map);
                        }
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


        return  view;
    }


    public class Viewholder{
        NetworkImageView iamge;
        TextView comName,c1_fname,c1_email,c1_mobile1,uname,review,rating_score,date_txt2;
        LinearLayout liner;
        MaterialRatingBar rtbar2;
        LinearLayout deleteed,edit;
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


            convertView=inflater.inflate(R.layout.list_view_rating_my,parent,false);

            final Viewholder viewholder=new Viewholder();


            viewholder.comName=convertView.findViewById(R.id.comName);
           // viewholder.c1_fname=convertView.findViewById(R.id.c1_fname);
           // viewholder.c1_email=convertView.findViewById(R.id.c1_email);
           // viewholder.c1_mobile1=convertView.findViewById(R.id.c1_mobile1);
           // viewholder.uname=convertView.findViewById(R.id.uname);
            viewholder.review=convertView.findViewById(R.id.review);
            viewholder.rtbar2=convertView.findViewById(R.id.rtbar2);
            viewholder.date_txt2=convertView.findViewById(R.id.date_txt2);
            viewholder.edit=convertView.findViewById(R.id.edit);
            viewholder.deleteed=convertView.findViewById(R.id.deleteed);


            viewholder.comName.setText(AllProducts.get(position).get("company_name"));
            //viewholder.c1_fname.setText(AllProducts.get(position).get("c1_fname"));
            //viewholder.c1_email.setText(AllProducts.get(position).get("c1_email"));
            //viewholder.c1_mobile1.setText(AllProducts.get(position).get("c1_mobile1"));
            //viewholder.uname.setText(AllProducts.get(position).get("uname"));
            viewholder.review.setText(AllProducts.get(position).get("review"));
            viewholder.date_txt2.setText(AllProducts.get(position).get("rating_date"));
            viewholder.rtbar2.setRating(Float.parseFloat(AllProducts.get(position).get("rating_score")));


            viewholder.deleteed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Do you want to delete this review ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog2, int id) {
                                    dialog2.cancel();


                                    StringRequest postRequest = new StringRequest(Request.Method.POST, Api.deleteSubmitedRating,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    // response
                                                    Log.d("Response", response);
                                                    Util.cancelPgDialog(dialog);
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {

//                                            Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                                            errorDialog(jsonObject.getString("message"));

                                                        } else {
                                                            Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                                        }

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // error
                                                    Toast.makeText(getActivity(), "Error! Please connect to the Internet.", Toast.LENGTH_SHORT).show();
                                                    Util.cancelPgDialog(dialog);
                                                }
                                            }
                                    ) {


                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("ratingId",AllProducts.get(position).get("id"));


                                            return params;
                                        }
                                    };
                                    postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    postRequest.setShouldCache(false);

                                    AppController.getInstance().addToRequestQueue(postRequest);

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog2, int id) {
                                    dialog2.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Delete");
                    alert.show();

                }
            });

            viewholder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment fragment=new WriteReviewEdit();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",AllProducts.get(position).get("id"));
                    bundle.putString("company_name", AllProducts.get(position).get("company_name"));
                    bundle.putString("address", AllProducts.get(position).get("address"));
                    bundle.putString("review", AllProducts.get(position).get("review"));
                    bundle.putString("rating_score", AllProducts.get(position).get("rating_score"));
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=manager.beginTransaction();
                    fragment.setArguments(bundle);
                    ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();

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

                Fragment fragment = new RatingView();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

            }
        });
        dialog.show();

    }

}
