package cu.lt.joe.calculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cu.lt.joe.calculator.models.HistoryItem;
import cu.lt.joe.calculator.R;
import java.util.ArrayList;

public class OperationsHistoryAdapter extends ArrayAdapter<HistoryItem>
{
    private Context context;
    
    public OperationsHistoryAdapter(Context context, ArrayList<HistoryItem> records)
    {
        super(context, R.layout.history_item_entry_layout, records);
        this.context = context;
    }
    
    private class ViewHolder
    {
        TextView operation, result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.history_item_entry_layout, parent, false);
            holder = new ViewHolder();
            holder.operation = convertView.findViewById(R.id.history_item_entry_layout_operation);
            holder.result = convertView.findViewById(R.id.history_item_entry_layout_result);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        HistoryItem item = getItem(position);
        holder.operation.setText(item.getOperation());
        holder.result.setText("="+item.getResult());
        return convertView;
    }
}
