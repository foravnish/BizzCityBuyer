package buyer.bizzcity.bizzcityinfobuyer.Activites;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;


import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buyer.bizzcity.bizzcityinfobuyer.Fragments.AboutCity;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.Aboutus;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.Advertisement;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.AllEvents;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.ContactUs;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.Emergency;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.Feedback;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.Home;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.LatestNews;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.LatestOffers;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.ListedPage;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.MyFavourate;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.MyNeeds;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.NewOpenings;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.PopularPlace;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.RatingView;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.TermAndCondition;
import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Function;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

  //  public static LinearLayout linerFilter;
    TextView reqirement;
    public static TextView title;
    TextView temp,cityName;
    ImageView imageGPS;

    private Location mylocation;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;

    public static  Double latitude=null,longitude=null;

    Dialog dialog4,dialog;
    List<String> str =new ArrayList<>();
    List<HashMap<String,String>> AllProductsLocation ;

    public  static CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AllProductsLocation = new ArrayList<>();

        dialog=new Dialog(HomeAct.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
       // Util.showPgDialog(dialog);

        //openDialogCity();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView name=(TextView)header.findViewById(R.id.name);
        TextView emailID=(TextView)header.findViewById(R.id.emailID);
        imageView=(CircleImageView)header.findViewById(R.id.imageView);
        temp=(TextView)header.findViewById(R.id.temp);
        cityName=(TextView)header.findViewById(R.id.cityName);

        Menu menu2 = navigationView.getMenu();
        MenuItem menuItem = menu2.findItem(R.id.nav_nearby);
        imageGPS = (ImageView) menuItem.getActionView().findViewById(R.id.imageGPS);

        Log.d("dsfsdfsdfgsdgdfgd",getIntent().getStringExtra("type"));

        if (MyPrefrences.getCityDialog(getApplicationContext())!=true) {
            openDialogCity();
        }
//        openDialogCity();
        displayFirebaseRegId();

        MyPrefrences.setCityDialog(getApplicationContext(), true);

        if (getIntent().getStringExtra("type").equals("1")){
            setUpGClient();

            Fragment fragment = new Home();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).commit();


            imageGPS.setBackgroundResource(R.drawable.gps_on);

        }

        else if (getIntent().getStringExtra("type").equals("fav")){
            Fragment fragment = new MyFavourate();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).commit();

        }

       else if (getIntent().getStringExtra("type").equals("rating")){
            Fragment fragment = new RatingView();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).commit();
        }
        else if (getIntent().getStringExtra("type").equals("0")){
            Fragment fragment = new Home();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).commit();
        }

        //setTitle("Ghaziabad");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        title=findViewById(R.id.title);





//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//        imageView.setImageUrl(MyPrefrences.getImage(getApplicationContext()), imageLoader);


        Log.d("fvdgdfghd",MyPrefrences.getImage(getApplicationContext()));

        try {
            Picasso
                    .with(getApplicationContext())
                    .load(MyPrefrences.getImage(getApplicationContext()))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }




       // cityName=(TextView)header.findViewById(R.id.cityName);

      //  setUpGClient();


        cityName.setText(MyPrefrences.getCityName(getApplicationContext())+" ▼");
        cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeAct.this,SelectCity.class));
            }
        });





//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                "http://35.184.93.23:3000/api/class", null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("sdfsdfsdgfsdgdfg", response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("sdfsdfsdgfsdgdfg", error.toString());
//                    }
//                })
//
//
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//
//                HashMap<String, String> params = new HashMap<String, String>();
//                //params.put("Content-Type", "application/json");
//                String creds = String.format("%s:%s","username","password");
//                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
//                params.put("Authorization", auth);
//                params.put("OAUTH-TOKEN","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YjAyNTJlNDFlZjY3YzBjNWJjNTNhM2MiLCJyb2xlIjoicGFyZW50IiwiaWF0IjoxNTI3MjI2OTUyLCJleHAiOjE1MjcyNDEzNTJ9.8z4CkZkS2h93rFrH3QmTjyA2vXdJFs5KC1KYmc8M5RY");
//                return params;
////
////                Log.e("fdgdfgdfgdfg","Inside getHeaders()");
////                Map<String,String> headers=new HashMap<>();
//////                headers.put("Content-Type","application/x-www-form-urlencoded");
////                // headers.put("Content-Type","application/json");
////                //headers.put("Accept", "application/json");
////                String credentials = MyPrefrences.getToken(getActivity());
////                String auth = "Basic " + credentials;
////
////                headers.put("Authorization", auth);
////                return headers;
//            }
//        }
//
//                ;
//
//// Adding the request to the queue along with a unique string tag
//        AppController.getInstance().addToRequestQueue(jsonObjReq,"headerRequest");







