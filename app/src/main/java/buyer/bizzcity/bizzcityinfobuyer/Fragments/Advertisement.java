package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import buyer.bizzcity.bizzcityinfobuyer.Activites.HomeAct;
import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.JSONParser;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class Advertisement extends Fragment {


    public Advertisement() {
        // Required empty public constructor
    }

    Button Continue;

    TextView price;
    Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_advertisement, container, false);
        Continue=view.findViewById(R.id.Continue);
        price= (TextView) view.findViewById(R.id.price);
        HomeAct.title.setText("Advertise with us");

        new ShowPackage(getActivity()).execute();


        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isAppInstalled = appInstalledOrNot("sntinfotech.bizzcityinfo");

                if(isAppInstalled) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent = getActivity().getPackageManager()
                            .getLaunchIntentForPackage("sntinfotech.bizzcityinfo");
                    startActivity(LaunchIntent);

                    Log.i("dsdfsdfsdfsdf","Application is already installed.");
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store

                    Log.i("dfdsgdgdfgdfg","Application is not currently installed.");


                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=sntinfotech.bizzcityinfo"));
                    startActivity(intent);

                }

            }
        });
        return view;
    }
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    private class ShowPackage extends AsyncTask<String, Void, String> {
        Context context;
        public ShowPackage(Context context) {
            this.context = context;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }


        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","premiunPackage");

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.Login,"GET",map);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("response", ": " + s);
            Util.cancelPgDialog(dialog);
            try {
                final JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {

                        JSONArray jsonArray=jsonObject.getJSONArray("message");


                        JSONObject jsonObject1=jsonArray.getJSONObject(0);

                        price.setText(jsonObject1.optString("actual_price")+" /-");

                    }

                    else {
                        Util.errorDialog(context,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }

}
