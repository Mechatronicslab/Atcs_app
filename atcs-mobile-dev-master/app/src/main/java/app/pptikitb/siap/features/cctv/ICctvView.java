package app.pptikitb.siap.features.cctv;

import java.util.List;

import app.pptikitb.siap.features.cctv.model.Cctvs;

/**
 * Created by omgimbot on 2/27/2019.
 */

public interface ICctvView {
    void initView();

    void showMediaPlayer(String url);

    void initCctvList();

    void initMarker();

    void onDataRready(List<Cctvs> cctv);

    void onNetworkError(String message);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void showTrackerList();

    void hideTrackerList();
}
