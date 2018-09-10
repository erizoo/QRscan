package by.erizo.scan.qrscan.data.network;

import by.erizo.scan.qrscan.data.ResponseModel.ResponseScan;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiMethods {

    @Multipart
    @POST("{number}")
    Observable<ResponseScan> sendNumber(@Path("number") String text,
                                        @Part("leadid") String contents);
}
