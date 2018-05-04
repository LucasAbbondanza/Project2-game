package walker;

import com.lucasabbondanza.android.project2.walker.R;

public class HeartSprite extends Sprite {

    private static final int velocity = 400;

    public HeartSprite(Vec2d p) {
        super(p);
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.heart), 0.1);
        s.addImage(r.getImage(R.drawable.heart1), 0.1);
        setBitmaps(s);

    }

    @Override
    public boolean isActive() {
        return false;
    }
}
