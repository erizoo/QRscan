package by.erizo.scan.qrscan;

import by.erizo.scan.qrscan.ui.base.MvpPresenter;

public interface ScannerMvpPresenter <V extends ScannerMvpView> extends MvpPresenter<V> {

    void sendNumber(String password, String leadId);
}
