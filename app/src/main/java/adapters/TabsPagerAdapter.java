package adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import menufragments.NewOrderTab;
import menufragments.ShowOrderTab;
import menufragments.ShowTripSheet;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			
			return new NewOrderTab();
		case 1:
			
			return new ShowOrderTab();
			
		/*case 2:
			
			return new CaseFollowUp();
			
			
		case 3:
			return new CaseHistory();
		*/
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

}
