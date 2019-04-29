package app.pptikitb.siap.network;


import java.util.List;

import app.pptikitb.siap.features.cctv.model.Cctvs;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by github.com/adip28 on 7/17/2018.
 */
public interface NetworkService {


    @GET("cctv")
    Call<List<Cctvs>> getCctvList();

}
