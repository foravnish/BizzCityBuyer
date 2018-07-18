package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import buyer.bizzcity.bizzcityinfobuyer.Activites.HomeAct;
import buyer.bizzcity.bizzcityinfobuyer.Activites.Login;
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
public class Home extends Fragment implements View.OnClickListener {


    public Home() {
        // Required empty public constructor
    }
    ViewPager viewPager,viewPager2;
    CustomPagerAdapter mCustomPagerAdapter;
    CustomPagerAdapter2 mCustomPagerAdapter2;

    int[] mResources = {
            R.drawable.homeimgjpg,
            R.drawable.download1,
            R.drawable.homeimgjpg,

    };
    List<HashMap<String,String>> AllProducts ;
    //    List<HashMap<String,String>> AllEvents ;
    List<Const> AllEvents   = new ArrayList<>();
    List<Const> AllBaner   = new ArrayList<>();


    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<String> alName;
    ArrayList<Integer> alImage;
    RelativeLayout rel1, rel2, rel3, rel4, rel5, rel6, rel7, rel8, rel9, rel10, rel11, rel12, rel13, rel14, rel15, rel16;
    RelativeLayout rels1, rels2, rels3, rels4, rels5, rels6, rels7, rels8, rels9;
    RelativeLayout rels1_1, rels2_2, rels3_3, rels4_4, rels4_5;
    RelativeLayout rels1_14, rels2_24, rels3_34, rels4_44b, rels4_54;
    RelativeLayout rels1_11, rels2_22, rels3_33, rels4_44, rels4_55;
    RelativeLayout rels1_136, rels1_13, rels2_23, rels3_313, rels4_43, rels4_53;
    RelativeLayout rels4_535;
    RelativeLayout rels4_5353, rels4_5253, rels4_5153, rels4_445b3, rels3_353, rels2_253, rels1_153;
    LinearLayout rels4_445b316;
    RelativeLayout relativeLayout1;
    LinearLayout l_boutique, l_cosmetic, l_jewellery, l_garment, l_footwear, l_parlour, rels1_156, rels3_3536, rels2_2536, rels4_445b36;

    Fragment fragment;
    CirclePageIndicator indicator, indicator2;
    TextView seeAll, seeAll2;
    ImageView seeAll5, seeAll56;
    Dialog dialog, dialog4;

    //new
    LinearLayout l_child_wear, l_gifts, l_watch, l_bags, rel_home_furniture, rel_office_furniture;
    LinearLayout rel_furniture_repair, rel_property_agent, rel_interior_designer, l_doors, l_glass_fibre;
    LinearLayout l_home_furnish, l_granite_marble, l_hardware, l_iron, l_plywood, l_bricks;
    LinearLayout l_men_salon,l_mobile_acces,l_grocery_store,l_super_market,l_electrician,l_dry_cleaner,l_taxi_service,l_waterbottle;
    LinearLayout l_clinic,l_hospital,l_fast_food,l_fast_food2,l_tiffin,l_CA,l_insurance,l_consultant,l_money_transfer,l_broker;

    LinearLayout l_allergist,l_ent,l_ayurvedic,l_cardiologist,l_dentist,l_dermatologist,l_dietitian,l_endocrinologist,l_homeopathist,l_eye,l_familydoctor;
    LinearLayout l_gastroenterologist,l_oncologist,l_nephrologist,l_neurologist,l_obstetrician,l_physicaltherapist,l_orthopaedist,l_pathologist;
    LinearLayout l_pediatrician,l_urologist,l_psychiatrist,l_pulmonologist,l_surgeon;
    LinearLayout l_taro_cards,l_vastu_service,l_palmists,l_numerologists,l_astrologists;
    LinearLayout edu1,edu2,edu3,edu4,edu5,edu6,rel_Hotels,water_botels,lay_chemists;
    RelativeLayout rel_job_service,rel_car_learning,rel_dth_service;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 100;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.
    JSONObject jsonObject;
    JSONObject jsonObject2;
    ShimmerTextView orText,orText2,orText5,orText4,orText24,orText22,orText21,orText52,orText241,orText2413,orText2416,head_doctor_new,head_finance,head_real_estate,head_furniture,orText24167,head_daily_needs,orTextEdu,head_hotel;
    ShimmerTextView head_astro;
    Shimmer shimmer;
    List<String > subCat=new ArrayList<>();
    CardView cardView1,eventcard;
    GridView lvExp2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view=inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.slider);
        viewPager2 = (ViewPager) view.findViewById(R.id.slider2);
//        lvExp2 = (GridView) view.findViewById(R.id.lvExp2);

        indicator = (CirclePageIndicator)view.findViewById(R.id.indicat);
        indicator2 = (CirclePageIndicator)view.findViewById(R.id.indicat2);

        orText=view.findViewById(R.id.orText);
        orText2=view.findViewById(R.id.orText2);
        orText5=view.findViewById(R.id.orText5);
        orText4=view.findViewById(R.id.orText4);
        orText52=view.findViewById(R.id.orText52);
        orText241=view.findViewById(R.id.orText241);
        orText2413=view.findViewById(R.id.orText2413);
        orText2416=view.findViewById(R.id.orText2416);
        head_doctor_new=view.findViewById(R.id.head_doctor_new);
        head_finance=view.findViewById(R.id.head_finance);
        head_real_estate=view.findViewById(R.id.head_real_estate);
        head_furniture=view.findViewById(R.id.head_furniture);
        orText24167=view.findViewById(R.id.orText24167);
        head_daily_needs=view.findViewById(R.id.head_daily_needs);
        orTextEdu=view.findViewById(R.id.orTextEdu);
        head_hotel=view.findViewById(R.id.head_hotel);

        orText24=view.findViewById(R.id.orText24);
        orText22=view.findViewById(R.id.orText22);

        orText21=view.findViewById(R.id.orText21);
        cardView1=view.findViewById(R.id.cardView1);
        eventcard=view.findViewById(R.id.eventcard);

        edu1=view.findViewById(R.id.edu1);
        edu2=view.findViewById(R.id.edu2);
        edu3=view.findViewById(R.id.edu3);
        edu4=view.findViewById(R.id.edu4);
        edu5=view.findViewById(R.id.edu5);
        edu6=view.findViewById(R.id.edu6);
        rel_Hotels=view.findViewById(R.id.rel_Hotels);
        water_botels=view.findViewById(R.id.water_botels);
        lay_chemists=view.findViewById(R.id.lay_chemists);

        head_astro = view.findViewById(R.id.head_astro);

        l_taro_cards = view.findViewById(R.id.l_taro_cards);
        l_taro_cards.setOnClickListener(this);
        l_vastu_service = view.findViewById(R.id.l_vastu_service);
        l_vastu_service.setOnClickListener(this);
        l_palmists = view.findViewById(R.id.l_palmists);
        l_palmists.setOnClickListener(this);
        l_numerologists = view.findViewById(R.id.l_numerologists);
        l_numerologists.setOnClickListener(this);
        l_astrologists = view.findViewById(R.id.l_astrologists);
        l_astrologists.setOnClickListener(this);

        edu1.setOnClickListener(this);
        edu2.setOnClickListener(this);
        edu3.setOnClickListener(this);
        edu4.setOnClickListener(this);
        edu5.setOnClickListener(this);
        edu6.setOnClickListener(this);
        rel_Hotels.setOnClickListener(this);
        water_botels.setOnClickListener(this);
        lay_chemists.setOnClickListener(this);


        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        Util.showPgDialog(dialog);

