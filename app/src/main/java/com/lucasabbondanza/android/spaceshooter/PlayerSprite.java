package com.lucasabbondanza.android.spaceshooter;

import android.view.TextureView;

public class PlayerSprite extends Sprite {

    private int xVelocity;
    private int yVelocity;
    private double deathTime;
    private boolean dead;
    private boolean shoot;
    private BitmapSequence deadSequence;
    private BitmapSequence gameoverSequence;
    private TextureView view;

    PlayerSprite(Vec2d v, TextureView textureView) {
        super(v);
        view = textureView;
        xVelocity = 0;
        yVelocity = -1000;
        deathTime = 0;
        dead = false;
        shoot = false;
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.player), 0.1);
        setBitmaps(s);

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
        deadSequence.addImage(r.getImage(R.drawable.blank), 5);

        gameoverSequence = new BitmapSequence();
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_00), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_01), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_02), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_03), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_04), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_05), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_06), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_07), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_08), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_09), 0.001);
        //Removed frames that made the animation a bit awkward
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_12), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_13), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_14), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_15), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_17), 0.001);
        gameoverSequence.addImage(r.getImage(R.drawable.over_frame_19_final), 100000);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
        checkForTouch();
        setPosition(getPosition().add(new Vec2d((float)(xVelocity*dt), (float)(yVelocity*dt))));
        if(position.getX() < 0)
            setPosition(new Vec2d(0, (int)position.getY()));
        if(position.getX() > view.getWidth()-getBoundingBox().width())
            setPosition(new Vec2d(view.getWidth()-getBoundingBox().width(), (int)position.getY()));
        if(dead) {
            shoot = false;
            deathTime++;
            if(deathTime > 5) {
                removeTime = true;
            }
        }
    }

    private void checkForTouch() {
    }

    @Override
    public void resolve(Collision collision, Sprite other) {
        if(other.isDangerous()) {
            if (!dead && !other.isDead()) {
                makeDead();
            }
            if (!(other instanceof BulletSprite)) {
                other.makeDead();
            }
        } else {
            if(other instanceof StarSprite) {
                Database.getDatabase().addScore(300);
                other.makeDead();
            }
        }
    }

    public void makeDead() {
        dead = true;
        xVelocity = 0;
        yVelocity = 0;
        setBitmaps(deadSequence);
    }

    public void move(float targetX) {
        if(!dead) {
            if (position.getX() - targetX > -getBoundingBox().width()/2 && position.getX() - targetX < 0) {
                xVelocity = 0;
            } else {
                if (position.getX() > targetX) {
                    xVelocity = -3000;
                }
                if (position.getX() < targetX) {
                    xVelocity = 3000;
                }
            }
        }
    }

    public void move(double targetX) {
        move((float)targetX);
    }

    public void stop() {
        xVelocity = 0;
    }

    public void fire(boolean shoot) {
        this.shoot = shoot;
    }

    public boolean isShooting() {
        return shoot;
    }

    public boolean isDead() {
        return dead;
    }

}
