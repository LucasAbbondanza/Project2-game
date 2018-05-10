package com.lucasabbondanza.android.spaceshooter;

import android.view.View;

public class GameMessageSprite extends Sprite {

    BitmapSequence gameOver;
    BitmapSequence level1;
    BitmapSequence level2;
    BitmapSequence level3;
    BitmapSequence win;

    View view;
    
    public GameMessageSprite(Vec2d v, View view, int select) {
        super(v);
        this.view = view;
        loadBitmaps(select);
        setPosition(new Vec2d((view.getWidth()- getBoundingBox().width())/2, getPosition().getY()));
    }

    private void loadBitmaps(int which) {
        BitmapRepo r = BitmapRepo.getInstance();

        level1 = new BitmapSequence();
        level1.addImage(r.getImage(R.drawable.level1), 3);
        level1.addImage(r.getImage(R.drawable.blank), 10000);

        level2 = new BitmapSequence();
        level2.addImage(r.getImage(R.drawable.level2), 3);
        level2.addImage(r.getImage(R.drawable.blank), 10000);

        level3 = new BitmapSequence();
        level3.addImage(r.getImage(R.drawable.level3), 3);
        level3.addImage(r.getImage(R.drawable.blank), 10000);

        BitmapSequence gameOver = new BitmapSequence();
        gameOver.addImage(r.getImage(R.drawable.over_frame_00), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_01), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_02), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_03), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_04), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_05), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_06), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_07), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_08), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_09), 0.001);
        //Removed frames that made the animation a bit awkward
        gameOver.addImage(r.getImage(R.drawable.over_frame_12), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_13), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_14), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_15), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_17), 0.001);
        gameOver.addImage(r.getImage(R.drawable.over_frame_19), 1);
        gameOver.addImage(r.getImage(R.drawable.over_frame_20_final), 100000);

        win = new BitmapSequence();
        win.addImage(r.getImage(R.drawable.winner), 100000);

        switch (which) {
            case 0: setBitmaps(gameOver);
                    break;
            case 1: setBitmaps(level1);
                    break;
            case 2: setBitmaps(level2);
                    break;
            case 3: setBitmaps(level3);
                    break;
            case 4: setBitmaps(win);
                    break;
        }
    }

    @Override
    public boolean isDangerous() {
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void makeDead() {

    }
}
