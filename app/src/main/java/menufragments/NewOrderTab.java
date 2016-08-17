package menufragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.technowavegroup.ordertrackingsystem.Home;
import com.technowavegroup.ordertrackingsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import adapters.Item;
import adapters.ItemsAdapter;
import barcodeReader.BarcodeGraphic;

import barcodeReader.BarcodeTrackerFactory;
import camera.CameraSource;
import camera.CameraSourcePreview;
import camera.GraphicOverlay;
import database.DBAdapter;
import ip.ServiceIP;
import iterfaces.IFragmentToActivity;
import mehdi.sakout.fancybuttons.FancyButton;

import com.android.volley.RequestQueue;


/**
 * Created by technoway on 4/29/2016.
 */
public class NewOrderTab extends Fragment   implements QRCodeReaderView.OnQRCodeReadListener {

RelativeLayout relativeLayout;


    private static final int RC_HANDLE_GMS = 9001;

    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;


    private static final String TAG = "Barcode-reader";
    private IFragmentToActivity mCallback;
    TextView textView;
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String User_id = "User_id";
    private int mPage;
    private TimePicker start_piPicker;
    private SimpleDateFormat dateFormatter;
    private Spinner accSpinner, activitySpinner;
    private CameraSource mCameraSource;
    String addclick = "false";
    String data[] = {"Bisucut", "MilkyBar", "CupCakes"};
    ArrayAdapter<String> ap;
    AppCompatSpinner spinner, uom;
    private String muserid;
    ArrayList<String> list;
    DBAdapter db_adapter;
    ImageButton add;
    Button save_order;
    //AutoCompleteTextView shop;
    //QRCodeReaderView qrCodeReaderView;

    AutoCompleteTextView shop;
    String toggler="false";
    EditText qty_etxt;
    View view;
    private SharedPreferences.Editor editor;
    private String datathere = "false";
    private String codeContent;
    private String codeFormat;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String PassCode = "passCode";
    public static final String ShopID = "shopid";
    public static final String ShopName = "shopname";

    public static final String AddClick= "addclick";

    public static final String TenantID = "tenantID";
    SharedPreferences sharedpreferences;
    private ArrayAdapter<String> spinneradapter;
   // private ImageButton scan;
    private ArrayAdapter<String> uomadapter;
    private ArrayList<String> s_list;
    private ArrayList<String> shopid_list;
    ImageView imageNewItem;
    private String shopidSingle;
    private ArrayList<String> itemname_list;
    private ArrayList<String> item_id_list;
    private String singleitemId = "";
    String data1[] = {"One", "Two", "three"};
    FloatingActionButton scan;
    //QRCodeReaderView qrCodeReaderView;

    private RecyclerView recyclerView;

    LinearLayout linearLayout;
    private ItemsAdapter adapter;
    private List<Item> itemList;
    private List<Item> new_itemList;
    FancyButton newitms,allitems;
    TextView desc;

    SearchView search;

    private CameraSourcePreview mPreview;

