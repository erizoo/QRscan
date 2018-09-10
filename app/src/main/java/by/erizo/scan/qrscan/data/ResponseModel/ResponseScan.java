package by.erizo.scan.qrscan.data.ResponseModel;

import com.google.gson.annotations.SerializedName;

public class ResponseScan {

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
