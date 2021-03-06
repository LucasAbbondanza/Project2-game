package com.lucasabbondanza.android.spaceshooter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;

public class GameFragment extends Fragment {

    private TextureView textureView;
    private TextureView menuButton;
    private Thread renderLoopThread;
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
        Database.getDatabase().resetScore();
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        BitmapRepo.getInstance().setContext(context);
        textureView = view.findViewById(R.id.texture_view);
        textureView.setSurfaceTextureListener(textureListener);
        textureView.setOnTouchListener((v, event) -> {
            TouchEventQueue.getInstance().enqueue(event);
            return true;
        });
        menuButton = view.findViewById(R.id.back_to_menu);
        menuButton.setOnClickListener((View v) -> {
            Intent intent = getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
        });
        Database.getDatabase().resetScore();
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

}
