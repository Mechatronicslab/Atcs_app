package app.pptikitb.siap.features.cctv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;
import java.util.List;

import app.pptikitb.siap.R;
import app.pptikitb.siap.features.cctv.fragmentCctvList.CctvListFragment;
import app.pptikitb.siap.features.cctv.fragmentCctvList.CctvPresenter;
import app.pptikitb.siap.features.cctv.model.Cctvs;
import app.pptikitb.siap.ui.AnimationHelper;
import app.pptikitb.siap.ui.BottomDialogs;
import app.pptikitb.siap.ui.CustomDrawable;
import app.pptikitb.siap.ui.SweetDialogs;
import app.pptikitb.siap.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CctvActivity extends AppCompatActivity implements ICctvView, OnMapReadyCallback, View.OnClickListener {
    GoogleMap Gmap;
    SupportMapFragment GmapView;
    Animation slideUp, slideDown;
    @BindView(R.id.list_layout)
    LinearLayout mListMainLayout;
    @BindView(R.id.list_viewpager)
    ViewPager mListViewPager;
    @BindView(R.id.listbtn_fab)
    FloatingActionButton mListBtnFab;
    List<CctvListFragment> mCctvFragment = new ArrayList<>();
    CctvPresenter presenter;
    List<Cctvs> cctvs = new ArrayList<>();
    @BindView(R.id.loading_layout)
    RelativeLayout mLoadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);
        //getSupportActionBar().hide();
        ButterKnife.bind(this);
        this.initView();
        this.hideTrackerList();
        presenter = new CctvPresenter(this);
        presenter.getCctvList();
    }

    @Override
    public void initView() {
        GmapView = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        GmapView.getMapAsync(this);

        //mListBtnFab.setOnClickListener(this);

    }

    @Override
    public void showMediaPlayer(String url) {

    }

    @Override
    public void initCctvList() {
        mListBtnFab.setOnClickListener(this);
        final CctvPageAdapter adapter = new CctvPageAdapter(getSupportFragmentManager());
        CctvListFragment cctvfragment = new CctvListFragment();
        cctvfragment.setData(cctvs);
        adapter.addFragment(cctvfragment, "Tittle");
        mCctvFragment.add(cctvfragment);
        mListViewPager.setAdapter(adapter);

    }

    @Override
    public void initMarker() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        int height = 60;
        int width = 50;
        for (Cctvs cctv : this.cctvs) {
            BitmapDescriptor icon = null;
            LatLng coordinate = new LatLng(Double.parseDouble(cctv.getLocation().getCoordinates().get(0)), Double.parseDouble(cctv.getLocation().getCoordinates().get(1)));
//            Gmap.addMarker(new MarkerOptions().position(coordinate)
//                    .title(location.getNama()));
            Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.cctv_icon);
            Bitmap tintImage = Utils.tintImage(myIcon);
            Bitmap markers = Bitmap.createScaledBitmap(tintImage, width, height, false);
            icon = BitmapDescriptorFactory.fromBitmap(markers);
            Gmap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(cctv.getLocation().getCoordinates().get(0)), Double.parseDouble(cctv.getLocation().getCoordinates().get(1))))
                    .title(cctv.getNama())
                    .snippet(cctv.getUrl())
                    .icon(icon));
            if (coordinate != null) {
                builder.include(coordinate);
                LatLngBounds bounds = builder.build();
                Gmap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 180));
            }

            Gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    marker.hideInfoWindow();
                    BottomDialogs.showTrackerDetail(CctvActivity.this, marker);
//                    CustomInfoWindowAdapter markerInfoWindowAdapter = new CustomInfoWindowAdapter(getApplicationContext());
//                    Gmap.setInfoWindowAdapter(markerInfoWindowAdapter);
                    return true;
                }
            });
        }


    }

    @Override
    public void onDataRready(List<Cctvs> cctv) {
        this.cctvs = cctv;
        this.initCctvList();
        this.initMarker();
    }

    @Override
    public void onNetworkError(String message) {
        Log.e("String error", message);
        SweetDialogs.endpointError(this);
    }

    @Override
    public void showLoadingIndicator() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showTrackerList() {
        if (slideUp == null) {
            slideUp = AnimationHelper.getAnimation(this, R.anim.slide_up, anim -> {

            });
        }
        mListBtnFab.setImageDrawable(CustomDrawable.googleMaterialDrawable(
                this, R.color.flat_blue, 24, GoogleMaterial.Icon.gmd_keyboard_arrow_down
        ));
        mListMainLayout.setVisibility(View.VISIBLE);
        mListMainLayout.startAnimation(slideUp);
    }

    @Override
    public void hideTrackerList() {
        if (slideDown == null) {
            slideDown = AnimationHelper.getAnimation(this, R.anim.slide_down, anim -> {
                mListMainLayout.setVisibility(View.GONE);
                mListBtnFab.setImageDrawable(CustomDrawable.googleMaterialDrawable(
                        this, R.color.flat_blue, 24, GoogleMaterial.Icon.gmd_view_list
                ));

            });
        }
        mListMainLayout.startAnimation(slideDown);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Gmap = googleMap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GmapView != null)
            GmapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (GmapView != null)
            GmapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (GmapView != null)
            GmapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (GmapView != null)
            GmapView.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.listbtn_fab:
                if (mListMainLayout.getVisibility() == View.VISIBLE)
                    this.hideTrackerList();
                else this.showTrackerList();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mListMainLayout.getVisibility() == View.VISIBLE)
            this.hideTrackerList();
        else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_favorite) {
        //    Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

}
