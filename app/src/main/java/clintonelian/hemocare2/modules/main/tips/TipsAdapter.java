package clintonelian.hemocare2.modules.main.tips;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import clintonelian.hemocare2.R;

/**
 * Created by Admin on 3/30/2018.
 *  create the adapter that will be used by the RecyclerView.
 */

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipsViewHolder> {

    private ArrayList<Tips> dataList;

    public TipsAdapter(ArrayList<Tips> dataList) {
        this.dataList = dataList;
    }

    @Override
    public TipsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_tips, parent, false);
        return new TipsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TipsViewHolder holder, int position) {
        holder.txtTipsName.setText(dataList.get(position).getTipsName());
        holder.txtTipsEmail.setText(dataList.get(position).getTipsSuggestion());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class TipsViewHolder extends RecyclerView.ViewHolder {

        TextView txtTipsName, txtTipsEmail;

        TipsViewHolder(View itemView) {
            super(itemView);
            txtTipsName = (TextView) itemView.findViewById(R.id.txt_tips_name);
            txtTipsEmail = (TextView) itemView.findViewById(R.id.txt_tips_suggestion);
        }
    }
}