package com.lucasabbondanza.android.spaceshooter;

import android.view.View;

public class RedEnemySprite extends EnemySprite {

    private static BitmapSequence deadSequence;

    private int xVelocity;
    private int yVelocity;
    private int deadTime;
    private boolean dead;
    private View view;

    RedEnemySprite(Vec2d p, View view, boolean direction) {
        super(p);
        yVelocity = -500;
        if(direction)
            xVelocity = 2000;
        else
            xVelocity = -2000;
        this.view = view;
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.redenemy), 0.1);

        deadSequence = new BitmapSequence();
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_00), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_01), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_02), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_03), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_04), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_05), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_06), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_07), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_08), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_09), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_10), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_11), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.blank), 10);
        setBitmaps(s);
    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
        setPosition(getPosition().add(new Vec2d(xVelocity *dt, yVelocity *dt)));
        if(position.getX() < 0)
            xVelocity = 2000;
        if(position.getX() > view.getWidth()-getBoundingBox().width())
            xVelocity = -2000;
        if(dead) {
            deadTime++;
            if(deadTime > 10)
                removeTime = true;
        }
    }

    @Override
    public void makeDead() {
        dead = true;
        xVelocity = 0;
        yVelocity = 0;
        setBitmaps(deadSequence);
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public int getDeadTime() { return deadTime;}

}
