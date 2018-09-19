package by.erizo.scan.qrscan;

import javax.inject.Inject;

import by.erizo.scan.qrscan.data.RepositoryManager;
import by.erizo.scan.qrscan.ui.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ScannerMvpPresenterImpl <V extends ScannerMvpView> extends BasePresenter<V>
        implements ScannerMvpPresenter<V> {

    @Inject
    public ScannerMvpPresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }

    @Override
    public void sendNumber(String password, String leadId) {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().sendNumber(password, leadId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onSendedNumber,
                                Throwable::printStackTrace
                        )
        );
    }
}
