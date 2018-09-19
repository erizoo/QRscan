package by.erizo.scan.qrscan.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.journeyapps.barcodescanner.CaptureActivity;


public class CaptureActivityPortrait extends CaptureActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Context getContext(Context context){
        return context;
    }

    public void showDialog() {

    }
}
