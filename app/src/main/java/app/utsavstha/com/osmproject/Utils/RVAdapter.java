package app.utsavstha.com.osmproject.Utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.utsavstha.com.osmproject.DataClass.SchoolData;
import app.utsavstha.com.osmproject.R;

/**
 * Created by utsav on 12/18/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DataViewHolder> {
    List<SchoolData> data;

    public RVAdapter(List<SchoolData> data) {
        this.data = data;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView address;


        DataViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            name = (TextView)itemView.findViewById(R.id.name);
            address = (TextView)itemView.findViewById(R.id.address);
        }
    }

    @Override
    public RVAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        DataViewHolder pvh = new DataViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(RVAdapter.DataViewHolder holder, int position) {

        holder.name.setText("Name: "+data.get(position).getName());
        holder.address.setText("Address: "+data.get(position).getAddressStreet());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