/// wether report begin
        Function.placeIdTask asyncTask =new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

//                cityField.setText(weather_city);
//                updatedField.setText(weather_updatedOn);
//                detailsField.setText(weather_description);
//                currentTemperatureField.setText(weather_temperature);
//                humidity_field.setText("Humidity: "+weather_humidity);
//                pressure_field.setText("Pressure: "+weather_pressure);
//                weatherIcon.setText(Html.fromHtml(weather_iconText));

                Log.d("fgdgdfhgd",weather_temperature);
               temp.setText(weather_temperature);
               //cityName.setText(weather_city);

            }
        });
        asyncTask.execute("28.669156", "77.453758"); //  asyncTask.execute("Latitude", "Longitude")
        /// end wether report
//
//        Menu menu1 = navigationView.getMenu();
//        MenuItem mydesh = menu1.findItem(R.id.nav_adver);
//



        if (MyPrefrences.getUserLogin(getApplicationContext())==true){
            name.setText(MyPrefrences.getUSENAME(getApplicationContext()).substring(0,1).toUpperCase()+MyPrefrences.getUSENAME(getApplicationContext()).substring(1).toLowerCase());
            emailID.setText("+91 "+MyPrefrences.getMobile(getApplicationContext()));

            Menu menu = navigationView.getMenu();
            MenuItem nav_login = menu.findItem(R.id.nav_login);
            nav_login.setTitle("Logout");
        }

        else if (MyPrefrences.getUserLogin(getApplicationContext())==false){
            name.setText("Guest");
            emailID.setText("+91 "+"XXXXXXXXXX");

            Menu menu = navigationView.getMenu();
            MenuItem nav_login = menu.findItem(R.id.nav_login);
            nav_login.setTitle("Login");
        }


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.syncState();
//        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.bci_small);
//        toolbar.setNavigationIcon(drawable);
//        setSupportActionBar(toolbar);




      //  linerFilter=findViewById(R.id.linerFilter);
       // reqirement=findViewById(R.id.reqirement);
     //   HomeAct.linerFilter.setVisibility(View.GONE);




    }

    private void openDialogCity() {

//        Button Yes_action,No_action;
//        final Spinner spiner;
//        TextView heading;
//        dialog4 = new Dialog(HomeAct.this);
//        dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog4.setContentView(R.layout.dialog_city);
//        dialog4.setCancelable(false);
//
//        Yes_action=(Button) dialog4.findViewById(R.id.Yes_action);
//        heading=(TextView)dialog4.findViewById(R.id.heading);
//        spiner=(Spinner)dialog4.findViewById(R.id.spiner);
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                Api.city, null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("Respose", response.toString());
//
//                Util.cancelPgDialog(dialog);
//                try {
//                    // Parsing json object response
//                    // response will be a json object
////                    String name = response.getString("name");
//
//                    if (response.getString("status").equalsIgnoreCase("success")){
//
//                        JSONArray jsonArray=response.getJSONArray("message");
//
////                        if (jsonArray.length()>1){
////                            dialog4.show();
////                        }
////                        else {
////                            dialog4.dismiss();
////                        }
//
//                        dialog4.show();
//
//                        for (int i=0;i<jsonArray.length();i++){
//                            JSONObject jsonObject=jsonArray.getJSONObject(i);
//
//                            HashMap<String,String> map=new HashMap();
//                            map.put("id",jsonObject.optString("id"));
//                            map.put("city",jsonObject.optString("city"));
//                            map.put("state_id",jsonObject.optString("state_id"));
//                            map.put("status",jsonObject.optString("status"));
//
//
//
//                            str.add(jsonObject.optString("city"));
//
//                            ArrayAdapter subcat = new ArrayAdapter(HomeAct.this,android.R.layout.simple_spinner_item,str);
//                            subcat.setDropDownViewResource(R.layout.simple_spinner_item);
//                            spiner.setAdapter(subcat);
//
//                            AllProductsLocation.add(map);
//
//                        }
//
//
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                    Util.cancelPgDialog(dialog);
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("Respose", "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        "Error! Please Connect to the internet", Toast.LENGTH_SHORT).show();
//                // hide the progress dialog
//                Util.cancelPgDialog(dialog);
//
//            }
//        });
//
//        // Adding request to request queue
//        jsonObjReq.setShouldCache(false);
//        AppController.getInstance().addToRequestQueue(jsonObjReq);
//
//
//
//
//        heading.setText("Select City");
//
//        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                title.setText(spiner.getSelectedItem().toString());
////                title.setText(AllProductsLocation.get(i).get("id").toString());
//                MyPrefrences.setCityID(getApplicationContext(), AllProductsLocation.get(i).get("id"));
//                MyPrefrences.setCityName(getApplicationContext(), AllProductsLocation.get(i).get("city"));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
//
//        Yes_action.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog4.dismiss();
//            }
//        });



        startActivity(new Intent(HomeAct.this,SelectCity.class));
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // register GCM registration complete receiver
////        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
////                new IntentFilter(Config.REGISTRATION_COMPLETE));
//
//        // register new push message receiver
//        // by doing this, the activity will be notified each time a new message arrives
////        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
////                new IntentFilter(Config.PUSH_NOTIFICATION));
//
//        // clear the notification area when the app is opened
////        NotificationUtils.clearNotifications(getApplicationContext());
//    }
//
//    @Override
//    protected void onPause() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
//        super.onPause();
//    }



    private synchronized void setUpGClient() {
        try {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, 0, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            googleApiClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.

//MenuInflater inflater = getMenuInflater();
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.home, menu);

        // Get the SearchView and set the searchable configuration
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        // Assumes current activity is the searchable activity
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default



        getMenuInflater().inflate(R.menu.home, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.black));



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();


                Fragment fragment=new ListedPage();
                Bundle bundle=new Bundle();
                bundle.putString("id","");
                bundle.putString("title","");
                bundle.putString("search","yes");
                bundle.putString("keyowd",query);
                bundle.putString("value","");
                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();




        if (id == R.id.action_cal) {

            //Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_need) {
            // Handle the camera action
            Fragment fragment = new MyNeeds();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

        }  else if (id == R.id.nav_profile) {
            // Handle the camera action
//            Fragment fragment = new MyNeeds();
//            FragmentManager manager = getSupportFragmentManager();
//            FragmentTransaction ft = manager.beginTransaction();
//            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            startActivity(new Intent(HomeAct.this,ProfileAct.class));

        } else if (id == R.id.nav_fav) {
            Fragment fragment = new MyFavourate();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        }
            else if (id == R.id.nav_nearby) {

            Intent intent=new Intent(HomeAct.this,HomeAct.class);
            intent.putExtra("type","1");
            startActivity(intent);

           // setUpGClient();
        } else if (id == R.id.nav_rating) {

            Fragment fragment = new RatingView();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        } else if (id == R.id.nav_home) {

            Fragment fragment = new Home();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

 } else if (id == R.id.nav_feedback) {

            Fragment fragment = new Feedback();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

 } else if (id == R.id.nav_bus_login) {



            boolean isAppInstalled = appInstalledOrNot("sntinfotech.bizzcityinfo");

            if(isAppInstalled) {
                //This intent will help you to launch if the package is already installed
                Intent LaunchIntent = getPackageManager()
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


//            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("sntinfotech.bizzcityinfo");
//            startActivity(launchIntent);


    }

        else if (id == R.id.nav_adver) {

            Fragment fragment = new Advertisement();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

        }

        else if (id == R.id.nav_refer) {

//            Intent sendIntent = new Intent();
//            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//            sendIntent.setType("text/plain");
//            startActivity(sendIntent);

            refrelFriend();


        }
        else if (id == R.id.nav_emergency) {

            Fragment fragment = new Emergency();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

        } else if (id == R.id.nav_event) {

            Fragment fragment = new AllEvents();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

        } else if (id == R.id.nav_offers) {

            Fragment fragment = new LatestOffers();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

          } else if (id == R.id.nav_news) {

            Fragment fragment = new LatestNews();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

         } else if (id == R.id.nav_shop) {

            Fragment fragment = new NewOpenings();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

         } else if (id == R.id.nav_place) {

            Fragment fragment = new PopularPlace();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();


        } else if (id == R.id.nav_tnc) {
            Fragment fragment = new TermAndCondition();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();


        } else if (id == R.id.nav_about) {
            Fragment fragment = new Aboutus();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

        } else if (id == R.id.nav_about_city) {
            Fragment fragment = new AboutCity();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();


        } else if (id == R.id.nav_contact) {
            Fragment fragment = new ContactUs();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

        } else if (id == R.id.nav_list) {
//            Fragment fragment = new LatestOffers();
//            FragmentManager manager = getSupportFragmentManager();
//            FragmentTransaction ft = manager.beginTransaction();
//            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

            Intent intent=new Intent(getApplicationContext(), WebViewOpen.class);
            intent.putExtra("link","http://bizzcityinfo.com/register.php");
            startActivity(intent);

        } else if (id == R.id.nav_login) {
            MyPrefrences.resetPrefrences(HomeAct.this);
            Intent intent = new Intent(HomeAct.this, Login.class);
            startActivity(intent);
            finishAffinity();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void refrelFriend() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.referralContent, null, new Response.Listener<JSONObject>() {

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

                          //  HomeAct.title.setText(jsonObject.optString("title"));


                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody =jsonObject.optString("content");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Download BizzCityInfo App");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareBody);
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));



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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkPermissions();
    }

    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(HomeAct.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }else{
            getMyLocation();
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("fdgdfgdfgdfgd","fdfdfd");

    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            latitude=mylocation.getLatitude();
            longitude=mylocation.getLongitude();
//            latitudeTextView.setText("Latitude : "+latitude);
//            longitudeTextView.setText("Longitude : "+longitude);
            Log.d("dfsdgdgdfghdfh1",latitude.toString());
            Log.d("dfsdgdgdfghdfh2",longitude.toString());
            //Or Do whatever you want with your location
        }
        
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(HomeAct.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }

    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(HomeAct.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback() {

                        @Override
                        public void onResult(@NonNull Result result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(HomeAct.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(HomeAct.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied. However, we have no way to fix the
                                    // settings so we won't show the dialog.
                                    //finish();
                                    break;
                            }
                        }

                    });
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        //finish();
                        break;
                }
                break;
        }
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        final String regId = pref.getString("regId", null);

        Log.d("djfsakljf;sldkfsdk", "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)){
            //txtRegId.setText("Firebase Reg Id: " + regId);

            StringRequest postRequest = new StringRequest(Request.Method.POST, Api.storeDeviceToken,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("ResponseToken", response);
                            Util.cancelPgDialog(dialog);
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if (jsonObject.getString("status").equalsIgnoreCase("success")){



                                }
                                else{
                                   // Toast.makeText(getApplicationContext(),jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Toast.makeText(HomeAct.this, "Error! Please connect to the Internet.", Toast.LENGTH_SHORT).show();
                            Util.cancelPgDialog(dialog);
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("deviceToken", regId);

                    Log.d("dfsdsdgfdgdfgdfg",regId);

                    return params;
                }
            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            postRequest.setShouldCache(false);

            AppController.getInstance().addToRequestQueue(postRequest);


        }
        else {
//            txtRegId.setText("Firebase Reg Id is not received yet!");
            Log.d("djfsakljf;sldkfsdk", "Firebase Reg Id is not received yet!");
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


}
