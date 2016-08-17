package adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.technowavegroup.ordertrackingsystem.R;
import java.util.ArrayList;
import java.util.List;

import letterimage.LetterImageView;


public class TodayOrderAdapter extends ExpandableRecyclerAdapter<TodayOrderAdapter.PeopleListItem> {
    public static final int TYPE_PERSON = 1001;


    ArrayList<String> ordername_list;


    ArrayList <Order> orders;
    public TodayOrderAdapter(Context context, ArrayList<String> ordername_list, ArrayList<Order> orders) {
        super(context);
        this.ordername_list=ordername_list;
        this.orders=orders;


        setItems(getSampleItems());
    }

    public static class PeopleListItem extends ExpandableRecyclerAdapter.ListItem {
        public String Text;
        public  String Number;

        public String QtyText;


        public PeopleListItem(String group) {
            super(TYPE_HEADER);

            Text = group;
        }

        public PeopleListItem(String first, String last,String number) {
            super(TYPE_PERSON);



            Text = first ;
            QtyText=last;

            Number=number;
        }
    }

    public class HeaderViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {
        TextView name;

        public HeaderViewHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.item_arrow));

            name = (TextView) view.findViewById(R.id.item_header_name);
        }

        public void bind(int position) {
            super.bind(position);

            name.setText(visibleItems.get(position).Text);
        }
    }

    public class PersonViewHolder extends ExpandableRecyclerAdapter.ViewHolder {
        TextView name;
        LetterImageView numberimge;
        TextView qty;

        public PersonViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.item_name);
            numberimge= (LetterImageView) view.findViewById(R.id.iv_avatar);
            qty= (TextView) view.findViewById(R.id.item_peaple_quantity);

            numberimge.setOval(true);
        }

        public void bind(int position) {
            name.setText(visibleItems.get(position).Text);
            numberimge.setLetter(visibleItems.get(position).Number);
            qty.setText(visibleItems.get(position).QtyText);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflate(R.layout.item_header, parent));
            case TYPE_PERSON:
            default:
                return new PersonViewHolder(inflate(R.layout.item_person, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            case TYPE_PERSON:
            default:
                ((PersonViewHolder) holder).bind(position);
                break;
        }
    }

    private List<PeopleListItem> getSampleItems() {
        List<PeopleListItem> items = new ArrayList<>();

String temp="";

        for (int i=0;i<ordername_list.size();i++){


            items.add(new PeopleListItem(ordername_list.get(i)));
            temp=ordername_list.get(i);

           for(int k=0;k<orders.size();k++){


               if(temp.equals(orders.get(k).getShopname())) {

                   items.add(new PeopleListItem(orders.get(k).getItemname(),orders.get(k).getQuantity(),orders.get(k).getOrderno()));
               }
           }


        }

       /*items.add(new PeopleListItem("Friends"));
        items.add(new PeopleListItem("Bill", "Smith"));
        items.add(new PeopleListItem("John", "Doe"));
        items.add(new PeopleListItem("Frank", "Hall"));
        items.add(new PeopleListItem("Sue", "West"));
        items.add(new PeopleListItem("Family"));
        items.add(new PeopleListItem("Drew", "Smith"));
        items.add(new PeopleListItem("Chris", "Doe"));
        items.add(new PeopleListItem("Alex", "Hall"));
        items.add(new PeopleListItem("Associates"));
        items.add(new PeopleListItem("John", "Jones"));
        items.add(new PeopleListItem("Ed", "Smith"));
        items.add(new PeopleListItem("Jane", "Hall"));
        items.add(new PeopleListItem("Tim", "Lake"));
        items.add(new PeopleListItem("Colleagues"));
        items.add(new PeopleListItem("Carol", "Jones"));
        items.add(new PeopleListItem("Alex", "Smith"));
        items.add(new PeopleListItem("Kristin", "Hall"));
        items.add(new PeopleListItem("Pete", "Lake"));*/

        return items;
    }
}