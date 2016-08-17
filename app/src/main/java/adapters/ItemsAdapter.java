package adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technowavegroup.ordertrackingsystem.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;
import barcodeReader.MainActivity;
import database.DBAdapter;
import ip.ServiceIP;
import iterfaces.IFragmentToActivity;
import servicehandlers.ServiceHandler;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder>{

    private Context mContext;
    private  List<Item> itemList;
    private ArrayList<Uom> uom_list;

    private  Uom uom;

    private String addclick;
    private DBAdapter dbAdapter;
    LayoutInflater inflater;
    private String datathere="false";
    IFragmentToActivity fragmentToActivity;
    SharedPreferences sharedPreferences;
    private AlertDialog dialog,dialog1;
    ProgressDialog pDialog;
    private Bitmap bitmap;
    private ImageView img;
    TextView  desc;

    private SharedPreferences.Editor  editor;

    public static final String Name = "nameKey";
    public static final String PassCode = "passCode";
    public static final String ShopID = "shopid";
    public static final String AddClick= "addclick";

    public static final String TenantID = "tenantID";
    private ArrayList<String> u_list;
    private TextView singleid;
    private String uomtxt;
    private LoadImage imgtask;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count,quantitydata,itemid,url,uom,description_data;
        public ImageView thumbnail, overflow;



        public CircleButton plus,minus;

        String addclick;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            itemid=(TextView) view.findViewById(R.id.item_id);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            plus= (CircleButton) view.findViewById(R.id.circle_pls_btn);
            minus= (CircleButton) view.findViewById(R.id.circle_mins_btn);
            quantitydata= (TextView) view.findViewById(R.id.quantity_data);
            description_data= (TextView) view.findViewById(R.id.description_new_txt);
            url= (TextView) view.findViewById(R.id.url_txt);
            uom= (TextView) view.findViewById(R.id.uom_txt);



        }
    }


    public ItemsAdapter(Context mContext, DBAdapter dbAdapter, List<Item> itemList, IFragmentToActivity fragmentToActivity, String addclick, SharedPreferences sharedPreferences) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.dbAdapter=dbAdapter;
        this.addclick=addclick;

        inflater = ( LayoutInflater )mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.sharedPreferences=sharedPreferences;

        this.fragmentToActivity=fragmentToActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Item item = itemList.get(position);
        holder.title.setText(item.getName());
        holder.itemid.setText(item.getItemid());
        holder.url.setText(item.getThumbnail());
        holder.uom.setText(item.getUom());
        holder.description_data.setText(item.getItemdes());


        //holder.count.setText(album.getNumOfSongs() + " songs");

        // loading album cover using Glide library


        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

                 if(item.getThumbnail().equals("")){
                     Glide.with(mContext).load("http://54.251.98.159/OOPSSvc/Images/NoImage.png")
                             .thumbnail(0.5f)
                             .crossFade()
                             .diskCacheStrategy(DiskCacheStrategy.ALL)
                             .into(holder.thumbnail);
                 }else {

                     Glide.with(mContext).load("http://54.251.98.159/OOPSSvc"+ item.getThumbnail())
                             .thumbnail(0.5f)
                             .crossFade()
                             .diskCacheStrategy(DiskCacheStrategy.ALL)
                             .into(holder.thumbnail);
                 }



        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







               /* if(imgtask!=null) {

                    if (imgtask.getStatus().equals(AsyncTask.Status.RUNNING)) {

                        imgtask.cancel(true);
            }
                }*/



                AlertDialog.Builder builder1=new AlertDialog.Builder(mContext);
                builder1.setTitle(holder.title.getText().toString());

                View myview=inflater.inflate(R.layout.imagelayout,null);
                 img= (ImageView) myview.findViewById(R.id.imagesingle);
                desc= (TextView) myview.findViewById(R.id.itemdes);




                if(holder.description_data.getText().toString().equals("")){
                    desc.setText("No Description Available");
                }

                else {
                    desc.setText(holder.description_data.getText().toString());
                }



                //img.setImageDrawable(holder.thumbnail.getDrawable());

                //Bitmap bitmap = holder.thumbnail.getDrawable();


                Bitmap mutableBitmap = Bitmap.createBitmap(70, 70, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(mutableBitmap);
                holder.thumbnail.getDrawable().setBounds(0, 0, 70, 70);
                holder.thumbnail.getDrawable().draw(canvas);

                Bitmap resized = Bitmap.createScaledBitmap(mutableBitmap,250, 250, true);
                img.setImageBitmap(resized);

              //  Glide.with(mContext).load("http://54.251.98.159/OOPSSvc/Images/NoImage.png")
                        //.thumbnail(0.5f)
                       // .crossFade()
                      //  .diskCacheStrategy(DiskCacheStrategy.ALL)
                        //.into(img);
                //ImageLoader loader=new ImageLoader(mContext);

               // if(holder.description_data.getText().toString().equals("")){
                   // desc.setText("No description Available");
               // }


                ///else {

               //}

                if(holder.url.getText().toString().equals("")){

                    imgtask=new LoadImage();
                    imgtask.execute("http://54.251.98.159/OOPSSvc/Images/NoImage.png" );

                }else {

                    imgtask=new LoadImage();
                   imgtask.execute("http://54.251.98.159/OOPSSvc" + holder.url.getText().toString());
                }



                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog1.dismiss();

                    }
                });


                builder1.setView(myview);


                dialog1=builder1.create();

                dialog1.show();





            }
        });



        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, holder.quantitydata, holder.title, holder.itemid,holder.uom);
            }
        });



        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                int num=Integer.parseInt(holder.quantitydata.getText().toString());

                //int check=num-1;


                    holder.quantitydata.setText(Integer.toString(num + 1));



            }
        });



        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                int num=Integer.parseInt(holder.quantitydata.getText().toString());
                int check=num-1;

                if(check==0) {


                }

                else{
                    holder.quantitydata.setText(Integer.toString(num-1));
                }

            }
        });



        holder.quantitydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String data[]={"One","Two","Three","Four"};

                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setTitle("Enter Quantity");
                View myview=inflater.inflate(R.layout.quantity_edit,null);
                final EditText enter= (EditText) myview.findViewById(R.id.et_quantity);
                TextView textView= (TextView) myview.findViewById(R.id.item_uom_txt);
                AppCompatSpinner spinner= (AppCompatSpinner) myview.findViewById(R.id.item_uom);
                enter.setText(holder.quantitydata.getText().toString());
                enter.setSelection(holder.quantitydata.length());
                 holder.uom.getText().toString();

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {



                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        singleid= (TextView) view.findViewById(R.id.uom_single_id);
                        uomtxt=singleid.getText().toString();
                       // Toast.makeText(mContext,singleid.getText().toString(),Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }




                });





                if(item.getUom().equals("1001"))


                {
                   textView.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                    new GetUoms(spinner).execute();



                }else{

                    if(textView.getVisibility()==View.VISIBLE||spinner.getVisibility()==View.VISIBLE){
                        textView.setVisibility(View.GONE);
                        spinner.setVisibility(View.GONE);

                    }



                }


                builder.setView(myview);
                builder.setPositiveButton("Add to cart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {






                        if(enter.getText().toString().equals("")){
                            Toast.makeText(mContext,"The Quantity field is Blank",Toast.LENGTH_LONG).show();
                        }

                        else if(Integer.parseInt(enter.getText().toString())<=0){
                            Toast.makeText(mContext,"Enter a quantity greater than zero",Toast.LENGTH_LONG).show();
                        }

else{
                            holder.quantitydata.setText(enter.getText().toString());




                            //editor = sharedPreferences.edit();

                            //editor.putString(AddClick, "true");
                            //editor.commit();




                            dbAdapter.open();


                            Cursor c = dbAdapter.getallItemsData();

                            String oldquantity = null;
                            String ids = null;
                            if (c.moveToFirst()) {

                                do {
                                    if (c.getString(2).equals(holder.title.getText().toString())) {
                                        datathere = "true";
                                        ids = c.getString(0);
                                        oldquantity = c.getString(3);



                                    }


                                } while (c.moveToNext());


                            }






                            if(item.getUom().equals("1001"))


                            {

                                if (datathere.equals("true")) {





                                    String sum = Integer.toString(Integer.parseInt(oldquantity) + Integer.parseInt(holder.quantitydata.getText().toString()));

                                    // boolean data = adapter.updateData(sum, Integer.parseInt(ids));


                                    boolean data = dbAdapter.updateDataUom(enter.getText().toString(), uomtxt, Integer.parseInt(ids));
                                    datathere = "false";

                                }

                                else{


                                    long id = dbAdapter.addItemData("Velocity", holder.title.getText().toString(), enter.getText().toString(), uomtxt, holder.itemid.getText().toString());

                                }





                            }

                            else {


                                if (datathere.equals("true")) {


                                    // Toast.makeText(mContext, "Update", Toast.LENGTH_LONG).show();

                                    String sum = Integer.toString(Integer.parseInt(oldquantity) + Integer.parseInt(holder.quantitydata.getText().toString()));

                                    // boolean data = adapter.updateData(sum, Integer.parseInt(ids));


                                    boolean data = dbAdapter.updateData(enter.getText().toString(), Integer.parseInt(ids));
                                    datathere = "false";

                                } else {


                                    long id = dbAdapter.addItemData("Velocity", holder.title.getText().toString(), enter.getText().toString(), holder.uom.getText().toString(), holder.itemid.getText().toString());

                                    //  Toast.makeText(mContext, "Insert", Toast.LENGTH_LONG).show();


                                }

                            }



                            dbAdapter.close();


                        }




                    }
                });


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();

                    }
                });






               dialog=builder.create();

                dialog.show();






            }
        });

    }





    private class GetUoms extends  AsyncTask<Void,Void,Void>{






        Spinner spinner;

        public GetUoms(Spinner spinner) {
            this.spinner = spinner;
        }

        String jString;
        JSONArray jsonArray = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
            ServiceHandler handler=new ServiceHandler();

            jString=handler.makeServiceCall(ServiceIP.URL+"UOM/Get?option=1",ServiceHandler.GET);




                JSONObject j_response = new JSONObject(jString);


                uom_list = new ArrayList<Uom>();

                   jsonArray = j_response.getJSONArray("List");

              u_list = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject single = jsonArray.getJSONObject(i);
                    String uomName = single.getString("UOMValue").toString();
                    String uomid=single.getString("UOMID").toString();
                    uom=new Uom();

                    uom.setUonid(uomid);
                    uom.setUomname(uomName);



                    uom_list.add(uom);
                    u_list.add(uomName);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }




                return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);




          UomAdapter arrayAdapter=new UomAdapter(uom_list,mContext);
            spinner.setAdapter(arrayAdapter);

        }
    }


    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Loading Image ....");
            //pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(mContext, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
    

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view,TextView quantity ,TextView tittle,TextView itemid,TextView uom) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(quantity, tittle, mContext, dbAdapter, fragmentToActivity, addclick, sharedPreferences,itemid,uom));
        popup.show();
    }




    @Override
    public int getItemCount() {
        return itemList.size();
    }








}


