package adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.technowavegroup.ordertrackingsystem.R;

import java.util.ArrayList;


import database.DBAdapter;

/**
 * Created by vineeth on 6/22/2016.
 */
public class ShowOrderAdapter  extends RecyclerView.Adapter<ShowOrderAdapter.RecyclerViewHolder> {
   /* String [] name={"Androidwarriors","Stackoverflow","Developer Android","AndroidHive",
            "Slidenerd","TheNewBoston","Truiton","HmkCode","JavaTpoint","Javapeper"};*/
    Context context;
    LayoutInflater inflater;
    ArrayList<String> itemname,quantity;
    DBAdapter adapter;


    public ShowOrderAdapter() {

    }

    public ShowOrderAdapter(Context context,ArrayList<String> itemname,ArrayList<String> quantity, DBAdapter dbAdapter) {
        this.context=context;
        this.quantity=quantity;
        this.itemname=itemname;
        inflater=LayoutInflater.from(context);
        adapter=dbAdapter;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.singleshowtabitem, parent, false);

        RecyclerViewHolder viewHolder=new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        holder.tv1.setText(itemname.get(position));
        holder.tv2.setText(quantity.get(position));
       // holder.tv1.setOnClickListener(clickListener);
        //holder.imageView.setTag(holder);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.open();
String id="";

                Cursor c=adapter.getallItemsData();





               // Toast.makeText(context, holder.tv1.getText().toString()+"   "+c.getString(2),Toast.LENGTH_LONG).show();



                if (c.moveToFirst()){

                    do{


                       // Toast.makeText(context, holder.tv1.getText().toString()+"   "+c.getString(2),Toast.LENGTH_LONG).show();

if(holder.tv1.getText().toString().equals(c.getString(2))){
    id=c.getString(0);
}




                    }

                    while (c.moveToNext());

                }










                boolean value = adapter.deleteRow(id);







                itemname.remove(position);
                quantity.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,itemname.size());

                adapter.close();














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

        TextView tv1,tv2;
   ImageView imageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tv1= (TextView) itemView.findViewById(R.id.list_title);
            tv2= (TextView) itemView.findViewById(R.id.list_desc);
            imageView= (ImageView) itemView.findViewById(R.id.list_avatar);

        }
    }




}