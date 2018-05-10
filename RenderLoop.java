package com.lucasabbondanza.android.spaceshooter;

import android.app.Fragment;
import android.graphics.Canvas;
import android.view.TextureView;
import android.widget.Button;

class RenderLoop implements Runnable {
    private static final int FPS = 30;
    private final World world;
    private final TextureView textureView;
    private final Button exitButton;

    public RenderLoop(TextureView textureView,Button exitButton) {
        this.textureView = textureView;
        this.exitButton = exitButton;
        world = new World(textureView,exitButton);
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            world.tick(1.0/FPS);
            drawWorld();
            try {
                delay();
            } catch (InterruptedException ex) {

            }
        }
    }

    private void delay() throws InterruptedException {
        Thread.sleep((long)(1.0/FPS * 1000));
    }

    private void drawWorld() {
        Canvas c = textureView.lockCanvas();
        try {
            world.draw(c);
        } finally {
            textureView.unlockCanvasAndPost(c);
        }
    }
}
