package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import buyer.bizzcity.bizzcityinfobuyer.Activites.HomeAct;
import buyer.bizzcity.bizzcityinfobuyer.Activites.WebViewOpen;
import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Const;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategory extends Fragment {


    public SubCategory() {
        // Required empty public constructor
    }
    List<HashMap<String,String>> AllProducts ;
    GridView expListView;
    Button btnGetQuote;
    Dialog dialog;
    String name;
    String array;
    JSONArray jsonArray2;
    JSONObject jsonObject1;
    List<String> subCat =new ArrayList<>();
    JSONArray jsonArray3;
    JSONArray jsonArray1;
    ViewPager viewPager2;
    List<Const> AllBaner   = new ArrayList<>();
    CustomPagerAdapter2 mCustomPagerAdapter2;
    CirclePageIndicator indicator2;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sub_category, container, false);
        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);
        btnGetQuote = (Button) view.findViewById(R.id.btnGetQuote);


        viewPager2 = (ViewPager) view.findViewById(R.id.slider2);
        indicator2 = (CirclePageIndicator)view.findViewById(R.id.indicat2);

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

        Log.d("gfgfhfhfghg",getArguments().getString("id").toString());


        ////  Begin Baner Api


        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(Request.Method.GET,
                Api.bannerImage+"?catId=Home"+"&cityId="+ MyPrefrences.getCityID(getActivity()), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("ResposeBaner", response.toString());
                Util.cancelPgDialog(dialog);
                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");
                    HashMap<String,String> hashMap = null;
                    if (response.getString("status").equalsIgnoreCase("success")){
                        AllBaner.clear();
                        JSONArray jsonArray=response.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

//                            hashMap = new HashMap<>();
//                            hashMap.put("id",jsonObject.optString("id"));
//                            hashMap.put("cat_id",jsonObject.optString("cat_id"));
//                            hashMap.put("eventName",jsonObject.optString("eventName"));
//                            hashMap.put("photo",jsonObject.optString("photo"));
//
//             3
//               viewPager2.setAdapter(mCustomPagerAdapter2);
//                            indicator.setViewPager(viewPager2);
//                            mCustomPagerAdapter2.notifyDataSetChanged();


                            if (jsonObject.optString("cat_id").equalsIgnoreCase(getArguments().getString("id").toString())) {
                                AllBaner.add(new Const(jsonObject.optString("id"), jsonObject.optString("cat_id"), jsonObject.optString("subcategory"), jsonObject.optString("image"), jsonObject.optString("meta_keywords"), jsonObject.optString("meta_description"), jsonObject.optString("meta_title"),null,null,null));
                            }
                            mCustomPagerAdapter2=new CustomPagerAdapter2(getActivity());
                            viewPager2.setAdapter(mCustomPagerAdapter2);
                            indicator2.setViewPager(viewPager2);
                            mCustomPagerAdapter2.notifyDataSetChanged();
                            //viewPager2.setPageTransformer(true, new RotateUpTransformer());
                            //mCustomPagerAdapter2.addData(AllBaner);


                            final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (currentPage == AllBaner.size()) {
                                        currentPage = 0;
                                    }
                                    viewPager2.setCurrentItem(currentPage++, true);
                                }
                            };

                            timer = new Timer(); // This will create a new Thread
                            timer .schedule(new TimerTask() { // task to be scheduled

                                @Override
                                public void run() {
                                    handler.post(Update);
                                }
                            }, DELAY_MS, PERIOD_MS);



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
        jsonObjReq2.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(jsonObjReq2);
        ///// End baner APi

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.topCategory, null, new Response.Listener<JSONObject>() {

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
                            if (jsonObject.optString("id").equalsIgnoreCase(getArguments().getString("id").toString())){
                                jsonArray1=jsonObject.getJSONArray("subcategory");

                                Log.d("dfsdfgfsdgdfg",jsonArray1.toString());


                                btnGetQuote.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Fragment fragment=new GetQuotes();
                                        Bundle bundle=new Bundle();
                                        bundle.putString("type","sub");
                                        bundle.putString("id",name);
                                        bundle.putString("comName","");
                                        //              bundle.putStringArrayList("array", (ArrayList<String>) subCat);
                                        bundle.putString("array", jsonArray1.toString());
                                        FragmentManager manager=getActivity().getSupportFragmentManager();
                                        FragmentTransaction ft=manager.beginTransaction();
                                        fragment.setArguments(bundle);
                                        ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
                                    }
                                });



                                Log.d("fgdsgdfgdfgd",jsonObject.optString("category"));

                                getActivity().setTitle(jsonObject.optString("category"));
                                HomeAct.title.setText(jsonObject.optString("category").toString());
                                name=jsonObject.optString("category");
                                for (int j=0;j<jsonArray1.length();j++) {

                                    jsonObject1 = jsonArray1.getJSONObject(j);
                                    Log.d("fgdgdfhh",jsonObject1.optString("subcategory"));

                                    jsonArray3=new JSONArray();
                                    jsonObject1.put("id",jsonObject1.optString("id"));
                                    jsonArray3.put(jsonObject1);



                                    Log.d("dfssdfgsdgdfgdfg",jsonArray3.toString());


                                    array=jsonObject1.optString("subcategory");
                                   // jsonArray2=new JSONArray(jsonObject1.optString("subcategory"));

                                    HashMap<String,String> map=new HashMap<>();

                                        map.put("id", jsonObject1.optString("id"));
                                        map.put("cat_id", jsonObject1.optString("cat_id"));
                                        map.put("subcategory", jsonObject1.optString("subcategory"));
                                        map.put("meta_title", jsonObject1.optString("meta_title"));


                                        subCat.add(jsonObject1.optString("subcategory"));


                                        Adapter adapter=new Adapter();
                                        expListView.setAdapter(adapter);
                                        AllProducts.add(map);






                                }

                            }

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




        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment=new ListedPage();
                Bundle bundle=new Bundle();
                bundle.putString("id",AllProducts.get(i).get("id"));
                bundle.putString("title",AllProducts.get(i).get("subcategory"));
                bundle.putString("search","no");
                bundle.putString("keyowd","");
                bundle.putString("value","");
                FragmentManager manager=getFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
            }
        });
        return  view;
    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        TextView textview;

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

            convertView=inflater.inflate(R.layout.list_s_list,parent,false);
            textview=convertView.findViewById(R.id.textview);
            textview.setText(AllProducts.get(position).get("subcategory"));


            Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "muli_semibold.ttf");
            textview.setTypeface(face);

            return convertView;
        }
    }

    class CustomPagerAdapter2 extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter2(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return AllBaner.size();
        }



        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }



        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.page_item, container, false);

            NetworkImageView imageView = (NetworkImageView) itemView.findViewById(R.id.imageView);





            //imageView.setImageResource(R.drawable.bci_small);
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            imageView.setImageUrl(AllBaner.get(position).getPhoto().toString().replace(" ", "%20"), imageLoader);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AllBaner.get(position).getOrgby().toString().isEmpty() ) {

                       // Toast.makeText(getActivity(), "blank", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        //Toast.makeText(getActivity(), AllBaner.get(position).getOrgby().toString(), Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(getActivity(), WebViewOpen.class);
                        intent.putExtra("link",AllBaner.get(position).getOrgby().toString());
                        startActivity(intent);

//                        Fragment fragment=new WebViewOpen();
//                        Bundle bundle=new Bundle();
//                        bundle.putString("link",AllBaner.get(position).getOrgby().toString());
//                        FragmentManager manager=getActivity().getSupportFragmentManager();
//                        FragmentTransaction ft=manager.beginTransaction();
//                        fragment.setArguments(bundle);
//                        ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();

                    }
                }
            });



//            imageView.setImageResource(mResources[position]);

//            Button gotopage=(Button)itemView.findViewById(R.id.goto_page);
//            gotopage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getApplicationContext(),Choose_Login.class));
//
//                }
//            });


            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }




}
