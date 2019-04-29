package app.pptikitb.siap.features.cctv.fragmentCctvList;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.pptikitb.siap.R;
import app.pptikitb.siap.features.cctv.CctvActivity;
import app.pptikitb.siap.features.cctv.model.Cctvs;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CctvListFragment extends Fragment implements ICctvListView {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters

    List<Cctvs> cctvs = new ArrayList<>();
    @BindView(R.id.recyleview)
    RecyclerView mRecyclerView;
    private CctvListAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public static CctvListFragment newInstance() {
        CctvListFragment fragment = new CctvListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cctv_list, container, false);
        ButterKnife.bind(this, rootView);
//        String testUrl = "http://192.168.100.4:3000/video/basukir1";
//        String testUr2 = "http://172.32.1.42:3000/video";
//        urls.add(testUrl);

        if (cctvs.size() > 0)
            this.setData(cctvs);
        return rootView;
    }




    @Override
    public void initViews() {
        Parcelable recyclerViewState;
        //recyclerViewState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        //mRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.clearFocus();
        mRecyclerView.getAdapter().notifyDataSetChanged();


    }


    @Override
    public void setData(List<Cctvs> cctv) {

        this.cctvs = cctv ;
        if (mRecyclerView != null) {
            if (mRecyclerView.getAdapter() == null) {
                adapter = new CctvListAdapter(getActivity(), cctvs);
                mRecyclerView.setAdapter(adapter);
                this.initViews();
            }
            else {
                adapter.swap(this.cctvs);
            }
        }
    }

    @Override
    public void onSelect(String url) {

        ((CctvActivity) getActivity()).hideTrackerList();
        //((TrackerActivity) getActivity()).zoomToLng(model.getDeviceId(), lng);

    }

}
