package app.pptikitb.siap.features.cctv.fragmentCctvList;

import java.util.List;

import app.pptikitb.siap.features.cctv.ICctvView;
import app.pptikitb.siap.features.cctv.model.Cctvs;
import app.pptikitb.siap.network.NetworkService;
import app.pptikitb.siap.network.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by omgimbot on 3/13/2019.
 */

public class CctvPresenter  {

    private final String TAG = CctvPresenter.class.getSimpleName();
    private final ICctvView view;
    private final Retrofit restService;
    public CctvPresenter(ICctvView view ) {
        this.view = view;
        restService = RestService.getRetroftInstance();

    }

    public void getCctvList(){
        view.showLoadingIndicator();
        restService.create(NetworkService.class).getCctvList()
                .enqueue(new Callback<List<Cctvs>>() {
                    @Override
                    public void onResponse(Call<List<Cctvs>> call, Response<List<Cctvs>> response) {
                        view.hideLoadingIndicator();
                        view.onDataRready(response.body());

                    }
                    @Override
                    public void onFailure(Call<List<Cctvs>> call, Throwable t) {
                        view.hideLoadingIndicator();
                        view.onNetworkError(t.getLocalizedMessage());
                    }
                });
    }
}
