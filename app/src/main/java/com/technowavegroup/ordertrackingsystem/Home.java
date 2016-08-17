package com.technowavegroup.ordertrackingsystem;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionMenu;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import adapters.MyMenuItemClickListener;
import adapters.NewOrderAdapter;
import adapters.ViewPagerAdapter;
import database.DBAdapter;
import iterfaces.IFragmentToActivity;
import iterfaces.SwipeListen;
import nonSwipePager.NonSwipePager;


public class Home extends AppCompatActivity  implements IFragmentToActivity, SwipeListen{


    NonSwipePager pager;

    ViewPagerAdapter mAdapter;
    //ActionBar bar;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String PassCode = "passCode";
    SharedPreferences sharedpreferences;
    public DBAdapter  adapter=new DBAdapter(Home.this);
    NewOrderAdapter nAdapter;

    ViewPager getpager;
    FloatingActionMenu menu;
    MyMenuItemClickListener getAdClickListener;

    String value;
    int colorList[] = {R.color.Brown, R.color.Chocolate, R.color.DarkCyan, R.color.DarkGreen, R.color.Crimson};
    int imageList[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private String codeContent;
    private MyMenuItemClickListener adClickListener;







    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();

        if(sharedpreferences.getString(Name,"").equals("passcode")){


            Intent intent = new Intent(getApplicationContext(), PassCodeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }

        else{

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onRestart() {

        super.onRestart();


       // if(notnow.equals("true")) {




        //}else{
            //Intent intent = new Intent(getApplicationContext(), PassCodeActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivity(intent);
            //finish();
       // }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        pager = (NonSwipePager) findViewById(R.id.pager);

        //bar=getSupportActionBar();
       // bar.setTitle("New Order");
       // newOrderAdapter=(NewOrder).

   getAdClickListener=new MyMenuItemClickListener();


        getAdClickListener.setmicListener(new MyMenuItemClickListener.OnmicClickListner() {
            @Override
            public void OnmicClickListner() {
                Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_LONG).show();
            }
        });



        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

       // final SharedPreferences.Editor editor = sharedpreferences.edit();
        // bar.setIcon(R.mipmap.logo);
        //bar.setHomeButtonEnabled(true);
      //  bar.setHomeButtonEnabled(true);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.logo_transparent);

    //actionBar.setDisplayHomeAsUpEnabled(false);.setDisplayHomeAsUpEnabled(false);
        //  bar.setTitle("Order Taking System");
       // bar.hide();
        //linearLayout=findViewById(R)




        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.isColoredBackground(false);
        //bottomNavigationView.ic

        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                ("", ContextCompat.getColor(getApplicationContext(), R.color.Brown), R.mipmap.addcart);
        final BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                ("", ContextCompat.getColor(getApplicationContext(), R.color.Chocolate), R.mipmap.naecustomer);
        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
                ("", ContextCompat.getColor(getApplicationContext(), R.color.DarkCyan), R.mipmap.sheet);
        BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem
                ("", ContextCompat.getColor(getApplicationContext(), R.color.DarkGreen), R.mipmap.todaysorder);
        BottomNavigationItem bottomNavigationItem4 = new BottomNavigationItem
                ("", ContextCompat.getColor(getApplicationContext(), R.color.Crimson), R.mipmap.settingnew);
        bottomNavigationView.addTab(bottomNavigationItem);
        bottomNavigationView.addTab(bottomNavigationItem1);
        bottomNavigationView.addTab(bottomNavigationItem2);
        bottomNavigationView.addTab(bottomNavigationItem3);
        bottomNavigationView.addTab(bottomNavigationItem4);

       // adClickListener=new MyMenuItemClickListener();





       /* adClickListener.setmicListener(new MyMenuItemClickListener.OnmicClickListner() {
            @Override
            public void OnmicClickListner() {
                Toast.makeText(Home.this, "Hello", Toast.LENGTH_LONG).show();
            }
        });*/

        //bottomNavigationView.setUpWithViewPager(pager,colorList,imageList);
        //bottomNavigationView.disableViewPagerSlide();

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(mAdapter);


        /*pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });*/
        /*pager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });*/

        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {



                Fragment fragment = ((ViewPagerAdapter)pager.getAdapter()).getFragment(index);

                if (fragment != null)
                {
                    fragment.onResume();
                }




                switch (index) {





                    case 0:



                        pager.setCurrentItem(0,false);

                       // bar.hide();
                        //bar.setTitle("New Order");
                        // Fragment fragment=pager.getAdapter()

                        break;
                    case 1:
                        pager.setCurrentItem(1,false);
                        getpager.setCurrentItem(0);
                        //bar.show();bar.show();
                       // bar.setTitle(" Near By Custumer");
                        break;
                    case 2:
                        //bar.setTitle("Show Trip Sheet");
                        //bar.show();
                        pager.setCurrentItem(2,false);
                        getpager.setCurrentItem(0);
                        break;
                    case 3:
                       // bar.setTitle("Todays Order");
                        // bar.show();
                        pager.setCurrentItem(3,false);
                        getpager.setCurrentItem(0);
                        break;
                    case 4:
                        //bar.setTitle("Settings");
                        //bar.show();
                        pager.setCurrentItem(4,false);
                        getpager.setCurrentItem(0);
                        break;


                }
            }
        });


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {






            }

            @Override
            public void onPageSelected(int position) {








                Fragment fragment = ((ViewPagerAdapter)pager.getAdapter()).getFragment(position);

                if (fragment != null&&position!=1)
                {
                    fragment.onResume();
                }


                switch (position) {
                    case 0:


                        bottomNavigationView.selectTab(0);

                        //bar.setTitle("New Order");
                        break;

                    case 1:

                        //bar.setTitle(" Near By Custumer");
                        bottomNavigationView.selectTab(1);

                        break;

                    case 2:

                     //bar.setTitle("Show Trip Sheet");
                       bottomNavigationView.selectTab(2);
                        break;
                    case 3:
                        //bar.setTitle("Todays Order");

                        bottomNavigationView.selectTab(3);
                        break;
                    case 4:

                        //bar.setTitle("Settings");
                        bottomNavigationView.selectTab(4);
                        break;


                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {



               Fragment fragment = ((ViewPagerAdapter)pager.getAdapter()).getFragment(state);

                if (fragment != null)
                {
                    fragment.onResume();
                }



            }
        });


    }






    public void onAdapterChange(ViewPager pager) {
        this.getpager=pager;
    }

    @Override
    public void Reload() {







        Fragment fragment = ((ViewPagerAdapter)pager.getAdapter()).getFragment(0);

        //getpager.getAdapter().notifyDataSetChanged();

        if (fragment != null)
        {
            fragment.onResume();
        }else{
            Toast.makeText(Home.this,"Null Fragments",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void communicateToFragment2() {


       /*ShowOrderTab fragment = (ShowOrderTab) nAdapter.getFragment(1);
        if (fragment != null) {
            fragment.fragmentCommunication();
        } else {
             Log.i("TABGOT", "Fragment 2 is not initialized");
        }*/

    }


    public void fabButton(FloatingActionMenu menu) {

        this.menu=menu;

    }

    @Override
    public void swipe() {
        //Toast.makeText(Home.this,"Swiped",Toast.LENGTH_LONG).show();



        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        menu.startAnimation(animation1);

    }











}
