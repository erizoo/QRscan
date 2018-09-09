package by.erizo.scan.qrscan.data.network;


import by.erizo.scan.qrscan.data.ResponseModel.ResponseCountry;
import io.reactivex.Observable;

public interface ServiceNetwork {

    Observable<ResponseCountry> checkCountry();
}
