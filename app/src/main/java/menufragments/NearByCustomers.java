package menufragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technowavegroup.ordertrackingsystem.Home;
import com.technowavegroup.ordertrackingsystem.R;

import adapters.ViewPagerAdapter;
import adapters.ViewPagerHomeAdapter;

/**
 * Created by technoway on 4/29/2016.
 */
public class NearByCustomers extends Fragment {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentTabHost tabHost;
    ViewPagerHomeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.nearbycustumers, container, false);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_nearby);
        ((Home) getActivity()).setSupportActionBar(toolbar);
        ((Home) getActivity()).getSupportActionBar().setTitle("Near By Customers");
        return  v ;
    }



    }
