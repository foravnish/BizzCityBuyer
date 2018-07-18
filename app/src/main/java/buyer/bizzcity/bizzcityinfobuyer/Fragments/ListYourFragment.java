package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import buyer.bizzcity.bizzcityinfobuyer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListYourFragment extends Fragment {


    public ListYourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_list_your, container, false);
        return view;
    }

}
