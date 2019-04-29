package app.pptikitb.siap.features.cctv.fragmentCctvList;

import java.util.List;

import app.pptikitb.siap.features.cctv.model.Cctvs;

/**
 * Created by omgimbot on 2/28/2019.
 */

public interface ICctvListView {
    void initViews();

    void setData(List<Cctvs> urls);

    void onSelect(String url);
}
