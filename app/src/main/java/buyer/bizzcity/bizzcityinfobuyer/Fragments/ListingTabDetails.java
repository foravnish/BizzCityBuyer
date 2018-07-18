package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import buyer.bizzcity.bizzcityinfobuyer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListingTabDetails extends Fragment {


    public ListingTabDetails() {
        // Required empty public constructor
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.detail,
            R.drawable.um_offer
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_listing_tab_details, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        Log.d("fgdfgdfhdghfg",getArguments().getString("id"));

        for (int i = 0; i < 1; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout, tabLayout, false);
            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setCompoundDrawablesWithIntrinsicBounds(tabIcons[i], 0, 0, 0);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);
// tab.select();
        }

        TextView tv=(TextView)LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab,null);
        tv.setCompoundDrawablesWithIntrinsicBounds(tabIcons[1], 0, 0, 0);
//// tv.setTypeface(Typeface);
        tabLayout.getTabAt(1).setCustomView(tv);

        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        //        adapter.addFrag(new DeshAll(), "All"+"("+jsonCount.optString("totalAdv")+")");
//        adapter.addFrag(new Listing(),"LISTING");


            Log.d("fdgdgdfgdfgdf","true");
            adapter.addFrag(ListingDetails.NewInstance(getArguments().getString("id").toString(), getArguments().getString("company_name").toString(), getArguments().getString("address").toString(), getArguments().getString("c1_mobile1").toString(), getArguments().getString("rating").toString(), getArguments().getString("totlauser").toString(), getArguments().getString("name").toString(),getArguments().getString("keywords"),getArguments().getString("locationName"),getArguments().getString("liking"),getArguments().getString("std_code"),getArguments().getString("pincode"),getArguments().getString("companyLogo"),   getArguments().getString("payment_mode"),getArguments().getString("closing_time"),getArguments().getString("opening_time"),getArguments().getString("closing_time2"),getArguments().getString("opening_time2"),getArguments().getString("min_order_amnt"),getArguments().getString("min_order_qty"),getArguments().getString("closing_days"),getArguments().getString("latitude"),getArguments().getString("longitude"),getArguments().getString("status"),getArguments().getString("jsonArray2")), "DETAILS");
            adapter.addFrag(Offers2.NewInstance(getArguments().getString("id").toString(),getArguments().getString("company_name").toString(), getArguments().getString("address").toString()), "OFFERS");


        viewPager.setAdapter(adapter);

    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }


    }

}