//        if (shimmer != null && shimmer.isAnimating()) {
//            shimmer.cancel();
//        } else {
//            shimmer = new Shimmer();
//            shimmer.start(orText);
//            shimmer.start(orText2);
//            shimmer.start(orText5);
//            shimmer.start(orText4);
//        }


        shimmer = new Shimmer();
        shimmer.start(orText);
        shimmer.start(orText2);
        shimmer.start(orText5);
        shimmer.start(orText4);
        shimmer.start(orText24);
        shimmer.start(orText22);
//        shimmer.start(orText23);
        shimmer.start(orText21);
        shimmer.start(orText52);
        shimmer.start(orText241);
        shimmer.start(orText2413);
        shimmer.start(orText2416);
        shimmer.start(head_doctor_new);
        shimmer.start(head_finance);
        shimmer.start(head_real_estate);
        shimmer.start(head_furniture);
        shimmer.start(orText24167);
        shimmer.start(head_daily_needs);
        shimmer.start(head_astro);
        shimmer.start(orTextEdu);
        shimmer.start(head_hotel);

        //mCustomPagerAdapter2 = new CustomPagerAdapter2(getActivity());


//        viewPager2 = (ViewPager) view .findViewById(R.id.slider2);
//        viewPager2.setAdapter(mCustomPagerAdapter2);
//        viewPager2.setPageTransformer(true, new RotateUpTransformer());



//        AdView adView = (AdView)view. findViewById(R.id.search_ad_view);
//        AdView adView2 = (AdView)view. findViewById(R.id.search_ad_view2);
//        AdView adView3 = (AdView)view. findViewById(R.id.search_ad_view3);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//        adView2.loadAd(adRequest);
//        adView3.loadAd(adRequest);

        rel1 = view.findViewById(R.id.rel1);
        rel2 = view.findViewById(R.id.rel2);
        rel3 = view.findViewById(R.id.rel3);
        rel4 = view.findViewById(R.id.rel4);
        rel5 = view.findViewById(R.id.rel5);
        rel6 = view.findViewById(R.id.rel6);
        rel7 = view.findViewById(R.id.rel7);
        rel8 = view.findViewById(R.id.rel8);
        rel9 = view.findViewById(R.id.rel9);
        rel10 = view.findViewById(R.id.rel10);
        rel11 = view.findViewById(R.id.rel11);
        rel12 = view.findViewById(R.id.rel12);
        rel13 = view.findViewById(R.id.rel13);
        rel14 = view.findViewById(R.id.rel14);
        rel15 = view.findViewById(R.id.rel15);
        rel16 = view.findViewById(R.id.rel16);

        rels1 = view.findViewById(R.id.rels1);
        rels2 = view.findViewById(R.id.rels2);
        rels3 = view.findViewById(R.id.rels3);
        rels4 = view.findViewById(R.id.rels4);
        rels5 = view.findViewById(R.id.rels5);
        rels6 = view.findViewById(R.id.rels6);
        rels7 = view.findViewById(R.id.rels7);
        rels8 = view.findViewById(R.id.rels8);
        rels9 = view.findViewById(R.id.rels9);


        rels1_1 = view.findViewById(R.id.rels1_1);
        rels2_2 = view.findViewById(R.id.rels2_2);
        rels3_3 = view.findViewById(R.id.rels3_3);
        rels4_4 = view.findViewById(R.id.rels4_4);
        rels4_5 = view.findViewById(R.id.rels4_5);


        rels1_14 = view.findViewById(R.id.rels1_14);
        rels2_24 = view.findViewById(R.id.rels2_24);
        rels3_34 = view.findViewById(R.id.rels3_34);
        rels4_44b = view.findViewById(R.id.rels4_44b);
        rels4_54 = view.findViewById(R.id.rels4_54);

        rels1_11 = view.findViewById(R.id.rels1_11);
        rels2_22 = view.findViewById(R.id.rels2_22);
        rels3_33 = view.findViewById(R.id.rels3_33);
        rels4_44 = view.findViewById(R.id.rels4_44);
        rels4_55 = view.findViewById(R.id.rels4_55);

        // rels1_13 = view.findViewById(R.id.rels1_13);
        //   rels3_313 = view.findViewById(R.id.rels3_313);
        //  rels4_53 = view.findViewById(R.id.rels4_53);


        l_boutique = view.findViewById(R.id.l_boutique);
        l_parlour = view.findViewById(R.id.l_parlour);
        l_garment = view.findViewById(R.id.l_garment);
        l_cosmetic = view.findViewById(R.id.l_cosmetic);
        l_jewellery = view.findViewById(R.id.l_jewellery);
        l_footwear = view.findViewById(R.id.l_footwear);
        // rels4_535 = view.findViewById(R.id.rels4_535);

        rels4_5353 = view.findViewById(R.id.rels4_5353);
        rels4_5253 = view.findViewById(R.id.rels4_5253);
        rels4_5153 = view.findViewById(R.id.rels4_5153);
        rels4_445b3 = view.findViewById(R.id.rels4_445b3);
        rels3_353 = view.findViewById(R.id.rels3_353);
        rels2_253 = view.findViewById(R.id.rels2_253);
        rels1_153 = view.findViewById(R.id.rels1_153);


        rels1_156 = view.findViewById(R.id.rels1_156);
        rels2_2536 = view.findViewById(R.id.rels2_2536);
        rels3_3536 = view.findViewById(R.id.rels3_3536);
        rels4_445b36 = view.findViewById(R.id.rels4_445b36);
        rels4_445b316 = view.findViewById(R.id.rels4_445b316);

        relativeLayout1 = view.findViewById(R.id.relativeLayout1);


        seeAll = view.findViewById(R.id.seeAll);
        seeAll2 = view.findViewById(R.id.seeAll2);
        seeAll5 = view.findViewById(R.id.seeAll5);
        seeAll56 = view.findViewById(R.id.seeAll56);


        rel_job_service = view.findViewById(R.id.rel_job_service);
        rel_car_learning = view.findViewById(R.id.rel_car_learning);
        rel_dth_service = view.findViewById(R.id.rel_dth_service);



        //TODO taran START

        l_child_wear = view.findViewById(R.id.l_child_wear);
        l_gifts = view.findViewById(R.id.l_gifts);
        l_watch = view.findViewById(R.id.l_watch);
        l_bags = view.findViewById(R.id.l_bags);
        rel_home_furniture = view.findViewById(R.id.rel_home_furniture);
        rel_office_furniture = view.findViewById(R.id.rel_office_furniture);
        rel_furniture_repair = view.findViewById(R.id.rel_furniture_repair);
        rel_property_agent = view.findViewById(R.id.rel_property_agent);
        rel_interior_designer = view.findViewById(R.id.rel_interior_designer);
        l_doors = view.findViewById(R.id.l_doors);
        l_glass_fibre = view.findViewById(R.id.l_glass_fibre);
        l_home_furnish = view.findViewById(R.id.l_home_furnish);
        l_granite_marble = view.findViewById(R.id.l_granite_marble);
        l_hardware = view.findViewById(R.id.l_hardware);
        l_iron = view.findViewById(R.id.l_iron);
        l_plywood = view.findViewById(R.id.l_plywood);
        l_bricks = view.findViewById(R.id.l_bricks);


        l_men_salon = view.findViewById(R.id.l_men_salon);
        l_mobile_acces = view.findViewById(R.id.l_mobile_acces);
        l_grocery_store = view.findViewById(R.id.l_grocery_store);
        //l_super_market = view.findViewById(R.id.l_super_market);
        l_electrician = view.findViewById(R.id.l_electrician);
        l_dry_cleaner = view.findViewById(R.id.l_dry_cleaner);
        l_taxi_service = view.findViewById(R.id.l_taxi_service);
        l_waterbottle = view.findViewById(R.id.l_waterbottle);
        l_clinic = view.findViewById(R.id.l_clinic);
        l_hospital = view.findViewById(R.id.l_hospital);
        l_fast_food = view.findViewById(R.id.l_fast_food);
        l_fast_food2 = view.findViewById(R.id.l_fast_food2);
        l_tiffin = view.findViewById(R.id.l_tiffin);
        l_CA = view.findViewById(R.id.l_CA);
        l_insurance = view.findViewById(R.id.l_insurance);
        l_consultant = view.findViewById(R.id.l_consultant);
        l_money_transfer = view.findViewById(R.id.l_money_transfer);
        l_broker = view.findViewById(R.id.l_broker);


        l_allergist = view.findViewById(R.id.l_allergist);
        l_ayurvedic = view.findViewById(R.id.l_ayurvedic);
        l_ent = view.findViewById(R.id.l_ent);
        l_cardiologist = view.findViewById(R.id.l_cardiologist);
        l_dentist = view.findViewById(R.id.l_dentist);
        l_dermatologist = view.findViewById(R.id.l_dermatologist);
        l_dietitian = view.findViewById(R.id.l_dietitian);
        l_endocrinologist = view.findViewById(R.id.l_endocrinologist);
        l_homeopathist = view.findViewById(R.id.l_homeopathist);
        l_eye = view.findViewById(R.id.l_eye);
        l_gastroenterologist = view.findViewById(R.id.l_gastroenterologist);
        l_familydoctor = view.findViewById(R.id.l_familydoctor);
        l_oncologist = view.findViewById(R.id.l_oncologist);
        l_nephrologist = view.findViewById(R.id.l_nephrologist);
        l_neurologist = view.findViewById(R.id.l_neurologist);
        l_obstetrician = view.findViewById(R.id.l_obstetrician);
        l_physicaltherapist = view.findViewById(R.id.l_physicaltherapist);
        l_orthopaedist = view.findViewById(R.id.l_orthopaedist);
        l_pathologist = view.findViewById(R.id.l_pathologist);
        l_pediatrician = view.findViewById(R.id.l_pediatrician);
        l_urologist = view.findViewById(R.id.l_urologist);
        l_psychiatrist = view.findViewById(R.id.l_psychiatrist);
        l_pulmonologist = view.findViewById(R.id.l_pulmonologist);
        l_surgeon = view.findViewById(R.id.l_surgeon);




        l_allergist.setOnClickListener(this);
        l_ent.setOnClickListener(this);
        l_cardiologist.setOnClickListener(this);
        l_dentist.setOnClickListener(this);
        l_dermatologist.setOnClickListener(this);
        l_endocrinologist.setOnClickListener(this);
        l_dietitian.setOnClickListener(this);
        l_homeopathist.setOnClickListener(this);
        l_eye.setOnClickListener(this);
        l_gastroenterologist.setOnClickListener(this);
        l_familydoctor.setOnClickListener(this);
        l_oncologist.setOnClickListener(this);
        l_nephrologist.setOnClickListener(this);
        l_neurologist.setOnClickListener(this);
        l_obstetrician.setOnClickListener(this);
        l_physicaltherapist.setOnClickListener(this);
        l_orthopaedist.setOnClickListener(this);
        l_pathologist.setOnClickListener(this);
        l_pediatrician.setOnClickListener(this);
        l_urologist.setOnClickListener(this);
        l_psychiatrist.setOnClickListener(this);
        l_pulmonologist.setOnClickListener(this);
        l_surgeon.setOnClickListener(this);
        l_ayurvedic.setOnClickListener(this);
