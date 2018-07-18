package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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
import buyer.bizzcity.bizzcityinfobuyer.Activites.Login;
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
public class WriteReview extends Fragment {


    public WriteReview() {
        // Required empty public constructor
    }

    Button bubmitReview;
    EditText input_name,input_email,editText;
    MaterialRatingBar rtbar;
    Dialog dialog;
    TextView comName,comAdd,totalReviwe;
    String rate="";


    List<HashMap<String,String>> AllProducts ;
    ListView expListView;
    HashMap<String,String> map;
    JSONObject jsonObject1;
    FloatingActionButton fabButton;
    String value="";
    List<String> data=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_write_review, container, false);
//
//        LayoutInflater inflater2 = getLayoutInflater();
//        ViewGroup header = (ViewGroup)inflater2.inflate(R.layout.header, expListView, false);

        comName=view.findViewById(R.id.comName);
        comAdd=view.findViewById(R.id.comAdd);
        editText=view.findViewById(R.id.editText);
        rtbar=view.findViewById(R.id.rtbar);
        bubmitReview=view.findViewById(R.id.submitReview);
        totalReviwe=view.findViewById(R.id.totalReviwe);

        //input_name.setText(MyPrefrences.getUSENAME(getActivity()));

        HomeAct.title.setText("Write a Review");
        AllProducts = new ArrayList<>();
        expListView = (ListView) view.findViewById(R.id.lvExp);

        //    expListView.addHeaderView(header);

        comName.setText(getArguments().getString("company_name"));
        comAdd.setText(getArguments().getString("address"));
        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //  dialog.setCancelable(false);
        Util.showPgDialog(dialog);

        Log.d("sdfsdgsfgdfgd",getArguments().getString("id"));

//        if (MyPrefrences.getUserLogin(getActivity())==false){
//            Toast.makeText(getActivity(), "login", Toast.LENGTH_SHORT).show();
//        }

        rtbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("sfsgfsdgdfgdfg","true");
                MaterialRatingBar bar = (MaterialRatingBar) view;
                Log.d("dfsdfgsdgdfgdfg", String.valueOf(bar.getRating()));
            }
        });
        rtbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar, final float rating, final boolean fromUser) {
                if (fromUser) {
//                    ratingBar.setRating(Math.ceil(rating));
                    Log.d("dfsdfsdfsdfsdf", String.valueOf(rating));
                    rate= String.valueOf(rating);
                }
            }
        });


        bubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!rate.isEmpty()) {
                    Util.showPgDialog(dialog);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, Api.ratingSubmit,
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
                            params.put("fullName", MyPrefrences.getUSENAME(getActivity()));
                            params.put("email", MyPrefrences.getEMAILID(getActivity()));
                            params.put("companyCode", getArguments().getString("id"));
                            params.put("review", editText.getText().toString());
                            params.put("rating_score", rate);
                            params.put("mobile", MyPrefrences.getMobile(getActivity()));
                            params.put("userId", MyPrefrences.getUserID(getActivity()));
                            Log.d("sdfsfsdfsdgfs", MyPrefrences.getUserID(getActivity()));

                            return params;
                        }
                    };
                    postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    postRequest.setShouldCache(false);

                    AppController.getInstance().addToRequestQueue(postRequest);
                }
                else{
                    Toast.makeText(getActivity(), "Please give review.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.ratingByCompany+"?companyId="+ getArguments().getString("id"), null, new Response.Listener<JSONObject>() {

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

                            totalReviwe.setText("Total Reviews ("+jsonArray.length()+")");

                            //   JSONObject jsonObject2=jsonObject.getJSONObject("comapnyDetails");

                            map=new HashMap();
                            map.put("id",jsonObject.optString("id"));
                            map.put("mobile",jsonObject.optString("mobile"));
                            map.put("rating_score",jsonObject.optString("rating_score"));
                            map.put("rating_date",jsonObject.optString("rating_date"));
                            map.put("review",jsonObject.optString("review"));
                            map.put("uname",jsonObject.optString("uname"));
                            //    map.put("company_name",jsonObject2.optString("company_name"));
//                            map.put("c1_fname",jsonObject2.optString("c1_fname")+" "+jsonObject2.optString("c1_mname")+" "+jsonObject2.optString("c1_lname"));
//                            map.put("c1_email",jsonObject2.optString("c1_email"));
//                            map.put("c1_mobile1",jsonObject2.optString("c1_mobile1"));
//                            map.put("keywords",jsonObject2.optString("keywords"));
//                            map.put("new_keywords",jsonObject2.optString("new_keywords"));


                            Adapter adapter=new Adapter();
                            expListView.setAdapter(adapter);
                            AllProducts.add(map);
                        }
                    }
                    else{
                       // Toast.makeText(getActivity(), "No Reviews in this Company...", Toast.LENGTH_SHORT).show();
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

                Fragment fragment=new WriteReview();
                Bundle bundle=new Bundle();
                bundle.putString("id",getArguments().getString("id"));
                bundle.putString("company_name", comName.getText().toString());
                bundle.putString("address", comAdd.getText().toString());
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();

            }
        });
        dialog.show();

    }

    public class Viewholder{
        NetworkImageView iamge;
        TextView comName,c1_fname,c1_email,c1_mobile1,uname,review,user_icon;
        LinearLayout liner;
        MaterialRatingBar rtbar2;
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

            convertView=inflater.inflate(R.layout.list_view_rating,parent,false);

            final Viewholder viewholder=new Viewholder();

            viewholder.comName=convertView.findViewById(R.id.comName);
            viewholder.c1_mobile1=convertView.findViewById(R.id.c1_mobile1);
            viewholder.review=convertView.findViewById(R.id.review);
            viewholder.user_icon=convertView.findViewById(R.id.user_icon);
            viewholder.rtbar2=convertView.findViewById(R.id.rtbar2);

//            viewholder.comName.setText(AllProducts.get(position).get("uname"));
            viewholder.comName.setText(AllProducts.get(position).get("uname").substring(0,1).toUpperCase()+AllProducts.get(position).get("uname").substring(1).toLowerCase());
            viewholder.user_icon.setText(AllProducts.get(position).get("uname"));
//            viewholder.c1_mobile1.setText(AllProducts.get(position).get("mobile"));
            String number;
            number="+91-XXXXXXX"+AllProducts.get(position).get("mobile").substring(AllProducts.get(position).get("mobile").length() - 3);
            viewholder.c1_mobile1.setText(number);
            viewholder.review.setText(AllProducts.get(position).get("review"));
            viewholder.rtbar2.setRating(Float.parseFloat(AllProducts.get(position).get("rating_score")));

            return convertView;
        }
    }
}
