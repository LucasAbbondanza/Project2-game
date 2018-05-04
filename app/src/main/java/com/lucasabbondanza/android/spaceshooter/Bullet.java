package com.lucasabbondanza.android.spaceshooter;

/**
 * Created by caitlynpeace on 4/30/18.
 */

public class Bullet extends Sprite{

    private static final int velocityX = 0;
    private static final int velocityY = -1000;

    public Bullet(Vec2d v) {
        super(v);
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.pbullet), 0.1);
        s.addImage(r.getImage(R.drawable.pbullet), 0.1);
        s.addImage(r.getImage(R.drawable.pbullet), 0.1);
        setBitmaps(s);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public void tick(double dt) {
        super.tick(dt);
        setPosition(getPosition().add(new Vec2d(velocityX*dt,velocityY*dt)));
    }

    public void getBool(boolean b)
    {
        if(b = true) {
            BitmapRepo r = BitmapRepo.getInstance();
            BitmapSequence s = new BitmapSequence();
            s.addImage(r.getImage(R.drawable.pbullet), 0.1);
            setBitmaps(s);
        }
        else{

        }
    }

}
