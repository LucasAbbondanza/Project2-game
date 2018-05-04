package walker;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.lucasabbondanza.android.project2.walker.R;

public class GameFragment extends Fragment implements View.OnClickListener{

    private TextureView textureView;
    private Thread renderLoopThread;
    public static MediaPlayer gameOnsound ;
    private Context context;

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(android.view.LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_game, container, false);
        BitmapRepo.getInstance().setContext(context);
        //getting the button
        ImageButton buttonPlay = view.findViewById(R.id.buttonPlay);
        //adding a sound
        gameOnsound= MediaPlayer.create(context, R.raw.gameon);
        //adding a click listener
        buttonPlay.setOnClickListener(this);
        gameOnsound.start();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public void onClick(View v) {
        //starting game activity
        startActivity(new Intent(getActivity(), GameActivity.class));
        gameOnsound.stop();
    }
}