//




        l_men_salon.setOnClickListener(this);
        l_mobile_acces.setOnClickListener(this);
        l_grocery_store.setOnClickListener(this);
       // l_super_market.setOnClickListener(this);
        l_electrician.setOnClickListener(this);
        l_dry_cleaner.setOnClickListener(this);
        l_taxi_service.setOnClickListener(this);
        l_waterbottle.setOnClickListener(this);
        l_clinic.setOnClickListener(this);
        l_hospital.setOnClickListener(this);
        l_fast_food.setOnClickListener(this);
        l_fast_food2.setOnClickListener(this);
        l_tiffin.setOnClickListener(this);
        l_CA.setOnClickListener(this);
        l_insurance.setOnClickListener(this);
        l_consultant.setOnClickListener(this);
        l_money_transfer.setOnClickListener(this);
        l_broker.setOnClickListener(this);


        l_child_wear.setOnClickListener(this);
        l_gifts.setOnClickListener(this);
        l_watch.setOnClickListener(this);
        l_bags.setOnClickListener(this);
        rel_home_furniture.setOnClickListener(this);
        rel_office_furniture.setOnClickListener(this);
        rel_furniture_repair.setOnClickListener(this);
        rel_property_agent.setOnClickListener(this);
        rel_interior_designer.setOnClickListener(this);
        l_home_furnish.setOnClickListener(this);
        l_glass_fibre.setOnClickListener(this);
        l_doors.setOnClickListener(this);
        l_granite_marble.setOnClickListener(this);
        l_hardware.setOnClickListener(this);
        l_iron.setOnClickListener(this);
        l_plywood.setOnClickListener(this);
        l_bricks.setOnClickListener(this);


        rel_job_service.setOnClickListener(this);
        rel_car_learning.setOnClickListener(this);
        rel_dth_service.setOnClickListener(this);



//TODO taran finish


        HomeAct.title.setText(MyPrefrences.getCityName(getActivity()));

        AllProducts = new ArrayList<>();
//        AllEvents = new ArrayList<>();

        rel1.setOnClickListener(this);
        rel2.setOnClickListener(this);
        rel3.setOnClickListener(this);
        rel4.setOnClickListener(this);
        rel5.setOnClickListener(this);
        rel6.setOnClickListener(this);
        rel7.setOnClickListener(this);
        rel8.setOnClickListener(this);
        rel9.setOnClickListener(this);
        rel10.setOnClickListener(this);
        rel11.setOnClickListener(this);
        rel12.setOnClickListener(this);
        rel13.setOnClickListener(this);
        rel14.setOnClickListener(this);
        rel15.setOnClickListener(this);
        rel16.setOnClickListener(this);

        rels1.setOnClickListener(this);
        rels2.setOnClickListener(this);
        rels3.setOnClickListener(this);
        rels4.setOnClickListener(this);
        rels5.setOnClickListener(this);
        rels6.setOnClickListener(this);
        rels7.setOnClickListener(this);
        rels8.setOnClickListener(this);
        rels9.setOnClickListener(this);




        rels1_14.setOnClickListener(this);
        rels2_24.setOnClickListener(this);
        rels3_34.setOnClickListener(this);
        rels4_44b.setOnClickListener(this);
        rels4_54.setOnClickListener(this);


        rels1_11.setOnClickListener(this);
        rels2_22.setOnClickListener(this);
        rels3_33.setOnClickListener(this);
        rels4_44.setOnClickListener(this);
        rels4_55.setOnClickListener(this);

