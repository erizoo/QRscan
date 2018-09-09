package by.erizo.scan.qrscan.di.component;

import android.content.Context;

import javax.inject.Singleton;

import by.erizo.scan.qrscan.Scan;
import by.erizo.scan.qrscan.data.RepositoryManager;
import by.erizo.scan.qrscan.di.ApplicationContext;
import by.erizo.scan.qrscan.di.module.ApiModule;
import by.erizo.scan.qrscan.di.module.ApplicationModule;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {

    void inject(Scan application);

    @ApplicationContext
    Context context();

    RepositoryManager getRepositoryManager();

}

