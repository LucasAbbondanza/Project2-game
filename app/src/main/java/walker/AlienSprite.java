package walker;

import com.lucasabbondanza.android.project2.walker.R;

public class AlienSprite extends Sprite {

    private static final int velocityX = -300;
    private static final int velocityY = 0;

    public AlienSprite(Vec2d p) {
        super(p);
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.enemy), 0.1);
        //s.addImage(r.getImage(R.drawable.alien), 0.1);
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
}