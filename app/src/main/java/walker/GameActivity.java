package walker;

import android.app.ActionBar;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;

import com.lucasabbondanza.android.project2.walker.R;


public class GameActivity extends AppCompatActivity {


    private TextureView textureView;
    private Thread renderLoopThread;
    public static MediaPlayer gameOnsound ;

    private TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            startThreads();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            stopThreads();
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    private void stopThreads() {
        if (renderLoopThread != null) {
            renderLoopThread.interrupt();
        }
        try {
            renderLoopThread.join();
        } catch (InterruptedException e) {
            // don't really care
        }
        renderLoopThread = null;
    }

    private void startThreads() {
        RenderLoop renderLoop = new RenderLoop(textureView);
        renderLoopThread = new Thread(renderLoop);
        renderLoopThread.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_game);
        BitmapRepo.getInstance().setContext(this);

        gameOnsound= MediaPlayer.create(GameActivity.this, R.raw.gameon);
        textureView = findViewById(R.id.texture_view);
        textureView.setSurfaceTextureListener(textureListener);
        textureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TouchEventQueue.getInstance().enqueue(event);
                return true;
            }
        });
        goFullScreen();
        gameOnsound.start();
    }

    // See https://developer.android.com/training/system-ui/status
    private void goFullScreen() {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        if (actionBar != null) actionBar.hide();
    }


}