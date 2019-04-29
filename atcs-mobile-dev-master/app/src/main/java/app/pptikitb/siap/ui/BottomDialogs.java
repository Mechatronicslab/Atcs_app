package app.pptikitb.siap.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import app.pptikitb.siap.R;

/**
 * Created by hynra [github.com/hynra] on 20/02/2018.
 */

public class BottomDialogs {

    public BottomDialogs() {

    }

    public interface onDialogClose {
        void onDismiss(String string);

    }

    public interface onPlaceSelected {
        void onDismiss(LatLng pos, String address);
    }


    /*public static void editAddress(Activity activity, String address, onDialogClose listener) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.layout_bottom_dialog_edittext, null);
        EditText editText = customView.findViewById(R.id.common_edit);
        editText.setHint("Alamat");
        editText.setText(address);
        Button actionButton = customView.findViewById(R.id.action_button);
        actionButton.setText("Simpan Alamat");
        BottomDialog dialog = new BottomDialog.Builder(activity)
                .setTitle("Ubah Alamat")
                .setCancelable(true)
                .setContent("Tambahkan keterangan tempat sejelas mungkin")
                .setIcon(CustomDrawable.googleMaterialDrawable(activity, R.color.colorPrimaryDark, 24,
                        GoogleMaterial.Icon.gmd_mode_edit))
                .setCustomView(customView)
                .build();
        dialog.show();
        actionButton.setOnClickListener(view ->
        {
            listener.onDismiss(editText.getText().toString());
            dialog.dismiss();
        });
    }

    public static void searchAddress(Activity activity, String title, onDialogClose listener) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.layout_bottom_dialog_edittext, null);
        EditText editText = customView.findViewById(R.id.common_edit);
        editText.setHint("Alamat");
        Button actionButton = customView.findViewById(R.id.action_button);
        actionButton.setText(title);
        BottomDialog dialog = new BottomDialog.Builder(activity)
                .setTitle(title)
                .setContent("Cari nama jalan, nama tempat, nama gedung dan lain sebagainya. ")
                .setCancelable(true)
                .setIcon(CustomDrawable.googleMaterialDrawable(activity, R.color.colorPrimaryDark, 22,
                        GoogleMaterial.Icon.gmd_search))
                .setCustomView(customView)
                .build();
        dialog.show();
        actionButton.setOnClickListener(view ->
        {
            listener.onDismiss(editText.getText().toString());
            dialog.dismiss();
        });
    }*/


    public static void showTrackerDetail(Activity activity,Marker cctv){
        String cityName = "";
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.info_window, null);
        VideoView mVideoView = customView.findViewById(R.id.mVideoView);
        mVideoView.setVideoURI(Uri.parse(cctv.getSnippet()));
        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                mVideoView.start();
            }
        });
        mVideoView.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(Exception e) {
                e.printStackTrace();
                Toast.makeText(activity,
                        "CCTV untuk sementara tidak dapat diakses", Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        });
        mVideoView.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion() {
            }
        });

        BottomDialog dialog = new BottomDialog.Builder(activity)
                .setTitle(cctv.getTitle())
                .setCancelable(true)
                .setCustomView(customView)
                .setPositiveBackgroundColorResource(R.color.material_indigo_A400)

                .build();

        dialog.show();
    }

    /*public static void addNotes(Activity activity, String notes, onDialogClose listener) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.layout_bottom_dialog_edittext, null);
        EditText editText = customView.findViewById(R.id.common_edit);
        editText.setHint("Catatan");
        editText.setText(notes);
        Button actionButton = customView.findViewById(R.id.action_button);
        actionButton.setText("Tambahkan Catatan");
        BottomDialog dialog = new BottomDialog.Builder(activity)
                .setTitle("Tambahkan catatan")
                .setCancelable(true)
                .setContent("Tambahkan catatan sebagai deskripsi tambahan yang berhubungan dengan perjalanan Anda")
                .setIcon(CustomDrawable.googleMaterialDrawable(activity, R.color.colorPrimaryDark, 24,
                        GoogleMaterial.Icon.gmd_note_add))
                .setCustomView(customView)
                .build();
        dialog.show();
        actionButton.setOnClickListener(view ->
        {
            listener.onDismiss(editText.getText().toString());
            dialog.dismiss();
        });
    }


    public static class PlaceDialog {
        private Activity activity;
        private List<NominatimPlace> places;
        private onPlaceSelected listener;
        BottomDialog bDialog;

        private void dismissDialog(){
            bDialog.dismiss();

        }

        public PlaceDialog(Activity activity, String title, List<NominatimPlace> places, onPlaceSelected listener) {
            this.activity = activity;
            this.places = places;
            this.listener = listener;
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.layout_bottom_dialog_place, null);
            RecyclerView recyclerView = customView.findViewById(R.id.recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.clearFocus();
            PlaceAdapter.Listener pListener = (LatLng position, String address) -> {
                this.listener.onDismiss(position, address);
                this.dismissDialog();
            };
            PlaceAdapter adapter = new PlaceAdapter(this.places, pListener);
            recyclerView.setAdapter(adapter);

            bDialog = new BottomDialog.Builder(activity).setTitle(title)
                    .setCancelable(true)
                    .setIcon(CustomDrawable.googleMaterialDrawable(this.activity, R.color.colorPrimaryDark, 24,
                            GoogleMaterial.Icon.gmd_place))
                    .setCustomView(customView).build();
            bDialog.show();
        }
    }*/

}
