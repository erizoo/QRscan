package by.erizo.scan.qrscan.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import by.erizo.scan.qrscan.R;
import by.erizo.scan.qrscan.ui.base.BaseActivity;

public class ShowHistoryScreen extends BaseActivity {

    private static final String FILE_NAME = "simple";

    private MenuItem item;
    private ShareActionProvider mShareActionProvider;

    private HistoryAdapter historyAdapter;
    private RecyclerView recyclerViewHistory;
    private List<String> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewHistory = findViewById(R.id.history_rv);
        historyAdapter = new HistoryAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewHistory.setLayoutManager(layoutManager);
        recyclerViewHistory.setAdapter(historyAdapter);

        Bundle extras = getIntent().getExtras();
        assert extras != null;

        historyList.clear();
        historyList.addAll(Objects.requireNonNull(extras.getStringArrayList("list")));
        historyAdapter.setItems(historyList);

        if (historyList.isEmpty()) {
            Toast.makeText(this, "Ваша история пуста", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                StringBuilder sb = new StringBuilder();
                sb.setLength(0);
                for (String items: historyList) {
                    sb.append(items).append("\t\t");
                }
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sb.toString());
                try {
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Some error", Toast.LENGTH_SHORT).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected int getContentView() {
        return R.layout.show_history_screen;
    }

}
