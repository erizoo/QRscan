package by.erizo.scan.qrscan;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import by.erizo.scan.qrscan.ui.CaptureActivityPortrait;
import by.erizo.scan.qrscan.ui.base.BaseActivity;
import by.erizo.scan.qrscan.ui.history.ShowHistoryScreen;
import by.erizo.scan.qrscan.utils.Constant;

public class MainActivity extends BaseActivity {

    private static final String FILE_NAME = "history_a";
    private List<String> historyList = new ArrayList<>();

    private String resultNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getScreenComponent().inject(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("QRScan");
        setSupportActionBar(toolbar);

        EditText password = findViewById(R.id.code_editText);

        findViewById(R.id.with_code_button).setOnClickListener(v -> {
            if (password.getText() != null && !password.getText().toString().equals("")) {
                Intent intent = new Intent(this, QrCodeScannerActivity.class);
                intent.putExtra("password", password.getText().toString());
                intent.putExtra("check", true);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Введите код", Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.without_code_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, QrCodeScannerActivity.class);
            intent.putExtra("check", false);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history_scan: {
                readFile();
                Intent intent = new Intent(this, ShowHistoryScreen.class);
                intent.putStringArrayListExtra("list", (ArrayList<String>) historyList);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }


    public void readFile() {
        Log.d(Constant.TAG, "readFile");
        FileInputStream stream = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            stream = openFileInput(FILE_NAME);
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } finally {
                String result = sb.toString();
                String[] lines = result.split(";");
                historyList.clear();
                historyList.addAll(Arrays.asList(lines));
                stream.close();
            }
            Log.d(Constant.TAG, "Data from file: " + sb.toString());
        } catch (Exception e) {
            Log.d(Constant.TAG, "Файла нет или произошла ошибка при чтении");
        }
    }

}
