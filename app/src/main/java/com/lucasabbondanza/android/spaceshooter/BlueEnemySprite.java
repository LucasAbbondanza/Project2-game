package com.lucasabbondanza.android.spaceshooter;

import android.view.View;

public class BlueEnemySprite extends EnemySprite {

    private static BitmapSequence baseSequence;
    private static BitmapSequence deadSequence;
    private static BitmapSequence damageSequence;

    private int xVelocity;
    private int yVelocity;
    private int timer;
    private int health;
    private int deadTime;
    private boolean dead;
    private View view;

    BlueEnemySprite(Vec2d p, View view, boolean direction) {
        super(p);
        timer = 0;
        health = 4;
        yVelocity = -2000;
        if(direction)
            xVelocity = 2500;
        else
            xVelocity = -2500;
        this.view = view;
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        baseSequence = new BitmapSequence();
        baseSequence.addImage(r.getImage(R.drawable.blueenemy), 0.1);

        damageSequence = new BitmapSequence();
        damageSequence.addImage(r.getImage(R.drawable.blueenemydamage), 0.1);

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
        setBitmaps(baseSequence);
    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
        timer++;
        setPosition(getPosition().add(new Vec2d(xVelocity *dt, yVelocity *dt)));
        if(timer > 10) {
            if (position.getX() < 0)
                xVelocity = 2500;
            if (position.getX() > view.getWidth() - getBoundingBox().width())
                xVelocity = -2500;
        }
        if(dead) {
            deadTime++;
            if(deadTime > 10)
                removeTime = true;
        } else
            setBitmaps(baseSequence);
    }

    @Override
    public void makeDead() {
        if(health < 1) {
            dead = true;
            xVelocity = 0;
            yVelocity = 0;
            setBitmaps(deadSequence);
        } else {
            health--;
            setBitmaps(damageSequence);
        }
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public int getDeadTime() { return deadTime;}

}
