package by.erizo.scan.qrscan;

import android.content.Context;

import by.erizo.scan.qrscan.ui.base.MvpPresenter;

public interface MainMvpPresenter  <V extends MainMvpView> extends MvpPresenter<V> {

    void sendNumber(String text, String contents);

}
