package by.erizo.scan.qrscan;

import javax.inject.Inject;

import by.erizo.scan.qrscan.data.RepositoryManager;
import by.erizo.scan.qrscan.ui.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainMvpPressenterImpl <V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V>  {

    @Inject
    public MainMvpPressenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }

    @Override
    public void sendNumber(String text, String contents) {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().sendNumber(text, contents)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onSendedNumber,
                                Throwable::printStackTrace
                        )
        );
    }
}
