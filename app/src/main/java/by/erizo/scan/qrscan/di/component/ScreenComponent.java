package by.erizo.scan.qrscan.di.component;

import by.erizo.scan.qrscan.MainActivity;
import by.erizo.scan.qrscan.QrCodeScannerActivity;
import by.erizo.scan.qrscan.di.PerScreen;
import by.erizo.scan.qrscan.di.module.ScreenModule;
import dagger.Component;

@PerScreen
@Component(modules = ScreenModule.class, dependencies = ApplicationComponent.class)
public interface ScreenComponent {

    void inject(MainActivity activity);

    void inject(QrCodeScannerActivity activity);

}
