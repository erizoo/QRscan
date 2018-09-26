package by.erizo.scan.qrscan;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.Result;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import javax.inject.Inject;

import by.erizo.scan.qrscan.data.ResponseModel.ResponseScan;
import by.erizo.scan.qrscan.ui.base.BaseActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;
import static android.graphics.Color.parseColor;

public class QrCodeScannerActivity extends BaseActivity implements ZXingScannerView.ResultHandler, ScannerMvpView {

    private static final String FILE_NAME = "history_a";

    @Inject
    ScannerMvpPresenter<ScannerMvpView> presenter;

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private String password;
    private String result;
    private boolean check = false;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreenComponent().inject(this);
        presenter.onAttach(this);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        Intent intent = getIntent();
        password = intent.getStringExtra("password");
        check = intent.getBooleanExtra("check", false);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
        if (check) {
            showAlertDialog();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showAlertDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Введите номер штрихкода");
        alertDialog.setCancelable(false);


        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Отправить код",
                (dialog, which) -> {
                    if (input.getText().toString() != null || !input.getText().toString().equals("")) {
                        presenter.sendNumber(password, input.getText().toString());
                    }
                    dialog.dismiss();
                    showAlertDialog();
                }
        );
        alertDialog.setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
                alertDialog.dismiss();
            }
            return true;
        });

        Rect displayRectangle = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);


        Objects.requireNonNull(alertDialog.getWindow()).setLayout((int) (displayRectangle.width() *
                2f), (int) (displayRectangle.height() * 0.3f));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = 20;   //x position
        wmlp.y = 100;   //y position
        alertDialog.getWindow().setAttributes(wmlp);

        alertDialog.show();
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        (dialog, which) -> {
                                            requestPermissions(new String[]{CAMERA},
                                                    REQUEST_CAMERA);
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(QrCodeScannerActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (mScannerView == null) {
                    mScannerView = new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera(camId);
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
        mScannerView = null;
    }

    @Override
    public void handleResult(Result rawResult) {
        result = rawResult.getText();
        Log.e("QRCodeScanner", rawResult.getText());
        Log.e("QRCodeScanner", rawResult.getBarcodeFormat().toString());
        if (check) {
            presenter.sendNumber(password, rawResult.getText());
        } else {
            Toast.makeText(this, "Scanned: " + rawResult.getText(), Toast.LENGTH_LONG).show();
            mScannerView.resumeCameraPreview(QrCodeScannerActivity.this);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());
            String resultValue = date + "- " + rawResult.getText() + ";";
            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE | MODE_APPEND);
                outputStream.write(resultValue.getBytes());
                outputStream.write(System.getProperty("line.separator").getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void show(boolean type, ResponseScan responseScan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        if (type) {
            sb.append(responseScan.getNumber()).append(System.getProperty("line.separator")).append(responseScan.getName()).append(System.getProperty("line.separator")).append("Код принят");
        } else {
            sb.append("Код не принят");
        }
        builder.setMessage(sb);
        builder.setPositiveButton("OK", (dialog, which) -> mScannerView.resumeCameraPreview(QrCodeScannerActivity.this));
        AlertDialog alert1 = builder.create();
        alert1.setCanceledOnTouchOutside(true);
        alert1.setOnCancelListener(
                dialog -> mScannerView.resumeCameraPreview(QrCodeScannerActivity.this)
        );
        alert1.show();
        alert1.getButton(AlertDialog.BUTTON_NEUTRAL).setDrawingCacheBackgroundColor(parseColor("#000000"));
        if (type) {
            Objects.requireNonNull(alert1.getWindow()).setBackgroundDrawableResource(android.R.color.holo_green_light);
        } else {
            Objects.requireNonNull(alert1.getWindow()).setBackgroundDrawableResource(android.R.color.holo_red_dark);
        }

    }

    @Override
    public void onSendedNumber(ResponseScan responseScan) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        String resultValue = date + "- " + result + ";";
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE | MODE_APPEND);
            outputStream.write(resultValue.getBytes());
            outputStream.write(System.getProperty("line.separator").getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (responseScan.getColor().equals("red")) {
            show(false, responseScan);
        } else {
            show(true, responseScan);
        }
    }
}