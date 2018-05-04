package walker;

import com.lucasabbondanza.android.project2.walker.R;

public class PlayerSprite extends Sprite {

    private static final int velocity = 400;
    public static boolean dead;
    private BitmapSequence deadSequence;
    private BitmapSequence s;
    private int numberOfDead = 0;

    public PlayerSprite(Vec2d v) {
        super(v);
        dead = false;
        if (numberOfDead < 3)
            makealive();
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.player), 0.1);
        setBitmaps(s);

        deadSequence = new BitmapSequence();
        deadSequence.addImage(r.getImage(R.drawable.boom), 100);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
        checkForTouch();
        setPosition(getPosition().add(new Vec2d((float)(0), 0)));
    }

    private void checkForTouch() {
    }

    @Override
    public void resolve(Collision collision, Sprite other) {
        if (!dead) makeDead();
    }

    private void makeDead() {
        dead = true;
        setBitmaps(deadSequence);
        numberOfDead +=1;
        // gameover.start();
    }
    private void makealive() {
        dead = false;
        setBitmaps(s);
    }
}
