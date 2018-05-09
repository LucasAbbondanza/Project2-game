package com.lucasabbondanza.android.spaceshooter;

/**
 * Created by caitlynpeace on 4/30/18.
 */

public class EnemyBulletSprite extends Sprite{

    private static final int velocityX = 0;
    private static final int velocityY = 100;
    private boolean dead;
    private boolean removeTime;

    public EnemyBulletSprite(Vec2d v) {
        super(v);
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.ebullet), 0.1);
        setBitmaps(s);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    public void tick(double dt) {
        super.tick(dt);
        setPosition(getPosition().add(new Vec2d(velocityX*dt,velocityY*dt)));
    }

    @Override
    public boolean isDangerous() {
        return true;
    }

    @Override
    public void resolve(Collision collision, Sprite other) { }

    public void makeDead() {
        if(!dead) {
            dead = true;
            removeTime = true;
        }
    }

}