//        rels1_136.setOnClickListener(this);
//        rels1_13.setOnClickListener(this);
//        rels2_23.setOnClickListener(this);
//        rels3_313.setOnClickListener(this);
//        rels4_43.setOnClickListener(this);
//        rels4_53.setOnClickListener(this);


        rels1_1.setOnClickListener(this);
        rels2_2.setOnClickListener(this);
        rels3_3.setOnClickListener(this);
        rels4_4.setOnClickListener(this);
        rels4_5.setOnClickListener(this);


        l_boutique.setOnClickListener(this);
        l_parlour.setOnClickListener(this);
        l_garment.setOnClickListener(this);
        l_cosmetic.setOnClickListener(this);
        l_jewellery.setOnClickListener(this);
        l_footwear.setOnClickListener(this);
        //   rels4_535.setOnClickListener(this);


        rels4_5353.setOnClickListener(this);
        rels4_5253.setOnClickListener(this);
        rels4_5153.setOnClickListener(this);
        rels4_445b3.setOnClickListener(this);
        rels3_353.setOnClickListener(this);
        rels2_253.setOnClickListener(this);
        rels1_153.setOnClickListener(this);

        rels1_156.setOnClickListener(this);
        rels2_2536.setOnClickListener(this);
        rels3_3536.setOnClickListener(this);
        rels4_445b36.setOnClickListener(this);
        rels4_445b316.setOnClickListener(this);

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment=new LatestNews();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
            }
        });

        seeAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment=new SubCategory();
                replce2("111");
            }
        });

        seeAll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment=new LatestOffers();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();

            }
        });
        seeAll56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Fragment fragment=new GetQuotes();
                Bundle bundle=new Bundle();
                bundle.putString("type","main");
                bundle.putString("id","");
                bundle.putString("comName","");
                bundle.putStringArrayList("array", (ArrayList<String>) subCat );
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
            }
        });

      //  ImageView img1 = (ImageView) view.findViewById(R.id.img1);
//        img1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment=new SubCategory();
//                FragmentManager manager=getFragmentManager();
//                FragmentTransaction ft=manager.beginTransaction();
//                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
//
//
//            }
//        });
//










