package by.erizo.scan.qrscan.ui.base;


import by.erizo.scan.qrscan.data.RepositoryManager;
import io.reactivex.disposables.CompositeDisposable;

public interface MvpPresenter<V> {

    void onAttach(V mvpView);

    void onDetach();

    void onDestroy();

    CompositeDisposable getCompositeDisposable();

    V getMvpView();

    RepositoryManager getRepositoryManager();

}
