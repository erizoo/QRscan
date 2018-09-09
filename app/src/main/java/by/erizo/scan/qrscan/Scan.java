package by.erizo.scan.qrscan;

import android.app.Application;

import by.erizo.scan.qrscan.di.component.ApplicationComponent;
import by.erizo.scan.qrscan.di.component.DaggerApplicationComponent;
import by.erizo.scan.qrscan.di.module.ApplicationModule;


public class Scan extends Application {


    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
