package com.lucasabbondanza.android.spaceshooter;

public class LivesSprite extends Sprite {

    private static final int velocity = 400;

    public LivesSprite(Vec2d p) {
        super(p);
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.heart), 0);
        setBitmaps(s);

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void makeDead() {

    }
}