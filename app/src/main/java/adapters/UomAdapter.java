package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technowavegroup.ordertrackingsystem.R;

import java.util.List;

/**
 * Created by vineeth on 7/1/2016.
 */
public class UomAdapter extends BaseAdapter {


    private final LayoutInflater inflater;
    List<Uom> obUom;
    Context  context;

    public UomAdapter(List<Uom> obUom, Context context) {
        this.obUom = obUom;
        this.context = context;


        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return obUom.size();
    }

    @Override
    public Object getItem(int position) {
        return obUom.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView=inflater.inflate(R.layout.uom_list_item,null);


        TextView uomname= (TextView) convertView.findViewById(R.id.uom_single_item);
        TextView uomid= (TextView) convertView.findViewById(R.id.uom_single_id);

        uomname.setText(obUom.get(position).getUomname());
        uomid.setText(obUom.get(position).getUonid());









        return convertView;
    }
}
