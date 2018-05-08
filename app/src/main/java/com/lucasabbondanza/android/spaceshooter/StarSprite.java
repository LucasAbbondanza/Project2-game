package com.lucasabbondanza.android.spaceshooter;

public class StarSprite extends Sprite {

    private static final int velocityX = 0;
    private static final int velocityY = 0;

    public StarSprite(Vec2d p) {
        super(p);
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.star_frame_00), .25);
        s.addImage(r.getImage(R.drawable.star_frame_01), .25);
        setBitmaps(s);
    }


    @Override
    public void tick(double dt) {
        super.tick(dt);
        setPosition(getPosition().add(new Vec2d(velocityX*dt,velocityY*dt)));

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean isDangerous() {
        return false;
    }

    @Override
    public void makeDead(){
        if(!dead)
            dead = true;
        removeTime = true;
    }
}