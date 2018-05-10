package com.lucasabbondanza.android.spaceshooter;

import android.util.Log;

/**
 * Created by caitlynpeace on 4/30/18.
 */

public class BulletSprite extends Sprite{

    private static final int velocityX = 0;
    private static final int velocityY = -4000;
    private boolean dead;
    private boolean removeTime;
    private int timer;

    public BulletSprite(Vec2d v) {
        super(v);
        timer = 0;
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.pbullet), 0.1);
        setBitmaps(s);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    public void tick(double dt) {
        super.tick(dt);
        timer++;
        setPosition(getPosition().add(new Vec2d(velocityX*dt,velocityY*dt)));
        if(timer > 20) {
            makeDead();
        }
    }

    @Override
    public boolean isDangerous() {
        return false;
    }

    @Override
    public void resolve(Collision collision, Sprite other) {
        if(!other.isDead() && other.isDangerous())
            makeDead();
        if(other instanceof EnemySprite) {
            other.makeDead();
            if(other instanceof RedEnemySprite)
                Database.getDatabase().addScore(200);
            else if(other instanceof BlueEnemySprite)
                Database.getDatabase().addScore(500);
            else
                Database.getDatabase().addScore(100);
        }
    }

    public void makeDead() {
        if(!dead) {
            dead = true;
            removeTime = true;
        }
    }

    @Override
    public boolean isRemoveTime() {
        return removeTime;
    }

}
