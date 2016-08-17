package adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import menufragments.NewOrderTab;
import menufragments.ShowOrderTab;


public class NewOrderAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"New Order", "Show Order"};



    private final HashMap<Integer, String> mFragmentTags;
    private final FragmentManager mFragmentManager;

    private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray<>();
    private ArrayList<String> mTabHeader;

    public NewOrderAdapter(FragmentManager fm) {
        super(fm);
        mFragmentTags = new HashMap<Integer,String>();
        mFragmentManager=fm;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                NewOrderTab tab1 = new NewOrderTab();
                return tab1;
            case 1:
                ShowOrderTab tab2 = new ShowOrderTab();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }







    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment) {
            // record the fragment tag here.
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position, tag);
        }
        return obj;




    }



    public Fragment getFragment(int position) {
        String tag = mFragmentTags.get(position);
        if (tag == null)
            return null;
        return mFragmentManager.findFragmentByTag(tag);
    }


    /*@Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        instantiatedFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }*/

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        instantiatedFragments.remove(position);
        super.destroyItem(container, position, object);
    }

   /* @Nullable
    public Fragment getFragment(final int position) {
        final WeakReference<Fragment> wr = instantiatedFragments.get(position);
        if (wr != null) {
            return wr.get();
        } else {
            return null;
        }
    }*/

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}