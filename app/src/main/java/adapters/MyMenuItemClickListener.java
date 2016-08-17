package adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.technowavegroup.ordertrackingsystem.R;

import database.DBAdapter;
import iterfaces.AddItem;
import iterfaces.IFragmentToActivity;

/**
 * Created by vineeth on 6/16/2016.
 */
public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    TextView Quantity;
    TextView Tittle,itemid,uom;
    Context mContext;

    DBAdapter adapter;
    String addclick;


    private OnmicClickListner onMicClickListner;
    private String datathere="false";
    IFragmentToActivity fragmentToActivity;
    SharedPreferences sharedPreferences;
    private SharedPreferences.Editor  editor;
    public static final String Name = "nameKey";
    public static final String PassCode = "passCode";
    public static final String ShopID = "shopid";
    public static final String AddClick= "addclick";

    public static final String TenantID = "tenantID";

    public MyMenuItemClickListener(){

    }



    public void setmicListener(OnmicClickListner listener) {
        onMicClickListner = listener;
    }

    public MyMenuItemClickListener(TextView quantity,TextView tittle,Context mContext, DBAdapter adapter, IFragmentToActivity fragmentToActivity,String addclick,SharedPreferences sharedPreferences,TextView itemid,TextView uom) {
        Quantity=quantity;
        Tittle=tittle;
        this.mContext=mContext;
        this.adapter=adapter;
        this.fragmentToActivity=fragmentToActivity;
        this.sharedPreferences=sharedPreferences;
        this.itemid=itemid;
        this.uom=uom;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {



            case R.id.action_add_favourite:



                if(uom.getText().toString().equals("1001")){


                    Quantity.performClick();
                }

                else {


                    editor = sharedPreferences.edit();

                    editor.putString(AddClick, "true");
                    editor.commit();


                    adapter.open();


                    Cursor c = adapter.getallItemsData();

                    String oldquantity = null;
                    String ids = null;
                    if (c.moveToFirst()) {

                        do {
                            if (c.getString(2).equals(Tittle.getText().toString())) {
                                datathere = "true";
                                ids = c.getString(0);
                                oldquantity = c.getString(3);


                            }


                        } while (c.moveToNext());


                    }


                    if (datathere.equals("true")) {

                        String sum = Integer.toString(Integer.parseInt(oldquantity) + Integer.parseInt(Quantity.getText().toString()));

                        // boolean data = adapter.updateData(sum, Integer.parseInt(ids));


                        boolean data = adapter.updateData(Quantity.getText().toString(), Integer.parseInt(ids));
                        datathere = "false";

                    } else {

                        long id = adapter.addItemData("Velocity", Tittle.getText().toString(), Quantity.getText().toString(), uom.getText().toString(), itemid.getText().toString());

                        // Toast.makeText(getActivity(), "Inserted", Toast.LENGTH_LONG).show();


                    }


                    adapter.close();

                    //  fragmentToActivity.Reload();
                }

                return true;

            default:
        }
        return false;
    }



    public interface OnmicClickListner {

        public abstract void OnmicClickListner();


    }

}