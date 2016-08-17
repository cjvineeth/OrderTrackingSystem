package menufragments;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.technowavegroup.ordertrackingsystem.Home;
import com.technowavegroup.ordertrackingsystem.R;


/**
 * Created by technoway on 4/29/2016.
 */
public class ShowTripSheet extends Fragment {

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        v = inflater.inflate(R.layout.showtripsheet, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_show_trip);
        ((Home) getActivity()).setSupportActionBar(toolbar);
        ((Home) getActivity()).getSupportActionBar().setTitle("Show Trip Sheet");
        //ImageView img= (ImageView) v.findViewById(R.id.test_img);


        return  v ;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();


        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(this).commit();
        //FragmentManager fm = getActivity().getSupportFragmentManager();
        //Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        //FragmentTransaction ft = fm.beginTransaction();
        ///ft.remove(fragment);
       // ft.commit();


    }


    @Override
    public void onPause() {
        super.onPause();


        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }
}
