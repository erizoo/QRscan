package by.erizo.scan.qrscan.ui.base;

import android.content.Context;

public interface MvpView {

    Context getContext();

    void startScreen(Class activity, boolean isCleatTop);

}
