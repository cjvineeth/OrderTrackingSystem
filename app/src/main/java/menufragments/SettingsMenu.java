package menufragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technowavegroup.ordertrackingsystem.Home;
import com.technowavegroup.ordertrackingsystem.R;

import java.util.ArrayList;

import adapters.SettingsAdapter;
import adapters.ShowOrderAdapter;

/**
 * Created by technoway on 4/29/2016.
 */
public class SettingsMenu extends Fragment {


    private RecyclerView recyclerView;
    private SettingsAdapter sadapter;
    ArrayList<String> names;
    ArrayList<Integer>  icons;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String PassCode = "passCode";
    public static final String ShopID = "shopid";
    public static final String ShopName = "shopname";

    public static final String AddClick= "addclick";

    public static final String TenantID = "tenantID";
    SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.settingsmenu, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_settings);
        ((Home) getActivity()).setSupportActionBar(toolbar);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        ///editor = sharedpreferences.edit();
        ((Home) getActivity()).getSupportActionBar().setTitle("Settings");
        names=new ArrayList<String>();
        icons=new ArrayList<Integer>();
        icons.add(R.mipmap.logoutred);
        names.add("Logout");


        recyclerView= (RecyclerView)v. findViewById(R.id.recycler_view_settings);
        sadapter=new SettingsAdapter(getActivity(),names,icons,sharedpreferences );
        recyclerView.setAdapter(sadapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return  v ;
    }
}
