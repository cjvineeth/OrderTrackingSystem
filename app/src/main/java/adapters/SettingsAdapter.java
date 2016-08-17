package adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.technowavegroup.ordertrackingsystem.LoginActivity;
import com.technowavegroup.ordertrackingsystem.R;

import java.util.ArrayList;

import database.DBAdapter;

/**
 * Created by ansa on 7/12/2016.
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.RecyclerViewHolder> {

    /* String [] name={"Androidwarriors","Stackoverflow","Developer Android","AndroidHive",
                 "Slidenerd","TheNewBoston","Truiton","HmkCode","JavaTpoint","Javapeper"};*/
    Context context;
    LayoutInflater inflater;
    ArrayList<String> itemname;
    ArrayList<Integer> icons;
    SharedPreferences sharedpreferences;
    //ActionBar bar;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String PassCode = "passCode";

  SharedPreferences.Editor editor;


    public SettingsAdapter() {

    }

    public SettingsAdapter(Context context,ArrayList<String> itemname,ArrayList<Integer> icons, SharedPreferences sharedpreferences  ) {
        this.context=context;
        editor = sharedpreferences.edit();
        this.itemname=itemname;
        this.icons=icons;
        this.sharedpreferences=sharedpreferences;
        inflater=LayoutInflater.from(context);


    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.singlesettingitem, parent, false);



        RecyclerViewHolder viewHolder=new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        holder.tv1.setText(itemname.get(position));

        holder.imageView.setImageResource(icons.get(position));
        // holder.tv1.setOnClickListener(clickListener);
        //holder.imageView.setTag(holder);



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//if(position==0) {

    Dialog dialog;
    AlertDialog.Builder builder = new AlertDialog.Builder(context);

    builder.setTitle("Logout");
    builder.setMessage("Are you sure ?");

    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {


            editor.putString(Name, "");
            editor.commit();
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);



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





//}


            }
        });






    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            RecyclerViewHolder vholder = (RecyclerViewHolder) v.getTag();
            int position = vholder.getPosition();

            Toast.makeText(context, "This is position " + position, Toast.LENGTH_LONG).show();

        }
    };



    @Override
    public int getItemCount() {
        return itemname.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tv1;
        ImageView imageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tv1= (TextView) itemView.findViewById(R.id.list_title_singlesetting);

            imageView= (ImageView) itemView.findViewById(R.id.list_avatar_singlesetting);

        }
    }




}