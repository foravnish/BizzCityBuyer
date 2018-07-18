package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
public class ContactUs extends Fragment {
    private GoogleMap mMap;

    public ContactUs() {
        // Required empty public constructor
    }

    TextView sellerNo, buyerNo;

    private static View view;
    TextView linerYoutube,lineInsta,lineTwitter,linerFB,linerPhone,linerPhone1;
    TextView name;
    TextView linerEmail,skype;
    Dialog dialog;
    TextView website,email,helpNo,address,com_name,co_name,facebook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us2, container, false);

        HomeAct.title.setText("Contact Us");


        website=view.findViewById(R.id.website);
        email=view.findViewById(R.id.email);
        helpNo=view.findViewById(R.id.helpNo);
        address=view.findViewById(R.id.address);
        com_name=view.findViewById(R.id.com_name);
        co_name=view.findViewById(R.id.co_name);
        facebook=view.findViewById(R.id.facebook);

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);
//
//

        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(Request.Method.GET,
                Api.contactUs, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Respose", response.toString());
                Util.cancelPgDialog(dialog);
                try {

                    if (response.getString("status").equalsIgnoreCase("success")){

                        JSONArray jsonArray=response.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            //linerPhone.setText(Html.fromHtml(jsonObject.optString("content")));
                            com_name.setText(jsonObject.optString("company_name"));
                            co_name.setText("C/O "+jsonObject.optString("parent_comp"));
                            address.setText(jsonObject.optString("address"));
                            helpNo.setText(jsonObject.optString("phone"));
                            email.setText(jsonObject.optString("email"));
                            website.setText(jsonObject.optString("website"));

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


        helpNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });


        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), WebViewOpen.class);
                intent.putExtra("link",website.getText().toString());
                startActivity(intent);

            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), WebViewOpen.class);
                intent.putExtra("link","https://www.facebook.com/Bizzcityinfo/");
                startActivity(intent);
            }
        });


////
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               emailSend();
            }
        });

//        skype.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    //Intent sky = new Intent("android.intent.action.CALL_PRIVILEGED");
//                    //the above line tries to create an intent for which the skype app doesn't supply public api
//
//                    Intent sky = new Intent("android.intent.action.VIEW");
//                    sky.setData(Uri.parse("skype:" + "9999940002"));
//                    Log.d("UTILS", "tel:" + "9999940002");
//                    startActivity(sky);
//                } catch (ActivityNotFoundException e) {
//                    Log.e("SKYPE CALL", "Skype failed", e);
//                }
//
//            }
//        });
        return  view;
    }

    private void emailSend() {

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","support@bizzcityinfo.com", null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "BizzCityInfo App");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));


    }

    private void call() {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "01204136767"));
                startActivity(callIntent);
            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "01204136767"));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {ex.printStackTrace();
        }

    }
}




