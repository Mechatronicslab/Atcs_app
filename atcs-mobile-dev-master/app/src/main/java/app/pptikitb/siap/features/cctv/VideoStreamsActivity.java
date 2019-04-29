package app.pptikitb.siap.features.cctv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;

import app.pptikitb.siap.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoStreamsActivity extends AppCompatActivity implements OnPreparedListener, OnErrorListener, OnCompletionListener {
    @BindView(R.id.videoView)
    VideoView mVideoView;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_streams);
        ButterKnife.bind(this);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            pDialog = new ProgressDialog(this);
            pDialog.setCancelable(true);
            pDialog.setMessage("Loading...");
            showDialog();

            mVideoView.setVideoURI(Uri.parse(b.getString("videoUrl")));
            mVideoView.setOnPreparedListener(this);
            mVideoView.setOnErrorListener(this);
            mVideoView.setOnCompletionListener(this);

        }

    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onPrepared() {
        hideDialog();
        mVideoView.start();
    }

    @Override
    public boolean onError(Exception e) {
        hideDialog();
        e.printStackTrace();
        Toast.makeText(getApplicationContext(),
                "CCTV untuk sementara tidak dapat diakses", Toast.LENGTH_LONG)
                .show();
        finish();
        return false;
    }

    @Override
    public void onCompletion() {
        finish();
    }

}
