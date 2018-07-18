package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetail extends Fragment {


    public EventDetail() {
        // Required empty public constructor
    }


   NetworkImageView bannerpic;
    TextView eventname,addresstv,datetv,about,orgBy,orgAdd;
    Button btnCall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_event_detail, container, false);

        bannerpic=view.findViewById(R.id.bannerpic);
        eventname=view.findViewById(R.id.eventname);
        addresstv=view.findViewById(R.id.addresstv);
        datetv=view.findViewById(R.id.datetv);
        about=view.findViewById(R.id.about);
        orgBy=view.findViewById(R.id.orgBy);
        orgAdd=view.findViewById(R.id.orgAdd);
        btnCall=view.findViewById(R.id.btnCall);

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        bannerpic.setImageUrl(getArguments().getString("photo").replace(" ","%20"),imageLoader);

        eventname.setText(getArguments().getString("eventName"));
        addresstv.setText(getArguments().getString("eventVen"));
        datetv.setText(getArguments().getString("eventDate")+"  -  "+getArguments().getString("eventDateTo"));
        about.setText(getArguments().getString("meta_description"));
        orgBy.setText(getArguments().getString("orgBy"));
        orgAdd.setText(getArguments().getString("orgAddress"));



        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        callIntent.setData(Uri.parse("tel:" + getArguments().getString("orgMobile")));
                        startActivity(callIntent);
                    }
                    else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + getArguments().getString("orgMobile")));
                        startActivity(callIntent);
                    }
                }
                catch (Exception ex)
                {ex.printStackTrace();
                }
            }
        });

        return view;
    }

}
