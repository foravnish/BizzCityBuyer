package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import org.w3c.dom.Text;

import java.util.HashMap;

import buyer.bizzcity.bizzcityinfobuyer.Activites.HomeAct;
import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutCity extends Fragment {


    public AboutCity() {
        // Required empty public constructor
    }

    Dialog dialog;
    TextView country,state,city,pincode,telCode,language,population,area,vichel,timeZone,about,textCity;
    NetworkImageView netImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about_city, container, false);

        country=view.findViewById(R.id.country);
        state=view.findViewById(R.id.state);
        city=view.findViewById(R.id.city);
        pincode=view.findViewById(R.id.pincode);
        telCode=view.findViewById(R.id.telCode);
        language=view.findViewById(R.id.language);
        population=view.findViewById(R.id.population);
        area=view.findViewById(R.id.area);
        vichel=view.findViewById(R.id.vichel);
        timeZone=view.findViewById(R.id.timeZone);
        about=view.findViewById(R.id.about);
        netImage=view.findViewById(R.id.netImage);
        textCity=view.findViewById(R.id.textCity);

        HomeAct.title.setText("About City");

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.cityDetails+"?cityId="+ MyPrefrences.getCityID(getActivity()), null, new Response.Listener<JSONObject>() {

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

                            country.setText(jsonObject.optString("country_name"));
                            state.setText(jsonObject.optString("state_name"));
                            city.setText(jsonObject.optString("city"));
                            pincode.setText(jsonObject.optString("pincode"));
                            telCode.setText(jsonObject.optString("tel_code"));
                            language.setText(jsonObject.optString("languages"));
                            population.setText(jsonObject.optString("total_population"));
                            area.setText(jsonObject.optString("area"));
                            vichel.setText(jsonObject.optString("vehicle_reg"));
                            timeZone.setText(jsonObject.optString("time_zone"));
                            about.setText(jsonObject.optString("about"));
                            textCity.setText(jsonObject.optString("parent_city"));

                            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                            netImage.setImageUrl(jsonObject.optString("image").replace(" ","%20"), imageLoader);
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


        return view;
    }

}
