package app.pptikitb.siap.features.cctv.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by omgimbot on 3/13/2019.
 */

public class Locations {
    @SerializedName("coordinate")
    private List<String> coordinate = null;

    public List<String> getCoordinates() {
        return coordinate;
    }

    public void setCoordinates(List<String> coordinates) {
        this.coordinate = coordinates;
    }
}