//        JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(Request.Method.GET,
//                "https://www.bizzduniya.com/Api/index.php/Main/serviceBySubcategory?serviceId=5041", null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("ResposeListing", response.toString());
//
//                // Util.cancelPgDialog(dialog);
////                try {
////
////                    if (response.getString("status").equalsIgnoreCase("success")){
////
//////                        expListView.setVisibility(View.VISIBLE);
//////                        imageNoListing.setVisibility(View.GONE);
////                        JSONArray jsonArray=response.getJSONArray("message");
////                        for (int i=0;i<jsonArray.length();i++){
////                            JSONObject jsonObject=jsonArray.getJSONObject(i);
////
////
//////                            map=new HashMap();
//////                            map.put("id",jsonObject.optString("id"));
//////                            map.put("company_name",jsonObject.optString("company_name"));
//////                            map.put("city",jsonObject.optString("city"));
//////                            map.put("country_code",jsonObject.optString("country_code"));
//////                            map.put("user_type",jsonObject.optString("user_type"));
//////                            map.put("company_type_word",jsonObject.optString("company_type_word"));
//////                            map.put("country_flag",jsonObject.optString("country_flag"));
//////                            map.put("exporter",jsonObject.optString("exporter"));
//////                            map.put("exporterText",jsonObject.optString("exporterText"));
//////                            map.put("premium",jsonObject.optString("premium"));
//////                            map.put("logo",jsonObject.optString("logo"));
//////
//////                            Listing.Adapter adapter=new Listing.Adapter();
//////                            expListView.setAdapter(adapter);
//////                            AllProducts.add(map);
////                        }
////                    }
////                    else{
//////                        expListView.setVisibility(View.GONE);
//////                        imageNoListing.setVisibility(View.VISIBLE);
////                        //  Toast.makeText(getActivity(), "No Record Found...", Toast.LENGTH_SHORT).show();
////                    }
////
////
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                    Toast.makeText(getActivity(),
////                            "Error: " + e.getMessage(),
////                            Toast.LENGTH_LONG).show();
////                    // Util.cancelPgDialog(dialog);
////                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                    Log.d("ResposeError", "Error: " + error.getMessage());
//                Toast.makeText(getActivity(),
//                        "Error! Please Connect to the internet...", Toast.LENGTH_SHORT).show();
//                // hide the progress dialog
//                //   Util.cancelPgDialog(dialog);
//
//            }
//        });
//
//        // Adding request to request queue
//      //  jsonObjReq1.setShouldCache(false);
//        AppController.getInstance().addToRequestQueue(jsonObjReq1);







//        StringRequest postRequest = new StringRequest(Request.Method.GET, "https://www.bizzduniya.com/Api/index.php/Main/serviceBySubcategory?serviceId=5041",
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d("ResposeListing", response);
//                        Util.cancelPgDialog(dialog);
//
//
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Log.d("ResposeError", "Error: " + error.getMessage());
//                        Toast.makeText(getActivity(), "Error! Please connect to the Internet.", Toast.LENGTH_SHORT).show();
//                        Util.cancelPgDialog(dialog);
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("serviceId", "5041");
//             //   params.put("password", password.getText().toString());
//
//                return params;
//            }
//        };
//        postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        postRequest.setShouldCache(false);
//
//        AppController.getInstance().addToRequestQueue(postRequest);








        ////  Begin Baner Api


        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(Request.Method.GET,
                Api.homeBannerImage+"?cityId="+ MyPrefrences.getCityID(getActivity()), null, new Response.Listener<JSONObject>() {

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
//                            viewPager2.setAdapter(mCustomPagerAdapter2);
//                            indicator.setViewPager(viewPager2);
//                            mCustomPagerAdapter2.notifyDataSetChanged();


                            if (jsonObject.optString("cat_id").equalsIgnoreCase("Home")) {
                                AllBaner.add(new Const(jsonObject.optString("id"), jsonObject.optString("cat_id"), jsonObject.optString("subcategory"), jsonObject.optString("image"), jsonObject.optString("meta_keywords"), jsonObject.optString("meta_description"), jsonObject.optString("meta_title"),null,null,null));
                            }

                            mCustomPagerAdapter2=new CustomPagerAdapter2(getActivity());
                            viewPager2.setAdapter(mCustomPagerAdapter2);
                            indicator2.setViewPager(viewPager2);
                            mCustomPagerAdapter2.notifyDataSetChanged();
                            //viewPager2.setPageTransformer(true, new RotateUpTransformer());
                            //mCustomPagerAdapter2.addData(AllBaner);


//                            final Handler handler = new Handler();
//                            final Runnable Update = new Runnable() {
//                                public void run() {
//                                    if (currentPage == AllBaner.size()) {
//                                        currentPage = 0;
//                                    }
//                                    viewPager2.setCurrentItem(currentPage++);
//                                }
//                            };
//
//                            timer = new Timer(); // This will create a new Thread
//                            timer .schedule(new TimerTask() { // task to be scheduled
//
//                                @Override
//                                public void run() {
//                                    handler.post(Update);
//                                }
//                            }, DELAY_MS, PERIOD_MS);





                        }
                        final Handler handler = new Handler();

                        final Runnable update = new Runnable() {

                            public void run() {
                                if (currentPage == AllBaner.size()) {
                                    currentPage = 0;
                                }
                                viewPager2.setCurrentItem(currentPage++);
                            }
                        };
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(update);
                            }
                        }, 100, 5000);


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
                Api.news+"?cityId="+ MyPrefrences.getCityID(getActivity()), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("ResposeEvent", response.toString());
                Util.cancelPgDialog(dialog);
                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");
                    HashMap<String,String> hashMap = null;
                    if (response.getString("status").equalsIgnoreCase("success")){
                        eventcard.setVisibility(View.VISIBLE);
                        AllEvents.clear();
                        JSONArray jsonArray=response.getJSONArray("message");
                        int len=5;
                        if (jsonArray.length()<=5){
                            len=jsonArray.length();
                        }

                        for (int i=0;i<len;i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

//                            hashMap = new HashMap<>();
//                            hashMap.put("id",jsonObject.optString("id"));
//                            hashMap.put("cat_id",jsonObject.optString("cat_id"));
//                            hashMap.put("eventName",jsonObject.optString("eventName"));
//                            hashMap.put("photo",jsonObject.optString("photo"));
//
//                            viewPager.setAdapter(mCustomPagerAdapter);
//                            indicator.setViewPager(viewPager);
//                            mCustomPagerAdapter.notifyDataSetChanged();

                            AllEvents.add(new Const(jsonObject.optString("id"),jsonObject.optString("heading"),null,jsonObject.optString("image_thumb"),null,null,null,null,null,null));
                            mCustomPagerAdapter=new CustomPagerAdapter(getActivity());
                            viewPager.setAdapter(mCustomPagerAdapter);
                            indicator.setViewPager(viewPager);
                            mCustomPagerAdapter.notifyDataSetChanged();

                        }
                    }
                    else{
                        eventcard.setVisibility(View.GONE);
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


        /// Offers
        JsonObjectRequest jsonObjReqOffers = new JsonObjectRequest(Request.Method.GET,
                Api.trendingCategory+"?cityId="+ MyPrefrences.getCityID(getActivity()), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Respose", response.toString());
                Util.cancelPgDialog(dialog);
                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");
                    HashMap<String,String> hashMap = null;
                    if (response.getString("status").equalsIgnoreCase("success")){
                        relativeLayout1.setVisibility(View.VISIBLE);
                        cardView1.setVisibility(View.VISIBLE);
                        AllProducts.clear();
                        JSONArray jsonArray=response.getJSONArray("message");
                        int len=5;
                        if (jsonArray.length()<=5){
                            len=jsonArray.length();
                        }

                        for (int i=0;i<len;i++) {
                            jsonObject = jsonArray.getJSONObject(i);


                            HashMap<String,String> map=new HashMap<>();
                            map.put("id", jsonObject.optString("id"));
                            map.put("short_name", jsonObject.optString("short_name"));
                            map.put("subcat_id", jsonObject.optString("subcat_id"));
                            map.put("subcategory", jsonObject.optString("subcategory"));
                            map.put("image_thumb", jsonObject.optString("image_thumb"));


//                                map.put("heading", jsonObject.optString("heading"));
//                                map.put("description", jsonObject.optString("description"));
//                                map.put("offer_type", jsonObject.optString("offer_type"));
//                                map.put("discount", jsonObject.optString("discount"));
//                                map.put("actual_price", jsonObject.optString("actual_price"));
//                                map.put("offer_price", jsonObject.optString("offer_price"));
//                                map.put("coupon_code", jsonObject.optString("coupon_code"));
//                                map.put("offer_from", jsonObject.optString("offer_from"));
//                                map.put("offer_to", jsonObject.optString("offer_to"));
//                                map.put("image", jsonObject.optString("image"));
//                                map.put("posted_date", jsonObject.optString("posted_date"));
//
//                             jsonObject2=jsonObject.getJSONObject("comapnyDetails");
//
//                            map.put("new_keywords", jsonObject2.optString("new_keywords"));
//                            map.put("company_name", jsonObject2.optString("company_name"));
//                            map.put("address", jsonObject2.optString("address"));
//                            map.put("c1_mobile1", jsonObject2.optString("c1_mobile1"));
//                            map.put("c1_fname", jsonObject2.optString("c1_fname")+" "+jsonObject2.optString("c1_mname")+" "+jsonObject2.optString("c1_lname"));


                            mAdapter = new HLVAdapter(getActivity());

                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                            AllProducts.add(map);


//
////                            hashMap = new HashMap<>();
////                            hashMap.put("id",jsonObject.optString("id"));
////                            hashMap.put("cat_id",jsonObject.optString("cat_id"));
////                            hashMap.put("eventName",jsonObject.optString("eventName"));
////                            hashMap.put("photo",jsonObject.optString("photo"));
////
////                            viewPager.setAdapter(mCustomPagerAdapter);
////                            indicator.setViewPager(viewPager);
////                            mCustomPagerAdapter.notifyDataSetChanged();


                            //AllEvents.add(new Const(jsonObject.optString("id"),jsonObject.optString("eventVen"),jsonObject.optString("eventName"),jsonObject.optString("photo"),jsonObject.optString("eventDate"),jsonObject.optString("meta_description"),jsonObject.optString("orgBy")));

//                            viewPager.setAdapter(mCustomPagerAdapter);
//                            indicator.setViewPager(viewPager);
//                            //mCustomPagerAdapter.notifyDataSetChanged();

                        }
                    }
                    else if (response.getString("status").equalsIgnoreCase("failure")){
                        relativeLayout1.setVisibility(View.GONE);
                        cardView1.setVisibility(View.GONE);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("dfsdfsdfsdfgsd",e.getMessage());
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
        jsonObjReqOffers.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(jsonObjReqOffers);




//        expListView = (GridView) view.findViewById(R.id.horizontal_gridView);
//

//        alName = new ArrayList<>(Arrays.asList("Cheesy...", "Crispy... ", "Fizzy...", "Cool...", "Softy...", "Fruity...", "Fresh...", "Sticky..."));
//        alImage = new ArrayList<>(Arrays.asList(R.drawable.homeimgjpg, R.drawable.download1, R.drawable.homeimgjpg, R.drawable.download1, R.drawable.homeimgjpg, R.drawable.download1, R.drawable.homeimgjpg, R.drawable.download1));

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view2);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);


//        HashMap<String,String> map=new HashMap<>();
//        for (int i=0;i<20;i++) {
//            map.put("name", "Name");
//            mAdapter = new HLVAdapter(getActivity());
//            mRecyclerView.setAdapter(mAdapter);
//            AllProducts.add(map);
//        }



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {


                        Button Yes_action,No_action;
                        TextView heading;
                        dialog4 = new Dialog(getActivity());
                        dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog4.setContentView(R.layout.update_state1);

                        Yes_action=(Button)dialog4.findViewById(R.id.Yes_action);
                        No_action=(Button)dialog4.findViewById(R.id.No_action);
                        heading=(TextView)dialog4.findViewById(R.id.heading);


                        heading.setText("Are you sure you want to exit?");
                        Yes_action.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //System.exit(0);
                                //getActivity().finish();
                                getActivity().finishAffinity();

                            }
                        });

                        No_action.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog4.dismiss();
                            }
                        });
                        dialog4.show();
