package by.erizo.scan.qrscan.ui.history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.erizo.scan.qrscan.R;
import by.erizo.scan.qrscan.ui.base.BaseViewHolder;

public class HistoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<String> historyList = new ArrayList<>();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryAdapter.HistoryViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.items_history_rv, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    private Context context;

    public void setItems(List<String> historyList) {
        this.historyList.clear();
        this.historyList.addAll(historyList);
        notifyDataSetChanged();
    }

    public class HistoryViewHolder extends BaseViewHolder {

        @BindView(R.id.history_textView)
        TextView historyTextView;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        @Override
        public void onBind(int position) {
            historyTextView.setText(historyList.get(position));
        }
    }

}
