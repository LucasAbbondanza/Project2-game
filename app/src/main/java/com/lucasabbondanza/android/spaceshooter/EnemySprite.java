package com.lucasabbondanza.android.spaceshooter;

public class EnemySprite extends Sprite {

    private int xVelocity;
    private int yVelocity;
    private double time;


    EnemySprite(Vec2d p) {
        super(p);
        loadBitmaps();
        xVelocity = 0;
        yVelocity = 1000;
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.enemy), 0.1);
        setBitmaps(s);

    }

    private void path() {

    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
        setPosition(getPosition().add(new Vec2d(xVelocity *dt, yVelocity *dt)));
    }

    @Override
    public boolean isActive() {
        return false;
    }

    public void setVelocity(float x, float y) {

    }

}
