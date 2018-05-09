package com.lucasabbondanza.android.spaceshooter;

import android.view.TextureView;

public class PlayerSprite extends Sprite {

    private int xVelocity;
    private int yVelocity;
    private int deadTime;
    private boolean dead;
    private boolean shoot;

    private BitmapSequence baseSequence;
    private BitmapSequence deadSequence;
    private TextureView view;

    PlayerSprite(Vec2d v, TextureView textureView) {
        super(v);
        view = textureView;
        xVelocity = 0;
        yVelocity = -1000;
        deadTime = 0;
        dead = false;
        shoot = false;
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();

        baseSequence = new BitmapSequence();
        baseSequence.addImage(r.getImage(R.drawable.player1), 0.1);
        baseSequence.addImage(r.getImage(R.drawable.player2), 0.1);
        baseSequence.addImage(r.getImage(R.drawable.player3), 0.1);
        
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

        setBitmaps(baseSequence);
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
        else if(position.getX() > view.getWidth()-getBoundingBox().width())
            setPosition(new Vec2d(view.getWidth()-getBoundingBox().width(), (int)position.getY()));
        if(dead) {
            shoot = false;
            deadTime++;
            if(deadTime > 10) {
                removeTime = true;
            }
        }
    }

    private void checkForTouch() {
    }

    @Override
    public void resolve(Collision collision, Sprite other) {
        if(other.isDangerous()) {
            if (!other.isDead()) {
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

    @Override
    public int getDeadTime() {
        return deadTime;
    }

    public void move(float targetX) {
        if(!dead) {
            if (position.getX() - targetX > -getBoundingBox().width() && position.getX() - targetX < 0) {
                xVelocity = 0;
                setBitmaps(baseSequence);
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
