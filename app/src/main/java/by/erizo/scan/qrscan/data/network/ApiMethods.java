package by.erizo.scan.qrscan.data.network;

import by.erizo.scan.qrscan.data.ResponseModel.ResponseScan;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiMethods {


    @POST("mobile-app-call")
    Observable<ResponseScan> sendNumber(@Query("password") String password,
                                        @Query("leadid") String contents);
}
