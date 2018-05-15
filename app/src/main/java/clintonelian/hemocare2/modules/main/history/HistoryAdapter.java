package clintonelian.hemocare2.modules.main.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import clintonelian.hemocare2.R;
import clintonelian.hemocare2.models.Hemoglobin;

/**
 * Created by Admin on 3/30/2018.
 *  create the adapter that will be used by the RecyclerView.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private ArrayList<Hemoglobin> dataList;

    public HistoryAdapter(ArrayList<Hemoglobin> dataList) {
        this.dataList = dataList;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
//        holder.txtHistName.setText(dataList.get(position).gethistoryName());
//        holder.txtHistDate.setText(dataList.get(position).gethistoryDate());
//        holder.txtHistHbValue.setText(dataList.get(position).getHistoryHbValue());
        //holder.txtHistName.setText(dataList.get(position).getName());
        holder.txtHistDate.setText(dataList.get(position).getDate());
        holder.txtHistHbValue.setText(String.valueOf(dataList.get(position).getHb()));
//        if(dataList.get(position).getIdHb()!=null){
            holder.txtHistidHb.setText(String.valueOf(dataList.get(position).getIdHb()));
//        } else
//        {holder.txtHistidHb.setText(String.valueOf(dataList.get(position).getIdHb()));}
        holder.txtHistidaccount.setText(String.valueOf(dataList.get(position).getIdAccount()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
//get Hemoglobin id account by date , sort by the newest
    class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView txtHistName, txtHistDate,txtHistHbValue,txtHistidHb,txtHistidaccount;

        HistoryViewHolder(View itemView) {
            super(itemView);
            //txtHistName = (TextView) itemView.findViewById(R.id.txt_history_name);
            txtHistDate = (TextView) itemView.findViewById(R.id.txt_history_date);
            txtHistHbValue = (TextView) itemView.findViewById(R.id.txt_history_hbvalue);
            txtHistidHb = (TextView) itemView.findViewById(R.id.txt_history_idhb);
            txtHistidaccount = (TextView) itemView.findViewById(R.id.txt_history_idaccount);
        }
    }
}