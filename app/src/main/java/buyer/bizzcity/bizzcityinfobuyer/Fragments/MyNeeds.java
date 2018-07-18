package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
public class MyNeeds extends Fragment {


    public MyNeeds() {
        // Required empty public constructor
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.needs,
            R.drawable.um_quote
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_needs, container, false);


        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        HomeAct.title.setText("My Needs");


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
        adapter.addFrag(new Needs(),"My Needs");
        adapter.addFrag(new Enquiries(),"Enquiries");


        viewPager.setAdapter(adapter);
        //setCustomFont();

    }
//    public void setCustomFont() {
//
//        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
//        int tabsCount = vg.getChildCount();
//
//        for (int j = 0; j < tabsCount; j++) {
//            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
//
//            int tabChildsCount = vgTab.getChildCount();
//
//            for (int i = 0; i < tabChildsCount; i++) {
//                View tabViewChild = vgTab.getChildAt(i);
//                if (tabViewChild instanceof TextView) {
//                    //Put your font in assests folder
//                    //assign name of the font here (Must be case sensitive)
//                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font1.xml"));
//                }
//            }
//        }
//    }


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
