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

import buyer.bizzcity.bizzcityinfobuyer.Activites.HomeAct;
import buyer.bizzcity.bizzcityinfobuyer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListedPage extends Fragment {


    public ListedPage() {
        // Required empty public constructor
    }


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.um_listing,
            R.drawable.um_offer,
            R.drawable.um_quote
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_listed_page, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

       // HomeAct.linerFilter.setVisibility(View.VISIBLE);

        for (int i = 0; i < 2; i++) {
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
        tv.setCompoundDrawablesWithIntrinsicBounds(tabIcons[2], 0, 0, 0);
//// tv.setTypeface(Typeface);
        tabLayout.getTabAt(2).setCustomView(tv);


        Log.d("dfsdfsdgs",getArguments().getString("id"));
        Log.d("dfsdffggfgsdgs",getArguments().getString("search"));
        return view;

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        //        adapter.addFrag(new DeshAll(), "All"+"("+jsonCount.optString("totalAdv")+")");
//        adapter.addFrag(new Listing(),"LISTING");

        if (getArguments().getString("search").toString().equalsIgnoreCase("yes")){
            Log.d("fdgdgdfgdfgdf","true");
            adapter.addFrag(ListingSearch.NewInstance(getArguments().getString("keyowd").toString(), getArguments().getString("title").toString(),"yes"), "Listings");
            adapter.addFrag(Offers.NewInstance(getArguments().getString("keyowd").toString(),"yes",getArguments().getString("id").toString()), "Offers");
            adapter.addFrag(GetBestQuote.NewInstance(getArguments().getString("keyowd").toString(), getArguments().getString("title").toString(),"yes",getArguments().getString("id").toString()), "Enquiry");
        }
        else if (getArguments().getString("search").toString().equalsIgnoreCase("no")) {
            Log.d("fdgdgdfgdfgdf","false");
            Log.d("fdgfgfgdgdfgdfgdf",getArguments().getString("value").toString());
            adapter.addFrag(Listing.NewInstance(getArguments().getString("id").toString(), getArguments().getString("title").toString(),"no",getArguments().getString("value").toString()), "Listing");
            adapter.addFrag(Offers.NewInstance(getArguments().getString("id").toString(),"no",getArguments().getString("id").toString()), "Offers");
            adapter.addFrag(GetBestQuote.NewInstance(getArguments().getString("id").toString(), getArguments().getString("title").toString(),"no",getArguments().getString("id").toString()), "Enquiry");
        }
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
