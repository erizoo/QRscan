package by.erizo.scan.qrscan.data.network;

import javax.inject.Inject;

import by.erizo.scan.qrscan.data.ResponseModel.ResponseScan;
import io.reactivex.Observable;

public class ServiceNetworkImp implements ServiceNetwork {

    private static final String TAG = ServiceNetworkImp.class.getSimpleName();

    private ApiMethods apiMethods;

    @Inject
    public ServiceNetworkImp(ApiMethods apiMethods) {
        this.apiMethods = apiMethods;
    }

    @Override
    public Observable<ResponseScan> sendNumber(String text, String contents) {
        return apiMethods.sendNumber(text,contents);
    }
}
