package by.erizo.scan.qrscan.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import by.erizo.scan.qrscan.ScannerMvpPresenter;
import by.erizo.scan.qrscan.ScannerMvpPresenterImpl;
import by.erizo.scan.qrscan.ScannerMvpView;
import by.erizo.scan.qrscan.di.ActivityContext;
import by.erizo.scan.qrscan.di.PerScreen;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ScreenModule {

    private final AppCompatActivity activity;

    public ScreenModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @PerScreen
    ScannerMvpPresenter<ScannerMvpView> provideScannerMvpPresenter(ScannerMvpPresenterImpl<ScannerMvpView> presenter) {
        return presenter;
    }
}