    private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;
    private ArrayList<Item> newitem_list;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (IFragmentToActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IFragmentToActivity");
        }
    }
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();



           // editor.putString(AddClick, "false");
          //  editor.commit();


        db_adapter = ((Home) getActivity()).adapter;
        setHasOptionsMenu(true);

        view = inflater.inflate(R.layout.newordertab, container, false);
        shop= (AutoCompleteTextView) view.findViewById(R.id.shop);
        search= (SearchView) view.findViewById(R.id.serach);
        scan= (FloatingActionButton) view.findViewById(R.id.fab_scan);
        mPreview = (CameraSourcePreview)view. findViewById(R.id.preview_new);
        mGraphicOverlay = (GraphicOverlay<BarcodeGraphic>)view. findViewById(R.id.graphicOverlay_new);
        linearLayout= (LinearLayout) view.findViewById(R.id.topLayout_new);
        newitms= (FancyButton) view.findViewById(R.id.btn_new);
        allitems= (FancyButton) view.findViewById(R.id.btn_all);
        desc= (TextView) view.findViewById(R.id.selector_desc);
        shop.setImeOptions(EditorInfo.IME_ACTION_DONE);

       // qrCodeReaderView= (QRCodeReaderView) view.findViewById(R.id.qrdecoderview);
        relativeLayout= (RelativeLayout) view.findViewById(R.id.layoutgone);
      //  qrCodeReaderView= (QRCodeReaderView) view.findViewById(R.id.qrdecoderview);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((Home) getActivity()).setSupportActionBar(toolbar);

        initCollapsingToolbar();
        linearLayout.setVisibility(View.GONE);
        int rc = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(true, true);
        } else {
            requestCameraPermission();
        }


        if(sharedpreferences.getString(ShopName,"").equals("")){

            desc.setVisibility(View.VISIBLE);
            //desc.setText("Please Select a Shop");

           // recyclerView.setVisibility(View.GONE);


        }
        else {


            desc.setVisibility(View.GONE);
           // recyclerView.setVisibility(View.VISIBLE);

            shop.setText(sharedpreferences.getString(ShopName, ""));

           shop.setSelection(shop.getText().toString().length());

        }

        gestureDetector = new GestureDetector(getActivity(), new CaptureGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(getActivity(), new ScaleListener());

       // Snackbar.make(mGraphicOverlay, "Tap to capture. Pinch/Stretch to zoom",
               // Snackbar.LENGTH_LONG)
               // .show();


        itemList = new ArrayList<>();
        new_itemList = new ArrayList<>();


        adapter = new ItemsAdapter(getActivity(),db_adapter, itemList,mCallback,addclick, sharedpreferences);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final RecyclerView.LayoutManager mLayoutManager;







        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (toggler.equals("false")) {
                    startCameraSource();
                    linearLayout.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    int rc = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                    if (rc == PackageManager.PERMISSION_GRANTED) {
                        createCameraSource(true, true);
                    } else {
                        requestCameraPermission();
                    }
                    toggler = "true";


                } else {

                    linearLayout.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);

                    mPreview.release();
                    mPreview.stop();
                    toggler = "false";

                }




            }
        });

        //qrCodeReaderView.setOnQRCodeReadListener(this);


        if ((getActivity().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_XLARGE) {

           mLayoutManager = new GridLayoutManager(getActivity(), 3);
        }
        else{
             mLayoutManager = new GridLayoutManager(getActivity(), 2);
        }


        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);





        // Capture Text in EditText
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {






                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {








                newText= newText.toLowerCase();







                final List<Item> filteredList = new ArrayList<>();

                for (int i = 0; i < new_itemList.size(); i++) {

                    final String text = new_itemList.get(i).getName().toLowerCase();
                    final String barcode= new_itemList.get(i).getBarcode();
                    if (text.contains(newText)||barcode.equals(newText)) {




                        //Toast.makeText(getActivity(),"hi",Toast.LENGTH_LONG).show();
                        filteredList.add(new_itemList.get(i));
                    }
                }


                itemList.clear();
                itemList.addAll(filteredList);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

     //   prepareAlbums();
        //Toast.makeText(getActivity(),sharedpreferences.getString(AddClick,""),Toast.LENGTH_LONG).show();



            StringRequest stringRequest = new StringRequest(ServiceIP.URL+"Shop/Get?TenantID=" + sharedpreferences.getString(TenantID, ""),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                            try {
                                JSONObject j_response = new JSONObject(response.toString());


                                JSONArray jsonArray = j_response.getJSONArray("List");

                                s_list = new ArrayList<String>();
                                shopid_list = new ArrayList<String>();

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject single = jsonArray.getJSONObject(i);
                                    String shopName = single.getString("ShopName").toString();
                                    String shopid = single.getString("ShopID").toString();
                                    s_list.add(shopName);
                                    shopid_list.add(shopid);


                                }


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.listitem, s_list);

                                shop.setAdapter(adapter);


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



















        itemList.clear();


       /* int i = s_list.indexOf(shop.getText().toString());


        shopidSingle = shopid_list.get(i);


        editor.putString(ShopID, shopidSingle);
        editor.commit();


        db_adapter.open();
        db_adapter.deleteAlltype();
        db_adapter.close();*/


       // if(addclick.equals("true")) {




        if(sharedpreferences.getString(ShopID, "").equals("")){

        }

else{

            itemList.clear();


            new_itemList.clear();


           // desc.setVisibility(View.VISIBLE);
           // desc.setText("No Items Found");

          // recyclerView.setVisibility(View.GONE);

            StringRequest stringRequestitem = new StringRequest(ServiceIP.URL+"ShopItemLink/Get?TenantID=" + sharedpreferences.getString(TenantID, ""),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                            try {





                                JSONObject j_response = new JSONObject(response.toString());


                                JSONArray jsonArray = j_response.getJSONArray("List");




                                if(jsonArray.length()==0){


                                    adapter.notifyDataSetChanged();

                                }


                                else{



                                    //desc.setVisibility(View.GONE);
                                   // desc.setText("No Items Found");

                                   // recyclerView.setVisibility(View.VISIBLE);


                                    itemname_list = new ArrayList<String>();
                                    item_id_list = new ArrayList<String>();


                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject single = jsonArray.getJSONObject(i);
                                        String itemName = single.getString("ItemName").toString();
                                        String itemId = single.getString("ItemID").toString();
                                        String barcode=single.getString("ItemBarcode").toString();
                                        String image=single.getString("ItemImagePath").toString();
                                        String uom=single.getString("ItemUOM").toString();
                                        String desc=single.getString("ItemDesc").toString();
                                        Item a = new Item();
                                        a.setName(itemName);
                                        a.setItemid(itemId);
                                        a.setThumbnail(image);
                                        a.setBarcode(barcode);
                                        a.setUom(uom);
                                        a.setItemdes(desc);

                                        itemList.add(a);
                                        new_itemList.add(a);


                                        itemname_list.add(itemName);
                                        item_id_list.add(itemId);


                                    }


                                    // spinneradapter = new ArrayAdapter<String>(getActivity(),R.layout.listitem, itemname_list);

                                    // spinner.setAdapter(spinneradapter);


                                    // prepareAlbumsNew();


                                    //uom.setAdapter(spinneradapter);
                                    adapter.notifyDataSetChanged();

                                }



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

            RequestQueue requestQueueitem = Volley.newRequestQueue(getActivity());
            requestQueueitem.add(stringRequestitem);


       }







        newitms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







                itemList.clear();
                adapter.notifyDataSetChanged();
               // new_albumList.clear();
               // desc.setVisibility(View.VISIBLE);
                //desc.setText("No Items Found");

                //recyclerView.setVisibility(View.GONE);

                StringRequest stringRequestitem = new StringRequest(ServiceIP.URL+"ShopItemLink/Get?TenantID=" + sharedpreferences.getString(TenantID, ""),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject j_response = new JSONObject(response.toString());


                                    JSONArray jsonArray = j_response.getJSONArray("List");

                                    newitem_list = new ArrayList<Item>();

                                    //desc.setVisibility(View.GONE);
                                   // desc.setText("No Items Found");

                                    //recyclerView.setVisibility(View.VISIBLE);

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject single = jsonArray.getJSONObject(i);
                                        String newitemname = single.getString("ItemName").toString();
                                        String newitemId = single.getString("ItemID").toString();
                                        String barcode=single.getString("ItemBarcode").toString();
                                        String image=single.getString("ItemImagePath").toString();
                                        String uom=single.getString("ItemUOM").toString();
                                        String desc=single.getString("ItemDesc").toString();

                                        Item a = new Item();
                                        a.setName(newitemname);
                                        a.setItemid(newitemId);
                                        a.setThumbnail(image);
                                        a.setBarcode(barcode);
                                        a.setUom(uom);
                                        a.setItemdes(desc);
                                        newitem_list.add(a);




                                    }


                                    // spinneradapter = new ArrayAdapter<String>(getActivity(),R.layout.listitem, itemname_list);

                                    // spinner.setAdapter(spinneradapter);


                                    // prepareAlbumsNew();


                                    //uom.setAdapter(spinneradapter);

                                    itemList.addAll(newitem_list);
                                    adapter.notifyDataSetChanged();

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

                RequestQueue requestQueueitem = Volley.newRequestQueue(getActivity());
                requestQueueitem.add(stringRequestitem);













                //albumList.clear();








            }
        });






        allitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemList.clear();
                new_itemList.clear();
                adapter.notifyDataSetChanged();





                // desc.setVisibility(View.VISIBLE);
                // desc.setText("No Items Found");

                // recyclerView.setVisibility(View.GONE);

                StringRequest stringRequestitem = new StringRequest(ServiceIP.URL+"ShopItemLink/Get?TenantID=" + sharedpreferences.getString(TenantID, ""),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                                try {





                                    JSONObject j_response = new JSONObject(response.toString());


                                    JSONArray jsonArray = j_response.getJSONArray("List");








                                        //desc.setVisibility(View.GONE);
                                        // desc.setText("No Items Found");

                                        // recyclerView.setVisibility(View.VISIBLE);


                                        itemname_list = new ArrayList<String>();
                                        item_id_list = new ArrayList<String>();


                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject single = jsonArray.getJSONObject(i);
                                            String itemName = single.getString("ItemName").toString();
                                            String itemId = single.getString("ItemID").toString();
                                            String barcode=single.getString("ItemBarcode").toString();
                                            String image=single.getString("ItemImagePath").toString();
                                            String uom=single.getString("ItemUOM").toString();
                                            String desc=single.getString("ItemDesc").toString();
                                            Item a = new Item();
                                            a.setName(itemName);
                                            a.setItemid(itemId);
                                            a.setThumbnail(image);
                                            a.setBarcode(barcode);
                                            a.setUom(uom);
                                            a.setItemdes(desc);

                                            itemList.add(a);
                                            new_itemList.add(a);


                                            //itemname_list.add(itemName);
                                           // item_id_list.add(itemId);





                                        // spinneradapter = new ArrayAdapter<String>(getActivity(),R.layout.listitem, itemname_list);

                                        // spinner.setAdapter(spinneradapter);


                                        // prepareAlbumsNew();


                                        //uom.setAdapter(spinneradapter);
                                        adapter.notifyDataSetChanged();

                                    }



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

                RequestQueue requestQueueitem = Volley.newRequestQueue(getActivity());
                requestQueueitem.add(stringRequestitem);
            }
        });





        shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {






                // addclick="true";

                    itemList.clear();
                new_itemList.clear();
                adapter.notifyDataSetChanged();
                //adapter = new AlbumsAdapter(getActivity(),db_adapter,albumList,mCallback,addclick, sharedpreferences);


                    int i = s_list.indexOf(shop.getText().toString());


                    shopidSingle = shopid_list.get(i);


                    editor.putString(ShopID, shopidSingle);
                editor.putString(ShopName,shop.getText().toString());
                    editor.commit();
               // Toast.makeText(getActivity(),shopidSingle,Toast.LENGTH_LONG).show();

                    db_adapter.open();
                    db_adapter.deleteAlltype();
                    db_adapter.close();
               // desc.setVisibility(View.VISIBLE);
                //desc.setText("No Items Found");

                //recyclerView.setVisibility(View.GONE);

                    StringRequest stringRequestitem = new StringRequest(ServiceIP.URL+"ShopItemLink/Get?TenantID=" + sharedpreferences.getString(TenantID, ""),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                                    try {



                                        desc.setVisibility(View.GONE);
                                        //desc.setText("No Items Found");

                                        recyclerView.setVisibility(View.VISIBLE);
                                        JSONObject j_response = new JSONObject(response.toString());


                                        JSONArray jsonArray = j_response.getJSONArray("List");




                                        //if(jsonArray.length()==0){


                                            //adapter.notifyDataSetChanged();

                                          //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();



                                       // }
//else{

                                            itemname_list = new ArrayList<String>();
                                            item_id_list = new ArrayList<String>();


                                            for (int i = 0; i < jsonArray.length(); i++) {

                                                JSONObject single = jsonArray.getJSONObject(i);
                                                String itemName = single.getString("ItemName").toString();
                                                String itemId = single.getString("ItemID").toString();
                                                String image=single.getString("ItemImagePath").toString();
                                                String barcode=single.getString("ItemBarcode").toString();
                                                String uom=single.getString("ItemUOM").toString();
                                                String desc=single.getString("ItemDesc").toString();

                                                Item a = new Item();
                                                a.setName(itemName);
                                                a.setItemid(itemId);
                                                a.setThumbnail(image);
                                                a.setBarcode(barcode);
                                                a.setItemdes(desc);
                                                a.setUom(uom);

                                                itemList.add(a);
                                                new_itemList.add(a);


                                                itemname_list.add(itemName);
                                                item_id_list.add(itemId);


                                            }


                                            adapter.notifyDataSetChanged();

                                       // }


                                        // spinneradapter = new ArrayAdapter<String>(getActivity(),R.layout.listitem, itemname_list);

                                        // spinner.setAdapter(spinneradapter);


                                        // prepareAlbumsNew();


                                        //uom.setAdapter(spinneradapter);

                                        //mCallback.Reload();

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

                    RequestQueue requestQueueitem = Volley.newRequestQueue(getActivity());
                    requestQueueitem.add(stringRequestitem);


                    editor.putString(AddClick,"false");
                    editor.commit();

                }


        });











        try {
            Glide.with(this).load(R.mipmap.three).into((ImageView)  view. findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }












        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                    boolean b = scaleGestureDetector.onTouchEvent(event);

                    boolean c = gestureDetector.onTouchEvent(event);

                    return b || c ;


            }
        });








        return view;



    }


    @Override
    public void onPause() {
        super.onPause();

        mPreview.release();
        mPreview.stop();
        itemList.clear();
        adapter.notifyDataSetChanged();


    }

    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(getActivity(), permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity =getActivity();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        /*Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();*/
    }





    @SuppressLint("InlinedApi")


    private void createCameraSource(boolean autoFocus, boolean useFlash) {
        Context context = getActivity();

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay);
        barcodeDetector.setProcessor(
                new MultiProcessor.Builder<>(barcodeFactory).build());

        if (!barcodeDetector.isOperational()) {
            // Note: The first time that an app using the barcode or face API is installed on a
            // device, GMS will download a native libraries to the device in order to do detection.
            // Usually this completes before the app is run for the first time.  But if that
            // download has not yet completed, then the above call will not detect any barcodes
            // and/or faces.
            //
            // isOperational() can be used to check if the required native libraries are currently
            // available.  The detectors will automatically become operational once the library
            // downloads complete on device.
            Log.w(TAG, "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = getActivity().registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(getActivity(), R.string.low_storage_error, Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        CameraSource.Builder builder = new CameraSource.Builder(getActivity(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f);

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(
                    autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);
        }

        mCameraSource = builder
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();
    }






    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

           search.performClick();
        search.setQuery(text, false);


    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }

    /**
     * Adding few albums for testing
     */



















    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }




    private boolean onTap(float rawX, float rawY) {

        //TODO: use the tap position to select the barcode.
        BarcodeGraphic graphic = mGraphicOverlay.getFirstGraphic();
        Barcode barcode = null;
        if (graphic != null) {
            barcode = graphic.getBarcode();
            if (barcode != null) {
                //Intent data = new Intent();
                //data.putExtra(BarcodeObject, barcode);
                //setResult(CommonStatusCodes.SUCCESS, data);
                // finish();

                search.performClick();
                search.setQuery(barcode.displayValue, false);

                Toast.makeText(getActivity(),barcode.displayValue,Toast.LENGTH_LONG).show();
            }
            else {
                Log.d(TAG, "barcode data is null");
            }
        }
        else {
            Log.d(TAG,"no barcode detected");
        }
        return barcode != null;
    }







    private class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
        }
    }





    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should consider this event
         * as handled. If an event was not handled, the detector
         * will continue to accumulate movement until an event is
         * handled. This can be useful if an application, for example,
         * only wants to update scaling factors if the change is
         * greater than 0.01.
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should continue recognizing
         * this gesture. For example, if a gesture is beginning
         * with a focal point outside of a region where it makes
         * sense, onScaleBegin() may return false to ignore the
         * rest of the gesture.
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         * <p/>
         * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
         * and {@link ScaleGestureDetector#getFocusY()} will return focal point
         * of the pointers remaining on the screen.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         */
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            mCameraSource.doZoom(detector.getScaleFactor());
        }
    }




    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
              getActivity());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

}
       /* spinner = (AppCompatSpinner) view.findViewById(R.id.product);
        //ap = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
        add = (ImageButton) view.findViewById(R.id.button_add);
        uom= (AppCompatSpinner) view.findViewById(R.id.uom);
        scan = (ImageButton) view.findViewById(R.id.button_scan);
       // save_order = (Button) view.findViewById(R.id.save_order);
        //shop = (AutoCompleteTextView) view.findViewById(R.id.shop);
        qty_etxt = (EditText) view.findViewById(R.id.quantity_et);
        uom= (AppCompatSpinner) view.findViewById(R.id.uom);
        imageNewItem= (ImageView) view.findViewById(R.id.neworder);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        //Toast.makeText(getActivity(),,Toast.LENGTH_LONG).show();


       // spinner.setAdapter(ap);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



              int index=itemname_list.indexOf(spinner.getSelectedItem());




                singleitemId=item_id_list.get(index);


                Toast.makeText(getActivity(),singleitemId,Toast.LENGTH_LONG).show();





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                Dialog dialog;
                builder.setMessage("AddItem ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int idn) {

                        addclick="true";
                        String ids = "", oldquantity = "";
                        adapter.open();


                        Cursor c = adapter.getallItemsData();

                        if (c.moveToFirst()) {

                            do {
                                if (c.getString(2).equals(spinner.getSelectedItem().toString())) {
                                    datathere = "true";
                                    ids = c.getString(0);
                                    oldquantity = c.getString(3);
                                }


                            } while (c.moveToNext());


                        }

                        // Toast.makeText(getActivity(),datathere,Toast.LENGTH_LONG).show();


                        if (datathere.equals("true")) {


                            String sum = Integer.toString(Integer.parseInt(oldquantity) + Integer.parseInt(qty_etxt.getText().toString()));


                            boolean data = adapter.updateData(sum, Integer.parseInt(ids));
                            datathere = "false";

                        } else {
                            long id = adapter.addItemData(shop.getSelectedItem().toString(), spinner.getSelectedItem().toString(), qty_etxt.getText().toString(),uom.getSelectedItem().toString(),singleitemId);

                        }                  // Toast.makeText(getActivity(), "Inserted", Toast.LENGTH_LONG).show();

                        adapter.close();


                        //Button btn= (Button) view.findViewById(R.id.my_btn);


                        // BuildTableHeader(1, 3);


                        dialog.dismiss();


                        mCallback.Reload();


                    }
                });







                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int idn) {


                        dialog.dismiss();


                    }
                });


                dialog = builder.create();
                dialog.show();


            }
        });


        if(addclick.equals("true")){

            spinner.setAdapter(spinneradapter);

        }


imageNewItem.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {





        if(addclick.equals("true")){

            spinner.setAdapter(spinneradapter);

        }
        else {
            StringRequest stringRequestnew = new StringRequest("http://192.168.1.23/OPSSvc/api/Item/GetNewItems?ShopID="+shopidSingle,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                            try {
                                JSONObject j_response = new JSONObject(response.toString());


                                JSONArray jsonArray = j_response.getJSONArray("List");

                                  itemname_list = new ArrayList<String>();
                                 item_id_list=new ArrayList<String>();

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject single = jsonArray.getJSONObject(i);
                                    String itemName = single.getString("ItemName").toString();
                                    String itemId=single.getString("ItemID").toString();
                                    itemname_list.add(itemName);
                                    item_id_list.add(itemId);


                                }


                                ArrayAdapter<String> newitemadapter = new ArrayAdapter<String>(getActivity(),R.layout.listitem, itemname_list);

                                spinner.setAdapter(newitemadapter);
                                //uom.setAdapter(spinneradapter);

                               // Toast.makeText(getActivity(),itemname_list.get(0),Toast.LENGTH_LONG).show();

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

            RequestQueue requestQueuenew = Volley.newRequestQueue(getActivity());
            requestQueuenew.add(stringRequestnew);


        }
















    }
});












       /* shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getActivity(),"Click",Toast.LENGTH_LONG).show();









               addclick="false";


                int i=s_list.indexOf(shop.getText().toString());


              shopidSingle=shopid_list.get(i);





                editor.putString(ShopID,shopidSingle);
                editor.commit();




                adapter.open();

                adapter.deleteAlltype();
                adapter.close();
                mCallback.Reload();
               // Toast.makeText(getActivity(),shopid_list.get(i),Toast.LENGTH_LONG).show();





                if(addclick.equals("true")){

                    spinner.setAdapter(spinneradapter);

                }
                else {
                    StringRequest stringRequestitem = new StringRequest("http://192.168.1.23/OPSSvc/api/ShopItemLink/Get?ShopID="+shopid_list.get(i),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                                    try {
                                        JSONObject j_response = new JSONObject(response.toString());


                                        JSONArray jsonArray = j_response.getJSONArray("List");

                                        itemname_list = new ArrayList<String>();
                                        item_id_list=new ArrayList<String>();

                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject single = jsonArray.getJSONObject(i);
                                            String itemName = single.getString("ItemName").toString();
                                            String itemId=single.getString("ItemID").toString();
                                            itemname_list.add(itemName);
                                            item_id_list.add(itemId);


                                        }


                                        spinneradapter = new ArrayAdapter<String>(getActivity(),R.layout.listitem, itemname_list);

                                        spinner.setAdapter(spinneradapter);
                                        //uom.setAdapter(spinneradapter);


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

                    RequestQueue requestQueueitem = Volley.newRequestQueue(getActivity());
                    requestQueueitem.add(stringRequestitem);


                }










            }
        });*/


