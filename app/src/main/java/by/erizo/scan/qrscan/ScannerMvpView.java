package by.erizo.scan.qrscan;

import by.erizo.scan.qrscan.data.ResponseModel.ResponseScan;
import by.erizo.scan.qrscan.ui.base.MvpView;

public interface ScannerMvpView extends MvpView {

    void onSendedNumber(ResponseScan responseScan);
}