//

                        //Toast.makeText(getActivity(), "back", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });




        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.rel1:
                fragment=new SubCategory();
                replce("116");
                break;
            case R.id.rel2:
                fragment=new SubCategory();
                replce("117");
                break;
            case R.id.rel3:
                fragment=new SubCategory();
                replce("112");
                break;
            case R.id.rel4:
                fragment=new SubCategory();
                replce("113");
                break;
            case R.id.rel5:
                fragment=new SubCategory();
                replce("106");
                break;
            case R.id.rel6:
                fragment=new SubCategory();
                replce("115");
                break;
            case R.id.rel7:
                fragment=new SubCategory();
                replce("107");
                break;
            case R.id.rel8:
                fragment=new SubCategory();
                replce("114");
                break;
            case R.id.rel9:
                fragment=new SubCategory();
                replce("103");
                break;
            case R.id.rel10:
                fragment=new SubCategory();
                replce("105");
                break;
            case R.id.rel11:
                fragment=new SubCategory();
                replce("109");
                break;
            case R.id.rel12:
                fragment=new SubCategory();
                replce("110");
                break;
            case R.id.rel13:
                fragment=new SubCategory();
                replce("108");
                break;
            case R.id.rel14:
                fragment=new SubCategory();
                replce("104");
                break;
            case R.id.rel15:
                fragment=new SubCategory();
                replce("118");
                break;
            case R.id.rel16:
                fragment=new SubCategory();
                replce("111");
                break;

// TODO NEW
            case R.id.rel_job_service:
                fragment=new ListedPage();
                replce2("292");
                break;
            case R.id.rel_car_learning:
                fragment=new ListedPage();
                replce2("290");
                break;

                case R.id.rel_dth_service:
                fragment=new ListedPage();
                replce2("281");
                break;






                case R.id.rels1:
                fragment=new ListedPage();
                replce2("363");
                break;
            case R.id.rels2:
                fragment=new ListedPage();
                replce2("335");
                break;
            case R.id.rels3:
                fragment=new ListedPage();
                replce2("380");
                break;
            case R.id.rels4:
                fragment=new ListedPage();
                replce2("202");
                break;
            case R.id.rels5:
                fragment=new ListedPage();
                replce2("376");
                break;
            case R.id.rels6:
                fragment=new ListedPage();
                replce2("378");
                break;
            case R.id.rels7:
                fragment=new ListedPage();
                replce2("377");
                break;
            case R.id.rels8:
                fragment=new ListedPage();
                replce2("288");
                break;
            case R.id.rels9:
                fragment=new ListedPage();
                replce2("201");
                break;



            case R.id.rels1_1:
                fragment=new ListedPage();
                replce2("223");
                break;

            case R.id.rels2_2:
                fragment=new ListedPage();
                replce2("224");
                break;

            case R.id.rels3_3:
                fragment=new ListedPage();
                replce2("226");
                break;

            case R.id.rels4_4:
                fragment=new ListedPage();
                replce2("268");
                break;

            case R.id.rels4_5:
                fragment=new ListedPage();
                replce2("225");
                break;


            case R.id.rels1_14:
                fragment=new ListedPage();
                replce2("243");
                break;
            case R.id.rels2_24:
                fragment=new ListedPage();
                replce2("249");
                break;
            case R.id.rels3_34:
                fragment=new ListedPage();
                replce2("247");
                break;
            case R.id.rels4_44b:
                fragment=new ListedPage();
                replce2("244");
                break;
            case R.id.rels4_54:
                fragment=new ListedPage();
                replce2("246");
                break;


            case R.id.rels1_11:
                fragment=new ListedPage();
                replce2("250");
                break;
            case R.id.rels2_22:
                fragment=new ListedPage();
                replce2("254");
                break;
            case R.id.rels3_33:
                fragment=new ListedPage();
                replce2("257");
                break;
            case R.id.rels4_44:
                fragment=new ListedPage();
                replce2("253");
                break;
            case R.id.rels4_55:
                fragment=new ListedPage();
                replce2("252");
                break;


//            case R.id.rels1_136:
//                fragment=new ListedPage();
//                replce2("306");
//                break;
//            case R.id.rels1_13:
//                fragment=new ListedPage();
//                replce2("309");
//                break;
//            case R.id.rels2_23:
//                fragment=new ListedPage();
//                replce2("316");
//                break;
//            case R.id.rels3_313:
//                fragment=new ListedPage();
//                replce2("323");
//                break;
//            case R.id.rels4_43:
//                fragment=new ListedPage();
//                replce2("313");
//                break;
//            case R.id.rels4_53:
//                fragment=new ListedPage();
//                replce2("322");
//                break;



            case R.id.l_boutique:
                fragment=new ListedPage();
                replce2("264");
                break;
            case R.id.l_parlour:
                fragment=new ListedPage();
                replce2("243");
                break;
            case R.id.l_garment:
                fragment=new ListedPage();
                replce2("265");
                break;
            case R.id.l_cosmetic:
                fragment=new ListedPage();
                replce2("266");
                break;
            case R.id.l_jewellery:
                fragment=new ListedPage();
                replce2("273");
                break;
            case R.id.l_footwear:
                fragment=new ListedPage();
                replce2("269");
                break;


            case R.id.rels4_5353:
                fragment=new ListedPage();
                replce2("378");
                break;

            case R.id.rels4_5253:
                fragment=new ListedPage();
                replce2("271");
                break;

            case R.id.rels4_5153:
                fragment=new ListedPage();
                replce2("261");
                break;

            case R.id.rels4_445b3:
                fragment=new ListedPage();
                replce2("286");
                break;

            case R.id.rels3_353:
                fragment=new ListedPage();
                replce2("291");
                break;

            case R.id.rels2_253:
                fragment=new ListedPage();
                replce2("251");
                break;

            case R.id.rels1_153:
                fragment=new ListedPage();
                replce2("280");
                break;


            case R.id.rels1_156:
                fragment=new ListedPage();
                replce2("209");
                break;

            case R.id.rels2_2536:
                fragment=new ListedPage();
                replce2("212");
                break;

            case R.id.rels3_3536:
                fragment=new ListedPage();
                replce2("213");
                break;

            case R.id.rels4_445b36:
                fragment=new ListedPage();
                replce2("214");
                break;

            case R.id.rels4_445b316:
                fragment=new ListedPage();
                replce2("215");
                break;

                /// TODO DOctors

            case R.id.l_allergist:
                fragment=new ListedPage();
                replce2("302");
                break;

            case R.id.l_ayurvedic:
                fragment=new ListedPage();
                replce2("304");
                break;

            case R.id.l_ent:
                fragment=new ListedPage();
                replce2("309");
                break;
            case R.id.l_cardiologist:
                fragment=new ListedPage();
                replce2("305");
                break;

            case R.id.l_dentist:
                fragment=new ListedPage();
                replce2("306");
                break;

            case R.id.l_dermatologist:
                fragment=new ListedPage();
                replce2("307");
                break;

            case R.id.l_dietitian:
                fragment=new ListedPage();
                replce2("308");
                break;

            case R.id.l_endocrinologist:
                fragment=new ListedPage();
                replce2("310");
                break;

            case R.id.l_homeopathist:
                fragment=new ListedPage();
                replce2("313");
                break;

            case R.id.l_eye:
                fragment=new ListedPage();
                replce2("311");
                break;

            case R.id.l_gastroenterologist:
                fragment=new ListedPage();
                replce2("312");
                break;

            case R.id.l_familydoctor:
                fragment=new ListedPage();
                replce2("381");
                break;

            case R.id.l_oncologist:
                fragment=new ListedPage();
                replce2("317");
                break;

            case R.id.l_nephrologist:
                fragment=new ListedPage();
                replce2("314");
                break;

            case R.id.l_neurologist:
                fragment=new ListedPage();
                replce2("315");
                break;

            case R.id.l_obstetrician:
                fragment=new ListedPage();
                replce2("316");
                break;

            case R.id.l_physicaltherapist:
                fragment=new ListedPage();
                replce2("323");
                break;

            case R.id.l_orthopaedist:
                fragment=new ListedPage();
                replce2("320");
                break;

            case R.id.l_pathologist:
                fragment=new ListedPage();
                replce2("321");
                break;

            case R.id.l_pediatrician:
                fragment=new ListedPage();
                replce2("322");
                break;

            case R.id.l_urologist:
                fragment=new ListedPage();
                replce2("327");
                break;

            case R.id.l_psychiatrist:
                fragment=new ListedPage();
                replce2("324");
                break;

            case R.id.l_pulmonologist:
                fragment=new ListedPage();
                replce2("325");
                break;

            case R.id.l_surgeon:
                fragment=new ListedPage();
                replce2("326");
                break;

                ///Todo Daily Needs

            case R.id.l_men_salon:
                fragment=new ListedPage();
                replce2("393");
                break;
            case R.id.l_mobile_acces:
                fragment=new ListedPage();
                replce2("239");
                break;
            case R.id.l_grocery_store:
                fragment=new ListedPage();
                replce2("254");
                break;