//getall Shop

     /*   StringRequest stringRequest = new StringRequest("http://192.168.1.23/OPSSvc/api/Shop/Get?TenantID="+sharedpreferences.getString(TenantID,""),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                        try {
                            JSONObject j_response=new JSONObject(response.toString());


                            JSONArray jsonArray=j_response.getJSONArray("List");

                            s_list=new ArrayList<>();
                            shopid_list=new ArrayList<String>();

                            for (int i=0;i<jsonArray.length();i++){

                                JSONObject single=jsonArray.getJSONObject(i);
                                String shopName=single.getString("ShopName").toString();
                                String shopid=single.getString("ShopID").toString();
                                s_list.add(shopName);
                                shopid_list.add(shopid);





                            }





                            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.listitem,s_list);

                            shop.setAdapter(adapter);



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


//getAlliems







        if(addclick.equals("true")){

            uom.setAdapter(uomadapter);

        }
        else {
            StringRequest stringRequestuom = new StringRequest("http://192.168.1.23/OPSSvc/api/UOM/Get",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                            try {
                                JSONObject j_response = new JSONObject(response.toString());


                                JSONArray jsonArray = j_response.getJSONArray("List");

                                ArrayList<String> u_list = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject single = jsonArray.getJSONObject(i);
                                    String itemName = single.getString("UOMValue").toString();
                                    u_list.add(itemName);


                                }




                                //if ((getActivity().getResources().getConfiguration().screenLayout &
                                        //Configuration.SCREENLAYOUT_SIZE_MASK) ==
                                       // Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                                    // on a large screen device ...
                                    //uomadapter = new ArrayAdapter<String>(getActivity(), R.layout.listitem, u_list);

                               // }
                               // else{
                                    uomadapter = new ArrayAdapter<String>(getActivity(),R.layout.listitem, u_list);
                                //}





                                uom.setAdapter(uomadapter);
                                //uom.setAdapter(spinneradapter);


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

            RequestQueue requestQueueitemuom = Volley.newRequestQueue(getActivity());
            requestQueueitemuom.add(stringRequestuom);


        }
















        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Place Your Barcode Inside Rectangle");
                integrator.setResultDisplayDuration(0);
                //integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
                //integrator.setScanningRectangle(50,50);
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.initiateScan();

               // Intent intent=new Intent(getActivity(), ScanActivity.class);
                //startActivity(intent);
                //getActivity().finish();




            }
        });


return view;
    }



    
    @Override
    public void onResume() {
        super.onResume();


        //Toast.makeText(getActivity(),"hello",Toast.LENGTH_LONG).show();


    }



*/
