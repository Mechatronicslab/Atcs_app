package app.pptikitb.siap.features.cctv.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by omgimbot on 3/13/2019.
 */

public class Cctvs {
    @SerializedName("nama")
    private String mNama;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("location")
    private Locations location;

    public String getNama() {
        return mNama;
    }

    public void setNama(String mNama) {
        this.mNama = mNama;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }
}