//            case R.id.l_super_market:
//                fragment=new ListedPage();
//                replce2("277");
//                break;
            case R.id.l_electrician:
                fragment=new ListedPage();
                replce2("379");
                break;
            case R.id.l_dry_cleaner:
                fragment=new ListedPage();
                replce2("285");
                break;
            case R.id.l_taxi_service:
                fragment=new ListedPage();
                replce2("217");
                break;
            case R.id.l_waterbottle:
                fragment=new ListedPage();
                replce2("252");
                break;
            case R.id.l_clinic:
                fragment=new ListedPage();
                replce2("245");
                break;
            case R.id.l_hospital:
                fragment=new ListedPage();
                replce2("392");
                break;
            case R.id.l_fast_food:
                fragment=new ListedPage();
                replce2("389");
                break;
            case R.id.l_fast_food2:
                fragment=new ListedPage();
                replce2("389");
                break;
            case R.id.l_tiffin:
                fragment=new ListedPage();
                replce2("258");
                break;
            case R.id.l_CA:
                fragment=new ListedPage();
                replce2("373");
                break;
            case R.id.l_insurance:
                fragment=new ListedPage();
                replce2("370");
                break;
            case R.id.l_consultant:
                fragment=new ListedPage();
                replce2("374");
                break;
            case R.id.l_money_transfer:
                fragment=new ListedPage();
                replce2("384");
                break;
            case R.id.l_broker:
                fragment=new ListedPage();
                replce2("372");
                break;

//// TODO Child

            case R.id.l_child_wear:
                fragment=new ListedPage();
                replce2("263");
                break;
            case R.id.l_gifts:
                fragment=new ListedPage();
                replce2("271");
                break;
            case R.id.l_watch:
                fragment=new ListedPage();
                replce2("279");
                break;
            case R.id.l_bags:
                fragment=new ListedPage();
                replce2("274");
                break;
            case R.id.rel_home_furniture:
                fragment=new ListedPage();
                replce2("348");
                break;
            case R.id.rel_office_furniture:
                fragment=new ListedPage();
                replce2("354");
                break;
            case R.id.rel_furniture_repair:
                fragment=new ListedPage();
                replce2("345");
                break;
            case R.id.rel_property_agent:
                fragment=new ListedPage();
                replce2("201");
                break;
            case R.id.rel_interior_designer:
                fragment=new ListedPage();
                replce2("202");
                break;
            case R.id.l_home_furnish:
                fragment=new ListedPage();
                replce2("270");
                break;
            case R.id.l_glass_fibre:
                fragment=new ListedPage();
                replce2("296");
                break;
            case R.id.l_doors:
                fragment=new ListedPage();
                replce2("295");
                break;
            case R.id.l_granite_marble:
                fragment=new ListedPage();
                replce2("297");
                break;
            case R.id.l_hardware:
                fragment=new ListedPage();
                replce2("298");
                break;
            case R.id.l_iron:
                fragment=new ListedPage();
                replce2("299");
                break;
            case R.id.l_plywood:
                fragment=new ListedPage();
                replce2("300");
                break;
            case R.id.l_bricks:
                fragment=new ListedPage();
                replce2("388");
                break;

            case R.id.l_taro_cards:
                fragment=new ListedPage();
                replce2("369");
                break;
            case R.id.l_vastu_service:
                fragment=new ListedPage();
                replce2("368");
                break;
            case R.id.l_palmists:
                fragment=new ListedPage();
                replce2("367");
                break;
            case R.id.l_numerologists:
                fragment=new ListedPage();
                replce2("366");
                break;
            case R.id.l_astrologists:
                fragment=new ListedPage();
                replce2("365");
                break;

