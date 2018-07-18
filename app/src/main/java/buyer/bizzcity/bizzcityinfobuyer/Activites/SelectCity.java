package buyer.bizzcity.bizzcityinfobuyer.Activites;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import buyer.bizzcity.bizzcityinfobuyer.Fragments.Home;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.SubCategory;
import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;

public class SelectCity extends AppCompatActivity {

    List<HashMap<String,String>> AllProducts ;

    Button btnGetQuote;
    Dialog dialog;
    TextView back;
    AutoCompleteTextView autoTextSearch;
    List<String> data=new ArrayList<>();
    List<String> data2=new ArrayList<>();
    String vasa;


    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    // Search EditText



    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        AllProducts = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lv);
        back = (TextView) findViewById(R.id.back);
        autoTextSearch = (AutoCompleteTextView) findViewById(R.id.autoTextSearch);


        dialog=new Dialog(SelectCity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                vasa= String.valueOf(adapterView.getItemAtPosition(i));

               // Toast.makeText(getApplicationContext(), ""+vasa, Toast.LENGTH_SHORT).show();

                Util.showPgDialog(dialog);

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        Api.city, null, new Response.Listener<JSONObject>() {

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

                                    HashMap<String,String> map=new HashMap();
                                    map.put("id",jsonObject.optString("id"));
                                    map.put("city",jsonObject.optString("city"));
                                    map.put("state_id",jsonObject.optString("state_id"));
                                    map.put("status",jsonObject.optString("status"));


                                    if (vasa.equalsIgnoreCase(jsonObject.optString("city"))){
//                                        Toast.makeText(SelectCity.this, ""+jsonObject.optString("id"), Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(SelectCity.this, ""+jsonObject.optString("city"), Toast.LENGTH_SHORT).show();


                                        MyPrefrences.setCityID(getApplicationContext(),jsonObject.optString("id") );
                                        MyPrefrences.setCityName(getApplicationContext(), jsonObject.optString("city"));

                                        Intent intent=new Intent(SelectCity.this,HomeAct.class);
                                        intent.putExtra("type","0");
                                        startActivity(intent);


                                    }
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            Util.cancelPgDialog(dialog);
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Respose", "Error: " + error.getMessage());
                        //Toast.makeText(getApplicationContext(), "Error! Please Connect to the internet", Toast.LENGTH_SHORT).show();
                        // hide the progress dialog
                        Util.cancelPgDialog(dialog);
                    }
                });

                jsonObjReq.setShouldCache(false);
                AppController.getInstance().addToRequestQueue(jsonObjReq);




//
//                                    if (AllProducts.get(i).get("city").equals(vasa)){
//                                        Toast.makeText(getApplicationContext(), AllProducts.get(i).get("id"), Toast.LENGTH_SHORT).show();
//                                    }



            }
        });




        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.city, null, new Response.Listener<JSONObject>() {

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

//                        if (jsonArray.length()>1){
//                            dialog4.show();
//                        }
//                        else {
//                            dialog4.dismiss();
//                        }


                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            HashMap<String,String> map=new HashMap();
                            map.put("id",jsonObject.optString("id"));
                            map.put("city",jsonObject.optString("city"));
                            map.put("state_id",jsonObject.optString("state_id"));
                            map.put("status",jsonObject.optString("status"));

                            data.add(jsonObject.optString("city"));
                         //   data.add(jsonObject.optString("id"));





//
//                            Adapter adapter=new Adapter();
//                            expListView.setAdapter(adapter);
                            AllProducts.add(map);



                            adapter = new ArrayAdapter<String>(getApplicationContext(),  R.layout.list_city, R.id.textCity, data);
                            lv.setAdapter(adapter);





                            autoTextSearch.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                                    // When user changed the Text
                                    SelectCity.this.adapter.getFilter().filter(cs);
                                }

                                @Override
                                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                              int arg3) {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void afterTextChanged(Editable arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });

//
////                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_city_item, data);
//                            autoTextSearch.setThreshold(1);//will start working from first character
//                            autoTextSearch.setAdapter(adapter2);//setting the adapter data into the AutoCompleteTextView


//                            autoTextSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////
//                                    Toast.makeText(getApplicationContext(), ""+adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
//
////                                    MyPrefrences.setCityID(getApplicationContext(), AllProducts.get(i).get("id"));
////                                    MyPrefrences.setCityName(getApplicationContext(), AllProducts.get(i).get("city"));
////
////                                    Intent intent=new Intent(SelectCity.this,HomeAct.class);
////                                    intent.putExtra("type","0");
////                                    startActivity(intent);
//                                }
//                            });

                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Util.cancelPgDialog(dialog);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Respose", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Error! Please Connect to the internet", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                Util.cancelPgDialog(dialog);
            }
        });


        // Adding request to request queue
        jsonObjReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


    class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        TextView textCity;

        Adapter() {
            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

            convertView=inflater.inflate(R.layout.list_city,parent,false);
            textCity=convertView.findViewById(R.id.textCity);
            textCity.setText(AllProducts.get(position).get("city"));


            Typeface face = Typeface.createFromAsset(getApplicationContext().getAssets(), "muli_semibold.ttf");
            textCity.setTypeface(face);

            return convertView;
        }
    }


}
