package by.erizo.scan.qrscan.data.ResponseModel;

import com.google.gson.annotations.SerializedName;

public class ResponseScan {

    @SerializedName("color")
    private String color;
    @SerializedName("number")
    private String number;
    @SerializedName("name")
    private String name;
    @SerializedName("result")
    private String result;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
