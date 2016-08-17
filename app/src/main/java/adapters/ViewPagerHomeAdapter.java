package adapters;

import menufragments.NewOrderTab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerHomeAdapter extends FragmentPagerAdapter {

    public ViewPagerHomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

       /* switch (index) {
            case 0:
                // Top Rated fragment activity
                return new NewsFragment();
            case 1:
                // Games fragment activity

                return new NewOrderTab();




        }*/

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}
