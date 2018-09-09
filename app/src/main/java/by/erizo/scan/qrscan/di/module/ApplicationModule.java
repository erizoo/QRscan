package by.erizo.scan.qrscan.di.module;

import android.content.Context;

import javax.inject.Singleton;

import by.erizo.scan.qrscan.Scan;
import by.erizo.scan.qrscan.data.RepositoryManager;
import by.erizo.scan.qrscan.data.RepositoryManagerImpl;
import by.erizo.scan.qrscan.di.ApplicationContext;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Scan application;

    public ApplicationModule(Scan application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    Scan provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    RepositoryManager provideRepositoryManager(RepositoryManagerImpl repositoryManager) {
        return repositoryManager;
    }
}

