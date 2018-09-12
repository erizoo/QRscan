//package by.erizo.scan.qrscan.ui.history;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import by.erizo.scan.qrscan.R;
//import by.erizo.scan.qrscan.ui.base.BaseActivity;
//
//public class HistoryActivity extends BaseActivity {
//
//    private static final String FILE_NAME = "simple";
//
//    private HistoryAdapter historyAdapter;
//    private RecyclerView recyclerViewHistory;
//    private List<String> historyList = new ArrayList<>();
//
//    @Override
//    protected int getContentView() {
//        return R.layout.history_activity;
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        recyclerViewHistory = findViewById(R.id.history_rv);
//        historyAdapter = new HistoryAdapter();
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        recyclerViewHistory.setLayoutManager(layoutManager);
//        recyclerViewHistory.setAdapter(historyAdapter);
//
//        Bundle extras = getIntent().getExtras();
//        assert extras != null;
//
//        historyAdapter.setItems(extras.getStringArrayList("list"));
//
//        if (historyList.isEmpty()) {
//            Toast.makeText(this, "Ваша история пуста", Toast.LENGTH_LONG).show();
//        }
//
//        final Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        String textToSend = "some text";
//        intent.putExtra(Intent.EXTRA_TEXT, textToSend);
//        try {
//            startActivity(Intent.createChooser(intent, "Описание действия"));
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(getApplicationContext(), "Some error", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//}
