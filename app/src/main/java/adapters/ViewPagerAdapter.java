package adapters;

import menufragments.NearByCustomers;
import menufragments.NewOrder;
import menufragments.SettingsMenu;
import menufragments.ShowTripSheet;
import menufragments.TodaysOrders;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;

public class ViewPagerAdapter extends FragmentPagerAdapter {


	private final HashMap<Integer, String>   mFragmentTags;
	private final FragmentManager mFragmentManager;

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
		mFragmentTags = new HashMap<Integer,String>();
		mFragmentManager=fm;
		
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new NewOrder();
		case 1:
			// Games fragment activity
			return new NearByCustomers();


		case 2:
			return new ShowTripSheet();


			case 3:

				return new TodaysOrders();


			case 4:

				return new SettingsMenu();


		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 5;
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


}
