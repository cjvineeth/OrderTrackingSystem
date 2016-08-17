package menufragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionMenu;
import com.technowavegroup.ordertrackingsystem.Home;

import com.technowavegroup.ordertrackingsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import adapters.ShowOrderAdapter;
import database.DBAdapter;
import ip.ServiceIP;
import servicehandlers.ServiceHandler;

/**
 * Created by technoway on 4/29/2016.
 */
public class ShowOrderTab extends Fragment {


    TextView textView;
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String User_id= "User_id";
    private int mPage;
    private TimePicker start_piPicker;
    private SimpleDateFormat dateFormatter;
    private Spinner accSpinner,activitySpinner;
    String data[]={"Bisucut","MilkyBar","CupCakes"};
    ArrayAdapter<String> ap;
    AppCompatSpinner spinner;
    private String  muserid;
    ArrayList<String> list;
    DBAdapter adapter;
    ImageButton add;
    Button save_order;
    AutoCompleteTextView shop;
    EditText qty_etxt;
    public static final String ShopID = "shopid";
    View view;
    TableLayout table_layout;
    private TableRow row;
    TextView additems;
    private EditText tv;
    private EditText itemview;
    FloatingActionButton fab,save;

    private SharedPreferences.Editor editor;
    //TextView myview;

    List<CheckBox> boxes;
    private EditText textEdit;
    private float textzize=15;
    private LayoutInflater inflaternew;
    private ProgressDialog pdialoge;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String PassCode = "passCode";
    public static final String  UserName= "userName";

    public static final String  TenantID= "tenantID";
    private SharedPreferences sharedpreferences;
    FloatingActionMenu menu;
    private ArrayList<String> items,quantity;
    private RecyclerView recyclerView;
    private ShowOrderAdapter sadapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub


        adapter = ((Home) getActivity()).adapter;
        view= inflater.inflate(R.layout.showordertab, container, false);

          fab= (FloatingActionButton) view.findViewById(R.id.fl_btn);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_showord);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        ((Home) getActivity()).setSupportActionBar(toolbar);
        items=new ArrayList<String>();
        quantity=new ArrayList<String>();

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        recyclerView= (RecyclerView)view. findViewById(R.id.recycler_view_showorder);





        Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.jump_to_down);
        fab.startAnimation(animation1);

fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        new SaveData().execute();

    }
});

        sadapter=new ShowOrderAdapter(getActivity(),items,quantity,adapter);
        recyclerView.setAdapter(sadapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




        /*//myview= (TextView) view.findViewById(R.id.my_text_view);
        additems= (TextView) view.findViewById(R.id.additem);
        table_layout = (TableLayout) view.findViewById(R.id.tableLayout1);

        menu= (FloatingActionMenu) view.findViewById(R.id.menu_blue);
        fab= (FloatingActionButton) view.findViewById(R.id.fab12);
        save= (FloatingActionButton) view.findViewById(R.id.fab32);
             boxes=new ArrayList<CheckBox>();
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_showord);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        ((Home) getActivity()).setSupportActionBar(toolbar);
        ((Home)getActivity()).fabButton(menu);


        //Toast.makeText(getActivity(), "page 2", Toast.LENGTH_LONG).show();
        //Button btn= (Button) view.findViewById(R.id.my_btn);





        BuildTableHeader(1, 4);

table_layout.setVisibility(View.GONE);



        adapter.open();



        Cursor mcursor = adapter.CheckTableisEmpty();
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0){

   additems.setVisibility(View.GONE);
            table_layout.setVisibility(View.VISIBLE);
            Cursor c=adapter.getallItemsData();
            if(c.moveToFirst()){
                do{
                    BuildTable(1,3,c.getString(2),c.getString(3),c.getString(0));
                }while (c.moveToNext());
            }


        }
//leave
        else{

        }
//populate table


//


        adapter.close();




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SaveHeader().execute();


               //new SaveData().execute();




             // list_pling=new ArrayList<String>();



/*
                adapter.open();

                Cursor c=adapter.getallItemsData();


                if (c.moveToFirst()){

                    do{


                        //ServiceHandler handler=new ServiceHandler();



                        /*object.put("OrderNo",c.getString(0));
                        object.put("OrderDate","05/24/2016");
                        object.put("TenantID",sharedpreferences.getString(TenantID,""));
                        object.put("ItemID",c.getString(5));
                        object.put("ItemQty",c.getString(3));
                        object.put("ItemUOM",c.getString(4));
                        object.put( "ItemBarcode","10101011");
                        list_json.add(object);*/


                       // list_pling.add(c.getString(0));







                   // }

                    //while (c.moveToNext());

               // }





                //adapter.close();






                //Toast.makeText(getActivity(),list_pling.get(1),Toast.LENGTH_LONG).show();







           /* }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (boxes.size() > 0) {


                    if (boxes.get(0).getVisibility() == View.GONE) {
                        for (int i = 0; i < boxes.size(); i++) {

                            boxes.get(i).setVisibility(View.VISIBLE);
                            textEdit.setVisibility(View.VISIBLE);








                        }
                    } else {





                        for (int i = 0; i < boxes.size(); i++) {

                            if (boxes.get(i).isChecked()) {
                                //Toast.makeText(getActivity(),Integer.toString(i)+" is Checked",Toast.LENGTH_LONG).show();


                                TableRow parent = (TableRow) boxes.get(i).getParent();

                                int parentint = table_layout.getChildCount();

                                EditText textviewnew = (EditText) parent.getChildAt(1);
                               // itemview = (EditText) parent.getChildAt(1);


                                //Toast.makeText(getActivity(),Integer.toString(parentint),Toast.LENGTH_LONG).show();


                                if (parentint <= 2) {
                                    table_layout.setVisibility(View.GONE);
                                    additems.setVisibility(View.VISIBLE);
                                    adapter.open();
                                    adapter.deleteAlltype();
                                    adapter.close();
                                }

                                adapter.open();

                                boolean value = adapter.deleteRow(textviewnew.getText().toString());


                                if (value = true) {


                                    //    Toast.makeText(getActivity(),"deleted",Toast.LENGTH_LONG).show();

                                }


                                adapter.close();


                                //String text = textview.getText().toString;
                                //text parent.getChildAt(2)
                                table_layout.removeView(parent);


                            }

                            boxes.get(i).setVisibility(View.GONE);
                            textEdit.setVisibility(View.GONE);

                        }




                    }


                }


            }
        });


   /* table_layout.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {




            EditText et = (EditText) row.getChildAt(1);
            String text = et.getText().toString();

            Toast.makeText(getActivity(),"hello",Toast.LENGTH_LONG).show();


            return true;
        }
    });*/
            /*btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BuildTable(1, 3);
                }
            });*/

        // final SortableCarTableView carTableView = (SortableCarTableView)view. findViewById(R.id.tableView);


        //List<Item> items=new ArrayList<>();
        ///Item item=new Item();
        //item.setName("hello");
        //item.setPrice("10");
        //item.setQuanity("10");
        //items.add(item);

        //carTableView.setDataAdapter(new CarTableDataAdapter(getActivity(),items));
        //carTableView.addDataClickListener(new CarClickListener());


    return view;
    }






    public class SaveData extends AsyncTask<Void,Void,Void>{

        JSONObject object;
        JSONArray array;
        List<JSONObject> list_json;
        String jstring;
        String nowAsString;

        ProgressDialog   pdialoge;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdialoge=new ProgressDialog(getActivity());
            pdialoge.setMessage("Saving Item Orders......");
            pdialoge.show();
            array=new JSONArray();
            list_json=new ArrayList<>();

        }

        @Override
        protected Void doInBackground(Void... params) {




           try {

                adapter.open();

                Cursor c=adapter.getallItemsData();


                if (c.moveToFirst()){

                    do{




                        object=new JSONObject();
                        // Date now = new Date();
                        Date alsoNow = Calendar.getInstance().getTime();
                        nowAsString = new SimpleDateFormat("MM/dd/yyyy").format(alsoNow);

                        object.put("OrderNo",c.getString(0));
                        object.put("OrderDate",nowAsString);
                        object.put("TenantID",sharedpreferences.getString(TenantID, ""));
                        object.put("ItemID",c.getString(5));
                        object.put("ItemQty",c.getString(3));
                        object.put("ItemUOM",c.getString(4));
                        object.put("ItemBarcode", "10101011");
                        object.put("ShopID",sharedpreferences.getString(ShopID,""));
                        object.put("PONo","PO100");
                        object.put("CreatedBy",sharedpreferences.getString(UserName,""));
                        array.put(object);



                        //list_pling.add(c.getString(0));




                    }

                    while (c.moveToNext());

                }









                adapter.close();





                ServiceHandler handler=new ServiceHandler();

               jstring=handler.PostJsonArray(ServiceIP.URL+"OrderDetails/Insert",array);





            } catch (JSONException e) {
                e.printStackTrace();
            }







            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);






            JSONObject result= null;
            try {







                /*AlertDialog.Builder builder=new AlertDialog.Builder(getActivity()) ;
                builder.setMessage(jstring);
                Dialog dialog=builder.create();
                dialog.show();*/



               result = new JSONObject(jstring);







               if(result.getString("Status").equals("true")&&result.getString("RetValue").equals("1")){
                   // table_layout.setVisibility(View.GONE);
                    //additems.setVisibility(View.VISIBLE);






                    adapter.open();
                    adapter.deleteAlltype();
                    adapter.close();

                  items.clear();
                   quantity.clear();
                   sadapter.notifyDataSetChanged();
                   recyclerView.setAdapter(sadapter);

                    // sadapter.clearData();
                  // sadapter.notifyDataSetChanged();


                    Toast.makeText(getActivity(),"Order Saved",Toast.LENGTH_LONG).show();
                }
                pdialoge.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }




        }


    }




    @Override
    public void onResume() {
        super.onResume();


        //Toast.makeText(getActivity(),"hello",Toast.LENGTH_LONG).show();
    }

    private void BuildTable(int rows, int cols,String itemname,String quantity,String price) {

        // outer for loop
        for (int i = 1; i <= rows; i++) {











             row = new TableRow(getActivity());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));



           // tv= new EditText(getActivity());
            //tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    //TableRow.LayoutParams.WRAP_CONTENT));
            ////tv.setBackgroundResource(R.drawable.cell_shape);
            //tv.setPadding(5, 5, 5, 5);



           // final CheckBox box= new CheckBox(getActivity());
           // box.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            //box.setPadding(5, 5, 5, 5);
            //boxes.add(box);
           // box.setVisibility(View.GONE);




            final EditText slno=new EditText(getActivity());
            slno.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            slno.setBackgroundResource(R.drawable.cell_shape);
            slno.setInputType(InputType.TYPE_NULL);
            slno.setPadding(5, 10, 20, 10);


            if ((getActivity().getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                slno.setTextSize(25);

            }
            else{
                slno.setTextSize(textzize);
            }

           // slno.setPadding


            slno.setText(price);

            final EditText item_etxt=new EditText(getActivity());
            item_etxt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            item_etxt.setBackgroundResource(R.drawable.cell_shape);
            item_etxt.setInputType(InputType.TYPE_NULL);



            if ((getActivity().getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                // on a large screen device ...
                item_etxt.setPadding(5, 10, 350, 10);
                item_etxt.setTextSize(25);

            }
            else{
                item_etxt.setPadding(5, 10, 160, 10);
                item_etxt.setTextSize(textzize);
            }



            item_etxt.setText(itemname);


            final EditText quantity_etxt=new EditText(getActivity());
            quantity_etxt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            quantity_etxt.setBackgroundResource(R.drawable.cell_shape);
            quantity_etxt.setInputType(InputType.TYPE_NULL);
            quantity_etxt.setPadding(5, 10, 5, 10);
            //quantity_etxt.setTextSize(textzize);



            if ((getActivity().getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                quantity_etxt.setTextSize(25);

            }
            else{
                quantity_etxt.setTextSize(textzize);
            }



            quantity_etxt.setText(quantity);

            quantity_etxt.setInputType(InputType.TYPE_CLASS_NUMBER);
            quantity_etxt.setImeOptions(EditorInfo.IME_ACTION_DONE);




            quantity_etxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        Log.i("TAG", "Enter pressed");


                        final TableRow parent = (TableRow) v.getParent();


                        int parentint = table_layout.getChildCount();

                        EditText textviewnew = (EditText) parent.getChildAt(1);
                        // itemview = (EditText) parent.getChildAt(1);
                        adapter.open();
                        boolean data = adapter.updateData(quantity_etxt.getText().toString(), Integer.parseInt(textviewnew.getText().toString()));

                        adapter.close();


                         /*           if (data = true) {

                                        Toast.makeText(getActivity(),"Updated", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(getActivity(), "Not Updataed", Toast.LENGTH_LONG).show();

                                    }*/

                    }
                    return false;
                }
            });












            final ImageView button = new ImageView(getActivity());
            button.setImageResource(R.mipmap.delete_btn);
            button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TableRow parent = (TableRow) v.getParent();


                    int parentint = table_layout.getChildCount();

                    EditText textviewnew = (EditText) parent.getChildAt(0);
                    itemview = (EditText) parent.getChildAt(1);


                    //Toast.makeText(getActivity(),Integer.toString(parentint),Toast.LENGTH_LONG).show();

                    if (parentint <= 2) {
                        table_layout.setVisibility(View.GONE);
                        additems.setVisibility(View.VISIBLE);
                        adapter.open();
                        adapter.deleteAlltype();
                        adapter.close();
                    }

                    adapter.open();

                    boolean value = adapter.deleteRow(textviewnew.getText().toString());


                    if (value = true) {


                        //    Toast.makeText(getActivity(),"deleted",Toast.LENGTH_LONG).show();

                    }


                    adapter.close();


                    //String text = textview.getText().toString;
                    //text parent.getChildAt(2)
                    table_layout.removeView(parent);
                }
            });





            final CheckBox box= new CheckBox(getActivity());
            box.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            boxes.add(box);
            //box.setPadding(5, 5, 5, 5);


                 box.setVisibility(View.GONE);








            //tableRow.addView(text);
            // tableRow.addView(button);

            // inner for loop
            /*for (int j = 1; j <= cols; j++) {

                tv= new EditText(getActivity());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setPadding(5, 5, 5, 5);


                tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            Log.i("TAG", "Enter pressed");
                            EditText textviewnew = (EditText) row.getChildAt(1);
                            Toast.makeText(getActivity(), textviewnew.getText().toString(), Toast.LENGTH_LONG).show();

                            adapter.open();
                            //boolean data = adapter.updateData(tv.getText().toString(), Integer.parseInt("0"));

                            adapter.close();


                                    /*if (data = true) {

                                        Toast.makeText(getActivity(),"Updated", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(getActivity(), "Not Updataed", Toast.LENGTH_LONG).show();

                                    }*/

                  //      }
                      //  return false;
                    //}
                //});

                //tv.setTypeface(null, Typeface.BOLD);
                //tv.setText("R " + i + ", C" + j);


                /*switch (j){
                    case 1:tv.setText(price);
                       tv.setInputType(InputType.TYPE_NULL);
                        row.addView(tv);
                        break;
                    case 2:tv.setText(itemname);
                        row.addView(tv);
                        tv.setInputType(InputType.TYPE_NULL);
                        break;
                    case 3:tv.setText(quantity);
                        tv.setInputType(InputType.TYPE_CLASS_NUMBER);
                        tv.setImeOptions(EditorInfo.IME_ACTION_DONE);


                        row.addView(tv);
                        break;

                }

                //row.addView(tv);

            }*/
            //row.addView(tv);
            //row.addView(box);
           // row.addView(button);
            row.addView(box);
            row.addView(slno);
            row.addView(item_etxt);
            row.addView(quantity_etxt);
            //row.addView(button);



            table_layout.addView(row);


        }


    }






   /* public  class  SaveHeader extends  AsyncTask<Void,Void,Void>{


        JSONObject object;

        String jstring;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


           // pdialoge=new ProgressDialog(getActivity());
            pdialoge.setMessage("Saving Shop Details......");
            pdialoge.show();
        }

        @Override
        protected Void doInBackground(Void... params) {


            try {


                Date alsoNow = Calendar.getInstance().getTime();
                String nowAsString = new SimpleDateFormat("MM/dd/yyyy").format(alsoNow);

                object=new JSONObject();
                object.put("OrderNo","12343");

            object.put("OrderDate",nowAsString);
            object.put("TenantID",sharedpreferences.getString(TenantID, ""));
            object.put("ShopID",sharedpreferences.getString(ShopID,""));
            object.put("PONo","PO100");
            object.put("CreatedBy","CreatedBy");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ServiceHandler handler=new ServiceHandler();

            jstring=handler.PostBody("http://192.168.1.23/OPSSvc/api/OrderHeader/Insert",object);


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);






            try {


                JSONObject result=new JSONObject(jstring);
                if(result.getString("Status").equals("true")&&result.getString("RetValue").equals("1")){
                    new SaveData().execute();
                }





            } catch (JSONException e) {
                e.printStackTrace();
            }

            //pdialoge.dismiss();
            //AlertDialog.Builder builder=new AlertDialog.Builder(getActivity()) ;
            //builder.setMessage(jstring);
            //Dialog dialog=builder.create();
            //dialog.show();



        }
    }
*/




    private void BuildTableHeader(int rows, int cols) {

        // outer for loop
        for (int i = 1; i <= rows; i++) {

            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));



            // inner for loop
            for (int j = 1; j <= cols; j++) {

                EditText tv = new EditText(getActivity());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                tv.setPadding(5, 5, 5, 5);



                if ((getActivity().getResources().getConfiguration().screenLayout &
                        Configuration.SCREENLAYOUT_SIZE_MASK) ==
                        Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                   tv.setTextSize(25);

                }
                else{
                    tv.setTextSize(textzize);
                }


                tv.setTypeface(null, Typeface.BOLD);
                //tv.setText("R " + i + ", C" + j);


                switch (j) {


                    case 1:
                        tv.setInputType(InputType.TYPE_NULL);
                        tv.setVisibility(View.GONE);
                        tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        textEdit=tv;
                        row.addView(tv);
                        break;
                    case 2:
                        tv.setText("#");
                         tv.setInputType(InputType.TYPE_NULL);
                        tv.setBackgroundResource(R.drawable.cell_shape);
                        row.addView(tv);
                        break;
                    case 3:
                        tv.setText("Item");
                        tv.setInputType(InputType.TYPE_NULL);
                        tv.setBackgroundResource(R.drawable.cell_shape);
                        row.addView(tv);
                        break;
                    case 4:
                        tv.setText("Qty");
                        tv.setInputType(InputType.TYPE_NULL);
                        tv.setBackgroundResource(R.drawable.cell_shape);
                        row.addView(tv);
                        break;

                }

                //row.addView(tv);

            }

            table_layout.addView(row);

        }
    }





    public void fragmentCommunication() {



        ///////////myview.setText("Hello from Tab Fragment 1");
    }



}
