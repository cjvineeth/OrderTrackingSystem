package menufragments;


import android.content.Context;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.technowavegroup.ordertrackingsystem.Home;
import com.technowavegroup.ordertrackingsystem.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import adapters.NewOrderAdapter;
import adapters.ShowOrderAdapter;
import adapters.ViewPagerAdapter;
import database.DBAdapter;

import iterfaces.IFragmentToActivity;
import iterfaces.SwipeListen;

/**
 * Created by technoway on 4/29/2016.
 */




public class NewOrder extends Fragment implements IFragmentToActivity {


    DBAdapter adapter;

    TextView view;
    NewOrderAdapter mAdapter;
    View v;

    private PagerSlidingTabStrip tabs;
    ViewPager pager;
    //ActionBar bar;
    private IFragmentToActivity mCallback;
    private SwipeListen swipeListen;
    private ArrayList<String> items,quantity;
    private ShowOrderAdapter myadapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try {
            swipeListen=(SwipeListen)context;
            mCallback = (IFragmentToActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IFragmentToActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
         v = inflater.inflate(R.layout.neworders, container, false);

        pager= (ViewPager) v.findViewById(R.id.home_pager);
        adapter = ((Home) getActivity()).adapter;
        //tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
         // bar=((Home)getActivity()).getSupportActionBar();






        tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        mAdapter=new NewOrderAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(mAdapter);



        ((Home)getActivity()).onAdapterChange(pager);


        tabs.setIndicatorColor(getActivity().getResources().getColor(R.color.CornflowerBlue));
        tabs.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
       // tabs.setTextColor(getActivity().getResources().getColor(R.color.CornflowerBlue));

        tabs.setIndicatorHeight(5);
        tabs.setTextSize(20);
        tabs.setDividerColor(getActivity().getResources().getColor(R.color.white));


        //tabs.setTabBackground(R.color.darkblue);
       // tabs.setTextColor(getActivity().getResources().getColor(R.color.white));
        tabs.setViewPager(pager);

        tabs.changeTextColorAt(0, getActivity().getResources().getColor(R.color.CornflowerBlue));
        tabs.changeTextColorAt(1, getActivity().getResources().getColor(R.color.Gray));






        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {






                switch (position)
                {
                    case 0:

                        tabs.changeTextColorAt(0, getActivity().getResources().getColor(R.color.CornflowerBlue));
                        tabs.changeTextColorAt(1, getActivity().getResources().getColor(R.color.Gray));
                       break;

                    case 1:


                        items=new ArrayList<String>();

                        quantity=new ArrayList<String>();
                        adapter.open();




                        Cursor c=adapter.getallItemsData();



                        if (c.moveToFirst()){

                            do{





                                items.add(c.getString(2));
                                quantity.add(c.getString(3));







                            }

                            while (c.moveToNext());

                        }





                        adapter.close();





                        tabs.changeTextColorAt(1, getActivity().getResources().getColor(R.color.CornflowerBlue));
                        tabs.changeTextColorAt(0, getActivity().getResources().getColor(R.color.Gray));

                        Fragment fragment= ((NewOrderAdapter)pager.getAdapter()).getFragment(position);


                        RecyclerView recyclerView= (RecyclerView) fragment.getView().findViewById(R.id.recycler_view_showorder);
                        myadapter=new ShowOrderAdapter(getActivity(),items,quantity,adapter);

                        recyclerView.setAdapter(myadapter);

                        //final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                       // ft.detach(fragment);
                        //ft.attach(fragment);
                        //ft.commit();



                      //  tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);

                        //swipeListen.swipe();

                       // mCallback.showToast("second tab");


                        //EventBus bus = EventBus.getDefault();
                        //bus.post(new TextChangedEvent("hello"));
                       // mAdapter=new NewOrderAdapter(getActivity().getSupportFragmentManager(),"123");
                        //pager.setAdapter(mAdapter);
                        //pager.setCurrentItem(1);


                        //Toast.makeText(getActivity(), "page 2", Toast.LENGTH_LONG).show();

                        //onMicClickListner.OnmicClickListner(position, "hello");

                        //onMicClickListner.OnmicClickListner("hello");


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



















        //final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                //.getDisplayMetrics());
        //pager.setPageMargin(pageMargin);

        //tabs.setViewPager(pager);







        // pager=  v.findViewById(R.id.re);










        return  v ;
    }


    @Override
    public void Reload() {

       // Toast.makeText(getActivity(),"Hello",Toast.LENGTH_LONG).show();


        /*  Fragment fragment = ((NewOrderAdapter)pager.getAdapter()).getFragment(1);

        if (fragment != null)
        {
            fragment.onResume();
        }
*/


    }

    @Override
    public void communicateToFragment2() {

    }






    @Override
    public void onResume() {
        super.onResume();


        pager= (ViewPager) v.findViewById(R.id.home_pager);
        mAdapter=new NewOrderAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(mAdapter);

        tabs.changeTextColorAt(0, getActivity().getResources().getColor(R.color.CornflowerBlue));
        tabs.changeTextColorAt(1, getActivity().getResources().getColor(R.color.Gray));


        // pager.setCurrentItem(1);

        //Toast.makeText(getActivity(),"toasted",Toast.LENGTH_LONG).show();
    }
}
