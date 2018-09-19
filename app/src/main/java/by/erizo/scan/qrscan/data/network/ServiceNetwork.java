package by.erizo.scan.qrscan.data.network;

import by.erizo.scan.qrscan.data.ResponseModel.ResponseScan;
import io.reactivex.Observable;

public interface ServiceNetwork {

    Observable<ResponseScan> sendNumber(String password, String leadId);
}
