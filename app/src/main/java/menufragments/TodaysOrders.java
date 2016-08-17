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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.technowavegroup.ordertrackingsystem.Home;
import com.technowavegroup.ordertrackingsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import adapters.Order;
import adapters.TodayOrderAdapter;
import ip.ServiceIP;



public class TodaysOrders extends Fragment {


    private TodayOrderAdapter adapter;
    private RecyclerView recycler;
    private ArrayList<String> ordername_list,orderquantity_list;
    ArrayList<Order> arrayList ;
    private String nowAsString;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String PassCode = "passCode";

    private SharedPreferences.Editor editor;


    public static final String  TenantID= "tenantID";
    private SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.todaysorders, container, false);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_todays_order);
        ((Home) getActivity()).setSupportActionBar(toolbar);
        ((Home) getActivity()).getSupportActionBar().setTitle("Todays Order");
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();



        recycler = (RecyclerView)  v. findViewById(R.id.main_recycler);

        Date alsoNow = Calendar.getInstance().getTime();
        nowAsString = new SimpleDateFormat("yyyy-MM-dd").format(alsoNow);








        StringRequest stringRequest = new StringRequest(ServiceIP.URL+"OrderDetails/Get?TenantID="+sharedpreferences.getString(TenantID, "")+"&date="+nowAsString+"&ShopID=0",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                        try {
                            JSONObject j_response = new JSONObject(response.toString());


                            JSONArray jsonArray = j_response.getJSONArray("List");
                            ordername_list = new ArrayList<String>();
                            orderquantity_list = new ArrayList<String>();
                            arrayList = new ArrayList<Order>();


                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject single = jsonArray.getJSONObject(i);
                                String shopName = single.getString("ShopName").toString();
                                String itemName = single.getString("ItemName").toString();
                                String quantity=single.getString("ItemQty").toString();
                                String orderno=single.getString("OrderNo").toString();
                               // String orderno=single.getString("ItemName").toString();



                                if(ordername_list.contains(shopName)){

                                }else{
                                    ordername_list.add(shopName);
                                }

                                Order order=new Order();

                                   order.setItemname(itemName);
                                order.setQuantity(quantity);
                                order.setShopname(shopName);
                                order.setOrderno(orderno);
                                arrayList.add(order);





                               // s_list.add(shopName);
                               // shopid_list.add(shopid)

                            }




                            //String sListName = "";

                            //String keyToSearch = "Velocity";



              // AlertDialog.Builder builder=new AlertDialog.Builder(getActivity()) ;
                //builder.setMessage(h1.get("Nexsus Computers"));
                //Dialog dialog=builder.create();
                //dialog.show();



                           adapter = new TodayOrderAdapter(getActivity(),ordername_list,arrayList);
                           adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
                           recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                           recycler.setAdapter(adapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }







                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
























        return  v ;
    }



}
