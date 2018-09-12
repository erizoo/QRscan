package by.erizo.scan.qrscan;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import by.erizo.scan.qrscan.data.ResponseModel.ResponseScan;
import by.erizo.scan.qrscan.ui.CaptureActivityPortrait;
import by.erizo.scan.qrscan.ui.base.BaseActivity;
import by.erizo.scan.qrscan.ui.history.ShowHistoryScreen;
import by.erizo.scan.qrscan.utils.Constant;

public class MainActivity extends BaseActivity implements MainMvpView {

    private static final String FILE_NAME = "history";
    private List<String> historyList = new ArrayList<>();

    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    @BindView(R.id.code_editText)
    EditText codeEdit;

    private IntentIntegrator integrator;
    private Map<String, Object> objectMap;
    private boolean isCode = false;
    private String resultNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreenComponent().inject(this);
        presenter.onAttach(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("QRScan");
        setSupportActionBar(toolbar);

        integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(false);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.setBarcodeImageEnabled(false);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Ag0zLolRYqQIQmOTshbiKKJJK6x2");

        if (isNetworkConnected()) {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    objectMap = (HashMap<String, Object>) dataSnapshot.getValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(this, "Нужно подключить интернет!", Toast.LENGTH_LONG).show();
        }

    }

    @OnClick(R.id.with_code_button)
    public void startScanWithCode() {
        System.out.println("");
        if (codeEdit.getText() != null && !codeEdit.getText().toString().equals("")) {
            try {
                String content = objectMap.get(codeEdit.getText().toString()).toString();
                isCode = true;
                integrator.initiateScan();
            } catch (NullPointerException e) {
                Toast.makeText(this, "Вы ввели неверный код", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Введите код", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.without_code_button)
    public void startScan() {
        integrator.initiateScan();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                if (isCode) {
                    Log.d("MainActivity", "Scanned");
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    integrator.initiateScan();
                    String content = objectMap.get(codeEdit.getText().toString()).toString();
                    resultNumber = result.getContents();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Calendar cal = Calendar.getInstance();
                    String date = dateFormat.format(cal.getTime());
                    String resultValue = date + "- " + resultNumber + ";";
                    Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
                    FileOutputStream outputStream;
                    try {
                        outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE | MODE_APPEND);
                        outputStream.write(resultValue.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    presenter.sendNumber(content, result.getContents());
                } else {
                    Log.d("MainActivity", "Scanned");
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    integrator.initiateScan();
                }
            }
        }
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

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onSendedNumber(ResponseScan responseScan) {
        if (responseScan.getStatus().equals("success")) {

        } else {
            Toast.makeText(this, "Номер не отправлен", Toast.LENGTH_LONG).show();
        }
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