// TODO Education

            case R.id.edu1:
                fragment=new ListedPage();
                replce2("229");
                break;
            case R.id.edu2:
                fragment=new ListedPage();
                replce2("231");
                break;
            case R.id.edu3:
                fragment=new ListedPage();
                replce2("397");
                break;
            case R.id.edu4:
                fragment=new ListedPage();
                replce2("225");
                break;
            case R.id.edu5:
                fragment=new ListedPage();
                replce2("228");
                break;
            case R.id.edu6:
                fragment=new ListedPage();
                replce2("224");
                break;
            case R.id.rel_Hotels:
                fragment=new ListedPage();
                replce2("396");
                break;
            case R.id.water_botels:
                fragment=new ListedPage();
                replce2("255");
                break;
             case R.id.lay_chemists:
                fragment=new ListedPage();
                replce2("244");
                break;

        }
    }

    private void replce(String s) {
        FragmentManager manager=getFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("id",s);
        FragmentTransaction ft=manager.beginTransaction();
        fragment.setArguments(bundle);
        ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
    }

    private void replce2(String s) {
        FragmentManager manager=getFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("id",s);
        bundle.putString("title",MyPrefrences.getCityName(getActivity()));
        bundle.putString("search","no");
        bundle.putString("keyowd","");
        bundle.putString("value","");
        FragmentTransaction ft=manager.beginTransaction();
        fragment.setArguments(bundle);
        ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
    }

    public class HLVAdapter extends RecyclerView.Adapter<HLVAdapter.ViewHolder> {

        ArrayList<String> alName;
        ArrayList<Integer> alImage;
        Context context;

        public HLVAdapter(Context context) {
            super();
            this.context = context;
            this.alName = alName;
            this.alImage = alImage;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_notifiction1, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {


            viewHolder.tvSpecies.setText(AllProducts.get(i).get("short_name"));
//            viewHolder.actPrice.setText(AllProducts.get(i).get("offer_price"));
//            viewHolder.desc.setText(AllProducts.get(i).get("description"));
//            viewHolder.oldPrice.setText(AllProducts.get(i).get("actual_price"));


            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewHolder.imgThumbnail.setImageUrl(AllProducts.get(i).get("image_thumb").replace(" ","%20"),imageLoader);

//            viewHolder.actPrice.setPaintFlags(viewHolder.actPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            final Typeface tvFont = Typeface.createFromAsset(getActivity().getAssets(), "muli_bold.ttf");
            //final Typeface tvFont2 = Typeface.createFromAsset(getActivity().getAssets(), "muli.ttf");
            viewHolder.tvSpecies.setTypeface(tvFont);
//            viewHolder.actPrice.setTypeface(tvFont);
//            viewHolder.desc.setTypeface(tvFont2);
//            viewHolder.oldPrice.setTypeface(tvFont2);


            //viewHolder.imgThumbnail.setImageResource(alImage.get(i));

//            viewHolder.setClickListener(new ItemClickListener() {
//                @Override
//                public void onClick(View view, int position, boolean isLongClick) {
//                    if (isLongClick) {
//                        Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
//                        context.startActivity(new Intent(context, MainActivity.class));
//                    } else {
//                        Toast.makeText(context, "#" + position + " - " + alName.get(position), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return AllProducts.size();
        }



        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

            public NetworkImageView imgThumbnail;

            //private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);
                imgThumbnail = (NetworkImageView) itemView.findViewById(R.id.s1_15);
                tvSpecies = (TextView) itemView.findViewById(R.id.tv_species);
                actPrice = (TextView) itemView.findViewById(R.id.actPrice);
                desc = (TextView) itemView.findViewById(R.id.desc);
                oldPrice = (TextView) itemView.findViewById(R.id.oldPrice);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            public TextView tvSpecies,act_price,oldPrice,actPrice,desc;

            @Override
            public void onClick(View view) {

                int position = mRecyclerView.getChildLayoutPosition(view);
                Fragment fragment=new ListedPage();
                FragmentManager manager=getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("id",AllProducts.get(position).get("subcat_id"));
                bundle.putString("title",MyPrefrences.getCityName(getActivity()));
                bundle.putString("search","no");
                bundle.putString("keyowd","");
                bundle.putString("value","");
                FragmentTransaction ft=manager.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();

//                Bundle bundle=new Bundle();
////                    bundle.putString("company_id",AllProducts.get(position).get("company_id"));
////                    bundle.putString("id",AllProducts.get(position).get("company_id"));
//
//                bundle.putString("id",AllProducts.get(position).get("id"));
//                bundle.putString("company_name",AllProducts.get(position).get("company_name"));
//                bundle.putString("address",AllProducts.get(position).get("address"));
//                bundle.putString("c1_mobile1",AllProducts.get(position).get("c1_mobile1"));
//                // bundle.putString("rating",AllProducts.get(position).get("rating"));
//                //bundle.putString("totlauser",AllProducts.get(position).get("totlauser"));
//                bundle.putString("c1_fname",AllProducts.get(position).get("c1_fname"));
//                bundle.putString("new_keywords",AllProducts.get(position).get("new_keywords"));
//
//
////                bundle.putString("company_name",AllProducts.get(i).get("company_name"));
////                bundle.putString("address",AllProducts.get(i).get("address"));
////                bundle.putString("c1_mobile1",AllProducts.get(i).get("c1_mobile1"));
////                bundle.putString("name",AllProducts.get(i).get("c1_fname")+" "+AllProducts.get(i).get("c1_fname")+" "+AllProducts.get(i).get("c1_lname"));
//                FragmentManager manager=getActivity().getSupportFragmentManager();
//                FragmentTransaction ft=manager.beginTransaction();
//                fragment.setArguments(bundle);
//                ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();

            }

            @Override
            public boolean onLongClick(View view) {
                return false;
            }

//            public void setClickListener(ItemClickListener itemClickListener) {
//                this.clickListener = itemClickListener;
//            }

//            @Override
//            public void onClick(View view) {
//                clickListener.onClick(view, getPosition(), false);
//            }

//            @Override
//            public boolean onLongClick(View view) {
//                clickListener.onClick(view, getPosition(), true);
//                return true;
//            }
        }

    }

/// baner


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




            ImageLoader imageLoader = AppController.getInstance().getImageLoader();

            imageView.setImageUrl(AllBaner.get(position).getPhoto().toString().replace(" ","%20"),imageLoader);


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AllBaner.get(position).getOrgby().toString().isEmpty() ) {

                        //  Toast.makeText(getActivity(), "blank", Toast.LENGTH_SHORT).show();
                    }
                    else{
//                        Toast.makeText(getActivity(), AllBaner.get(position).getOrgby().toString(), Toast.LENGTH_SHORT).show();


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

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

    //// end baner

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        TextView headline;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return AllEvents.size();
        }



        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.page_item2, container, false);

            NetworkImageView imageView = (NetworkImageView) itemView.findViewById(R.id.imageView);
            headline = (TextView) itemView.findViewById(R.id.headline);
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();

            imageView.setImageUrl(AllEvents.get(position).getPhoto().toString().replace(" ","%20"),imageLoader);
            headline.setText(Html.fromHtml(AllEvents.get(position).getCatid().toString()));

//            imageView.setImageUrl("https://www.pinerria.com/upload_images/posting/1524295015img11.jpg",imageLoader);

//            Button gotopage=(Button)itemView.findViewById(R.id.goto_page);
//            gotopage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getApplicationContext(),Choose_Login.class));
//
//                }
//            });

            Log.d("dsfdsfsdfsdfsd", String.valueOf(AllEvents.size()));

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment fragment=new LatestNews();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=manager.beginTransaction();
                    ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();

                }
            });

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
        public void addData(@NonNull List<Const> data) {
            AllEvents.addAll(data);
            notifyDataSetChanged();
        }

    }



//    class CustomPagerAdapter extends BaseAdapter {
//
//        Context mContext;
//        LayoutInflater mLayoutInflater;
//        TextView headline;
//
//        public CustomPagerAdapter(Context context) {
//            mContext = context;
//            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
//
//        @Override
//        public int getCount() {
//            return AllEvents.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            View itemView = mLayoutInflater.inflate(R.layout.page_item2, viewGroup, false);
//
//            NetworkImageView imageView = (NetworkImageView) itemView.findViewById(R.id.imageView);
//            headline = (TextView) itemView.findViewById(R.id.headline);
//            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//
//            imageView.setImageUrl(AllEvents.get(i).getPhoto().toString().replace(" ","%20"),imageLoader);
//            headline.setText(Html.fromHtml(AllEvents.get(i).getCatid().toString()));
//
////            imageView.setImageUrl("https://www.pinerria.com/upload_images/posting/1524295015img11.jpg",imageLoader);
//
////            Button gotopage=(Button)itemView.findViewById(R.id.goto_page);
////            gotopage.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    startActivity(new Intent(getApplicationContext(),Choose_Login.class));
////
////                }
////            });
//
//            Log.d("dsfdsfsdfsdfsd", String.valueOf(AllEvents.size()));
//
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Fragment fragment=new LatestNews();
//                    FragmentManager manager=getActivity().getSupportFragmentManager();
//                    FragmentTransaction ft=manager.beginTransaction();
//                    ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
//
//                }
//            });
//
//
//
//            return itemView;
//
//        }
//
//
//
//
////        public void addData(@NonNull List<Const> data) {
////            AllEvents.addAll(data);
////            notifyDataSetChanged();
////        }
//
//    }




}
