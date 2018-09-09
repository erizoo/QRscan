package by.erizo.scan.qrscan.data.network;

import javax.inject.Inject;

import by.erizo.scan.qrscan.data.ResponseModel.ResponseCountry;
import io.reactivex.Observable;

public class ServiceNetworkImp implements ServiceNetwork {

    private static final String TAG = ServiceNetworkImp.class.getSimpleName();

    private ApiMethods apiMethods;

    @Inject
    public ServiceNetworkImp(ApiMethods apiMethods) {
        this.apiMethods = apiMethods;
    }


    @Override
    public Observable<ResponseCountry> checkCountry() {
        return apiMethods.checkCountry("java-ipapi-client");
    }
}
